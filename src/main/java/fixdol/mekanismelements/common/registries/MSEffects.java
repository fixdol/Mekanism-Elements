package fixdol.mekanismelements.common.registries;

import fixdol.mekanismelements.common.registries.MSEffects;
import fixdol.mekanismelements.common.MekanismElements;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

import fixdol.mekanismelements.common.effect.RadiationResistance;
import fixdol.mekanismelements.common.effect.SensoryParalysis;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class MSEffects {
    public static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(net.minecraft.core.registries.Registries.MOB_EFFECT, MekanismElements.MODID);

    //public static final RegistryObject<MobEffect> GOOD_SLEEP = MOB_EFFECTS.register("good_sleep",()-> new GoodSleep(MobEffectCategory.BENEFICIAL,0xFFCF2Ae));
    public static final RegistryObject<MobEffect> SENSORY_PARALYSIS = MOB_EFFECTS.register("sensory_paralysis",()-> new SensoryParalysis(MobEffectCategory.BENEFICIAL,0xFFCF2Ae));
    public static final RegistryObject<MobEffect> RADIATION_RESISTANCE = MOB_EFFECTS.register("radiation_resistance",()-> new RadiationResistance(MobEffectCategory.BENEFICIAL,0xFFCF2Ae));

    private MSEffects(){
    }
}

