package fixdol.mekanismelements.common.recipe;

import fixdol.mekanismelements.api.recipes.AdsorptionRecipe;
import fixdol.mekanismelements.api.recipes.ChemicalDemolitionRecipe;
import fixdol.mekanismelements.api.recipes.InfinityOreReprocessingRecipe;
import fixdol.mekanismelements.api.recipes.RadiationIrradiatingRecipe;
import fixdol.mekanismelements.common.MekanismElements;
import fixdol.mekanismelements.common.recipe.lookup.cache.MSInputRecipeCache;
import fixdol.mekanismelements.common.registration.impl.MSRecipeTypeDeferredRegister;
import fixdol.mekanismelements.common.registration.impl.MSRecipeTypeRegistryObject;
import mekanism.api.recipes.FluidToFluidRecipe;
import mekanism.api.recipes.MekanismRecipe;
import mekanism.client.MekanismClient;
import mekanism.common.recipe.lookup.cache.IInputRecipeCache;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.RecipeMap;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.server.ServerLifecycleHooks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class MSRecipeType<RECIPE extends MekanismRecipe<?>, INPUT_CACHE extends IInputRecipeCache> implements RecipeType<RECIPE>,
        IMSRecipeTypeProvider<RECIPE, INPUT_CACHE> {
    public static final MSRecipeTypeDeferredRegister RECIPE_TYPES = new MSRecipeTypeDeferredRegister(MekanismElements.MODID);

    public static final MSRecipeTypeRegistryObject<RadiationIrradiatingRecipe, MSInputRecipeCache.ItemChemical<RadiationIrradiatingRecipe>> RADIATION_IRRADIATING =
            register("radiation_irradiating", recipeType -> new MSInputRecipeCache.ItemChemical<>(recipeType, RadiationIrradiatingRecipe::getItemInput, RadiationIrradiatingRecipe::getGasInput));
    public static final MSRecipeTypeRegistryObject<AdsorptionRecipe, MSInputRecipeCache.ItemFluid<AdsorptionRecipe>> ADSORPTION =
            register("adsorption", recipeType -> new MSInputRecipeCache.ItemFluid<>(recipeType, AdsorptionRecipe::getItemInput, AdsorptionRecipe::getFluidInput));
    public static final MSRecipeTypeRegistryObject<FluidToFluidRecipe, MSInputRecipeCache.SingleFluid<FluidToFluidRecipe>> ADVANCED_EVAPORATING =
            register("evaporating", recipeType -> new MSInputRecipeCache.SingleFluid<>(recipeType, FluidToFluidRecipe::getInput));
    public static final MSRecipeTypeRegistryObject<ChemicalDemolitionRecipe, MSInputRecipeCache.ItemChemical<ChemicalDemolitionRecipe>> CHEMICAL_DEMOLITION =
            register("chemical_demolition", recipeType -> new MSInputRecipeCache.ItemChemical<>(recipeType, ChemicalDemolitionRecipe::getItemInput, ChemicalDemolitionRecipe::getGasInput));
    public static final MSRecipeTypeRegistryObject<InfinityOreReprocessingRecipe, MSInputRecipeCache.ItemChemical<InfinityOreReprocessingRecipe>> INFINITY_ORE_REPROCESSING =
        register("infinity_ore_reprocessing", recipeType -> new MSInputRecipeCache.ItemChemical<>(recipeType, InfinityOreReprocessingRecipe::getItemInput, InfinityOreReprocessingRecipe::getChemicalInput));

   public static <RECIPE extends MekanismRecipe<?>, INPUT_CACHE extends IInputRecipeCache> MSRecipeTypeRegistryObject<RECIPE, INPUT_CACHE> register(String name,
                                                                                                                                                   Function<MSRecipeType<RECIPE, INPUT_CACHE>, INPUT_CACHE> inputCacheCreator) {
        return RECIPE_TYPES.registerRecipeType(name, () -> new MSRecipeType<>(name, inputCacheCreator));
    }

    public static void clearCache() {
        for (IMSRecipeTypeProvider<?, ?> recipeTypeProvider : RECIPE_TYPES.getAllRecipeTypes()) {
            recipeTypeProvider.getMSRecipeType().clearCaches();
        }
    }

    private List<RECIPE> cachedRecipes = Collections.emptyList();
    private final Identifier registryName;
    private final INPUT_CACHE inputCache;

    private MSRecipeType(String name, Function<MSRecipeType<RECIPE, INPUT_CACHE>, INPUT_CACHE> inputCacheCreator) {
        this.registryName = MekanismElements.rl(name);
        this.inputCache = inputCacheCreator.apply(this);
    }

    @Override
    public String toString() {
        return registryName.toString();
    }

    @Override
    public Identifier getRegistryName() {
        return registryName;
    }

    @Override
    public MSRecipeType<RECIPE, INPUT_CACHE> getMSRecipeType() {
        return this;
    }

    private void clearCaches() {
        cachedRecipes = Collections.emptyList();
        inputCache.clear();
    }

    @Override
    public INPUT_CACHE getInputCache() {
        return inputCache;
    }

    @NotNull
    @Override
    public List<RECIPE> getRecipes(@Nullable Level world) {
        RecipeMap recipeMap = getRecipeMap(world);
        if (recipeMap == RecipeMap.EMPTY) {
            return Collections.emptyList();
        }
        return getRecipes(recipeMap);
    }

    @NotNull
    public List<RECIPE> getRecipes(RecipeMap recipeMap) {
        if (cachedRecipes.isEmpty()) {
            @SuppressWarnings({"unchecked", "rawtypes"})
            Collection<RecipeHolder<RECIPE>> recipeHolders = (Collection) recipeMap.byType((RecipeType) this);

            cachedRecipes = recipeHolders.stream()
                    .filter(holder -> !holder.value().isIncomplete())
                    .map(holder -> {
                        RECIPE recipe = holder.value();
                        Identifier id = holder.id().identifier();
                        if (recipe instanceof AdsorptionRecipe r) {
                            r.setId(id);
                        } else if (recipe instanceof RadiationIrradiatingRecipe r) {
                            r.setId(id);
                        } else if (recipe instanceof ChemicalDemolitionRecipe r) {
                            r.setId(id);
                        } else if (recipe instanceof InfinityOreReprocessingRecipe r) {
                            r.setId(id);
                        }
                        return recipe;
                    })
                    .toList();
        }
        return cachedRecipes;
    }

    private static RecipeMap getRecipeMap(@Nullable Level level) {
        if (level instanceof ServerLevel serverLevel) {
            return serverLevel.recipeAccess().recipeMap();
        } else if (level != null) {
            if (level.isClientSide()) {
                return MekanismClient.clientRecipes();
            }
        } else if (FMLEnvironment.getDist().isClient()) {
            return MekanismClient.clientRecipes();
        } else {
            MinecraftServer currentServer = ServerLifecycleHooks.getCurrentServer();
            if (currentServer != null) {
                return currentServer.getRecipeManager().recipeMap();
            }
        }
        return RecipeMap.EMPTY;
    }

    public static <I extends RecipeInput, RECIPE_TYPE extends Recipe<I>> Optional<RecipeHolder<RECIPE_TYPE>> getRecipeFor(RecipeType<RECIPE_TYPE> recipeType, I input,
                                                                                                                         Level level) {
        return getRecipeMap(level).getRecipesFor(recipeType, input, level).findFirst();
    }

    /**
     * Helper for getting a recipe from a level's recipe manager.
     */
    @Nullable
    public static RecipeHolder<?> byKey(Level level, ResourceKey<Recipe<?>> id) {
        return getRecipeMap(level).byKey(id);
    }
}
