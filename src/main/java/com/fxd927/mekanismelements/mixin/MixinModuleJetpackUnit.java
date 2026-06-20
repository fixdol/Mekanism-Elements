package com.fxd927.mekanismelements.mixin;

import mekanism.api.gear.IHUDElement;
import mekanism.api.gear.IModule;
import mekanism.api.gear.IModuleContainer;
import mekanism.api.gear.IModuleHelper;
import mekanism.api.chemical.IChemicalHandler;
import mekanism.api.chemical.ChemicalStack;
import mekanism.common.capabilities.Capabilities;
import mekanism.common.content.gear.mekasuit.ModuleJetpackUnit;
import mekanism.common.util.StorageUtils;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import com.fxd927.mekanismelements.common.registries.MSGases;
import java.util.function.Consumer;

@Mixin(value = ModuleJetpackUnit.class, remap = false)
public abstract class MixinModuleJetpackUnit {

    @Inject(method = "addHUDElements", at = @At("HEAD"), cancellable = true)
    private void onAddHUDElements(IModule<ModuleJetpackUnit> module, IModuleContainer moduleContainer, ItemStack stack, Player player, Consumer<IHUDElement> hudElementAdder, CallbackInfo ci) {
        if (module.isEnabled()) {
            IChemicalHandler chemicalHandler = Capabilities.CHEMICAL.getCapability(stack);
            if (chemicalHandler != null) {
                ChemicalStack stored = StorageUtils.getContainedChemical(chemicalHandler, MSGases.AMMONIA);
                if (!stored.isEmpty()) {
                    ModuleJetpackUnit self = (ModuleJetpackUnit) (Object) this;
                    double ratio = StorageUtils.getRatio(stored.getAmount(), chemicalHandler.getChemicalTankCapacity(0));
                    hudElementAdder.accept(IModuleHelper.INSTANCE.hudElementPercent(self.mode().getHUDIcon(), ratio));
                    ci.cancel();
                }
            }
        }
    }
}
