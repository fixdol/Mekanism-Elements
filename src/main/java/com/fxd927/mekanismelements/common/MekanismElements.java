package com.fxd927.mekanismelements.common;

import com.fxd927.mekanismelements.common.config.MSConfig;
import com.fxd927.mekanismelements.common.recipe.MSRecipeType;
import com.fxd927.mekanismelements.common.registries.*;
import mekanism.api.MekanismAPI;
import mekanism.api.chemical.Chemical;
import mekanism.api.chemical.ChemicalStack;
import mekanism.api.datamaps.IMekanismDataMapTypes;
import mekanism.api.datamaps.chemical.attribute.ChemicalFuel;
import mekanism.common.lib.Version;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.AddReloadListenerEvent;
import net.neoforged.neoforge.registries.datamaps.DataMapsUpdatedEvent;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;

import static mekanism.api.MekanismAPI.logger;

@Mod(MekanismElements.MODID)
public class MekanismElements
{
    public static final String MODID = "mekanismelements";
    public static final org.slf4j.Logger logger = com.mojang.logging.LogUtils.getLogger();

    public final Version versionNumber;
    private MSReloadListener recipeCacheManager;

    public MekanismElements()
    {
        NeoForge.EVENT_BUS.addListener(EventPriority.LOWEST, this::addReloadListenersLowest);
        NeoForge.EVENT_BUS.addListener(this::onDataMapsUpdated);
        IEventBus modEventBus = ModLoadingContext.get().getActiveContainer().getEventBus();
        MSConfig.registerConfigs(ModLoadingContext.get());
        modEventBus.addListener(this::commonSetup);
        MSCreativeTab.CREATIVE_TABS.register(modEventBus);
        MSFluids.FLUIDS.register(modEventBus);
        MSGases.GASES.register(modEventBus);
        MSItems.ITEMS.register(modEventBus);
        MSItems.BUILDING_ITEMS.register(modEventBus);
        MSBlocks.BLOCKS.register(modEventBus);
        MSBlocks.BUILDING_BLOCKS.register(modEventBus);
        MSContainerTypes.CONTAINER_TYPES.register(modEventBus);
        MSTileEntityTypes.TILE_ENTITY_TYPES.register(modEventBus);
        MSEffects.MOB_EFFECTS.register(modEventBus);
        MSRecipeType.RECIPE_TYPES.register(modEventBus);
        MSRecipeSerializers.RECIPE_SERIALIZERS.register(modEventBus);
        MSSounds.SOUND_EVENTS.register(modEventBus);

        MSGases.Coolants.init();

        versionNumber = new Version(ModLoadingContext.get().getActiveContainer());
        // Events are registered via addListener() calls above, no need to register this class
    }

    public static ResourceLocation rl(String path){
        return ResourceLocation.fromNamespaceAndPath(MekanismElements.MODID, path);
    }

    private void setRecipeCacheManager(MSReloadListener manager) {
        if (recipeCacheManager == null) {
            recipeCacheManager = manager;
        } else {
            logger.warn("Recipe cache manager has already been set.");
        }
    }

    public MSReloadListener getRecipeCacheManager() {
        return recipeCacheManager;
    }

    private void addReloadListenersLowest(AddReloadListenerEvent event) {
        if (recipeCacheManager != null) {
            event.addListener(recipeCacheManager);
        }
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        logger.info("Version {} initializing...", versionNumber);
        setRecipeCacheManager(new MSReloadListener());

        event.enqueueWork(() -> {
            // Chunk loading callback registration - may need to be handled differently in NeoForge
            MSFluids.FLUIDS.registerBucketDispenserBehavior();
        });
    }

    private void onDataMapsUpdated(DataMapsUpdatedEvent event) {
        logger.info("=== DATA MAPS UPDATED EVENT START ===");
        // Ensure datamaps are processed for our chemicals
        event.ifRegistry(MekanismAPI.CHEMICAL_REGISTRY_NAME, registry -> {
            logger.info("Processing chemical registry, total holders: {}", registry.holders().count());
            
            // Test hydrogen and ethene for comparison
            var hydrogenHolder = registry.getHolder(net.minecraft.resources.ResourceLocation.fromNamespaceAndPath("mekanism", "hydrogen"));
            if (hydrogenHolder.isPresent()) {
                var h = hydrogenHolder.get();
                var hFuel = h.getData(IMekanismDataMapTypes.INSTANCE.chemicalFuel());
                logger.info("HYDROGEN TEST: Holder found={}, Fuel from holder={}, Fuel from ChemicalStack={}", 
                    true, hFuel != null, 
                    new ChemicalStack(h.value(), 1000).getData(IMekanismDataMapTypes.INSTANCE.chemicalFuel()) != null);
                if (hFuel != null) {
                    logger.info("HYDROGEN FUEL VALUES: burn_time={}, energy={}, energyPerTick={}", 
                        hFuel.burnTicks(), hFuel.energyDensity(), hFuel.energyPerTick());
                }
            } else {
                logger.warn("HYDROGEN TEST: Holder not found!");
            }
            
            registry.holders().forEach(holder -> {
                if (holder.value().getRegistryName() != null && 
                    holder.value().getRegistryName().getNamespace().equals(MODID)) {
                    String chemicalName = holder.value().getRegistryName().getPath();
                    logger.info("Processing chemical: {}", chemicalName);
                    holder.value().updateFromDataMap(holder);
                    
                    // Programmatically add fuel datamap for ammonia if not found
                    if (chemicalName.equals("ammonia")) {
                        logger.info("=== AMMONIA FUEL DATAMAP PROCESSING START ===");
                        ChemicalFuel fuel = holder.getData(IMekanismDataMapTypes.INSTANCE.chemicalFuel());
                        logger.info("AMMONIA: Initial fuel from holder.getData(): {}", fuel != null ? 
                            String.format("burn_time=%d, energy=%d", fuel.burnTicks(), fuel.energyPerTick()) : "null");
                        
                        if (fuel == null) {
                            logger.info("AMMONIA: Fuel datamap not found, adding programmatically...");
                            // Datamap file didn't load, add it programmatically
                            // Note: ChemicalFuel constructor takes (burnTicks, energyPerTick)
                            // If total energy is 600000 and burn_time is 100, then energyPerTick = 600000 / 100 = 6000
                            try {
                                ChemicalFuel ammoniaFuel = new ChemicalFuel(100, 6000L);
                                logger.info("AMMONIA: Creating fuel object: burn_time={}, energy={}, energyPerTick={}, energyDensity={}", 
                                    ammoniaFuel.burnTicks(), ammoniaFuel.energyPerTick(), ammoniaFuel.energyPerTick(), ammoniaFuel.energyDensity());
                                
                                registry.getDataMap(IMekanismDataMapTypes.INSTANCE.chemicalFuel())
                                    .put(holder.key(), ammoniaFuel);
                                logger.info("AMMONIA: Fuel datamap added to registry");
                                
                                // Force update the chemical from the datamap
                                holder.value().updateFromDataMap(holder);
                                logger.info("AMMONIA: Chemical updated from datamap");
                                
                                // Verify it was added
                                ChemicalFuel verifyFuel = holder.getData(IMekanismDataMapTypes.INSTANCE.chemicalFuel());
                                logger.info("AMMONIA: Verification - fuel from holder.getData(): {}", verifyFuel != null ? 
                                    String.format("burn_time=%d, energy=%d", verifyFuel.burnTicks(), verifyFuel.energyPerTick()) : "null");
                                
                                if (verifyFuel != null) {
                                    logger.info("AMMONIA: Fuel datamap added programmatically: burn_time={}, energy={}, total={} FE/mB", 
                                        verifyFuel.burnTicks(), verifyFuel.energyPerTick(), verifyFuel.energyDensity());
                                    
                                    // Test with multiple ChemicalStack instances
                                    for (long amount : new long[]{1, 100, 1000, 10000}) {
                                        ChemicalStack testStack = new ChemicalStack(holder.value(), amount);
                                        ChemicalFuel stackFuel = testStack.getData(IMekanismDataMapTypes.INSTANCE.chemicalFuel());
                                        boolean hasFuel = mekanism.generators.common.tile.TileEntityGasGenerator.HAS_FUEL.test(testStack);
                                        logger.info("AMMONIA: ChemicalStack test (amount={}): getData()={}, HAS_FUEL={}, fuel values={}", 
                                            amount, stackFuel != null, hasFuel,
                                            stackFuel != null ? String.format("burn_time=%d, energy=%d", stackFuel.burnTicks(), stackFuel.energyPerTick()) : "null");
                                        
                                        if (stackFuel != null) {
                                            logger.info("AMMONIA: ChemicalStack fuel details: burnTicks()={}, energyPerTick()={}, energyDensity()={}", 
                                                stackFuel.burnTicks(), stackFuel.energyPerTick(), stackFuel.energyDensity());
                                        }
                                    }
                                    
                                    // Compare with hydrogen and ethene
                                    var hHolder = registry.getHolder(net.minecraft.resources.ResourceLocation.fromNamespaceAndPath("mekanism", "hydrogen"));
                                    if (hHolder.isPresent()) {
                                        ChemicalStack hStack = new ChemicalStack(hHolder.get().value(), 1000);
                                        ChemicalFuel hFuel = hStack.getData(IMekanismDataMapTypes.INSTANCE.chemicalFuel());
                                        boolean hHasFuel = mekanism.generators.common.tile.TileEntityGasGenerator.HAS_FUEL.test(hStack);
                                        logger.info("HYDROGEN COMPARISON: ChemicalStack (amount=1000): getData()={}, HAS_FUEL={}, fuel values={}", 
                                            hFuel != null, hHasFuel,
                                            hFuel != null ? String.format("burn_time=%d, energy=%d", hFuel.burnTicks(), hFuel.energyPerTick()) : "null");
                                    }
                                    
                                    var eHolder = registry.getHolder(net.minecraft.resources.ResourceLocation.fromNamespaceAndPath("mekanism", "ethene"));
                                    if (eHolder.isPresent()) {
                                        ChemicalStack eStack = new ChemicalStack(eHolder.get().value(), 1000);
                                        ChemicalFuel eFuel = eStack.getData(IMekanismDataMapTypes.INSTANCE.chemicalFuel());
                                        boolean eHasFuel = mekanism.generators.common.tile.TileEntityGasGenerator.HAS_FUEL.test(eStack);
                                        logger.info("ETHENE COMPARISON: ChemicalStack (amount=1000): getData()={}, HAS_FUEL={}, fuel values={}", 
                                            eFuel != null, eHasFuel,
                                            eFuel != null ? String.format("burn_time=%d, energy=%d", eFuel.burnTicks(), eFuel.energyPerTick()) : "null");
                                    }
                                    
                                    // Simulate what the generator does - test getFuel() on a mock tank
                                    logger.info("=== SIMULATING GENERATOR FUEL CHECK ===");
                                    ChemicalStack ammoniaStack = new ChemicalStack(holder.value(), 1000);
                                    ChemicalFuel ammoniaFuelFromStack = ammoniaStack.getData(IMekanismDataMapTypes.INSTANCE.chemicalFuel());
                                    logger.info("AMMONIA SIMULATION: Stack amount={}, getData()={}, fuel={}", 
                                        ammoniaStack.getAmount(), ammoniaFuelFromStack != null,
                                        ammoniaFuelFromStack != null ? String.format("burnTicks=%d, energyPerTick=%d", 
                                            ammoniaFuelFromStack.burnTicks(), ammoniaFuelFromStack.energyPerTick()) : "null");
                                    
                                    if (hHolder.isPresent()) {
                                        ChemicalStack hStack = new ChemicalStack(hHolder.get().value(), 1000);
                                        ChemicalFuel hFuelFromStack = hStack.getData(IMekanismDataMapTypes.INSTANCE.chemicalFuel());
                                        logger.info("HYDROGEN SIMULATION: Stack amount={}, getData()={}, fuel={}", 
                                            hStack.getAmount(), hFuelFromStack != null,
                                            hFuelFromStack != null ? String.format("burnTicks=%d, energyPerTick=%d", 
                                                hFuelFromStack.burnTicks(), hFuelFromStack.energyPerTick()) : "null");
                                    }
                                    
                                    if (!mekanism.generators.common.tile.TileEntityGasGenerator.HAS_FUEL.test(new ChemicalStack(holder.value(), 1000))) {
                                        logger.error("AMMONIA CRITICAL: HAS_FUEL predicate returns false even though fuel datamap exists!");
                                    }
                                } else {
                                    logger.error("AMMONIA: Fuel datamap was added but verification failed!");
                                }
                            } catch (Exception e) {
                                logger.error("AMMONIA: Failed to add fuel datamap programmatically", e);
                            }
                        } else {
                            logger.info("AMMONIA: Fuel datamap loaded from file: burn_time={}, energy={}, total={} FE/mB", 
                                fuel.burnTicks(), fuel.energyPerTick(), fuel.energyDensity());
                        }
                        logger.info("=== AMMONIA FUEL DATAMAP PROCESSING END ===");
                    }
                }
            });
        });
        logger.info("=== DATA MAPS UPDATED EVENT END ===");
    }
}

