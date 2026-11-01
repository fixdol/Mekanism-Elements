package fixdol.mekanismelements.common.inventory.slot;

import mekanism.api.AutomationType;
import mekanism.api.IContentsListener;
import mekanism.api.functions.ConstantPredicates;
import mekanism.common.inventory.container.slot.ContainerSlotType;
import net.neoforged.neoforge.transfer.item.ItemResource;
import org.jspecify.annotations.Nullable;

import java.util.Objects;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

public class MSInputInventorySlot extends MSBasicInventorySlot {

    public static MSInputInventorySlot at(@Nullable IContentsListener listener, int x, int y) {
        return at(ConstantPredicates.alwaysTrue(), listener, x, y);
    }

    public static MSInputInventorySlot at(Predicate<ItemResource> isItemValid, @Nullable IContentsListener listener, int x, int y) {
        return at(ConstantPredicates.alwaysTrueBi(), isItemValid, listener, x, y);
    }

    public static MSInputInventorySlot at(BiPredicate<ItemResource, AutomationType> insertPredicate, Predicate<ItemResource> isItemValid, @Nullable IContentsListener listener,
                                        int x, int y) {
        Objects.requireNonNull(insertPredicate, "Insertion check cannot be null");
        Objects.requireNonNull(isItemValid, "Item validity check cannot be null");
        return new MSInputInventorySlot(insertPredicate, isItemValid, listener, x, y);
    }

    protected MSInputInventorySlot(BiPredicate<ItemResource, AutomationType> insertPredicate, Predicate<ItemResource> isItemValid, @Nullable IContentsListener listener, int x, int y) {
        super(ConstantPredicates.notExternal(), insertPredicate, isItemValid, listener, x, y);
        setSlotType(ContainerSlotType.INPUT);
    }
}
