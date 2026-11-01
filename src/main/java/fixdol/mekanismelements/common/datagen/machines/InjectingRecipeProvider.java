package fixdol.mekanismelements.datagen.machines;

import fixdol.mekanismelements.common.MekanismElements;
import fixdol.mekanismelements.common.registries.MSGases;
import fixdol.mekanismelements.common.registries.MSItems;
import mekanism.api.MekanismAPI;
import mekanism.api.chemical.Chemical;
import mekanism.api.datagen.recipe.builder.ItemStackChemicalToItemStackRecipeBuilder;
import mekanism.api.recipes.ingredients.creator.IngredientCreatorAccess;
import mekanism.common.registries.MekanismChemicals;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.Items;

public class InjectingRecipeProvider {

    public static void buildRecipes(RecipeOutput recipeOutput, HolderLookup.Provider registries) {
        String basePath = "injecting/";

        // Syringe -> Anesthetic Syringe (Xenon)
        ItemStackChemicalToItemStackRecipeBuilder.injecting(
                IngredientCreatorAccess.item().from(MSItems.SYRINGE.get()),
                IngredientCreatorAccess.chemicalStack().fromHolder(MSGases.XENON, 1),
                new ItemStackTemplate(MSItems.ANESTHETIC_SYRINGE.get()),
                false
        ).save(recipeOutput, MekanismElements.rl(basePath + "syringe_anesthetic"));

        // Syringe -> Flame Retardant Syringe (Bromine)
        ItemStackChemicalToItemStackRecipeBuilder.injecting(
                IngredientCreatorAccess.item().from(MSItems.SYRINGE.get()),
                IngredientCreatorAccess.chemicalStack().fromHolder(MSGases.BROMINE, 1),
                new ItemStackTemplate(MSItems.FLAME_RETARDANT_SYRINGE.get()),
                false
        ).save(recipeOutput, MekanismElements.rl(basePath + "syringe_flame_retardant"));

        // Syringe -> Levitation Syringe (Helium)
        ItemStackChemicalToItemStackRecipeBuilder.injecting(
                IngredientCreatorAccess.item().from(MSItems.SYRINGE.get()),
                IngredientCreatorAccess.chemicalStack().fromHolder(MSGases.HELIUM, 1),
                new ItemStackTemplate(MSItems.LEVITATION_SYRINGE.get()),
                false
        ).save(recipeOutput, MekanismElements.rl(basePath + "syringe_levitation"));

        // TNT -> TNT x8 (Ammonium Nitrate)
        ItemStackChemicalToItemStackRecipeBuilder.injecting(
                IngredientCreatorAccess.item().from(Items.TNT),
                IngredientCreatorAccess.chemicalStack().fromHolder(MSGases.AMMONIUM_NITRATE, 5),
                new ItemStackTemplate(Items.TNT, 8),
                false
        ).save(recipeOutput, MekanismElements.rl(basePath + "tnt"));

        // Firework Rocket -> Firework Rocket x8 (Ammonium Nitrate)
        ItemStackChemicalToItemStackRecipeBuilder.injecting(
                IngredientCreatorAccess.item().from(Items.FIREWORK_ROCKET),
                IngredientCreatorAccess.chemicalStack().fromHolder(MSGases.AMMONIUM_NITRATE, 5),
                new ItemStackTemplate(Items.FIREWORK_ROCKET, 8),
                false
        ).save(recipeOutput, MekanismElements.rl(basePath + "firework_rocket"));

        // High Quality Concrete: Powder -> Clump (Water Vapor)
        buildHighQualityConcreteRecipes(recipeOutput, registries.lookupOrThrow(MekanismAPI.CHEMICAL_REGISTRY_NAME));
    }

    /*
    * High Quality Concrete: Powder -> Clump (Water Vapor)
    * location: injecting/high_quality_concrete/[color].json
    */ 
    private static void buildHighQualityConcreteRecipes(RecipeOutput recipeOutput, HolderGetter<Chemical> chemicals) {
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
        ItemStackChemicalToItemStackRecipeBuilder.injecting(
                IngredientCreatorAccess.item().from(MSItems.HIGH_QUALITY_CONCRETE_POWDER_MAGENTA),
                IngredientCreatorAccess.chemicalStack().from(chemicals, TagKey.create(MekanismAPI.CHEMICAL_REGISTRY_NAME, Identifier.fromNamespaceAndPath("mekanism", "water_vapor")), 1),
                new ItemStackTemplate(MSItems.HIGH_QUALITY_CONCRETE_CLUMP_MAGENTA.get()),
                true // per_tick_usage
        ).save(recipeOutput, MekanismElements.rl(basePath + "high_quality_concrete_magenta"));

        // Alt
        hqcByChemical(recipeOutput, basePath, "high_quality_concrete_magenta_alt",
                MSItems.HIGH_QUALITY_CONCRETE_POWDER_MAGENTA.get(), MSItems.HIGH_QUALITY_CONCRETE_CLUMP_MAGENTA.get());
    }

    /** Receta estándar powder -> clump, 1x mekanism:water_vapor (chemical directo), per_tick_usage false. */
    private static void hqcByChemical(RecipeOutput recipeOutput, String basePath, String name, Item input, Item output) {
        ItemStackChemicalToItemStackRecipeBuilder.injecting(
                IngredientCreatorAccess.item().from(input),
                IngredientCreatorAccess.chemicalStack().fromHolder(MekanismChemicals.WATER_VAPOR, 1),
                new ItemStackTemplate(output),
                false
        ).save(recipeOutput, MekanismElements.rl(basePath + name));
    }
}