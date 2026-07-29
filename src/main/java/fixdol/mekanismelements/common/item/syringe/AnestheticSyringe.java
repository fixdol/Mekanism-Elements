package fixdol.mekanismelements.common.item.syringe;

import fixdol.mekanismelements.common.registries.MSEffects;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;

public class AnestheticSyringe extends DrugSyringe {
    public AnestheticSyringe(Properties properties) {
        super(properties,4);
    }

    @Override
    protected Holder<MobEffect> getEffectType() {
        return MSEffects.SENSORY_PARALYSIS;
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

