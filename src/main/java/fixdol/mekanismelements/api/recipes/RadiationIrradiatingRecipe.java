package fixdol.mekanismelements.api.recipes;

import mekanism.api.chemical.Chemical;
import mekanism.api.chemical.ChemicalStack;
import mekanism.api.chemical.ChemicalStackTemplate;
import mekanism.api.recipes.TwoInputMekRecipe;
import mekanism.api.recipes.ingredients.ChemicalStackIngredient;
import mekanism.api.recipes.ingredients.ItemStackIngredient;
import mekanism.api.recipes.vanilla_input.SingleItemChemicalRecipeInput;
import net.minecraft.core.TypedInstance;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Contract;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public abstract class RadiationIrradiatingRecipe extends TwoInputMekRecipe<Item, ItemStack, ItemStackIngredient, Chemical, ChemicalStack, ChemicalStackIngredient, SingleItemChemicalRecipeInput, ChemicalStackTemplate> {
    private final ItemStackIngredient itemInput;
    private final ChemicalStackIngredient gasInput;
    private final ChemicalStackTemplate output;

    /**
     * @param itemInput Item input.
     * @param gasInput  Chemical input.
     * @param output    Output.
     */
    public RadiationIrradiatingRecipe(ItemStackIngredient itemInput, ChemicalStackIngredient gasInput, ChemicalStackTemplate output) {
        super();
        this.itemInput = Objects.requireNonNull(itemInput, "Item input cannot be null.");
        this.gasInput = Objects.requireNonNull(gasInput, "Chemical input cannot be null.");
        this.output = Objects.requireNonNull(output, "Output cannot be null.");
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
     * Gets a new output based on the given inputs.
     *
     * @param inputItem Specific item input.
     * @param inputGas  Specific chemical input.
     * @return Output template.
     */
    @Override
    @Contract(pure = true)
    public ChemicalStackTemplate getOutput(TypedInstance<Item> inputItem, TypedInstance<Chemical> inputGas) {
        return output;
    }

    public ChemicalStackTemplate getOutputRaw() {
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
    public List<ChemicalStackTemplate> getOutputDefinition() {
        return Collections.singletonList(output);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RadiationIrradiatingRecipe other = (RadiationIrradiatingRecipe) o;
        return itemInput.equals(other.itemInput) && gasInput.equals(other.gasInput) && output.equals(other.output);
    }

    @Override
    public int hashCode() {
        int result = itemInput.hashCode();
        result = 31 * result + gasInput.hashCode();
        result = 31 * result + output.hashCode();
        return result;
    }
}
