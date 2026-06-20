package com.fxd927.mekanismelements.mixin;

import mekanism.common.item.gear.ItemJetpack;
import mekanism.common.registries.MekanismChemicals;
import mekanism.common.capabilities.Capabilities;
import mekanism.api.chemical.IChemicalHandler;
import mekanism.api.chemical.ChemicalStack;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import com.fxd927.mekanismelements.common.registries.MSGases;
import mekanism.common.item.interfaces.IJetpackItem.JetpackMode;

@Mixin(value = ItemJetpack.class, remap = false)
public abstract class MixinItemJetpack {

    @Inject(method = "canUseJetpack", at = @At("HEAD"), cancellable = true)
    private void canUseJetpackWithAmmonia(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        IChemicalHandler chemicalHandler = Capabilities.CHEMICAL.getCapability(stack);
        if (chemicalHandler != null) {
            for (int tank = 0, tanks = chemicalHandler.getChemicalTanks(); tank < tanks; tank++) {
                ChemicalStack stored = chemicalHandler.getChemicalInTank(tank);
                if (!stored.isEmpty() && (stored.is(MekanismChemicals.HYDROGEN) || stored.is(MSGases.AMMONIA))) {
                    cir.setReturnValue(true);
                    return;
                }
            }
        }
    }
    
    @Inject(method = "getJetpackThrust", at = @At("HEAD"), cancellable = true)
    private void getJetpackThrustAmmonia(ItemStack stack, CallbackInfoReturnable<Double> cir) {
        IChemicalHandler chemicalHandler = Capabilities.CHEMICAL.getCapability(stack);
        if (chemicalHandler != null && chemicalHandler.getChemicalTanks() > 0) {
            ChemicalStack stored = chemicalHandler.getChemicalInTank(0);
            if (stored.is(MSGases.AMMONIA)) {
                cir.setReturnValue(0.15 * 1.5);
            }
        }
    }

    @Inject(method = "useJetpackFuel", at = @At("HEAD"), cancellable = true)
    private void useJetpackFuelAmmonia(ItemStack stack, CallbackInfo ci) {
        IChemicalHandler chemicalHandler = Capabilities.CHEMICAL.getCapability(stack);
        if (chemicalHandler != null && chemicalHandler.getChemicalTanks() > 0) {
            ChemicalStack stored = chemicalHandler.getChemicalInTank(0);
            if (stored.is(MSGases.AMMONIA)) {
                if (Math.random() < 0.2) {
                    chemicalHandler.extractChemical(MSGases.AMMONIA.asStack(1), mekanism.api.Action.EXECUTE);
                }
                ci.cancel();
            }
        }
    }
}
