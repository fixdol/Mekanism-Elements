package fixdol.mekanismelements.mixin;

import fixdol.mekanismelements.common.registries.MSGases;
import mekanism.api.AutomationType;
import mekanism.api.chemical.gas.Gas;
import mekanism.common.capabilities.chemical.item.RateLimitGasHandler;
import mekanism.common.item.gear.ItemGasArmor;
import mekanism.common.item.gear.ItemJetpack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.function.BiPredicate;
import java.util.function.LongSupplier;
import java.util.function.Predicate;

@Mixin(value = ItemGasArmor.class, remap = false)
public abstract class MixinItemGasArmor {

    @Redirect(method = "gatherCapabilities", at = @At(value = "INVOKE",
          target = "Lmekanism/common/capabilities/chemical/item/RateLimitGasHandler;create(Ljava/util/function/LongSupplier;Ljava/util/function/LongSupplier;Ljava/util/function/BiPredicate;Ljava/util/function/BiPredicate;Ljava/util/function/Predicate;)Lmekanism/common/capabilities/chemical/item/RateLimitGasHandler;"))
    private RateLimitGasHandler acceptAmmonia(LongSupplier rate, LongSupplier capacity, BiPredicate<Gas, AutomationType> canExtract,
          BiPredicate<Gas, AutomationType> canInsert, Predicate<Gas> isValid) {
        if ((Object) this instanceof ItemJetpack) {
            return RateLimitGasHandler.create(rate, capacity, canExtract, canInsert,
                  gas -> isValid.test(gas) || gas == MSGases.AMMONIA.getChemical());
        }
        return RateLimitGasHandler.create(rate, capacity, canExtract, canInsert, isValid);
    }
}
