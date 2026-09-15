package fixdol.mekanismelements.common.effect;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import fixdol.mekanismelements.common.effect.RadiationResistance;

import mekanism.common.capabilities.Capabilities;

public class RadiationResistance extends MobEffect {
    public RadiationResistance(MobEffectCategory mobEffectCategory, int color) {
        super(mobEffectCategory, color);
    }

    // applyEffectTick replaced with tick() in 1.21.1
    public boolean tick(LivingEntity entity, int amplifier) {
        var radiationEntity = entity.getCapability(Capabilities.RADIATION_ENTITY);
        if (radiationEntity != null) {
            radiationEntity.ifPresent(handler -> handler.set(0));
        }
        return true;
    }
}


