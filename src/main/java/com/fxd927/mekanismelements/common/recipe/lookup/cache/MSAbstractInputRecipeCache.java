package com.fxd927.mekanismelements.common.recipe.lookup.cache;

import com.fxd927.mekanismelements.common.recipe.MSRecipeType;
import mekanism.api.recipes.MekanismRecipe;
import mekanism.api.recipes.ingredients.InputIngredient;
import mekanism.common.recipe.lookup.cache.IInputRecipeCache;
import mekanism.common.recipe.lookup.cache.type.IInputCache;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;

public abstract class MSAbstractInputRecipeCache<RECIPE extends MekanismRecipe> implements IInputRecipeCache {
    protected final MSRecipeType<RECIPE, ?> recipeType;
    protected boolean initialized;

    protected MSAbstractInputRecipeCache(MSRecipeType<RECIPE, ?> recipeType) {
        this.recipeType = recipeType;
    }

    @Override
    public void clear() {
        initialized = false;
    }

    /**
     * Initializes the cache if it hasn't already been initialized.
     */
    protected void initCacheIfNeeded(@Nullable Level world) {
        if (!initialized) {
            initialized = true;
            initCache(recipeType.getRecipes(world));
        }
    }

    /**
     * Initializes the recipe cache based on the given recipes.
     *
     * @param recipes Recipes to build the cache for.
     */
    protected abstract void initCache(List<RECIPE> recipes);

    /**
     * Helper to filter a potentially null collection of recipes by a given predicate.
     */
    @Nullable
    protected RECIPE findFirstRecipe(@Nullable Collection<RECIPE> recipes, Predicate<RECIPE> matchCriteria) {
        return recipes == null ? null : recipes.stream().filter(matchCriteria).findFirst().orElse(null);
    }

    /**
     * Helper to check if a cache contains a given input, or if not, if the complex recipe fallback set contains a matching recipe.
     */
    protected <INPUT, INGREDIENT extends InputIngredient<INPUT>, CACHE extends IInputCache<INPUT, INGREDIENT, RECIPE>> boolean containsInput(
            @Nullable Level world, INPUT input, Function<RECIPE, INGREDIENT> inputExtractor, CACHE cache, Set<RECIPE> complexRecipes) {
        if (cache.isEmpty(input)) {
            return false;
        }
        initCacheIfNeeded(world);
        return cache.contains(input) || complexRecipes.stream().anyMatch(recipe -> inputExtractor.apply(recipe).testType(input));
    }

    /**
     * Helper to check if a cache contains a given input grouping, or if not, if the complex recipe fallback set contains a matching recipe. This method is mainly used
     * for purposes of implementing insertion predicates, so it has the following behaviors. This allows it to short circuit in cases where we already know the input is
     * valid (the last case in the below list).
     * <ul>
     * <li>If the first input is empty: This will check if there is a recipe that the second input is valid for.</li>
     * <li>If the first input is not empty but the second input is empty: This will return true.</li>
     * </ul>
     */
    protected <INPUT_1, INGREDIENT_1 extends InputIngredient<INPUT_1>, CACHE_1 extends IInputCache<INPUT_1, INGREDIENT_1, RECIPE>, INPUT_2,
            INGREDIENT_2 extends InputIngredient<INPUT_2>, CACHE_2 extends IInputCache<INPUT_2, INGREDIENT_2, RECIPE>> boolean containsPairing(@Nullable Level world,
                                                                                                                                               INPUT_1 input1, Function<RECIPE, INGREDIENT_1> input1Extractor, CACHE_1 cache1, Set<RECIPE> complexIngredients1, INPUT_2 input2,
                                                                                                                                               Function<RECIPE, INGREDIENT_2> input2Extractor, CACHE_2 cache2, Set<RECIPE> complexIngredients2) {
        if (cache1.isEmpty(input1)) {
            //Note: We don't bother checking if 2 is empty here as it will be verified in containsInput
            return containsInput(world, input2, input2Extractor, cache2, complexIngredients2);
        } else if (cache2.isEmpty(input2)) {
            return true;
        }
        initCacheIfNeeded(world);
        //Note: If cache 1 contains input 1 then we only need to test the type of input 2 as we already know input 1 matches
        if (cache1.contains(input1, recipe -> input2Extractor.apply(recipe).testType(input2))) {
            return true;
        }
        //Our quick lookup 1 cache does not contain it, check any recipes where the 1 ingredient was complex
        return complexIngredients1.stream().anyMatch(recipe -> input1Extractor.apply(recipe).testType(input1) && input2Extractor.apply(recipe).testType(input2));
    }
}
