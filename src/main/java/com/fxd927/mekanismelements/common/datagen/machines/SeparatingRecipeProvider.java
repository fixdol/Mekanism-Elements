package com.fxd927.mekanismelements.datagen.machines;

import com.fxd927.mekanismelements.common.MekanismElements;
import com.fxd927.mekanismelements.common.registries.MSFluids;
import com.fxd927.mekanismelements.common.registries.MSGases;
import mekanism.api.chemical.ChemicalStack;
import mekanism.api.datagen.recipe.builder.ElectrolysisRecipeBuilder;
import mekanism.api.recipes.ingredients.creator.IngredientCreatorAccess;
import mekanism.common.registries.MekanismChemicals;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;

public class SeparatingRecipeProvider {

    public static void buildRecipes(RecipeOutput recipeOutput) {
        String basePath = "separating/";

        // Potassium Chloride (fluid, 2 mB) -> Potassium Hydroxide (left, 2 mB) + Chlorine (right, 1 mB)
        ElectrolysisRecipeBuilder.separating(
                IngredientCreatorAccess.fluid().from(MSFluids.POTASSIUM_CHLORIDE.get(), 2),
                new ChemicalStack(MSGases.POTASSIUM_HYDROXIDE.get().getAsHolder(), 2),
                new ChemicalStack(MekanismChemicals.CHLORINE.get().getAsHolder(), 1)
        ).build(recipeOutput, ResourceLocation.fromNamespaceAndPath(MekanismElements.MODID, basePath + "potassium_chloride"));
    }
}