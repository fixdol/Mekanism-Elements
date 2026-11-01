package fixdol.mekanismelements.api.recipes;

import mekanism.api.chemical.Chemical;
import mekanism.api.chemical.ChemicalStack;
import mekanism.api.recipes.TwoInputMekRecipe;
import mekanism.api.recipes.ingredients.ChemicalStackIngredient;
import mekanism.api.recipes.ingredients.ItemStackIngredient;
import mekanism.api.recipes.vanilla_input.SingleItemChemicalRecipeInput;
import net.minecraft.core.TypedInstance;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Contract;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public abstract class ChemicalDemolitionRecipe extends TwoInputMekRecipe<Item, ItemStack, ItemStackIngredient, Chemical, ChemicalStack, ChemicalStackIngredient, SingleItemChemicalRecipeInput, ItemStackTemplate> {
    private final ItemStackIngredient itemInput;
    private final ChemicalStackIngredient gasInput;
    private final ItemStackTemplate firstOutput;
    private final ItemStackTemplate secondOutput;

    /**
     * @param itemInput    Item input.
     * @param gasInput     Chemical input.
     * @param firstOutput  First output.
     * @param secondOutput Second output.
     */
    public ChemicalDemolitionRecipe(ItemStackIngredient itemInput, ChemicalStackIngredient gasInput, ItemStackTemplate firstOutput, ItemStackTemplate secondOutput) {
        super();
        this.itemInput = Objects.requireNonNull(itemInput, "Item input cannot be null.");
        this.gasInput = Objects.requireNonNull(gasInput, "Chemical input cannot be null.");
        this.firstOutput = Objects.requireNonNull(firstOutput, "First output cannot be null.");
        this.secondOutput = Objects.requireNonNull(secondOutput, "Second output cannot be null.");
    }

    private Identifier id;

    public void setId(Identifier id) {
        this.id = id;
    }

    public Identifier getId() {
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

    @Override
    public ItemStackIngredient getInputA() {
        return itemInput;
    }

    @Override
    public ChemicalStackIngredient getInputB() {
        return gasInput;
    }

    /**
     * Gets the first output based on the given inputs.
     *
     * @param inputItem Specific item input.
     * @param inputGas  Specific chemical input.
     * @return Output template.
     */
    @Contract(pure = true)
    public ItemStackTemplate getFirstOutput(TypedInstance<Item> inputItem, TypedInstance<Chemical> inputGas) {
        return firstOutput;
    }

    @Contract(pure = true)
    public ItemStackTemplate getSecondOutput(TypedInstance<Item> inputItem, TypedInstance<Chemical> inputGas) {
        return secondOutput;
    }

    /**
     * The main output of this recipe, equal to {@link #getFirstOutput(TypedInstance, TypedInstance)}.
     */
    @Override
    @Contract(pure = true)
    public ItemStackTemplate getOutput(TypedInstance<Item> inputItem, TypedInstance<Chemical> inputGas) {
        return firstOutput;
    }

    public ItemStackTemplate getFirstOutputRaw() {
        return firstOutput;
    }

    public ItemStackTemplate getSecondOutputRaw() {
        return secondOutput;
    }

    @Override
    public boolean matches(SingleItemChemicalRecipeInput input, Level level) {
        return !isIncomplete() && test(input.item(), input.chemical());
    }

    /**
     * For JEI, gets the output representations to display.
     *
     * @return Representation of the output, <strong>MUST NOT</strong> be modified.
     */
    public List<ItemStackTemplate> getFirstOutputDefinition() {
        return Collections.singletonList(firstOutput);
    }

    public List<ItemStackTemplate> getSecondOutputDefinition() {
        return Collections.singletonList(secondOutput);
    }

    @Override
    public List<ItemStackTemplate> getOutputDefinition() {
        return List.of(firstOutput, secondOutput);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChemicalDemolitionRecipe other = (ChemicalDemolitionRecipe) o;
        return itemInput.equals(other.itemInput) && gasInput.equals(other.gasInput) && firstOutput.equals(other.firstOutput) && secondOutput.equals(other.secondOutput);
    }

    @Override
    public int hashCode() {
        int result = itemInput.hashCode();
        result = 31 * result + gasInput.hashCode();
        result = 31 * result + firstOutput.hashCode();
        result = 31 * result + secondOutput.hashCode();
        return result;
    }
}
