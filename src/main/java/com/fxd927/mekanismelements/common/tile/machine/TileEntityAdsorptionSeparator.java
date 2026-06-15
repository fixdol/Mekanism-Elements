package com.fxd927.mekanismelements.common.tile.machine;

import mekanism.common.tile.component.config.ConfigInfo;
import mekanism.common.tile.component.config.DataType;
import com.fxd927.mekanismelements.api.recipes.AdsorptionRecipe;
import com.fxd927.mekanismelements.api.recipes.cache.AdsorptionCachedRecipe;
import com.fxd927.mekanismelements.common.inventory.slot.MSInputInventorySlot;
import com.fxd927.mekanismelements.common.recipe.IMSRecipeTypeProvider;
import com.fxd927.mekanismelements.common.recipe.MSRecipeType;
import com.fxd927.mekanismelements.common.recipe.lookup.IMSDoubleRecipeLookupHandler;
import com.fxd927.mekanismelements.common.recipe.lookup.cache.MSInputRecipeCache;
import com.fxd927.mekanismelements.common.registries.MSBlocks;
import com.fxd927.mekanismelements.common.tile.prefab.MSTileEntityProgressMachine;
import mekanism.api.*;
import mekanism.api.providers.IBlockProvider;
import mekanism.common.registration.impl.BlockRegistryObject;
import mekanism.api.chemical.ChemicalStack;
import mekanism.api.chemical.Chemical;
import mekanism.api.chemical.ChemicalStack;
import mekanism.api.chemical.IChemicalTank;
import mekanism.api.chemical.BasicChemicalTank;
import mekanism.api.recipes.cache.CachedRecipe;
import mekanism.api.recipes.inputs.IInputHandler;
import mekanism.api.recipes.inputs.InputHelper;
import mekanism.api.recipes.outputs.IOutputHandler;
import mekanism.api.recipes.outputs.OutputHelper;
import mekanism.common.capabilities.energy.MachineEnergyContainer;
import mekanism.common.capabilities.fluid.BasicFluidTank;
import mekanism.common.capabilities.holder.chemical.ChemicalTankHelper;
import mekanism.common.capabilities.holder.chemical.IChemicalTankHolder;
import mekanism.common.capabilities.holder.energy.EnergyContainerHelper;
import mekanism.common.capabilities.holder.energy.IEnergyContainerHolder;
import mekanism.common.capabilities.holder.fluid.FluidTankHelper;
import mekanism.common.capabilities.holder.fluid.IFluidTankHolder;
import mekanism.common.capabilities.holder.slot.IInventorySlotHolder;
import mekanism.common.capabilities.holder.slot.InventorySlotHelper;
import mekanism.common.integration.computer.SpecialComputerMethodWrapper;
import mekanism.common.integration.computer.annotation.ComputerMethod;
import mekanism.common.integration.computer.annotation.WrappingComputerMethod;
import mekanism.common.integration.computer.computercraft.ComputerConstants;
import mekanism.common.inventory.container.slot.SlotOverlay;
import mekanism.common.inventory.slot.EnergyInventorySlot;
import mekanism.common.inventory.slot.chemical.ChemicalInventorySlot;
import mekanism.common.inventory.warning.WarningTracker;
import mekanism.api.RelativeSide;
import mekanism.common.lib.transmitter.TransmissionType;
import mekanism.common.tile.component.TileComponentConfig;
import mekanism.common.tile.component.TileComponentEjector;
import mekanism.common.util.MekanismUtils;
import mekanism.common.util.StatUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.fluids.FluidStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Set;



public class TileEntityAdsorptionSeparator extends MSTileEntityProgressMachine<AdsorptionRecipe> implements
        IMSDoubleRecipeLookupHandler.ItemFluidRecipeLookupHandler<AdsorptionRecipe> {
        private static final List<CachedRecipe.OperationTracker.RecipeError> TRACKED_ERROR_TYPES = List.of(
                CachedRecipe.OperationTracker.RecipeError.NOT_ENOUGH_ENERGY,
                CachedRecipe.OperationTracker.RecipeError.NOT_ENOUGH_ENERGY_REDUCED_RATE,
                CachedRecipe.OperationTracker.RecipeError.NOT_ENOUGH_INPUT,
                CachedRecipe.OperationTracker.RecipeError.NOT_ENOUGH_SECONDARY_INPUT,
                CachedRecipe.OperationTracker.RecipeError.NOT_ENOUGH_OUTPUT_SPACE,
                CachedRecipe.OperationTracker.RecipeError.INPUT_DOESNT_PRODUCE_OUTPUT
        );
        private static final long MAX_CHEMICAL = 10_000;
        public static final int BASE_TICKS_REQUIRED = 20;

        @WrappingComputerMethod(wrapper = SpecialComputerMethodWrapper.ComputerFluidTankWrapper.class, methodNames = {"getChemicalInput", "getChemicalInputCapacity", "getChemicalInputNeeded",
                "getChemicalInputFilledPercentage"}, docPlaceholder = "chemical input tank")
        public BasicFluidTank inputTank;
        public IChemicalTank chemicalOutputTank;
        public double injectUsage = 1;

        private final IOutputHandler<ChemicalStack> outputHandler;
        private final IInputHandler<@NotNull ItemStack> itemInputHandler;
        private final IInputHandler<@NotNull FluidStack> fluidInputHandler;

        private MachineEnergyContainer<com.fxd927.mekanismelements.common.tile.machine.TileEntityAdsorptionSeparator> energyContainer;
        @WrappingComputerMethod(wrapper = SpecialComputerMethodWrapper.ComputerIInventorySlotWrapper.class, methodNames = "getInputItem", docPlaceholder = "input slot")
        MSInputInventorySlot inputSlot;
        @WrappingComputerMethod(wrapper = SpecialComputerMethodWrapper.ComputerIInventorySlotWrapper.class, methodNames = "getOutputItem", docPlaceholder = "output slot")
        ChemicalInventorySlot outputSlot;
        @WrappingComputerMethod(wrapper = SpecialComputerMethodWrapper.ComputerIInventorySlotWrapper.class, methodNames = "getEnergyItem", docPlaceholder = "energy slot")
        EnergyInventorySlot energySlot;

        public TileEntityAdsorptionSeparator(BlockPos pos, BlockState state) {
            super(MSBlocks.ADSORPTION_SEPARATOR, pos, state, TRACKED_ERROR_TYPES, BASE_TICKS_REQUIRED);
            // Config is created from block attributes in parent constructor
            getConfig().setupItemIOConfig(inputSlot, outputSlot, energySlot);
            
            // Fluid Input Config - LEFT side default
            ConfigInfo fluidConfig = getConfig().setupInputConfig(TransmissionType.FLUID, inputTank);
            if (fluidConfig != null) {
                fluidConfig.setDataType(DataType.INPUT, RelativeSide.LEFT);
                fluidConfig.setDataType(DataType.INPUT, RelativeSide.BACK);
            }
            
            // Chemical Output Config - RIGHT side default
            ConfigInfo chemicalConfig = getConfig().setupOutputConfig(TransmissionType.CHEMICAL, chemicalOutputTank, RelativeSide.RIGHT);
            if (chemicalConfig != null) {
                chemicalConfig.setDataType(DataType.OUTPUT, RelativeSide.RIGHT);
                chemicalConfig.setDataType(DataType.OUTPUT, RelativeSide.FRONT);
            }
            
            // Energy Config - all sides accept
            ConfigInfo energyConfig = getConfig().setupInputConfig(TransmissionType.ENERGY, energyContainer);
            if (energyConfig != null) {
                for (RelativeSide side : RelativeSide.values()) {
                    energyConfig.setDataType(DataType.INPUT, side);
                }
            }

            ejectorComponent = new TileComponentEjector(this);
            ejectorComponent.setOutputData(getConfig(), TransmissionType.ITEM, TransmissionType.FLUID, TransmissionType.CHEMICAL)
                    .setCanTankEject(tank -> tank != inputTank);

            itemInputHandler = InputHelper.getInputHandler(inputSlot, CachedRecipe.OperationTracker.RecipeError.NOT_ENOUGH_INPUT);
            fluidInputHandler = InputHelper.getInputHandler(inputTank, CachedRecipe.OperationTracker.RecipeError.NOT_ENOUGH_SECONDARY_INPUT);
            outputHandler = OutputHelper.getOutputHandler(chemicalOutputTank, CachedRecipe.OperationTracker.RecipeError.NOT_ENOUGH_OUTPUT_SPACE);
        }

    @Override
    protected void presetVariables() {
        super.presetVariables();
        inputTank = BasicFluidTank.create((int) MAX_CHEMICAL, this::containsRecipeB, this::containsRecipeB, recipeCacheLookupMonitor);
        chemicalOutputTank = BasicChemicalTank.output(MAX_CHEMICAL, recipeCacheLookupMonitor);
        energyContainer = MachineEnergyContainer.input(this, recipeCacheLookupMonitor);
    }

    @NotNull
    @Override
    protected IFluidTankHolder getInitialFluidTanks(IContentsListener listener, IContentsListener recipeCacheListener) {
        FluidTankHelper builder = FluidTankHelper.forSideWithConfig(this);
        builder.addTank(inputTank);
        return builder.build();
    }

    @NotNull
    @Override
    public IChemicalTankHolder getInitialChemicalTanks(IContentsListener listener) {
        ChemicalTankHelper builder = ChemicalTankHelper.forSideWithConfig(this);
        builder.addTank(chemicalOutputTank);
        return builder.build();
    }

        @NotNull
        @Override
        protected IEnergyContainerHolder getInitialEnergyContainers(IContentsListener listener, IContentsListener recipeCacheListener) {
            EnergyContainerHelper builder = EnergyContainerHelper.forSideWithConfig(this);
            builder.addContainer(energyContainer = MachineEnergyContainer.input(this, () -> {
            listener.onContentsChanged();
            recipeCacheListener.onContentsChanged();
        }));
            return builder.build();
        }

    @NotNull
    @Override
    protected IInventorySlotHolder getInitialInventory(IContentsListener listener, IContentsListener recipeCacheListener) {
        InventorySlotHelper builder = InventorySlotHelper.forSideWithConfig(this);
        builder.addSlot(inputSlot = MSInputInventorySlot.at(item -> containsRecipeAB(item, inputTank.getFluid()), this::containsRecipeA, recipeCacheListener, 80, 22))
                .tracksWarnings(slot -> slot.warning(WarningTracker.WarningType.NO_MATCHING_RECIPE, getWarningCheck(CachedRecipe.OperationTracker.RecipeError.NOT_ENOUGH_INPUT)));
        builder.addSlot(outputSlot = ChemicalInventorySlot.drain(chemicalOutputTank, recipeCacheListener, 152, 55));
        builder.addSlot(energySlot = EnergyInventorySlot.fillOrConvert(energyContainer, this::getLevel, recipeCacheListener, 152, 14));
        outputSlot.setSlotOverlay(SlotOverlay.PLUS);
        return builder.build();
    }

        @Override
    protected boolean onUpdateServer() {
        boolean needsUpdate = super.onUpdateServer();
        energySlot.fillContainerOrConvert();
        outputSlot.drainTank();
        if (recipeCacheLookupMonitor.updateAndProcess()) {
            needsUpdate = true;
        }
        
        return needsUpdate;
    }

        @Override
        public IMSRecipeTypeProvider<AdsorptionRecipe, MSInputRecipeCache.ItemFluid<AdsorptionRecipe>> getMSRecipeType() {
            return MSRecipeType.ADSORPTION;
        }

        @Nullable
        @Override
        public AdsorptionRecipe getRecipe(int cacheIndex) {
            return findFirstRecipe(itemInputHandler, fluidInputHandler);
        }

        @NotNull
        @Override
        public CachedRecipe<AdsorptionRecipe> createNewCachedRecipe(@NotNull AdsorptionRecipe recipe, int cacheIndex) {
            return new AdsorptionCachedRecipe(recipe, recheckAllRecipeErrors, itemInputHandler, fluidInputHandler, () -> StatUtils.inversePoisson(injectUsage), outputHandler)
                    .setErrorsChanged(this::onErrorsChanged)
                    .setCanHolderFunction(this::canFunction)
                    .setActive(this::setActive)
                    .setEnergyRequirements(energyContainer::getEnergyPerTick, energyContainer)
                    .setRequiredTicks(this::getTicksRequired)
                    .setOnFinish(this::markForSave)
                    .setOperatingTicksChanged(this::setOperatingTicks);
        }

        public MachineEnergyContainer<com.fxd927.mekanismelements.common.tile.machine.TileEntityAdsorptionSeparator> getEnergyContainer() {
            return energyContainer;
        }

        @ComputerMethod(methodDescription = ComputerConstants.DESCRIPTION_GET_ENERGY_USAGE)
        long getEnergyUsage() {
            return getActive() ? energyContainer.getEnergyPerTick() : 0;
        }

        @WrappingComputerMethod(wrapper = SpecialComputerMethodWrapper.ComputerChemicalTankWrapper.class, methodNames = {"getOutput", "getOutputCapacity", "getOutputNeeded", "getOutputFilledPercentage"}, docPlaceholder = "output tank")
        IChemicalTank getOutputTank() {
            // Return the chemical tank as default, or determine based on current output
            return chemicalOutputTank;
        }
}
