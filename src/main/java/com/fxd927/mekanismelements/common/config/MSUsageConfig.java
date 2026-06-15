package com.fxd927.mekanismelements.common.config;

import mekanism.common.config.BaseMekanismConfig;
import mekanism.common.config.IConfigTranslation;
import mekanism.common.config.value.CachedLongValue;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.fml.config.ModConfig;

public class MSUsageConfig extends BaseMekanismConfig {

    public final CachedLongValue airCompressor;
    public final CachedLongValue radiationIrradiator;
    public final CachedLongValue adsorptionSeparator;
    public final CachedLongValue seawaterPump;
    public final CachedLongValue organicLiquidExtractor;

    private final ModConfigSpec configSpec;

    MSUsageConfig() {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();
        builder.comment("MS Energy Usage Config. This config is synced from server to client.").push("storage");

        airCompressor = CachedLongValue.define(this, builder, new IConfigTranslation.ConfigTranslation("config.mekanismelements.usage.airCompressor", "Air Compressor", "Energy usage per tick (Joules)."), "airCompressor", 100L, 0L, Long.MAX_VALUE);
        radiationIrradiator = CachedLongValue.define(this, builder, new IConfigTranslation.ConfigTranslation("config.mekanismelements.usage.radiationIrradiator", "Radiation Irradiator", "Energy usage per tick (Joules)."), "radiationIrradiator", 1_000L, 0L, Long.MAX_VALUE);
        adsorptionSeparator = CachedLongValue.define(this, builder, new IConfigTranslation.ConfigTranslation("config.mekanismelements.usage.adsorptionSeparator", "Adsorption Separator", "Energy usage per tick (Joules)."), "adsorptionSeparator", 500L, 0L, Long.MAX_VALUE);
        organicLiquidExtractor = CachedLongValue.define(this, builder, new IConfigTranslation.ConfigTranslation("config.mekanismelements.usage.organicLiquidExtractor", "Organic Liquid Extractor", "Energy usage per tick (Joules)."), "organicLiquidExtractor", 100L, 0L, Long.MAX_VALUE);
        seawaterPump = CachedLongValue.define(this, builder, new IConfigTranslation.ConfigTranslation("config.mekanismelements.usage.seawaterPump", "Seawater Pump", "Energy usage per tick (Joules)."), "seawaterPump", 100L, 0L, Long.MAX_VALUE);

        builder.pop();
        configSpec = builder.build();
    }

    @Override
    public String getFileName() {
        return "science-usage";
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
        return "config.mekanismelements.usage";
    }
}

