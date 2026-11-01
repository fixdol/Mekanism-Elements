package fixdol.mekanismelements.common.config;

import mekanism.common.config.BaseMekanismConfig;
import mekanism.common.config.IConfigTranslation;
import mekanism.common.config.value.CachedBooleanValue;
import mekanism.common.config.value.CachedIntValue;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.fml.config.ModConfig;

public class MSUsageConfig extends BaseMekanismConfig {

    public final CachedBooleanValue gasGeneratorDebug;
    public final CachedIntValue airCompressor;
    public final CachedIntValue radiationIrradiator;
    public final CachedIntValue adsorptionSeparator;
    public final CachedIntValue seawaterPump;
    public final CachedIntValue organicLiquidExtractor;
    public final CachedIntValue infinityOreReprocessing;

    private final ModConfigSpec configSpec;

    MSUsageConfig() {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();
        builder.comment("MS Energy Usage Config. This config is synced from server to client.").push("storage");

        gasGeneratorDebug = CachedBooleanValue.wrap(this, builder.comment(
                "Enable debug logging for the Gas Generator mixin.",
                "Logs fuel/burn/energy info every server tick. Disable in production.")
                .define("gasGeneratorDebug", false));

        airCompressor = CachedIntValue.wrap(this, new IConfigTranslation.ConfigTranslation("config.mekanismelements.usage.airCompressor", "Air Compressor", "Energy usage per tick (Joules).").applyToBuilder(builder).defineInRange("airCompressor", 100, 0, Integer.MAX_VALUE));
        radiationIrradiator = CachedIntValue.wrap(this, new IConfigTranslation.ConfigTranslation("config.mekanismelements.usage.radiationIrradiator", "Radiation Irradiator", "Energy usage per tick (Joules).").applyToBuilder(builder).defineInRange("radiationIrradiator", 1_000, 0, Integer.MAX_VALUE));
        adsorptionSeparator = CachedIntValue.wrap(this, new IConfigTranslation.ConfigTranslation("config.mekanismelements.usage.adsorptionSeparator", "Adsorption Separator", "Energy usage per tick (Joules).").applyToBuilder(builder).defineInRange("adsorptionSeparator", 500, 0, Integer.MAX_VALUE));
        organicLiquidExtractor = CachedIntValue.wrap(this, new IConfigTranslation.ConfigTranslation("config.mekanismelements.usage.organicLiquidExtractor", "Organic Liquid Extractor", "Energy usage per tick (Joules).").applyToBuilder(builder).defineInRange("organicLiquidExtractor", 100, 0, Integer.MAX_VALUE));
        seawaterPump = CachedIntValue.wrap(this, new IConfigTranslation.ConfigTranslation("config.mekanismelements.usage.seawaterPump", "Seawater Pump", "Energy usage per tick (Joules).").applyToBuilder(builder).defineInRange("seawaterPump", 100, 0, Integer.MAX_VALUE));
        infinityOreReprocessing = CachedIntValue.wrap(this, new IConfigTranslation.ConfigTranslation("config.mekanismelements.usage.infinityOreReprocessing", "Infinity Ore Reprocessing", "Energy usage per tick (Joules).").applyToBuilder(builder).defineInRange("infinityOreReprocessing", 500, 0, Integer.MAX_VALUE));

        builder.pop();
        configSpec = builder.build();
    }

    @Override
    public String getFileName() {
        return "MekanismElements-Usage";
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