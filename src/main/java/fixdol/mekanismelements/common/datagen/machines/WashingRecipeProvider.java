package fixdol.mekanismelements.datagen.machines;

import fixdol.mekanismelements.common.MekanismElements;
import fixdol.mekanismelements.common.registries.MSGases;
import mekanism.api.datagen.recipe.builder.FluidChemicalToChemicalRecipeBuilder;
import mekanism.api.recipes.ingredients.creator.IngredientCreatorAccess;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.tags.FluidTags;

public class WashingRecipeProvider {

    public static void buildRecipes(RecipeOutput recipeOutput, HolderLookup.Provider registries) {
        String basePath = "washing/";

        FluidChemicalToChemicalRecipeBuilder.washing(
                IngredientCreatorAccess.fluid().from(registries.lookupOrThrow(Registries.FLUID), FluidTags.WATER, 100),
                IngredientCreatorAccess.chemicalStack().fromHolder(MSGases.AMMONIUM_NITRATE, 1000),
                MSGases.AMMONIUM_NITRATE_SOLUTION.asTemplate(1000)
        ).save(recipeOutput, MekanismElements.rl(basePath + "ammonium_nitrate_solution"));
    }
}
