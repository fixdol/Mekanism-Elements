package fixdol.mekanismelements.common;

import fixdol.mekanismelements.common.registries.MSBlocks;
import fixdol.mekanismelements.common.registries.MSContainerTypes;
import fixdol.mekanismelements.common.registries.MSEffects;
import fixdol.mekanismelements.common.registries.MSFluids;
import fixdol.mekanismelements.common.registries.MSGases;
import fixdol.mekanismelements.common.registries.MSItems;
import fixdol.mekanismelements.common.registries.MSRecipeSerializers;
import fixdol.mekanismelements.common.recipe.MSRecipeType;
import fixdol.mekanismelements.common.MekanismElements;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraft.resources.ResourceLocation;
import static fixdol.mekanismelements.common.MekanismElements.rl;

import fixdol.mekanismelements.common.config.MSConfig;
import fixdol.mekanismelements.common.registries.*;
import mekanism.api.MekanismAPI;
import mekanism.common.lib.Version;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

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
        MinecraftForge.EVENT_BUS.addListener(EventPriority.LOWEST, this::addReloadListenersLowest);
        IEventBus modEventBus = net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext.get().getModEventBus();
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


        versionNumber = new Version(ModLoadingContext.get().getActiveContainer());
        // Events are registered via addListener() calls above, no need to register this class
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
        if (recipeCacheManager != null) {
            event.addListener(recipeCacheManager);
        }
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        logger.info("Version {} initializing...", versionNumber);
        setRecipeCacheManager(new MSReloadListener());

        event.enqueueWork(() -> {
            // Chunk loading callback registration 
            MSFluids.FLUIDS.registerBucketDispenserBehavior();
        });
    }

}
