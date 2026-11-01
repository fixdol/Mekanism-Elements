package fixdol.mekanismelements.datagen.machines;

import fixdol.mekanismelements.common.MekanismElements;
import fixdol.mekanismelements.common.registries.MSGases;
import mekanism.api.datagen.recipe.builder.ItemStackToChemicalRecipeBuilder;
import mekanism.api.recipes.ingredients.creator.IngredientCreatorAccess;
import mekanism.common.registries.MekanismItems;
import net.minecraft.data.recipes.RecipeOutput;

public class OxidizingRecipeProvider {

    public static void buildRecipes(RecipeOutput recipeOutput) {
        String basePath = "oxidizing/";

        // Substrate -> Methane (100 mB)
        ItemStackToChemicalRecipeBuilder.oxidizing(
                IngredientCreatorAccess.item().from(MekanismItems.SUBSTRATE),
                MSGases.METHANE.asTemplate(100)
        ).save(recipeOutput, MekanismElements.rl(basePath + "methane"));
    }
}
