package fixdol.mekanismelements.datagen.machines;

import fixdol.mekanismelements.api.recipes.AdsorptionRecipe;
import fixdol.mekanismelements.common.MekanismElements;
import fixdol.mekanismelements.common.recipe.impl.AdsorptionIRecipe;
import mekanism.api.MekanismAPI;
import mekanism.api.chemical.Chemical;
import mekanism.api.chemical.ChemicalStackTemplate;
import mekanism.api.recipes.ingredients.FluidStackIngredient;
import mekanism.api.recipes.ingredients.ItemStackIngredient;
import mekanism.api.recipes.ingredients.creator.IngredientCreatorAccess;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.criterion.InventoryChangeTrigger;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.advancements.criterion.RecipeUnlockedTrigger;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Recipe;
import org.jspecify.annotations.Nullable;

import java.util.LinkedHashMap;
import java.util.Map;

import static fixdol.mekanismelements.common.registries.MSFluids.*;
import static fixdol.mekanismelements.common.registries.MSItems.*;

public class AdsorptionRecipeProvider {

    private static final ResourceKey<Recipe<?>> NO_DEFAULT_ID = ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath("if_you_see_this", "you_forgot_an_id"));

    private static ChemicalStackTemplate chemical(HolderGetter<Chemical> chemicals, String namespace, String path, int amount) {
        return new ChemicalStackTemplate(chemicals.getOrThrow(ResourceKey.create(MekanismAPI.CHEMICAL_REGISTRY_NAME, Identifier.fromNamespaceAndPath(namespace, path))), amount);
    }

    private static Criterion<?> hasItem(Item item) {
        return InventoryChangeTrigger.TriggerInstance.hasItems(item);
    }

    private static Criterion<?> hasTag(HolderGetter<Item> items, String namespace, String path) {
        TagKey<Item> tag = TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(namespace, path));
        return InventoryChangeTrigger.TriggerInstance.hasItems(
                ItemPredicate.Builder.item().of(items, tag).build()
        );
    }

    public static void buildRecipes(RecipeOutput recipeOutput, HolderLookup.Provider registries) {
        HolderGetter<Item> items = registries.lookupOrThrow(Registries.ITEM);
        HolderGetter<Chemical> chemicals = registries.lookupOrThrow(MekanismAPI.CHEMICAL_REGISTRY_NAME);

        new Builder(
                IngredientCreatorAccess.item().from(HIGH_PERFORMANCE_ADSORBENT_LEAD, 1),
                IngredientCreatorAccess.fluid().fromHolder(SEAWATER, 50),
                chemical(chemicals, "mekanism", "dirty_lead", 400)
        )
        .unlockedBy("has_adsorbent_lead", hasItem(HIGH_PERFORMANCE_ADSORBENT_LEAD.get()))
        .save(recipeOutput, MekanismElements.rl("adsorption/dirty_lead"));

        new Builder(
                IngredientCreatorAccess.item().from(HIGH_PERFORMANCE_ADSORBENT_OSMIUM, 1),
                IngredientCreatorAccess.fluid().fromHolder(SEAWATER, 50),
                chemical(chemicals, "mekanism", "dirty_osmium", 400)
        )
        .unlockedBy("has_adsorbent_osmium", hasItem(HIGH_PERFORMANCE_ADSORBENT_OSMIUM.get()))
        .save(recipeOutput, MekanismElements.rl("adsorption/dirty_osmium"));

        new Builder(
                IngredientCreatorAccess.item().from(HIGH_PERFORMANCE_ADSORBENT_TIN, 1),
                IngredientCreatorAccess.fluid().fromHolder(SEAWATER, 50),
                chemical(chemicals, "mekanism", "dirty_tin", 400)
        )
        .unlockedBy("has_adsorbent_tin", hasItem(HIGH_PERFORMANCE_ADSORBENT_TIN.get()))
        .save(recipeOutput, MekanismElements.rl("adsorption/dirty_tin"));

        new Builder(
                IngredientCreatorAccess.item().from(HIGH_PERFORMANCE_ADSORBENT_URANIUM, 1),
                IngredientCreatorAccess.fluid().fromHolder(SEAWATER, 50),
                chemical(chemicals, "mekanism", "dirty_uranium", 400)
        )
        .unlockedBy("has_adsorbent_uranium", hasItem(HIGH_PERFORMANCE_ADSORBENT_URANIUM.get()))
        .save(recipeOutput, MekanismElements.rl("adsorption/dirty_uranium"));

        new Builder(
                IngredientCreatorAccess.item().from(HIGH_PERFORMANCE_ADSORBENT_COPPER, 1),
                IngredientCreatorAccess.fluid().fromHolder(SEAWATER, 50),
                chemical(chemicals, "mekanism", "dirty_copper", 400)
        )
        .unlockedBy("has_adsorbent_copper", hasItem(HIGH_PERFORMANCE_ADSORBENT_COPPER.get()))
        .save(recipeOutput, MekanismElements.rl("adsorption/dirty_copper"));

        new Builder(
                IngredientCreatorAccess.item().from(HIGH_PERFORMANCE_ADSORBENT_GOLD, 1),
                IngredientCreatorAccess.fluid().fromHolder(SEAWATER, 50),
                chemical(chemicals, "mekanism", "dirty_gold", 400)
        )
        .unlockedBy("has_adsorbent_gold", hasItem(HIGH_PERFORMANCE_ADSORBENT_GOLD.get()))
        .save(recipeOutput, MekanismElements.rl("adsorption/dirty_gold"));

        new Builder(
                IngredientCreatorAccess.item().from(HIGH_PERFORMANCE_ADSORBENT_IRON, 1),
                IngredientCreatorAccess.fluid().fromHolder(SEAWATER, 50),
                chemical(chemicals, "mekanism", "dirty_iron", 400)
        )
        .unlockedBy("has_adsorbent_iron", hasItem(HIGH_PERFORMANCE_ADSORBENT_IRON.get()))
        .save(recipeOutput, MekanismElements.rl("adsorption/dirty_iron"));

        new Builder(
                IngredientCreatorAccess.item().from(HIGH_PERFORMANCE_ADSORBENT_BERYLLIUM, 1),
                IngredientCreatorAccess.fluid().fromHolder(SEAWATER, 50),
                chemical(chemicals, "mekanismelements", "beryllium", 400)
        )
        .unlockedBy("has_adsorbent_beryllium", hasItem(HIGH_PERFORMANCE_ADSORBENT_BERYLLIUM.get()))
        .save(recipeOutput, MekanismElements.rl("adsorption/beryllium"));

        new Builder(
                IngredientCreatorAccess.item().from(Items.KELP, 1),
                IngredientCreatorAccess.fluid().fromHolder(SEAWATER, 50),
                chemical(chemicals, "mekanismelements", "iodine", 300)
        )
        .unlockedBy("has_kelp", hasItem(Items.KELP))
        .save(recipeOutput, MekanismElements.rl("adsorption/iodine"));

        new Builder(
                IngredientCreatorAccess.item().from(items, TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath("c", "dusts/charcoal")), 1),
                IngredientCreatorAccess.fluid().fromHolder(COMPRESSED_AIR, 50),
                chemical(chemicals, "mekanismelements", "nitrogen", 800)
        )
        .unlockedBy("has_charcoal_dust", hasTag(items, "c", "dusts/charcoal"))
        .save(recipeOutput, MekanismElements.rl("adsorption/nitrogen"));
    }

    private static class Builder implements RecipeBuilder {

        private final ItemStackIngredient itemInput;
        private final FluidStackIngredient fluidInput;
        private final ChemicalStackTemplate chemOutput;
        private final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();
        @Nullable
        private String group;

        private Builder(ItemStackIngredient itemInput, FluidStackIngredient fluidInput, ChemicalStackTemplate chemOutput) {
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
        public ResourceKey<Recipe<?>> defaultId() {
            return NO_DEFAULT_ID;
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

            AdsorptionRecipe recipe = new AdsorptionIRecipe(itemInput, fluidInput, chemOutput);

            output.accept(id, recipe, advancement.build(id.identifier().withPrefix("recipes/")));
        }
    }
}
