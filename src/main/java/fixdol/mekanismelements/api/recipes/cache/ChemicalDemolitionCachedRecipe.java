package fixdol.mekanismelements.api.recipes.cache;

import fixdol.mekanismelements.api.recipes.ChemicalDemolitionRecipe;
import mekanism.api.chemical.Chemical;
import mekanism.api.chemical.ChemicalStack;
import mekanism.api.recipes.cache.CachedRecipe;
import mekanism.api.recipes.inputs.IInputHandler;
import mekanism.api.recipes.outputs.IOutputHandler;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.neoforged.neoforge.transfer.transaction.TransactionContext;
import org.jspecify.annotations.Nullable;

import java.util.Objects;
import java.util.function.BooleanSupplier;
import java.util.function.IntSupplier;

public class ChemicalDemolitionCachedRecipe extends CachedRecipe<ChemicalDemolitionRecipe> {
    private final IOutputHandler<ItemStackTemplate> firstOutputHandler;
    private final IOutputHandler<ItemStackTemplate> secondOutputHandler;
    private final IInputHandler<Item, ItemStack> itemInputHandler;
    private final IInputHandler<Chemical, ChemicalStack> gasInputHandler;
    private final IntSupplier gasUsage;
    private int gasUsageMultiplier;

    private ItemStack recipeItem = ItemStack.EMPTY;
    private ChemicalStack recipeGas = ChemicalStack.EMPTY;
    @Nullable
    private ItemStackTemplate firstOutput;
    @Nullable
    private ItemStackTemplate secondOutput;

    /**
     * @param recipe              Recipe.
     * @param recheckAllErrors    Returns {@code true} if processing should be continued even if an error is hit in order to gather all the errors. It is recommended to not
     *                            do this every tick or if there is no one viewing recipes.
     * @param itemInputHandler    Item input handler.
     * @param gasInputHandler     Chemical input handler.
     * @param gasUsage            Chemical usage multiplier.
     * @param firstOutputHandler  First output handler.
     * @param secondOutputHandler Second output handler.
     */
    public ChemicalDemolitionCachedRecipe(ChemicalDemolitionRecipe recipe, BooleanSupplier recheckAllErrors, IInputHandler<Item, ItemStack> itemInputHandler,
                                          IInputHandler<Chemical, ChemicalStack> gasInputHandler, IntSupplier gasUsage, IOutputHandler<ItemStackTemplate> firstOutputHandler,
                                          IOutputHandler<ItemStackTemplate> secondOutputHandler) {
        super(recipe, recheckAllErrors);
        this.itemInputHandler = Objects.requireNonNull(itemInputHandler, "Item input handler cannot be null.");
        this.gasInputHandler = Objects.requireNonNull(gasInputHandler, "Chemical input handler cannot be null.");
        this.gasUsage = Objects.requireNonNull(gasUsage, "Chemical usage cannot be null.");
        this.firstOutputHandler = Objects.requireNonNull(firstOutputHandler, "First output handler cannot be null.");
        this.secondOutputHandler = Objects.requireNonNull(secondOutputHandler, "Second output handler cannot be null.");
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
                        firstOutput = recipe.getFirstOutput(recipeItem, recipeGas);
                        secondOutput = recipe.getSecondOutput(recipeItem, recipeGas);
                        firstOutputHandler.calculateOperationsCanSupport(tracker, firstOutput);
                        secondOutputHandler.calculateOperationsCanSupport(tracker, secondOutput);
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
            if (!gasStack.isEmpty() && recipe.test(itemInput, gasStack)) {
                ChemicalStack recipeGas = gasInputHandler.getRecipeInput(recipe.getGasInput());
                return !recipeGas.isEmpty() && gasStack.amount() >= recipeGas.amount();
            }
        }
        return false;
    }

    @Override
    protected boolean finishProcessing(int operations, TransactionContext transaction) {
        if (firstOutput == null || secondOutput == null) {
            return false;
        }
        //Note: The chemical is only consumed once the recipe finishes
        return itemInputHandler.use(recipeItem, operations, transaction) &&
               gasInputHandler.use(recipeGas, operations * gasUsageMultiplier, transaction) &&
               firstOutputHandler.handleOutput(firstOutput, operations, transaction) &&
               secondOutputHandler.handleOutput(secondOutput, operations, transaction);
    }
}
