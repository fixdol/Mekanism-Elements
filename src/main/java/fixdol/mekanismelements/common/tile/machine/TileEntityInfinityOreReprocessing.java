package fixdol.mekanismelements.common.tile.machine;

import fixdol.mekanismelements.api.recipes.InfinityOreReprocessingRecipe;
import fixdol.mekanismelements.common.recipe.IMSRecipeTypeProvider;
import fixdol.mekanismelements.common.recipe.MSRecipeType;
import fixdol.mekanismelements.common.recipe.lookup.cache.MSInputRecipeCache;
import fixdol.mekanismelements.common.registries.MSBlocks;
import fixdol.mekanismelements.common.tile.prefab.MSTileEntityProgressMachine;
import mekanism.api.IContentsListener;
import mekanism.api.RelativeSide;
import mekanism.api.chemical.BasicChemicalTank;
import mekanism.api.chemical.Chemical;
import mekanism.api.chemical.ChemicalStack;
import mekanism.api.chemical.IChemicalTank;
import mekanism.api.recipes.cache.CachedRecipe;
import mekanism.api.recipes.cache.TwoInputCachedRecipe;
import mekanism.api.recipes.inputs.IInputHandler;
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
import mekanism.common.inventory.slot.BasicInventorySlot;
import mekanism.common.inventory.slot.InputInventorySlot;
import mekanism.common.inventory.slot.OutputInventorySlot;
import mekanism.common.inventory.slot.EnergyInventorySlot;
import mekanism.common.inventory.slot.chemical.ChemicalInventorySlot;
import mekanism.common.inventory.container.slot.SlotOverlay;
import mekanism.common.inventory.warning.WarningTracker.WarningType;
import mekanism.common.recipe.IMekanismRecipeTypeProvider;
import mekanism.common.recipe.lookup.IRecipeLookupHandler;
import mekanism.common.tile.component.TileComponentEjector;
import mekanism.common.tile.component.config.DataType;
import mekanism.common.tile.component.config.ConfigInfo;
import mekanism.common.lib.transmitter.TransmissionType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;

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

    public IChemicalTank chemicalInputTank;
    public BasicInventorySlot outputSlot;
    public BasicInventorySlot itemInputSlot;

    private final IInputHandler<@NotNull ItemStack> itemInputHandler;
    private final IInputHandler<@NotNull ChemicalStack> chemicalInputHandler;
    private final IOutputHandler<@NotNull ItemStack> outputHandler;

    private MachineEnergyContainer<TileEntityInfinityOreReprocessing> energyContainer;
    private ChemicalInventorySlot chemicalInputSlot;
    private EnergyInventorySlot energySlot;

    public TileEntityInfinityOreReprocessing(BlockPos pos, BlockState state) {
        super(MSBlocks.INFINITY_ORE_REPROCESSING, pos, state, TRACKED_ERROR_TYPES, BASE_TICKS_REQUIRED);

        getConfig().setupItemIOConfig(Collections.singletonList(itemInputSlot), Collections.singletonList(outputSlot), energySlot, false);

        ConfigInfo chemicalConfig = getConfig().setupInputConfig(TransmissionType.CHEMICAL, chemicalInputTank);
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
    protected void presetVariables() {
        super.presetVariables();
        chemicalInputTank = BasicChemicalTank.input(MAX_CHEMICAL,
                this::containsRecipe,
                recipeCacheLookupMonitor);
    }

    @NotNull
    @Override
    public IChemicalTankHolder getInitialChemicalTanks(IContentsListener listener) {
        ChemicalTankHelper builder = ChemicalTankHelper.forSideWithConfig(this);
        builder.addTank(chemicalInputTank);
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
        // Slot de item de entrada (la mena "sucia"). Posición GUI a tu gusto; 21,17 es un ejemplo (arriba del slot quimico).
        builder.addSlot(itemInputSlot = InputInventorySlot.at(item -> containsRecipe(item), recipeCacheListener, 21, 17));
        builder.addSlot(outputSlot = OutputInventorySlot.at(recipeCacheListener, 116, 36));
        outputSlot.setSlotOverlay(SlotOverlay.PLUS);
        outputSlot.tracksWarnings(slot -> slot.warning(WarningType.NO_SPACE_IN_OUTPUT, getWarningCheck(CachedRecipe.OperationTracker.RecipeError.NOT_ENOUGH_OUTPUT_SPACE)));
        builder.addSlot(chemicalInputSlot = ChemicalInventorySlot.fill(chemicalInputTank, recipeCacheListener, 21, 56));
        builder.addSlot(energySlot = EnergyInventorySlot.fillOrConvert(energyContainer, this::getLevel, recipeCacheListener, 144, 35));
        return builder.build();
    }

    @Override
    protected boolean onUpdateServer() {
        boolean needsUpdate = super.onUpdateServer();
        chemicalInputSlot.fillTank();
        energySlot.fillContainerOrConvert();
        if (recipeCacheLookupMonitor.updateAndProcess()) {
            needsUpdate = true;
        }
        return needsUpdate;
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
            if (recipe.getChemicalInput().testType(chemical)) {
                return true;
            }
        }
        return false;
    }

    @Nullable
    private InfinityOreReprocessingRecipe findFirstRecipe(ItemStack item, ChemicalStack chemical) {
        for (InfinityOreReprocessingRecipe recipe : MSRecipeType.INFINITY_ORE_REPROCESSING.get().getRecipes(getLevel())) {
            if (recipe.test(item, chemical)) {
                return recipe;
            }
        }
        return null;
    }

    @Nullable
    private InfinityOreReprocessingRecipe findFirstRecipe(IInputHandler<ItemStack> itemInputHandler, IInputHandler<ChemicalStack> chemicalInputHandler) {
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
                ItemStack::isEmpty, ChemicalStack::isEmpty, ItemStack::isEmpty) {}
                .setErrorsChanged(this::onErrorsChanged)
                .setCanHolderFunction(this::canFunction)
                .setActive(this::setActive)
                .setEnergyRequirements(energyContainer::getEnergyPerTick, energyContainer)
                .setRequiredTicks(this::getTicksRequired)
                .setOnFinish(this::markForSave)
                .setOperatingTicksChanged(this::setOperatingTicks);
    }

    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public IMekanismRecipeTypeProvider<?, InfinityOreReprocessingRecipe, ?> getRecipeType() {
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