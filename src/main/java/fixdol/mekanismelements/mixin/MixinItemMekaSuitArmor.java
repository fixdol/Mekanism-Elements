package fixdol.mekanismelements.mixin;

import fixdol.mekanismelements.common.registries.MSGases;
import mekanism.api.chemical.gas.Gas;
import mekanism.api.chemical.gas.GasStack;
import mekanism.api.chemical.gas.IGasHandler;
import mekanism.api.gear.IModule;
import mekanism.api.gear.IModuleHelper;
import mekanism.common.capabilities.Capabilities;
import mekanism.common.capabilities.chemical.item.ChemicalTankSpec;
import mekanism.common.config.MekanismConfig;
import mekanism.common.content.gear.IModuleContainerItem;
import mekanism.common.content.gear.mekasuit.ModuleJetpackUnit;
import mekanism.common.item.gear.ItemMekaSuitArmor;
import mekanism.common.registries.MekanismGases;
import mekanism.common.registries.MekanismModules;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(value = ItemMekaSuitArmor.class, remap = false)
public abstract class MixinItemMekaSuitArmor {

    @Shadow @Final private List<ChemicalTankSpec<Gas>> gasTankSpecs;

    private static final float MS_THRUST_MULTIPLIER = 1.0F;

    @Inject(method = "<init>", at = @At("RETURN"))
    private void onInit(ArmorItem.Type armorType, Item.Properties properties, CallbackInfo ci) {
        if (armorType == ArmorItem.Type.CHESTPLATE) {
            this.gasTankSpecs.clear();
            this.gasTankSpecs.add(ChemicalTankSpec.createFillOnly(
                  MekanismConfig.gear.mekaSuitJetpackTransferRate,
                  MekanismConfig.gear.mekaSuitJetpackMaxStorage,
                  chemical -> chemical == MekanismGases.HYDROGEN.getChemical() || chemical == MSGases.AMMONIA.getChemical(),
                  stack -> ((IModuleContainerItem) stack.getItem()).hasModule(stack, MekanismModules.JETPACK_UNIT)));
        }
    }

    @Inject(method = "canUseJetpack", at = @At("HEAD"), cancellable = true)
    private void canUseJetpackWithAmmonia(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        ItemMekaSuitArmor self = (ItemMekaSuitArmor) (Object) this;
        if (self.getType() == ArmorItem.Type.CHESTPLATE && self.isModuleEnabled(stack, MekanismModules.JETPACK_UNIT)) {
            IGasHandler chemicalHandler = stack.getCapability(Capabilities.GAS_HANDLER).resolve().orElse(null);
            if (chemicalHandler != null) {
                for (int tank = 0, tanks = chemicalHandler.getTanks(); tank < tanks; tank++) {
                    GasStack stored = chemicalHandler.getChemicalInTank(tank);
                    if (!stored.isEmpty() && (stored.getType() == MekanismGases.HYDROGEN.getChemical() || stored.getType() == MSGases.AMMONIA.getChemical())) {
                        cir.setReturnValue(true);
                        return;
                    }
                }
            }
        }
    }


    @Inject(method = "useJetpackFuel", at = @At("HEAD"), cancellable = true)
    private void useJetpackFuelAmmonia(ItemStack stack, CallbackInfo ci) {
        IModule<ModuleJetpackUnit> module = IModuleHelper.INSTANCE.load(stack, MekanismModules.JETPACK_UNIT);
        if (module != null && module.isEnabled()) {
            IGasHandler chemicalHandler = stack.getCapability(Capabilities.GAS_HANDLER).resolve().orElse(null);
            if (chemicalHandler != null && chemicalHandler.getTanks() > 0) {
                GasStack stored = chemicalHandler.getChemicalInTank(0);
                if (stored.getType() == MSGases.AMMONIA.getChemical()) {
                    int amount = Mth.ceil(MS_THRUST_MULTIPLIER);
                    if (Math.random() < 0.2) {
                        chemicalHandler.extractChemical(MSGases.AMMONIA.getStack(amount), mekanism.api.Action.EXECUTE);
                    }
                    ci.cancel();
                }
            }
        }
    }
}
