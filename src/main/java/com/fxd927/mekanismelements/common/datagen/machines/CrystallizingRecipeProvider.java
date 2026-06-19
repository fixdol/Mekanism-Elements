package com.fxd927.mekanismelements.datagen.machines;

import com.fxd927.mekanismelements.common.MekanismElements;
import com.fxd927.mekanismelements.common.registries.MSGases;
import com.fxd927.mekanismelements.common.registries.MSItems;
import mekanism.api.datagen.recipe.builder.ChemicalCrystallizerRecipeBuilder;
import mekanism.api.recipes.ingredients.creator.IngredientCreatorAccess;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class CrystallizingRecipeProvider {

    public static void buildRecipes(RecipeOutput recipeOutput) {
        String basePath = "crystallizing/";

        // Beryllium (gas, 1000 mB) -> Dust Beryllium (item)
        ChemicalCrystallizerRecipeBuilder.crystallizing(
                IngredientCreatorAccess.chemicalStack().from(MSGases.BERYLLIUM.get(), 1000),
                new ItemStack(MSItems.DUST_BERYLLIUM.get())
        ).build(recipeOutput, ResourceLocation.fromNamespaceAndPath(MekanismElements.MODID, basePath + "dust_beryllium"));

        // Potassium Iodide (gas, 100 mB) -> Tablet Iodine (item)
        ChemicalCrystallizerRecipeBuilder.crystallizing(
                IngredientCreatorAccess.chemicalStack().from(MSGases.POTASSIUM_IODIDE.get(), 100),
                new ItemStack(MSItems.TABLET_IODINE.get())
        ).build(recipeOutput, ResourceLocation.fromNamespaceAndPath(MekanismElements.MODID, basePath + "tablet_iodine"));
    }
}