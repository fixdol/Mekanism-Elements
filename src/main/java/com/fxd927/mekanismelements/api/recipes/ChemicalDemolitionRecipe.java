package com.fxd927.mekanismelements.api.recipes;

import mekanism.api.annotations.NothingNullByDefault;
import mekanism.api.chemical.ChemicalStack;
import mekanism.api.recipes.MekanismRecipe;
import mekanism.api.recipes.vanilla_input.ItemChemicalRecipeInput;
import mekanism.api.recipes.ingredients.ChemicalStackIngredient;
import mekanism.api.recipes.ingredients.ItemStackIngredient;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.BiPredicate;

@NothingNullByDefault
public abstract class ChemicalDemolitionRecipe extends MekanismRecipe<ItemChemicalRecipeInput> implements BiPredicate<@NotNull ItemStack, @NotNull ChemicalStack> {
    private final ItemStackIngredient itemInput;
    private final ChemicalStackIngredient gasInput;
    private final ItemStack firstOutput;
    private final ItemStack secondOutput;

    /**
     * @param itemInput Item input.
     * @param gasInput  Chemical input.
     * @param firstOutput    Output.
     */
    public ChemicalDemolitionRecipe(ItemStackIngredient itemInput, ChemicalStackIngredient gasInput, ItemStack firstOutput, ItemStack secondOutput) {
        super();
        this.itemInput = Objects.requireNonNull(itemInput, "Item input cannot be null.");
        this.gasInput = Objects.requireNonNull(gasInput, "Chemical input cannot be null.");
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

    private ResourceLocation id;

    public void setId(ResourceLocation id) {
        this.id = id;
    }

    public ResourceLocation getId() {
        return id;
    }

    /**
     * Gets the input item ingredient.
     */
    public ItemStackIngredient getItemInput() {
        return itemInput;
    }

    /**
     * Gets the input chemical ingredient.
     */
    public ChemicalStackIngredient getGasInput() {
        return gasInput;
    }

    /**
     * Gets a new output based on the given inputs.
     *
     * @param inputItem Specific item input.
     * @param inputGas  Specific chemical input.
     * @return New output.
     * @apiNote While Mekanism does not currently make use of the inputs, it is important to support it and pass the proper value in case any addons define input based
     * outputs where things like NBT may be different.
     * @implNote The passed in inputs should <strong>NOT</strong> be modified.
     */
    @Contract(value = "_, _ -> new", pure = true)
    public ItemStack getFirstOutput(ItemStack inputItem, ChemicalStack inputGas) {
        return firstOutput.copy();
    }
    @Contract(value = "_, _ -> new", pure = true)
    public ItemStack getSecondOutput(ItemStack inputItem, ChemicalStack inputGas) {
        return secondOutput.copy();
    }

    @Override
    public boolean test(ItemStack itemStack, ChemicalStack gasStack) {
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

    public void write(RegistryFriendlyByteBuf buffer) {
        ItemStackIngredient.STREAM_CODEC.encode(buffer, itemInput);
        ChemicalStackIngredient.STREAM_CODEC.encode(buffer, gasInput);
        ItemStack.STREAM_CODEC.encode(buffer, firstOutput);
        ItemStack.STREAM_CODEC.encode(buffer, secondOutput);
    }
}

