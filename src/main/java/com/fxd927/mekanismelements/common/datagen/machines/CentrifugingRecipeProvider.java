package com.fxd927.mekanismelements.datagen.machines;

import com.fxd927.mekanismelements.common.MekanismElements;
import com.fxd927.mekanismelements.common.registries.MSGases;
import mekanism.api.chemical.ChemicalStack;
import mekanism.api.datagen.recipe.builder.ChemicalToChemicalRecipeBuilder;
import mekanism.api.recipes.ingredients.creator.IngredientCreatorAccess;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;

public class CentrifugingRecipeProvider {

    public static void buildRecipes(RecipeOutput recipeOutput) {
        String basePath = "centrifuging/";

        // Dissolved Spent Nuclear Waste (100 mB) -> Curium (1 mB)
        ChemicalToChemicalRecipeBuilder.centrifuging(
                IngredientCreatorAccess.chemicalStack().from(MSGases.DISSOLVED_SPENT_NUCLEAR_WASTE.get(), 100),
                new ChemicalStack(MSGases.CURIUM.get().getAsHolder(), 1)
        ).build(recipeOutput, ResourceLocation.fromNamespaceAndPath(MekanismElements.MODID, basePath + "curium"));

        // Yttrium (10 mB) -> Helium (1 mB)
        ChemicalToChemicalRecipeBuilder.centrifuging(
                IngredientCreatorAccess.chemicalStack().from(MSGases.YTTRIUM.get(), 10),
                new ChemicalStack(MSGases.HELIUM.get().getAsHolder(), 1)
        ).build(recipeOutput, ResourceLocation.fromNamespaceAndPath(MekanismElements.MODID, basePath + "helium"));

        // Iodine (1 mB) -> Xenon (1 mB)
        ChemicalToChemicalRecipeBuilder.centrifuging(
                IngredientCreatorAccess.chemicalStack().from(MSGases.IODINE.get(), 1),
                new ChemicalStack(MSGases.XENON.get().getAsHolder(), 1)
        ).build(recipeOutput, ResourceLocation.fromNamespaceAndPath(MekanismElements.MODID, basePath + "xenon"));
    }
}