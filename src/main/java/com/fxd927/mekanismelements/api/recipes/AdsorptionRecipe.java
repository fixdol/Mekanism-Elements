package com.fxd927.mekanismelements.api.recipes;

import mekanism.api.chemical.ChemicalStack;
import mekanism.api.chemical.merged.BoxedChemicalStack;
import mekanism.api.recipes.MekanismRecipe;
import mekanism.api.recipes.ingredients.FluidStackIngredient;
import mekanism.api.recipes.ingredients.ItemStackIngredient;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.BiPredicate;

public abstract class AdsorptionRecipe extends MekanismRecipe implements BiPredicate<@NotNull ItemStack, @NotNull FluidStack> {
    private final ItemStackIngredient itemInput;
    private final FluidStackIngredient fluidInput;
    private final BoxedChemicalStack output;

    /**
     * @param id        Recipe name.
     * @param itemInput Item input.
     * @param fluidInput  Gas input.
     * @param output    Output.
     */
    public AdsorptionRecipe(ResourceLocation id, ItemStackIngredient itemInput, FluidStackIngredient fluidInput, ChemicalStack<?> output) {
        super(id);
        this.itemInput = Objects.requireNonNull(itemInput, "Item input cannot be null.");
        this.fluidInput = Objects.requireNonNull(fluidInput, "Fluid input cannot be null.");
        Objects.requireNonNull(output, "Output cannot be null.");
        if (output.isEmpty()) {
            throw new IllegalArgumentException("Output cannot be empty.");
        }
        this.output = BoxedChemicalStack.box(output.copy());
    }

    /**
     * Gets the input item ingredient.
     */
    public ItemStackIngredient getItemInput() {
        return itemInput;
    }

    /**
     * Gets the input gas ingredient.
     */
    public FluidStackIngredient getFluidInput() {
        return fluidInput;
    }

    /**
     * Gets a new output based on the given inputs.
     *
     * @param inputItem Specific item input.
     * @param inputGas  Specific gas input.
     * @return New output.
     * @apiNote While Mekanism does not currently make use of the inputs, it is important to support it and pass the proper value in case any addons define input based
     * outputs where things like NBT may be different.
     * @implNote The passed in inputs should <strong>NOT</strong> be modified.
     */
    @Contract(value = "_, _ -> new", pure = true)
    public BoxedChemicalStack getOutput(ItemStack inputItem, FluidStack inputGas) {
        return output.copy();
    }

    @Override
    public boolean test(ItemStack itemStack, FluidStack fluidStack) {
        return itemInput.test(itemStack) && fluidInput.test(fluidStack);
    }

    /**
     * For JEI, gets the output representations to display.
     *
     * @return Representation of the output, <strong>MUST NOT</strong> be modified.
     */
    public List<BoxedChemicalStack> getOutputDefinition() {
        return Collections.singletonList(output);
    }

    @Override
    public boolean isIncomplete() {
        return itemInput.hasNoMatchingInstances() || fluidInput.hasNoMatchingInstances();
    }

    @Override
    public void write(FriendlyByteBuf buffer) {
        itemInput.write(buffer);
        fluidInput.write(buffer);
        buffer.writeEnum(output.getChemicalType());
        output.getChemicalStack().writeToPacket(buffer);
    }
}
