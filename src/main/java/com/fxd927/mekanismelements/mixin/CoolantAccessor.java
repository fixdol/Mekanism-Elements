package com.fxd927.mekanismelements.mixin;

import mekanism.api.chemical.gas.attribute.GasAttributes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = GasAttributes.Coolant.class, remap = false)
public interface CoolantAccessor {
    @Mutable
    @Accessor
    void setConductivity(double conductivity);
}
