package com.fxd927.mekanismelements.common.registries;

import com.fxd927.mekanismelements.common.MekanismElements;
import com.fxd927.mekanismelements.common.gas.MSChemicalConstants;
import mekanism.api.chemical.Chemical;
import mekanism.api.chemical.ChemicalBuilder;
import mekanism.api.chemical.attribute.ChemicalAttributes;
import mekanism.common.registration.impl.ChemicalDeferredRegister;
import mekanism.common.registration.impl.DeferredChemical;

public class MSGases {
    public static final ChemicalDeferredRegister GASES = new ChemicalDeferredRegister(MekanismElements.MODID);

    public static final DeferredChemical<Chemical> AMERICIUM;
    public static final DeferredChemical<Chemical> AMMONIA;
    public static final DeferredChemical<Chemical> AMMONIUM_NITRATE;
    public static final DeferredChemical<Chemical> AQUA_REGIA;
    public static final DeferredChemical<Chemical> BROMINE;
    public static final DeferredChemical<Chemical> BERYLLIUM;
    public static final DeferredChemical<Chemical> CALIFORNIUM;
    public static final DeferredChemical<Chemical> COMPRESSED_AIR;
    public static final DeferredChemical<Chemical> CURIUM;
    public static final DeferredChemical<Chemical> DISSOLVED_SPENT_NUCLEAR_WASTE;
    public static final DeferredChemical<Chemical> HELIUM;
    public static final DeferredChemical<Chemical> HYDROGEN_CYANIDE;
    public static final DeferredChemical<Chemical> SUPERHEATED_HELIUM;
    public static final DeferredChemical<Chemical> IODINE;
    public static final DeferredChemical<Chemical> METHANE;
    public static final DeferredChemical<Chemical> NITROGEN_DIOXIDE;
    public static final DeferredChemical<Chemical> NITRIC_ACID;
    public static final DeferredChemical<Chemical> NITROGEN;
    public static final DeferredChemical<Chemical> NETHERITE_ACID;
    public static final DeferredChemical<Chemical> NITRIC_OXIDE;
    public static final DeferredChemical<Chemical> POTASSIUM_CHLORIDE;
    public static final DeferredChemical<Chemical> POTASSIUM_CYANIDE;
    public static final DeferredChemical<Chemical> POTASSIUM_HYDROXIDE;
    public static final DeferredChemical<Chemical> POTASSIUM_IODIDE;
    public static final DeferredChemical<Chemical> SEAWATER;
    public static final DeferredChemical<Chemical> STRONTIUM;
    public static final DeferredChemical<Chemical> XENON;
    public static final DeferredChemical<Chemical> YTTRIUM;

    static {
        AMERICIUM                     = GASES.register("americium", 13983840);
        AMMONIA                       = GASES.register(MSChemicalConstants.AMMONIA);
        AMMONIUM_NITRATE              = GASES.register(MSChemicalConstants.AMMONIUM_NITRATE);
        AQUA_REGIA                    = GASES.register(MSChemicalConstants.AQUA_REGIA);
        BROMINE                       = GASES.register(MSChemicalConstants.BROMINE);
        BERYLLIUM                     = GASES.register(MSChemicalConstants.BERYLLIUM);
        CALIFORNIUM                   = GASES.register("californium", 0x00F08B00);
        COMPRESSED_AIR                = GASES.register(MSChemicalConstants.COMPRESSED_AIR);
        CURIUM                        = GASES.register("curium", 15725501);
        DISSOLVED_SPENT_NUCLEAR_WASTE = GASES.register("dissolved_spent_nuclear_waste", 0x8588b1);
        HELIUM                        = GASES.register(MSChemicalConstants.HELIUM);
        HYDROGEN_CYANIDE              = GASES.register(MSChemicalConstants.HYDROGEN_CYANIDE);
        SUPERHEATED_HELIUM            = GASES.register(MSChemicalConstants.SUPERHEATED_HELIUM);
        IODINE                        = GASES.register(MSChemicalConstants.IODINE);
        METHANE                       = GASES.register(MSChemicalConstants.METHANE);
        NETHERITE_ACID                = GASES.register(MSChemicalConstants.NETHERITE_ACID);
        NITRIC_ACID                   = GASES.register(MSChemicalConstants.NITRIC_ACID);
        NITROGEN                      = GASES.register(MSChemicalConstants.NITROGEN);
        NITROGEN_DIOXIDE              = GASES.register(MSChemicalConstants.NITROGEN_DIOXIDE);
        NITRIC_OXIDE                  = GASES.register(MSChemicalConstants.NITRIC_OXIDE);
        POTASSIUM_CHLORIDE            = GASES.register(MSChemicalConstants.POTASSIUM_CHLORIDE);
        POTASSIUM_CYANIDE             = GASES.register(MSChemicalConstants.POTASSIUM_CYANIDE);
        POTASSIUM_HYDROXIDE           = GASES.register(MSChemicalConstants.POTASSIUM_HYDROXIDE);
        POTASSIUM_IODIDE              = GASES.register(MSChemicalConstants.POTASSIUM_IODIDE);
        SEAWATER                      = GASES.register(MSChemicalConstants.SEAWATER);
        STRONTIUM                     = GASES.register(MSChemicalConstants.STRONTIUM);
        XENON                         = GASES.register(MSChemicalConstants.XENON);
        YTTRIUM                       = GASES.register(MSChemicalConstants.YTTRIUM);
    }

    private MSGases() {
    }

    
    public static class Coolants {


        public static ChemicalAttributes.CooledCoolant getHeliumCoolant() {
            return new ChemicalAttributes.CooledCoolant(
                    SUPERHEATED_HELIUM.get().getAsHolder(), 100, 1.5);
        }

        public static ChemicalAttributes.HeatedCoolant getHeatedHeliumCoolant() {
            return new ChemicalAttributes.HeatedCoolant(
                    HELIUM.get().getAsHolder(), 100);
        }
    }
}