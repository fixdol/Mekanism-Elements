package fixdol.mekanismelements.datagen.machines;

import fixdol.mekanismelements.common.MekanismElements;
import fixdol.mekanismelements.common.registries.MSGases;
import fixdol.mekanismelements.common.registries.MSItems;
import mekanism.api.datagen.recipe.builder.ChemicalCrystallizerRecipeBuilder;
import mekanism.api.recipes.ingredients.creator.IngredientCreatorAccess;
import net.minecraft.data.recipes.RecipeOutput;

public class CrystallizingRecipeProvider {

    public static void buildRecipes(RecipeOutput recipeOutput) {
        String basePath = "crystallizing/";

        // Beryllium (gas, 1000 mB) -> Dust Beryllium (item)
        ChemicalCrystallizerRecipeBuilder.crystallizing(
                IngredientCreatorAccess.chemicalStack().fromHolder(MSGases.BERYLLIUM, 1000),
                MSItems.DUST_BERYLLIUM.asTemplate()
        ).save(recipeOutput, MekanismElements.rl(basePath + "dust_beryllium"));

        // Potassium Iodide (gas, 100 mB) -> Tablet Iodine (item)
        ChemicalCrystallizerRecipeBuilder.crystallizing(
                IngredientCreatorAccess.chemicalStack().fromHolder(MSGases.POTASSIUM_IODIDE, 100),
                MSItems.TABLET_IODINE.asTemplate()
        ).save(recipeOutput, MekanismElements.rl(basePath + "tablet_iodine"));
    }
}
