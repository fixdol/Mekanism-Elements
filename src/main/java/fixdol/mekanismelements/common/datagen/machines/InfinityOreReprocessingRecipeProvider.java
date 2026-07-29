package fixdol.mekanismelements.datagen.machines;

import fixdol.mekanismelements.api.recipes.InfinityOreReprocessingRecipe;
import fixdol.mekanismelements.common.recipe.impl.InfinityOreReprocessingIRecipe;
import mekanism.api.MekanismAPI;
import mekanism.api.chemical.Chemical;
import mekanism.api.recipes.ingredients.ChemicalStackIngredient;
import mekanism.api.recipes.ingredients.ItemStackIngredient;
import mekanism.api.recipes.ingredients.creator.IngredientCreatorAccess;
import mekanism.common.registries.MekanismItems;
import mekanism.common.registries.MekanismBlocks;
import mekanism.common.resource.PrimaryResource;
import mekanism.common.resource.ResourceType;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.core.Holder;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedHashMap;
import java.util.Map;

import static fixdol.mekanismelements.common.registries.MSItems.*;

public class InfinityOreReprocessingRecipeProvider {

    private static Holder<Chemical> chemicalHolder(String namespace, String path) {
        ResourceLocation id = ResourceLocation.fromNamespaceAndPath(namespace, path);
        return MekanismAPI.CHEMICAL_REGISTRY.getHolder(id)
                .orElseThrow(() -> new IllegalStateException("Unknown chemical: " + id));
    }

    // Dust
    private static ItemStack metalDust(PrimaryResource resource) {
        return new ItemStack(MekanismItems.PROCESSED_RESOURCES.get(ResourceType.DUST, resource).get(), 16);
    }

    // Dirty Dust: es el item que sale del Crusher al procesar la mena vanilla (raw ore),
    // primer eslabon de la cadena de Mekanism antes de Clump/Shard/Crystal/Dust.
    private static Item dirtyDust(PrimaryResource resource) {
        return MekanismItems.PROCESSED_RESOURCES.get(ResourceType.DIRTY_DUST, resource).get();
    }

    public static void buildRecipes(RecipeOutput recipeOutput) {

        //mekanism , vanilla and other mods
        register(recipeOutput, "dirty_lead", dirtyDust(PrimaryResource.LEAD), "mekanismelements", "seawater", 9500, metalDust(PrimaryResource.LEAD));
        register(recipeOutput, "dirty_osmium", dirtyDust(PrimaryResource.OSMIUM), "mekanismelements", "seawater", 9500, metalDust(PrimaryResource.OSMIUM));
        register(recipeOutput, "dirty_tin", dirtyDust(PrimaryResource.TIN), "mekanismelements", "seawater", 9500, metalDust(PrimaryResource.TIN));
        register(recipeOutput, "dirty_uranium", dirtyDust(PrimaryResource.URANIUM), "mekanismelements", "seawater", 9500, metalDust(PrimaryResource.URANIUM));
        register(recipeOutput, "dirty_copper", dirtyDust(PrimaryResource.COPPER), "mekanismelements", "seawater", 9500, metalDust(PrimaryResource.COPPER));
        register(recipeOutput, "dirty_gold", dirtyDust(PrimaryResource.GOLD), "mekanismelements", "seawater", 9500, metalDust(PrimaryResource.GOLD));
        register(recipeOutput, "dirty_iron", dirtyDust(PrimaryResource.IRON), "mekanismelements", "seawater", 9500, metalDust(PrimaryResource.IRON));
        register(recipeOutput, "sulfur_dust", MekanismBlocks.CHARCOAL_BLOCK.get(), "mekanismelements", "seawater", 9500, new ItemStack(MekanismItems.SULFUR_DUST.get(), 16));
        register(recipeOutput, "fluorite_dust", MekanismItems.FLUORITE_GEM.get(), "mekanismelements", "seawater", 9500, new ItemStack(MekanismItems.FLUORITE_DUST.get(), 16));

        //mekanism elements
        register(recipeOutput, "beryllium", INGOT_BERYLLIUM.get(), "mekanismelements", "seawater", 9500, new ItemStack(DUST_BERYLLIUM.get(), 16));
    }

    private static void register(RecipeOutput recipeOutput, String name, ItemLike itemInput, String chemicalNamespace, String chemicalPath, long amount, ItemStack output) {
        register(recipeOutput, name, itemInput, 1, chemicalNamespace, chemicalPath, amount, output);
    }

    private static void register(RecipeOutput recipeOutput, String name, ItemLike itemInput, int itemAmount, String chemicalNamespace, String chemicalPath, long amount, ItemStack output) {
        ItemStackIngredient itemIngredient = IngredientCreatorAccess.item().from(itemInput, itemAmount);
        ChemicalStackIngredient chemicalIngredient = IngredientCreatorAccess.chemicalStack().fromHolder(chemicalHolder(chemicalNamespace, chemicalPath), amount);
        new Builder(itemIngredient, chemicalIngredient, output)
                .unlockedBy("has_" + name, RecipeUnlockedTrigger.unlocked(ResourceLocation.fromNamespaceAndPath("mekanismelements", "infinity_ore_reprocessing/" + name)))
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath("mekanismelements", "infinity_ore_reprocessing/" + name));
    }

    private static class Builder implements RecipeBuilder {

        private final ItemStackIngredient itemInput;
        private final ChemicalStackIngredient chemicalInput;
        private final ItemStack output;
        private final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();
        @Nullable
        private String group;

        private Builder(ItemStackIngredient itemInput, ChemicalStackIngredient chemicalInput, ItemStack output) {
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
        public Item getResult() {
            return output.getItem();
        }

        @Override
        public void save(RecipeOutput output, ResourceLocation id) {
            Advancement.Builder advancement = output.advancement()
                    .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(id))
                    .rewards(AdvancementRewards.Builder.recipe(id))
                    .requirements(AdvancementRequirements.Strategy.OR);
            this.criteria.forEach(advancement::addCriterion);

            InfinityOreReprocessingRecipe recipe = new InfinityOreReprocessingIRecipe(itemInput, chemicalInput, this.output);

            output.accept(id, recipe, advancement.build(id.withPrefix("recipes/")));
        }
    }
}