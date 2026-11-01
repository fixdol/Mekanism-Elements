package fixdol.mekanismelements.api.recipes.vanilla_input;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;
import net.neoforged.neoforge.fluids.FluidStack;
import org.jspecify.annotations.Nullable;

/**
 * Simple implementation of a recipe input of one item and one fluid.
 */
public record SingleItemFluidRecipeInput(ItemStack item, FluidStack fluid) implements RecipeInput {

    @Override
    public ItemStack getItem(int index) {
        if (index != 0) {
            throw new IllegalArgumentException("No item for index " + index);
        }
        return item;
    }

    public FluidStack getFluid(int index) {
        if (index != 0) {
            throw new IllegalArgumentException("No fluid for index " + index);
        }
        return fluid;
    }

    @Override
    public int size() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return item.isEmpty() || fluid.isEmpty();
    }

    @Override
    public boolean equals(@Nullable Object o) {
        if (o == this) {
            return true;
        } else if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SingleItemFluidRecipeInput other = (SingleItemFluidRecipeInput) o;
        return FluidStack.matches(fluid, other.fluid) && ItemStack.matches(item, other.item);
    }

    @Override
    public int hashCode() {
        int hash = FluidStack.hashFluidAndComponents(fluid);
        hash = 31 * hash + fluid.amount();
        hash = 31 * hash + ItemStack.hashItemAndComponents(item);
        hash = 31 * hash + item.count();
        return hash;
    }
}
