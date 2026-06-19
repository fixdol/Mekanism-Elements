package com.fxd927.mekanismelements.datagen.machines;

import com.fxd927.mekanismelements.common.MekanismElements;
import com.fxd927.mekanismelements.common.registries.MSGases;
import com.fxd927.mekanismelements.common.registries.MSItems;
import mekanism.api.MekanismAPI;
import mekanism.api.datagen.recipe.builder.ItemStackChemicalToItemStackRecipeBuilder;
import mekanism.api.recipes.basic.BasicInjectingRecipe;
import mekanism.api.recipes.ingredients.ChemicalStackIngredient;
import mekanism.api.recipes.ingredients.ItemStackIngredient;
import mekanism.api.recipes.ingredients.creator.IngredientCreatorAccess;
import mekanism.common.registries.MekanismChemicals;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

public class InjectingRecipeProvider {

    public static void buildRecipes(RecipeOutput recipeOutput) {
        String basePath = "injecting/";

        // Syringe -> Anesthetic Syringe (Xenon)
        ItemStackChemicalToItemStackRecipeBuilder.injecting(
                IngredientCreatorAccess.item().from(Ingredient.of(MSItems.SYRINGE.get())),
                IngredientCreatorAccess.chemicalStack().from(MSGases.XENON.get(), 1),
                new ItemStack(MSItems.ANESTHETIC_SYRINGE.get()),
                false
        ).build(recipeOutput, ResourceLocation.fromNamespaceAndPath(MekanismElements.MODID, basePath + "syringe_anesthetic"));

        // Syringe -> Flame Retardant Syringe (Bromine)
        ItemStackChemicalToItemStackRecipeBuilder.injecting(
                IngredientCreatorAccess.item().from(Ingredient.of(MSItems.SYRINGE.get())),
                IngredientCreatorAccess.chemicalStack().from(MSGases.BROMINE.get(), 1),
                new ItemStack(MSItems.FLAME_RETARDANT_SYRINGE.get()),
                false
        ).build(recipeOutput, ResourceLocation.fromNamespaceAndPath(MekanismElements.MODID, basePath + "syringe_flame_retardant"));

        // Syringe -> Levitation Syringe (Helium)
        ItemStackChemicalToItemStackRecipeBuilder.injecting(
                IngredientCreatorAccess.item().from(Ingredient.of(MSItems.SYRINGE.get())),
                IngredientCreatorAccess.chemicalStack().from(MSGases.HELIUM.get(), 1),
                new ItemStack(MSItems.LEVITATION_SYRINGE.get()),
                false
        ).build(recipeOutput, ResourceLocation.fromNamespaceAndPath(MekanismElements.MODID, basePath + "syringe_levitation"));

        // TNT -> TNT x8 (Ammonium Nitrate)
        ItemStackChemicalToItemStackRecipeBuilder.injecting(
                IngredientCreatorAccess.item().from(Ingredient.of(Items.TNT)),
                IngredientCreatorAccess.chemicalStack().from(MSGases.AMMONIUM_NITRATE.get(), 5),
                new ItemStack(Items.TNT, 8),
                false
        ).build(recipeOutput, ResourceLocation.fromNamespaceAndPath(MekanismElements.MODID, basePath + "tnt"));

        // Firework Rocket -> Firework Rocket x8 (Ammonium Nitrate)
        ItemStackChemicalToItemStackRecipeBuilder.injecting(
                IngredientCreatorAccess.item().from(Ingredient.of(Items.FIREWORK_ROCKET)),
                IngredientCreatorAccess.chemicalStack().from(MSGases.AMMONIUM_NITRATE.get(), 5),
                new ItemStack(Items.FIREWORK_ROCKET, 8),
                false
        ).build(recipeOutput, ResourceLocation.fromNamespaceAndPath(MekanismElements.MODID, basePath + "firework_rocket"));

        // High Quality Concrete: Powder -> Clump (Water Vapor)
        buildHighQualityConcreteRecipes(recipeOutput);
    }

    /*
    * High Quality Concrete: Powder -> Clump (Water Vapor)
    * location: injecting/high_quality_concrete/[color].json
    */ 
    private static void buildHighQualityConcreteRecipes(RecipeOutput recipeOutput) {
        String basePath = "injecting/high_quality_concrete/";

        hqcByChemical(recipeOutput, basePath, "high_quality_concrete",
                MSItems.HIGH_QUALITY_CONCRETE_POWDER.get(), MSItems.HIGH_QUALITY_CONCRETE_CLUMP.get());

        hqcByChemical(recipeOutput, basePath, "high_quality_concrete_aqua",
                MSItems.HIGH_QUALITY_CONCRETE_POWDER_AQUA.get(), MSItems.HIGH_QUALITY_CONCRETE_CLUMP_AQUA.get());

        hqcByChemical(recipeOutput, basePath, "high_quality_concrete_black",
                MSItems.HIGH_QUALITY_CONCRETE_POWDER_BLACK.get(), MSItems.HIGH_QUALITY_CONCRETE_CLUMP_BLACK.get());

        hqcByChemical(recipeOutput, basePath, "high_quality_concrete_blue",
                MSItems.HIGH_QUALITY_CONCRETE_POWDER_BLUE.get(), MSItems.HIGH_QUALITY_CONCRETE_CLUMP_BLUE.get());

        hqcByChemical(recipeOutput, basePath, "high_quality_concrete_brown",
                MSItems.HIGH_QUALITY_CONCRETE_POWDER_BROWN.get(), MSItems.HIGH_QUALITY_CONCRETE_CLUMP_BROWN.get());

        hqcByChemical(recipeOutput, basePath, "high_quality_concrete_cyan",
                MSItems.HIGH_QUALITY_CONCRETE_POWDER_CYAN.get(), MSItems.HIGH_QUALITY_CONCRETE_CLUMP_CYAN.get());

        hqcByChemical(recipeOutput, basePath, "high_quality_concrete_dark_red",
                MSItems.HIGH_QUALITY_CONCRETE_POWDER_DARK_RED.get(), MSItems.HIGH_QUALITY_CONCRETE_CLUMP_DARK_RED.get());

        hqcByChemical(recipeOutput, basePath, "high_quality_concrete_gray",
                MSItems.HIGH_QUALITY_CONCRETE_POWDER_GRAY.get(), MSItems.HIGH_QUALITY_CONCRETE_CLUMP_GRAY.get());

        hqcByChemical(recipeOutput, basePath, "high_quality_concrete_green",
                MSItems.HIGH_QUALITY_CONCRETE_POWDER_GREEN.get(), MSItems.HIGH_QUALITY_CONCRETE_CLUMP_GREEN.get());

        hqcByChemical(recipeOutput, basePath, "high_quality_concrete_light_blue",
                MSItems.HIGH_QUALITY_CONCRETE_POWDER_LIGHT_BLUE.get(), MSItems.HIGH_QUALITY_CONCRETE_CLUMP_LIGHT_BLUE.get());

        hqcByChemical(recipeOutput, basePath, "high_quality_concrete_light_gray",
                MSItems.HIGH_QUALITY_CONCRETE_POWDER_LIGHT_GRAY.get(), MSItems.HIGH_QUALITY_CONCRETE_CLUMP_LIGHT_GRAY.get());

        hqcByChemical(recipeOutput, basePath, "high_quality_concrete_lime",
                MSItems.HIGH_QUALITY_CONCRETE_POWDER_LIME.get(), MSItems.HIGH_QUALITY_CONCRETE_CLUMP_LIME.get());

        hqcByChemical(recipeOutput, basePath, "high_quality_concrete_orange",
                MSItems.HIGH_QUALITY_CONCRETE_POWDER_ORANGE.get(), MSItems.HIGH_QUALITY_CONCRETE_CLUMP_ORANGE.get());

        hqcByChemical(recipeOutput, basePath, "high_quality_concrete_pink",
                MSItems.HIGH_QUALITY_CONCRETE_POWDER_PINK.get(), MSItems.HIGH_QUALITY_CONCRETE_CLUMP_PINK.get());

        hqcByChemical(recipeOutput, basePath, "high_quality_concrete_purple",
                MSItems.HIGH_QUALITY_CONCRETE_POWDER_PURPLE.get(), MSItems.HIGH_QUALITY_CONCRETE_CLUMP_PURPLE.get());

        hqcByChemical(recipeOutput, basePath, "high_quality_concrete_red",
                MSItems.HIGH_QUALITY_CONCRETE_POWDER_RED.get(), MSItems.HIGH_QUALITY_CONCRETE_CLUMP_RED.get());

        hqcByChemical(recipeOutput, basePath, "high_quality_concrete_white",
                MSItems.HIGH_QUALITY_CONCRETE_POWDER_WHITE.get(), MSItems.HIGH_QUALITY_CONCRETE_CLUMP_WHITE.get());

        hqcByChemical(recipeOutput, basePath, "high_quality_concrete_yellow",
                MSItems.HIGH_QUALITY_CONCRETE_POWDER_YELLOW.get(), MSItems.HIGH_QUALITY_CONCRETE_CLUMP_YELLOW.get());

        // Magenta
        ItemStackIngredient magentaItemInput = IngredientCreatorAccess.item().from(
                Ingredient.of(MSItems.HIGH_QUALITY_CONCRETE_POWDER_MAGENTA.get()));
        ChemicalStackIngredient magentaChemicalInput = IngredientCreatorAccess.chemicalStack().from(
                TagKey.create(MekanismAPI.CHEMICAL_REGISTRY_NAME, ResourceLocation.fromNamespaceAndPath("mekanism", "water_vapor")), 1);
        BasicInjectingRecipe magentaRecipe = new BasicInjectingRecipe(
                magentaItemInput,
                magentaChemicalInput,
                new ItemStack(MSItems.HIGH_QUALITY_CONCRETE_CLUMP_MAGENTA.get()),
                true // per_tick_usage
        );
        recipeOutput.accept(
                ResourceLocation.fromNamespaceAndPath(MekanismElements.MODID, basePath + "high_quality_concrete_magenta"),
                magentaRecipe,
                null
        );

        // Alt
        hqcByChemical(recipeOutput, basePath, "high_quality_concrete_magenta_alt",
                MSItems.HIGH_QUALITY_CONCRETE_POWDER_MAGENTA.get(), MSItems.HIGH_QUALITY_CONCRETE_CLUMP_MAGENTA.get());
    }

    /** Receta estándar powder -> clump, 1x mekanism:water_vapor (chemical directo), per_tick_usage false. */
    private static void hqcByChemical(RecipeOutput recipeOutput, String basePath, String name, Item input, Item output) {
        ItemStackChemicalToItemStackRecipeBuilder.injecting(
                IngredientCreatorAccess.item().from(Ingredient.of(input)),
                IngredientCreatorAccess.chemicalStack().from(MekanismChemicals.WATER_VAPOR.get(), 1),
                new ItemStack(output),
                false
        ).build(recipeOutput, ResourceLocation.fromNamespaceAndPath(MekanismElements.MODID, basePath + name));
    }
}