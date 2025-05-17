package com.fxd927.mekanismelements.common.tier;

import com.fxd927.mekanismelements.api.IMSTier;
import com.fxd927.mekanismelements.api.MSBaseTier;
import mekanism.common.config.value.CachedIntValue;

public enum MSBinTier implements IMSTier {
    TRANSCENDENT(MSBaseTier.TRANSCENDENT, Long.MAX_VALUE);

    private final long baseStorage;
    private final MSBaseTier baseTier;
    private CachedIntValue storageReference;

    MSBinTier(MSBaseTier tier, long s) {
        baseTier = tier;
        baseStorage = s;
    }

    @Override
    public MSBaseTier getBaseTier() {
        return baseTier;
    }

    public long getStorage() {
        return storageReference == null ? getBaseStorage() : storageReference.getOrDefault();
    }

    public long getBaseStorage() {
        return baseStorage;
    }

    /**
     * ONLY CALL THIS FROM TierConfig. It is used to give the BinTier a reference to the actual config value object
     */
    public void setConfigReference(CachedIntValue storageReference) {
        this.storageReference = storageReference;
    }
}