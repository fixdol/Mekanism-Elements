package com.fxd927.mekanismelements.common;

import mekanism.api.text.ILangEntry;
import net.minecraft.Util;

public enum MSLang implements ILangEntry {
    DESCRIPTION_ADSORPTION_SEPARATOR("description", "adsorption_separator"),
    DESCRIPTION_AIR_COMPRESSOR("description", "air_compressor"),
    DESCRIPTION_RADIATION_IRRADIATOR("description","radiation_irradiator"),
    ADSORPTION_TYPE_SEAWATER_METAL_EXTRACTOR("description", "adsorption_type_seawater_metal_extractor"),
    DESCRIPTION_ORGANIC_LIQUID_EXTRACTOR("description","organic_liquid_extractor"),
    DESCRIPTION_SEAWATER_PUMP("description", "seawater_pump"),
    MEKANISM_SCIENCE("constants","mod_name"),
    MEKANISM_SCIENCE_BUILDING("constants","building");

    private final String key;

    MSLang(String type,String path){
        this(Util.makeDescriptionId(type, MekanismElements.rl(path)));
    }

    MSLang(String key){
        this.key = key;
    }

    @Override
    public String getTranslationKey(){
        return key;
    }
}
