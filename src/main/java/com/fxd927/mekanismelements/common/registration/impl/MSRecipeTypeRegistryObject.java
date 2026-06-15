package com.fxd927.mekanismelements.common.registration.impl;

import com.fxd927.mekanismelements.common.recipe.IMSRecipeTypeProvider;
import com.fxd927.mekanismelements.common.recipe.MSRecipeType;
import mekanism.api.recipes.MekanismRecipe;
import mekanism.common.recipe.lookup.cache.IInputRecipeCache;
import mekanism.common.registration.MekanismDeferredHolder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeType;

public class MSRecipeTypeRegistryObject <RECIPE extends MekanismRecipe<?>, MS_INPUT_CACHE extends IInputRecipeCache> extends
        MekanismDeferredHolder<RecipeType<?>, MSRecipeType<RECIPE, MS_INPUT_CACHE>> implements IMSRecipeTypeProvider<RECIPE, MS_INPUT_CACHE> {
    public MSRecipeTypeRegistryObject(MekanismDeferredHolder<RecipeType<?>, MSRecipeType<RECIPE, MS_INPUT_CACHE>> holder) {
        super(Registries.RECIPE_TYPE, holder.getId());
    }

    @Override
    public MSRecipeType<RECIPE, MS_INPUT_CACHE> getMSRecipeType() {
        return get();
    }
}

