package fixdol.mekanismelements.api.recipes;

import java.util.function.BiPredicate;
import mekanism.api.chemical.Chemical;
import mekanism.api.recipes.ingredients.ChemicalStackIngredient;
import java.util.Collections;
import org.jetbrains.annotations.Contract;
import net.minecraft.network.FriendlyByteBuf;
import mekanism.api.chemical.gas.GasStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import mekanism.api.recipes.ingredients.ItemStackIngredient;
import java.util.List;
import mekanism.common.Mekanism;
import mekanism.api.recipes.MekanismRecipe;
import org.jetbrains.annotations.NotNull;
import java.util.Objects;
import fixdol.mekanismelements.api.recipes.RadiationIrradiatingRecipe;
import net.minecraft.resources.ResourceLocation;



public abstract class RadiationIrradiatingRecipe extends MekanismRecipe implements BiPredicate<@NotNull ItemStack, @NotNull GasStack> {
    private final ItemStackIngredient itemInput;
    private final ChemicalStackIngredient.GasStackIngredient gasInput;
    private final GasStack output;

    /**
     * @param itemInput Item input.
     * @param gasInput  Chemical input.
     * @param output    Output.
     */
    public RadiationIrradiatingRecipe(ResourceLocation id, ItemStackIngredient itemInput, ChemicalStackIngredient.GasStackIngredient gasInput, GasStack output) {
        super(id);
        this.itemInput = Objects.requireNonNull(itemInput, "Item input cannot be null.");
        this.gasInput = Objects.requireNonNull(gasInput, "Chemical input cannot be null.");
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
    public ChemicalStackIngredient.GasStackIngredient getGasInput() {
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
    public GasStack getOutput(ItemStack inputItem, GasStack inputGas) {
        return output.copy();
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
    public List<GasStack> getOutputDefinition() {
        return Collections.singletonList(output);
    }

    @Override
    public boolean isIncomplete() {
        return itemInput.hasNoMatchingInstances() || gasInput.hasNoMatchingInstances();
    }

    @Override
    public void write(FriendlyByteBuf buffer) {
        itemInput.write(buffer);
        gasInput.write(buffer);
        output.writeToPacket(buffer);
    }
}
