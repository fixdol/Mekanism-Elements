package com.fxd927.mekanismelements.common.registries;

import com.fxd927.mekanismelements.common.MekanismElements;
import com.fxd927.mekanismelements.common.tile.machine.*;
import mekanism.common.inventory.container.tile.MekanismTileContainer;
import mekanism.common.registration.impl.ContainerTypeDeferredRegister;
import mekanism.common.registration.impl.ContainerTypeRegistryObject;

public class MSContainerTypes {
    public static final ContainerTypeDeferredRegister CONTAINER_TYPES = new ContainerTypeDeferredRegister(MekanismElements.MODID);


    public static final ContainerTypeRegistryObject<MekanismTileContainer<TileEntityAdsorptionSeparator>> ADSORPTION_SEPARATOR;
    public static final ContainerTypeRegistryObject<MekanismTileContainer<TileEntityAirCompressor>> AIR_COMPRESSOR;
    //public static final ContainerTypeRegistryObject<MekanismTileContainer<TileEntityChemicalDemolitionMachine>> CHEMICAL_DEMOLITION_MACHINE;
    public static final ContainerTypeRegistryObject<MekanismTileContainer<TileEntityRadiationIrradiator>> RADIATION_IRRADIATOR;
    public static final ContainerTypeRegistryObject<MekanismTileContainer<TileEntitySeawaterPump>> SEAWATER_PUMP;


    //public static final ContainerTypeRegistryObject<MekanismTileContainer<TileEntityAdsorptionTypeSeawaterMetalExtractor>> ADSORPTION_TYPE_SEAWATER_METAL_EXTRACTOR = CONTAINER_TYPES.register(MSBlocks.ADSORPTION_TYPE_SEAWATER_METAL_EXTRACTOR, TileEntityAdsorptionTypeSeawaterMetalExtractor.class);
    //public static final ContainerTypeRegistryObject<MekanismTileContainer<TileEntityOrganicLiquidExtractor>> ORGANIC_LIQUID_EXTRACTOR = CONTAINER_TYPES.register(MSBlocks.ORGANIC_LIQUID_EXTRACTOR, TileEntityOrganicLiquidExtractor.class);
    //public static final ContainerTypeRegistryObject<MekanismTileContainer<TileEntitySeawaterPump>> SEAWATER_PUMP = CONTAINER_TYPES.register(MSBlocks.SEAWATER_PUMP, TileEntitySeawaterPump.class);

    static {
        ADSORPTION_SEPARATOR = CONTAINER_TYPES.register(MSBlocks.ADSORPTION_SEPARATOR, TileEntityAdsorptionSeparator.class);
        AIR_COMPRESSOR = CONTAINER_TYPES.register(MSBlocks.AIR_COMPRESSOR, TileEntityAirCompressor.class);
        //CHEMICAL_DEMOLITION_MACHINE = CONTAINER_TYPES.register(MSBlocks.CHEMICAL_DEMOLITION_MACHINE, TileEntityChemicalDemolitionMachine.class);
        RADIATION_IRRADIATOR = CONTAINER_TYPES.register(MSBlocks.RADIATION_IRRADIATOR, TileEntityRadiationIrradiator.class);
        SEAWATER_PUMP = CONTAINER_TYPES.register(MSBlocks.SEAWATER_PUMP, TileEntitySeawaterPump.class);
    }

    private MSContainerTypes(){
    }
}
