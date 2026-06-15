package com.fxd927.mekanismelements.api.recipes;

import mekanism.api.chemical.ChemicalStack;
import mekanism.api.recipes.MekanismRecipe;
import mekanism.api.recipes.vanilla_input.FluidRecipeInput;
import mekanism.api.recipes.ingredients.FluidStackIngredient;
import mekanism.api.recipes.ingredients.ItemStackIngredient;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.fluids.FluidStack;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.BiPredicate;

public abstract class AdsorptionRecipe extends MekanismRecipe<FluidRecipeInput> implements BiPredicate<@NotNull ItemStack, @NotNull FluidStack> {
    private final ItemStackIngredient itemInput;
    private final FluidStackIngredient fluidInput;
    private final ChemicalStack output;

    /**
     * @param itemInput Item input.
     * @param fluidInput  Chemical input.
     * @param output    Output.
     */
    public AdsorptionRecipe(ItemStackIngredient itemInput, FluidStackIngredient fluidInput, ChemicalStack output) {
        super();
        this.itemInput = Objects.requireNonNull(itemInput, "Item input cannot be null.");
        this.fluidInput = Objects.requireNonNull(fluidInput, "Fluid input cannot be null.");
        Objects.requireNonNull(output, "Output cannot be null.");
        if (output.isEmpty()) {
            throw new IllegalArgumentException("Output cannot be empty.");
        }
        this.output = output.copy();
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
    public FluidStackIngredient getFluidInput() {
        return fluidInput;
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
    public ChemicalStack getOutput(ItemStack inputItem, FluidStack inputGas) {
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
    public List<ChemicalStack> getOutputDefinition() {
        return Collections.singletonList(output);
    }

    @Override
    public boolean isIncomplete() {
        return itemInput.hasNoMatchingInstances() || fluidInput.hasNoMatchingInstances();
    }

    public void write(RegistryFriendlyByteBuf buffer) {
        ItemStackIngredient.STREAM_CODEC.encode(buffer, itemInput);
        FluidStackIngredient.STREAM_CODEC.encode(buffer, fluidInput);
        ChemicalStack.STREAM_CODEC.encode(buffer, output);
    }
}

