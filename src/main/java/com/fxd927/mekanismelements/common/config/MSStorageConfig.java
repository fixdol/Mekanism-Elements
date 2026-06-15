package com.fxd927.mekanismelements.common.config;

import mekanism.common.config.BaseMekanismConfig;
import mekanism.common.config.IConfigTranslation;
import mekanism.common.config.value.CachedLongValue;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.fml.config.ModConfig;

public class MSStorageConfig extends BaseMekanismConfig {
    private final ModConfigSpec configSpec;

    public final CachedLongValue airCompressor;
    public final CachedLongValue radiationIrradiator;
    public final CachedLongValue adsorptionSeparator;
    public final CachedLongValue seawaterPump;
    public final CachedLongValue organicLiquidExtractor;

    MSStorageConfig() {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();
        builder.comment("Science Energy Storage Config. This config is synced from server to client.").push("storage");

        airCompressor = CachedLongValue.define(this, builder, new IConfigTranslation.ConfigTranslation("config.mekanismelements.storage.airCompressor", "Air Compressor", "Base energy storage (Joules)."), "airCompressor", 40_000L, 0L, Long.MAX_VALUE);

        radiationIrradiator = CachedLongValue.define(this, builder, new IConfigTranslation.ConfigTranslation("config.mekanismelements.storage.radiationIrradiator", "Radiation Irradiator", "Base energy storage (Joules)."), "radiationIrradiator", 40_000L, 0L, Long.MAX_VALUE);

        adsorptionSeparator = CachedLongValue.define(this, builder, new IConfigTranslation.ConfigTranslation("config.mekanismelements.storage.adsorptionSeparator", "Adsorption Separator", "Base energy storage (Joules)."), "adsorptionSeparator", 40_000L, 0L, Long.MAX_VALUE);

        organicLiquidExtractor = CachedLongValue.define(this, builder, new IConfigTranslation.ConfigTranslation("config.mekanismelements.storage.organicLiquidExtractor", "Organic Liquid Extractor", "Base energy storage (Joules)."), "organicLiquidExtractor", 40_000L, 0L, Long.MAX_VALUE);

        seawaterPump = CachedLongValue.define(this, builder, new IConfigTranslation.ConfigTranslation("config.mekanismelements.storage.seawaterPump", "Seawater Pump", "Base energy storage (Joules)."), "seawaterPump", 40_000L, 0L, Long.MAX_VALUE);

        builder.pop();
        configSpec = builder.build();
    }

    @Override
    public String getFileName() {
        return "science-storage";
    }

    @Override
    public ModConfigSpec getConfigSpec() {
        return configSpec;
    }

    @Override
    public ModConfig.Type getConfigType() {
        return ModConfig.Type.SERVER;
    }

    @Override
    public String getTranslation() {
        return "config.mekanismelements.storage";
    }
}

