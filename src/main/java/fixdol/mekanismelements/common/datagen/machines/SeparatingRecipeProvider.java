package fixdol.mekanismelements.datagen.machines;

import fixdol.mekanismelements.common.MekanismElements;
import fixdol.mekanismelements.common.registries.MSFluids;
import fixdol.mekanismelements.common.registries.MSGases;
import mekanism.api.datagen.recipe.builder.ElectrolysisRecipeBuilder;
import mekanism.api.recipes.ingredients.creator.IngredientCreatorAccess;
import mekanism.common.registries.MekanismChemicals;
import net.minecraft.data.recipes.RecipeOutput;

public class SeparatingRecipeProvider {

    public static void buildRecipes(RecipeOutput recipeOutput) {
        String basePath = "separating/";

        // Potassium Chloride (fluid, 2 mB) -> Potassium Hydroxide (left, 2 mB) + Chlorine (right, 1 mB)
        ElectrolysisRecipeBuilder.separating(
                IngredientCreatorAccess.fluid().fromHolder(MSFluids.POTASSIUM_CHLORIDE, 2),
                MSGases.POTASSIUM_HYDROXIDE.asTemplate(2),
                MekanismChemicals.CHLORINE.asTemplate(1)
        ).save(recipeOutput, MekanismElements.rl(basePath + "potassium_chloride"));
    }
}
