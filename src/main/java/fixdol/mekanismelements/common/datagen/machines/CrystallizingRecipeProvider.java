package fixdol.mekanismelements.common.datagen.machines;

import java.util.function.Consumer;
import fixdol.mekanismelements.common.datagen.machines.CrystallizingRecipeProvider;
import net.minecraft.data.recipes.FinishedRecipe;
import mekanism.api.recipes.ingredients.creator.IngredientCreatorAccess;
import net.minecraft.world.item.ItemStack;
import fixdol.mekanismelements.common.registries.MSGases;
import fixdol.mekanismelements.common.registries.MSItems;
import fixdol.mekanismelements.common.MekanismElements;
import net.minecraft.resources.ResourceLocation;


import mekanism.api.datagen.recipe.builder.ChemicalCrystallizerRecipeBuilder;

public class CrystallizingRecipeProvider {

    public static void buildRecipes(Consumer<FinishedRecipe> recipeOutput) {
        String basePath = "crystallizing/";

        // Beryllium (gas, 1000 mB) -> Dust Beryllium (item)
        ChemicalCrystallizerRecipeBuilder.crystallizing(
                IngredientCreatorAccess.gas().from(MSGases.BERYLLIUM.get(), 1000),
                new ItemStack(MSItems.DUST_BERYLLIUM.get())
        ).build(recipeOutput, new ResourceLocation(MekanismElements.MODID, basePath + "dust_beryllium"));

        // Potassium Iodide (gas, 100 mB) -> Tablet Iodine (item)
        ChemicalCrystallizerRecipeBuilder.crystallizing(
                IngredientCreatorAccess.gas().from(MSGases.POTASSIUM_IODIDE.get(), 100),
                new ItemStack(MSItems.TABLET_IODINE.get())
        ).build(recipeOutput, new ResourceLocation(MekanismElements.MODID, basePath + "tablet_iodine"));
    }
}