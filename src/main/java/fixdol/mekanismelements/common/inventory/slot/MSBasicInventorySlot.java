package fixdol.mekanismelements.common.inventory.slot;

import mekanism.api.AutomationType;
import mekanism.api.IContentsListener;
import mekanism.common.inventory.slot.BasicInventorySlot;
import net.neoforged.neoforge.transfer.item.ItemResource;
import org.jspecify.annotations.Nullable;

import java.util.function.BiPredicate;
import java.util.function.Predicate;

public class MSBasicInventorySlot extends BasicInventorySlot {

    protected MSBasicInventorySlot(BiPredicate<ItemResource, AutomationType> canExtract, BiPredicate<ItemResource, AutomationType> canInsert, Predicate<ItemResource> validator, @Nullable IContentsListener listener, int x, int y) {
        super(1, canExtract, canInsert, validator, null, null, listener, x, y);
    }
}
