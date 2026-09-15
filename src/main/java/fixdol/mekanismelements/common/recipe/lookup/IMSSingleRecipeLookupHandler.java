package fixdol.mekanismelements.common.recipe.lookup;

import net.minecraftforge.fluids.FluidStack;
import mekanism.api.chemical.gas.GasStack;
import mekanism.api.recipes.inputs.IInputHandler;
import fixdol.mekanismelements.common.recipe.lookup.IMSRecipeLookupHandler;
import net.minecraft.world.item.ItemStack;
import fixdol.mekanismelements.common.recipe.lookup.cache.MSInputRecipeCache;
import mekanism.api.recipes.MekanismRecipe;
import org.jetbrains.annotations.Nullable;
import java.util.function.Predicate;

import fixdol.mekanismelements.common.recipe.lookup.cache.MSSingleInputRecipeCache;


public interface IMSSingleRecipeLookupHandler <INPUT, RECIPE extends MekanismRecipe & Predicate<INPUT>, INPUT_CACHE extends MSSingleInputRecipeCache<INPUT, ?, RECIPE, ?>>
        extends IMSRecipeLookupHandler.IMSRecipeTypedLookupHandler<RECIPE, INPUT_CACHE> {
    default boolean containsRecipe(INPUT input) {
        return getMSRecipeType().getInputCache().containsInput(getHandlerWorld(), input);
    }

    /**
     * Finds the first recipe for the type of recipe we handle ({@link #getMSRecipeType()}) by looking up the given input against the recipe type's input cache.
     *
     * @param input Recipe input.
     *
     * @return Recipe matching the given input, or {@code null} if no recipe matches.
     */
    @Nullable
    default RECIPE findFirstRecipe(INPUT input) {
        return getMSRecipeType().getInputCache().findFirstRecipe(getHandlerWorld(), input);
    }

    /**
     * Finds the first recipe for the type of recipe we handle ({@link #getMSRecipeType()}) by looking up the given input against the recipe type's input cache.
     *
     * @param inputHandler Input handler to grab the recipe input from.
     *
     * @return Recipe matching the given input, or {@code null} if no recipe matches.
     */
    @Nullable
    default RECIPE findFirstRecipe(IInputHandler<INPUT> inputHandler) {
        return findFirstRecipe(inputHandler.getInput());
    }

    /**
     * Helper interface to make the generics that we have to pass to {@link IMSSingleRecipeLookupHandler} not as messy.
     */
    interface ItemRecipeLookupHandler<RECIPE extends MekanismRecipe & Predicate<ItemStack>> extends IMSSingleRecipeLookupHandler<ItemStack, RECIPE, MSInputRecipeCache.SingleItem<RECIPE>> {
    }

    /**
     * Helper interface to make the generics that we have to pass to {@link IMSSingleRecipeLookupHandler} not as messy.
     */
    interface FluidRecipeLookupHandler<RECIPE extends MekanismRecipe & Predicate<FluidStack>> extends IMSSingleRecipeLookupHandler<FluidStack, RECIPE, MSInputRecipeCache.SingleFluid<RECIPE>> {
    }

    /**
     * Helper interface to make the generics that we have to pass to {@link IMSSingleRecipeLookupHandler} not as messy.
     */
    interface ChemicalRecipeLookupHandler<RECIPE extends MekanismRecipe & Predicate<GasStack>>
            extends IMSSingleRecipeLookupHandler<GasStack, RECIPE, MSInputRecipeCache.SingleChemical<RECIPE>> {

        /**
         * Helper wrapper to convert a chemical to a chemical stack and pass it to {@link #containsRecipe(Object)} to make validity predicates easier and cleaner.
         */
        default boolean containsRecipe(mekanism.api.chemical.Chemical input) {
            return containsRecipe(new GasStack((mekanism.api.chemical.gas.Gas) input, 1));
        }
    }
}

