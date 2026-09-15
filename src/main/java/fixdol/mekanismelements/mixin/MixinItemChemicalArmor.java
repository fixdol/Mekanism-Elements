package fixdol.mekanismelements.mixin;

import fixdol.mekanismelements.common.registries.MSGases;
import mekanism.common.config.MekanismConfig;
import mekanism.common.item.gear.ItemGasArmor;
import mekanism.common.item.gear.ItemJetpack;
import mekanism.common.util.ChemicalUtil;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = ItemGasArmor.class, remap = false)
public abstract class MixinItemChemicalArmor {

    @Inject(method = "addItems", at = @At("TAIL"))
    private void onAddItems(CreativeModeTab.Output tabOutput, CallbackInfo ci) {
        if ((Object) this instanceof ItemJetpack) {
            tabOutput.accept(ChemicalUtil.getFilledVariant(new ItemStack((net.minecraft.world.item.Item) (Object) this),
                  MekanismConfig.gear.jetpackMaxGas, MSGases.AMMONIA));
        }
    }
}
