package fixdol.mekanismelements.common.recipe.lookup;

import java.util.function.Predicate;
import mekanism.api.chemical.Chemical;
import mekanism.api.chemical.ChemicalStack;
import mekanism.api.recipes.MekanismRecipe;
import mekanism.api.recipes.inputs.IInputHandler;
import fixdol.mekanismelements.common.recipe.lookup.IMSRecipeLookupHandler.IMSRecipeTypedLookupHandler;
import fixdol.mekanismelements.common.recipe.lookup.cache.MSInputRecipeCache.SingleChemical;
import fixdol.mekanismelements.common.recipe.lookup.cache.MSInputRecipeCache.SingleFluid;
import fixdol.mekanismelements.common.recipe.lookup.cache.MSInputRecipeCache.SingleItem;
import fixdol.mekanismelements.common.recipe.lookup.cache.MSSingleInputRecipeCache;
import net.minecraft.core.TypedInstance;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.fluids.FluidStack;
import org.jspecify.annotations.Nullable;

/// Helper expansion of [IMSRecipeLookupHandler] for easily implementing contains and find recipe lookups for recipes that take a single input using the input cache.
public interface IMSSingleRecipeLookupHandler<TYPE, INPUT extends TypedInstance<TYPE>, RECIPE extends MekanismRecipe<?> & Predicate<INPUT>,
      INPUT_CACHE extends MSSingleInputRecipeCache<TYPE, INPUT, ?, RECIPE, ?>> extends IMSRecipeTypedLookupHandler<RECIPE, INPUT_CACHE> {

    /// Checks if there is a matching recipe of type [#getMSRecipeType()] that has the given input.
    ///
    /// @param input Recipe input.
    ///
    /// @return `true` if there is a match, `false` if there isn't.
    default boolean containsRecipe(TypedInstance<TYPE> input) {
        return getMSRecipeType().getInputCache().containsInput(getHandlerWorld(), input);
    }

    /// Finds the first recipe for the type of recipe we handle ([#getMSRecipeType()]) by looking up the given input against the recipe type's input cache.
    ///
    /// @param input Recipe input.
    ///
    /// @return Recipe matching the given input, or `null` if no recipe matches.
    @Nullable
    default RECIPE findFirstRecipe(INPUT input) {
        return getMSRecipeType().getInputCache().findFirstRecipe(getHandlerWorld(), input);
    }

    /// Finds the first recipe for the type of recipe we handle ([#getMSRecipeType()]) by looking up the given input against the recipe type's input cache.
    ///
    /// @param inputHandler Input handler to grab the recipe input from.
    ///
    /// @return Recipe matching the given input, or `null` if no recipe matches.
    @Nullable
    default RECIPE findFirstRecipe(IInputHandler<TYPE, INPUT> inputHandler) {
        return findFirstRecipe(inputHandler.getInput());
    }

    /// Helper interface to make the generics that we have to pass to [IMSSingleRecipeLookupHandler] not as messy.
    interface ItemRecipeLookupHandler<RECIPE extends MekanismRecipe<?> & Predicate<ItemStack>> extends IMSSingleRecipeLookupHandler<Item, ItemStack, RECIPE, SingleItem<RECIPE>> {
    }

    /// Helper interface to make the generics that we have to pass to [IMSSingleRecipeLookupHandler] not as messy.
    interface FluidRecipeLookupHandler<RECIPE extends MekanismRecipe<?> & Predicate<FluidStack>> extends IMSSingleRecipeLookupHandler<Fluid, FluidStack, RECIPE, SingleFluid<RECIPE>> {
    }

    /// Helper interface to make the generics that we have to pass to [IMSSingleRecipeLookupHandler] not as messy.
    interface ChemicalRecipeLookupHandler<RECIPE extends MekanismRecipe<?> & Predicate<ChemicalStack>> extends IMSSingleRecipeLookupHandler<Chemical, ChemicalStack, RECIPE, SingleChemical<RECIPE>> {
    }
}