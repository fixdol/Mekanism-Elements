package com.fxd927.mekanismelements.common.item.syringe;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;

public class FlameRetardantSyringe extends DrugSyringe{
    public FlameRetardantSyringe(Properties properties) {
        super(properties,4);
    }

    @Override
    protected MobEffect getEffectType() {
        return MobEffects.FIRE_RESISTANCE;
    }

    @Override
    protected int getBaseDuration() {
        return 20 * 120;
    }

    @Override
    protected int getEffectAmplifier() {
        return 0;
    }
}
