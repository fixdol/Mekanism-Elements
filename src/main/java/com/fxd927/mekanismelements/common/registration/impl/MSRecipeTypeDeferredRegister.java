package com.fxd927.mekanismelements.common.registration.impl;

import com.fxd927.mekanismelements.common.recipe.IMSRecipeTypeProvider;
import com.fxd927.mekanismelements.common.recipe.MSRecipeType;
import mekanism.api.recipes.MekanismRecipe;
import mekanism.common.recipe.lookup.cache.IInputRecipeCache;
import mekanism.common.registration.MekanismDeferredRegister;
import mekanism.common.registration.MekanismDeferredHolder;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

public class MSRecipeTypeDeferredRegister extends MekanismDeferredRegister<RecipeType<?>> {
    private final List<IMSRecipeTypeProvider<?, ?>> recipeTypes = new ArrayList<>();

    public MSRecipeTypeDeferredRegister(String modid) {
        super(Registries.RECIPE_TYPE, modid);
    }

    public <RECIPE extends MekanismRecipe<?>, MS_INPUT_CACHE extends IInputRecipeCache> MSRecipeTypeRegistryObject<RECIPE, MS_INPUT_CACHE> registerRecipeType(String name,
                                                                                                                                              Supplier<? extends MSRecipeType<RECIPE, MS_INPUT_CACHE>> sup) {
        MekanismDeferredHolder<RecipeType<?>, MSRecipeType<RECIPE, MS_INPUT_CACHE>> holder = register(name, () -> sup.get());
        MSRecipeTypeRegistryObject<RECIPE, MS_INPUT_CACHE> registeredRecipeType = new MSRecipeTypeRegistryObject<>(holder);
        recipeTypes.add(registeredRecipeType);
        return registeredRecipeType;
    }

    public List<IMSRecipeTypeProvider<?, ?>> getAllRecipeTypes() {
        return Collections.unmodifiableList(recipeTypes);
    }
}

