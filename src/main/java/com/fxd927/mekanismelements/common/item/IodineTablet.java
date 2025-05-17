package com.fxd927.mekanismelements.common.item;

import com.fxd927.mekanismelements.common.registries.MSEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

public class IodineTablet extends Item {
    private static final int BASE_DURATION = 12000;
    private static final int REGENERATION_DURATION = 3600;
    private static final int REGENERATION_AMPLIFIER = 1;

    public IodineTablet(Properties properties) {
        super(properties);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        if (!level.isClientSide && entity instanceof Player player) {

            MobEffectInstance currentEffect = player.getEffect(MSEffects.RADIATION_RESISTANCE.get());
            //player.getCooldowns().addCooldown(this, 3600);

            int newDuration;
            if (currentEffect != null) {
                newDuration = currentEffect.getDuration() + BASE_DURATION;
            } else {
                newDuration = BASE_DURATION;
            }

            player.addEffect(new MobEffectInstance(MSEffects.RADIATION_RESISTANCE.get(), newDuration, 0));

            player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, REGENERATION_DURATION, REGENERATION_AMPLIFIER));
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
