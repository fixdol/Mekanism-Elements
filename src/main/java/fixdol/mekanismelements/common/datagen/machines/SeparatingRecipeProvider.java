package fixdol.mekanismelements.common.datagen.machines;

import java.util.function.Consumer;
import net.minecraft.data.recipes.FinishedRecipe;
import mekanism.api.chemical.gas.GasStack;
import mekanism.api.recipes.ingredients.creator.IngredientCreatorAccess;
import fixdol.mekanismelements.common.registries.MSFluids;
import fixdol.mekanismelements.common.registries.MSGases;
import fixdol.mekanismelements.common.MekanismElements;
import mekanism.common.registries.MekanismGases;
import net.minecraft.resources.ResourceLocation;
import fixdol.mekanismelements.common.datagen.machines.SeparatingRecipeProvider;


import mekanism.api.datagen.recipe.builder.ElectrolysisRecipeBuilder;

public class SeparatingRecipeProvider {

    public static void buildRecipes(Consumer<FinishedRecipe> recipeOutput) {
        String basePath = "separating/";

        // Potassium Chloride (fluid, 2 mB) -> Potassium Hydroxide (left, 2 mB) + Chlorine (right, 1 mB)
        ElectrolysisRecipeBuilder.separating(
                IngredientCreatorAccess.fluid().from(MSFluids.POTASSIUM_CHLORIDE.getFluid(), 2),
                new GasStack(MSGases.POTASSIUM_HYDROXIDE.get(), 2),
                new GasStack(MekanismGases.CHLORINE.get(), 1)
        ).build(recipeOutput, new ResourceLocation(MekanismElements.MODID, basePath + "potassium_chloride"));
    }
}