package fixdol.mekanismelements.common.tile.machine;

import mekanism.common.tile.component.TileComponentConfig;

import mekanism.api.chemical.gas.Gas;

import mekanism.api.math.FloatingLong;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import mekanism.api.recipes.cache.CachedRecipe;
import mekanism.api.chemical.ChemicalTankBuilder;
import mekanism.common.capabilities.holder.chemical.ChemicalTankHelper;
import mekanism.common.integration.computer.computercraft.ComputerConstants;
import mekanism.common.integration.computer.annotation.ComputerMethod;
import mekanism.common.tile.component.config.ConfigInfo;
import mekanism.api.functions.ConstantPredicates;
import mekanism.common.tile.component.config.DataType;
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
import mekanism.common.inventory.slot.InputInventorySlot;
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
import fixdol.mekanismelements.api.recipes.RadiationIrradiatingRecipe;
import mekanism.api.RelativeSide;
import mekanism.common.inventory.container.slot.SlotOverlay;
import mekanism.common.integration.computer.SpecialComputerMethodWrapper;
import mekanism.common.util.StatUtils;
import mekanism.common.tile.component.TileComponentEjector;
import fixdol.mekanismelements.common.tile.machine.TileEntityRadiationIrradiator;
import mekanism.common.lib.transmitter.TransmissionType;
import mekanism.api.Upgrade;
import mekanism.common.inventory.warning.WarningTracker;
import mekanism.common.integration.computer.annotation.WrappingComputerMethod;

import fixdol.mekanismelements.api.recipes.cache.RadiationIrradiatingCachedRecipe;
import mekanism.api.chemical.attribute.ChemicalAttributeValidator;


public class TileEntityRadiationIrradiator extends MSTileEntityProgressMachine<RadiationIrradiatingRecipe> implements
        IMSDoubleRecipeLookupHandler.ItemChemicalRecipeLookupHandler<RadiationIrradiatingRecipe>,
        IRecipeLookupHandler<RadiationIrradiatingRecipe> {
    private static final List<CachedRecipe.OperationTracker.RecipeError> TRACKED_ERROR_TYPES = List.of(
            CachedRecipe.OperationTracker.RecipeError.NOT_ENOUGH_ENERGY,
            CachedRecipe.OperationTracker.RecipeError.NOT_ENOUGH_ENERGY_REDUCED_RATE,
            CachedRecipe.OperationTracker.RecipeError.NOT_ENOUGH_INPUT,
            CachedRecipe.OperationTracker.RecipeError.NOT_ENOUGH_SECONDARY_INPUT,
            CachedRecipe.OperationTracker.RecipeError.NOT_ENOUGH_OUTPUT_SPACE,
            CachedRecipe.OperationTracker.RecipeError.INPUT_DOESNT_PRODUCE_OUTPUT
    );
    private static final long MAX_CHEMICAL = 10_000;
    public static final int BASE_TICKS_REQUIRED = 25;

    @WrappingComputerMethod(wrapper = SpecialComputerMethodWrapper.ComputerChemicalTankWrapper.class, methodNames = {"getChemicalInput", "getChemicalInputCapacity", "getChemicalInputNeeded",
            "getChemicalInputFilledPercentage"}, docPlaceholder = "chemical input tank")
    public IGasTank injectTank;
    public IGasTank chemicalOutputTank;
    public double injectUsage = 1;

    private final IOutputHandler<GasStack> outputHandler;
    private final IInputHandler<@NotNull ItemStack> itemInputHandler;
    private final ILongInputHandler<@NotNull GasStack> chemicalInputHandler;

    private MachineEnergyContainer<TileEntityRadiationIrradiator> energyContainer;
    @WrappingComputerMethod(wrapper = SpecialComputerMethodWrapper.ComputerIInventorySlotWrapper.class, methodNames = "getInputChemicalItem", docPlaceholder = "chemical input item slot")
    GasInventorySlot chemicalInputSlot;
    @WrappingComputerMethod(wrapper = SpecialComputerMethodWrapper.ComputerIInventorySlotWrapper.class, methodNames = "getInputItem", docPlaceholder = "input slot")
    InputInventorySlot inputSlot;
    @WrappingComputerMethod(wrapper = SpecialComputerMethodWrapper.ComputerIInventorySlotWrapper.class, methodNames = "getOutputItem", docPlaceholder = "output slot")
    GasInventorySlot outputSlot;
    @WrappingComputerMethod(wrapper = SpecialComputerMethodWrapper.ComputerIInventorySlotWrapper.class, methodNames = "getEnergyItem", docPlaceholder = "energy slot")
    EnergyInventorySlot energySlot;

    public TileEntityRadiationIrradiator(BlockPos pos, BlockState state) {
        super(MSBlocks.RADIATION_IRRADIATOR, pos, state, TRACKED_ERROR_TYPES, BASE_TICKS_REQUIRED);
        configComponent = new TileComponentConfig(this, mekanism.common.lib.transmitter.TransmissionType.ITEM, mekanism.common.lib.transmitter.TransmissionType.GAS, mekanism.common.lib.transmitter.TransmissionType.ENERGY);
        // Config is created from block attributes in parent constructor
        getConfig().setupItemIOExtraConfig(inputSlot, outputSlot, chemicalInputSlot, energySlot);
        
        // Chemical I/O Config - LEFT for input, RIGHT for output
        ConfigInfo chemicalConfig = getConfig().setupIOConfig(TransmissionType.GAS, injectTank, chemicalOutputTank, RelativeSide.RIGHT);
        if (chemicalConfig != null) {
            chemicalConfig.setDataType(DataType.INPUT, RelativeSide.LEFT);
            chemicalConfig.setDataType(DataType.INPUT, RelativeSide.BACK);
            chemicalConfig.setDataType(DataType.OUTPUT, RelativeSide.RIGHT);
            chemicalConfig.setDataType(DataType.OUTPUT, RelativeSide.FRONT);
            chemicalConfig.setEjecting(true);
        }
        
        // Energy Config - all sides accept
        ConfigInfo energyConfig = getConfig().setupInputConfig(TransmissionType.ENERGY, energyContainer);
        if (energyConfig != null) {
            for (RelativeSide side : RelativeSide.values()) {
                energyConfig.setDataType(DataType.INPUT, side);
            }
        }

        ejectorComponent = new TileComponentEjector(this);
        ejectorComponent.setOutputData(getConfig(), TransmissionType.ITEM, TransmissionType.GAS)
                .setCanTankEject(tank -> tank != injectTank);

        itemInputHandler = InputHelper.getInputHandler(inputSlot, CachedRecipe.OperationTracker.RecipeError.NOT_ENOUGH_INPUT);
        chemicalInputHandler = InputHelper.getInputHandler(injectTank, CachedRecipe.OperationTracker.RecipeError.NOT_ENOUGH_SECONDARY_INPUT);
        outputHandler = OutputHelper.getOutputHandler(chemicalOutputTank, CachedRecipe.OperationTracker.RecipeError.NOT_ENOUGH_OUTPUT_SPACE);
    }

    @Override
    public net.minecraft.world.level.Level getHandlerWorld() {
        return getLevel();
    }

    protected void presetVariables() {
        super.presetVariables();
        injectTank = ChemicalTankBuilder.GAS.create(MAX_CHEMICAL, ChemicalTankHelper.radioactiveInputTankPredicate(() -> chemicalOutputTank),
                ConstantPredicates.alwaysTrueBi(), this::containsRecipeB, ChemicalAttributeValidator.ALWAYS_ALLOW, recipeCacheLookupMonitor);
        chemicalOutputTank = ChemicalTankBuilder.GAS.output(MAX_CHEMICAL, recipeCacheLookupMonitor);
        energyContainer = MachineEnergyContainer.input(this, recipeCacheLookupMonitor);
    }

    @NotNull
    @Override
    protected IChemicalTankHolder getInitialGasTanks(IContentsListener listener, IContentsListener recipeCacheListener) {
        ChemicalTankHelper builder = ChemicalTankHelper.forSideGasWithConfig(this::getDirection, this::getConfig);
        builder.addTank(injectTank);
        builder.addTank(chemicalOutputTank);
        return builder.build();
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
        builder.addSlot(chemicalInputSlot = GasInventorySlot.fillOrConvert(injectTank, this::getLevel, recipeCacheListener, 7, 55));
        builder.addSlot(inputSlot = InputInventorySlot.at(item -> containsRecipeAB(item, injectTank.getStack()), this::containsRecipeA, recipeCacheListener, 7, 36))
                .tracksWarnings(slot -> slot.warning(WarningTracker.WarningType.NO_MATCHING_RECIPE, getWarningCheck(CachedRecipe.OperationTracker.RecipeError.NOT_ENOUGH_INPUT)));
        builder.addSlot(outputSlot = GasInventorySlot.drain(chemicalOutputTank, recipeCacheListener, 152, 55));
        builder.addSlot(energySlot = EnergyInventorySlot.fillOrConvert(energyContainer, this::getLevel, recipeCacheListener, 152, 14));
        chemicalInputSlot.setSlotOverlay(SlotOverlay.MINUS);
        outputSlot.setSlotOverlay(SlotOverlay.PLUS);
        return builder.build();
    }

    @Override
    protected void onUpdateServer() {
        super.onUpdateServer();
        energySlot.fillContainerOrConvert();
        chemicalInputSlot.fillTank();
        outputSlot.drainTank();
        if (recipeCacheLookupMonitor.updateAndProcess()) {
        }
        
        if (level.getGameTime() % 40 == 0) {
        }
    }

    @Override
    public IMSRecipeTypeProvider<RadiationIrradiatingRecipe, MSInputRecipeCache.ItemChemical<RadiationIrradiatingRecipe>> getMSRecipeType() {
        return MSRecipeType.RADIATION_IRRADIATING;
    }

    @Nullable
    @Override
    public RadiationIrradiatingRecipe getRecipe(int cacheIndex) {
        return findFirstRecipe(itemInputHandler, chemicalInputHandler);
    }

    @NotNull
    @Override
    public CachedRecipe<RadiationIrradiatingRecipe> createNewCachedRecipe(@NotNull RadiationIrradiatingRecipe recipe, int cacheIndex) {
        return new RadiationIrradiatingCachedRecipe(recipe, recheckAllRecipeErrors, itemInputHandler, chemicalInputHandler, () -> StatUtils.inversePoisson(injectUsage), outputHandler)
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
            injectUsage = Math.max(1, MekanismUtils.getGasPerTickMeanMultiplier(this));
        }
    }



    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public IMekanismRecipeTypeProvider<RadiationIrradiatingRecipe, ?> getRecipeType() {
        return (IMekanismRecipeTypeProvider) getMSRecipeType();
    }

    @Override
    public void onCachedRecipeChanged(@Nullable mekanism.api.recipes.cache.CachedRecipe<RadiationIrradiatingRecipe> cachedRecipe, int cacheIndex) {
        clearRecipeErrors(cacheIndex);
    }

    public MachineEnergyContainer<TileEntityRadiationIrradiator> getEnergyContainer() {
        return energyContainer;
    }

    //Methods relating to IComputerTile
    @ComputerMethod(methodDescription = ComputerConstants.DESCRIPTION_GET_ENERGY_USAGE)
    FloatingLong getEnergyUsage() {
        return getActive() ? energyContainer.getEnergyPerTick() : FloatingLong.ZERO;
    }

    @WrappingComputerMethod(wrapper = SpecialComputerMethodWrapper.ComputerChemicalTankWrapper.class, methodNames = {"getOutput", "getOutputCapacity", "getOutputNeeded", "getOutputFilledPercentage"}, docPlaceholder = "output tank")
    IGasTank getOutputTank() {
        // Return the chemical tank as default, or determine based on current output
        return chemicalOutputTank;
    }
    //End methods IComputerTile
}