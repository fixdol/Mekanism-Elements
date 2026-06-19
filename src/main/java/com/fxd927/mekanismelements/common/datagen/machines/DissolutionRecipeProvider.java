package com.fxd927.mekanismelements.datagen.machines;

import com.fxd927.mekanismelements.common.MekanismElements;
import com.fxd927.mekanismelements.common.registries.MSGases;
import mekanism.api.chemical.ChemicalStack;
import mekanism.api.datagen.recipe.builder.ChemicalDissolutionRecipeBuilder;
import mekanism.api.recipes.ingredients.creator.IngredientCreatorAccess;
import mekanism.common.registries.MekanismChemicals;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;

public class DissolutionRecipeProvider {

    public static void buildRecipes(RecipeOutput recipeOutput) {
        String basePath = "dissolution/";

        // Netherite Dust (item) + Aqua Regia (gas, 1 mB) -> Netherite Acid (gas, 100 mB)
        ChemicalDissolutionRecipeBuilder.dissolution(
                IngredientCreatorAccess.item().from(
                        ItemTags.create(ResourceLocation.fromNamespaceAndPath("c", "dusts/netherite")), 1),
                IngredientCreatorAccess.chemicalStack().from(MSGases.AQUA_REGIA.get(), 1),
                new ChemicalStack(MSGases.NETHERITE_ACID.get().getAsHolder(), 100),
                false
        ).build(recipeOutput, ResourceLocation.fromNamespaceAndPath(MekanismElements.MODID, basePath + "netherite_acid"));

        // Emerald Ore (item, 1) + Sulfuric Acid (gas, 1 mB) -> Beryllium (gas, 1000 mB) [per_tick_usage = true]
        ChemicalDissolutionRecipeBuilder.dissolution(
                IngredientCreatorAccess.item().from(
                        ItemTags.create(ResourceLocation.fromNamespaceAndPath("c", "ores/emerald")), 1),
                IngredientCreatorAccess.chemicalStack().from(MekanismChemicals.SULFURIC_ACID.get(), 1),
                new ChemicalStack(MSGases.BERYLLIUM.get().getAsHolder(), 1000),
                true
        ).build(recipeOutput, ResourceLocation.fromNamespaceAndPath(MekanismElements.MODID, basePath + "beryllium"));
    }
}