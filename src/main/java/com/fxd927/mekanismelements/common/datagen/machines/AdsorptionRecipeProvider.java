package com.fxd927.mekanismelements.datagen.machines;

import com.fxd927.mekanismelements.api.recipes.AdsorptionRecipe;
import com.fxd927.mekanismelements.common.recipe.impl.AdsorptionIRecipe;
import mekanism.api.MekanismAPI;
import mekanism.api.chemical.ChemicalStack;
import mekanism.api.recipes.ingredients.FluidStackIngredient;
import mekanism.api.recipes.ingredients.ItemStackIngredient;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.common.crafting.SizedIngredient;
import net.neoforged.neoforge.fluids.crafting.SizedFluidIngredient;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedHashMap;
import java.util.Map;

import static com.fxd927.mekanismelements.common.registries.MSFluids.*;
import static com.fxd927.mekanismelements.common.registries.MSItems.*;

public class AdsorptionRecipeProvider {

    private static ChemicalStack chemical(String namespace, String path, long amount) {
        return new ChemicalStack(MekanismAPI.CHEMICAL_REGISTRY.get(ResourceLocation.fromNamespaceAndPath(namespace, path)), amount);
    }

    private static Criterion<?> hasItem(Item item) {
        return InventoryChangeTrigger.TriggerInstance.hasItems(item);
    }

    private static Criterion<?> hasTag(String namespace, String path) {
        TagKey<Item> tag = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(namespace, path));
        return InventoryChangeTrigger.TriggerInstance.hasItems(
                ItemPredicate.Builder.item().of(tag).build()
        );
    }

    public static void buildRecipes(RecipeOutput recipeOutput) {

        new Builder(
                ItemStackIngredient.of(SizedIngredient.of(HIGH_PERFORMANCE_ADSORBENT_LEAD.get(), 1)),
                FluidStackIngredient.of(SizedFluidIngredient.of(SEAWATER.get(), 50)),
                chemical("mekanism", "dirty_lead", 400)
        )
        .unlockedBy("has_adsorbent_lead", hasItem(HIGH_PERFORMANCE_ADSORBENT_LEAD.get()))
        .save(recipeOutput, ResourceLocation.fromNamespaceAndPath("mekanismelements", "adsorption/dirty_lead"));

        new Builder(
                ItemStackIngredient.of(SizedIngredient.of(HIGH_PERFORMANCE_ADSORBENT_OSMIUM.get(), 1)),
                FluidStackIngredient.of(SizedFluidIngredient.of(SEAWATER.get(), 50)),
                chemical("mekanism", "dirty_osmium", 400)
        )
        .unlockedBy("has_adsorbent_osmium", hasItem(HIGH_PERFORMANCE_ADSORBENT_OSMIUM.get()))
        .save(recipeOutput, ResourceLocation.fromNamespaceAndPath("mekanismelements", "adsorption/dirty_osmium"));

        new Builder(
                ItemStackIngredient.of(SizedIngredient.of(HIGH_PERFORMANCE_ADSORBENT_TIN.get(), 1)),
                FluidStackIngredient.of(SizedFluidIngredient.of(SEAWATER.get(), 50)),
                chemical("mekanism", "dirty_tin", 400)
        )
        .unlockedBy("has_adsorbent_tin", hasItem(HIGH_PERFORMANCE_ADSORBENT_TIN.get()))
        .save(recipeOutput, ResourceLocation.fromNamespaceAndPath("mekanismelements", "adsorption/dirty_tin"));

        new Builder(
                ItemStackIngredient.of(SizedIngredient.of(HIGH_PERFORMANCE_ADSORBENT_URANIUM.get(), 1)),
                FluidStackIngredient.of(SizedFluidIngredient.of(SEAWATER.get(), 50)),
                chemical("mekanism", "dirty_uranium", 400)
        )
        .unlockedBy("has_adsorbent_uranium", hasItem(HIGH_PERFORMANCE_ADSORBENT_URANIUM.get()))
        .save(recipeOutput, ResourceLocation.fromNamespaceAndPath("mekanismelements", "adsorption/dirty_uranium"));

        new Builder(
                ItemStackIngredient.of(SizedIngredient.of(HIGH_PERFORMANCE_ADSORBENT_COPPER.get(), 1)),
                FluidStackIngredient.of(SizedFluidIngredient.of(SEAWATER.get(), 50)),
                chemical("mekanism", "dirty_copper", 400)
        )
        .unlockedBy("has_adsorbent_copper", hasItem(HIGH_PERFORMANCE_ADSORBENT_COPPER.get()))
        .save(recipeOutput, ResourceLocation.fromNamespaceAndPath("mekanismelements", "adsorption/dirty_copper"));

        new Builder(
                ItemStackIngredient.of(SizedIngredient.of(HIGH_PERFORMANCE_ADSORBENT_GOLD.get(), 1)),
                FluidStackIngredient.of(SizedFluidIngredient.of(SEAWATER.get(), 50)),
                chemical("mekanism", "dirty_gold", 400)
        )
        .unlockedBy("has_adsorbent_gold", hasItem(HIGH_PERFORMANCE_ADSORBENT_GOLD.get()))
        .save(recipeOutput, ResourceLocation.fromNamespaceAndPath("mekanismelements", "adsorption/dirty_gold"));

        new Builder(
                ItemStackIngredient.of(SizedIngredient.of(HIGH_PERFORMANCE_ADSORBENT_IRON.get(), 1)),
                FluidStackIngredient.of(SizedFluidIngredient.of(SEAWATER.get(), 50)),
                chemical("mekanism", "dirty_iron", 400)
        )
        .unlockedBy("has_adsorbent_iron", hasItem(HIGH_PERFORMANCE_ADSORBENT_IRON.get()))
        .save(recipeOutput, ResourceLocation.fromNamespaceAndPath("mekanismelements", "adsorption/dirty_iron"));

        new Builder(
                ItemStackIngredient.of(SizedIngredient.of(HIGH_PERFORMANCE_ADSORBENT_BERYLLIUM.get(), 1)),
                FluidStackIngredient.of(SizedFluidIngredient.of(SEAWATER.get(), 50)),
                chemical("mekanismelements", "beryllium", 400)
        )
        .unlockedBy("has_adsorbent_beryllium", hasItem(HIGH_PERFORMANCE_ADSORBENT_BERYLLIUM.get()))
        .save(recipeOutput, ResourceLocation.fromNamespaceAndPath("mekanismelements", "adsorption/beryllium"));

        new Builder(
                ItemStackIngredient.of(SizedIngredient.of(Items.KELP, 1)),
                FluidStackIngredient.of(SizedFluidIngredient.of(SEAWATER.get(), 50)),
                chemical("mekanismelements", "iodine", 300)
        )
        .unlockedBy("has_kelp", hasItem(Items.KELP))
        .save(recipeOutput, ResourceLocation.fromNamespaceAndPath("mekanismelements", "adsorption/iodine"));

        new Builder(
                ItemStackIngredient.of(SizedIngredient.of(net.minecraft.tags.ItemTags.create(ResourceLocation.fromNamespaceAndPath("c", "dusts/charcoal")), 1)),
                FluidStackIngredient.of(SizedFluidIngredient.of(COMPRESSED_AIR.get(), 50)),
                chemical("mekanismelements", "nitrogen", 800)
        )
        .unlockedBy("has_charcoal_dust", hasTag("c", "dusts/charcoal"))
        .save(recipeOutput, ResourceLocation.fromNamespaceAndPath("mekanismelements", "adsorption/nitrogen"));
    }

    private static class Builder implements RecipeBuilder {

        private final ItemStackIngredient itemInput;
        private final FluidStackIngredient fluidInput;
        private final ChemicalStack chemOutput;
        private final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();
        @Nullable
        private String group;

        private Builder(ItemStackIngredient itemInput, FluidStackIngredient fluidInput, ChemicalStack chemOutput) {
            this.itemInput = itemInput;
            this.fluidInput = fluidInput;
            this.chemOutput = chemOutput;
        }

        @Override
        public Builder unlockedBy(String name, Criterion<?> criterion) {
            this.criteria.put(name, criterion);
            return this;
        }

        @Override
        public Builder group(@Nullable String group) {
            this.group = group;
            return this;
        }

        @Override
        public Item getResult() {
            return Items.AIR;
        }

        @Override
        public void save(RecipeOutput output, ResourceLocation id) {
            Advancement.Builder advancement = output.advancement()
                    .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(id))
                    .rewards(AdvancementRewards.Builder.recipe(id))
                    .requirements(AdvancementRequirements.Strategy.OR);
            this.criteria.forEach(advancement::addCriterion);

            AdsorptionRecipe recipe = new AdsorptionIRecipe(itemInput, fluidInput, chemOutput);

            output.accept(id, recipe, advancement.build(id.withPrefix("recipes/")));
        }
    }
}