package com.fxd927.mekanismelements.common.recipe.lookup;

import com.fxd927.mekanismelements.common.recipe.lookup.cache.MSDoubleInputRecipeCache;
import com.fxd927.mekanismelements.common.recipe.lookup.cache.MSInputRecipeCache;
import mekanism.api.chemical.Chemical;
import mekanism.api.chemical.ChemicalStack;
import mekanism.api.recipes.MekanismRecipe;
import mekanism.api.recipes.inputs.IInputHandler;
import mekanism.common.recipe.lookup.cache.DoubleInputRecipeCache;
import mekanism.common.util.ChemicalUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.fluids.FluidStack;
import org.jetbrains.annotations.Nullable;

import java.util.function.BiPredicate;

public interface IMSDoubleRecipeLookupHandler <INPUT_A, INPUT_B, RECIPE extends MekanismRecipe & BiPredicate<INPUT_A, INPUT_B>,
        INPUT_CACHE extends MSDoubleInputRecipeCache<INPUT_A, ?, INPUT_B, ?, RECIPE, ?, ?>> extends IMSRecipeLookupHandler.IMSRecipeTypedLookupHandler<RECIPE, INPUT_CACHE> {
    /**
     * Checks if there is a matching recipe of type {@link #getMSRecipeType()} that has the given inputs.
     *
     * @param inputA Recipe input a.
     * @param inputB Recipe input b.
     *
     * @return {@code true} if there is a match, {@code false} if there isn't.
     *
     * @apiNote See {@link DoubleInputRecipeCache#containsInputAB(Level, Object, Object)} and {@link DoubleInputRecipeCache#containsInputBA(Level, Object, Object)} for
     * more details about when this method should be called versus when {@link #containsRecipeBA(Object, Object)} should be called.
     */
    default boolean containsRecipeAB(INPUT_A inputA, INPUT_B inputB) {
        return getMSRecipeType().getInputCache().containsInputAB(getHandlerWorld(), inputA, inputB);
    }

    /**
     * Checks if there is a matching recipe of type {@link #getMSRecipeType()} that has the given inputs.
     *
     * @param inputA Recipe input a.
     * @param inputB Recipe input b.
     *
     * @return {@code true} if there is a match, {@code false} if there isn't.
     *
     * @apiNote See {@link MSDoubleInputRecipeCache#containsInputAB(Level, Object, Object)} and {@link DoubleInputRecipeCache#containsInputBA(Level, Object, Object)} for
     * more details about when this method should be called versus when {@link #containsRecipeAB(Object, Object)} should be called.
     */
    default boolean containsRecipeBA(INPUT_A inputA, INPUT_B inputB) {
        return getMSRecipeType().getInputCache().containsInputBA(getHandlerWorld(), inputA, inputB);
    }

    /**
     * Checks if there is a matching recipe of type {@link #getMSRecipeType()} that has the given input.
     *
     * @param input Recipe input.
     *
     * @return {@code true} if there is a match, {@code false} if there isn't.
     */
    default boolean containsRecipeA(INPUT_A input) {
        return getMSRecipeType().getInputCache().containsInputA(getHandlerWorld(), input);
    }

    /**
     * Checks if there is a matching recipe of type {@link #getMSRecipeType()} that has the given input.
     *
     * @param input Recipe input.
     *
     * @return {@code true} if there is a match, {@code false} if there isn't.
     */
    default boolean containsRecipeB(INPUT_B input) {
        return getMSRecipeType().getInputCache().containsInputB(getHandlerWorld(), input);
    }

    /**
     * Finds the first recipe for the type of recipe we handle ({@link #getMSRecipeType()}) by looking up the given inputs against the recipe type's input cache.
     *
     * @param inputA Recipe input a.
     * @param inputB Recipe input b.
     *
     * @return Recipe matching the given inputs, or {@code null} if no recipe matches.
     */
    @Nullable
    default RECIPE findFirstRecipe(INPUT_A inputA, INPUT_B inputB) {
        return getMSRecipeType().getInputCache().findFirstRecipe(getHandlerWorld(), inputA, inputB);
    }

    /**
     * Finds the first recipe for the type of recipe we handle ({@link #getMSRecipeType()}) by looking up the given inputs against the recipe type's input cache.
     *
     * @param inputAHandler Input handler to grab the first recipe input from.
     * @param inputBHandler Input handler to grab the second recipe input from.
     *
     * @return Recipe matching the given inputs, or {@code null} if no recipe matches.
     */
    @Nullable
    default RECIPE findFirstRecipe(IInputHandler<INPUT_A> inputAHandler, IInputHandler<INPUT_B> inputBHandler) {
        return findFirstRecipe(inputAHandler.getInput(), inputBHandler.getInput());
    }

    /**
     * Helper interface to make the generics that we have to pass to {@link IMSDoubleRecipeLookupHandler} not as messy.
     */
    interface DoubleItemRecipeLookupHandler<RECIPE extends MekanismRecipe & BiPredicate<ItemStack, ItemStack>> extends
            IMSDoubleRecipeLookupHandler<ItemStack, ItemStack, RECIPE, MSInputRecipeCache.DoubleItem<RECIPE>> {
    }

    interface ItemFluidRecipeLookupHandler<RECIPE extends MekanismRecipe & BiPredicate<ItemStack, FluidStack>> extends
            IMSDoubleRecipeLookupHandler<ItemStack, FluidStack, RECIPE, MSInputRecipeCache.ItemFluid<RECIPE>> {
    }

    /**
     * Helper interface to make the generics that we have to pass to {@link IMSDoubleRecipeLookupHandler} not as messy, and reduce the duplicate code in the other chemical
     * based helper interfaces.
     */
    interface ObjectChemicalRecipeLookupHandler<INPUT, CHEMICAL extends Chemical<CHEMICAL>, STACK extends ChemicalStack<CHEMICAL>, RECIPE extends MekanismRecipe &
            BiPredicate<INPUT, STACK>, INPUT_CACHE extends MSDoubleInputRecipeCache<INPUT, ?, STACK, ?, RECIPE, ?, ?>> extends
            IMSDoubleRecipeLookupHandler<INPUT, STACK, RECIPE, INPUT_CACHE> {

        /**
         * Helper wrapper to convert a chemical to a chemical stack and pass it to {@link #containsRecipeBA(Object, Object)} to make validity predicates easier and
         * cleaner.
         */
        default boolean containsRecipeBA(INPUT inputA, CHEMICAL inputB) {
            return containsRecipeBA(inputA, ChemicalUtil.withAmount(inputB, 1));
        }

        /**
         * Helper wrapper to convert a chemical to a chemical stack and pass it to {@link #containsRecipeB(Object)} to make validity predicates easier and cleaner.
         */
        default boolean containsRecipeB(CHEMICAL input) {
            return containsRecipeB(ChemicalUtil.withAmount(input, 1));
        }
    }

    /**
     * Helper interface to make the generics that we have to pass to {@link IMSDoubleRecipeLookupHandler} not as messy.
     */
    interface ItemChemicalRecipeLookupHandler<CHEMICAL extends Chemical<CHEMICAL>, STACK extends ChemicalStack<CHEMICAL>, RECIPE extends MekanismRecipe &
            BiPredicate<ItemStack, STACK>> extends IMSDoubleRecipeLookupHandler.ObjectChemicalRecipeLookupHandler<ItemStack, CHEMICAL, STACK, RECIPE, MSInputRecipeCache.ItemChemical<CHEMICAL, STACK, RECIPE>> {
    }
    /**
     * Helper interface to make the generics that we have to pass to {@link IMSDoubleRecipeLookupHandler} not as messy.
     */
    interface FluidChemicalRecipeLookupHandler<CHEMICAL extends Chemical<CHEMICAL>, STACK extends ChemicalStack<CHEMICAL>, RECIPE extends MekanismRecipe &
            BiPredicate<FluidStack, STACK>> extends IMSDoubleRecipeLookupHandler.ObjectChemicalRecipeLookupHandler<FluidStack, CHEMICAL, STACK, RECIPE, MSInputRecipeCache.FluidChemical<CHEMICAL, STACK, RECIPE>> {
    }
}
