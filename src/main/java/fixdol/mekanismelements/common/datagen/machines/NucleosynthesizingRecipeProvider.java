package fixdol.mekanismelements.common.datagen.machines;

import java.util.function.Consumer;
import net.minecraft.data.recipes.FinishedRecipe;
import mekanism.api.recipes.ingredients.creator.IngredientCreatorAccess;
import net.minecraft.world.item.ItemStack;
import mekanism.api.datagen.recipe.builder.ItemStackChemicalToItemStackRecipeBuilder;
import fixdol.mekanismelements.common.registries.MSItems;
import fixdol.mekanismelements.common.MekanismElements;
import mekanism.common.registries.MekanismGases;
import fixdol.mekanismelements.common.datagen.machines.NucleosynthesizingRecipeProvider;
import net.minecraft.resources.ResourceLocation;



public class NucleosynthesizingRecipeProvider {

    public static void buildRecipes(Consumer<FinishedRecipe> recipeOutput) {
        // Requiere MSItems.UNSTABLE_CALIFORNIUM_MIXTURE e MSItems.INGOT_REFINED_CALIFORNIUM
        // Descomentar cuando estén registrados en MSItems

        /*
        ItemStackChemicalToItemStackRecipeBuilder.nucleosynthesizing(
                IngredientCreatorAccess.item().from(MSItems.UNSTABLE_CALIFORNIUM_MIXTURE.get(), 8),
                IngredientCreatorAccess.gas().from(MekanismGases.ANTIMATTER.get(), 1000),
                new ItemStack(MSItems.INGOT_REFINED_CALIFORNIUM.get(), 1),
                1000
        ).build(recipeOutput, new ResourceLocation(MekanismElements.MODID, "nucleosynthesizing/ingot_refined_californium"));
        */
    }
}