package com.fxd927.mekanismelements.common;

import com.fxd927.mekanismelements.common.config.MSConfig;
import com.fxd927.mekanismelements.common.recipe.MSRecipeType;
import com.fxd927.mekanismelements.common.registries.*;
import mekanism.common.lib.Version;
import mekanism.common.tile.component.TileComponentChunkLoader;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.world.ForgeChunkManager;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import static mekanism.api.MekanismAPI.logger;

@Mod(MekanismElements.MODID)
public class MekanismElements
{
    public static final String MODID = "mekanismelements";

    public MekanismElements(){
        this(FMLJavaModLoadingContext.get());
    }

    public final Version versionNumber;
    private MSReloadListener recipeCacheManager;

    public MekanismElements(FMLJavaModLoadingContext context)
    {
        MinecraftForge.EVENT_BUS.addListener(EventPriority.LOWEST, this::addReloadListenersLowest);
        IEventBus modEventBus = context.getModEventBus();
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
        MinecraftForge.EVENT_BUS.register(this);
    }

    public static ResourceLocation rl(String path){
        return new ResourceLocation(MekanismElements.MODID, path);
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
        event.addListener(getRecipeCacheManager());
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        logger.info("Version {} initializing...", versionNumber);
        setRecipeCacheManager(new MSReloadListener());

        event.enqueueWork(() -> {
            ForgeChunkManager.setForcedChunkLoadingCallback(MekanismElements.MODID, TileComponentChunkLoader.ChunkValidationCallback.INSTANCE);
            MSFluids.FLUIDS.registerBucketDispenserBehavior();
        });
    }
}
