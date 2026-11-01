package fixdol.mekanismelements.mixin;

import fixdol.mekanismelements.common.registries.MSGases;
import mekanism.api.chemical.ChemicalResource;
import mekanism.api.gear.IModule;
import mekanism.api.gear.IModuleHelper;
import mekanism.common.capabilities.Capabilities;
import mekanism.common.capabilities.GenericTankSpec;
import mekanism.common.capabilities.proxy.AutomatedResourceHandler;
import mekanism.common.config.MekanismConfig;
import mekanism.common.content.gear.mekasuit.ModuleJetpackUnit;
import mekanism.common.item.gear.ItemMekaSuitArmor;
import mekanism.common.registries.MekanismChemicals;
import mekanism.common.registries.MekanismModules;
import net.minecraft.core.TypedInstance;
import net.minecraft.core.component.DataComponentGetter;
import net.minecraft.util.Mth;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.equipment.ArmorType;
import net.neoforged.neoforge.transfer.ResourceHandler;
import net.neoforged.neoforge.transfer.access.ItemAccess;
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.neoforged.neoforge.transfer.transaction.TransactionContext;
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

    @Shadow @Final private List<GenericTankSpec<ChemicalResource>> chemicalTankSpecs;
    @Shadow @Final private ArmorType armorType;

    @Inject(method = "<init>", at = @At("RETURN"))
    private void onInit(ArmorType armorType, Item.Properties properties, CallbackInfo ci) {
        if (armorType == ArmorType.CHESTPLATE) {
            ItemMekaSuitArmor self = (ItemMekaSuitArmor) (Object) this;
            this.chemicalTankSpecs.clear();
            this.chemicalTankSpecs.add(GenericTankSpec.createFillOnly(MekanismConfig.gear.mekaSuitJetpackTransferRate, itemAccess -> {
                ItemResource itemType = itemAccess.getResource();
                if (itemType.isEmpty()) {
                    return 0L;
                }
                IModule<ModuleJetpackUnit> module = IModuleHelper.INSTANCE.getModule(itemType, MekanismModules.JETPACK_UNIT);
                return module == null ? 0L : MekanismConfig.gear.mekaSuitJetpackMaxStorage.get() * module.getInstalledCount();
            }, chemical -> chemical.is(MekanismChemicals.HYDROGEN) || chemical.is(MSGases.AMMONIA), itemType -> self.hasModule(itemType, MekanismModules.JETPACK_UNIT)));
        }
    }

    @Inject(method = "canUseJetpack", at = @At("HEAD"), cancellable = true)
    private void canUseJetpackWithAmmonia(ItemAccess itemAccess, CallbackInfoReturnable<Boolean> cir) {
        if (armorType == ArmorType.CHESTPLATE) {
            ItemMekaSuitArmor self = (ItemMekaSuitArmor) (Object) this;
            if (self.isModuleEnabled(itemAccess.getResource(), MekanismModules.JETPACK_UNIT)) {
                ResourceHandler<ChemicalResource> chemicalHandler = Capabilities.CHEMICAL.getCapability(itemAccess);
                if (chemicalHandler != null) {
                    for (int tank = 0, tanks = chemicalHandler.size(); tank < tanks; tank++) {
                        ChemicalResource stored = chemicalHandler.getResource(tank);
                        if (!stored.isEmpty() && chemicalHandler.getAmountAsLong(tank) > 0 && (stored.is(MekanismChemicals.HYDROGEN) || stored.is(MSGases.AMMONIA))) {
                            cir.setReturnValue(true);
                            return;
                        }
                    }
                }
            }
        }
    }

    /**
     * Mekanism 10.8 no longer has a separate thrust getter: the thrust is the value returned when using the fuel.
     * With ammonia the thrust is 0.15 * 1.5 * multiplier (scaled down if there is not enough ammonia stored)
     * and the fuel is only consumed 20% of the ticks.
     */
    @Inject(method = "useJetpackFuel", at = @At("HEAD"), cancellable = true)
    private <ITEM extends TypedInstance<Item> & DataComponentGetter> void useJetpackFuelAmmonia(ItemAccess itemAccess, ITEM primaryInstance, TransactionContext transaction,
                                                                                               CallbackInfoReturnable<Double> cir) {
        ItemMekaSuitArmor self = (ItemMekaSuitArmor) (Object) this;
        IModule<ModuleJetpackUnit> module = self.getEnabledModule(primaryInstance, MekanismModules.JETPACK_UNIT);
        if (module != null) {
            ResourceHandler<ChemicalResource> chemicalHandler = AutomatedResourceHandler.manual(Capabilities.CHEMICAL.getCapability(itemAccess));
            if (chemicalHandler != null && chemicalHandler.size() > 0 && chemicalHandler.getResource(0).is(MSGases.AMMONIA)) {
                float thrustMultiplier = module.getCustomInstance().getThrustMultiplier();
                int neededGas = Mth.ceil(thrustMultiplier);
                long stored = chemicalHandler.getAmountAsLong(0);
                // Just like Hydrogen, scale thrust down if we do not have enough gas
                if (neededGas > stored) {
                    thrustMultiplier = stored;
                }
                if (Math.random() < 0.2) {
                    chemicalHandler.extract(MSGases.AMMONIA.asResource(), neededGas, transaction);
                }
                cir.setReturnValue(0.15 * 1.5 * thrustMultiplier);
            }
        }
    }
}
