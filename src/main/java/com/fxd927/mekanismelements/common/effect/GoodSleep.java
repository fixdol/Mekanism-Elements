package com.fxd927.mekanismelements.common.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class GoodSleep extends MobEffect {
    public GoodSleep(MobEffectCategory mobEffectCategory, int color) {
        super(mobEffectCategory, color);
    }

    // applyEffectTick replaced with tick() in 1.21.1
    public boolean tick(LivingEntity entity, int amplifier) {
        if (entity instanceof Player player && player.isSleeping()) {
            player.setHealth(player.getMaxHealth());
        }
        return true;
    }
}

