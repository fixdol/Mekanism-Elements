package com.fxd927.mekanismelements.common.gas;

import mekanism.common.base.IChemicalConstant;

public enum MSChemicalConstants implements IChemicalConstant {
    AMMONIA("ammonia", 0xFFC8B3FF, 0, 240F, 0.6942F),
    AMMONIUM_NITRATE("ammonium_nitrate", 0xFF5E89FF, 0, 483.15F, 1700F),
    AQUA_REGIA("aqua_regia", 0xFFF3DC44, 0, 381F, 1210F),
    BERYLLIUM("beryllium", 0xFF00DB1B, 0, 1560.15F, 1850F),
    BROMINE("bromine", 0xFFBA1A08, 0, 332F, 3102.8F),
    COMPRESSED_AIR("compressed_air", 0xFFFFFFFF, 0, 78.80F, 870F),
    HELIUM("helium", 0xFFE0F0FF, 0, 4.22F, 124.9F),
    HYDROGEN_CYANIDE("hydrogen_cyanide", 0xFFC7D9F4, 0, 299F, 687.6F),
    SUPERHEATED_HELIUM("superheated_helium", 0xFFFFDDB5, 0, 2_000F, 124.9F),
    IODINE("iodine", 0xFF8536B2, 0, 363.7F, 4933F),
    METHANE("methane", 0xFFC6FFE9, 0, 112F, 	0.717F),
    NETHERITE_ACID("netherite_acid", 0xFF4D4C4C, 0, 381F, 1210F),
    NITRIC_ACID("nitric_acid", 0xFFB5BCFF, 0, 363.7F, 4933F),
    NITROGEN("nitrogen", 0xFFFA8FF0, 0, 77.36F, 1.251F),
    NITRIC_OXIDE("nitric_oxide", 0xFFE19DF2, 0, 121F, 1.34F),
    NITROGEN_DIOXIDE("nitrogen_dioxide", 0xFFD6A3F3, 0, 294F, 1.449F),
    POTASSIUM_CHLORIDE("potassium_chloride", 0xFF66DE9E, 0, 1690F, 1984F),
    POTASSIUM_CYANIDE("potassium_cyanide", 0xFFA7E0DB, 0, 1898F, 1520F),
    POTASSIUM_HYDROXIDE("potassium_hydroxide", 0xFF87E7C3, 0, 1600F, 2044F),
    POTASSIUM_IODIDE("potassium_iodide", 0xFFC25CC1, 0, 1603.15F, 3130F),
    STRONTIUM("strontium", 0xFFFFC1B2, 0, 1050.15F, 2375F),
    SEAWATER("seawater", 0xFF06C9E6, 0, 373.15F, 1000F),
    XENON("xenon", 0xFF665BFF, 0, 373.15F, 1030F),
    YTTRIUM("yttrium", 0xFFCCE5FF, 0, 1799.15F, 4240F);

    private final String name;
    private final int color;
    private final int lightLevel;
    private final float temperature;
    private final float density;

    MSChemicalConstants(String name, int color, int lightLevel, float temperature, float density) {
        this.name = name;
        this.color = color;
        this.lightLevel = lightLevel;
        this.temperature = temperature;
        this.density = density;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getColor() {
        return color;
    }

    @Override
    public int getLightLevel() {
        return lightLevel;
    }

    @Override
    public float getTemperature() {
        return temperature;
    }

    @Override
    public float getDensity() {
        return density;
    }

}

