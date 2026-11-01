package fixdol.mekanismelements.mixin;

import fixdol.mekanismelements.common.registries.MSGases;
import mekanism.common.component.containers.type.ContainerType;
import mekanism.common.item.gear.ItemChemicalArmor;
import mekanism.common.item.gear.ItemJetpack;
import net.minecraft.core.Holder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Consumer;

@Mixin(value = ItemChemicalArmor.class, remap = false)
public abstract class MixinItemChemicalArmor {

    @Inject(method = "addItems", at = @At("TAIL"))
    private void onAddItems(Holder<Item> item, Consumer<ItemStack> tabOutput, CallbackInfo ci) {
        if ((Object) this instanceof ItemJetpack) {
            tabOutput.accept(ContainerType.CHEMICAL.getFilledVariant(item, MSGases.AMMONIA, null));
        }
    }
}
