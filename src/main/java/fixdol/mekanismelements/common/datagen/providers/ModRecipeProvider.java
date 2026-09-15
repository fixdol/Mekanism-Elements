package fixdol.mekanismelements.common.datagen.providers;

import net.minecraft.world.level.block.Block;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.core.HolderLookup;
import fixdol.mekanismelements.common.registries.MSBlocks;
import fixdol.mekanismelements.common.MekanismElements;
import fixdol.mekanismelements.common.datagen.providers.ModRecipeProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;


import fixdol.mekanismelements.common.registries.MSItems;
import fixdol.mekanismelements.common.datagen.machines.AdsorptionRecipeProvider;
import fixdol.mekanismelements.common.datagen.machines.ChemicalDemolitionRecipeProvider;
import fixdol.mekanismelements.common.datagen.machines.RadiationIrradiatingRecipeProvider;
import fixdol.mekanismelements.common.datagen.machines.ChemicalInfusingRecipeProvider;
import fixdol.mekanismelements.common.datagen.machines.CrushingRecipeProvider;
import fixdol.mekanismelements.common.datagen.machines.ReactionRecipeProvider;
import fixdol.mekanismelements.common.datagen.machines.NucleosynthesizingRecipeProvider;
import fixdol.mekanismelements.common.datagen.machines.RotaryRecipeProvider;
import fixdol.mekanismelements.common.datagen.machines.SeparatingRecipeProvider;
import fixdol.mekanismelements.common.datagen.machines.CentrifugingRecipeProvider;
import fixdol.mekanismelements.common.datagen.machines.OxidizingRecipeProvider;
import fixdol.mekanismelements.common.datagen.machines.EvaporatingRecipeProvider;
import fixdol.mekanismelements.common.datagen.machines.DissolutionRecipeProvider;
import fixdol.mekanismelements.common.datagen.machines.PaintingRecipeProvider;
import fixdol.mekanismelements.common.datagen.machines.CrystallizingRecipeProvider;
import fixdol.mekanismelements.common.datagen.machines.ActivatingRecipeProvider;
import fixdol.mekanismelements.common.datagen.machines.InjectingRecipeProvider;
import fixdol.mekanismelements.common.datagen.machines.InfinityOreReprocessingRecipeProvider;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.*;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.crafting.conditions.ICondition;
import mekanism.common.registries.MekanismItems;
import mekanism.common.registries.MekanismBlocks;


public class ModRecipeProvider extends RecipeProvider {

    public ModRecipeProvider(PackOutput output) {
        super(output);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> writer) {

        Consumer<FinishedRecipe> recipeOutput = writer;

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
                .requires(net.minecraft.tags.ItemTags.create(new ResourceLocation("forge", "dusts/lead")))
                .requires(Items.SAND)
                .requires(net.minecraft.tags.ItemTags.create(new ResourceLocation("forge", "dusts/calcium_oxide")))
                .unlockedBy("has_calcium_oxide", has(net.minecraft.tags.ItemTags.create(new ResourceLocation("forge", "dusts/calcium_oxide"))))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, MSItems.HIGH_PERFORMANCE_ADSORBENT.get().asItem())
                .pattern("AAA")
                .pattern("_X_")
                .pattern("AAA")
                .define('A', MekanismItems.HDPE_SHEET.get())
                .define('X', MekanismItems.ANTIMATTER_PELLET.get())
                .define('_', net.minecraft.tags.ItemTags.create(new ResourceLocation("forge", "ingots/lead")))
                .unlockedBy("has_pellet_antimatter", has(MekanismItems.ANTIMATTER_PELLET.get()))
                .save(recipeOutput);

        // Colored High Quality Concrete Clumps
        addColorRecipe(recipeOutput, MSItems.HIGH_QUALITY_CONCRETE_POWDER_BLACK.get(), Items.BLACK_DYE);
        addColorRecipe(recipeOutput, MSItems.HIGH_QUALITY_CONCRETE_POWDER_AQUA.get(), Items.CYAN_DYE);
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
                .define('S', net.minecraft.tags.ItemTags.create(new ResourceLocation("forge", "glass")))
                .define('G', net.minecraft.tags.ItemTags.create(new ResourceLocation("forge", "glass_panes")))
                .unlockedBy("has_glass", has(Items.GLASS))
                .save(recipeOutput);

        // Radiation Irradiator
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, MSBlocks.RADIATION_IRRADIATOR.getBlock().asItem())
                .pattern("CSC")
                .pattern("_X_")
                .pattern("CAC")
                .define('A', MekanismItems.POLONIUM_PELLET.get())
                .define('S', MekanismBlocks.LASER.getBlock())
                .define('C', MSItems.HIGH_QUALITY_CONCRETE_CLUMP.get())
                .define('X', MekanismBlocks.STEEL_CASING.asItem())
                .define('_', MekanismItems.ULTIMATE_CONTROL_CIRCUIT)
                .unlockedBy("has_steel_casing", has(MekanismBlocks.STEEL_CASING.asItem()))
                .save(recipeOutput);

        // Seawater Pump
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, MSBlocks.SEAWATER_PUMP.getBlock().asItem())
                .pattern("AAA")
                .pattern("_E_")
                .pattern("AAA")
                .define('A', net.minecraft.tags.ItemTags.create(new ResourceLocation("forge", "ingots/osmium")))
                .define('_', MekanismItems.ELITE_CONTROL_CIRCUIT.get())
                .define('E', MekanismBlocks.ELECTRIC_PUMP.asItem())
                .unlockedBy("has_electric_pump", has(MekanismBlocks.ELECTRIC_PUMP.asItem()))
                .save(recipeOutput);

        // Adsorption Separator
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, MSBlocks.ADSORPTION_SEPARATOR.getBlock().asItem())
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
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, MSBlocks.AIR_COMPRESSOR.getBlock().asItem())
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

        // chemical demolition 
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, MSBlocks.CHEMICAL_DEMOLITION_MACHINE.getBlock().asItem())
                .pattern("ACA")
                .pattern("SXS")
                .pattern("ATA")
                .define('T', MekanismBlocks.ULTIMATE_CHEMICAL_TANK.asItem())
                .define('C', Items.NETHERITE_BLOCK)
                .define('S', Items.NETHER_STAR)
                .define('A', MekanismItems.POLONIUM_PELLET.get())
                .define('X', MSBlocks.ADSORPTION_SEPARATOR.getBlock())
                .unlockedBy("has_steel_casing", has(MekanismBlocks.STEEL_CASING.asItem()))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, MSBlocks.INFINITY_ORE_REPROCESSING.getBlock().asItem())
                .pattern("ACA")
                .pattern("PXP")
                .pattern("ATA")
                .define('A', MekanismItems.ATOMIC_ALLOY.get())
                .define('C', MekanismItems.ULTIMATE_CONTROL_CIRCUIT.get())
                .define('P', MSBlocks.SEAWATER_PUMP.getBlock())
                .define('X', MSBlocks.ADSORPTION_SEPARATOR.getBlock())
                .define('T', MekanismBlocks.ULTIMATE_CHEMICAL_TANK.asItem())
                .unlockedBy("has_seawater_pump", has(MSBlocks.SEAWATER_PUMP.getBlock()))
                .save(recipeOutput);

        // Blocks (slabs y stairs por color)
        addBlock(recipeOutput, MSItems.HIGH_QUALITY_CONCRETE_CLUMP.get(), MSBlocks.HIGH_QUALITY_CONCRETE.getBlock(), MSBlocks.HIGH_QUALITY_CONCRETE.getBlock(), MSBlocks.HIGH_QUALITY_CONCRETE_SLABS.getBlock(), MSBlocks.HIGH_QUALITY_CONCRETE_STAIRS.getBlock());
        addBlock(recipeOutput, MSItems.HIGH_QUALITY_CONCRETE_CLUMP_AQUA.get(), MSBlocks.AQUA_HIGH_QUALITY_CONCRETE.getBlock(), MSBlocks.AQUA_HIGH_QUALITY_CONCRETE.getBlock(), MSBlocks.AQUA_HIGH_QUALITY_CONCRETE_SLABS.getBlock(), MSBlocks.AQUA_HIGH_QUALITY_CONCRETE_STAIRS.getBlock());
        addBlock(recipeOutput, MSItems.HIGH_QUALITY_CONCRETE_CLUMP_BLACK.get(), MSBlocks.BLACK_HIGH_QUALITY_CONCRETE.getBlock(), MSBlocks.BLACK_HIGH_QUALITY_CONCRETE.getBlock(), MSBlocks.BLACK_HIGH_QUALITY_CONCRETE_SLABS.getBlock(), MSBlocks.BLACK_HIGH_QUALITY_CONCRETE_STAIRS.getBlock());
        addBlock(recipeOutput, MSItems.HIGH_QUALITY_CONCRETE_CLUMP_BLUE.get(), MSBlocks.BLUE_HIGH_QUALITY_CONCRETE.getBlock(), MSBlocks.BLUE_HIGH_QUALITY_CONCRETE.getBlock(), MSBlocks.BLUE_HIGH_QUALITY_CONCRETE_SLABS.getBlock(), MSBlocks.BLUE_HIGH_QUALITY_CONCRETE_STAIRS.getBlock());
        addBlock(recipeOutput, MSItems.HIGH_QUALITY_CONCRETE_CLUMP_GREEN.get(), MSBlocks.GREEN_HIGH_QUALITY_CONCRETE.getBlock(), MSBlocks.GREEN_HIGH_QUALITY_CONCRETE.getBlock(), MSBlocks.GREEN_HIGH_QUALITY_CONCRETE_SLABS.getBlock(), MSBlocks.GREEN_HIGH_QUALITY_CONCRETE_STAIRS.getBlock());
        addBlock(recipeOutput, MSItems.HIGH_QUALITY_CONCRETE_CLUMP_CYAN.get(), MSBlocks.CYAN_HIGH_QUALITY_CONCRETE.getBlock(), MSBlocks.CYAN_HIGH_QUALITY_CONCRETE.getBlock(), MSBlocks.CYAN_HIGH_QUALITY_CONCRETE_SLABS.getBlock(), MSBlocks.CYAN_HIGH_QUALITY_CONCRETE_STAIRS.getBlock());
        addBlock(recipeOutput, MSItems.HIGH_QUALITY_CONCRETE_CLUMP_DARK_RED.get(), MSBlocks.DARK_RED_HIGH_QUALITY_CONCRETE.getBlock(), MSBlocks.DARK_RED_HIGH_QUALITY_CONCRETE.getBlock(), MSBlocks.DARK_RED_HIGH_QUALITY_CONCRETE_SLABS.getBlock(), MSBlocks.DARK_RED_HIGH_QUALITY_CONCRETE_STAIRS.getBlock());
        addBlock(recipeOutput, MSItems.HIGH_QUALITY_CONCRETE_CLUMP_PURPLE.get(), MSBlocks.PURPLE_HIGH_QUALITY_CONCRETE.getBlock(), MSBlocks.PURPLE_HIGH_QUALITY_CONCRETE.getBlock(), MSBlocks.PURPLE_HIGH_QUALITY_CONCRETE_SLABS.getBlock(), MSBlocks.PURPLE_HIGH_QUALITY_CONCRETE_STAIRS.getBlock());
        addBlock(recipeOutput, MSItems.HIGH_QUALITY_CONCRETE_CLUMP_ORANGE.get(), MSBlocks.ORANGE_HIGH_QUALITY_CONCRETE.getBlock(), MSBlocks.ORANGE_HIGH_QUALITY_CONCRETE.getBlock(), MSBlocks.ORANGE_HIGH_QUALITY_CONCRETE_SLABS.getBlock(), MSBlocks.ORANGE_HIGH_QUALITY_CONCRETE_STAIRS.getBlock());
        addBlock(recipeOutput, MSItems.HIGH_QUALITY_CONCRETE_CLUMP_LIGHT_GRAY.get(), MSBlocks.LIGHT_GRAY_HIGH_QUALITY_CONCRETE.getBlock(), MSBlocks.LIGHT_GRAY_HIGH_QUALITY_CONCRETE.getBlock(), MSBlocks.LIGHT_GRAY_HIGH_QUALITY_CONCRETE_SLABS.getBlock(), MSBlocks.LIGHT_GRAY_HIGH_QUALITY_CONCRETE_STAIRS.getBlock());
        addBlock(recipeOutput, MSItems.HIGH_QUALITY_CONCRETE_CLUMP_GRAY.get(), MSBlocks.GRAY_HIGH_QUALITY_CONCRETE.getBlock(), MSBlocks.GRAY_HIGH_QUALITY_CONCRETE.getBlock(), MSBlocks.GRAY_HIGH_QUALITY_CONCRETE_SLABS.getBlock(), MSBlocks.GRAY_HIGH_QUALITY_CONCRETE_STAIRS.getBlock());
        addBlock(recipeOutput, MSItems.HIGH_QUALITY_CONCRETE_CLUMP_LIGHT_BLUE.get(), MSBlocks.LIGHT_BLUE_HIGH_QUALITY_CONCRETE.getBlock(), MSBlocks.LIGHT_BLUE_HIGH_QUALITY_CONCRETE.getBlock(), MSBlocks.LIGHT_BLUE_HIGH_QUALITY_CONCRETE_SLABS.getBlock(), MSBlocks.LIGHT_BLUE_HIGH_QUALITY_CONCRETE_STAIRS.getBlock());
        addBlock(recipeOutput, MSItems.HIGH_QUALITY_CONCRETE_CLUMP_LIME.get(), MSBlocks.LIME_HIGH_QUALITY_CONCRETE.getBlock(), MSBlocks.LIME_HIGH_QUALITY_CONCRETE.getBlock(), MSBlocks.LIME_HIGH_QUALITY_CONCRETE_SLABS.getBlock(), MSBlocks.LIME_HIGH_QUALITY_CONCRETE_STAIRS.getBlock());
        addBlock(recipeOutput, MSItems.HIGH_QUALITY_CONCRETE_CLUMP_RED.get(), MSBlocks.RED_HIGH_QUALITY_CONCRETE.getBlock(), MSBlocks.RED_HIGH_QUALITY_CONCRETE.getBlock(), MSBlocks.RED_HIGH_QUALITY_CONCRETE_SLABS.getBlock(), MSBlocks.RED_HIGH_QUALITY_CONCRETE_STAIRS.getBlock());
        addBlock(recipeOutput, MSItems.HIGH_QUALITY_CONCRETE_CLUMP_MAGENTA.get(), MSBlocks.MAGENTA_HIGH_QUALITY_CONCRETE.getBlock(), MSBlocks.MAGENTA_HIGH_QUALITY_CONCRETE.getBlock(), MSBlocks.MAGENTA_HIGH_QUALITY_CONCRETE_SLABS.getBlock(), MSBlocks.MAGENTA_HIGH_QUALITY_CONCRETE_STAIRS.getBlock());
        addBlock(recipeOutput, MSItems.HIGH_QUALITY_CONCRETE_CLUMP_YELLOW.get(), MSBlocks.YELLOW_HIGH_QUALITY_CONCRETE.getBlock(), MSBlocks.YELLOW_HIGH_QUALITY_CONCRETE.getBlock(), MSBlocks.YELLOW_HIGH_QUALITY_CONCRETE_SLABS.getBlock(), MSBlocks.YELLOW_HIGH_QUALITY_CONCRETE_STAIRS.getBlock());
        addBlock(recipeOutput, MSItems.HIGH_QUALITY_CONCRETE_CLUMP_WHITE.get(), MSBlocks.WHITE_HIGH_QUALITY_CONCRETE.getBlock(), MSBlocks.WHITE_HIGH_QUALITY_CONCRETE.getBlock(), MSBlocks.WHITE_HIGH_QUALITY_CONCRETE_SLABS.getBlock(), MSBlocks.WHITE_HIGH_QUALITY_CONCRETE_STAIRS.getBlock());
        addBlock(recipeOutput, MSItems.HIGH_QUALITY_CONCRETE_CLUMP_BROWN.get(), MSBlocks.BROWN_HIGH_QUALITY_CONCRETE.getBlock(), MSBlocks.BROWN_HIGH_QUALITY_CONCRETE.getBlock(), MSBlocks.BROWN_HIGH_QUALITY_CONCRETE_SLABS.getBlock(), MSBlocks.BROWN_HIGH_QUALITY_CONCRETE_STAIRS.getBlock());
        addBlock(recipeOutput, MSItems.HIGH_QUALITY_CONCRETE_CLUMP_PINK.get(), MSBlocks.PINK_HIGH_QUALITY_CONCRETE.getBlock(), MSBlocks.PINK_HIGH_QUALITY_CONCRETE.getBlock(), MSBlocks.PINK_HIGH_QUALITY_CONCRETE_SLABS.getBlock(), MSBlocks.PINK_HIGH_QUALITY_CONCRETE_STAIRS.getBlock());

        addOreRecipe(recipeOutput, MSItems.HIGH_PERFORMANCE_ADSORBENT_BERYLLIUM.get(), Ingredient.of(TagKey.create(Registries.ITEM, new ResourceLocation("forge", "ingots/beryllium"))));
        addOreRecipe(recipeOutput, MSItems.HIGH_PERFORMANCE_ADSORBENT_COPPER.get(), Ingredient.of(TagKey.create(Registries.ITEM, new ResourceLocation("forge", "ingots/copper"))));
        addOreRecipe(recipeOutput, MSItems.HIGH_PERFORMANCE_ADSORBENT_GOLD.get(), Ingredient.of(TagKey.create(Registries.ITEM, new ResourceLocation("forge", "ingots/gold"))));
        addOreRecipe(recipeOutput, MSItems.HIGH_PERFORMANCE_ADSORBENT_IRON.get(), Ingredient.of(TagKey.create(Registries.ITEM, new ResourceLocation("forge", "ingots/iron"))));
        addOreRecipe(recipeOutput, MSItems.HIGH_PERFORMANCE_ADSORBENT_LEAD.get(), Ingredient.of(TagKey.create(Registries.ITEM, new ResourceLocation("forge", "ingots/lead"))));
        addOreRecipe(recipeOutput, MSItems.HIGH_PERFORMANCE_ADSORBENT_OSMIUM.get(), Ingredient.of(TagKey.create(Registries.ITEM, new ResourceLocation("forge", "ingots/osmium"))));
        addOreRecipe(recipeOutput, MSItems.HIGH_PERFORMANCE_ADSORBENT_TIN.get(), Ingredient.of(TagKey.create(Registries.ITEM, new ResourceLocation("forge", "ingots/tin"))));
        addOreRecipe(recipeOutput, MSItems.HIGH_PERFORMANCE_ADSORBENT_URANIUM.get(), Ingredient.of(TagKey.create(Registries.ITEM, new ResourceLocation("forge", "ingots/uranium"))));

        // Smelting: Beryllium Dust -> Beryllium Ingot
        SimpleCookingRecipeBuilder.smelting(
                Ingredient.of(MSItems.DUST_BERYLLIUM.get()),
                RecipeCategory.MISC,
                MSItems.INGOT_BERYLLIUM.get(),
                0.7f, 200)
                .group("beryllium_ingot")
                .unlockedBy("has_dust_beryllium", has(MSItems.DUST_BERYLLIUM.get()))
                .save(recipeOutput, new ResourceLocation(MekanismElements.MODID, "smelting/beryllium_ingot_from_dust"));

        // Blasting: Beryllium Dust -> Beryllium Ingot
        SimpleCookingRecipeBuilder.blasting(
                Ingredient.of(MSItems.DUST_BERYLLIUM.get()),
                RecipeCategory.MISC,
                MSItems.INGOT_BERYLLIUM.get(),
                0.7f, 100)
                .group("beryllium_ingot")
                .unlockedBy("has_dust_beryllium", has(MSItems.DUST_BERYLLIUM.get()))
                .save(recipeOutput, new ResourceLocation(MekanismElements.MODID, "blasting/beryllium_ingot_from_dust"));

        // Smelting: Calcium Oxide Dust desde corales muertos y calcite
        SimpleCookingRecipeBuilder.smelting(
                Ingredient.of(Items.CALCITE),
                RecipeCategory.MISC,
                MSItems.DUST_CALCIUM_OXIDE.get(),
                0.6f, 200)
                .unlockedBy("has_calcite", has(Items.CALCITE))
                .save(recipeOutput, new ResourceLocation(MekanismElements.MODID, "smelting/dust_calcium_oxide_from_calcite"));

        SimpleCookingRecipeBuilder.smelting(
                Ingredient.of(Items.DRIPSTONE_BLOCK),
                RecipeCategory.MISC,
                MSItems.DUST_CALCIUM_OXIDE.get(),
                0.6f, 200)
                .unlockedBy("has_dripstone", has(Items.DRIPSTONE_BLOCK))
                .save(recipeOutput, new ResourceLocation(MekanismElements.MODID, "smelting/dust_calcium_oxide_from_dripstone"));

        SimpleCookingRecipeBuilder.smelting(
                Ingredient.of(Items.DEAD_TUBE_CORAL_BLOCK),
                RecipeCategory.MISC,
                MSItems.DUST_CALCIUM_OXIDE.get(),
                0.6f, 200)
                .unlockedBy("has_coral", has(Items.DEAD_TUBE_CORAL_BLOCK))
                .save(recipeOutput, new ResourceLocation(MekanismElements.MODID, "smelting/dust_calcium_oxide_from_tube_coral"));

        SimpleCookingRecipeBuilder.smelting(
                Ingredient.of(Items.DEAD_BRAIN_CORAL_BLOCK),
                RecipeCategory.MISC,
                MSItems.DUST_CALCIUM_OXIDE.get(),
                0.6f, 200)
                .unlockedBy("has_coral", has(Items.DEAD_BRAIN_CORAL_BLOCK))
                .save(recipeOutput, new ResourceLocation(MekanismElements.MODID, "smelting/dust_calcium_oxide_from_brain_coral"));

        SimpleCookingRecipeBuilder.smelting(
                Ingredient.of(Items.DEAD_BUBBLE_CORAL_BLOCK),
                RecipeCategory.MISC,
                MSItems.DUST_CALCIUM_OXIDE.get(),
                0.6f, 200)
                .unlockedBy("has_coral", has(Items.DEAD_BUBBLE_CORAL_BLOCK))
                .save(recipeOutput, new ResourceLocation(MekanismElements.MODID, "smelting/dust_calcium_oxide_from_bubble_coral"));

        SimpleCookingRecipeBuilder.smelting(
                Ingredient.of(Items.DEAD_FIRE_CORAL_BLOCK),
                RecipeCategory.MISC,
                MSItems.DUST_CALCIUM_OXIDE.get(),
                0.6f, 200)
                .unlockedBy("has_coral", has(Items.DEAD_FIRE_CORAL_BLOCK))
                .save(recipeOutput, new ResourceLocation(MekanismElements.MODID, "smelting/dust_calcium_oxide_from_fire_coral"));

        SimpleCookingRecipeBuilder.smelting(
                Ingredient.of(Items.DEAD_HORN_CORAL_BLOCK),
                RecipeCategory.MISC,
                MSItems.DUST_CALCIUM_OXIDE.get(),
                0.6f, 200)
                .unlockedBy("has_coral", has(Items.DEAD_HORN_CORAL_BLOCK))
                .save(recipeOutput, new ResourceLocation(MekanismElements.MODID, "smelting/dust_calcium_oxide_from_horn_coral"));

        // Recetas de máquinas custom (delegadas)
        CrushingRecipeProvider.buildRecipes(recipeOutput);
        ChemicalDemolitionRecipeProvider.buildRecipes(recipeOutput);
        AdsorptionRecipeProvider.buildRecipes(recipeOutput);
        RadiationIrradiatingRecipeProvider.buildRecipes(recipeOutput);
        ChemicalInfusingRecipeProvider.buildRecipes(recipeOutput);
        ReactionRecipeProvider.buildRecipes(recipeOutput);
        NucleosynthesizingRecipeProvider.buildRecipes(recipeOutput);
        RotaryRecipeProvider.buildRecipes(recipeOutput);
        SeparatingRecipeProvider.buildRecipes(recipeOutput);
        CentrifugingRecipeProvider.buildRecipes(recipeOutput);
        OxidizingRecipeProvider.buildRecipes(recipeOutput);
        EvaporatingRecipeProvider.buildRecipes(recipeOutput);
        DissolutionRecipeProvider.buildRecipes(recipeOutput);
        PaintingRecipeProvider.buildRecipes(recipeOutput);
        CrystallizingRecipeProvider.buildRecipes(recipeOutput);
        ActivatingRecipeProvider.buildRecipes(recipeOutput);
        InjectingRecipeProvider.buildRecipes(recipeOutput);
        InfinityOreReprocessingRecipeProvider.buildRecipes(recipeOutput);

        // Stonecutting: High Quality Concrete -> Slab / Stairs (todos los colores)
        addStonecuttingRecipes(recipeOutput);
    }

    private void addStonecuttingRecipes(Consumer<FinishedRecipe> output) {
        addHqcStonecutting(output, "high_quality_concrete", MSBlocks.HIGH_QUALITY_CONCRETE.getBlock(), MSBlocks.HIGH_QUALITY_CONCRETE_SLABS.getBlock(), MSBlocks.HIGH_QUALITY_CONCRETE_STAIRS.getBlock());
        addHqcStonecutting(output, "aqua_high_quality_concrete", MSBlocks.AQUA_HIGH_QUALITY_CONCRETE.getBlock(), MSBlocks.AQUA_HIGH_QUALITY_CONCRETE_SLABS.getBlock(), MSBlocks.AQUA_HIGH_QUALITY_CONCRETE_STAIRS.getBlock());
        addHqcStonecutting(output, "black_high_quality_concrete", MSBlocks.BLACK_HIGH_QUALITY_CONCRETE.getBlock(), MSBlocks.BLACK_HIGH_QUALITY_CONCRETE_SLABS.getBlock(), MSBlocks.BLACK_HIGH_QUALITY_CONCRETE_STAIRS.getBlock());
        addHqcStonecutting(output, "blue_high_quality_concrete", MSBlocks.BLUE_HIGH_QUALITY_CONCRETE.getBlock(), MSBlocks.BLUE_HIGH_QUALITY_CONCRETE_SLABS.getBlock(), MSBlocks.BLUE_HIGH_QUALITY_CONCRETE_STAIRS.getBlock());
        addHqcStonecutting(output, "brown_high_quality_concrete", MSBlocks.BROWN_HIGH_QUALITY_CONCRETE.getBlock(), MSBlocks.BROWN_HIGH_QUALITY_CONCRETE_SLABS.getBlock(), MSBlocks.BROWN_HIGH_QUALITY_CONCRETE_STAIRS.getBlock());
        addHqcStonecutting(output, "cyan_high_quality_concrete", MSBlocks.CYAN_HIGH_QUALITY_CONCRETE.getBlock(), MSBlocks.CYAN_HIGH_QUALITY_CONCRETE_SLABS.getBlock(), MSBlocks.CYAN_HIGH_QUALITY_CONCRETE_STAIRS.getBlock());
        addHqcStonecutting(output, "dark_red_high_quality_concrete", MSBlocks.DARK_RED_HIGH_QUALITY_CONCRETE.getBlock(), MSBlocks.DARK_RED_HIGH_QUALITY_CONCRETE_SLABS.getBlock(), MSBlocks.DARK_RED_HIGH_QUALITY_CONCRETE_STAIRS.getBlock());
        addHqcStonecutting(output, "gray_high_quality_concrete", MSBlocks.GRAY_HIGH_QUALITY_CONCRETE.getBlock(), MSBlocks.GRAY_HIGH_QUALITY_CONCRETE_SLABS.getBlock(), MSBlocks.GRAY_HIGH_QUALITY_CONCRETE_STAIRS.getBlock());
        addHqcStonecutting(output, "green_high_quality_concrete", MSBlocks.GREEN_HIGH_QUALITY_CONCRETE.getBlock(), MSBlocks.GREEN_HIGH_QUALITY_CONCRETE_SLABS.getBlock(), MSBlocks.GREEN_HIGH_QUALITY_CONCRETE_STAIRS.getBlock());
        addHqcStonecutting(output, "light_blue_high_quality_concrete", MSBlocks.LIGHT_BLUE_HIGH_QUALITY_CONCRETE.getBlock(), MSBlocks.LIGHT_BLUE_HIGH_QUALITY_CONCRETE_SLABS.getBlock(), MSBlocks.LIGHT_BLUE_HIGH_QUALITY_CONCRETE_STAIRS.getBlock());
        addHqcStonecutting(output, "light_gray_high_quality_concrete", MSBlocks.LIGHT_GRAY_HIGH_QUALITY_CONCRETE.getBlock(), MSBlocks.LIGHT_GRAY_HIGH_QUALITY_CONCRETE_SLABS.getBlock(), MSBlocks.LIGHT_GRAY_HIGH_QUALITY_CONCRETE_STAIRS.getBlock());
        addHqcStonecutting(output, "lime_high_quality_concrete", MSBlocks.LIME_HIGH_QUALITY_CONCRETE.getBlock(), MSBlocks.LIME_HIGH_QUALITY_CONCRETE_SLABS.getBlock(), MSBlocks.LIME_HIGH_QUALITY_CONCRETE_STAIRS.getBlock());
        addHqcStonecutting(output, "magenta_high_quality_concrete", MSBlocks.MAGENTA_HIGH_QUALITY_CONCRETE.getBlock(), MSBlocks.MAGENTA_HIGH_QUALITY_CONCRETE_SLABS.getBlock(), MSBlocks.MAGENTA_HIGH_QUALITY_CONCRETE_STAIRS.getBlock());
        addHqcStonecutting(output, "orange_high_quality_concrete", MSBlocks.ORANGE_HIGH_QUALITY_CONCRETE.getBlock(), MSBlocks.ORANGE_HIGH_QUALITY_CONCRETE_SLABS.getBlock(), MSBlocks.ORANGE_HIGH_QUALITY_CONCRETE_STAIRS.getBlock());
        addHqcStonecutting(output, "pink_high_quality_concrete", MSBlocks.PINK_HIGH_QUALITY_CONCRETE.getBlock(), MSBlocks.PINK_HIGH_QUALITY_CONCRETE_SLABS.getBlock(), MSBlocks.PINK_HIGH_QUALITY_CONCRETE_STAIRS.getBlock());
        addHqcStonecutting(output, "purple_high_quality_concrete", MSBlocks.PURPLE_HIGH_QUALITY_CONCRETE.getBlock(), MSBlocks.PURPLE_HIGH_QUALITY_CONCRETE_SLABS.getBlock(), MSBlocks.PURPLE_HIGH_QUALITY_CONCRETE_STAIRS.getBlock());
        addHqcStonecutting(output, "red_high_quality_concrete", MSBlocks.RED_HIGH_QUALITY_CONCRETE.getBlock(), MSBlocks.RED_HIGH_QUALITY_CONCRETE_SLABS.getBlock(), MSBlocks.RED_HIGH_QUALITY_CONCRETE_STAIRS.getBlock());
        addHqcStonecutting(output, "white_high_quality_concrete", MSBlocks.WHITE_HIGH_QUALITY_CONCRETE.getBlock(), MSBlocks.WHITE_HIGH_QUALITY_CONCRETE_SLABS.getBlock(), MSBlocks.WHITE_HIGH_QUALITY_CONCRETE_STAIRS.getBlock());
        addHqcStonecutting(output, "yellow_high_quality_concrete", MSBlocks.YELLOW_HIGH_QUALITY_CONCRETE.getBlock(), MSBlocks.YELLOW_HIGH_QUALITY_CONCRETE_SLABS.getBlock(), MSBlocks.YELLOW_HIGH_QUALITY_CONCRETE_STAIRS.getBlock());
    }

    /**
     * Genera las dos recetas de cortapiedras (slab x2, stairs x1) para un bloque de High Quality Concrete.
     * Los ids coinciden exactamente con los JSON ya existentes: "{name}_slab" y "{name}_stairs".
     */
    private void addHqcStonecutting(Consumer<FinishedRecipe> output, String name, Block base, Block slab, Block stairs) {
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(base), RecipeCategory.BUILDING_BLOCKS, slab, 2)
                .unlockedBy("has_" + name, has(base))
                .save(output, new ResourceLocation(MekanismElements.MODID, "stonecutting/" + name + "_slab_alt"));

        SingleItemRecipeBuilder.stonecutting(Ingredient.of(base), RecipeCategory.BUILDING_BLOCKS, stairs, 1)
                .unlockedBy("has_" + name, has(base))
                .save(output, new ResourceLocation(MekanismElements.MODID, "stonecutting/" + name + "_stairs_alt"));
    }

    private void addColorRecipe(Consumer<FinishedRecipe> output, Item item, Item dye) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, item, 8)
                .requires(MSItems.HIGH_QUALITY_CONCRETE_POWDER.get(), 8)
                .requires(dye)
                .unlockedBy("has_clump", has(MSItems.HIGH_QUALITY_CONCRETE_POWDER.get()))
                .save(output);
    }

    private void addOreRecipe(Consumer<FinishedRecipe> output, Item item, Ingredient ore) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, item, 1)
                .pattern("ACA")
                .pattern("CXC")
                .pattern("ACA")
                .define('A', MekanismItems.ANTIMATTER_PELLET.get())
                .define('X', MSItems.HIGH_PERFORMANCE_ADSORBENT)
                .define('C', ore)
                .unlockedBy("has_high_performance_adsorbent", has(MSItems.HIGH_PERFORMANCE_ADSORBENT.get()))
                .save(output);
    }

    private void addBlock(Consumer<FinishedRecipe> output, Item input1, Block input2, Block block, Block slab, Block stairs) {
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