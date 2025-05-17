package com.fxd927.mekanismelements.common.item.tablet;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

public class PoisonTablet extends Item {
    private static final int BASE_DURATION = 3600;

    public PoisonTablet(Properties properties) {
        super(properties);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        if (!level.isClientSide && entity instanceof Player player) {

            MobEffectInstance currentEffect = player.getEffect(MobEffects.POISON);
            player.getCooldowns().addCooldown(this, 4800);

            int newDuration;
            if (currentEffect != null) {
                newDuration = currentEffect.getDuration() + BASE_DURATION;
            } else {
                newDuration = BASE_DURATION;
            }

            player.addEffect(new MobEffectInstance(MobEffects.POISON, newDuration, 0));
        }
        return super.finishUsingItem(stack, level, entity);
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.EAT;
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 20;
    }
}
