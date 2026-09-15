package fixdol.mekanismelements.common.datagen.machines;

import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;

import com.google.gson.JsonObject;
import fixdol.mekanismelements.common.datagen.MSFinishedRecipe;
import fixdol.mekanismelements.common.registries.MSRecipeSerializers;
import mekanism.api.JsonConstants;
import mekanism.api.SerializerHelper;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import java.util.function.Consumer;

import fixdol.mekanismelements.common.datagen.machines.AdsorptionRecipeProvider;
import mekanism.api.chemical.gas.GasStack;
import mekanism.api.recipes.ingredients.creator.IngredientCreatorAccess;


import fixdol.mekanismelements.api.recipes.AdsorptionRecipe;
import fixdol.mekanismelements.common.recipe.impl.AdsorptionIRecipe;
import mekanism.api.MekanismAPI;
import mekanism.api.recipes.ingredients.FluidStackIngredient;
import mekanism.api.recipes.ingredients.ItemStackIngredient;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedHashMap;
import java.util.Map;

import static fixdol.mekanismelements.common.registries.MSFluids.*;
import static fixdol.mekanismelements.common.registries.MSItems.*;

public class AdsorptionRecipeProvider {

    private static mekanism.api.chemical.ChemicalStack<?> chemical(String namespace, String path, long amount) {
        ResourceLocation id = new ResourceLocation(namespace, path);
        mekanism.api.chemical.gas.Gas gas = MekanismAPI.gasRegistry().getValue(id);
        if (gas != null && !gas.isEmptyType()) {
            return new GasStack(gas, amount);
        }
        mekanism.api.chemical.slurry.Slurry slurry = MekanismAPI.slurryRegistry().getValue(id);
        if (slurry != null && !slurry.isEmptyType()) {
            return new mekanism.api.chemical.slurry.SlurryStack(slurry, amount);
        }
        throw new IllegalStateException("Unknown chemical: " + id);
    }

    private static Object hasItem(Item item) {
        return InventoryChangeTrigger.TriggerInstance.hasItems(item);
    }

    private static Object hasTag(String namespace, String path) {
        TagKey<Item> tag = TagKey.create(Registries.ITEM, new ResourceLocation(namespace, path));
        return InventoryChangeTrigger.TriggerInstance.hasItems(
                ItemPredicate.Builder.item().of(tag).build()
        );
    }

    public static void buildRecipes(Consumer<FinishedRecipe> recipeOutput) {

        new Builder(
                IngredientCreatorAccess.item().from(HIGH_PERFORMANCE_ADSORBENT_LEAD.get(), 1),
                IngredientCreatorAccess.fluid().from(SEAWATER.getFluid(), 50),
                chemical("mekanism", "dirty_lead", 400)
        )
        .unlockedBy("has_adsorbent_lead", hasItem(HIGH_PERFORMANCE_ADSORBENT_LEAD.get()))
        .save(recipeOutput, new ResourceLocation("mekanismelements", "adsorption/dirty_lead"));

        new Builder(
                IngredientCreatorAccess.item().from(HIGH_PERFORMANCE_ADSORBENT_OSMIUM.get(), 1),
                IngredientCreatorAccess.fluid().from(SEAWATER.getFluid(), 50),
                chemical("mekanism", "dirty_osmium", 400)
        )
        .unlockedBy("has_adsorbent_osmium", hasItem(HIGH_PERFORMANCE_ADSORBENT_OSMIUM.get()))
        .save(recipeOutput, new ResourceLocation("mekanismelements", "adsorption/dirty_osmium"));

        new Builder(
                IngredientCreatorAccess.item().from(HIGH_PERFORMANCE_ADSORBENT_TIN.get(), 1),
                IngredientCreatorAccess.fluid().from(SEAWATER.getFluid(), 50),
                chemical("mekanism", "dirty_tin", 400)
        )
        .unlockedBy("has_adsorbent_tin", hasItem(HIGH_PERFORMANCE_ADSORBENT_TIN.get()))
        .save(recipeOutput, new ResourceLocation("mekanismelements", "adsorption/dirty_tin"));

        new Builder(
                IngredientCreatorAccess.item().from(HIGH_PERFORMANCE_ADSORBENT_URANIUM.get(), 1),
                IngredientCreatorAccess.fluid().from(SEAWATER.getFluid(), 50),
                chemical("mekanism", "dirty_uranium", 400)
        )
        .unlockedBy("has_adsorbent_uranium", hasItem(HIGH_PERFORMANCE_ADSORBENT_URANIUM.get()))
        .save(recipeOutput, new ResourceLocation("mekanismelements", "adsorption/dirty_uranium"));

        new Builder(
                IngredientCreatorAccess.item().from(HIGH_PERFORMANCE_ADSORBENT_COPPER.get(), 1),
                IngredientCreatorAccess.fluid().from(SEAWATER.getFluid(), 50),
                chemical("mekanism", "dirty_copper", 400)
        )
        .unlockedBy("has_adsorbent_copper", hasItem(HIGH_PERFORMANCE_ADSORBENT_COPPER.get()))
        .save(recipeOutput, new ResourceLocation("mekanismelements", "adsorption/dirty_copper"));

        new Builder(
                IngredientCreatorAccess.item().from(HIGH_PERFORMANCE_ADSORBENT_GOLD.get(), 1),
                IngredientCreatorAccess.fluid().from(SEAWATER.getFluid(), 50),
                chemical("mekanism", "dirty_gold", 400)
        )
        .unlockedBy("has_adsorbent_gold", hasItem(HIGH_PERFORMANCE_ADSORBENT_GOLD.get()))
        .save(recipeOutput, new ResourceLocation("mekanismelements", "adsorption/dirty_gold"));

        new Builder(
                IngredientCreatorAccess.item().from(HIGH_PERFORMANCE_ADSORBENT_IRON.get(), 1),
                IngredientCreatorAccess.fluid().from(SEAWATER.getFluid(), 50),
                chemical("mekanism", "dirty_iron", 400)
        )
        .unlockedBy("has_adsorbent_iron", hasItem(HIGH_PERFORMANCE_ADSORBENT_IRON.get()))
        .save(recipeOutput, new ResourceLocation("mekanismelements", "adsorption/dirty_iron"));

        new Builder(
                IngredientCreatorAccess.item().from(HIGH_PERFORMANCE_ADSORBENT_BERYLLIUM.get(), 1),
                IngredientCreatorAccess.fluid().from(SEAWATER.getFluid(), 50),
                chemical("mekanismelements", "beryllium", 400)
        )
        .unlockedBy("has_adsorbent_beryllium", hasItem(HIGH_PERFORMANCE_ADSORBENT_BERYLLIUM.get()))
        .save(recipeOutput, new ResourceLocation("mekanismelements", "adsorption/beryllium"));

        new Builder(
                IngredientCreatorAccess.item().from(Items.KELP, 1),
                IngredientCreatorAccess.fluid().from(SEAWATER.getFluid(), 50),
                chemical("mekanismelements", "iodine", 300)
        )
        .unlockedBy("has_kelp", hasItem(Items.KELP))
        .save(recipeOutput, new ResourceLocation("mekanismelements", "adsorption/iodine"));

        new Builder(
                IngredientCreatorAccess.item().from(net.minecraft.tags.ItemTags.create(new ResourceLocation("forge", "dusts/charcoal")), 1),
                IngredientCreatorAccess.fluid().from(COMPRESSED_AIR.getFluid(), 50),
                chemical("mekanismelements", "nitrogen", 800)
        )
        .unlockedBy("has_charcoal_dust", hasTag("forge", "dusts/charcoal"))
        .save(recipeOutput, new ResourceLocation("mekanismelements", "adsorption/nitrogen"));
    }

    private static class Builder {

        private final ItemStackIngredient itemInput;
        private final FluidStackIngredient fluidInput;
        private final mekanism.api.chemical.ChemicalStack<?> chemOutput;

        private Builder(ItemStackIngredient itemInput, FluidStackIngredient fluidInput, mekanism.api.chemical.ChemicalStack<?> chemOutput) {
            this.itemInput = itemInput;
            this.fluidInput = fluidInput;
            this.chemOutput = chemOutput;
        }

        public Builder unlockedBy(String name, Object criterion) {
            return this;
        }

        public Builder group(String group) {
            return this;
        }

        public void save(Consumer<FinishedRecipe> output, ResourceLocation id) {
            JsonObject json = new JsonObject();
            json.add(JsonConstants.ITEM_INPUT, itemInput.serialize());
            json.add(JsonConstants.FLUID_INPUT, fluidInput.serialize());
            json.add(JsonConstants.OUTPUT, SerializerHelper.serializeBoxedChemicalStack(mekanism.api.chemical.merged.BoxedChemicalStack.box(chemOutput)));
            output.accept(new MSFinishedRecipe(id, MSRecipeSerializers.ADSORPTION_SEPARATOR::get, json));
        }
    }
}
