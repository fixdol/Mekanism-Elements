package fixdol.mekanismelements.common.recipe.lookup.cache;

import java.util.function.BiPredicate;
import mekanism.api.recipes.ingredients.ChemicalStackIngredient;
import net.minecraftforge.fluids.FluidStack;
import mekanism.api.recipes.ingredients.FluidStackIngredient;
import java.util.function.Function;
import mekanism.api.chemical.gas.Gas;
import mekanism.api.chemical.gas.GasStack;
import net.minecraft.world.item.ItemStack;
import mekanism.api.recipes.ingredients.ItemStackIngredient;
import fixdol.mekanismelements.common.recipe.lookup.cache.MSDoubleInputRecipeCache;
import fixdol.mekanismelements.common.recipe.lookup.cache.MSInputRecipeCache;
import fixdol.mekanismelements.common.recipe.MSRecipeType;
import fixdol.mekanismelements.common.recipe.lookup.cache.MSSingleInputRecipeCache;
import mekanism.api.recipes.MekanismRecipe;
import java.util.function.Predicate;

import mekanism.api.recipes.chemical.ChemicalChemicalToChemicalRecipe;
import mekanism.common.recipe.lookup.cache.type.ChemicalInputCache;
import mekanism.common.recipe.lookup.cache.type.FluidInputCache;
import mekanism.common.recipe.lookup.cache.type.ItemInputCache;
import net.minecraftforge.common.util.TriPredicate;


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

    public static class SingleChemical<RECIPE extends MekanismRecipe & Predicate<GasStack>>
            extends MSSingleInputRecipeCache<GasStack, ChemicalStackIngredient<Gas, GasStack>, RECIPE, ChemicalInputCache<Gas, GasStack, RECIPE>> {

        public SingleChemical(MSRecipeType<RECIPE, ?> recipeType, Function<RECIPE, ChemicalStackIngredient<Gas, GasStack>> inputExtractor) {
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

    public static class ItemChemical<RECIPE extends MekanismRecipe &
            BiPredicate<ItemStack, GasStack>> extends MSDoubleInputRecipeCache<ItemStack, ItemStackIngredient, GasStack, ChemicalStackIngredient<Gas, GasStack>, RECIPE,
            ItemInputCache<RECIPE>, ChemicalInputCache<Gas, GasStack, RECIPE>> {

        public ItemChemical(MSRecipeType<RECIPE, ?> recipeType, Function<RECIPE, ItemStackIngredient> inputAExtractor,
                            Function<RECIPE, ChemicalStackIngredient<Gas, GasStack>> inputBExtractor) {
            super(recipeType, inputAExtractor, new ItemInputCache<>(), inputBExtractor, new ChemicalInputCache<>());
        }
    }

    public static class FluidChemical<RECIPE extends MekanismRecipe &
            BiPredicate<FluidStack, GasStack>> extends MSDoubleInputRecipeCache<FluidStack, FluidStackIngredient, GasStack, ChemicalStackIngredient<Gas, GasStack>, RECIPE,
            FluidInputCache<RECIPE>, ChemicalInputCache<Gas, GasStack, RECIPE>> {

        public FluidChemical(MSRecipeType<RECIPE, ?> recipeType, Function<RECIPE, FluidStackIngredient> inputAExtractor,
                             Function<RECIPE, ChemicalStackIngredient<Gas, GasStack>> inputBExtractor) {
            super(recipeType, inputAExtractor, new FluidInputCache<>(), inputBExtractor, new ChemicalInputCache<>());
        }
    }

    public static class EitherSideChemical<RECIPE extends ChemicalChemicalToChemicalRecipe<Gas, GasStack, ChemicalStackIngredient.GasStackIngredient>>
            extends MSEitherSideInputRecipeCache<GasStack, ChemicalStackIngredient<Gas, GasStack>, RECIPE, ChemicalInputCache<Gas, GasStack, RECIPE>> {

        public EitherSideChemical(MSRecipeType<RECIPE, ?> recipeType) {
            super(recipeType, ChemicalChemicalToChemicalRecipe<Gas, GasStack, ChemicalStackIngredient.GasStackIngredient>::getLeftInput, ChemicalChemicalToChemicalRecipe<Gas, GasStack, ChemicalStackIngredient.GasStackIngredient>::getRightInput, new ChemicalInputCache<>());
        }
    }

    public static class ItemFluidChemical<RECIPE extends MekanismRecipe &
            TriPredicate<ItemStack, FluidStack, GasStack>> extends MSTripleInputRecipeCache<ItemStack, ItemStackIngredient, FluidStack, FluidStackIngredient, GasStack,
            ChemicalStackIngredient<Gas, GasStack>, RECIPE, ItemInputCache<RECIPE>, FluidInputCache<RECIPE>, ChemicalInputCache<Gas, GasStack, RECIPE>> {

        public ItemFluidChemical(MSRecipeType<RECIPE, ?> recipeType, Function<RECIPE, ItemStackIngredient> inputAExtractor,
                                 Function<RECIPE, FluidStackIngredient> inputBExtractor, Function<RECIPE, ChemicalStackIngredient<Gas, GasStack>> inputCExtractor) {
            super(recipeType, inputAExtractor, new ItemInputCache<>(), inputBExtractor, new FluidInputCache<>(), inputCExtractor, new ChemicalInputCache<>());
        }
    }

    public static class TripleChemical<RECIPE extends MekanismRecipe &
            TriPredicate<GasStack, GasStack, GasStack>> extends MSTripleInputRecipeCache<GasStack, ChemicalStackIngredient<Gas, GasStack>, GasStack, ChemicalStackIngredient<Gas, GasStack>, GasStack,
            ChemicalStackIngredient<Gas, GasStack>, RECIPE, ChemicalInputCache<Gas, GasStack, RECIPE>, ChemicalInputCache<Gas, GasStack, RECIPE>, ChemicalInputCache<Gas, GasStack, RECIPE>> {

        public TripleChemical(MSRecipeType<RECIPE, ?> recipeType, Function<RECIPE, ChemicalStackIngredient<Gas, GasStack>> inputAExtractor,
                           Function<RECIPE, ChemicalStackIngredient<Gas, GasStack>> inputBExtractor, Function<RECIPE, ChemicalStackIngredient<Gas, GasStack>> inputCExtractor) {
            super(recipeType, inputAExtractor, new ChemicalInputCache<>(), inputBExtractor, new ChemicalInputCache<>(), inputCExtractor, new ChemicalInputCache<>());
        }
    }
}

