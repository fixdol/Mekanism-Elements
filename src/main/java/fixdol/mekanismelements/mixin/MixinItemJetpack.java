package fixdol.mekanismelements.mixin;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import mekanism.common.capabilities.Capabilities;
import mekanism.api.chemical.gas.GasStack;
import mekanism.api.chemical.gas.IGasHandler;
import org.spongepowered.asm.mixin.injection.Inject;
import mekanism.common.item.gear.ItemJetpack;
import net.minecraft.world.item.ItemStack;
import fixdol.mekanismelements.common.registries.MSGases;
import org.spongepowered.asm.mixin.Mixin;

import mekanism.common.registries.MekanismGases;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = ItemJetpack.class, remap = false)
public abstract class MixinItemJetpack {

    @Inject(method = "canUseJetpack", at = @At("HEAD"), cancellable = true)
    private void canUseJetpackWithAmmonia(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        IGasHandler chemicalHandler = stack.getCapability(Capabilities.GAS_HANDLER).resolve().orElse(null);
        if (chemicalHandler != null) {
            for (int tank = 0, tanks = chemicalHandler.getTanks(); tank < tanks; tank++) {
                GasStack stored = chemicalHandler.getChemicalInTank(tank);
                if (!stored.isEmpty() && (stored.getType() == MekanismGases.HYDROGEN.get() || stored.getType() == MSGases.AMMONIA.get())) {
                    cir.setReturnValue(true);
                    return;
                }
            }
        }
    }
    

    @Inject(method = "useJetpackFuel", at = @At("HEAD"), cancellable = true)
    private void useJetpackFuelAmmonia(ItemStack stack, CallbackInfo ci) {
        IGasHandler chemicalHandler = stack.getCapability(Capabilities.GAS_HANDLER).resolve().orElse(null);
        if (chemicalHandler != null && chemicalHandler.getTanks() > 0) {
            GasStack stored = chemicalHandler.getChemicalInTank(0);
            if (stored.getType() == MSGases.AMMONIA.get()) {
                if (Math.random() < 0.2) {
                    chemicalHandler.extractChemical(MSGases.AMMONIA.getStack(1), mekanism.api.Action.EXECUTE);
                }
                ci.cancel();
            }
        }
    }
}
