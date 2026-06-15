package com.fxd927.mekanismelements.common.tile.machine;

import mekanism.common.tile.component.config.ConfigInfo;
import com.fxd927.mekanismelements.api.recipes.ChemicalDemolitionRecipe;
import com.fxd927.mekanismelements.api.recipes.cache.ChemicalDemolitionCachedRecipe;
import com.fxd927.mekanismelements.common.recipe.IMSRecipeTypeProvider;
import com.fxd927.mekanismelements.common.recipe.MSRecipeType;
import com.fxd927.mekanismelements.common.recipe.lookup.IMSDoubleRecipeLookupHandler;
import com.fxd927.mekanismelements.common.recipe.lookup.cache.MSInputRecipeCache;
import com.fxd927.mekanismelements.common.tile.prefab.MSTileEntityProgressMachine;
import mekanism.api.AutomationType;
import mekanism.api.IContentsListener;
import mekanism.api.chemical.Chemical;
import com.fxd927.mekanismelements.common.registries.MSBlocks;
import mekanism.api.Upgrade;
import mekanism.api.chemical.ChemicalStack;
import mekanism.api.chemical.IChemicalTank;
import mekanism.api.chemical.BasicChemicalTank;
import mekanism.api.chemical.ChemicalStack;
import mekanism.api.functions.ConstantPredicates;
import mekanism.api.recipes.cache.CachedRecipe;
import mekanism.api.recipes.inputs.IInputHandler;
import mekanism.api.recipes.inputs.ILongInputHandler;
import mekanism.api.recipes.inputs.InputHelper;
import mekanism.api.recipes.outputs.IOutputHandler;
import mekanism.api.recipes.outputs.OutputHelper;
import mekanism.common.capabilities.energy.MachineEnergyContainer;
import mekanism.common.capabilities.holder.chemical.ChemicalTankHelper;
import mekanism.common.capabilities.holder.chemical.IChemicalTankHolder;
import mekanism.common.capabilities.holder.energy.EnergyContainerHelper;
import mekanism.common.capabilities.holder.energy.IEnergyContainerHolder;
import mekanism.common.capabilities.holder.slot.IInventorySlotHolder;
import mekanism.common.capabilities.holder.slot.InventorySlotHelper;
import mekanism.common.integration.computer.SpecialComputerMethodWrapper;
import mekanism.common.integration.computer.annotation.ComputerMethod;
import mekanism.common.integration.computer.annotation.WrappingComputerMethod;
import mekanism.common.integration.computer.computercraft.ComputerConstants;
import mekanism.common.inventory.container.slot.SlotOverlay;
import mekanism.common.inventory.slot.EnergyInventorySlot;
import mekanism.common.inventory.slot.InputInventorySlot;
import mekanism.common.inventory.slot.OutputInventorySlot;
import mekanism.common.inventory.slot.chemical.ChemicalInventorySlot;
import mekanism.common.inventory.warning.WarningTracker;
import mekanism.common.lib.transmitter.TransmissionType;
import mekanism.common.tile.component.TileComponentConfig;
import mekanism.common.tile.component.TileComponentEjector;
import mekanism.common.util.MekanismUtils;
import mekanism.common.util.StatUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.function.BiPredicate;

public class TileEntityChemicalDemolitionMachine extends MSTileEntityProgressMachine<ChemicalDemolitionRecipe> implements
        IMSDoubleRecipeLookupHandler.ItemChemicalRecipeLookupHandler<ChemicalDemolitionRecipe>{
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
    public IChemicalTank injectTank;
    public double injectUsage = 1;

    private final IOutputHandler firstOutputHandler;
    private final IOutputHandler secondOutputHandler;
    private final IInputHandler<@NotNull ItemStack> itemInputHandler;
    private final ILongInputHandler<@NotNull ChemicalStack> chemicalInputHandler;

    private MachineEnergyContainer<TileEntityChemicalDemolitionMachine> energyContainer;
    @WrappingComputerMethod(wrapper = SpecialComputerMethodWrapper.ComputerIInventorySlotWrapper.class, methodNames = "getInputChemicalItem", docPlaceholder = "chemical input item slot")
    ChemicalInventorySlot chemicalInputSlot;
    @WrappingComputerMethod(wrapper = SpecialComputerMethodWrapper.ComputerIInventorySlotWrapper.class, methodNames = "getInputItem", docPlaceholder = "input slot")
    InputInventorySlot inputSlot;
    @WrappingComputerMethod(wrapper = SpecialComputerMethodWrapper.ComputerIInventorySlotWrapper.class, methodNames = "getOutputItem", docPlaceholder = "output slot")
    OutputInventorySlot firstOutputSlot;
    @WrappingComputerMethod(wrapper = SpecialComputerMethodWrapper.ComputerIInventorySlotWrapper.class, methodNames = "getOutputItem", docPlaceholder = "output slot")
    OutputInventorySlot secondOutputSlot;
    @WrappingComputerMethod(wrapper = SpecialComputerMethodWrapper.ComputerIInventorySlotWrapper.class, methodNames = "getEnergyItem", docPlaceholder = "energy slot")
    EnergyInventorySlot energySlot;

    public TileEntityChemicalDemolitionMachine(BlockPos pos, BlockState state) {
        // TODO: Uncomment when CHEMICAL_DEMOLITION_MACHINE block is registered
        // super(MSBlocks.CHEMICAL_DEMOLITION_MACHINE, pos, state, TRACKED_ERROR_TYPES, BASE_TICKS_REQUIRED);
        super(null, pos, state, TRACKED_ERROR_TYPES, BASE_TICKS_REQUIRED); // Temporary fix - block is commented out
        // Config is created from block attributes in parent constructor
        getConfig().setupItemIOExtraConfig(inputSlot, firstOutputSlot, chemicalInputSlot, energySlot);
        
        // Chemical Input Config - LEFT/BACK sides
        ConfigInfo chemicalConfig = getConfig().setupInputConfig(TransmissionType.CHEMICAL, injectTank);
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
        ejectorComponent.setOutputData(getConfig(), TransmissionType.ITEM, TransmissionType.CHEMICAL)
                .setCanTankEject(tank -> tank != injectTank);

        itemInputHandler = InputHelper.getInputHandler(inputSlot, CachedRecipe.OperationTracker.RecipeError.NOT_ENOUGH_INPUT);
        chemicalInputHandler = InputHelper.getInputHandler(injectTank, CachedRecipe.OperationTracker.RecipeError.NOT_ENOUGH_SECONDARY_INPUT);
        firstOutputHandler = OutputHelper.getOutputHandler(firstOutputSlot, CachedRecipe.OperationTracker.RecipeError.NOT_ENOUGH_OUTPUT_SPACE);
        secondOutputHandler = OutputHelper.getOutputHandler(secondOutputSlot, NOT_ENOUGH_SPACE_SECOND_OUTPUT_ERROR);
    }

    @Override
    protected void presetVariables() {
        super.presetVariables();
        injectTank = BasicChemicalTank.createModern(MAX_CHEMICAL, allowExtractingChemical() ? ConstantPredicates.alwaysTrueBi() : ConstantPredicates.notExternal(),
                (chemical, automationType) -> containsRecipeBA(inputSlot.getStack(), chemical), this::containsRecipeB, recipeCacheLookupMonitor);
        energyContainer = MachineEnergyContainer.input(this, recipeCacheLookupMonitor);
    }

    @NotNull
    @Override
    public IChemicalTankHolder getInitialGasTanks(IContentsListener listener, IContentsListener recipeCacheListener) {
        ChemicalTankHelper builder = ChemicalTankHelper.forSideWithConfig(this);
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
    public IChemicalTankHolder getInitialInfusionTanks(IContentsListener listener, IContentsListener recipeCacheListener) {
        ChemicalTankHelper builder = ChemicalTankHelper.forSideWithConfig(this);
        return builder.build();
    }

    @NotNull
    @Override
    public IChemicalTankHolder getInitialPigmentTanks(IContentsListener listener, IContentsListener recipeCacheListener) {
        ChemicalTankHelper builder = ChemicalTankHelper.forSideWithConfig(this);
        return builder.build();
    }

    @NotNull
    @Override
    public IChemicalTankHolder getInitialSlurryTanks(IContentsListener listener, IContentsListener recipeCacheListener) {
        ChemicalTankHelper builder = ChemicalTankHelper.forSideWithConfig(this);
        return builder.build();
    }

    @NotNull
    @Override
    protected IEnergyContainerHolder getInitialEnergyContainers(IContentsListener listener, IContentsListener recipeCacheListener) {
        EnergyContainerHelper builder = EnergyContainerHelper.forSideWithConfig(this);
        builder.addContainer(energyContainer);
        return builder.build();
    }

    @NotNull
    @Override
    protected IInventorySlotHolder getInitialInventory(IContentsListener listener, IContentsListener recipeCacheListener) {
        InventorySlotHelper builder = InventorySlotHelper.forSideWithConfig(this);
        builder.addSlot(chemicalInputSlot = ChemicalInventorySlot.fillOrConvert(injectTank, this::getLevel, recipeCacheListener, 8, 65));
        builder.addSlot(inputSlot = InputInventorySlot.at(item -> containsRecipeAB(item, injectTank.getStack()), this::containsRecipeA, recipeCacheListener, 28, 36))
                .tracksWarnings(slot -> slot.warning(WarningTracker.WarningType.NO_MATCHING_RECIPE, getWarningCheck(CachedRecipe.OperationTracker.RecipeError.NOT_ENOUGH_INPUT)));
        builder.addSlot(firstOutputSlot = OutputInventorySlot.at(recipeCacheListener, 116, 35));
        builder.addSlot(secondOutputSlot = OutputInventorySlot.at(recipeCacheListener, 132, 35));
        builder.addSlot(energySlot = EnergyInventorySlot.fillOrConvert(energyContainer, this::getLevel, recipeCacheListener, 154, 62));
        chemicalInputSlot.setSlotOverlay(SlotOverlay.MINUS);
        return builder.build();
    }

    @Override
    protected boolean onUpdateServer() {
        boolean needsUpdate = super.onUpdateServer();
        energySlot.fillContainerOrConvert();
        chemicalInputSlot.fillTank();
        if (recipeCacheLookupMonitor.updateAndProcess()) {
            needsUpdate = true;
        }

        if (level.getGameTime() % 40 == 0) {
             com.fxd927.mekanismelements.common.MekanismElements.logger.info("DEBUG: ChemicalDemolition Tick | Energy: {}/{} | InputItem: {} | InputChemical: {} | Active: {}", 
                 energyContainer.getEnergy(), energyContainer.getMaxEnergy(),
                 inputSlot.getStack(), injectTank.getStack(), getActive());
        }
        return needsUpdate;
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
                .setCanHolderFunction(this::canFunction)
                .setActive(this::setActive)
                .setEnergyRequirements(energyContainer::getEnergyPerTick, energyContainer)
                .setRequiredTicks(this::getTicksRequired)
                .setOnFinish(this::markForSave)
                .setOperatingTicksChanged(this::setOperatingTicks);
    }

    @Override
    public void recalculateUpgrades(Upgrade upgrade) {
        super.recalculateUpgrades(upgrade);
        if (upgrade == Upgrade.CHEMICAL || upgrade == Upgrade.SPEED) {
            injectUsage = MekanismUtils.getGasPerTickMeanMultiplier(this);
        }
    }

    public MachineEnergyContainer<TileEntityChemicalDemolitionMachine> getEnergyContainer() {
        return energyContainer;
    }

    @ComputerMethod(methodDescription = ComputerConstants.DESCRIPTION_GET_ENERGY_USAGE)
    long getEnergyUsage() {
        return getActive() ? energyContainer.getEnergyPerTick() : 0;
    }
}

