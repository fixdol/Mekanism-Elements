package fixdol.mekanismelements.api.recipes;

import java.util.function.BiPredicate;
import java.util.Collections;
import org.jetbrains.annotations.Contract;
import net.minecraft.network.FriendlyByteBuf;
import mekanism.api.chemical.gas.GasStack;
import fixdol.mekanismelements.api.recipes.InfinityOreReprocessingRecipe;
import net.minecraft.world.item.ItemStack;
import mekanism.api.recipes.ingredients.ItemStackIngredient;
import java.util.List;
import mekanism.api.recipes.MekanismRecipe;
import org.jetbrains.annotations.NotNull;
import java.util.Objects;
import net.minecraft.resources.ResourceLocation;

import mekanism.api.recipes.ingredients.ChemicalStackIngredient;



public abstract class InfinityOreReprocessingRecipe extends MekanismRecipe implements
        BiPredicate<@NotNull ItemStack, @NotNull GasStack> {

    private final ItemStackIngredient itemInput;
    private final ChemicalStackIngredient.GasStackIngredient chemicalInput;
    private final ItemStack output;

    /**
     * @param id            Recipe id.
     * @param itemInput     Input de item (la mena "sucia" que se coloca en la maquina).
     * @param chemicalInput Input quimico.
     * @param output        Output.
     */
    public InfinityOreReprocessingRecipe(ResourceLocation id, ItemStackIngredient itemInput, ChemicalStackIngredient.GasStackIngredient chemicalInput, ItemStack output) {
        super(id);
        this.itemInput = Objects.requireNonNull(itemInput, "Item input cannot be null.");
        this.chemicalInput = Objects.requireNonNull(chemicalInput, "Chemical input cannot be null.");
        Objects.requireNonNull(output, "Output cannot be null.");
        if (output.isEmpty()) {
            throw new IllegalArgumentException("Output cannot be empty.");
        }
        this.output = output.copy();
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
    public ChemicalStackIngredient.GasStackIngredient getChemicalInput() {
        return chemicalInput;
    }

    /**
     * Gets a new output based on the given inputs.
     *
     * @param itemInput     Specific item input.
     * @param chemicalInput Specific chemical input.
     * @return New output.
     */
    @Contract(value = "_, _ -> new", pure = true)
    public ItemStack getOutput(ItemStack itemInput, GasStack chemicalInput) {
        return output.copy();
    }

    @Override
    public boolean test(ItemStack itemStack, GasStack chemicalStack) {
        return itemInput.test(itemStack) && chemicalInput.test(chemicalStack);
    }

    /**
     * For JEI, gets the output representations to display.
     *
     * @return Representation of the output, <strong>MUST NOT</strong> be modified.
     */
    public List<ItemStack> getOutputDefinition() {
        return Collections.singletonList(output);
    }

    @Override
    public boolean isIncomplete() {
        return itemInput.hasNoMatchingInstances() || chemicalInput.hasNoMatchingInstances();
    }

    @Override
    public void write(FriendlyByteBuf buffer) {
        itemInput.write(buffer);
        chemicalInput.write(buffer);
        buffer.writeItem(output);
    }
}
