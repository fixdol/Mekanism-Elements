package fixdol.mekanismelements.api.recipes.cache;

import fixdol.mekanismelements.api.recipes.AdsorptionRecipe;
import mekanism.api.chemical.ChemicalStackTemplate;
import mekanism.api.recipes.cache.CachedRecipe;
import mekanism.api.recipes.inputs.IInputHandler;
import mekanism.api.recipes.outputs.IOutputHandler;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.transfer.transaction.TransactionContext;
import org.jspecify.annotations.Nullable;

import java.util.Objects;
import java.util.function.BooleanSupplier;
import java.util.function.IntSupplier;

public class AdsorptionCachedRecipe extends CachedRecipe<AdsorptionRecipe> {
    private final IOutputHandler<ChemicalStackTemplate> outputHandler;
    private final IInputHandler<Item, ItemStack> itemInputHandler;
    private final IInputHandler<Fluid, FluidStack> fluidInputHandler;
    private final IntSupplier fluidUsage;
    private int fluidUsageMultiplier;

    private ItemStack recipeItem = ItemStack.EMPTY;
    private FluidStack recipeFluid = FluidStack.EMPTY;
    @Nullable
    private ChemicalStackTemplate output;

    /**
     * @param recipe            Recipe.
     * @param recheckAllErrors  Returns {@code true} if processing should be continued even if an error is hit in order to gather all the errors. It is recommended to not
     *                          do this every tick or if there is no one viewing recipes.
     * @param itemInputHandler  Item input handler.
     * @param fluidInputHandler Fluid input handler.
     * @param fluidUsage        Fluid usage multiplier.
     * @param outputHandler     Output handler.
     */
    public AdsorptionCachedRecipe(AdsorptionRecipe recipe, BooleanSupplier recheckAllErrors, IInputHandler<Item, ItemStack> itemInputHandler,
                                  IInputHandler<Fluid, FluidStack> fluidInputHandler, IntSupplier fluidUsage, IOutputHandler<ChemicalStackTemplate> outputHandler) {
        super(recipe, recheckAllErrors);
        this.itemInputHandler = Objects.requireNonNull(itemInputHandler, "Item input handler cannot be null.");
        this.fluidInputHandler = Objects.requireNonNull(fluidInputHandler, "Fluid input handler cannot be null.");
        this.fluidUsage = Objects.requireNonNull(fluidUsage, "Fluid usage cannot be null.");
        this.outputHandler = Objects.requireNonNull(outputHandler, "Output handler cannot be null.");
    }

    @Override
    protected void setupVariableValues() {
        fluidUsageMultiplier = Math.max(fluidUsage.getAsInt(), 0);
    }

    @Override
    protected void calculateOperationsThisTick(OperationTracker tracker) {
        super.calculateOperationsThisTick(tracker);
        if (tracker.shouldContinueChecking()) {
            recipeItem = itemInputHandler.getRecipeInput(recipe.getItemInput());
            if (recipeItem.isEmpty()) {
                tracker.mismatchedRecipe();
            } else {
                recipeFluid = fluidInputHandler.getRecipeInput(recipe.getFluidInput());
                if (recipeFluid.isEmpty()) {
                    tracker.updateOperations(0);
                    if (!tracker.shouldContinueChecking()) {
                        return;
                    }
                }
                itemInputHandler.calculateOperationsCanSupport(tracker, recipeItem);
                if (!recipeFluid.isEmpty() && tracker.shouldContinueChecking()) {
                    fluidInputHandler.calculateOperationsCanSupport(tracker, recipeFluid, fluidUsageMultiplier);
                    if (tracker.shouldContinueChecking()) {
                        output = recipe.getOutput(recipeItem, recipeFluid);
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
            FluidStack fluidStack = fluidInputHandler.getInput();
            if (!fluidStack.isEmpty() && recipe.test(itemInput, fluidStack)) {
                FluidStack recipeFluid = fluidInputHandler.getRecipeInput(recipe.getFluidInput());
                return !recipeFluid.isEmpty() && fluidStack.amount() >= recipeFluid.amount();
            }
        }
        return false;
    }

    @Override
    protected boolean useResources(int operations, TransactionContext transaction) {
        if (!super.useResources(operations, transaction)) {
            return false;
        }
        if (fluidUsageMultiplier <= 0 || recipeFluid.isEmpty()) {
            return true;
        }
        return fluidInputHandler.use(recipeFluid, operations * fluidUsageMultiplier, transaction);
    }

    @Override
    protected boolean finishProcessing(int operations, TransactionContext transaction) {
        //Note: The adsorbent item is not consumed
        return outputHandler.handleOutput(output, operations, transaction);
    }
}
