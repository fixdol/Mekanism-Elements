package fixdol.mekanismelements.common.tile.prefab;

import fixdol.mekanismelements.common.recipe.lookup.IMSRecipeLookupHandler;
import fixdol.mekanismelements.common.recipe.lookup.monitor.MSRecipeCacheLookupMonitor;
import mekanism.api.IContentsListener;
import mekanism.api.chemical.IChemicalTank;
import mekanism.api.energy.IEnergyContainer;
import mekanism.api.fluid.IFluidTank;
import mekanism.api.heat.IHeatCapacitor;
import mekanism.api.inventory.IInventorySlot;
import net.minecraft.core.Holder;
import net.minecraft.world.level.block.Block;
import mekanism.api.recipes.MekanismRecipe;
import mekanism.api.recipes.cache.CachedRecipe;
import mekanism.common.capabilities.heat.CachedAmbientTemperature;
import mekanism.common.capabilities.holder.container.IContainerHolder;
import mekanism.common.capabilities.holder.single.ISingleContainerHolder;
import mekanism.common.inventory.container.MekanismContainer;
import mekanism.common.tile.base.TileEntityMekanism;
import mekanism.common.tile.prefab.TileEntityConfigurableMachine;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import org.jspecify.annotations.Nullable;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.BooleanSupplier;

public abstract class MSTileEntityRecipeMachine<RECIPE extends MekanismRecipe<?>> extends TileEntityConfigurableMachine implements IMSRecipeLookupHandler<RECIPE> {
    public static final int RECIPE_CHECK_FREQUENCY = 100;

    protected final BooleanSupplier recheckAllRecipeErrors;
    private final List<CachedRecipe.OperationTracker.RecipeError> errorTypes;
    private final boolean[] trackedErrors;

    protected MSRecipeCacheLookupMonitor<RECIPE> recipeCacheLookupMonitor;
    @Nullable
    private IContentsListener recipeCacheSaveOnlyListener;
    @Nullable
    private IContentsListener recipeCacheUnpauseListener;
    @Nullable
    private IContentsListener recipeCacheUnpauseSaveOnlyListener;

    protected MSTileEntityRecipeMachine(Holder<Block> blockProvider, BlockPos pos, BlockState state, List<CachedRecipe.OperationTracker.RecipeError> errorTypes) {
        super(blockProvider, pos, state);
        this.errorTypes = List.copyOf(errorTypes);
        recheckAllRecipeErrors = shouldRecheckAllErrors(this);
        trackedErrors = new boolean[this.errorTypes.size()];
        recipeCacheSaveOnlyListener = null;
        recipeCacheUnpauseListener = null;
        recipeCacheUnpauseSaveOnlyListener = null;
    }

    public static BooleanSupplier shouldRecheckAllErrors(TileEntityMekanism tile) {
        int checkOffset = ThreadLocalRandom.current().nextInt(RECIPE_CHECK_FREQUENCY);
        return () -> !tile.playersUsing.isEmpty() && tile.getLevel() != null && tile.getLevel().getGameTime() % RECIPE_CHECK_FREQUENCY == checkOffset;
    }

    @Override
    protected void presetVariables() {
        super.presetVariables();
        recipeCacheLookupMonitor = createNewCacheMonitor();
    }

    protected MSRecipeCacheLookupMonitor<RECIPE> createNewCacheMonitor() {
        return new MSRecipeCacheLookupMonitor<>(this);
    }

    protected IContentsListener getRecipeCacheSaveOnlyListener() {
        if (supportsComparator()) {
            if (recipeCacheSaveOnlyListener == null) {
                recipeCacheSaveOnlyListener = () -> {
                    markForSave();
                    recipeCacheLookupMonitor.onChange();
                };
            }
            return recipeCacheSaveOnlyListener;
        }
        return recipeCacheLookupMonitor;
    }

    protected IContentsListener getRecipeCacheUnpauseListener(@Nullable IContentsListener listener) {
        if (listener == this) {
            if (recipeCacheUnpauseListener == null) {
                recipeCacheUnpauseListener = () -> {
                    onContentsChanged();
                    recipeCacheLookupMonitor.unpause();
                };
            }
            return recipeCacheUnpauseListener;
        }
        if (recipeCacheUnpauseSaveOnlyListener == null) {
            recipeCacheUnpauseSaveOnlyListener = () -> {
                markForSave();
                recipeCacheLookupMonitor.unpause();
            };
        }
        return recipeCacheUnpauseSaveOnlyListener;
    }

    @Override
    public void addContainerTrackers(MekanismContainer container) {
        super.addContainerTrackers(container);
        container.trackArray(trackedErrors);
    }

    @Override
    public void clearRecipeErrors(int cacheIndex) {
        Arrays.fill(trackedErrors, false);
    }

    protected void onErrorsChanged(Set<CachedRecipe.OperationTracker.RecipeError> errors) {
        for (int i = 0; i < trackedErrors.length; i++) {
            trackedErrors[i] = errors.contains(errorTypes.get(i));
        }
    }

    public BooleanSupplier getWarningCheck(CachedRecipe.OperationTracker.RecipeError error) {
        int errorIndex = errorTypes.indexOf(error);
        if (errorIndex == -1) {
            //Something went wrong
            return () -> false;
        }
        return () -> trackedErrors[errorIndex];
    }

    @Nullable
    @Override
    public final IContainerHolder<IChemicalTank> getInitialChemicalTanks(IContentsListener listener) {
        return getInitialChemicalTanks(listener, listener == this ? recipeCacheLookupMonitor : getRecipeCacheSaveOnlyListener(), getRecipeCacheUnpauseListener(listener));
    }

    /**
     * @apiNote Do not call directly, only override implementation
     */
    @Nullable
    protected IContainerHolder<IChemicalTank> getInitialChemicalTanks(IContentsListener listener, IContentsListener recipeCacheListener, IContentsListener recipeCacheUnpauseListener) {
        return null;
    }

    @Nullable
    @Override
    protected final IContainerHolder<IFluidTank> getInitialFluidTanks(IContentsListener listener) {
        return getInitialFluidTanks(listener, listener == this ? recipeCacheLookupMonitor : getRecipeCacheSaveOnlyListener(), getRecipeCacheUnpauseListener(listener));
    }

    /**
     * @apiNote Do not call directly, only override implementation
     */
    @Nullable
    protected IContainerHolder<IFluidTank> getInitialFluidTanks(IContentsListener listener, IContentsListener recipeCacheListener, IContentsListener recipeCacheUnpauseListener) {
        return null;
    }

    @Nullable
    @Override
    protected final ISingleContainerHolder<IEnergyContainer> getInitialEnergyContainer(IContentsListener listener) {
        return getInitialEnergyContainer(listener, listener == this ? recipeCacheLookupMonitor : getRecipeCacheSaveOnlyListener(), getRecipeCacheUnpauseListener(listener));
    }

    /**
     * @apiNote Do not call directly, only override implementation
     */
    @Nullable
    protected ISingleContainerHolder<IEnergyContainer> getInitialEnergyContainer(IContentsListener listener, IContentsListener recipeCacheListener, IContentsListener recipeCacheUnpauseListener) {
        return null;
    }

    @Nullable
    @Override
    protected final IContainerHolder<IInventorySlot> getInitialInventory(IContentsListener listener) {
        return getInitialInventory(listener, listener == this ? recipeCacheLookupMonitor : getRecipeCacheSaveOnlyListener(), getRecipeCacheUnpauseListener(listener));
    }

    /**
     * @apiNote Do not call directly, only override implementation
     */
    @Nullable
    protected IContainerHolder<IInventorySlot> getInitialInventory(IContentsListener listener, IContentsListener recipeCacheListener, IContentsListener recipeCacheUnpauseListener) {
        return null;
    }

    @Nullable
    @Override
    protected final ISingleContainerHolder<IHeatCapacitor> getInitialHeatCapacitor(IContentsListener listener, CachedAmbientTemperature ambientTemperature) {
        return getInitialHeatCapacitor(listener, listener == this ? recipeCacheLookupMonitor : getRecipeCacheSaveOnlyListener(), getRecipeCacheUnpauseListener(listener), ambientTemperature);
    }

    /**
     * @apiNote Do not call directly, only override implementation
     */
    @Nullable
    protected ISingleContainerHolder<IHeatCapacitor> getInitialHeatCapacitor(IContentsListener listener, IContentsListener recipeCacheListener,
                                                                            IContentsListener recipeCacheUnpauseListener, CachedAmbientTemperature ambientTemperature) {
        return null;
    }
}
