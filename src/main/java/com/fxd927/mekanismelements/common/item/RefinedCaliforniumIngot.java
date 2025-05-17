package com.fxd927.mekanismelements.common.item;

import mekanism.api.text.EnumColor;
import mekanism.api.text.TextComponentUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class RefinedCaliforniumIngot extends Item {
    protected EnumColor color;

    public RefinedCaliforniumIngot(Item.Properties properties, EnumColor color) {
        super(properties);
        this.color = color;
    }

    @Override
    public boolean isFoil(ItemStack itemStack) {
        return true;
    }

    @Override
    public Component getName(ItemStack stack) {
        return TextComponentUtil.build(this.color, super.getName(stack));
    }
}

