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
import org.jspecify.annotations.Nullable;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public abstract class InfinityOreReprocessingRecipe extends TwoInputMekRecipe<Item, ItemStack, ItemStackIngredient, Chemical, ChemicalStack, ChemicalStackIngredient, SingleItemChemicalRecipeInput, ItemStackTemplate> {

    private final ItemStackIngredient itemInput;
    private final ChemicalStackIngredient chemicalInput;
    private final ItemStackTemplate output;

    @Nullable
    private Identifier id;

    /**
     * @param itemInput     Item input (the "dirty" ore placed in the machine).
     * @param chemicalInput Chemical input.
     * @param output        Output.
     */
    public InfinityOreReprocessingRecipe(ItemStackIngredient itemInput, ChemicalStackIngredient chemicalInput, ItemStackTemplate output) {
        super();
        this.itemInput = Objects.requireNonNull(itemInput, "Item input cannot be null.");
        this.chemicalInput = Objects.requireNonNull(chemicalInput, "Chemical input cannot be null.");
        this.output = Objects.requireNonNull(output, "Output cannot be null.");
    }

    public void setId(Identifier id) {
        this.id = id;
    }

    @Nullable
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
    public ChemicalStackIngredient getChemicalInput() {
        return chemicalInput;
    }

    @Override
    public ItemStackIngredient getInputA() {
        return itemInput;
    }

    @Override
    public ChemicalStackIngredient getInputB() {
        return chemicalInput;
    }

    /**
     * Gets a new output based on the given inputs.
     *
     * @param itemInput     Specific item input.
     * @param chemicalInput Specific chemical input.
     * @return Output template.
     */
    @Override
    @Contract(pure = true)
    public ItemStackTemplate getOutput(TypedInstance<Item> itemInput, TypedInstance<Chemical> chemicalInput) {
        return output;
    }

    public ItemStackTemplate getOutputRaw() {
        return output;
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
    @Override
    public List<ItemStackTemplate> getOutputDefinition() {
        return Collections.singletonList(output);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (o == null || getClass() != o.getClass()) {
            return false;
        }
        InfinityOreReprocessingRecipe other = (InfinityOreReprocessingRecipe) o;
        return itemInput.equals(other.itemInput) && chemicalInput.equals(other.chemicalInput) && output.equals(other.output);
    }

    @Override
    public int hashCode() {
        int result = itemInput.hashCode();
        result = 31 * result + chemicalInput.hashCode();
        result = 31 * result + output.hashCode();
        return result;
    }
}
