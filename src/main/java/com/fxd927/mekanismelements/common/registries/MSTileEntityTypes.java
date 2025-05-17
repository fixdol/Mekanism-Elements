package com.fxd927.mekanismelements.common.registries;

import com.fxd927.mekanismelements.common.MekanismElements;
import com.fxd927.mekanismelements.common.tile.machine.*;
import mekanism.common.registration.impl.TileEntityTypeDeferredRegister;
import mekanism.common.registration.impl.TileEntityTypeRegistryObject;
import mekanism.common.tile.base.TileEntityMekanism;

public class MSTileEntityTypes {
    public static final TileEntityTypeDeferredRegister TILE_ENTITY_TYPES = new TileEntityTypeDeferredRegister(MekanismElements.MODID);

    public static final TileEntityTypeRegistryObject<TileEntityAdsorptionSeparator> ADSORPTION_SEPARATOR;
    public static final TileEntityTypeRegistryObject<TileEntityAirCompressor> AIR_COMPRESSOR;
    //public static final TileEntityTypeRegistryObject<TileEntityChemicalDemolitionMachine> CHEMICAL_DEMOLITION_MACHINE;
    public static final TileEntityTypeRegistryObject<TileEntityRadiationIrradiator> RADIATION_IRRADIATOR;
    public static final TileEntityTypeRegistryObject<TileEntitySeawaterPump> SEAWATER_PUMP;


    //public static final TileEntityTypeRegistryObject<TileEntityAdsorptionTypeSeawaterMetalExtractor> ADSORPTION_TYPE_SEAWATER_METAL_EXTRACTOR = TILE_ENTITY_TYPES.register(null, TileEntityAdsorptionTypeSeawaterMetalExtractor::new);
    //public static final TileEntityTypeRegistryObject<TileEntityOrganicLiquidExtractor> ORGANIC_LIQUID_EXTRACTOR = TILE_ENTITY_TYPES.register(null, TileEntityOrganicLiquidExtractor::new);
    //public static final TileEntityTypeRegistryObject<TileEntitySeawaterPump> SEAWATER_PUMP = TILE_ENTITY_TYPES.register(null, TileEntitySeawaterPump::new);

    static {
        ADSORPTION_SEPARATOR = TILE_ENTITY_TYPES.register(MSBlocks.ADSORPTION_SEPARATOR, TileEntityAdsorptionSeparator::new, TileEntityMekanism::tickServer, TileEntityMekanism::tickClient);
        AIR_COMPRESSOR = TILE_ENTITY_TYPES.register(MSBlocks.AIR_COMPRESSOR, TileEntityAirCompressor::new, TileEntityMekanism::tickServer, TileEntityMekanism::tickClient);
        //CHEMICAL_DEMOLITION_MACHINE = TILE_ENTITY_TYPES.register(MSBlocks.CHEMICAL_DEMOLITION_MACHINE, TileEntityChemicalDemolitionMachine::new, TileEntityMekanism::tickServer, TileEntityMekanism::tickClient);
        RADIATION_IRRADIATOR = TILE_ENTITY_TYPES.register(MSBlocks.RADIATION_IRRADIATOR, TileEntityRadiationIrradiator::new, TileEntityMekanism::tickServer, TileEntityMekanism::tickClient);
        SEAWATER_PUMP = TILE_ENTITY_TYPES.register(MSBlocks.SEAWATER_PUMP, TileEntitySeawaterPump::new, TileEntityMekanism::tickServer, TileEntityMekanism::tickClient);
    }

    private MSTileEntityTypes(){
    }
}
