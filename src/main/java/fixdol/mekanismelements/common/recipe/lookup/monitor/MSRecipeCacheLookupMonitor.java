package fixdol.mekanismelements.common.recipe.lookup.monitor;

import mekanism.api.math.FloatingLong;

import mekanism.api.recipes.cache.CachedRecipe;
import mekanism.api.IContentsListener;
import fixdol.mekanismelements.common.recipe.lookup.monitor.MSRecipeCacheLookupMonitor;
import mekanism.api.recipes.MekanismRecipe;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import fixdol.mekanismelements.common.recipe.lookup.IMSRecipeLookupHandler;
import mekanism.api.energy.IEnergyContainer;
import mekanism.api.recipes.cache.ICachedRecipeHolder;
import mekanism.common.CommonWorldTickHandler;
import mekanism.common.recipe.lookup.IRecipeLookupHandler;

public class MSRecipeCacheLookupMonitor<RECIPE extends MekanismRecipe> implements ICachedRecipeHolder<RECIPE>, IContentsListener {
    private final IMSRecipeLookupHandler<RECIPE> handler;
    protected final int cacheIndex;
    protected CachedRecipe<RECIPE> cachedRecipe;
    protected boolean hasNoRecipe;
    protected boolean shouldUnpause;

    public MSRecipeCacheLookupMonitor(IMSRecipeLookupHandler<RECIPE> handler) {
        this(handler, 0);
    }

    public MSRecipeCacheLookupMonitor(IMSRecipeLookupHandler<RECIPE> handler, int cacheIndex) {
        this.handler = handler;
        this.cacheIndex = cacheIndex;
    }

    protected boolean cachedIndexMatches(int cacheIndex) {
        return this.cacheIndex == cacheIndex;
    }

    @Override
    public final void onContentsChanged() {
        handler.onContentsChanged();
        onChange();
    }

    public void onChange() {
        //Mark that we may have a recipe again
        hasNoRecipe = false;
        unpause();
    }

    public void unpause() {
        shouldUnpause = true;
    }

    /**
     * Helper that wraps {@link #updateAndProcess()} inside of a brief check to calculate how much energy actually got used.
     */
    public FloatingLong updateAndProcess(IEnergyContainer energyContainer) {
        //Copy this so that if it changes we still have the original amount
        FloatingLong prev = energyContainer.getEnergy();
        if (updateAndProcess()) {
            //Update amount of energy that actually got used, as if we are "near" full we may not have performed our max number of operations
            return prev.subtract(energyContainer.getEnergy());
        }
        //If we do not have a cached recipe so did not process anything at all just return zero
        return FloatingLong.ZERO;
    }

    public boolean updateAndProcess() {
        CachedRecipe<RECIPE> oldCache = cachedRecipe;
        cachedRecipe = getUpdatedCache(cacheIndex);
        if (cachedRecipe != oldCache) {
            handler.onCachedRecipeChanged(cachedRecipe, cacheIndex);
        }
        if (cachedRecipe != null) {
            if (shouldUnpause) {
                shouldUnpause = false;
            }
            cachedRecipe.process();
            return true;
        }
        return false;
    }

    @Override
    public void loadSavedData(@NotNull CachedRecipe<RECIPE> cached, int cacheIndex) {
        if (cachedIndexMatches(cacheIndex)) {
            ICachedRecipeHolder.super.loadSavedData(cached, cacheIndex);
        }
    }

    @Override
    public int getSavedOperatingTicks(int cacheIndex) {
        return cachedIndexMatches(cacheIndex) ? handler.getSavedOperatingTicks(cacheIndex) : ICachedRecipeHolder.super.getSavedOperatingTicks(cacheIndex);
    }

    @Nullable
    @Override
    public CachedRecipe<RECIPE> getCachedRecipe(int cacheIndex) {
        return cachedIndexMatches(cacheIndex) ? cachedRecipe : null;
    }

    @Nullable
    @Override
    public RECIPE getRecipe(int cacheIndex) {
        return cachedIndexMatches(cacheIndex) ? handler.getRecipe(cacheIndex) : null;
    }

    @Nullable
    @Override
    public CachedRecipe<RECIPE> createNewCachedRecipe(@NotNull RECIPE recipe, int cacheIndex) {
        return cachedIndexMatches(cacheIndex) ? handler.createNewCachedRecipe(recipe, cacheIndex) : null;
    }

    @Override
    public boolean invalidateCache() {
        return CommonWorldTickHandler.flushTagAndRecipeCaches;
    }

    @Override
    public void setHasNoRecipe(int cacheIndex) {
        if (cachedIndexMatches(cacheIndex)) {
            hasNoRecipe = true;
        }
    }

    @Override
    public boolean hasNoRecipe(int cacheIndex) {
        return cachedIndexMatches(cacheIndex) ? hasNoRecipe : ICachedRecipeHolder.super.hasNoRecipe(cacheIndex);
    }
}