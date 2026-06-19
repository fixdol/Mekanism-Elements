package com.fxd927.mekanismelements.datagen.machines;

import com.fxd927.mekanismelements.common.MekanismElements;
import com.fxd927.mekanismelements.common.registries.MSItems;
import mekanism.api.datagen.recipe.builder.ItemStackToItemStackRecipeBuilder;
import mekanism.api.recipes.ingredients.creator.IngredientCreatorAccess;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class CrushingRecipeProvider {

    public static void buildRecipes(RecipeOutput recipeOutput) {
        String basePath = "crushing/";

        // Beryllium Ingot -> Beryllium Dust
        ItemStackToItemStackRecipeBuilder.crushing(
                IngredientCreatorAccess.item().from(MSItems.INGOT_BERYLLIUM.get()),
                new ItemStack(MSItems.DUST_BERYLLIUM.get(), 1)
        ).build(recipeOutput, ResourceLocation.fromNamespaceAndPath(MekanismElements.MODID, basePath + "beryllium_dust"));
    }
}