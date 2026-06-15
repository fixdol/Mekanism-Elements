package com.fxd927.mekanismelements.common.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.neoforged.neoforge.common.NeoForge;

public class SensoryParalysis extends MobEffect {
    public SensoryParalysis(MobEffectCategory mobEffectCategory, int color) {
        super(mobEffectCategory, color);
        // Events should be registered via addListener() if needed, not via register(this)
    }

    // applyEffectTick replaced with tick() in 1.21.1
    public boolean tick(LivingEntity entity, int amplifier) {
        if (!entity.isInvulnerable()) {
            entity.setInvulnerable(true);
        }
        return true;
    }
}

