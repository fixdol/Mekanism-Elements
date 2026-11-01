package fixdol.mekanismelements.mixin;

import fixdol.mekanismelements.common.registries.MSGases;
import mekanism.api.chemical.ChemicalResource;
import mekanism.api.gear.IHUDElement;
import mekanism.api.gear.IModule;
import mekanism.api.gear.IModuleContainer;
import mekanism.api.gear.IModuleHelper;
import mekanism.api.math.MathUtils;
import mekanism.common.capabilities.Capabilities;
import mekanism.common.content.gear.mekasuit.ModuleJetpackUnit;
import mekanism.common.util.ItemAccessUtils;
import mekanism.common.util.StorageUtils;
import net.minecraft.core.TypedInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.transfer.ResourceHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Consumer;

@Mixin(value = ModuleJetpackUnit.class, remap = false)
public abstract class MixinModuleJetpackUnit {

    @Inject(method = "addHUDElements", at = @At("HEAD"), cancellable = true)
    private void onAddHUDElements(IModule<ModuleJetpackUnit> module, IModuleContainer moduleContainer, TypedInstance<Item> instance, Player player,
                                  Consumer<IHUDElement> hudElementAdder, CallbackInfo ci) {
        if (module.isEnabled()) {
            ResourceHandler<ChemicalResource> chemicalHandler = Capabilities.CHEMICAL.getCapability(ItemAccessUtils.sideEffectFreeAccess(instance));
            if (chemicalHandler != null) {
                long stored = StorageUtils.getContainedChemical(chemicalHandler, MSGases.AMMONIA);
                if (stored > 0) {
                    ModuleJetpackUnit self = (ModuleJetpackUnit) (Object) this;
                    double ratio = MathUtils.divideToLevel(stored, chemicalHandler.getCapacityAsLong(0, chemicalHandler.getResource(0)));
                    hudElementAdder.accept(IModuleHelper.INSTANCE.hudElementPercent(self.mode().getHUDIcon(), ratio));
                    ci.cancel();
                }
            }
        }
    }
}
