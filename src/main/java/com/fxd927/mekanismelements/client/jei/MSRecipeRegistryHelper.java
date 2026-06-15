package com.fxd927.mekanismelements.client.jei;

import com.fxd927.mekanismelements.common.recipe.IMSRecipeTypeProvider;
import mekanism.api.recipes.MekanismRecipe;
import mekanism.client.MekanismClient;
import mekanism.client.recipe_viewer.jei.MekanismJEI;
import mekanism.client.recipe_viewer.type.IRecipeViewerRecipeType;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;

import java.util.List;

public class MSRecipeRegistryHelper {
    private MSRecipeRegistryHelper() {
    }

    public static <RECIPE extends MekanismRecipe<?>> void register(IRecipeRegistration registry, IRecipeViewerRecipeType<RECIPE> recipeType,
                                                                IMSRecipeTypeProvider<RECIPE, ?> type) {
        ClientLevel world = Minecraft.getInstance().level;
        if (world != null) {
            List<RECIPE> recipes = type.getMSRecipeType().getRecipes(world);
            if (!recipes.isEmpty()) {
                register(registry, recipeType, recipes);
            }
        }
    }

    public static <RECIPE> void register(IRecipeRegistration registry, IRecipeViewerRecipeType<RECIPE> recipeType, List<RECIPE> recipes) {
        if (!recipes.isEmpty()) {
            registry.addRecipes(MekanismJEI.recipeType(recipeType), recipes);
        }
    }
}