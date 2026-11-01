package fixdol.mekanismelements.datagen.machines;

import net.minecraft.data.recipes.RecipeOutput;

public class NucleosynthesizingRecipeProvider {

    public static void buildRecipes(RecipeOutput recipeOutput) {
        // Requiere MSItems.UNSTABLE_CALIFORNIUM_MIXTURE e MSItems.INGOT_REFINED_CALIFORNIUM
        // Descomentar cuando estén registrados en MSItems

        /*
        NucleosynthesizingRecipeBuilder.nucleosynthesizing(
                IngredientCreatorAccess.item().from(MSItems.UNSTABLE_CALIFORNIUM_MIXTURE, 8),
                IngredientCreatorAccess.chemicalStack().fromHolder(MekanismChemicals.ANTIMATTER, 1000),
                MSItems.INGOT_REFINED_CALIFORNIUM.asTemplate(),
                1000, false
        ).save(recipeOutput, MekanismElements.rl("nucleosynthesizing/ingot_refined_californium"));
        */
    }
}
