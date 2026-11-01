package fixdol.mekanismelements.mixin;

import fixdol.mekanismelements.common.registries.MSGases;
import mekanism.api.chemical.ChemicalResource;
import mekanism.common.capabilities.Capabilities;
import mekanism.common.capabilities.proxy.AutomatedResourceHandler;
import mekanism.common.item.gear.ItemJetpack;
import mekanism.common.registries.MekanismChemicals;
import net.minecraft.core.TypedInstance;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.transfer.ResourceHandler;
import net.neoforged.neoforge.transfer.access.ItemAccess;
import net.neoforged.neoforge.transfer.transaction.TransactionContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = ItemJetpack.class, remap = false)
public abstract class MixinItemJetpack {

    @Inject(method = "canUseJetpack", at = @At("HEAD"), cancellable = true)
    private void canUseJetpackWithAmmonia(ItemAccess itemAccess, CallbackInfoReturnable<Boolean> cir) {
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

    /**
     * Mekanism 10.8 no longer has a separate thrust getter: the thrust is the value returned when using the fuel.
     * With ammonia the thrust is always 0.15 * 1.5 and one unit is only consumed 20% of the ticks.
     */
    @Inject(method = "useJetpackFuel", at = @At("HEAD"), cancellable = true)
    private void useJetpackFuelAmmonia(ItemAccess itemAccess, TypedInstance<Item> primaryInstance, TransactionContext transaction, CallbackInfoReturnable<Double> cir) {
        ResourceHandler<ChemicalResource> chemicalHandler = AutomatedResourceHandler.manual(Capabilities.CHEMICAL.getCapability(itemAccess));
        if (chemicalHandler != null && chemicalHandler.size() > 0 && chemicalHandler.getResource(0).is(MSGases.AMMONIA)) {
            if (Math.random() < 0.2) {
                chemicalHandler.extract(MSGases.AMMONIA.asResource(), 1, transaction);
            }
            cir.setReturnValue(0.15 * 1.5);
        }
    }
}
