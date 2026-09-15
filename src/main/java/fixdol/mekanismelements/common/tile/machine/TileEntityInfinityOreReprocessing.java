package fixdol.mekanismelements.common.tile.machine;

import mekanism.common.tile.component.TileComponentConfig;

import mekanism.api.chemical.gas.Gas;

import mekanism.common.util.MekanismUtils;

import mekanism.common.inventory.slot.BasicInventorySlot;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import mekanism.api.recipes.cache.CachedRecipe;
import mekanism.api.chemical.Chemical;
import mekanism.api.chemical.ChemicalTankBuilder;
import mekanism.common.capabilities.holder.chemical.ChemicalTankHelper;
import java.util.Collections;
import mekanism.common.tile.component.config.ConfigInfo;
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
import fixdol.mekanismelements.common.recipe.IMSRecipeTypeProvider;
import mekanism.common.recipe.IMekanismRecipeTypeProvider;
import mekanism.api.recipes.outputs.IOutputHandler;
import mekanism.common.recipe.lookup.IRecipeLookupHandler;
import fixdol.mekanismelements.api.recipes.InfinityOreReprocessingRecipe;
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
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import mekanism.api.recipes.outputs.OutputHelper;
import mekanism.common.inventory.slot.OutputInventorySlot;
import mekanism.api.RelativeSide;
import mekanism.common.inventory.container.slot.SlotOverlay;
import mekanism.common.tile.component.TileComponentEjector;
import fixdol.mekanismelements.common.tile.machine.TileEntityInfinityOreReprocessing;
import mekanism.common.lib.transmitter.TransmissionType;

import mekanism.api.recipes.cache.TwoInputCachedRecipe;
import mekanism.common.inventory.warning.WarningTracker.WarningType;


public class TileEntityInfinityOreReprocessing extends MSTileEntityProgressMachine<InfinityOreReprocessingRecipe> implements
        IRecipeLookupHandler<InfinityOreReprocessingRecipe> {
    private static final List<CachedRecipe.OperationTracker.RecipeError> TRACKED_ERROR_TYPES = List.of(
            CachedRecipe.OperationTracker.RecipeError.NOT_ENOUGH_ENERGY,
            CachedRecipe.OperationTracker.RecipeError.NOT_ENOUGH_ENERGY_REDUCED_RATE,
            CachedRecipe.OperationTracker.RecipeError.NOT_ENOUGH_INPUT,
            CachedRecipe.OperationTracker.RecipeError.NOT_ENOUGH_OUTPUT_SPACE,
            CachedRecipe.OperationTracker.RecipeError.INPUT_DOESNT_PRODUCE_OUTPUT
    );
    private static final long MAX_CHEMICAL = 10_000;
    public static final int BASE_TICKS_REQUIRED = 100;

    public IGasTank chemicalInputTank;
    public BasicInventorySlot outputSlot;
    public BasicInventorySlot itemInputSlot;

    private final IInputHandler<@NotNull ItemStack> itemInputHandler;
    private final IInputHandler<@NotNull GasStack> chemicalInputHandler;
    private final IOutputHandler<@NotNull ItemStack> outputHandler;

    private MachineEnergyContainer<TileEntityInfinityOreReprocessing> energyContainer;
    private GasInventorySlot chemicalInputSlot;
    private EnergyInventorySlot energySlot;

    public TileEntityInfinityOreReprocessing(BlockPos pos, BlockState state) {
        super(MSBlocks.INFINITY_ORE_REPROCESSING, pos, state, TRACKED_ERROR_TYPES, BASE_TICKS_REQUIRED);
        configComponent = new TileComponentConfig(this, mekanism.common.lib.transmitter.TransmissionType.ITEM, mekanism.common.lib.transmitter.TransmissionType.GAS, mekanism.common.lib.transmitter.TransmissionType.ENERGY);

        getConfig().setupItemIOConfig(Collections.singletonList(itemInputSlot), Collections.singletonList(outputSlot), energySlot, false);

        ConfigInfo chemicalConfig = getConfig().setupInputConfig(TransmissionType.GAS, chemicalInputTank);
        if (chemicalConfig != null) {
            chemicalConfig.setDataType(DataType.INPUT, RelativeSide.LEFT);
            chemicalConfig.setDataType(DataType.INPUT, RelativeSide.BACK);
        }

        ConfigInfo energyConfig = getConfig().setupInputConfig(TransmissionType.ENERGY, energyContainer);
        if (energyConfig != null) {
            for (RelativeSide side : RelativeSide.values()) {
                energyConfig.setDataType(DataType.INPUT, side);
            }
        }

        ejectorComponent = new TileComponentEjector(this);
        ejectorComponent.setOutputData(getConfig(), TransmissionType.ITEM);

        itemInputHandler = InputHelper.getInputHandler(itemInputSlot, CachedRecipe.OperationTracker.RecipeError.NOT_ENOUGH_INPUT);
        chemicalInputHandler = InputHelper.getInputHandler(chemicalInputTank, CachedRecipe.OperationTracker.RecipeError.NOT_ENOUGH_INPUT);
        outputHandler = OutputHelper.getOutputHandler(outputSlot, CachedRecipe.OperationTracker.RecipeError.NOT_ENOUGH_OUTPUT_SPACE);
    }

    @Override
    public net.minecraft.world.level.Level getHandlerWorld() {
        return getLevel();
    }

    protected void presetVariables() {
        super.presetVariables();
        chemicalInputTank = ChemicalTankBuilder.GAS.input(MAX_CHEMICAL,
                this::containsRecipe,
                recipeCacheLookupMonitor);
    }

    @NotNull
    @Override
    protected IChemicalTankHolder getInitialGasTanks(IContentsListener listener, IContentsListener recipeCacheListener) {
        ChemicalTankHelper builder = ChemicalTankHelper.forSideGasWithConfig(this::getDirection, this::getConfig);
        builder.addTank(chemicalInputTank);
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
        // Slot de item de entrada (la mena "sucia"). Posición GUI a tu gusto; 21,17 es un ejemplo (arriba del slot quimico).
        builder.addSlot(itemInputSlot = InputInventorySlot.at(item -> containsRecipe(item), recipeCacheListener, 21, 17));
        builder.addSlot(outputSlot = OutputInventorySlot.at(recipeCacheListener, 116, 36));
        outputSlot.setSlotOverlay(SlotOverlay.PLUS);
        outputSlot.tracksWarnings(slot -> slot.warning(WarningType.NO_SPACE_IN_OUTPUT, getWarningCheck(CachedRecipe.OperationTracker.RecipeError.NOT_ENOUGH_OUTPUT_SPACE)));
        builder.addSlot(chemicalInputSlot = GasInventorySlot.fill(chemicalInputTank, recipeCacheListener, 21, 56));
        builder.addSlot(energySlot = EnergyInventorySlot.fillOrConvert(energyContainer, this::getLevel, recipeCacheListener, 144, 35));
        return builder.build();
    }

    @Override
    protected void onUpdateServer() {
        super.onUpdateServer();
        chemicalInputSlot.fillTank();
        energySlot.fillContainerOrConvert();
        if (recipeCacheLookupMonitor.updateAndProcess()) {
        }
    }

    @Override
    public IMSRecipeTypeProvider<InfinityOreReprocessingRecipe, MSInputRecipeCache.ItemChemical<InfinityOreReprocessingRecipe>> getMSRecipeType() {
        return MSRecipeType.INFINITY_ORE_REPROCESSING;
    }

    private boolean containsRecipe(ItemStack item) {
        for (InfinityOreReprocessingRecipe recipe : MSRecipeType.INFINITY_ORE_REPROCESSING.get().getRecipes(getLevel())) {
            if (recipe.getItemInput().test(item)) {
                return true;
            }
        }
        return false;
    }

    private boolean containsRecipe(Chemical chemical) {
        for (InfinityOreReprocessingRecipe recipe : MSRecipeType.INFINITY_ORE_REPROCESSING.get().getRecipes(getLevel())) {
            if (recipe.getChemicalInput().testType(new GasStack((mekanism.api.chemical.gas.Gas) chemical, 1))) {
                return true;
            }
        }
        return false;
    }

    @Nullable
    private InfinityOreReprocessingRecipe findFirstRecipe(ItemStack item, GasStack chemical) {
        for (InfinityOreReprocessingRecipe recipe : MSRecipeType.INFINITY_ORE_REPROCESSING.get().getRecipes(getLevel())) {
            if (recipe.test(item, chemical)) {
                return recipe;
            }
        }
        return null;
    }

    @Nullable
    private InfinityOreReprocessingRecipe findFirstRecipe(IInputHandler<ItemStack> itemInputHandler, IInputHandler<GasStack> chemicalInputHandler) {
        return findFirstRecipe(itemInputHandler.getInput(), chemicalInputHandler.getInput());
    }

    @Nullable
    @Override
    public InfinityOreReprocessingRecipe getRecipe(int cacheIndex) {
        return findFirstRecipe(itemInputHandler, chemicalInputHandler);
    }

    @NotNull
    @Override
    public CachedRecipe<InfinityOreReprocessingRecipe> createNewCachedRecipe(@NotNull InfinityOreReprocessingRecipe recipe, int cacheIndex) {
        return new TwoInputCachedRecipe<>(recipe, recheckAllRecipeErrors, itemInputHandler, chemicalInputHandler, outputHandler,
                recipe::getItemInput, recipe::getChemicalInput, recipe::getOutput,
                ItemStack::isEmpty, GasStack::isEmpty, ItemStack::isEmpty) {}
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
    public IMekanismRecipeTypeProvider<InfinityOreReprocessingRecipe, ?> getRecipeType() {
        return (IMekanismRecipeTypeProvider) getMSRecipeType();
    }

    @Override
    public void onCachedRecipeChanged(@Nullable CachedRecipe<InfinityOreReprocessingRecipe> cachedRecipe, int cacheIndex) {
        clearRecipeErrors(cacheIndex);
    }

    public MachineEnergyContainer<TileEntityInfinityOreReprocessing> getEnergyContainer() {
        return energyContainer;
    }
}
