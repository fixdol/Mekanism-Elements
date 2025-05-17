package com.fxd927.mekanismelements.common.registration.impl;

import com.fxd927.mekanismelements.common.recipe.IMSRecipeTypeProvider;
import com.fxd927.mekanismelements.common.recipe.MSRecipeType;
import mekanism.api.recipes.MekanismRecipe;
import mekanism.common.recipe.lookup.cache.IInputRecipeCache;
import mekanism.common.registration.WrappedRegistryObject;
import net.minecraftforge.registries.RegistryObject;

public class MSRecipeTypeRegistryObject <RECIPE extends MekanismRecipe, MS_INPUT_CACHE extends IInputRecipeCache> extends
        WrappedRegistryObject<MSRecipeType<RECIPE, MS_INPUT_CACHE>> implements IMSRecipeTypeProvider<RECIPE, MS_INPUT_CACHE> {
    public MSRecipeTypeRegistryObject(RegistryObject<MSRecipeType<RECIPE, MS_INPUT_CACHE>> registryObject) {
        super(registryObject);
    }

    @Override
    public MSRecipeType<RECIPE, MS_INPUT_CACHE> getMSRecipeType() {
        return get();
    }
}
