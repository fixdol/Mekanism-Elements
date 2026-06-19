package com.fxd927.mekanismelements.datagen.machines;

import com.fxd927.mekanismelements.common.MekanismElements;
import mekanism.api.datagen.recipe.builder.ItemStackChemicalToItemStackRecipeBuilder;
import mekanism.api.recipes.ingredients.creator.IngredientCreatorAccess;
import mekanism.common.registries.MekanismChemicals;
import net.minecraft.data.recipes.RecipeOutput;

public class NucleosynthesizingRecipeProvider {

    public static void buildRecipes(RecipeOutput recipeOutput) {
        // Requiere MSItems.UNSTABLE_CALIFORNIUM_MIXTURE e MSItems.INGOT_REFINED_CALIFORNIUM
        // Descomentar cuando estén registrados en MSItems

        /*
        ItemStackChemicalToItemStackRecipeBuilder.nucleosynthesizing(
                IngredientCreatorAccess.item().from(MSItems.UNSTABLE_CALIFORNIUM_MIXTURE.get(), 8),
                IngredientCreatorAccess.chemicalStack().from(MekanismChemicals.ANTIMATTER.get(), 1000),
                new ItemStack(MSItems.INGOT_REFINED_CALIFORNIUM.get(), 1),
                1000
        ).build(recipeOutput, ResourceLocation.fromNamespaceAndPath(MekanismElements.MODID, "nucleosynthesizing/ingot_refined_californium"));
        */
    }
}