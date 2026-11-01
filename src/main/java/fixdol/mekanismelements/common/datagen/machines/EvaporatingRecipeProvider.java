package fixdol.mekanismelements.datagen.machines;

import fixdol.mekanismelements.common.MekanismElements;
import fixdol.mekanismelements.common.registries.MSFluids;
import mekanism.api.datagen.recipe.builder.FluidToFluidRecipeBuilder;
import mekanism.api.recipes.ingredients.creator.IngredientCreatorAccess;
import net.minecraft.data.recipes.RecipeOutput;

public class EvaporatingRecipeProvider {

    public static void buildRecipes(RecipeOutput recipeOutput) {
        String basePath = "evaporating/";

        // Seawater (fluid, 1 mB) -> Potassium Chloride (fluid, 1 mB)
        FluidToFluidRecipeBuilder.evaporating(
                IngredientCreatorAccess.fluid().fromHolder(MSFluids.SEAWATER, 1),
                MSFluids.POTASSIUM_CHLORIDE.asTemplate(1)
        ).save(recipeOutput, MekanismElements.rl(basePath + "potassium_chloride"));
    }
}
