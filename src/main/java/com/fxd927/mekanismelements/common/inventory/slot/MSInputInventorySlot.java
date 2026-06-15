package com.fxd927.mekanismelements.common.inventory.slot;

import mekanism.api.AutomationType;
import mekanism.api.IContentsListener;
import mekanism.api.annotations.NothingNullByDefault;
import mekanism.common.inventory.container.slot.ContainerSlotType;
import mekanism.common.inventory.slot.BasicInventorySlot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

@NothingNullByDefault
public class MSInputInventorySlot extends MSBasicInventorySlot {
    private static final Predicate<@NotNull ItemStack> alwaysTrue = stack -> true;
    private static final java.util.function.BiPredicate<@NotNull ItemStack, @NotNull AutomationType> notExternal = (stack, automationType) -> automationType != AutomationType.EXTERNAL;

    public static MSInputInventorySlot at(@Nullable IContentsListener listener, int x, int y) {
        return at(alwaysTrue, listener, x, y);
    }

    public static MSInputInventorySlot at(Predicate<@NotNull ItemStack> isItemValid, @Nullable IContentsListener listener, int x, int y) {
        return at(alwaysTrue, isItemValid, listener, x, y);
    }

    public static MSInputInventorySlot at(Predicate<@NotNull ItemStack> insertPredicate, Predicate<@NotNull ItemStack> isItemValid, @Nullable IContentsListener listener,
                                        int x, int y) {
        Objects.requireNonNull(insertPredicate, "Insertion check cannot be null");
        Objects.requireNonNull(isItemValid, "Item validity check cannot be null");
        return new MSInputInventorySlot(insertPredicate, isItemValid, listener, x, y);
    }

    protected MSInputInventorySlot(Predicate<@NotNull ItemStack> insertPredicate, Predicate<@NotNull ItemStack> isItemValid, @Nullable IContentsListener listener, int x, int y) {
        super(notExternal, (stack, automationType) -> insertPredicate.test(stack), isItemValid, listener, x, y);
        setSlotType(ContainerSlotType.INPUT);
    }
}

