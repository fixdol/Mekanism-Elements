package com.fxd927.mekanismelements.datagen.machines;

import com.fxd927.mekanismelements.common.MekanismElements;
import com.fxd927.mekanismelements.common.registries.MSGases;
import mekanism.api.datagen.recipe.builder.FluidChemicalToChemicalRecipeBuilder;
import mekanism.api.recipes.ingredients.creator.IngredientCreatorAccess;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.FluidTags;

public class WashingRecipeProvider {

    public static void buildRecipes(RecipeOutput recipeOutput) {
        String basePath = "washing/";

       
        FluidChemicalToChemicalRecipeBuilder.washing(
                IngredientCreatorAccess.fluid().from(FluidTags.WATER, 100),
                IngredientCreatorAccess.chemicalStack().from(MSGases.AMMONIUM_NITRATE.get(), 1000),
                MSGases.AMMONIUM_NITRATE_SOLUTION.get().getStack(1000)
        ).build(recipeOutput, ResourceLocation.fromNamespaceAndPath(
                MekanismElements.MODID, basePath + "ammonium_nitrate_solution"));
    }
}