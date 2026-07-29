package fixdol.mekanismelements.common.tier;

import fixdol.mekanismelements.api.IMSTier;
import fixdol.mekanismelements.api.MSBaseTier;
import mekanism.common.config.value.CachedLongValue;
import org.jetbrains.annotations.Nullable;

public enum MSInductionCellTier implements IMSTier {
    TRANSCENDENT(MSBaseTier.TRANSCENDENT, Long.MAX_VALUE);

    private final long baseMaxEnergy;
    private final MSBaseTier baseTier;
    @Nullable
    private CachedLongValue storageReference;

    MSInductionCellTier(MSBaseTier tier, long max) {
        baseMaxEnergy = max;
        baseTier = tier;
    }

    @Override
    public MSBaseTier getBaseTier() {
        return baseTier;
    }

    public long getMaxEnergy() {
        return storageReference == null ? getBaseMaxEnergy() : storageReference.getOrDefault();
    }

    public long getBaseMaxEnergy() {
        return baseMaxEnergy;
    }

    /**
     * ONLY CALL THIS FROM TierConfig. It is used to give the InductionCellTier a reference to the actual config value object
     */
    public void setConfigReference(CachedLongValue storageReference) {
        this.storageReference = storageReference;
    }
}
