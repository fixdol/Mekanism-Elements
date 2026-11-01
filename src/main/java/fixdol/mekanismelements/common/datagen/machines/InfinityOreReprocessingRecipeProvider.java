package fixdol.mekanismelements.datagen.machines;

import fixdol.mekanismelements.api.recipes.InfinityOreReprocessingRecipe;
import fixdol.mekanismelements.common.MekanismElements;
import fixdol.mekanismelements.common.recipe.impl.InfinityOreReprocessingIRecipe;
import fixdol.mekanismelements.common.registries.MSGases;
import mekanism.api.chemical.Chemical;
import mekanism.api.recipes.ingredients.ChemicalStackIngredient;
import mekanism.api.recipes.ingredients.ItemStackIngredient;
import mekanism.api.recipes.ingredients.creator.IngredientCreatorAccess;
import mekanism.common.registries.MekanismBlocks;
import mekanism.common.registries.MekanismItems;
import mekanism.common.resource.PrimaryResource;
import mekanism.common.resource.ResourceType;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.criterion.RecipeUnlockedTrigger;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.ItemLike;
import org.jspecify.annotations.Nullable;

import java.util.LinkedHashMap;
import java.util.Map;

import static fixdol.mekanismelements.common.registries.MSItems.*;

public class InfinityOreReprocessingRecipeProvider {

    // Dust
    private static ItemStackTemplate metalDust(PrimaryResource resource) {
        return MekanismItems.getProcessedResource(ResourceType.DUST, resource).asTemplate(16);
    }

    // Dirty Dust: es el item que sale del Crusher al procesar la mena vanilla (raw ore),
    // primer eslabon de la cadena de Mekanism antes de Clump/Shard/Crystal/Dust.
    private static ItemLike dirtyDust(PrimaryResource resource) {
        return MekanismItems.getProcessedResource(ResourceType.DIRTY_DUST, resource);
    }

    public static void buildRecipes(RecipeOutput recipeOutput) {

        //mekanism , vanilla and other mods
        register(recipeOutput, "dirty_lead", dirtyDust(PrimaryResource.LEAD), MSGases.SEAWATER, 9500, metalDust(PrimaryResource.LEAD));
        register(recipeOutput, "dirty_osmium", dirtyDust(PrimaryResource.OSMIUM), MSGases.SEAWATER, 9500, metalDust(PrimaryResource.OSMIUM));
        register(recipeOutput, "dirty_tin", dirtyDust(PrimaryResource.TIN), MSGases.SEAWATER, 9500, metalDust(PrimaryResource.TIN));
        register(recipeOutput, "dirty_uranium", dirtyDust(PrimaryResource.URANIUM), MSGases.SEAWATER, 9500, metalDust(PrimaryResource.URANIUM));
        register(recipeOutput, "dirty_copper", dirtyDust(PrimaryResource.COPPER), MSGases.SEAWATER, 9500, metalDust(PrimaryResource.COPPER));
        register(recipeOutput, "dirty_gold", dirtyDust(PrimaryResource.GOLD), MSGases.SEAWATER, 9500, metalDust(PrimaryResource.GOLD));
        register(recipeOutput, "dirty_iron", dirtyDust(PrimaryResource.IRON), MSGases.SEAWATER, 9500, metalDust(PrimaryResource.IRON));
        register(recipeOutput, "sulfur_dust", MekanismBlocks.CHARCOAL_BLOCK, MSGases.SEAWATER, 9500, MekanismItems.SULFUR_DUST.asTemplate(16));
        register(recipeOutput, "fluorite_dust", MekanismItems.FLUORITE_GEM, MSGases.SEAWATER, 9500, MekanismItems.FLUORITE_DUST.asTemplate(16));

        //mekanism elements
        register(recipeOutput, "beryllium", INGOT_BERYLLIUM, MSGases.SEAWATER, 9500, DUST_BERYLLIUM.asTemplate(16));
    }

    private static void register(RecipeOutput recipeOutput, String name, ItemLike itemInput, Holder<Chemical> chemical, int amount, ItemStackTemplate output) {
        register(recipeOutput, name, itemInput, 1, chemical, amount, output);
    }

    private static void register(RecipeOutput recipeOutput, String name, ItemLike itemInput, int itemAmount, Holder<Chemical> chemical, int amount, ItemStackTemplate output) {
        ItemStackIngredient itemIngredient = IngredientCreatorAccess.item().from(itemInput, itemAmount);
        ChemicalStackIngredient chemicalIngredient = IngredientCreatorAccess.chemicalStack().fromHolder(chemical, amount);
        Identifier id = MekanismElements.rl("infinity_ore_reprocessing/" + name);
        new Builder(itemIngredient, chemicalIngredient, output)
                .unlockedBy("has_" + name, RecipeUnlockedTrigger.unlocked(ResourceKey.create(Registries.RECIPE, id)))
                .save(recipeOutput, id);
    }

    private static class Builder implements RecipeBuilder {

        private final ItemStackIngredient itemInput;
        private final ChemicalStackIngredient chemicalInput;
        private final ItemStackTemplate output;
        private final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();
        @Nullable
        private String group;

        private Builder(ItemStackIngredient itemInput, ChemicalStackIngredient chemicalInput, ItemStackTemplate output) {
            this.itemInput = itemInput;
            this.chemicalInput = chemicalInput;
            this.output = output;
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
            return RecipeBuilder.getDefaultRecipeId(output);
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

            InfinityOreReprocessingRecipe recipe = new InfinityOreReprocessingIRecipe(itemInput, chemicalInput, this.output);

            output.accept(id, recipe, advancement.build(id.identifier().withPrefix("recipes/")));
        }
    }
}
