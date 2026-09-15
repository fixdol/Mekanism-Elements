package fixdol.mekanismelements.common.inventory.slot;

import java.util.function.BiPredicate;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import mekanism.api.annotations.NothingNullByDefault;
import org.jetbrains.annotations.Nullable;

import mekanism.api.AutomationType;
import mekanism.api.IContentsListener;
import mekanism.common.inventory.slot.BasicInventorySlot;

import java.util.function.Predicate;

@NothingNullByDefault
public class MSBasicInventorySlot extends BasicInventorySlot {

    protected MSBasicInventorySlot(BiPredicate<@NotNull ItemStack, @NotNull AutomationType> canExtract, BiPredicate<@NotNull ItemStack, @NotNull AutomationType> canInsert, Predicate<@NotNull ItemStack> validator, @Nullable IContentsListener listener, int x, int y) {
        super(1, canExtract, canInsert, validator, listener, x, y);
    }
}

