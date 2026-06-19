package com.fxd927.mekanismelements.datagen.machines;

import com.fxd927.mekanismelements.api.recipes.ChemicalDemolitionRecipe;
import com.fxd927.mekanismelements.common.recipe.impl.ChemicalDemolitionIRecipe;
import com.fxd927.mekanismelements.common.registries.MSGases;
import mekanism.api.chemical.ChemicalStack;
import mekanism.api.recipes.ingredients.ChemicalStackIngredient;
import mekanism.api.recipes.ingredients.ItemStackIngredient;
import mekanism.api.recipes.ingredients.creator.IngredientCreatorAccess;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.common.crafting.SizedIngredient;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedHashMap;
import java.util.Map;

public class ChemicalDemolitionRecipeProvider {

    public static void buildRecipes(RecipeOutput output) {

        // clock -> 4 gold ingot + redstone
        new Builder(
                ItemStackIngredient.of(SizedIngredient.of(Items.CLOCK, 1)),
                IngredientCreatorAccess.chemicalStack().fromHolder(MSGases.AQUA_REGIA, 1),
                new ItemStack(Items.GOLD_INGOT, 4),
                new ItemStack(Items.REDSTONE, 1)
        )
        .unlockedBy("has_clock", InventoryChangeTrigger.TriggerInstance.hasItems(Items.CLOCK))
        .save(output, ResourceLocation.fromNamespaceAndPath("mekanismelements", "chemical_demolition/clock"));

        // compass -> 4 iron ingot + redstone
        new Builder(
                ItemStackIngredient.of(SizedIngredient.of(Items.COMPASS, 1)),
                IngredientCreatorAccess.chemicalStack().fromHolder(MSGases.AQUA_REGIA, 1),
                new ItemStack(Items.IRON_INGOT, 4),
                new ItemStack(Items.REDSTONE, 1)
        )
        .unlockedBy("has_compass", InventoryChangeTrigger.TriggerInstance.hasItems(Items.COMPASS))
        .save(output, ResourceLocation.fromNamespaceAndPath("mekanismelements", "chemical_demolition/compass"));
    }

    private static class Builder implements RecipeBuilder {

        private final ItemStackIngredient itemInput;
        private final ChemicalStackIngredient chemicalInput;
        private final ItemStack mainOutput;
        private final ItemStack secondaryOutput;
        private final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();
        @Nullable
        private String group;

        private Builder(ItemStackIngredient itemInput, ChemicalStackIngredient chemicalInput,
                        ItemStack mainOutput, ItemStack secondaryOutput) {
            this.itemInput = itemInput;
            this.chemicalInput = chemicalInput;
            this.mainOutput = mainOutput;
            this.secondaryOutput = secondaryOutput;
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
            return mainOutput.getItem();
        }

        @Override
        public void save(RecipeOutput output, ResourceLocation id) {
            Advancement.Builder advancement = output.advancement()
                    .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(id))
                    .rewards(AdvancementRewards.Builder.recipe(id))
                    .requirements(AdvancementRequirements.Strategy.OR);
            this.criteria.forEach(advancement::addCriterion);

            ChemicalDemolitionRecipe recipe = new ChemicalDemolitionIRecipe(
                    itemInput, chemicalInput, mainOutput, secondaryOutput);

            output.accept(id, recipe, advancement.build(id.withPrefix("recipes/")));
        }
    }
}