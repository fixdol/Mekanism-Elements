package fixdol.mekanismelements.common.item;

import mekanism.common.capabilities.Capabilities;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.LivingEntity;
import fixdol.mekanismelements.common.item.NeutronSourcePellet;
import net.minecraft.world.entity.player.Player;

import mekanism.api.text.EnumColor;
import mekanism.api.text.TextComponentUtil;
import net.minecraft.world.entity.Entity;

public class NeutronSourcePellet extends Item {
    protected EnumColor color;

    public NeutronSourcePellet(Item.Properties properties, EnumColor color) {
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
        var radiationEntity = entity.getCapability(Capabilities.RADIATION_ENTITY);
        if (radiationEntity != null) {
            radiationEntity.ifPresent(handler -> handler.radiate(magnitude));
        }
    }

    @Override
    public Component getName(ItemStack stack) {
        return TextComponentUtil.build(this.color, super.getName(stack));
    }
}

