package fixdol.mekanismelements.mixin;

import fixdol.mekanismelements.common.registries.MSGases;
import mekanism.api.chemical.gas.GasStack;
import mekanism.api.chemical.gas.IGasHandler;
import mekanism.api.gear.IHUDElement;
import mekanism.api.gear.IModule;
import mekanism.api.gear.IModuleHelper;
import mekanism.common.capabilities.Capabilities;
import mekanism.common.content.gear.mekasuit.ModuleJetpackUnit;
import mekanism.common.util.StorageUtils;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Consumer;

@Mixin(value = ModuleJetpackUnit.class, remap = false)
public abstract class MixinModuleJetpackUnit {

    @Inject(method = "addHUDElements", at = @At("HEAD"), cancellable = true)
    private void onAddHUDElements(IModule<ModuleJetpackUnit> module, Player player, Consumer<IHUDElement> hudElementAdder, CallbackInfo ci) {
        if (module.isEnabled()) {
            ItemStack stack = module.getContainer();
            IGasHandler chemicalHandler = stack.getCapability(Capabilities.GAS_HANDLER).resolve().orElse(null);
            if (chemicalHandler != null && chemicalHandler.getTanks() > 0) {
                GasStack stored = chemicalHandler.getChemicalInTank(0);
                if (!stored.isEmpty() && stored.getType() == MSGases.AMMONIA.getChemical()) {
                    ModuleJetpackUnit self = (ModuleJetpackUnit) (Object) this;
                    double ratio = StorageUtils.getRatio(stored.getAmount(), chemicalHandler.getTankCapacity(0));
                    hudElementAdder.accept(IModuleHelper.INSTANCE.hudElementPercent(self.getMode().getHUDIcon(), ratio));
                    ci.cancel();
                }
            }
        }
    }
}
