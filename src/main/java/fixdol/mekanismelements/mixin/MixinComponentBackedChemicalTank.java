package fixdol.mekanismelements.mixin;

import fixdol.mekanismelements.common.registries.MSGases;
import mekanism.api.chemical.ChemicalResource;
import mekanism.common.component.containers.chemical.ComponentBackedChemicalTank;
import mekanism.common.item.gear.ItemJetpack;
import net.neoforged.neoforge.transfer.access.ItemAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.util.function.Predicate;

@Mixin(value = ComponentBackedChemicalTank.class, remap = false)
public abstract class MixinComponentBackedChemicalTank {

    @ModifyVariable(method = "<init>", at = @At("HEAD"), argsOnly = true, ordinal = 0)
    private static Predicate<ChemicalResource> modifyValidator(Predicate<ChemicalResource> originalValidator, ItemAccess attachedAccess) {
        if (attachedAccess.getResource().getItem() instanceof ItemJetpack) {
            return resource -> originalValidator.test(resource) || resource.is(MSGases.AMMONIA);
        }
        return originalValidator;
    }
}
