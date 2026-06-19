package com.fxd927.mekanismelements.datagen.machines;

import com.fxd927.mekanismelements.common.MekanismElements;
import com.fxd927.mekanismelements.common.registries.MSGases;
import mekanism.api.chemical.ChemicalStack;
import mekanism.api.datagen.recipe.builder.ItemStackToChemicalRecipeBuilder;
import mekanism.api.recipes.ingredients.creator.IngredientCreatorAccess;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

public class OxidizingRecipeProvider {

    public static void buildRecipes(RecipeOutput recipeOutput) {
        String basePath = "oxidizing/";

        // Bio Fuel (item) -> Methane (100 mB)
        ItemStackToChemicalRecipeBuilder.oxidizing(
                IngredientCreatorAccess.item().from(Ingredient.of(mekanism.common.registries.MekanismItems.BIO_FUEL.get())),
                new ChemicalStack(MSGases.METHANE.get().getAsHolder(), 100)
        ).build(recipeOutput, ResourceLocation.fromNamespaceAndPath(MekanismElements.MODID, basePath + "methane"));
    }
}