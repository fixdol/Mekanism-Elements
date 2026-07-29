package fixdol.mekanismelements.api.recipes;

import mekanism.api.chemical.ChemicalStack;
import mekanism.api.recipes.MekanismRecipe;
import mekanism.api.recipes.ingredients.ChemicalStackIngredient;
import mekanism.api.recipes.ingredients.ItemStackIngredient;
import mekanism.api.recipes.vanilla_input.FluidRecipeInput;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.BiPredicate;


public abstract class InfinityOreReprocessingRecipe extends MekanismRecipe<FluidRecipeInput> implements
        BiPredicate<@NotNull ItemStack, @NotNull ChemicalStack> {

    private final ItemStackIngredient itemInput;
    private final ChemicalStackIngredient chemicalInput;
    private final ItemStack output;

    @org.jetbrains.annotations.Nullable
    private ResourceLocation id;

    /**
     * @param itemInput     Input de ítem (la mena "sucia" que se coloca en la máquina).
     * @param chemicalInput Input químico.
     * @param output        Output.
     */
    public InfinityOreReprocessingRecipe(ItemStackIngredient itemInput, ChemicalStackIngredient chemicalInput, ItemStack output) {
        super();
        this.itemInput = Objects.requireNonNull(itemInput, "Item input cannot be null.");
        this.chemicalInput = Objects.requireNonNull(chemicalInput, "Chemical input cannot be null.");
        Objects.requireNonNull(output, "Output cannot be null.");
        if (output.isEmpty()) {
            throw new IllegalArgumentException("Output cannot be empty.");
        }
        this.output = output.copy();
    }

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
    public ChemicalStackIngredient getChemicalInput() {
        return chemicalInput;
    }

    /**
     * Gets a new output based on the given inputs.
     *
     * @param itemInput     Specific item input.
     * @param chemicalInput Specific chemical input.
     * @return New output.
     * @apiNote While Mekanism does not currently make use of the inputs, it is important to support them and pass the proper values in case any addons define
     * input based outputs where things like NBT may be different.
     * @implNote The passed in inputs should <strong>NOT</strong> be modified.
     */
    @Contract(value = "_, _ -> new", pure = true)
    public ItemStack getOutput(ItemStack itemInput, ChemicalStack chemicalInput) {
        return output.copy();
    }

    @Override
    public boolean test(ItemStack itemStack, ChemicalStack chemicalStack) {
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

    public void write(RegistryFriendlyByteBuf buffer) {
        ItemStackIngredient.STREAM_CODEC.encode(buffer, itemInput);
        ChemicalStackIngredient.STREAM_CODEC.encode(buffer, chemicalInput);
        ItemStack.STREAM_CODEC.encode(buffer, output);
    }
}