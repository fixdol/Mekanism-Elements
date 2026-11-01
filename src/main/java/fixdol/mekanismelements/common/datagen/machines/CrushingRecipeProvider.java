package fixdol.mekanismelements.datagen.machines;

import fixdol.mekanismelements.common.MekanismElements;
import fixdol.mekanismelements.common.registries.MSItems;
import mekanism.api.datagen.recipe.builder.ItemStackToItemStackRecipeBuilder;
import mekanism.api.recipes.ingredients.creator.IngredientCreatorAccess;
import net.minecraft.data.recipes.RecipeOutput;

public class CrushingRecipeProvider {

    public static void buildRecipes(RecipeOutput recipeOutput) {
        String basePath = "crushing/";

        // Beryllium Ingot -> Beryllium Dust
        ItemStackToItemStackRecipeBuilder.crushing(
                IngredientCreatorAccess.item().from(MSItems.INGOT_BERYLLIUM),
                MSItems.DUST_BERYLLIUM.asTemplate()
        ).save(recipeOutput, MekanismElements.rl(basePath + "beryllium_dust"));
    }
}
