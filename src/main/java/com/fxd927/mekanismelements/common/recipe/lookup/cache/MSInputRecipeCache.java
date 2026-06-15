package com.fxd927.mekanismelements.common.recipe.lookup.cache;

import com.fxd927.mekanismelements.common.recipe.MSRecipeType;
import mekanism.api.chemical.ChemicalStack;
import mekanism.api.recipes.ChemicalChemicalToChemicalRecipe;
import mekanism.api.recipes.MekanismRecipe;
import mekanism.api.recipes.ingredients.ChemicalStackIngredient;
import mekanism.api.recipes.ingredients.FluidStackIngredient;
import mekanism.api.recipes.ingredients.ItemStackIngredient;
import mekanism.common.recipe.lookup.cache.type.ChemicalInputCache;
import mekanism.common.recipe.lookup.cache.type.FluidInputCache;
import mekanism.common.recipe.lookup.cache.type.ItemInputCache;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.util.TriPredicate;
import net.neoforged.neoforge.fluids.FluidStack;

import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;

public class MSInputRecipeCache {
    public static class SingleItem<RECIPE extends MekanismRecipe<?> & Predicate<ItemStack>>
            extends MSSingleInputRecipeCache<ItemStack, ItemStackIngredient, RECIPE, ItemInputCache<RECIPE>> {

        public SingleItem(MSRecipeType<RECIPE, ?> recipeType, Function<RECIPE, ItemStackIngredient> inputExtractor) {
            super(recipeType, inputExtractor, new ItemInputCache<>());
        }
    }

    public static class SingleFluid<RECIPE extends MekanismRecipe<?> & Predicate<FluidStack>>
            extends MSSingleInputRecipeCache<FluidStack, FluidStackIngredient, RECIPE, FluidInputCache<RECIPE>> {

        public SingleFluid(MSRecipeType<RECIPE, ?> recipeType, Function<RECIPE, FluidStackIngredient> inputExtractor) {
            super(recipeType, inputExtractor, new FluidInputCache<>());
        }
    }

    public static class SingleChemical<RECIPE extends MekanismRecipe<?> & Predicate<ChemicalStack>>
            extends MSSingleInputRecipeCache<ChemicalStack, ChemicalStackIngredient, RECIPE, ChemicalInputCache<RECIPE>> {

        public SingleChemical(MSRecipeType<RECIPE, ?> recipeType, Function<RECIPE, ChemicalStackIngredient> inputExtractor) {
            super(recipeType, inputExtractor, new ChemicalInputCache<>());
        }
    }

    public static class DoubleItem<RECIPE extends MekanismRecipe<?> & BiPredicate<ItemStack, ItemStack>>
            extends MSDoubleInputRecipeCache.DoubleSameInputRecipeCache<ItemStack, ItemStackIngredient, RECIPE, ItemInputCache<RECIPE>> {

        public DoubleItem(MSRecipeType<RECIPE, ?> recipeType, Function<RECIPE, ItemStackIngredient> inputAExtractor,
                          Function<RECIPE, ItemStackIngredient> inputBExtractor) {
            super(recipeType, inputAExtractor, inputBExtractor, ItemInputCache::new);
        }
    }

    public static class ItemFluid<RECIPE extends MekanismRecipe<?> &
            BiPredicate<ItemStack, FluidStack>> extends MSDoubleInputRecipeCache<ItemStack, ItemStackIngredient, FluidStack, FluidStackIngredient, RECIPE,
            ItemInputCache<RECIPE>, FluidInputCache<RECIPE>> {

        public ItemFluid(MSRecipeType<RECIPE, ?> recipeType, Function<RECIPE, ItemStackIngredient> inputAExtractor,
                         Function<RECIPE, FluidStackIngredient> inputBExtractor) {
            super(recipeType, inputAExtractor, new ItemInputCache<>(), inputBExtractor, new FluidInputCache<>());
        }
    }

    public static class ItemChemical<RECIPE extends MekanismRecipe<?> &
            BiPredicate<ItemStack, ChemicalStack>> extends MSDoubleInputRecipeCache<ItemStack, ItemStackIngredient, ChemicalStack, ChemicalStackIngredient, RECIPE,
            ItemInputCache<RECIPE>, ChemicalInputCache<RECIPE>> {

        public ItemChemical(MSRecipeType<RECIPE, ?> recipeType, Function<RECIPE, ItemStackIngredient> inputAExtractor,
                            Function<RECIPE, ChemicalStackIngredient> inputBExtractor) {
            super(recipeType, inputAExtractor, new ItemInputCache<>(), inputBExtractor, new ChemicalInputCache<>());
        }
    }

    public static class FluidChemical<RECIPE extends MekanismRecipe<?> &
            BiPredicate<FluidStack, ChemicalStack>> extends MSDoubleInputRecipeCache<FluidStack, FluidStackIngredient, ChemicalStack, ChemicalStackIngredient, RECIPE,
            FluidInputCache<RECIPE>, ChemicalInputCache<RECIPE>> {

        public FluidChemical(MSRecipeType<RECIPE, ?> recipeType, Function<RECIPE, FluidStackIngredient> inputAExtractor,
                             Function<RECIPE, ChemicalStackIngredient> inputBExtractor) {
            super(recipeType, inputAExtractor, new FluidInputCache<>(), inputBExtractor, new ChemicalInputCache<>());
        }
    }

    public static class EitherSideChemical<RECIPE extends ChemicalChemicalToChemicalRecipe>
            extends MSEitherSideInputRecipeCache<ChemicalStack, ChemicalStackIngredient, RECIPE, ChemicalInputCache<RECIPE>> {

        public EitherSideChemical(MSRecipeType<RECIPE, ?> recipeType) {
            super(recipeType, ChemicalChemicalToChemicalRecipe::getLeftInput, ChemicalChemicalToChemicalRecipe::getRightInput, new ChemicalInputCache<>());
        }
    }

    public static class ItemFluidChemical<RECIPE extends MekanismRecipe<?> &
            TriPredicate<ItemStack, FluidStack, ChemicalStack>> extends MSTripleInputRecipeCache<ItemStack, ItemStackIngredient, FluidStack, FluidStackIngredient, ChemicalStack,
            ChemicalStackIngredient, RECIPE, ItemInputCache<RECIPE>, FluidInputCache<RECIPE>, ChemicalInputCache<RECIPE>> {

        public ItemFluidChemical(MSRecipeType<RECIPE, ?> recipeType, Function<RECIPE, ItemStackIngredient> inputAExtractor,
                                 Function<RECIPE, FluidStackIngredient> inputBExtractor, Function<RECIPE, ChemicalStackIngredient> inputCExtractor) {
            super(recipeType, inputAExtractor, new ItemInputCache<>(), inputBExtractor, new FluidInputCache<>(), inputCExtractor, new ChemicalInputCache<>());
        }
    }

    public static class TripleChemical<RECIPE extends MekanismRecipe<?> &
            TriPredicate<ChemicalStack, ChemicalStack, ChemicalStack>> extends MSTripleInputRecipeCache<ChemicalStack, ChemicalStackIngredient, ChemicalStack, ChemicalStackIngredient, ChemicalStack,
            ChemicalStackIngredient, RECIPE, ChemicalInputCache<RECIPE>, ChemicalInputCache<RECIPE>, ChemicalInputCache<RECIPE>> {

        public TripleChemical(MSRecipeType<RECIPE, ?> recipeType, Function<RECIPE, ChemicalStackIngredient> inputAExtractor,
                           Function<RECIPE, ChemicalStackIngredient> inputBExtractor, Function<RECIPE, ChemicalStackIngredient> inputCExtractor) {
            super(recipeType, inputAExtractor, new ChemicalInputCache<>(), inputBExtractor, new ChemicalInputCache<>(), inputCExtractor, new ChemicalInputCache<>());
        }
    }
}

