package fixdol.mekanismelements.common.tile.machine;

import mekanism.api.chemical.ChemicalType;
import mekanism.api.chemical.merged.MergedChemicalTank;
import mekanism.api.chemical.infuse.InfuseType;
import mekanism.api.chemical.infuse.InfusionStack;
import mekanism.api.chemical.infuse.IInfusionTank;
import mekanism.api.chemical.pigment.Pigment;
import mekanism.api.chemical.pigment.PigmentStack;
import mekanism.api.chemical.pigment.IPigmentTank;
import mekanism.api.chemical.slurry.Slurry;
import mekanism.api.chemical.slurry.SlurryStack;
import mekanism.api.chemical.slurry.ISlurryTank;
import mekanism.api.recipes.outputs.BoxedChemicalOutputHandler;
import mekanism.common.inventory.slot.chemical.MergedChemicalInventorySlot;

import mekanism.api.chemical.gas.Gas;

import mekanism.api.math.FloatingLong;

import fixdol.mekanismelements.api.recipes.AdsorptionRecipe;
import net.minecraft.core.BlockPos;
import mekanism.api.recipes.cache.CachedRecipe;
import mekanism.common.tile.component.config.DataType;
import net.minecraftforge.fluids.FluidStack;
import mekanism.api.chemical.gas.GasStack;
import mekanism.api.IContentsListener;
import mekanism.api.recipes.inputs.IInputHandler;
import fixdol.mekanismelements.common.recipe.IMSRecipeTypeProvider;
import mekanism.api.recipes.outputs.IOutputHandler;
import mekanism.common.recipe.lookup.IRecipeLookupHandler;
import net.minecraft.world.item.ItemStack;
import java.util.List;
import fixdol.mekanismelements.common.registries.MSBlocks;
import fixdol.mekanismelements.common.recipe.lookup.cache.MSInputRecipeCache;
import fixdol.mekanismelements.common.recipe.MSRecipeType;
import mekanism.common.capabilities.energy.MachineEnergyContainer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import mekanism.common.inventory.container.slot.SlotOverlay;
import fixdol.mekanismelements.common.tile.machine.TileEntityAdsorptionSeparator;
import mekanism.common.inventory.warning.WarningTracker;
import mekanism.api.chemical.gas.IGasTank;

import mekanism.common.tile.component.config.ConfigInfo;
import fixdol.mekanismelements.api.recipes.cache.AdsorptionCachedRecipe;
import fixdol.mekanismelements.common.inventory.slot.MSInputInventorySlot;
import fixdol.mekanismelements.common.recipe.lookup.IMSDoubleRecipeLookupHandler;
import fixdol.mekanismelements.common.tile.prefab.MSTileEntityProgressMachine;
import mekanism.api.*;
import mekanism.api.providers.IBlockProvider;
import mekanism.api.chemical.ChemicalTankBuilder;
import mekanism.common.recipe.IMekanismRecipeTypeProvider;
import mekanism.api.recipes.inputs.InputHelper;
import mekanism.api.recipes.outputs.OutputHelper;
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
import mekanism.common.inventory.slot.EnergyInventorySlot;
import mekanism.common.inventory.slot.chemical.GasInventorySlot;
import mekanism.api.RelativeSide;
import mekanism.common.lib.transmitter.TransmissionType;
import mekanism.common.tile.component.TileComponentConfig;
import mekanism.common.tile.component.TileComponentEjector;
import mekanism.common.util.MekanismUtils;
import mekanism.common.util.StatUtils;
import net.minecraft.world.level.block.state.BlockState;




public class TileEntityAdsorptionSeparator extends MSTileEntityProgressMachine<AdsorptionRecipe> implements
        IMSDoubleRecipeLookupHandler.ItemFluidRecipeLookupHandler<AdsorptionRecipe>,
        IRecipeLookupHandler<AdsorptionRecipe> {
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
        public MergedChemicalTank outputTank;
        public double injectUsage = 1;

        private final BoxedChemicalOutputHandler outputHandler;
        private final IInputHandler<@NotNull ItemStack> itemInputHandler;
        private final IInputHandler<@NotNull FluidStack> fluidInputHandler;

        private MachineEnergyContainer<fixdol.mekanismelements.common.tile.machine.TileEntityAdsorptionSeparator> energyContainer;
        @WrappingComputerMethod(wrapper = SpecialComputerMethodWrapper.ComputerIInventorySlotWrapper.class, methodNames = "getInputItem", docPlaceholder = "input slot")
        MSInputInventorySlot inputSlot;
        @WrappingComputerMethod(wrapper = SpecialComputerMethodWrapper.ComputerIInventorySlotWrapper.class, methodNames = "getOutputItem", docPlaceholder = "output slot")
        MergedChemicalInventorySlot<MergedChemicalTank> outputSlot;
        @WrappingComputerMethod(wrapper = SpecialComputerMethodWrapper.ComputerIInventorySlotWrapper.class, methodNames = "getEnergyItem", docPlaceholder = "energy slot")
        EnergyInventorySlot energySlot;

        public TileEntityAdsorptionSeparator(BlockPos pos, BlockState state) {
            super(MSBlocks.ADSORPTION_SEPARATOR, pos, state, TRACKED_ERROR_TYPES, BASE_TICKS_REQUIRED);
        configComponent = new TileComponentConfig(this, mekanism.common.lib.transmitter.TransmissionType.ITEM, mekanism.common.lib.transmitter.TransmissionType.FLUID, mekanism.common.lib.transmitter.TransmissionType.GAS, mekanism.common.lib.transmitter.TransmissionType.INFUSION, mekanism.common.lib.transmitter.TransmissionType.PIGMENT, mekanism.common.lib.transmitter.TransmissionType.SLURRY, mekanism.common.lib.transmitter.TransmissionType.ENERGY);
            // Config is created from block attributes in parent constructor
            getConfig().setupItemIOConfig(inputSlot, outputSlot, energySlot);
            
            // Fluid Input Config - LEFT side default
            ConfigInfo fluidConfig = getConfig().setupInputConfig(TransmissionType.FLUID, inputTank);
            if (fluidConfig != null) {
                fluidConfig.setDataType(DataType.INPUT, RelativeSide.LEFT);
                fluidConfig.setDataType(DataType.INPUT, RelativeSide.BACK);
            }
            
            // Chemical Output Config - RIGHT side default
            for (TransmissionType chemicalTransmission : new TransmissionType[]{TransmissionType.GAS, TransmissionType.INFUSION, TransmissionType.PIGMENT, TransmissionType.SLURRY}) {
                ConfigInfo chemicalConfig = getConfig().setupOutputConfig(chemicalTransmission, outputTank.getTankForType(chemicalTypeFor(chemicalTransmission)), RelativeSide.RIGHT);
                if (chemicalConfig != null) {
                    chemicalConfig.setDataType(DataType.OUTPUT, RelativeSide.RIGHT);
                    chemicalConfig.setDataType(DataType.OUTPUT, RelativeSide.FRONT);
                }
            }
            
            // Energy Config - all sides accept
            ConfigInfo energyConfig = getConfig().setupInputConfig(TransmissionType.ENERGY, energyContainer);
            if (energyConfig != null) {
                for (RelativeSide side : RelativeSide.values()) {
                    energyConfig.setDataType(DataType.INPUT, side);
                }
            }

            ejectorComponent = new TileComponentEjector(this);
            ejectorComponent.setOutputData(getConfig(), TransmissionType.ITEM, TransmissionType.FLUID, TransmissionType.GAS, TransmissionType.INFUSION, TransmissionType.PIGMENT, TransmissionType.SLURRY)
                    .setCanTankEject(tank -> tank != inputTank);

            itemInputHandler = InputHelper.getInputHandler(inputSlot, CachedRecipe.OperationTracker.RecipeError.NOT_ENOUGH_INPUT);
            fluidInputHandler = InputHelper.getInputHandler(inputTank, CachedRecipe.OperationTracker.RecipeError.NOT_ENOUGH_SECONDARY_INPUT);
            outputHandler = new BoxedChemicalOutputHandler(outputTank, CachedRecipe.OperationTracker.RecipeError.NOT_ENOUGH_OUTPUT_SPACE);
        }

    @Override
    public net.minecraft.world.level.Level getHandlerWorld() {
        return getLevel();
    }

    protected void presetVariables() {
        super.presetVariables();
        inputTank = BasicFluidTank.create((int) MAX_CHEMICAL, this::containsRecipeB, this::containsRecipeB, recipeCacheLookupMonitor);
        outputTank = MergedChemicalTank.create(
                ChemicalTankBuilder.GAS.output(MAX_CHEMICAL, recipeCacheLookupMonitor),
                ChemicalTankBuilder.INFUSION.output(MAX_CHEMICAL, recipeCacheLookupMonitor),
                ChemicalTankBuilder.PIGMENT.output(MAX_CHEMICAL, recipeCacheLookupMonitor),
                ChemicalTankBuilder.SLURRY.output(MAX_CHEMICAL, recipeCacheLookupMonitor));
        energyContainer = MachineEnergyContainer.input(this, recipeCacheLookupMonitor);
    }

    @NotNull
    @Override
    protected IFluidTankHolder getInitialFluidTanks(IContentsListener listener, IContentsListener recipeCacheListener) {
        FluidTankHelper builder = FluidTankHelper.forSideWithConfig(this::getDirection, this::getConfig);
        builder.addTank(inputTank);
        return builder.build();
    }

    @NotNull
    @Override
    protected IChemicalTankHolder<Gas, GasStack, IGasTank> getInitialGasTanks(IContentsListener listener, IContentsListener recipeCacheListener) {
        ChemicalTankHelper<Gas, GasStack, IGasTank> builder = ChemicalTankHelper.forSideGasWithConfig(this::getDirection, this::getConfig);
        builder.addTank(outputTank.getGasTank());
        return builder.build();
    }

    @NotNull
    @Override
    protected IChemicalTankHolder<InfuseType, InfusionStack, IInfusionTank> getInitialInfusionTanks(IContentsListener listener, IContentsListener recipeCacheListener) {
        ChemicalTankHelper<InfuseType, InfusionStack, IInfusionTank> builder = ChemicalTankHelper.forSideInfusionWithConfig(this::getDirection, this::getConfig);
        builder.addTank(outputTank.getInfusionTank());
        return builder.build();
    }

    @NotNull
    @Override
    protected IChemicalTankHolder<Pigment, PigmentStack, IPigmentTank> getInitialPigmentTanks(IContentsListener listener, IContentsListener recipeCacheListener) {
        ChemicalTankHelper<Pigment, PigmentStack, IPigmentTank> builder = ChemicalTankHelper.forSidePigmentWithConfig(this::getDirection, this::getConfig);
        builder.addTank(outputTank.getPigmentTank());
        return builder.build();
    }

    @NotNull
    @Override
    protected IChemicalTankHolder<Slurry, SlurryStack, ISlurryTank> getInitialSlurryTanks(IContentsListener listener, IContentsListener recipeCacheListener) {
        ChemicalTankHelper<Slurry, SlurryStack, ISlurryTank> builder = ChemicalTankHelper.forSideSlurryWithConfig(this::getDirection, this::getConfig);
        builder.addTank(outputTank.getSlurryTank());
        return builder.build();
    }

        @NotNull
        @Override
        protected IEnergyContainerHolder getInitialEnergyContainers(IContentsListener listener, IContentsListener recipeCacheListener) {
            EnergyContainerHelper builder = EnergyContainerHelper.forSideWithConfig(this::getDirection, this::getConfig);
            builder.addContainer(energyContainer = MachineEnergyContainer.input(this, () -> {
            listener.onContentsChanged();
            recipeCacheListener.onContentsChanged();
        }));
            return builder.build();
        }

    @NotNull
    @Override
    protected IInventorySlotHolder getInitialInventory(IContentsListener listener, IContentsListener recipeCacheListener) {
        InventorySlotHelper builder = InventorySlotHelper.forSideWithConfig(this::getDirection, this::getConfig);
        builder.addSlot(inputSlot = MSInputInventorySlot.at(item -> containsRecipeAB(item, inputTank.getFluid()), this::containsRecipeA, recipeCacheListener, 80, 22))
                .tracksWarnings(slot -> slot.warning(WarningTracker.WarningType.NO_MATCHING_RECIPE, getWarningCheck(CachedRecipe.OperationTracker.RecipeError.NOT_ENOUGH_INPUT)));
        builder.addSlot(outputSlot = MergedChemicalInventorySlot.drain(outputTank, recipeCacheListener, 152, 55));
        builder.addSlot(energySlot = EnergyInventorySlot.fillOrConvert(energyContainer, this::getLevel, recipeCacheListener, 152, 14));
        outputSlot.setSlotOverlay(SlotOverlay.PLUS);
        return builder.build();
    }

        @Override
    protected void onUpdateServer() {
        super.onUpdateServer();
        energySlot.fillContainerOrConvert();
        outputSlot.drainChemicalTanks();
        if (recipeCacheLookupMonitor.updateAndProcess()) {
        }
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
                    .setCanHolderFunction(() -> MekanismUtils.canFunction(this))
                    .setActive(this::setActive)
                    .setEnergyRequirements(energyContainer::getEnergyPerTick, energyContainer)
                    .setRequiredTicks(this::getTicksRequired)
                    .setOnFinish(this::markForSave)
                    .setOperatingTicksChanged(this::setOperatingTicks);
        }



        @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public IMekanismRecipeTypeProvider<AdsorptionRecipe, ?> getRecipeType() {
        return (IMekanismRecipeTypeProvider) getMSRecipeType();
    }

    @Override
    public void onCachedRecipeChanged(@Nullable mekanism.api.recipes.cache.CachedRecipe<AdsorptionRecipe> cachedRecipe, int cacheIndex) {
        clearRecipeErrors(cacheIndex);
    }

    public MachineEnergyContainer<fixdol.mekanismelements.common.tile.machine.TileEntityAdsorptionSeparator> getEnergyContainer() {
            return energyContainer;
        }

        @ComputerMethod(methodDescription = ComputerConstants.DESCRIPTION_GET_ENERGY_USAGE)
        FloatingLong getEnergyUsage() {
            return getActive() ? energyContainer.getEnergyPerTick() : FloatingLong.ZERO;
        }

        public MergedChemicalTank getOutputTank() {
            return outputTank;
        }

        private static ChemicalType chemicalTypeFor(TransmissionType transmissionType) {
            return switch (transmissionType) {
                case INFUSION -> ChemicalType.INFUSION;
                case PIGMENT -> ChemicalType.PIGMENT;
                case SLURRY -> ChemicalType.SLURRY;
                default -> ChemicalType.GAS;
            };
        }
}