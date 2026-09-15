package fixdol.mekanismelements.common.config;

import mekanism.api.chemical.gas.Gas;

import mekanism.common.config.BaseMekanismConfig;
import mekanism.common.config.value.CachedFloatingLongValue;
import mekanism.api.math.FloatingLong;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.config.ModConfig;

import mekanism.common.config.value.CachedBooleanValue;

public class MSUsageConfig extends BaseMekanismConfig {

    public final CachedBooleanValue gasGeneratorDebug;
    public final CachedFloatingLongValue airCompressor;
    public final CachedFloatingLongValue radiationIrradiator;
    public final CachedFloatingLongValue adsorptionSeparator;
    public final CachedFloatingLongValue seawaterPump;
    public final CachedFloatingLongValue organicLiquidExtractor;
    public final CachedFloatingLongValue infinityOreReprocessing;

    private final ForgeConfigSpec configSpec;

    MSUsageConfig() {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
        builder.comment("MS Energy Usage Config. This config is synced from server to client.").push("usage");

        gasGeneratorDebug = CachedBooleanValue.wrap(this, builder.comment(
                "Enable debug logging for the Gas Generator mixin.",
                "Logs fuel/burn/energy info every server tick. Disable in production.")
                .define("gasGeneratorDebug", false));

        airCompressor = CachedFloatingLongValue.define(this, builder, "Energy usage per tick (Joules).", "airCompressor", FloatingLong.createConst(100));
        radiationIrradiator = CachedFloatingLongValue.define(this, builder, "Energy usage per tick (Joules).", "radiationIrradiator", FloatingLong.createConst(1_000));
        adsorptionSeparator = CachedFloatingLongValue.define(this, builder, "Energy usage per tick (Joules).", "adsorptionSeparator", FloatingLong.createConst(500));
        organicLiquidExtractor = CachedFloatingLongValue.define(this, builder, "Energy usage per tick (Joules).", "organicLiquidExtractor", FloatingLong.createConst(100));
        seawaterPump = CachedFloatingLongValue.define(this, builder, "Energy usage per tick (Joules).", "seawaterPump", FloatingLong.createConst(100));
        infinityOreReprocessing = CachedFloatingLongValue.define(this, builder, "Energy usage per tick (Joules).", "infinityOreReprocessing", FloatingLong.createConst(500));

        builder.pop();
        configSpec = builder.build();
    }

    @Override
    public String getFileName() {
        return "MekanismElements-Usage";
    }

    @Override
    public ForgeConfigSpec getConfigSpec() {
        return configSpec;
    }

    @Override
    public ModConfig.Type getConfigType() {
        return ModConfig.Type.SERVER;
    }
}
