package fixdol.mekanismelements.common.recipe.lookup;

import java.util.function.BiPredicate;
import mekanism.api.chemical.Chemical;
import mekanism.api.chemical.ChemicalStack;
import mekanism.api.recipes.MekanismRecipe;
import mekanism.api.recipes.inputs.IInputHandler;
import fixdol.mekanismelements.common.recipe.lookup.IMSRecipeLookupHandler.IMSRecipeTypedLookupHandler;
import fixdol.mekanismelements.common.recipe.lookup.cache.MSDoubleInputRecipeCache;
import fixdol.mekanismelements.common.recipe.lookup.cache.MSInputRecipeCache.DoubleItem;
import fixdol.mekanismelements.common.recipe.lookup.cache.MSInputRecipeCache.FluidChemical;
import fixdol.mekanismelements.common.recipe.lookup.cache.MSInputRecipeCache.ItemChemical;
import fixdol.mekanismelements.common.recipe.lookup.cache.MSInputRecipeCache.ItemFluid;
import net.minecraft.core.TypedInstance;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.fluids.FluidStack;
import org.jspecify.annotations.Nullable;

/// Helper expansion of [IMSRecipeLookupHandler] for easily implementing contains and find recipe lookups for recipes that takes two inputs.
public interface IMSDoubleRecipeLookupHandler<HOLDER_A, INPUT_A extends TypedInstance<HOLDER_A>, HOLDER_B, INPUT_B extends TypedInstance<HOLDER_B>,
      RECIPE extends MekanismRecipe<?> & BiPredicate<INPUT_A, INPUT_B>, INPUT_CACHE extends MSDoubleInputRecipeCache<HOLDER_A, INPUT_A, ?, HOLDER_B, INPUT_B, ?, RECIPE, ?, ?>>
      extends IMSRecipeTypedLookupHandler<RECIPE, INPUT_CACHE> {

    /// Checks if there is a matching recipe of type [#getMSRecipeType()] that has the given inputs.
    ///
    /// @param inputA Recipe input a.
    /// @param inputB Recipe input b.
    ///
    /// @return `true` if there is a match, `false` if there isn't.
    ///
    /// @apiNote See [MSDoubleInputRecipeCache#containsInputAB(Level, TypedInstance, TypedInstance)] and
    /// [MSDoubleInputRecipeCache#containsInputBA(Level, TypedInstance, TypedInstance)]
    /// for more details about when this method should be called versus when [#containsRecipeBA(TypedInstance, TypedInstance)] should be called.
    default boolean containsRecipeAB(TypedInstance<HOLDER_A> inputA, TypedInstance<HOLDER_B> inputB) {
        return getMSRecipeType().getInputCache().containsInputAB(getHandlerWorld(), inputA, inputB);
    }

    /// Checks if there is a matching recipe of type [#getMSRecipeType()] that has the given inputs.
    ///
    /// @param inputA Recipe input a.
    /// @param inputB Recipe input b.
    ///
    /// @return `true` if there is a match, `false` if there isn't.
    ///
    /// @apiNote See [MSDoubleInputRecipeCache#containsInputAB(Level, TypedInstance, TypedInstance)] and
    /// [MSDoubleInputRecipeCache#containsInputBA(Level, TypedInstance, TypedInstance)] for more details about when this method should be called versus when
    /// [#containsRecipeAB(TypedInstance, TypedInstance)] should be called.
    default boolean containsRecipeBA(TypedInstance<HOLDER_A> inputA, TypedInstance<HOLDER_B> inputB) {
        return getMSRecipeType().getInputCache().containsInputBA(getHandlerWorld(), inputA, inputB);
    }

    /// Checks if there is a matching recipe of type [#getMSRecipeType()] that has the given input.
    ///
    /// @param input Recipe input.
    ///
    /// @return `true` if there is a match, `false` if there isn't.
    default boolean containsRecipeA(TypedInstance<HOLDER_A> input) {
        return getMSRecipeType().getInputCache().containsInputA(getHandlerWorld(), input);
    }

    /// Checks if there is a matching recipe of type [#getMSRecipeType()] that has the given input.
    ///
    /// @param input Recipe input.
    ///
    /// @return `true` if there is a match, `false` if there isn't.
    default boolean containsRecipeB(TypedInstance<HOLDER_B> input) {
        return getMSRecipeType().getInputCache().containsInputB(getHandlerWorld(), input);
    }

    /// Finds the first recipe for the type of recipe we handle ([#getMSRecipeType()]) by looking up the given inputs against the recipe type's input cache.
    ///
    /// @param inputA Recipe input a.
    /// @param inputB Recipe input b.
    ///
    /// @return Recipe matching the given inputs, or `null` if no recipe matches.
    @Nullable
    default RECIPE findFirstRecipe(INPUT_A inputA, INPUT_B inputB) {
        return getMSRecipeType().getInputCache().findFirstRecipe(getHandlerWorld(), inputA, inputB);
    }

    /// Finds the first recipe for the type of recipe we handle ([#getMSRecipeType()]) by looking up the given inputs against the recipe type's input cache.
    ///
    /// @param inputAHandler Input handler to grab the first recipe input from.
    /// @param inputBHandler Input handler to grab the second recipe input from.
    ///
    /// @return Recipe matching the given inputs, or `null` if no recipe matches.
    @Nullable
    default RECIPE findFirstRecipe(IInputHandler<HOLDER_A, INPUT_A> inputAHandler, IInputHandler<HOLDER_B, INPUT_B> inputBHandler) {
        return findFirstRecipe(inputAHandler.getInput(), inputBHandler.getInput());
    }

    /// Helper interface to make the generics that we have to pass to [IMSDoubleRecipeLookupHandler] not as messy.
    interface DoubleItemRecipeLookupHandler<RECIPE extends MekanismRecipe<?> & BiPredicate<ItemStack, ItemStack>> extends
          IMSDoubleRecipeLookupHandler<Item, ItemStack, Item, ItemStack, RECIPE, DoubleItem<RECIPE>> {
    }

    /// Helper interface to make the generics that we have to pass to [IMSDoubleRecipeLookupHandler] not as messy.
    interface ItemFluidRecipeLookupHandler<RECIPE extends MekanismRecipe<?> & BiPredicate<ItemStack, FluidStack>> extends
          IMSDoubleRecipeLookupHandler<Item, ItemStack, Fluid, FluidStack, RECIPE, ItemFluid<RECIPE>> {
    }

    /// Helper interface to make the generics that we have to pass to [IMSDoubleRecipeLookupHandler] not as messy.
    interface ItemChemicalRecipeLookupHandler<RECIPE extends MekanismRecipe<?> & BiPredicate<ItemStack, ChemicalStack>> extends
          IMSDoubleRecipeLookupHandler<Item, ItemStack, Chemical, ChemicalStack, RECIPE, ItemChemical<RECIPE>> {
    }

    /// Helper interface to make the generics that we have to pass to [IMSDoubleRecipeLookupHandler] not as messy.
    interface FluidChemicalRecipeLookupHandler<RECIPE extends MekanismRecipe<?> & BiPredicate<FluidStack, ChemicalStack>> extends
          IMSDoubleRecipeLookupHandler<Fluid, FluidStack, Chemical, ChemicalStack, RECIPE, FluidChemical<RECIPE>> {
    }
}