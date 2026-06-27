package com.fxd927.mekanismelements.common.recipe.lookup;

import com.fxd927.mekanismelements.common.recipe.IMSRecipeTypeProvider;
import mekanism.api.IContentsListener;
import mekanism.api.recipes.MekanismRecipe;
import mekanism.api.recipes.cache.CachedRecipe;
import mekanism.common.recipe.lookup.cache.IInputRecipeCache;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface IMSRecipeLookupHandler<RECIPE extends MekanismRecipe<?>> extends IContentsListener {

    @Nullable
    default Level getHandlerWorld() {
        if (this instanceof BlockEntity tile) {
            return tile.getLevel();
        } else if (this instanceof Entity entity) {
            return entity.level();
        }
        return null;
    }

    @NotNull
    IMSRecipeTypeProvider<RECIPE, ?> getMSRecipeType();

    default int getSavedOperatingTicks(int cacheIndex) {
        return 0;
    }

    @Nullable
    RECIPE getRecipe(int cacheIndex);

    @NotNull
    CachedRecipe<RECIPE> createNewCachedRecipe(@NotNull RECIPE recipe, int cacheIndex);

    // Sin implementación default — cada TileEntity la provee con @Override,
    // evitando el conflicto con el default de IRecipeLookupHandler de Mekanism.
    void onCachedRecipeChanged(@Nullable CachedRecipe<RECIPE> cachedRecipe, int cacheIndex);

    default void clearRecipeErrors(int cacheIndex) {
    }

    interface IMSRecipeTypedLookupHandler<RECIPE extends MekanismRecipe<?>, INPUT_CACHE extends IInputRecipeCache>
            extends IMSRecipeLookupHandler<RECIPE> {

        @NotNull
        @Override
        IMSRecipeTypeProvider<RECIPE, INPUT_CACHE> getMSRecipeType();
    }

    interface ConstantUsageRecipeLookupHandler {
        default long getSavedUsedSoFar(int cacheIndex) {
            return 0;
        }
    }
}