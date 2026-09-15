package fixdol.mekanismelements.common.datagen.machines;

import java.util.function.Consumer;
import net.minecraft.data.recipes.FinishedRecipe;
import mekanism.api.recipes.ingredients.creator.IngredientCreatorAccess;
import net.minecraft.world.item.ItemStack;
import mekanism.api.datagen.recipe.builder.ItemStackChemicalToItemStackRecipeBuilder;
import fixdol.mekanismelements.common.registries.MSItems;
import fixdol.mekanismelements.common.MekanismElements;
import mekanism.common.registries.MekanismGases;
import fixdol.mekanismelements.common.datagen.machines.PaintingRecipeProvider;
import net.minecraft.resources.ResourceLocation;


import mekanism.api.text.EnumColor;

public class PaintingRecipeProvider {

    // Must match PigmentExtractingRecipeProvider.DYE_RATE from Mekanism
    private static final long PIGMENT_RATE = 256;

    public static void buildRecipes(Consumer<FinishedRecipe> recipeOutput) {
        String basePath = "painting/";

        // powder_high_quality_concrete + dark_red pigment (256 mB) -> powder_high_quality_concrete_dark_red
        ItemStackChemicalToItemStackRecipeBuilder.painting(
                IngredientCreatorAccess.item().from(MSItems.HIGH_QUALITY_CONCRETE_POWDER.get()),
                IngredientCreatorAccess.pigment().from(
                        mekanism.common.registries.MekanismPigments.PIGMENT_COLOR_LOOKUP.get(EnumColor.DARK_RED), PIGMENT_RATE),
                new ItemStack(MSItems.HIGH_QUALITY_CONCRETE_POWDER_DARK_RED.get())
        ).build(recipeOutput, new ResourceLocation(MekanismElements.MODID, basePath + "powder_high_quality_concrete_dark_red"));

        // powder_high_quality_concrete + aqua pigment (256 mB) -> powder_high_quality_concrete_aqua
        ItemStackChemicalToItemStackRecipeBuilder.painting(
                IngredientCreatorAccess.item().from(MSItems.HIGH_QUALITY_CONCRETE_POWDER.get()),
                IngredientCreatorAccess.pigment().from(
                        mekanism.common.registries.MekanismPigments.PIGMENT_COLOR_LOOKUP.get(EnumColor.AQUA), PIGMENT_RATE),
                new ItemStack(MSItems.HIGH_QUALITY_CONCRETE_POWDER_AQUA.get())
        ).build(recipeOutput, new ResourceLocation(MekanismElements.MODID, basePath + "powder_high_quality_concrete_aqua"));
    }
}