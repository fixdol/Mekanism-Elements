package com.fxd927.mekanismelements.common.registries;

import com.fxd927.mekanismelements.common.MekanismElements;
import com.fxd927.mekanismelements.common.gas.MSChemicalConstants;
import com.fxd927.mekanismelements.mixin.CoolantAccessor;
import mekanism.api.chemical.gas.Gas;
import mekanism.api.chemical.gas.attribute.GasAttributes;
import mekanism.api.math.FloatingLong;
import mekanism.common.registration.impl.GasDeferredRegister;
import mekanism.common.registration.impl.GasRegistryObject;

public class MSGases {
    public static final GasDeferredRegister GASES = new GasDeferredRegister(MekanismElements.MODID);

    public static final GasRegistryObject<Gas> AMERICIUM;
    public static final GasRegistryObject<Gas> AMMONIA;
    public static final GasRegistryObject<Gas> AMMONIUM_NITRATE;
    public static final GasRegistryObject<Gas> AQUA_REGIA;
    public static final GasRegistryObject<Gas> BROMINE;
    public static final GasRegistryObject<Gas> BERYLLIUM;
    //public static final GasRegistryObject<Gas> CALIFORNIUM;
    public static final GasRegistryObject<Gas> COMPRESSED_AIR;
    //public static final GasRegistryObject<Gas> CURIUM;
    public static final GasRegistryObject<Gas> DISSOLVED_SPENT_NUCLEAR_WASTE;
    public static final GasRegistryObject<Gas> HELIUM;
    public static final GasRegistryObject<Gas> HYDROGEN_CYANIDE;
    public static final GasRegistryObject<Gas> SUPERHEATED_HELIUM;
    public static final GasRegistryObject<Gas> IODINE;
    public static final GasRegistryObject<Gas> METHANE;
    public static final GasRegistryObject<Gas> NITROGEN_DIOXIDE;
    public static final GasRegistryObject<Gas> NITRIC_ACID;
    public static final GasRegistryObject<Gas> NITROGEN;
    //public static final GasRegistryObject<Gas> NETHERITE_ACID;
    public static final GasRegistryObject<Gas> NITRIC_OXIDE;
    public static final GasRegistryObject<Gas> POTASSIUM_CHLORIDE;
    public static final GasRegistryObject<Gas> POTASSIUM_CYANIDE;
    public static final GasRegistryObject<Gas> POTASSIUM_HYDROXIDE;
    public static final GasRegistryObject<Gas> POTASSIUM_IODIDE;
    public static final GasRegistryObject<Gas> SEAWATER;
    public static final GasRegistryObject<Gas> STRONTIUM;
    public static final GasRegistryObject<Gas> XENON;
    public static final GasRegistryObject<Gas> YTTRIUM;

    static {
        AMERICIUM = GASES.register("americium", 13983840, new GasAttributes.Radiation(0.05));
        AMMONIA = GASES.register(MSChemicalConstants.AMMONIA, new GasAttributes.Fuel(()-> 100, ()-> FloatingLong.createConst(600000)));
        AMMONIUM_NITRATE = GASES.register(MSChemicalConstants.AMMONIUM_NITRATE);
        AQUA_REGIA = GASES.register(MSChemicalConstants.AQUA_REGIA);
        BROMINE = GASES.register(MSChemicalConstants.BROMINE);
        BERYLLIUM = GASES.register(MSChemicalConstants.BERYLLIUM);
        //CALIFORNIUM = GASES.register("californium", 0xFFF08B00, new GasAttributes.Radiation(0.1));
        COMPRESSED_AIR = GASES.register(MSChemicalConstants.COMPRESSED_AIR);
        //CURIUM = GASES.register("curium",15725501,new GasAttributes.Radiation(0.07));
        DISSOLVED_SPENT_NUCLEAR_WASTE = GASES.register("dissolved_spent_nuclear_waste",0x8588b1);
        HELIUM = GASES.register(MSChemicalConstants.HELIUM, Coolants.HELIUM_COOLANT);
        HYDROGEN_CYANIDE = GASES.register(MSChemicalConstants.HYDROGEN_CYANIDE);
        SUPERHEATED_HELIUM = GASES.register(MSChemicalConstants.SUPERHEATED_HELIUM, Coolants.HEATED_HELIUM_COOLANT);
        IODINE = GASES.register(MSChemicalConstants.IODINE);
        METHANE = GASES.register(MSChemicalConstants.METHANE);
        //NETHERITE_ACID = GASES.register(MSChemicalConstants.NETHERITE_ACID);
        NITRIC_ACID = GASES.register(MSChemicalConstants.NITRIC_ACID);
        NITROGEN = GASES.register(MSChemicalConstants.NITROGEN);
        NITROGEN_DIOXIDE = GASES.register(MSChemicalConstants.NITROGEN_DIOXIDE);
        NITRIC_OXIDE = GASES.register(MSChemicalConstants.NITRIC_OXIDE);
        POTASSIUM_CHLORIDE = GASES.register(MSChemicalConstants.POTASSIUM_CHLORIDE);
        POTASSIUM_CYANIDE = GASES.register(MSChemicalConstants.POTASSIUM_CYANIDE);
        POTASSIUM_HYDROXIDE = GASES.register(MSChemicalConstants.POTASSIUM_HYDROXIDE);
        POTASSIUM_IODIDE = GASES.register(MSChemicalConstants.POTASSIUM_IODIDE);
        SEAWATER = GASES.register(MSChemicalConstants.SEAWATER);
        STRONTIUM = GASES.register(MSChemicalConstants.STRONTIUM);
        XENON = GASES.register(MSChemicalConstants.XENON);
        YTTRIUM = GASES.register(MSChemicalConstants.YTTRIUM);
    }

    private MSGases() {
    }

    public static class Coolants{
        public static void init(){
            setHeliumCoolantConductivity();
            setSuperheatedHeliumCoolantConductivity();
        }

        public static final GasAttributes.CooledCoolant HELIUM_COOLANT = new GasAttributes.CooledCoolant(() -> SUPERHEATED_HELIUM.get(), 100, 1.0);
        public static final GasAttributes.HeatedCoolant HEATED_HELIUM_COOLANT = new GasAttributes.HeatedCoolant(() -> HELIUM.get(), 100, 1.0);

        public static void setHeliumCoolantConductivity() {
            ((CoolantAccessor) HELIUM_COOLANT).setConductivity(1.5);
        }
        public static void setSuperheatedHeliumCoolantConductivity() {
            ((CoolantAccessor) HEATED_HELIUM_COOLANT).setConductivity(1.5);
        }
    }
}
