package com.fxd927.mekanismelements.common.tier;

import com.fxd927.mekanismelements.api.IMSTier;
import com.fxd927.mekanismelements.api.MSBaseTier;
import mekanism.api.math.FloatingLong;
import mekanism.common.config.value.CachedFloatingLongValue;
import org.jetbrains.annotations.Nullable;

public enum MSInductionCellTier implements IMSTier {
    TRANSCENDENT(MSBaseTier.TRANSCENDENT, FloatingLong.createConst(Long.MAX_VALUE));

    private final FloatingLong baseMaxEnergy;
    private final MSBaseTier baseTier;
    @Nullable
    private CachedFloatingLongValue storageReference;

    MSInductionCellTier(MSBaseTier tier, FloatingLong max) {
        baseMaxEnergy = max;
        baseTier = tier;
    }

    @Override
    public MSBaseTier getBaseTier() {
        return baseTier;
    }

    public FloatingLong getMaxEnergy() {
        return storageReference == null ? getBaseMaxEnergy() : storageReference.getOrDefault();
    }

    public FloatingLong getBaseMaxEnergy() {
        return baseMaxEnergy;
    }

    /**
     * ONLY CALL THIS FROM TierConfig. It is used to give the InductionCellTier a reference to the actual config value object
     */
    public void setConfigReference(CachedFloatingLongValue storageReference) {
        this.storageReference = storageReference;
    }
}