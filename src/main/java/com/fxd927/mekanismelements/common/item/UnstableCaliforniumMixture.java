package com.fxd927.mekanismelements.common.item;

import mekanism.api.text.EnumColor;
import mekanism.api.text.TextComponentUtil;
import mekanism.common.capabilities.Capabilities;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class UnstableCaliforniumMixture extends Item {
    protected EnumColor color;

    public UnstableCaliforniumMixture(Item.Properties properties, EnumColor color) {
        super(properties);
        this.color = color;
    }

    @Override
    public void inventoryTick(ItemStack stack, Level world, Entity entity, int slot, boolean selected) {
        if (!world.isClientSide && entity instanceof Player player) {
            double magnitude = 0.5;
            forceRadiate(player, magnitude);
        }
        super.inventoryTick(stack, world, entity, slot, selected);
    }

    private void forceRadiate(LivingEntity entity, double magnitude) {
        entity.getCapability(Capabilities.RADIATION_ENTITY).ifPresent(c -> {
            c.radiate(magnitude);
        });
    }

    @Override
    public Component getName(ItemStack stack) {
        return TextComponentUtil.build(this.color, super.getName(stack));
    }
}
