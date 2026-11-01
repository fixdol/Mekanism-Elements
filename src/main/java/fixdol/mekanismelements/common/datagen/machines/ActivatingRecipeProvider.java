package fixdol.mekanismelements.datagen.machines;

import fixdol.mekanismelements.common.MekanismElements;
import fixdol.mekanismelements.common.registries.MSGases;
import mekanism.api.datagen.recipe.builder.ChemicalToChemicalRecipeBuilder;
import mekanism.api.recipes.ingredients.creator.IngredientCreatorAccess;
import mekanism.common.registries.MekanismChemicals;
import net.minecraft.data.recipes.RecipeOutput;

public class ActivatingRecipeProvider {

    public static void buildRecipes(RecipeOutput recipeOutput) {
        String basePath = "activating/";

        // Plutonium (gas, 2 mB) -> Americium (gas, 1 mB)
        ChemicalToChemicalRecipeBuilder.activating(
                IngredientCreatorAccess.chemicalStack().fromHolder(MekanismChemicals.PLUTONIUM, 2),
                MSGases.AMERICIUM.asTemplate(1)
        ).save(recipeOutput, MekanismElements.rl(basePath + "americium"));

        // Uranium Hexafluoride (gas, 2 mB) -> Strontium (gas, 1 mB)
        ChemicalToChemicalRecipeBuilder.activating(
                IngredientCreatorAccess.chemicalStack().fromHolder(MekanismChemicals.URANIUM_HEXAFLUORIDE, 2),
                MSGases.STRONTIUM.asTemplate(1)
        ).save(recipeOutput, MekanismElements.rl(basePath + "strontium"));
    }
}
