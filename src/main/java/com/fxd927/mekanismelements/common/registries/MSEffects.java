package com.fxd927.mekanismelements.common.registries;

import com.fxd927.mekanismelements.common.MekanismElements;
import com.fxd927.mekanismelements.common.effect.RadiationResistance;
import com.fxd927.mekanismelements.common.effect.SensoryParalysis;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class MSEffects {
    public static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, MekanismElements.MODID);

    //public static final RegistryObject<MobEffect> GOOD_SLEEP = MOB_EFFECTS.register("good_sleep",()-> new GoodSleep(MobEffectCategory.BENEFICIAL,0xFFCF2Ae));
    public static final RegistryObject<MobEffect> SENSORY_PARALYSIS = MOB_EFFECTS.register("sensory_paralysis",()-> new SensoryParalysis(MobEffectCategory.BENEFICIAL,0xFFCF2Ae));
    public static final RegistryObject<MobEffect> RADIATION_RESISTANCE = MOB_EFFECTS.register("radiation_resistance",()-> new RadiationResistance(MobEffectCategory.BENEFICIAL,0xFFCF2Ae));

    private MSEffects(){
    }
}
