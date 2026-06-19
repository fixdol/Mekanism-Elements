package com.fxd927.mekanismelements.datagen.machines;

import com.fxd927.mekanismelements.common.MekanismElements;
import com.fxd927.mekanismelements.common.registries.MSItems;
import mekanism.api.datagen.recipe.builder.ItemStackChemicalToItemStackRecipeBuilder;
import mekanism.api.recipes.ingredients.creator.IngredientCreatorAccess;
import mekanism.api.text.EnumColor;
import mekanism.common.registries.MekanismChemicals;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class PaintingRecipeProvider {

    // Must match PigmentExtractingRecipeProvider.DYE_RATE from Mekanism
    private static final long PIGMENT_RATE = 256;

    public static void buildRecipes(RecipeOutput recipeOutput) {
        String basePath = "painting/";

        // powder_high_quality_concrete + dark_red pigment (256 mB) -> powder_high_quality_concrete_dark_red
        ItemStackChemicalToItemStackRecipeBuilder.painting(
                IngredientCreatorAccess.item().from(MSItems.HIGH_QUALITY_CONCRETE_POWDER.get()),
                IngredientCreatorAccess.chemicalStack().fromHolder(
                        MekanismChemicals.PIGMENT_COLOR_LOOKUP.get(EnumColor.DARK_RED), PIGMENT_RATE),
                new ItemStack(MSItems.HIGH_QUALITY_CONCRETE_POWDER_DARK_RED.get()),
                false
        ).build(recipeOutput, ResourceLocation.fromNamespaceAndPath(MekanismElements.MODID, basePath + "powder_high_quality_concrete_dark_red"));

        // powder_high_quality_concrete + aqua pigment (256 mB) -> powder_high_quality_concrete_aqua
        ItemStackChemicalToItemStackRecipeBuilder.painting(
                IngredientCreatorAccess.item().from(MSItems.HIGH_QUALITY_CONCRETE_POWDER.get()),
                IngredientCreatorAccess.chemicalStack().fromHolder(
                        MekanismChemicals.PIGMENT_COLOR_LOOKUP.get(EnumColor.AQUA), PIGMENT_RATE),
                new ItemStack(MSItems.HIGH_QUALITY_CONCRETE_POWDER_AQUA.get()),
                false
        ).build(recipeOutput, ResourceLocation.fromNamespaceAndPath(MekanismElements.MODID, basePath + "powder_high_quality_concrete_aqua"));
    }
}