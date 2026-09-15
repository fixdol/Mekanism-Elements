package fixdol.mekanismelements.common.datagen.machines;

import fixdol.mekanismelements.common.datagen.machines.ActivatingRecipeProvider;
import java.util.function.Consumer;

import net.minecraft.data.recipes.FinishedRecipe;

import fixdol.mekanismelements.common.MekanismElements;
import fixdol.mekanismelements.common.registries.MSGases;
import mekanism.api.chemical.gas.GasStack;
import mekanism.api.datagen.recipe.builder.GasToGasRecipeBuilder;
import mekanism.api.recipes.ingredients.creator.IngredientCreatorAccess;
import mekanism.common.registries.MekanismGases;
import net.minecraft.resources.ResourceLocation;

public class ActivatingRecipeProvider {

    public static void buildRecipes(Consumer<FinishedRecipe> recipeOutput) {
        String basePath = "activating/";

        // Plutonium (gas, 2 mB) -> Americium (gas, 1 mB)
        GasToGasRecipeBuilder.activating(
                IngredientCreatorAccess.gas().from(MekanismGases.PLUTONIUM.get(), 2),
                new GasStack(MSGases.AMERICIUM.get(), 1)
        ).build(recipeOutput, new ResourceLocation(MekanismElements.MODID, basePath + "americium"));

        // Uranium Hexafluoride (gas, 2 mB) -> Strontium (gas, 1 mB)
        GasToGasRecipeBuilder.activating(
                IngredientCreatorAccess.gas().from(MekanismGases.URANIUM_HEXAFLUORIDE.get(), 2),
                new GasStack(MSGases.STRONTIUM.get(), 1)
        ).build(recipeOutput, new ResourceLocation(MekanismElements.MODID, basePath + "strontium"));
    }
}