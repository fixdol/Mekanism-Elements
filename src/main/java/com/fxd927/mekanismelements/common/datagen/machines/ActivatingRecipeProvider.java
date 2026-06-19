package com.fxd927.mekanismelements.datagen.machines;

import com.fxd927.mekanismelements.common.MekanismElements;
import com.fxd927.mekanismelements.common.registries.MSGases;
import mekanism.api.chemical.ChemicalStack;
import mekanism.api.datagen.recipe.builder.ChemicalToChemicalRecipeBuilder;
import mekanism.api.recipes.ingredients.creator.IngredientCreatorAccess;
import mekanism.common.registries.MekanismChemicals;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;

public class ActivatingRecipeProvider {

    public static void buildRecipes(RecipeOutput recipeOutput) {
        String basePath = "activating/";

        // Plutonium (gas, 2 mB) -> Americium (gas, 1 mB)
        ChemicalToChemicalRecipeBuilder.activating(
                IngredientCreatorAccess.chemicalStack().from(MekanismChemicals.PLUTONIUM.get(), 2),
                new ChemicalStack(MSGases.AMERICIUM.get().getAsHolder(), 1)
        ).build(recipeOutput, ResourceLocation.fromNamespaceAndPath(MekanismElements.MODID, basePath + "americium"));

        // Uranium Hexafluoride (gas, 2 mB) -> Strontium (gas, 1 mB)
        ChemicalToChemicalRecipeBuilder.activating(
                IngredientCreatorAccess.chemicalStack().from(MekanismChemicals.URANIUM_HEXAFLUORIDE.get(), 2),
                new ChemicalStack(MSGases.STRONTIUM.get().getAsHolder(), 1)
        ).build(recipeOutput, ResourceLocation.fromNamespaceAndPath(MekanismElements.MODID, basePath + "strontium"));
    }
}