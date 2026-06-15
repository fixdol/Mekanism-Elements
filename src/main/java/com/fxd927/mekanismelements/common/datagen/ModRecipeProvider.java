package com.fxd927.mekanismelements.common.datagen;

import com.fxd927.mekanismelements.common.registries.MSBlocks;
import com.fxd927.mekanismelements.common.registries.MSItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.conditions.ICondition;
import mekanism.common.registries.MekanismItems;
import mekanism.common.registries.MekanismBlocks;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider {

    public ModRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider);
    }

    @Override
    protected void buildRecipes(RecipeOutput writer) {

        // Wrapper para evitar advancements automáticos
        RecipeOutput recipeOutput = new RecipeOutput() {
            @Override
            public void accept(ResourceLocation id, net.minecraft.world.item.crafting.Recipe<?> recipe,
                               net.minecraft.advancements.AdvancementHolder advancement) {
                writer.accept(id, recipe, null);
            }
            @Override
            public net.minecraft.advancements.Advancement.Builder advancement() {
                return writer.advancement();
            }
            @Override
            public void accept(ResourceLocation id, net.minecraft.world.item.crafting.Recipe<?> recipe,
                               net.minecraft.advancements.AdvancementHolder advancement, ICondition... conditions) {
                writer.accept(id, recipe, null, conditions);
            }
        };

        // Syringe
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, MSItems.SYRINGE.get())
                .pattern(" I ")
                .pattern(" G ")
                .pattern(" G ")
                .define('I', Items.IRON_INGOT)
                .define('G', Items.GLASS_PANE)
                .unlockedBy("has_iron", has(Items.IRON_INGOT))
                .save(recipeOutput);

        // High Quality Concrete Clump
        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, MSItems.HIGH_QUALITY_CONCRETE_POWDER.get(), 3)
                .requires(net.minecraft.tags.ItemTags.create(ResourceLocation.fromNamespaceAndPath("c", "dusts/lead")))
                .requires(Items.SAND)
                .requires(net.minecraft.tags.ItemTags.create(ResourceLocation.fromNamespaceAndPath("c", "dusts/calcium_oxide")))
                .unlockedBy("has_calcium_oxide", has(net.minecraft.tags.ItemTags.create(ResourceLocation.fromNamespaceAndPath("c", "dusts/calcium_oxide"))))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, MSItems.HIGH_PERFORMANCE_ADSORBENT.get().asItem())
                .pattern("AAA")
                .pattern("_X_")
                .pattern("AAA")
                .define('A', MekanismItems.HDPE_SHEET.get())
                .define('X', MekanismItems.ANTIMATTER_PELLET.get())
                .define('_', net.minecraft.tags.ItemTags.create(ResourceLocation.fromNamespaceAndPath("c", "ingots/lead")))
                .unlockedBy("has_pellet_antimatter", has(MekanismItems.ANTIMATTER_PELLET.get()))
                .save(recipeOutput);

        // Colored High Quality Concrete Clumps
        addColorRecipe(recipeOutput, MSItems.HIGH_QUALITY_CONCRETE_POWDER_BLACK.get(), Items.BLACK_DYE);
        addColorRecipe(recipeOutput, MSItems.HIGH_QUALITY_CONCRETE_POWDER_BLUE.get(), Items.BLUE_DYE);
        addColorRecipe(recipeOutput, MSItems.HIGH_QUALITY_CONCRETE_POWDER_BROWN.get(), Items.BROWN_DYE);
        addColorRecipe(recipeOutput, MSItems.HIGH_QUALITY_CONCRETE_POWDER_CYAN.get(), Items.CYAN_DYE);
        addColorRecipe(recipeOutput, MSItems.HIGH_QUALITY_CONCRETE_POWDER_DARK_RED.get(), Items.RED_DYE);
        addColorRecipe(recipeOutput, MSItems.HIGH_QUALITY_CONCRETE_POWDER_GRAY.get(), Items.GRAY_DYE);
        addColorRecipe(recipeOutput, MSItems.HIGH_QUALITY_CONCRETE_POWDER_GREEN.get(), Items.GREEN_DYE);
        addColorRecipe(recipeOutput, MSItems.HIGH_QUALITY_CONCRETE_POWDER_LIGHT_BLUE.get(), Items.LIGHT_BLUE_DYE);
        addColorRecipe(recipeOutput, MSItems.HIGH_QUALITY_CONCRETE_POWDER_LIGHT_GRAY.get(), Items.LIGHT_GRAY_DYE);
        addColorRecipe(recipeOutput, MSItems.HIGH_QUALITY_CONCRETE_POWDER_LIME.get(), Items.LIME_DYE);
        addColorRecipe(recipeOutput, MSItems.HIGH_QUALITY_CONCRETE_POWDER_MAGENTA.get(), Items.MAGENTA_DYE);
        addColorRecipe(recipeOutput, MSItems.HIGH_QUALITY_CONCRETE_POWDER_ORANGE.get(), Items.ORANGE_DYE);
        addColorRecipe(recipeOutput, MSItems.HIGH_QUALITY_CONCRETE_POWDER_PINK.get(), Items.PINK_DYE);
        addColorRecipe(recipeOutput, MSItems.HIGH_QUALITY_CONCRETE_POWDER_PURPLE.get(), Items.PURPLE_DYE);
        addColorRecipe(recipeOutput, MSItems.HIGH_QUALITY_CONCRETE_POWDER_RED.get(), Items.RED_DYE);
        addColorRecipe(recipeOutput, MSItems.HIGH_QUALITY_CONCRETE_POWDER_WHITE.get(), Items.WHITE_DYE);
        addColorRecipe(recipeOutput, MSItems.HIGH_QUALITY_CONCRETE_POWDER_YELLOW.get(), Items.YELLOW_DYE);

        // Structural Glass
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, MekanismBlocks.STRUCTURAL_GLASS.asItem(), 8)
                .pattern("SSS")
                .pattern("SGS")
                .pattern("SSS")
                .define('S', net.minecraft.tags.ItemTags.create(ResourceLocation.fromNamespaceAndPath("c", "glass_blocks/cheap")))
                .define('G', MSItems.DUST_CALCIUM_OXIDE.get())
                .unlockedBy("has_calcium_oxide", has(MSItems.DUST_CALCIUM_OXIDE.get()))
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath("mekanismelements", "structural_glass"));

        // Adsorption Separator
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, MSBlocks.ADSORPTION_SEPARATOR.get().asItem())
                .pattern("ACA")
                .pattern("SXS")
                .pattern("ACA")
                .define('C', MekanismItems.ELITE_CONTROL_CIRCUIT.get())
                .define('S', Items.IRON_BARS)
                .define('A', MekanismItems.REINFORCED_ALLOY.get())
                .define('X', MekanismBlocks.STEEL_CASING.asItem())
                .unlockedBy("has_steel_casing", has(MekanismBlocks.STEEL_CASING.asItem()))
                .save(recipeOutput);

        // Air Compressor
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, MSBlocks.AIR_COMPRESSOR.get().asItem())
                .pattern("ACA")
                .pattern("SXS")
                .pattern("ATA")
                .define('T', MekanismBlocks.BASIC_CHEMICAL_TANK.asItem())
                .define('C', MekanismItems.ADVANCED_CONTROL_CIRCUIT.get())
                .define('S', MekanismItems.HDPE_SHEET.get())
                .define('A', MekanismItems.INFUSED_ALLOY.get())
                .define('X', MekanismBlocks.STEEL_CASING.asItem())
                .unlockedBy("has_steel_casing", has(MekanismBlocks.STEEL_CASING.asItem()))
                .save(recipeOutput);

        // Radiation Irradiator
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, MSBlocks.RADIATION_IRRADIATOR.get().asItem())
                .pattern("CSC")
                .pattern("_X_")
                .pattern("CAC")
                .define('A', MekanismItems.POLONIUM_PELLET.get())
                .define('S', MekanismBlocks.LASER.get())
                .define('C', MSItems.HIGH_QUALITY_CONCRETE_CLUMP.get())
                .define('X', MekanismBlocks.STEEL_CASING.asItem())
                .define('_', MekanismItems.ULTIMATE_CONTROL_CIRCUIT)
                .unlockedBy("has_steel_casing", has(MekanismBlocks.STEEL_CASING.asItem()))
                .save(recipeOutput);

        // Seawater Pump
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, MSBlocks.SEAWATER_PUMP.get().asItem())
                .pattern("AAA")
                .pattern("_E_")
                .pattern("AAA")
                .define('A', net.minecraft.tags.ItemTags.create(ResourceLocation.fromNamespaceAndPath("c", "ingots/osmium")))
                .define('_', MekanismItems.ELITE_CONTROL_CIRCUIT.get())
                .define('E', MekanismBlocks.ELECTRIC_PUMP.asItem())
                .unlockedBy("has_electric_pump", has(MekanismBlocks.ELECTRIC_PUMP.asItem()))
                .save(recipeOutput);

        // ==========================================
        // CRAFTING - Slabs y Stairs
        // ==========================================
        addBlock(recipeOutput, MSItems.HIGH_QUALITY_CONCRETE_CLUMP.get(), MSBlocks.HIGH_QUALITY_CONCRETE.get(), MSBlocks.HIGH_QUALITY_CONCRETE.get(), MSBlocks.HIGH_QUALITY_CONCRETE_SLABS.get(), MSBlocks.HIGH_QUALITY_CONCRETE_STAIRS.get());
        addBlock(recipeOutput, MSItems.HIGH_QUALITY_CONCRETE_CLUMP_AQUA.get(), MSBlocks.AQUA_HIGH_QUALITY_CONCRETE.get(), MSBlocks.AQUA_HIGH_QUALITY_CONCRETE.get(),  MSBlocks.AQUA_HIGH_QUALITY_CONCRETE_SLABS.get(), MSBlocks.AQUA_HIGH_QUALITY_CONCRETE_STAIRS.get());
        addBlock(recipeOutput, MSItems.HIGH_QUALITY_CONCRETE_CLUMP_BLACK.get(), MSBlocks.BLACK_HIGH_QUALITY_CONCRETE.get(), MSBlocks.BLACK_HIGH_QUALITY_CONCRETE.get(), MSBlocks.BLACK_HIGH_QUALITY_CONCRETE_SLABS.get(), MSBlocks.BLACK_HIGH_QUALITY_CONCRETE_STAIRS.get());
        addBlock(recipeOutput, MSItems.HIGH_QUALITY_CONCRETE_CLUMP_BLUE.get(), MSBlocks.BLUE_HIGH_QUALITY_CONCRETE.get(), MSBlocks.BLUE_HIGH_QUALITY_CONCRETE.get(), MSBlocks.BLUE_HIGH_QUALITY_CONCRETE_SLABS.get(), MSBlocks.BLUE_HIGH_QUALITY_CONCRETE_STAIRS.get());
        addBlock(recipeOutput, MSItems.HIGH_QUALITY_CONCRETE_CLUMP_GREEN.get(), MSBlocks.GREEN_HIGH_QUALITY_CONCRETE.get(), MSBlocks.GREEN_HIGH_QUALITY_CONCRETE.get(), MSBlocks.GREEN_HIGH_QUALITY_CONCRETE_SLABS.get(), MSBlocks.GREEN_HIGH_QUALITY_CONCRETE_STAIRS.get());
        addBlock(recipeOutput, MSItems.HIGH_QUALITY_CONCRETE_CLUMP_CYAN.get(), MSBlocks.CYAN_HIGH_QUALITY_CONCRETE.get(),  MSBlocks.CYAN_HIGH_QUALITY_CONCRETE.get(), MSBlocks.CYAN_HIGH_QUALITY_CONCRETE_SLABS.get(), MSBlocks.CYAN_HIGH_QUALITY_CONCRETE_STAIRS.get());
        addBlock(recipeOutput, MSItems.HIGH_QUALITY_CONCRETE_CLUMP_DARK_RED.get(), MSBlocks.DARK_RED_HIGH_QUALITY_CONCRETE.get(), MSBlocks.DARK_RED_HIGH_QUALITY_CONCRETE.get(), MSBlocks.DARK_RED_HIGH_QUALITY_CONCRETE_SLABS.get(), MSBlocks.DARK_RED_HIGH_QUALITY_CONCRETE_STAIRS.get());
        addBlock(recipeOutput, MSItems.HIGH_QUALITY_CONCRETE_CLUMP_PURPLE.get(), MSBlocks.PURPLE_HIGH_QUALITY_CONCRETE.get(), MSBlocks.PURPLE_HIGH_QUALITY_CONCRETE.get(), MSBlocks.PURPLE_HIGH_QUALITY_CONCRETE_SLABS.get(), MSBlocks.PURPLE_HIGH_QUALITY_CONCRETE_STAIRS.get());
        addBlock(recipeOutput, MSItems.HIGH_QUALITY_CONCRETE_CLUMP_ORANGE.get(), MSBlocks.ORANGE_HIGH_QUALITY_CONCRETE.get(), MSBlocks.ORANGE_HIGH_QUALITY_CONCRETE.get(), MSBlocks.ORANGE_HIGH_QUALITY_CONCRETE_SLABS.get(), MSBlocks.ORANGE_HIGH_QUALITY_CONCRETE_STAIRS.get());
        addBlock(recipeOutput, MSItems.HIGH_QUALITY_CONCRETE_CLUMP_LIGHT_GRAY.get(), MSBlocks.LIGHT_GRAY_HIGH_QUALITY_CONCRETE.get(), MSBlocks.LIGHT_GRAY_HIGH_QUALITY_CONCRETE.get(), MSBlocks.LIGHT_GRAY_HIGH_QUALITY_CONCRETE_SLABS.get(), MSBlocks.LIGHT_GRAY_HIGH_QUALITY_CONCRETE_STAIRS.get());
        addBlock(recipeOutput, MSItems.HIGH_QUALITY_CONCRETE_CLUMP_GRAY.get(), MSBlocks.GRAY_HIGH_QUALITY_CONCRETE.get(), MSBlocks.GRAY_HIGH_QUALITY_CONCRETE.get(), MSBlocks.GRAY_HIGH_QUALITY_CONCRETE_SLABS.get(),  MSBlocks.GRAY_HIGH_QUALITY_CONCRETE_STAIRS.get());
        addBlock(recipeOutput, MSItems.HIGH_QUALITY_CONCRETE_CLUMP_LIGHT_BLUE.get(), MSBlocks.LIGHT_BLUE_HIGH_QUALITY_CONCRETE.get(), MSBlocks.LIGHT_BLUE_HIGH_QUALITY_CONCRETE.get(), MSBlocks.LIGHT_BLUE_HIGH_QUALITY_CONCRETE_SLABS.get(), MSBlocks.LIGHT_BLUE_HIGH_QUALITY_CONCRETE_STAIRS.get());
        addBlock(recipeOutput, MSItems.HIGH_QUALITY_CONCRETE_CLUMP_LIME.get(), MSBlocks.LIME_HIGH_QUALITY_CONCRETE.get(), MSBlocks.LIME_HIGH_QUALITY_CONCRETE.get(),  MSBlocks.LIME_HIGH_QUALITY_CONCRETE_SLABS.get(), MSBlocks.LIME_HIGH_QUALITY_CONCRETE_STAIRS.get());
        addBlock(recipeOutput, MSItems.HIGH_QUALITY_CONCRETE_CLUMP_RED.get(), MSBlocks.RED_HIGH_QUALITY_CONCRETE.get(), MSBlocks.RED_HIGH_QUALITY_CONCRETE.get(), MSBlocks.RED_HIGH_QUALITY_CONCRETE_SLABS.get(), MSBlocks.RED_HIGH_QUALITY_CONCRETE_STAIRS.get());
        addBlock(recipeOutput, MSItems.HIGH_QUALITY_CONCRETE_CLUMP_MAGENTA.get(), MSBlocks.MAGENTA_HIGH_QUALITY_CONCRETE.get(), MSBlocks.MAGENTA_HIGH_QUALITY_CONCRETE.get(), MSBlocks.MAGENTA_HIGH_QUALITY_CONCRETE_SLABS.get(), MSBlocks.MAGENTA_HIGH_QUALITY_CONCRETE_STAIRS.get());
        addBlock(recipeOutput, MSItems.HIGH_QUALITY_CONCRETE_CLUMP_YELLOW.get(), MSBlocks.YELLOW_HIGH_QUALITY_CONCRETE.get(), MSBlocks.YELLOW_HIGH_QUALITY_CONCRETE.get(), MSBlocks.YELLOW_HIGH_QUALITY_CONCRETE_SLABS.get(), MSBlocks.YELLOW_HIGH_QUALITY_CONCRETE_STAIRS.get());
        addBlock(recipeOutput, MSItems.HIGH_QUALITY_CONCRETE_CLUMP_WHITE.get(), MSBlocks.WHITE_HIGH_QUALITY_CONCRETE.get(), MSBlocks.WHITE_HIGH_QUALITY_CONCRETE.get(), MSBlocks.WHITE_HIGH_QUALITY_CONCRETE_SLABS.get(), MSBlocks.WHITE_HIGH_QUALITY_CONCRETE_STAIRS.get());
        addBlock(recipeOutput, MSItems.HIGH_QUALITY_CONCRETE_CLUMP_BROWN.get(), MSBlocks.BROWN_HIGH_QUALITY_CONCRETE.get(), MSBlocks.BROWN_HIGH_QUALITY_CONCRETE.get(), MSBlocks.BROWN_HIGH_QUALITY_CONCRETE_SLABS.get(), MSBlocks.BROWN_HIGH_QUALITY_CONCRETE_STAIRS.get());
        addBlock(recipeOutput, MSItems.HIGH_QUALITY_CONCRETE_CLUMP_PINK.get(), MSBlocks.PINK_HIGH_QUALITY_CONCRETE.get(), MSBlocks.PINK_HIGH_QUALITY_CONCRETE.get(), MSBlocks.PINK_HIGH_QUALITY_CONCRETE_SLABS.get(), MSBlocks.PINK_HIGH_QUALITY_CONCRETE_STAIRS.get());

        addOreRecipe(recipeOutput, MSItems.HIGH_PERFORMANCE_ADSORBENT_BERYLLIUM.get(), Ingredient.of(TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", "ingots/beryllium"))));
        addOreRecipe(recipeOutput, MSItems.HIGH_PERFORMANCE_ADSORBENT_COPPER.get(), Ingredient.of(TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", "ingots/copper"))));
        addOreRecipe(recipeOutput, MSItems.HIGH_PERFORMANCE_ADSORBENT_GOLD.get(), Ingredient.of(TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", "ingots/gold"))));
        addOreRecipe(recipeOutput, MSItems.HIGH_PERFORMANCE_ADSORBENT_IRON.get(), Ingredient.of(TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", "ingots/iron"))));
        addOreRecipe(recipeOutput, MSItems.HIGH_PERFORMANCE_ADSORBENT_LEAD.get(), Ingredient.of(TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", "ingots/lead"))));
        addOreRecipe(recipeOutput, MSItems.HIGH_PERFORMANCE_ADSORBENT_OSMIUM.get(), Ingredient.of(TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", "ingots/osmium"))));
        addOreRecipe(recipeOutput, MSItems.HIGH_PERFORMANCE_ADSORBENT_TIN.get(), Ingredient.of(TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", "ingots/tin"))));
        addOreRecipe(recipeOutput, MSItems.HIGH_PERFORMANCE_ADSORBENT_URANIUM.get(), Ingredient.of(TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", "ingots/uranium"))));
    }

    private void addColorRecipe(RecipeOutput output, Item item, Item dye) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, item, 8)
                .requires(MSItems.HIGH_QUALITY_CONCRETE_POWDER.get(), 8)
                .requires(dye)
                .unlockedBy("has_clump", has(MSItems.HIGH_QUALITY_CONCRETE_POWDER.get()))
                .save(output);
    }

    private void addOreRecipe(RecipeOutput output, Item item, Ingredient ore){
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, item, 1)
                .pattern("ACA")
                .pattern("CXC")
                .pattern("ACA")
                .define('A', MekanismItems.ANTIMATTER_PELLET.get())
                .define('X',MSItems.HIGH_PERFORMANCE_ADSORBENT)
                .define('C',ore)
                .unlockedBy("has_high_performance_adsorbent", has(MSItems.HIGH_PERFORMANCE_ADSORBENT.get()))
                .save(output);
    }

    private void addBlock(RecipeOutput output, Item input1, Block input2, Block block, Block slab, Block stairs) {
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, block, 4)
                .pattern("##")
                .pattern("##")
                .define('#', input1)
                .unlockedBy("has_high_quality_concrete_clump", has(input1))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, slab, 6)
                .pattern("###")
                .define('#', input2)
                .unlockedBy("has_block", has(input2))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, stairs, 4)
                .pattern("#  ")
                .pattern("## ")
                .pattern("###")
                .define('#', input2)
                .unlockedBy("has_block", has(input2))
                .save(output);
    }
}