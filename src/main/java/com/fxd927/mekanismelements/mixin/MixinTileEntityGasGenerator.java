package com.fxd927.mekanismelements.mixin;

import mekanism.api.datamaps.chemical.attribute.ChemicalFuel;
import mekanism.generators.common.tile.TileEntityGasGenerator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static mekanism.api.MekanismAPI.logger;

@Mixin(value = TileEntityGasGenerator.class, remap = false)
public class MixinTileEntityGasGenerator {
    @Shadow
    public TileEntityGasGenerator.FuelTank fuelTank;
    
    @Shadow(remap = false)
    private long burnTicks;
    
    @Shadow(remap = false)
    private int maxBurnTicks;
    
    @Shadow(remap = false)
    private long generationRate;
    
    @Inject(method = "onUpdateServer()Z", at = @At("HEAD"))
    private void onUpdateServerHead(CallbackInfoReturnable<Boolean> cir) {
        TileEntityGasGenerator self = (TileEntityGasGenerator) (Object) this;
        boolean tankEmpty = fuelTank.isEmpty();
        long energyNeeded = self.getEnergyContainer().getNeeded();
        long energyStored = self.getEnergyContainer().getEnergy();
        long energyCapacity = self.getEnergyContainer().getMaxEnergy();
        boolean canFunction = self.canFunction();
        long insertResult = self.getEnergyContainer().insert(generationRate, mekanism.api.Action.SIMULATE, mekanism.api.AutomationType.INTERNAL);
        boolean conditionResult = !tankEmpty && canFunction && insertResult == 0L;
        
        logger.info("[GasGenerator Debug] Tick: tankEmpty={}, canFunction={}, generationRate={}, insertResult={}, conditionResult={}, energyStored={}/{}, fuelTankStored={}",
            tankEmpty, canFunction, generationRate, insertResult, conditionResult, energyStored, energyCapacity, tankEmpty ? 0 : fuelTank.getStored());
        
        if (!tankEmpty) {
            ChemicalFuel fuel = fuelTank.getFuel();
            mekanism.api.chemical.ChemicalStack stack = fuelTank.getStack();
            mekanism.api.datamaps.chemical.attribute.ChemicalFuel directFuel = stack.getData(mekanism.api.datamaps.IMekanismDataMapTypes.INSTANCE.chemicalFuel());
            
            if (fuel != null) {
                logger.info("[GasGenerator Debug] Fuel details: fuel_burnTicks={}, fuel_energyPerTick={}, burnTicks={}, maxBurnTicks={}, stack={}, directFuel={}",
                    fuel.burnTicks(), fuel.energyPerTick(), burnTicks, maxBurnTicks, stack, directFuel != null);
            } else {
                logger.warn("[GasGenerator Debug] Fuel tank not empty but getFuel() returned null! Stack: {}, directFuel: {}, hasData: {}", 
                    stack, directFuel, directFuel != null);
            }
        }
    }
}

