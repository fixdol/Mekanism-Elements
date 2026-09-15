package fixdol.mekanismelements.common.datagen.machines;

import fixdol.mekanismelements.common.datagen.machines.CentrifugingRecipeProvider;
import mekanism.api.datagen.recipe.builder.GasToGasRecipeBuilder;
import java.util.function.Consumer;
import net.minecraft.data.recipes.FinishedRecipe;
import mekanism.api.chemical.gas.GasStack;
import mekanism.api.recipes.ingredients.creator.IngredientCreatorAccess;
import fixdol.mekanismelements.common.registries.MSGases;
import fixdol.mekanismelements.common.MekanismElements;
import net.minecraft.resources.ResourceLocation;



public class CentrifugingRecipeProvider {

    public static void buildRecipes(Consumer<FinishedRecipe> recipeOutput) {
        String basePath = "centrifuging/";

        // Dissolved Spent Nuclear Waste (100 mB) -> Curium (1 mB)
        GasToGasRecipeBuilder.centrifuging(
                IngredientCreatorAccess.gas().from(MSGases.DISSOLVED_SPENT_NUCLEAR_WASTE.get(), 100),
                new GasStack(MSGases.CURIUM.get(), 1)
        ).build(recipeOutput, new ResourceLocation(MekanismElements.MODID, basePath + "curium"));

        // Yttrium (10 mB) -> Helium (1 mB)
        GasToGasRecipeBuilder.centrifuging(
                IngredientCreatorAccess.gas().from(MSGases.YTTRIUM.get(), 10),
                new GasStack(MSGases.HELIUM.get(), 1)
        ).build(recipeOutput, new ResourceLocation(MekanismElements.MODID, basePath + "helium"));

        // Iodine (1 mB) -> Xenon (1 mB)
        GasToGasRecipeBuilder.centrifuging(
                IngredientCreatorAccess.gas().from(MSGases.IODINE.get(), 1),
                new GasStack(MSGases.XENON.get(), 1)
        ).build(recipeOutput, new ResourceLocation(MekanismElements.MODID, basePath + "xenon"));
    }
}