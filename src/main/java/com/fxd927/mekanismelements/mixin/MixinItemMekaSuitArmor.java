package com.fxd927.mekanismelements.mixin;

import mekanism.common.item.gear.ItemMekaSuitArmor;
import mekanism.common.registries.MekanismChemicals;
import mekanism.common.registries.MekanismModules;
import mekanism.common.config.MekanismConfig;
import mekanism.common.capabilities.chemical.item.ChemicalTankSpec;
import mekanism.common.capabilities.Capabilities;
import mekanism.api.gear.IModule;
import mekanism.api.gear.IModuleHelper;
import mekanism.api.chemical.IChemicalHandler;
import mekanism.api.chemical.ChemicalStack;
import mekanism.common.content.gear.mekasuit.ModuleJetpackUnit;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.Entity;
import net.minecraft.util.Mth;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import com.fxd927.mekanismelements.common.registries.MSGases;
import mekanism.common.item.interfaces.IJetpackItem.JetpackMode;
import java.util.List;

@Mixin(value = ItemMekaSuitArmor.class, remap = false)
public abstract class MixinItemMekaSuitArmor {

    @Shadow @Final private List<ChemicalTankSpec> chemicalTankSpecs;



    @Inject(method = "<init>", at = @At("RETURN"))
    private void onInit(ArmorItem.Type armorType, Item.Properties properties, CallbackInfo ci) {
        if (armorType == ArmorItem.Type.CHESTPLATE) {
            this.chemicalTankSpecs.clear();
            this.chemicalTankSpecs.add(ChemicalTankSpec.createFillOnly(MekanismConfig.gear.mekaSuitJetpackTransferRate, stack -> {
                IModule<ModuleJetpackUnit> module = IModuleHelper.INSTANCE.getModule(stack, MekanismModules.JETPACK_UNIT);
                return module != null ? MekanismConfig.gear.mekaSuitJetpackMaxStorage.get() * module.getInstalledCount() : 0L;
            }, chemical -> chemical.is(MekanismChemicals.HYDROGEN) || chemical.is(MSGases.AMMONIA), stack -> ((mekanism.common.content.gear.IModuleContainerItem)stack.getItem()).hasModule(stack, MekanismModules.JETPACK_UNIT)));
        }
    }

    @Inject(method = "canUseJetpack", at = @At("HEAD"), cancellable = true)
    private void canUseJetpackWithAmmonia(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        ItemMekaSuitArmor self = (ItemMekaSuitArmor) (Object) this;
        if (self.getType() == ArmorItem.Type.CHESTPLATE) {
            if (self.isModuleEnabled(stack, MekanismModules.JETPACK_UNIT)) {
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
        }
    }

    @Inject(method = "getJetpackThrust", at = @At("HEAD"), cancellable = true)
    private void getJetpackThrustAmmonia(ItemStack stack, CallbackInfoReturnable<Double> cir) {
        IModule<ModuleJetpackUnit> module = ((mekanism.common.content.gear.IModuleContainerItem) (Object) this).getEnabledModule(stack, MekanismModules.JETPACK_UNIT);
        if (module != null) {
            IChemicalHandler chemicalHandler = Capabilities.CHEMICAL.getCapability(stack);
            if (chemicalHandler != null && chemicalHandler.getChemicalTanks() > 0) {
                ChemicalStack stored = chemicalHandler.getChemicalInTank(0);
                if (stored.is(MSGases.AMMONIA)) {
                    float thrustMultiplier = module.getCustomInstance().getThrustMultiplier();
                    // Just like Hydrogen, scale thrust down if we don't have enough gas
                    int neededGas = Mth.ceil(thrustMultiplier);
                    if (neededGas > stored.getAmount()) {
                        thrustMultiplier = stored.getAmount();
                    }
                    cir.setReturnValue(0.15 * 1.5 * thrustMultiplier);
                }
            }
        }
    }

    @Inject(method = "useJetpackFuel", at = @At("HEAD"), cancellable = true)
    private void useJetpackFuelAmmonia(ItemStack stack, CallbackInfo ci) {
        IModule<ModuleJetpackUnit> module = ((mekanism.common.content.gear.IModuleContainerItem) (Object) this).getEnabledModule(stack, MekanismModules.JETPACK_UNIT);
        if (module != null) {
            IChemicalHandler chemicalHandler = Capabilities.CHEMICAL.getCapability(stack);
            if (chemicalHandler != null && chemicalHandler.getChemicalTanks() > 0) {
                ChemicalStack stored = chemicalHandler.getChemicalInTank(0);
                if (stored.is(MSGases.AMMONIA)) {
                    int amount = Mth.ceil(module.getCustomInstance().getThrustMultiplier());
                    if (Math.random() < 0.2) {
                        chemicalHandler.extractChemical(MSGases.AMMONIA.asStack(amount), mekanism.api.Action.EXECUTE);
                    }
                    ci.cancel();
                }
            }
        }
    }
}
