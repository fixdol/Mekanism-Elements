package com.fxd927.mekanismelements.mixin;

import mekanism.common.registration.impl.ItemRegistryObject;
import mekanism.common.config.IMekanismConfig;
import mekanism.common.attachments.containers.ContainerType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import java.util.function.Supplier;

@Mixin(value = ItemRegistryObject.class, remap = false)
public abstract class MixinItemRegistryObject {

    @ModifyVariable(method = "addAttachedContainerCapabilities", at = @At("HEAD"), argsOnly = true, ordinal = 0)
    private Supplier<?> modifyJetpackTank(Supplier<?> defaultCreator, ContainerType<?, ?, ?> containerType, Supplier<?> ignore, IMekanismConfig[] requiredConfigs) {
        ItemRegistryObject<?> self = (ItemRegistryObject<?>) (Object) this;
        if (self.getId() != null && (self.getId().getPath().equals("jetpack") || self.getId().getPath().equals("jetpack_armored"))) {
            if (containerType == ContainerType.CHEMICAL) {
                System.out.println("[Mekanism-Elements] Intercepted Jetpack chemical tank creation for Ammonia support!");
                return () -> mekanism.common.attachments.containers.chemical.ChemicalTanksBuilder.builder()
                    .addInternalStorage(mekanism.common.config.MekanismConfig.gear.jetpackFillRate,
                                        mekanism.common.config.MekanismConfig.gear.jetpackCapacity,
                                        chemical -> chemical.is(mekanism.common.registries.MekanismChemicals.HYDROGEN) || 
                                                    chemical.is(com.fxd927.mekanismelements.common.registries.MSGases.AMMONIA))
                    .build();
            }
        }
        return defaultCreator;
    }
}
