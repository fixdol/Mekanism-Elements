package com.fxd927.mekanismelements.common.config;

import mekanism.api.math.FloatingLong;
import mekanism.common.config.BaseMekanismConfig;
import mekanism.common.config.value.CachedFloatingLongValue;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.config.ModConfig;

public class MSStorageConfig extends BaseMekanismConfig {
    private final ForgeConfigSpec configSpec;

    public final CachedFloatingLongValue airCompressor;
    public final CachedFloatingLongValue radiationIrradiator;
    public final CachedFloatingLongValue adsorptionSeparator;
    public final CachedFloatingLongValue seawaterPump;
    public final CachedFloatingLongValue organicLiquidExtractor;

    MSStorageConfig() {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
        builder.comment("Science Energy Storage Config. This config is synced from server to client.").push("storage");

        airCompressor = CachedFloatingLongValue.define(this, builder, "Base energy storage (Joules).", "airCompressor",
                FloatingLong.createConst(40_000));

        radiationIrradiator = CachedFloatingLongValue.define(this, builder, "Base energy storage (Joules).", "airCompressor",
                FloatingLong.createConst(40_000));

        adsorptionSeparator = CachedFloatingLongValue.define(this, builder, "Base energy storage (Joules).", "adsorptionTypeSeawaterMetalExtractor",
                FloatingLong.createConst(40_000));

        organicLiquidExtractor = CachedFloatingLongValue.define(this, builder, "Base energy storage (Joules).", "organicLiquidExtractor",
                FloatingLong.createConst(40_000));

        seawaterPump = CachedFloatingLongValue.define(this, builder, "Base energy storage (Joules).", "seawaterPump",
                FloatingLong.createConst(40_000));

        builder.pop();
        configSpec = builder.build();
    }

    @Override
    public String getFileName() {
        return "science-storage";
    }

    @Override
    public ForgeConfigSpec getConfigSpec() {
        return configSpec;
    }

    @Override
    public ModConfig.Type getConfigType() {
        return ModConfig.Type.SERVER;
    }

    @Override
    public boolean addToContainer() {
        return false;
    }
}
