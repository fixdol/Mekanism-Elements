package com.fxd927.mekanismelements.mixin;

import mekanism.common.attachments.containers.chemical.ComponentBackedChemicalTank;
import mekanism.common.item.gear.ItemJetpack;
import mekanism.api.chemical.ChemicalStack;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import com.fxd927.mekanismelements.common.registries.MSGases;

import java.util.function.Predicate;

@Mixin(value = ComponentBackedChemicalTank.class, remap = false)
public abstract class MixinComponentBackedChemicalTank {

    @ModifyVariable(method = "<init>", at = @At("HEAD"), argsOnly = true, ordinal = 0)
    private static Predicate<ChemicalStack> modifyValidator(Predicate<ChemicalStack> originalValidator, ItemStack attachedTo) {
        if (attachedTo.getItem() instanceof ItemJetpack) {
            return stack -> originalValidator.test(stack) || stack.is(MSGases.AMMONIA);
        }
        return originalValidator;
    }
}
