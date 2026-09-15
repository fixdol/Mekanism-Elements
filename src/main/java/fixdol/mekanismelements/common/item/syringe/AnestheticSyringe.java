package fixdol.mekanismelements.common.item.syringe;

import fixdol.mekanismelements.common.item.syringe.AnestheticSyringe;
import fixdol.mekanismelements.common.registries.MSEffects;
import net.minecraft.world.effect.MobEffect;

import net.minecraft.core.Holder;

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

