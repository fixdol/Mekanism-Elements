package fixdol.mekanismelements.common.datagen.machines;

import java.util.function.Consumer;
import fixdol.mekanismelements.common.datagen.machines.CrushingRecipeProvider;
import net.minecraft.data.recipes.FinishedRecipe;
import mekanism.api.recipes.ingredients.creator.IngredientCreatorAccess;
import net.minecraft.world.item.ItemStack;
import fixdol.mekanismelements.common.MekanismElements;
import net.minecraft.resources.ResourceLocation;


import fixdol.mekanismelements.common.registries.MSItems;
import mekanism.api.datagen.recipe.builder.ItemStackToItemStackRecipeBuilder;

public class CrushingRecipeProvider {

    public static void buildRecipes(Consumer<FinishedRecipe> recipeOutput) {
        String basePath = "crushing/";

        // Beryllium Ingot -> Beryllium Dust
        ItemStackToItemStackRecipeBuilder.crushing(
                IngredientCreatorAccess.item().from(MSItems.INGOT_BERYLLIUM.get()),
                new ItemStack(MSItems.DUST_BERYLLIUM.get(), 1)
        ).build(recipeOutput, new ResourceLocation(MekanismElements.MODID, basePath + "beryllium_dust"));
    }
}