package fixdol.mekanismelements.mixin;

import fixdol.mekanismelements.common.config.MSConfig;
import mekanism.api.chemical.ChemicalResource;
import mekanism.api.chemical.IChemicalTank;
import mekanism.api.datamaps.IMekanismDataMapTypes;
import mekanism.api.datamaps.chemical.attribute.ChemicalFuel;
import mekanism.api.energy.IEnergyContainer;
import mekanism.generators.common.tile.TileEntityGasGenerator;
import net.minecraft.server.level.ServerLevel;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static mekanism.api.MekanismAPI.logger;

@Mixin(value = TileEntityGasGenerator.class, remap = false)
public class MixinTileEntityGasGenerator {

    @Shadow
    @Nullable
    private ChemicalFuel cachedFuel;

    @Inject(method = "onUpdateServer(Lnet/minecraft/server/level/ServerLevel;)Z", at = @At("HEAD"))
    private void onUpdateServerHead(ServerLevel level, CallbackInfoReturnable<Boolean> cir) {
        TileEntityGasGenerator self = (TileEntityGasGenerator) (Object) this;
        IChemicalTank fuelTank = self.getFuelTank();
        if (!fuelTank.isEmpty() && cachedFuel == null) {
            cachedFuel = fuelTank.resource().getData(IMekanismDataMapTypes.INSTANCE.chemicalFuel());
        }

        if (!MSConfig.usageConfig.gasGeneratorDebug.get()) {
            return;
        }

        boolean tankEmpty = fuelTank.isEmpty();
        IEnergyContainer energyContainer = self.getEnergyContainer();
        long energyStored = energyContainer == null ? 0 : energyContainer.getAmountAsLong();
        long energyCapacity = energyContainer == null ? 0 : energyContainer.getCapacityAsLong();
        boolean canFunction = self.canFunction();

        logger.info("[GasGenerator Debug] Tick: tankEmpty={}, canFunction={}, cachedFuel={}, energyStored={}/{}, fuelTankStored={}",
                tankEmpty, canFunction, cachedFuel, energyStored, energyCapacity, tankEmpty ? 0 : fuelTank.amountAsLong());

        if (!tankEmpty) {
            ChemicalResource resource = fuelTank.resource();
            ChemicalFuel directFuel = resource.getData(IMekanismDataMapTypes.INSTANCE.chemicalFuel());
            if (cachedFuel != null) {
                logger.info("[GasGenerator Debug] Fuel details: fuel_maxBurnPerTick={}, fuel_energyPerTick={}, resource={}, directFuel={}",
                        cachedFuel.maxBurnPerTick(), cachedFuel.energyPerTick(), resource, directFuel != null);
            } else {
                logger.warn("[GasGenerator Debug] Fuel tank not empty but cachedFuel is null! Resource: {}, directFuel: {}, hasData: {}",
                        resource, directFuel, directFuel != null);
            }
        }
    }
}
