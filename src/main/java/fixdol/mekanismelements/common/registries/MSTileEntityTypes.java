package fixdol.mekanismelements.common.registries;

import mekanism.common.capabilities.Capabilities;
import fixdol.mekanismelements.common.registries.MSBlocks;
import fixdol.mekanismelements.common.MekanismElements;
import fixdol.mekanismelements.common.tile.machine.TileEntityAdsorptionSeparator;
import fixdol.mekanismelements.common.tile.machine.TileEntityAirCompressor;
import fixdol.mekanismelements.common.tile.machine.TileEntityChemicalDemolitionMachine;
import fixdol.mekanismelements.common.tile.machine.TileEntityInfinityOreReprocessing;
import mekanism.common.tile.base.TileEntityMekanism;
import fixdol.mekanismelements.common.tile.machine.TileEntityRadiationIrradiator;
import fixdol.mekanismelements.common.tile.machine.TileEntitySeawaterPump;
import mekanism.common.registration.impl.TileEntityTypeRegistryObject;

import mekanism.common.registration.impl.TileEntityTypeDeferredRegister;

public class MSTileEntityTypes {
    public static final TileEntityTypeDeferredRegister TILE_ENTITY_TYPES = new TileEntityTypeDeferredRegister(MekanismElements.MODID);

    public static final TileEntityTypeRegistryObject<TileEntityAdsorptionSeparator> ADSORPTION_SEPARATOR;
    public static final TileEntityTypeRegistryObject<TileEntityAirCompressor> AIR_COMPRESSOR;
    public static final TileEntityTypeRegistryObject<TileEntityChemicalDemolitionMachine> CHEMICAL_DEMOLITION_MACHINE;
    public static final TileEntityTypeRegistryObject<TileEntityRadiationIrradiator> RADIATION_IRRADIATOR;
    public static final TileEntityTypeRegistryObject<TileEntitySeawaterPump> SEAWATER_PUMP;
    public static final TileEntityTypeRegistryObject<TileEntityInfinityOreReprocessing> INFINITY_ORE_REPROCESSING;


    //public static final TileEntityTypeRegistryObject<TileEntityAdsorptionTypeSeawaterMetalExtractor> ADSORPTION_TYPE_SEAWATER_METAL_EXTRACTOR = TILE_ENTITY_TYPES.register(null, TileEntityAdsorptionTypeSeawaterMetalExtractor::new);
    //public static final TileEntityTypeRegistryObject<TileEntityOrganicLiquidExtractor> ORGANIC_LIQUID_EXTRACTOR = TILE_ENTITY_TYPES.register(null, TileEntityOrganicLiquidExtractor::new);
    //public static final TileEntityTypeRegistryObject<TileEntitySeawaterPump> SEAWATER_PUMP = TILE_ENTITY_TYPES.register(null, TileEntitySeawaterPump::new);

    static {
        ADSORPTION_SEPARATOR = TILE_ENTITY_TYPES.builder(MSBlocks.ADSORPTION_SEPARATOR, TileEntityAdsorptionSeparator::new)
            .clientTicker(TileEntityMekanism::tickClient)
            .serverTicker(TileEntityMekanism::tickServer)
            .build();
        AIR_COMPRESSOR = TILE_ENTITY_TYPES.builder(MSBlocks.AIR_COMPRESSOR, TileEntityAirCompressor::new)
            .clientTicker(TileEntityMekanism::tickClient)
            .serverTicker(TileEntityMekanism::tickServer)
            .build();
        CHEMICAL_DEMOLITION_MACHINE = TILE_ENTITY_TYPES.builder(MSBlocks.CHEMICAL_DEMOLITION_MACHINE, TileEntityChemicalDemolitionMachine::new)
            .clientTicker(TileEntityMekanism::tickClient)
            .serverTicker(TileEntityMekanism::tickServer)
            .build();
        RADIATION_IRRADIATOR = TILE_ENTITY_TYPES.builder(MSBlocks.RADIATION_IRRADIATOR, TileEntityRadiationIrradiator::new)
            .clientTicker(TileEntityMekanism::tickClient)
            .serverTicker(TileEntityMekanism::tickServer)
            .build();
        SEAWATER_PUMP = TILE_ENTITY_TYPES.builder(MSBlocks.SEAWATER_PUMP, TileEntitySeawaterPump::new)
            .clientTicker(TileEntityMekanism::tickClient)
            .serverTicker(TileEntityMekanism::tickServer)
            .build();
        INFINITY_ORE_REPROCESSING = TILE_ENTITY_TYPES.builder(MSBlocks.INFINITY_ORE_REPROCESSING, TileEntityInfinityOreReprocessing::new)
            .clientTicker(TileEntityMekanism::tickClient)
            .serverTicker(TileEntityMekanism::tickServer)
            .build();
    }

    private MSTileEntityTypes(){
    }
}