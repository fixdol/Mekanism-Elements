package fixdol.mekanismelements.api.recipes.cache;

import fixdol.mekanismelements.api.recipes.RadiationIrradiatingRecipe;
import mekanism.api.chemical.Chemical;
import mekanism.api.chemical.ChemicalStack;
import mekanism.api.chemical.ChemicalStackTemplate;
import mekanism.api.recipes.cache.CachedRecipe;
import mekanism.api.recipes.inputs.IInputHandler;
import mekanism.api.recipes.outputs.IOutputHandler;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.transfer.transaction.TransactionContext;
import org.jspecify.annotations.Nullable;

import java.util.Objects;
import java.util.function.BooleanSupplier;
import java.util.function.IntSupplier;

public class RadiationIrradiatingCachedRecipe extends CachedRecipe<RadiationIrradiatingRecipe> {
    private final IOutputHandler<ChemicalStackTemplate> outputHandler;
    private final IInputHandler<Item, ItemStack> itemInputHandler;
    private final IInputHandler<Chemical, ChemicalStack> gasInputHandler;
    private final IntSupplier gasUsage;
    private int gasUsageMultiplier;

    private ItemStack recipeItem = ItemStack.EMPTY;
    private ChemicalStack recipeGas = ChemicalStack.EMPTY;
    @Nullable
    private ChemicalStackTemplate output;

    /**
     * @param recipe           Recipe.
     * @param recheckAllErrors Returns {@code true} if processing should be continued even if an error is hit in order to gather all the errors. It is recommended to not
     *                         do this every tick or if there is no one viewing recipes.
     * @param itemInputHandler Item input handler.
     * @param gasInputHandler  Chemical input handler.
     * @param gasUsage         Chemical usage multiplier.
     * @param outputHandler    Output handler.
     */
    public RadiationIrradiatingCachedRecipe(RadiationIrradiatingRecipe recipe, BooleanSupplier recheckAllErrors, IInputHandler<Item, ItemStack> itemInputHandler,
                                            IInputHandler<Chemical, ChemicalStack> gasInputHandler, IntSupplier gasUsage, IOutputHandler<ChemicalStackTemplate> outputHandler) {
        super(recipe, recheckAllErrors);
        this.itemInputHandler = Objects.requireNonNull(itemInputHandler, "Item input handler cannot be null.");
        this.gasInputHandler = Objects.requireNonNull(gasInputHandler, "Chemical input handler cannot be null.");
        this.gasUsage = Objects.requireNonNull(gasUsage, "Chemical usage cannot be null.");
        this.outputHandler = Objects.requireNonNull(outputHandler, "Output handler cannot be null.");
    }

    @Override
    protected void setupVariableValues() {
        gasUsageMultiplier = Math.max(gasUsage.getAsInt(), 0);
    }

    @Override
    protected void calculateOperationsThisTick(OperationTracker tracker) {
        super.calculateOperationsThisTick(tracker);
        if (tracker.shouldContinueChecking()) {
            recipeItem = itemInputHandler.getRecipeInput(recipe.getItemInput());
            if (recipeItem.isEmpty()) {
                tracker.mismatchedRecipe();
            } else {
                recipeGas = gasInputHandler.getRecipeInput(recipe.getGasInput());
                if (recipeGas.isEmpty()) {
                    tracker.updateOperations(0);
                    if (!tracker.shouldContinueChecking()) {
                        return;
                    }
                }
                itemInputHandler.calculateOperationsCanSupport(tracker, recipeItem);
                if (!recipeGas.isEmpty() && tracker.shouldContinueChecking()) {
                    gasInputHandler.calculateOperationsCanSupport(tracker, recipeGas, gasUsageMultiplier);
                    if (tracker.shouldContinueChecking()) {
                        output = recipe.getOutput(recipeItem, recipeGas);
                        outputHandler.calculateOperationsCanSupport(tracker, output);
                    }
                }
            }
        }
    }

    @Override
    public boolean isInputValid() {
        ItemStack itemInput = itemInputHandler.getInput();
        if (!itemInput.isEmpty()) {
            ChemicalStack gasStack = gasInputHandler.getInput();
            //Ensure that we check that we have enough for that the recipe matches *and* also that we have enough for how much we need to use
            if (!gasStack.isEmpty() && recipe.test(itemInput, gasStack)) {
                ChemicalStack recipeGas = gasInputHandler.getRecipeInput(recipe.getGasInput());
                return !recipeGas.isEmpty() && gasStack.amount() >= recipeGas.amount();
            }
        }
        return false;
    }

    @Override
    protected boolean useResources(int operations, TransactionContext transaction) {
        if (!super.useResources(operations, transaction)) {
            return false;
        }
        if (gasUsageMultiplier <= 0 || recipeGas.isEmpty()) {
            return true;
        }
        return gasInputHandler.use(recipeGas, operations * gasUsageMultiplier, transaction);
    }

    @Override
    protected boolean finishProcessing(int operations, TransactionContext transaction) {
        return itemInputHandler.use(recipeItem, operations, transaction) && outputHandler.handleOutput(output, operations, transaction);
    }
}
