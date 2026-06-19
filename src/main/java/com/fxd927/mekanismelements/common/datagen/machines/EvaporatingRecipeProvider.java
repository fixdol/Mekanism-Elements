package com.fxd927.mekanismelements.datagen.machines;

import com.fxd927.mekanismelements.common.MekanismElements;
import com.fxd927.mekanismelements.common.registries.MSFluids;
import mekanism.api.datagen.recipe.builder.FluidToFluidRecipeBuilder;
import mekanism.api.recipes.ingredients.creator.IngredientCreatorAccess;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.fluids.FluidStack;

public class EvaporatingRecipeProvider {

    public static void buildRecipes(RecipeOutput recipeOutput) {
        String basePath = "evaporating/";

        // Seawater (fluid, 1 mB) -> Potassium Chloride (fluid, 1 mB)
        FluidToFluidRecipeBuilder.evaporating(
                IngredientCreatorAccess.fluid().from(MSFluids.SEAWATER.get(), 1),
                new FluidStack(MSFluids.POTASSIUM_CHLORIDE.get(), 1)
        ).build(recipeOutput, ResourceLocation.fromNamespaceAndPath(MekanismElements.MODID, basePath + "potassium_chloride"));
    }
}