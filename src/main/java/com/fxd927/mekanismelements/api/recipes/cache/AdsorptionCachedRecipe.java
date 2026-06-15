package com.fxd927.mekanismelements.api.recipes.cache;

import com.fxd927.mekanismelements.api.recipes.AdsorptionRecipe;
import mekanism.api.chemical.ChemicalStack;
import mekanism.api.recipes.cache.CachedRecipe;
import mekanism.api.recipes.inputs.IInputHandler;
import mekanism.api.recipes.outputs.IOutputHandler;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.fluids.FluidStack;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.function.BooleanSupplier;
import java.util.function.IntSupplier;

public class AdsorptionCachedRecipe extends CachedRecipe<AdsorptionRecipe> {
    private static int diagCounter = 0;
    private final IOutputHandler<ChemicalStack> outputHandler;
    private final IInputHandler<@NotNull ItemStack> itemInputHandler;
    private final IInputHandler<@NotNull FluidStack> fluidInputHandler;
    private final IntSupplier fluidUsage;
    private int fluidUsageMultiplier;

    private ItemStack recipeItem = ItemStack.EMPTY;
    private FluidStack recipeFluid = FluidStack.EMPTY;
    private ChemicalStack output = ChemicalStack.EMPTY;

    /**
     * @param recipe            Recipe.
     * @param recheckAllErrors  Returns {@code true} if processing should be continued even if an error is hit in order to gather all the errors. It is recommended to not
     *                          do this every tick or if there is no one viewing recipes.
     * @param itemInputHandler  Item input handler.
     * @param fluidInputHandler Chemical input handler.
     * @param fluidUsage        Chemical usage multiplier.
     * @param outputHandler     Output handler.
     */
    public AdsorptionCachedRecipe(AdsorptionRecipe recipe, BooleanSupplier recheckAllErrors, IInputHandler<@NotNull ItemStack> itemInputHandler,
                                  IInputHandler<@NotNull FluidStack> fluidInputHandler, IntSupplier fluidUsage, IOutputHandler<ChemicalStack> outputHandler) {
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
                return !recipeFluid.isEmpty() && fluidStack.getAmount() >= recipeFluid.getAmount();
            }
        }
        return false;
    }

    @Override
    protected void useResources(int operations) {
        super.useResources(operations);
        if (fluidUsageMultiplier <= 0) {
            return;
        } else if (recipeFluid.isEmpty()) {
            return;
        }
        fluidInputHandler.use(recipeFluid, operations * fluidUsageMultiplier);
    }

    @Override
    protected void finishProcessing(int operations) {
        if (!output.isEmpty()) {
            outputHandler.handleOutput(output, operations);
        }
    }
}