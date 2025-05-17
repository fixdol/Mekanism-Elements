package com.fxd927.mekanismelements.common.recipe.lookup.cache;

import com.fxd927.mekanismelements.common.recipe.MSRecipeType;
import mekanism.api.chemical.Chemical;
import mekanism.api.chemical.ChemicalStack;
import mekanism.api.recipes.MekanismRecipe;
import mekanism.api.recipes.chemical.ChemicalChemicalToChemicalRecipe;
import mekanism.api.recipes.ingredients.ChemicalStackIngredient;
import mekanism.api.recipes.ingredients.FluidStackIngredient;
import mekanism.api.recipes.ingredients.ItemStackIngredient;
import mekanism.common.recipe.lookup.cache.type.ChemicalInputCache;
import mekanism.common.recipe.lookup.cache.type.FluidInputCache;
import mekanism.common.recipe.lookup.cache.type.ItemInputCache;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.util.TriPredicate;
import net.minecraftforge.fluids.FluidStack;

import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;

public class MSInputRecipeCache {
    public static class SingleItem<RECIPE extends MekanismRecipe & Predicate<ItemStack>>
            extends MSSingleInputRecipeCache<ItemStack, ItemStackIngredient, RECIPE, ItemInputCache<RECIPE>> {

        public SingleItem(MSRecipeType<RECIPE, ?> recipeType, Function<RECIPE, ItemStackIngredient> inputExtractor) {
            super(recipeType, inputExtractor, new ItemInputCache<>());
        }
    }

    public static class SingleFluid<RECIPE extends MekanismRecipe & Predicate<FluidStack>>
            extends MSSingleInputRecipeCache<FluidStack, FluidStackIngredient, RECIPE, FluidInputCache<RECIPE>> {

        public SingleFluid(MSRecipeType<RECIPE, ?> recipeType, Function<RECIPE, FluidStackIngredient> inputExtractor) {
            super(recipeType, inputExtractor, new FluidInputCache<>());
        }
    }

    public static class SingleChemical<CHEMICAL extends Chemical<CHEMICAL>, STACK extends ChemicalStack<CHEMICAL>, RECIPE extends MekanismRecipe & Predicate<STACK>>
            extends MSSingleInputRecipeCache<STACK, ChemicalStackIngredient<CHEMICAL, STACK>, RECIPE, ChemicalInputCache<CHEMICAL, STACK, RECIPE>> {

        public SingleChemical(MSRecipeType<RECIPE, ?> recipeType, Function<RECIPE, ChemicalStackIngredient<CHEMICAL, STACK>> inputExtractor) {
            super(recipeType, inputExtractor, new ChemicalInputCache<>());
        }
    }

    public static class DoubleItem<RECIPE extends MekanismRecipe & BiPredicate<ItemStack, ItemStack>>
            extends MSDoubleInputRecipeCache.DoubleSameInputRecipeCache<ItemStack, ItemStackIngredient, RECIPE, ItemInputCache<RECIPE>> {

        public DoubleItem(MSRecipeType<RECIPE, ?> recipeType, Function<RECIPE, ItemStackIngredient> inputAExtractor,
                          Function<RECIPE, ItemStackIngredient> inputBExtractor) {
            super(recipeType, inputAExtractor, inputBExtractor, ItemInputCache::new);
        }
    }

    public static class ItemFluid<RECIPE extends MekanismRecipe &
            BiPredicate<ItemStack, FluidStack>> extends MSDoubleInputRecipeCache<ItemStack, ItemStackIngredient, FluidStack, FluidStackIngredient, RECIPE,
            ItemInputCache<RECIPE>, FluidInputCache<RECIPE>> {

        public ItemFluid(MSRecipeType<RECIPE, ?> recipeType, Function<RECIPE, ItemStackIngredient> inputAExtractor,
                         Function<RECIPE, FluidStackIngredient> inputBExtractor) {
            super(recipeType, inputAExtractor, new ItemInputCache<>(), inputBExtractor, new FluidInputCache<>());
        }
    }

    public static class ItemChemical<CHEMICAL extends Chemical<CHEMICAL>, STACK extends ChemicalStack<CHEMICAL>, RECIPE extends MekanismRecipe &
            BiPredicate<ItemStack, STACK>> extends MSDoubleInputRecipeCache<ItemStack, ItemStackIngredient, STACK, ChemicalStackIngredient<CHEMICAL, STACK>, RECIPE,
            ItemInputCache<RECIPE>, ChemicalInputCache<CHEMICAL, STACK, RECIPE>> {

        public ItemChemical(MSRecipeType<RECIPE, ?> recipeType, Function<RECIPE, ItemStackIngredient> inputAExtractor,
                            Function<RECIPE, ChemicalStackIngredient<CHEMICAL, STACK>> inputBExtractor) {
            super(recipeType, inputAExtractor, new ItemInputCache<>(), inputBExtractor, new ChemicalInputCache<>());
        }
    }

    public static class FluidChemical<CHEMICAL extends Chemical<CHEMICAL>, STACK extends ChemicalStack<CHEMICAL>, RECIPE extends MekanismRecipe &
            BiPredicate<FluidStack, STACK>> extends MSDoubleInputRecipeCache<FluidStack, FluidStackIngredient, STACK, ChemicalStackIngredient<CHEMICAL, STACK>, RECIPE,
            FluidInputCache<RECIPE>, ChemicalInputCache<CHEMICAL, STACK, RECIPE>> {

        public FluidChemical(MSRecipeType<RECIPE, ?> recipeType, Function<RECIPE, FluidStackIngredient> inputAExtractor,
                             Function<RECIPE, ChemicalStackIngredient<CHEMICAL, STACK>> inputBExtractor) {
            super(recipeType, inputAExtractor, new FluidInputCache<>(), inputBExtractor, new ChemicalInputCache<>());
        }
    }

    public static class EitherSideChemical<CHEMICAL extends Chemical<CHEMICAL>, STACK extends ChemicalStack<CHEMICAL>,
            RECIPE extends ChemicalChemicalToChemicalRecipe<CHEMICAL, STACK, ? extends ChemicalStackIngredient<CHEMICAL, STACK>>>
            extends MSEitherSideInputRecipeCache<STACK, ChemicalStackIngredient<CHEMICAL, STACK>, RECIPE, ChemicalInputCache<CHEMICAL, STACK, RECIPE>> {

        public EitherSideChemical(MSRecipeType<RECIPE, ?> recipeType) {
            super(recipeType, ChemicalChemicalToChemicalRecipe::getLeftInput, ChemicalChemicalToChemicalRecipe::getRightInput, new ChemicalInputCache<>());
        }
    }

    public static class ItemFluidChemical<CHEMICAL extends Chemical<CHEMICAL>, STACK extends ChemicalStack<CHEMICAL>, RECIPE extends MekanismRecipe &
            TriPredicate<ItemStack, FluidStack, STACK>> extends MSTripleInputRecipeCache<ItemStack, ItemStackIngredient, FluidStack, FluidStackIngredient, STACK,
            ChemicalStackIngredient<CHEMICAL, STACK>, RECIPE, ItemInputCache<RECIPE>, FluidInputCache<RECIPE>, ChemicalInputCache<CHEMICAL, STACK, RECIPE>> {

        public ItemFluidChemical(MSRecipeType<RECIPE, ?> recipeType, Function<RECIPE, ItemStackIngredient> inputAExtractor,
                                 Function<RECIPE, FluidStackIngredient> inputBExtractor, Function<RECIPE, ChemicalStackIngredient<CHEMICAL, STACK>> inputCExtractor) {
            super(recipeType, inputAExtractor, new ItemInputCache<>(), inputBExtractor, new FluidInputCache<>(), inputCExtractor, new ChemicalInputCache<>());
        }
    }

    public static class TripleChemical<CHEMICAL extends Chemical<CHEMICAL>, STACK extends ChemicalStack<CHEMICAL>, RECIPE extends MekanismRecipe &
            TriPredicate<STACK, STACK, STACK>> extends MSTripleInputRecipeCache<STACK, ChemicalStackIngredient<CHEMICAL, STACK>, STACK, ChemicalStackIngredient<CHEMICAL, STACK>, STACK,
            ChemicalStackIngredient<CHEMICAL, STACK>, RECIPE, ChemicalInputCache<CHEMICAL, STACK, RECIPE>, ChemicalInputCache<CHEMICAL, STACK, RECIPE>, ChemicalInputCache<CHEMICAL, STACK, RECIPE>> {

        public TripleChemical(MSRecipeType<RECIPE, ?> recipeType, Function<RECIPE, ChemicalStackIngredient<CHEMICAL, STACK>> inputAExtractor,
                           Function<RECIPE, ChemicalStackIngredient<CHEMICAL, STACK>> inputBExtractor, Function<RECIPE, ChemicalStackIngredient<CHEMICAL, STACK>> inputCExtractor) {
            super(recipeType, inputAExtractor, new ChemicalInputCache<>(), inputBExtractor, new ChemicalInputCache<>(), inputCExtractor, new ChemicalInputCache<>());
        }
    }
}
