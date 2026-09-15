package fixdol.mekanismelements.common.datagen.machines;

import java.util.function.Consumer;
import net.minecraft.data.recipes.FinishedRecipe;
import mekanism.api.chemical.gas.GasStack;
import net.minecraft.world.item.crafting.Ingredient;
import mekanism.api.recipes.ingredients.creator.IngredientCreatorAccess;
import fixdol.mekanismelements.common.registries.MSGases;
import fixdol.mekanismelements.common.MekanismElements;
import mekanism.common.registries.MekanismItems;
import fixdol.mekanismelements.common.datagen.machines.OxidizingRecipeProvider;
import net.minecraft.resources.ResourceLocation;


import mekanism.api.datagen.recipe.builder.ItemStackToChemicalRecipeBuilder;

public class OxidizingRecipeProvider {

    public static void buildRecipes(Consumer<FinishedRecipe> recipeOutput) {
        String basePath = "oxidizing/";

        // Substrate -> Methane (100 mB)
        ItemStackToChemicalRecipeBuilder.oxidizing(
                IngredientCreatorAccess.item().from(Ingredient.of(MekanismItems.SUBSTRATE.get())),
                new GasStack(MSGases.METHANE.get(), 100)
        ).build(recipeOutput, new ResourceLocation(MekanismElements.MODID, basePath + "methane"));
    }
}