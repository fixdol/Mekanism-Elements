package com.fxd927.mekanismelements.common.effect;

import mekanism.common.capabilities.Capabilities;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class RadiationResistance extends MobEffect {
    public RadiationResistance(MobEffectCategory mobEffectCategory, int color) {
        super(mobEffectCategory, color);
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        entity.getCapability(Capabilities.RADIATION_ENTITY).ifPresent(radiationEntity -> {
            radiationEntity.set(0);
        });
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }
}

