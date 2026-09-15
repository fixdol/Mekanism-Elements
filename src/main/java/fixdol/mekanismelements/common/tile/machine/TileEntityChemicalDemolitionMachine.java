package fixdol.mekanismelements.common.tile.machine;

import mekanism.common.tile.component.TileComponentConfig;

import mekanism.api.chemical.gas.Gas;

import mekanism.api.math.FloatingLong;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import mekanism.api.recipes.cache.CachedRecipe;
import fixdol.mekanismelements.api.recipes.ChemicalDemolitionRecipe;
import mekanism.api.chemical.ChemicalTankBuilder;
import mekanism.common.capabilities.holder.chemical.ChemicalTankHelper;
import mekanism.common.integration.computer.computercraft.ComputerConstants;
import mekanism.common.integration.computer.annotation.ComputerMethod;
import mekanism.common.tile.component.config.ConfigInfo;
import mekanism.api.functions.ConstantPredicates;
import mekanism.common.capabilities.holder.energy.EnergyContainerHelper;
import mekanism.common.inventory.slot.EnergyInventorySlot;
import mekanism.common.inventory.slot.chemical.GasInventorySlot;
import mekanism.api.chemical.gas.GasStack;
import mekanism.common.capabilities.holder.chemical.IChemicalTankHolder;
import mekanism.api.IContentsListener;
import mekanism.common.capabilities.holder.energy.IEnergyContainerHolder;
import mekanism.api.chemical.gas.IGasTank;
import mekanism.api.recipes.inputs.IInputHandler;
import mekanism.common.capabilities.holder.slot.IInventorySlotHolder;
import mekanism.api.recipes.inputs.ILongInputHandler;
import fixdol.mekanismelements.common.recipe.lookup.IMSDoubleRecipeLookupHandler;
import fixdol.mekanismelements.common.recipe.IMSRecipeTypeProvider;
import mekanism.common.recipe.IMekanismRecipeTypeProvider;
import mekanism.api.recipes.outputs.IOutputHandler;
import mekanism.common.recipe.lookup.IRecipeLookupHandler;
import mekanism.api.recipes.inputs.InputHelper;
import mekanism.common.capabilities.holder.slot.InventorySlotHelper;
import net.minecraft.world.item.ItemStack;
import java.util.List;
import fixdol.mekanismelements.common.registries.MSBlocks;
import fixdol.mekanismelements.common.recipe.lookup.cache.MSInputRecipeCache;
import fixdol.mekanismelements.common.recipe.MSRecipeType;
import fixdol.mekanismelements.common.tile.prefab.MSTileEntityProgressMachine;
import mekanism.common.capabilities.energy.MachineEnergyContainer;
import mekanism.common.util.MekanismUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import mekanism.api.recipes.outputs.OutputHelper;
import mekanism.common.inventory.container.slot.SlotOverlay;
import mekanism.common.integration.computer.SpecialComputerMethodWrapper;
import mekanism.common.util.StatUtils;
import mekanism.common.tile.component.TileComponentEjector;
import fixdol.mekanismelements.common.tile.machine.TileEntityChemicalDemolitionMachine;
import mekanism.common.lib.transmitter.TransmissionType;
import mekanism.api.Upgrade;
import mekanism.common.inventory.warning.WarningTracker;
import mekanism.common.integration.computer.annotation.WrappingComputerMethod;

import fixdol.mekanismelements.api.recipes.cache.ChemicalDemolitionCachedRecipe;
import mekanism.common.inventory.slot.InputInventorySlot;
import mekanism.common.inventory.slot.OutputInventorySlot;


public class TileEntityChemicalDemolitionMachine extends MSTileEntityProgressMachine<ChemicalDemolitionRecipe> implements
        IMSDoubleRecipeLookupHandler.ItemChemicalRecipeLookupHandler<ChemicalDemolitionRecipe>,
        IRecipeLookupHandler<ChemicalDemolitionRecipe> {
    public static final CachedRecipe.OperationTracker.RecipeError NOT_ENOUGH_SPACE_SECOND_OUTPUT_ERROR = CachedRecipe.OperationTracker.RecipeError.create();
    private static final List<CachedRecipe.OperationTracker.RecipeError> TRACKED_ERROR_TYPES = List.of(
            CachedRecipe.OperationTracker.RecipeError.NOT_ENOUGH_ENERGY,
            CachedRecipe.OperationTracker.RecipeError.NOT_ENOUGH_ENERGY_REDUCED_RATE,
            CachedRecipe.OperationTracker.RecipeError.NOT_ENOUGH_INPUT,
            CachedRecipe.OperationTracker.RecipeError.NOT_ENOUGH_OUTPUT_SPACE,
            NOT_ENOUGH_SPACE_SECOND_OUTPUT_ERROR,
            CachedRecipe.OperationTracker.RecipeError.INPUT_DOESNT_PRODUCE_OUTPUT
    );
    private static final long MAX_CHEMICAL = 10_000;
    public static final int BASE_TICKS_REQUIRED = 100;

    @WrappingComputerMethod(wrapper = SpecialComputerMethodWrapper.ComputerChemicalTankWrapper.class, methodNames = {"getChemicalInput", "getChemicalInputCapacity", "getChemicalInputNeeded",
            "getChemicalInputFilledPercentage"}, docPlaceholder = "chemical input tank")
    public IGasTank injectTank;
    public double injectUsage = 1;

    private final IOutputHandler firstOutputHandler;
    private final IOutputHandler secondOutputHandler;
    private final IInputHandler<@NotNull ItemStack> itemInputHandler;
    private final ILongInputHandler<@NotNull GasStack> chemicalInputHandler;

    private MachineEnergyContainer<TileEntityChemicalDemolitionMachine> energyContainer;
    @WrappingComputerMethod(wrapper = SpecialComputerMethodWrapper.ComputerIInventorySlotWrapper.class, methodNames = "getInputChemicalItem", docPlaceholder = "chemical input item slot")
    GasInventorySlot chemicalInputSlot;
    @WrappingComputerMethod(wrapper = SpecialComputerMethodWrapper.ComputerIInventorySlotWrapper.class, methodNames = "getInputItem", docPlaceholder = "input slot")
    InputInventorySlot inputSlot;
    @WrappingComputerMethod(wrapper = SpecialComputerMethodWrapper.ComputerIInventorySlotWrapper.class, methodNames = "getOutputItem", docPlaceholder = "output slot")
    OutputInventorySlot firstOutputSlot;
    @WrappingComputerMethod(wrapper = SpecialComputerMethodWrapper.ComputerIInventorySlotWrapper.class, methodNames = "getOutputItem", docPlaceholder = "output slot")
    OutputInventorySlot secondOutputSlot;
    @WrappingComputerMethod(wrapper = SpecialComputerMethodWrapper.ComputerIInventorySlotWrapper.class, methodNames = "getEnergyItem", docPlaceholder = "energy slot")
    EnergyInventorySlot energySlot;

    public TileEntityChemicalDemolitionMachine(BlockPos pos, BlockState state) {
        super(MSBlocks.CHEMICAL_DEMOLITION_MACHINE, pos, state, TRACKED_ERROR_TYPES, BASE_TICKS_REQUIRED);
        configComponent = new TileComponentConfig(this, mekanism.common.lib.transmitter.TransmissionType.ITEM, mekanism.common.lib.transmitter.TransmissionType.GAS, mekanism.common.lib.transmitter.TransmissionType.ENERGY);
        // Config is created from block attributes in parent constructor
        ConfigInfo itemConfig = getConfig().setupItemIOConfig(List.of(inputSlot), List.of(firstOutputSlot, secondOutputSlot), energySlot, false);
        if (itemConfig != null) {
            itemConfig.addSlotInfo(mekanism.common.tile.component.config.DataType.EXTRA,
                  new mekanism.common.tile.component.config.slot.InventorySlotInfo(true, true, chemicalInputSlot));
        }
        
        // Chemical Input Config - LEFT/BACK sides
        ConfigInfo chemicalConfig = getConfig().setupInputConfig(TransmissionType.GAS, injectTank);
        if (chemicalConfig != null) {
            chemicalConfig.setDataType(mekanism.common.tile.component.config.DataType.INPUT, mekanism.api.RelativeSide.LEFT);
            chemicalConfig.setDataType(mekanism.common.tile.component.config.DataType.INPUT, mekanism.api.RelativeSide.BACK);
            chemicalConfig.setEjecting(true);
        }
        
        // Energy Config - all sides accept
        ConfigInfo energyConfig = getConfig().setupInputConfig(TransmissionType.ENERGY, energyContainer);
        if (energyConfig != null) {
            for (mekanism.api.RelativeSide side : mekanism.api.RelativeSide.values()) {
                energyConfig.setDataType(mekanism.common.tile.component.config.DataType.INPUT, side);
            }
        }

        ejectorComponent = new TileComponentEjector(this);
        ejectorComponent.setOutputData(getConfig(), TransmissionType.ITEM, TransmissionType.GAS)
                .setCanTankEject(tank -> tank != injectTank);

        itemInputHandler = InputHelper.getInputHandler(inputSlot, CachedRecipe.OperationTracker.RecipeError.NOT_ENOUGH_INPUT);
        chemicalInputHandler = InputHelper.getInputHandler(injectTank, CachedRecipe.OperationTracker.RecipeError.NOT_ENOUGH_SECONDARY_INPUT);
        firstOutputHandler = OutputHelper.getOutputHandler(firstOutputSlot, CachedRecipe.OperationTracker.RecipeError.NOT_ENOUGH_OUTPUT_SPACE);
        secondOutputHandler = OutputHelper.getOutputHandler(secondOutputSlot, NOT_ENOUGH_SPACE_SECOND_OUTPUT_ERROR);
    }

    @Override
    public net.minecraft.world.level.Level getHandlerWorld() {
        return getLevel();
    }

    protected void presetVariables() {
        super.presetVariables();
        injectTank = ChemicalTankBuilder.GAS.create(MAX_CHEMICAL, allowExtractingChemical() ? ConstantPredicates.alwaysTrueBi() : ConstantPredicates.notExternal(),
                (chemical, automationType) -> containsRecipeBA(inputSlot.getStack(), chemical), this::containsRecipeB, recipeCacheLookupMonitor);
        energyContainer = MachineEnergyContainer.input(this, recipeCacheLookupMonitor);
    }

    @NotNull
    @Override
    protected IChemicalTankHolder getInitialGasTanks(IContentsListener listener, IContentsListener recipeCacheListener) {
        ChemicalTankHelper builder = ChemicalTankHelper.forSideGasWithConfig(this::getDirection, this::getConfig);
        builder.addTank(injectTank);
        return builder.build();
    }

    protected boolean allowExtractingChemical() {
        return !useStatisticalMechanics();
    }

    protected boolean useStatisticalMechanics() {
        return false;
    }

    @NotNull
    @Override
    protected IEnergyContainerHolder getInitialEnergyContainers(IContentsListener listener, IContentsListener recipeCacheListener) {
        EnergyContainerHelper builder = EnergyContainerHelper.forSideWithConfig(this::getDirection, this::getConfig);
        builder.addContainer(energyContainer);
        return builder.build();
    }

    @NotNull
    @Override
    protected IInventorySlotHolder getInitialInventory(IContentsListener listener, IContentsListener recipeCacheListener) {
        InventorySlotHelper builder = InventorySlotHelper.forSideWithConfig(this::getDirection, this::getConfig);
        builder.addSlot(chemicalInputSlot = GasInventorySlot.fillOrConvert(injectTank, this::getLevel, recipeCacheListener, 8, 65));
        builder.addSlot(inputSlot = InputInventorySlot.at(item -> containsRecipeAB(item, injectTank.getStack()), this::containsRecipeA, recipeCacheListener, 28, 36))
                .tracksWarnings(slot -> slot.warning(WarningTracker.WarningType.NO_MATCHING_RECIPE, getWarningCheck(CachedRecipe.OperationTracker.RecipeError.NOT_ENOUGH_INPUT)));
        builder.addSlot(firstOutputSlot = OutputInventorySlot.at(recipeCacheListener, 116, 35));
        builder.addSlot(secondOutputSlot = OutputInventorySlot.at(recipeCacheListener, 132, 35));
        builder.addSlot(energySlot = EnergyInventorySlot.fillOrConvert(energyContainer, this::getLevel, recipeCacheListener, 154, 62));
        chemicalInputSlot.setSlotOverlay(SlotOverlay.MINUS);
        return builder.build();
    }

    @Override
    protected void onUpdateServer() {
        super.onUpdateServer();
        energySlot.fillContainerOrConvert();
        chemicalInputSlot.fillTank();
        if (recipeCacheLookupMonitor.updateAndProcess()) {
        }

        if (level.getGameTime() % 40 == 0) {
        }
    }

    @Override
    public IMSRecipeTypeProvider<ChemicalDemolitionRecipe, MSInputRecipeCache.ItemChemical<ChemicalDemolitionRecipe>> getMSRecipeType() {
        return MSRecipeType.CHEMICAL_DEMOLITION;
    }

    @Nullable
    @Override
    public ChemicalDemolitionRecipe getRecipe(int cacheIndex) {
        return findFirstRecipe(itemInputHandler, chemicalInputHandler);
    }

    @NotNull
    @Override
    public CachedRecipe<ChemicalDemolitionRecipe> createNewCachedRecipe(@NotNull ChemicalDemolitionRecipe recipe, int cacheIndex) {
        return new ChemicalDemolitionCachedRecipe(recipe, recheckAllRecipeErrors, itemInputHandler, chemicalInputHandler, () -> StatUtils.inversePoisson(injectUsage), firstOutputHandler, secondOutputHandler)
                .setErrorsChanged(this::onErrorsChanged)
                .setCanHolderFunction(() -> MekanismUtils.canFunction(this))
                .setActive(this::setActive)
                .setEnergyRequirements(energyContainer::getEnergyPerTick, energyContainer)
                .setRequiredTicks(this::getTicksRequired)
                .setOnFinish(this::markForSave)
                .setOperatingTicksChanged(this::setOperatingTicks);
    }

    @Override
    public void recalculateUpgrades(Upgrade upgrade) {
        super.recalculateUpgrades(upgrade);
        if (upgrade == Upgrade.GAS || upgrade == Upgrade.SPEED) {
            injectUsage = MekanismUtils.getGasPerTickMeanMultiplier(this);
        }
    }



    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public IMekanismRecipeTypeProvider<ChemicalDemolitionRecipe, ?> getRecipeType() {
        return (IMekanismRecipeTypeProvider) getMSRecipeType();
    }

    @Override
    public void onCachedRecipeChanged(@Nullable mekanism.api.recipes.cache.CachedRecipe<ChemicalDemolitionRecipe> cachedRecipe, int cacheIndex) {
        clearRecipeErrors(cacheIndex);
    }

    public MachineEnergyContainer<TileEntityChemicalDemolitionMachine> getEnergyContainer() {
        return energyContainer;
    }

    @ComputerMethod(methodDescription = ComputerConstants.DESCRIPTION_GET_ENERGY_USAGE)
    FloatingLong getEnergyUsage() {
        return getActive() ? energyContainer.getEnergyPerTick() : FloatingLong.ZERO;
    }
}