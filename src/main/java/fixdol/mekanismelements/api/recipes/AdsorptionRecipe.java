package fixdol.mekanismelements.api.recipes;

import fixdol.mekanismelements.api.recipes.vanilla_input.SingleItemFluidRecipeInput;
import mekanism.api.chemical.ChemicalStackTemplate;
import mekanism.api.recipes.TwoInputMekRecipe;
import mekanism.api.recipes.ingredients.FluidStackIngredient;
import mekanism.api.recipes.ingredients.ItemStackIngredient;
import net.minecraft.core.TypedInstance;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.fluids.FluidStack;
import org.jetbrains.annotations.Contract;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public abstract class AdsorptionRecipe extends TwoInputMekRecipe<Item, ItemStack, ItemStackIngredient, Fluid, FluidStack, FluidStackIngredient, SingleItemFluidRecipeInput, ChemicalStackTemplate> {
    private final ItemStackIngredient itemInput;
    private final FluidStackIngredient fluidInput;
    private final ChemicalStackTemplate output;

    /**
     * @param itemInput  Item input.
     * @param fluidInput Fluid input.
     * @param output     Output.
     */
    public AdsorptionRecipe(ItemStackIngredient itemInput, FluidStackIngredient fluidInput, ChemicalStackTemplate output) {
        super();
        this.itemInput = Objects.requireNonNull(itemInput, "Item input cannot be null.");
        this.fluidInput = Objects.requireNonNull(fluidInput, "Fluid input cannot be null.");
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
     * Gets the input fluid ingredient.
     */
    public FluidStackIngredient getFluidInput() {
        return fluidInput;
    }

    @Override
    public ItemStackIngredient getInputA() {
        return itemInput;
    }

    @Override
    public FluidStackIngredient getInputB() {
        return fluidInput;
    }

    /**
     * Gets a new output based on the given inputs.
     *
     * @param inputItem  Specific item input.
     * @param inputFluid Specific fluid input.
     * @return Output template.
     */
    @Override
    @Contract(pure = true)
    public ChemicalStackTemplate getOutput(TypedInstance<Item> inputItem, TypedInstance<Fluid> inputFluid) {
        return output;
    }

    public ChemicalStackTemplate getOutputRaw() {
        return output;
    }

    @Override
    public boolean matches(SingleItemFluidRecipeInput input, Level level) {
        return !isIncomplete() && test(input.item(), input.fluid());
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
        AdsorptionRecipe other = (AdsorptionRecipe) o;
        return itemInput.equals(other.itemInput) && fluidInput.equals(other.fluidInput) && output.equals(other.output);
    }

    @Override
    public int hashCode() {
        int result = itemInput.hashCode();
        result = 31 * result + fluidInput.hashCode();
        result = 31 * result + output.hashCode();
        return result;
    }
}
