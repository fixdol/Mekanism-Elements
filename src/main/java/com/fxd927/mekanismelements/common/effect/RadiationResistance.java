package com.fxd927.mekanismelements.common.effect;

import mekanism.common.capabilities.Capabilities;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class RadiationResistance extends MobEffect {
    public RadiationResistance(MobEffectCategory mobEffectCategory, int color) {
        super(mobEffectCategory, color);
    }

    // applyEffectTick replaced with tick() in 1.21.1
    public boolean tick(LivingEntity entity, int amplifier) {
        var radiationEntity = entity.getCapability(Capabilities.RADIATION_ENTITY);
        if (radiationEntity != null) {
            radiationEntity.set(0);
        }
        return true;
    }
}


