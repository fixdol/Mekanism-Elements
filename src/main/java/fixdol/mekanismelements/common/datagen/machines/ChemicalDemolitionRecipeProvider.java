package fixdol.mekanismelements.datagen.machines;

import fixdol.mekanismelements.api.recipes.ChemicalDemolitionRecipe;
import fixdol.mekanismelements.common.MekanismElements;
import fixdol.mekanismelements.common.recipe.impl.ChemicalDemolitionIRecipe;
import fixdol.mekanismelements.common.registries.MSGases;
import mekanism.api.recipes.ingredients.ChemicalStackIngredient;
import mekanism.api.recipes.ingredients.ItemStackIngredient;
import mekanism.api.recipes.ingredients.creator.IngredientCreatorAccess;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.criterion.InventoryChangeTrigger;
import net.minecraft.advancements.criterion.RecipeUnlockedTrigger;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Recipe;
import org.jspecify.annotations.Nullable;

import java.util.LinkedHashMap;
import java.util.Map;

public class ChemicalDemolitionRecipeProvider {

    public static void buildRecipes(RecipeOutput output) {

        // clock -> 4 gold ingot + redstone
        new Builder(
                IngredientCreatorAccess.item().from(Items.CLOCK, 1),
                IngredientCreatorAccess.chemicalStack().fromHolder(MSGases.SEAWATER, 1),
                new ItemStackTemplate(Items.GOLD_INGOT, 4),
                new ItemStackTemplate(Items.REDSTONE, 1)
        )
        .unlockedBy("has_clock", InventoryChangeTrigger.TriggerInstance.hasItems(Items.CLOCK))
        .save(output, MekanismElements.rl("chemical_demolition/clock"));

        // compass -> 4 iron ingot + redstone
        new Builder(
                IngredientCreatorAccess.item().from(Items.COMPASS, 1),
                IngredientCreatorAccess.chemicalStack().fromHolder(MSGases.SEAWATER, 1),
                new ItemStackTemplate(Items.IRON_INGOT, 4),
                new ItemStackTemplate(Items.REDSTONE, 1)
        )
        .unlockedBy("has_compass", InventoryChangeTrigger.TriggerInstance.hasItems(Items.COMPASS))
        .save(output, MekanismElements.rl("chemical_demolition/compass"));

        new Builder(
                IngredientCreatorAccess.item().from(Items.TOTEM_OF_UNDYING, 1),
                IngredientCreatorAccess.chemicalStack().fromHolder(MSGases.SEAWATER, 1),
                new ItemStackTemplate(Items.EMERALD, 20),
                new ItemStackTemplate(Items.ENCHANTED_GOLDEN_APPLE, 7)
        )
        .unlockedBy("has_compass", InventoryChangeTrigger.TriggerInstance.hasItems(Items.COMPASS))
        .save(output, MekanismElements.rl("chemical_demolition/totem_of_undying"));
    }

    private static class Builder implements RecipeBuilder {

        private final ItemStackIngredient itemInput;
        private final ChemicalStackIngredient chemicalInput;
        private final ItemStackTemplate mainOutput;
        private final ItemStackTemplate secondaryOutput;
        private final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();
        @Nullable
        private String group;

        private Builder(ItemStackIngredient itemInput, ChemicalStackIngredient chemicalInput,
                        ItemStackTemplate mainOutput, ItemStackTemplate secondaryOutput) {
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
        public ResourceKey<Recipe<?>> defaultId() {
            return RecipeBuilder.getDefaultRecipeId(mainOutput);
        }

        public void save(RecipeOutput output, Identifier id) {
            save(output, ResourceKey.create(Registries.RECIPE, id));
        }

        @Override
        public void save(RecipeOutput output, ResourceKey<Recipe<?>> id) {
            Advancement.Builder advancement = output.advancement()
                    .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(id))
                    .rewards(AdvancementRewards.Builder.recipe(id))
                    .requirements(AdvancementRequirements.Strategy.OR);
            this.criteria.forEach(advancement::addCriterion);

            ChemicalDemolitionRecipe recipe = new ChemicalDemolitionIRecipe(
                    itemInput, chemicalInput, mainOutput, secondaryOutput);

            output.accept(id, recipe, advancement.build(id.identifier().withPrefix("recipes/")));
        }
    }
}
