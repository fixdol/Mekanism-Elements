package fixdol.mekanismelements.common.datagen.machines;

import java.util.function.Consumer;
import fixdol.mekanismelements.common.datagen.machines.DissolutionRecipeProvider;
import net.minecraft.data.recipes.FinishedRecipe;
import mekanism.api.chemical.gas.GasStack;
import mekanism.api.recipes.ingredients.creator.IngredientCreatorAccess;
import fixdol.mekanismelements.common.registries.MSGases;
import fixdol.mekanismelements.common.MekanismElements;
import mekanism.common.registries.MekanismGases;
import net.minecraft.resources.ResourceLocation;


import mekanism.api.datagen.recipe.builder.ChemicalDissolutionRecipeBuilder;
import net.minecraft.tags.ItemTags;

public class DissolutionRecipeProvider {

    public static void buildRecipes(Consumer<FinishedRecipe> recipeOutput) {
        String basePath = "dissolution/";

        // Netherite Dust (item) + Aqua Regia (gas, 1 mB) -> Netherite Acid (gas, 100 mB)
        ChemicalDissolutionRecipeBuilder.dissolution(
                IngredientCreatorAccess.item().from(
                        ItemTags.create(new ResourceLocation("forge", "dusts/netherite")), 1),
                IngredientCreatorAccess.gas().from(MSGases.AQUA_REGIA.get(), 1),
                new GasStack(MSGases.NETHERITE_ACID.get(), 100)
        ).build(recipeOutput, new ResourceLocation(MekanismElements.MODID, basePath + "netherite_acid"));

        // Emerald Ore (item, 1) + Sulfuric Acid (gas, 1 mB) -> Beryllium (gas, 1000 mB) [per_tick_usage = true]
        ChemicalDissolutionRecipeBuilder.dissolution(
                IngredientCreatorAccess.item().from(
                        ItemTags.create(new ResourceLocation("forge", "ores/emerald")), 1),
                IngredientCreatorAccess.gas().from(MekanismGases.SULFURIC_ACID.get(), 1),
                new GasStack(MSGases.BERYLLIUM.get(), 1000)
        ).build(recipeOutput, new ResourceLocation(MekanismElements.MODID, basePath + "beryllium"));
    }
}