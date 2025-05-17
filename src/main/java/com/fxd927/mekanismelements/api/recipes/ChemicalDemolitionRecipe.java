package com.fxd927.mekanismelements.api.recipes;

import mekanism.api.annotations.NothingNullByDefault;
import mekanism.api.chemical.gas.GasStack;
import mekanism.api.recipes.MekanismRecipe;
import mekanism.api.recipes.ingredients.ChemicalStackIngredient;
import mekanism.api.recipes.ingredients.ItemStackIngredient;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.BiPredicate;

@NothingNullByDefault
public abstract class ChemicalDemolitionRecipe extends MekanismRecipe implements BiPredicate<@NotNull ItemStack, @NotNull GasStack> {
    private final ItemStackIngredient itemInput;
    private final ChemicalStackIngredient.GasStackIngredient gasInput;
    private final ItemStack firstOutput;
    private final ItemStack secondOutput;

    /**
     * @param id        Recipe name.
     * @param itemInput Item input.
     * @param gasInput  Gas input.
     * @param firstOutput    Output.
     */
    public ChemicalDemolitionRecipe(ResourceLocation id, ItemStackIngredient itemInput, ChemicalStackIngredient.GasStackIngredient gasInput, ItemStack firstOutput, ItemStack secondOutput) {
        super(id);
        this.itemInput = Objects.requireNonNull(itemInput, "Item input cannot be null.");
        this.gasInput = Objects.requireNonNull(gasInput, "Gas input cannot be null.");
        Objects.requireNonNull(firstOutput, "Output cannot be null.");
        if (firstOutput.isEmpty()) {
            throw new IllegalArgumentException("Output cannot be empty.");
        }
        if (secondOutput.isEmpty()) {
            throw new IllegalArgumentException("Output cannot be empty.");
        }
        this.firstOutput = firstOutput.copy();
        this.secondOutput = secondOutput.copy();
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
    public ChemicalStackIngredient.GasStackIngredient getGasInput() {
        return gasInput;
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
    public ItemStack getFirstOutput(ItemStack inputItem, GasStack inputGas) {
        return firstOutput.copy();
    }
    @Contract(value = "_, _ -> new", pure = true)
    public ItemStack getSecondOutput(ItemStack inputItem, GasStack inputGas) {
        return secondOutput.copy();
    }

    @Override
    public boolean test(ItemStack itemStack, GasStack gasStack) {
        return itemInput.test(itemStack) && gasInput.test(gasStack);
    }

    /**
     * For JEI, gets the output representations to display.
     *
     * @return Representation of the output, <strong>MUST NOT</strong> be modified.
     */
    public List<ItemStack> getFirstOutputDefinition() {
        return Collections.singletonList(firstOutput);
    }

    public List<ItemStack> getSecondOutputDefinition() {
        return Collections.singletonList(secondOutput);
    }

    @Override
    public boolean isIncomplete() {
        return itemInput.hasNoMatchingInstances() || gasInput.hasNoMatchingInstances();
    }

    @Override
    public void write(FriendlyByteBuf buffer) {
        itemInput.write(buffer);
        gasInput.write(buffer);
        buffer.writeItem(firstOutput);
        buffer.writeItem(secondOutput);    }
}
