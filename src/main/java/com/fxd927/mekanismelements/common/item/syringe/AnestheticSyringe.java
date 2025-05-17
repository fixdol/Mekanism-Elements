package com.fxd927.mekanismelements.common.item.syringe;

import com.fxd927.mekanismelements.common.registries.MSEffects;
import net.minecraft.world.effect.MobEffect;

public class AnestheticSyringe extends DrugSyringe {
    public AnestheticSyringe(Properties properties) {
        super(properties,4);
    }

    @Override
    protected MobEffect getEffectType() {
        return MSEffects.SENSORY_PARALYSIS.get();
    }

    @Override
    protected int getBaseDuration() {
        return 20 * 20;
    }

    @Override
    protected int getEffectAmplifier() {
        return 0;
    }
}
