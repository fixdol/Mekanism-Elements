package com.fxd927.mekanismelements.common.registries;

import com.fxd927.mekanismelements.common.MekanismElements;
import com.fxd927.mekanismelements.common.gas.MSChemicalConstants;
import mekanism.common.registration.impl.FluidDeferredRegister;
import mekanism.common.registration.impl.FluidRegistryObject;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraftforge.fluids.ForgeFlowingFluid;

public class MSFluids {
    public static final FluidDeferredRegister FLUIDS = new FluidDeferredRegister(MekanismElements.MODID);

    public static final FluidRegistryObject<FluidDeferredRegister.MekanismFluidType, ForgeFlowingFluid.Source, ForgeFlowingFluid.Flowing, LiquidBlock, BucketItem> AMMONIA = FLUIDS.registerLiquidChemical(MSChemicalConstants.AMMONIA);
    public static final FluidRegistryObject<FluidDeferredRegister.MekanismFluidType, ForgeFlowingFluid.Source, ForgeFlowingFluid.Flowing, LiquidBlock, BucketItem> AMMONIUM_NITRATE = FLUIDS.registerLiquidChemical(MSChemicalConstants.AMMONIUM_NITRATE);
    //public static final FluidRegistryObject<FluidDeferredRegister.MekanismFluidType, ForgeFlowingFluid.Source, ForgeFlowingFluid.Flowing, LiquidBlock, BucketItem> AQUA_REGIA = FLUIDS.registerLiquidChemical(MSChemicalConstants.AQUA_REGIA);
    public static final FluidRegistryObject<FluidDeferredRegister.MekanismFluidType, ForgeFlowingFluid.Source, ForgeFlowingFluid.Flowing, LiquidBlock, BucketItem> BERYLLIUM = FLUIDS.registerLiquidChemical(MSChemicalConstants.BERYLLIUM);
    public static final FluidRegistryObject<FluidDeferredRegister.MekanismFluidType, ForgeFlowingFluid.Source, ForgeFlowingFluid.Flowing, LiquidBlock, BucketItem> BROMINE = FLUIDS.registerLiquidChemical(MSChemicalConstants.BROMINE);
    public static final FluidRegistryObject<FluidDeferredRegister.MekanismFluidType, ForgeFlowingFluid.Source, ForgeFlowingFluid.Flowing, LiquidBlock, BucketItem> COMPRESSED_AIR = FLUIDS.registerLiquidChemical(MSChemicalConstants.COMPRESSED_AIR);
    public static final FluidRegistryObject<FluidDeferredRegister.MekanismFluidType, ForgeFlowingFluid.Source, ForgeFlowingFluid.Flowing, LiquidBlock, BucketItem> HELIUM = FLUIDS.registerLiquidChemical(MSChemicalConstants.HELIUM);
    public static final FluidRegistryObject<FluidDeferredRegister.MekanismFluidType, ForgeFlowingFluid.Source, ForgeFlowingFluid.Flowing, LiquidBlock, BucketItem> HYDROGEN_CYANIDE = FLUIDS.registerLiquidChemical(MSChemicalConstants.HYDROGEN_CYANIDE);
    public static final FluidRegistryObject<FluidDeferredRegister.MekanismFluidType, ForgeFlowingFluid.Source, ForgeFlowingFluid.Flowing, LiquidBlock, BucketItem> SUPERHEATED_HELIUM = FLUIDS.registerLiquidChemical(MSChemicalConstants.SUPERHEATED_HELIUM);
    public static final FluidRegistryObject<FluidDeferredRegister.MekanismFluidType, ForgeFlowingFluid.Source, ForgeFlowingFluid.Flowing, LiquidBlock, BucketItem> IODINE = FLUIDS.registerLiquidChemical(MSChemicalConstants.IODINE);
    public static final FluidRegistryObject<FluidDeferredRegister.MekanismFluidType, ForgeFlowingFluid.Source, ForgeFlowingFluid.Flowing, LiquidBlock, BucketItem> METHANE = FLUIDS.registerLiquidChemical(MSChemicalConstants.METHANE);
    //public static final FluidRegistryObject<FluidDeferredRegister.MekanismFluidType, ForgeFlowingFluid.Source, ForgeFlowingFluid.Flowing, LiquidBlock, BucketItem> NETHERITE_ACID = FLUIDS.registerLiquidChemical(MSChemicalConstants.NETHERITE_ACID);
    public static final FluidRegistryObject<FluidDeferredRegister.MekanismFluidType, ForgeFlowingFluid.Source, ForgeFlowingFluid.Flowing, LiquidBlock, BucketItem> NITRIC_ACID = FLUIDS.registerLiquidChemical(MSChemicalConstants.NITRIC_ACID);
    public static final FluidRegistryObject<FluidDeferredRegister.MekanismFluidType, ForgeFlowingFluid.Source, ForgeFlowingFluid.Flowing, LiquidBlock, BucketItem> NITROGEN = FLUIDS.registerLiquidChemical(MSChemicalConstants.NITROGEN);
    public static final FluidRegistryObject<FluidDeferredRegister.MekanismFluidType, ForgeFlowingFluid.Source, ForgeFlowingFluid.Flowing, LiquidBlock, BucketItem> NITROGEN_DIOXIDE = FLUIDS.registerLiquidChemical(MSChemicalConstants.NITROGEN_DIOXIDE);
    public static final FluidRegistryObject<FluidDeferredRegister.MekanismFluidType, ForgeFlowingFluid.Source, ForgeFlowingFluid.Flowing, LiquidBlock, BucketItem> NITRIC_OXIDE = FLUIDS.registerLiquidChemical(MSChemicalConstants.NITRIC_OXIDE);
    public static final FluidRegistryObject<FluidDeferredRegister.MekanismFluidType, ForgeFlowingFluid.Source, ForgeFlowingFluid.Flowing, LiquidBlock, BucketItem> POTASSIUM_CHLORIDE = FLUIDS.registerLiquidChemical(MSChemicalConstants.POTASSIUM_CHLORIDE);
    public static final FluidRegistryObject<FluidDeferredRegister.MekanismFluidType, ForgeFlowingFluid.Source, ForgeFlowingFluid.Flowing, LiquidBlock, BucketItem> POTASSIUM_CYANIDE = FLUIDS.registerLiquidChemical(MSChemicalConstants.POTASSIUM_CYANIDE);
    public static final FluidRegistryObject<FluidDeferredRegister.MekanismFluidType, ForgeFlowingFluid.Source, ForgeFlowingFluid.Flowing, LiquidBlock, BucketItem> POTASSIUM_HYDROXIDE = FLUIDS.registerLiquidChemical(MSChemicalConstants.POTASSIUM_HYDROXIDE);
    public static final FluidRegistryObject<FluidDeferredRegister.MekanismFluidType, ForgeFlowingFluid.Source, ForgeFlowingFluid.Flowing, LiquidBlock, BucketItem> POTASSIUM_IODIDE = FLUIDS.registerLiquidChemical(MSChemicalConstants.POTASSIUM_IODIDE);
    public static final FluidRegistryObject<FluidDeferredRegister.MekanismFluidType, ForgeFlowingFluid.Source, ForgeFlowingFluid.Flowing, LiquidBlock, BucketItem> SEAWATER = FLUIDS.registerLiquidChemical(MSChemicalConstants.SEAWATER);
    public static final FluidRegistryObject<FluidDeferredRegister.MekanismFluidType, ForgeFlowingFluid.Source, ForgeFlowingFluid.Flowing, LiquidBlock, BucketItem> STRONTIUM = FLUIDS.registerLiquidChemical(MSChemicalConstants.STRONTIUM);
    public static final FluidRegistryObject<FluidDeferredRegister.MekanismFluidType, ForgeFlowingFluid.Source, ForgeFlowingFluid.Flowing, LiquidBlock, BucketItem> XENON = FLUIDS.registerLiquidChemical(MSChemicalConstants.XENON);
    public static final FluidRegistryObject<FluidDeferredRegister.MekanismFluidType, ForgeFlowingFluid.Source, ForgeFlowingFluid.Flowing, LiquidBlock, BucketItem> YTTRIUM = FLUIDS.registerLiquidChemical(MSChemicalConstants.YTTRIUM);

    private MSFluids(){
    }
}
