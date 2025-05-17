package com.fxd927.mekanismelements.common.recipe;

import com.fxd927.mekanismelements.api.recipes.AdsorptionRecipe;
import com.fxd927.mekanismelements.api.recipes.ChemicalDemolitionRecipe;
import com.fxd927.mekanismelements.api.recipes.RadiationIrradiatingRecipe;
import com.fxd927.mekanismelements.common.MekanismElements;
import com.fxd927.mekanismelements.common.recipe.lookup.cache.MSInputRecipeCache;
import com.fxd927.mekanismelements.common.registration.impl.MSRecipeTypeDeferredRegister;
import com.fxd927.mekanismelements.common.registration.impl.MSRecipeTypeRegistryObject;
import mekanism.api.chemical.gas.Gas;
import mekanism.api.chemical.gas.GasStack;
import mekanism.api.recipes.FluidToFluidRecipe;
import mekanism.api.recipes.MekanismRecipe;
import mekanism.client.MekanismClient;
import mekanism.common.recipe.lookup.cache.IInputRecipeCache;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.server.ServerLifecycleHooks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class MSRecipeType<RECIPE extends MekanismRecipe, INPUT_CACHE extends IInputRecipeCache> implements RecipeType<RECIPE>,
        IMSRecipeTypeProvider<RECIPE, INPUT_CACHE> {
    public static final MSRecipeTypeDeferredRegister RECIPE_TYPES = new MSRecipeTypeDeferredRegister(MekanismElements.MODID);

    public static final MSRecipeTypeRegistryObject<RadiationIrradiatingRecipe, MSInputRecipeCache.ItemChemical<Gas, GasStack, RadiationIrradiatingRecipe>> RADIATION_IRRADIATING =
            register("radiation_irradiating", recipeType -> new MSInputRecipeCache.ItemChemical<>(recipeType, RadiationIrradiatingRecipe::getItemInput, RadiationIrradiatingRecipe::getGasInput));
    public static final MSRecipeTypeRegistryObject<AdsorptionRecipe, MSInputRecipeCache.ItemFluid<AdsorptionRecipe>> ADSORPTION =
            register("adsorption", recipeType -> new MSInputRecipeCache.ItemFluid<>(recipeType, AdsorptionRecipe::getItemInput, AdsorptionRecipe::getFluidInput));
    public static final MSRecipeTypeRegistryObject<FluidToFluidRecipe, MSInputRecipeCache.SingleFluid<FluidToFluidRecipe>> ADVANCED_EVAPORATING =
            register("evaporating", recipeType -> new MSInputRecipeCache.SingleFluid<>(recipeType, FluidToFluidRecipe::getInput));
    public static final MSRecipeTypeRegistryObject<ChemicalDemolitionRecipe, MSInputRecipeCache.ItemChemical<Gas, GasStack, ChemicalDemolitionRecipe>> CHEMICAL_DEMOLITION =
            register("chemical_demolition", recipeType -> new MSInputRecipeCache.ItemChemical<>(recipeType, ChemicalDemolitionRecipe::getItemInput, ChemicalDemolitionRecipe::getGasInput));
   public static <RECIPE extends MekanismRecipe, INPUT_CACHE extends IInputRecipeCache> MSRecipeTypeRegistryObject<RECIPE, INPUT_CACHE> register(String name,
                                                                                                                                                   Function<MSRecipeType<RECIPE, INPUT_CACHE>, INPUT_CACHE> inputCacheCreator) {
        return RECIPE_TYPES.register(name, () -> new MSRecipeType<>(name, inputCacheCreator));
    }

    public static void clearCache() {
        for (IMSRecipeTypeProvider<?, ?> recipeTypeProvider : RECIPE_TYPES.getAllRecipeTypes()) {
            recipeTypeProvider.getMSRecipeType().clearCaches();
        }
    }

    private List<RECIPE> cachedRecipes = Collections.emptyList();
    private final ResourceLocation registryName;
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
    public ResourceLocation getRegistryName() {
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
        if (world == null) {
            if (FMLEnvironment.dist.isClient()) {
                world = MekanismClient.tryGetClientWorld();
            } else {
                world = ServerLifecycleHooks.getCurrentServer().overworld();
            }
            if (world == null) {
                return Collections.emptyList();
            }
        }
        if (cachedRecipes.isEmpty()) {
            RecipeManager recipeManager = world.getRecipeManager();
            List<RECIPE> recipes = recipeManager.getAllRecipesFor(this);
            cachedRecipes = recipes.stream()
                    .filter(recipe -> !recipe.isIncomplete())
                    .toList();
        }
        return cachedRecipes;
    }

    /**
     * Helper for getting a recipe from a world's recipe manager.
     */
    public static <C extends Container, RECIPE_TYPE extends Recipe<C>> Optional<RECIPE_TYPE> getRecipeFor(RecipeType<RECIPE_TYPE> recipeType, C inventory, Level level) {
        return level.getRecipeManager().getRecipeFor(recipeType, inventory, level)
                .filter(recipe -> recipe.isSpecial() || !recipe.isIncomplete());
    }

    /**
     * Helper for getting a recipe from a world's recipe manager.
     */
    public static Optional<? extends Recipe<?>> byKey(Level level, ResourceLocation id) {
        return level.getRecipeManager().byKey(id)
                .filter(recipe -> recipe.isSpecial() || !recipe.isIncomplete());
    }
}
