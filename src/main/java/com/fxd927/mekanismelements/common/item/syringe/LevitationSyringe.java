package com.fxd927.mekanismelements.common.item.syringe;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;

public class LevitationSyringe extends DrugSyringe{
    public LevitationSyringe(Properties properties) {
        super(properties,4);
    }

    @Override
    protected MobEffect getEffectType() {
        return MobEffects.LEVITATION;
    }

    @Override
    protected int getBaseDuration() {
        return 20 * 60;
    }

    @Override
    protected int getEffectAmplifier() {
        return 0;
    }
}
