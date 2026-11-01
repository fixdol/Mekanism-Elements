package fixdol.mekanismelements.common;

import fixdol.mekanismelements.common.config.MSConfig;
import fixdol.mekanismelements.common.recipe.IMSRecipeTypeProvider;
import fixdol.mekanismelements.common.recipe.MSRecipeType;
import fixdol.mekanismelements.common.registries.*;
import mekanism.common.lib.Version;
import net.minecraft.resources.Identifier;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.AddServerReloadListenersEvent;
import net.neoforged.neoforge.event.OnDatapackSyncEvent;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;

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
        NeoForge.EVENT_BUS.addListener(OnDatapackSyncEvent.class, event -> {
            //Recipes are not synced to the client by default, and the recipe viewers read ours on the client
            for (IMSRecipeTypeProvider<?, ?> recipeTypeProvider : MSRecipeType.RECIPE_TYPES.getAllRecipeTypes()) {
                event.sendRecipes(recipeTypeProvider.getMSRecipeType());
            }
        });
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


        versionNumber = new Version(ModLoadingContext.get().getActiveContainer());
        // Events are registered via addListener() calls above, no need to register this class
    }

    public static Identifier rl(String path){
        return Identifier.fromNamespaceAndPath(MekanismElements.MODID, path);
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

    private void addReloadListenersLowest(AddServerReloadListenersEvent event) {
        if (recipeCacheManager != null) {
            event.addListener(rl("recipe_cache_manager"), recipeCacheManager);
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
