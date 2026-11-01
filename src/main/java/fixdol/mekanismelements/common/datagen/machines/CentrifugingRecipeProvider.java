package fixdol.mekanismelements.datagen.machines;

import fixdol.mekanismelements.common.MekanismElements;
import fixdol.mekanismelements.common.registries.MSGases;
import mekanism.api.datagen.recipe.builder.ChemicalToChemicalRecipeBuilder;
import mekanism.api.recipes.ingredients.creator.IngredientCreatorAccess;
import net.minecraft.data.recipes.RecipeOutput;

public class CentrifugingRecipeProvider {

    public static void buildRecipes(RecipeOutput recipeOutput) {
        String basePath = "centrifuging/";

        // Dissolved Spent Nuclear Waste (100 mB) -> Curium (1 mB)
        ChemicalToChemicalRecipeBuilder.centrifuging(
                IngredientCreatorAccess.chemicalStack().fromHolder(MSGases.DISSOLVED_SPENT_NUCLEAR_WASTE, 100),
                MSGases.CURIUM.asTemplate(1)
        ).save(recipeOutput, MekanismElements.rl(basePath + "curium"));

        // Yttrium (10 mB) -> Helium (1 mB)
        ChemicalToChemicalRecipeBuilder.centrifuging(
                IngredientCreatorAccess.chemicalStack().fromHolder(MSGases.YTTRIUM, 10),
                MSGases.HELIUM.asTemplate(1)
        ).save(recipeOutput, MekanismElements.rl(basePath + "helium"));

        // Iodine (1 mB) -> Xenon (1 mB)
        ChemicalToChemicalRecipeBuilder.centrifuging(
                IngredientCreatorAccess.chemicalStack().fromHolder(MSGases.IODINE, 1),
                MSGases.XENON.asTemplate(1)
        ).save(recipeOutput, MekanismElements.rl(basePath + "xenon"));
    }
}
