package fixdol.mekanismelements.common.datagen.machines;

import fixdol.mekanismelements.common.registries.MSItems;

import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;

import com.google.gson.JsonObject;
import fixdol.mekanismelements.common.datagen.MSFinishedRecipe;
import fixdol.mekanismelements.common.registries.MSRecipeSerializers;
import mekanism.api.JsonConstants;
import mekanism.api.SerializerHelper;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import java.util.function.Consumer;

import mekanism.api.recipes.ingredients.ChemicalStackIngredient;
import fixdol.mekanismelements.common.datagen.machines.InfinityOreReprocessingRecipeProvider;
import mekanism.api.recipes.ingredients.creator.IngredientCreatorAccess;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import mekanism.api.recipes.ingredients.ItemStackIngredient;
import java.util.LinkedHashMap;
import java.util.Map;
import mekanism.api.MekanismAPI;
import org.jetbrains.annotations.Nullable;


import fixdol.mekanismelements.api.recipes.InfinityOreReprocessingRecipe;
import fixdol.mekanismelements.common.recipe.impl.InfinityOreReprocessingIRecipe;
import mekanism.api.chemical.Chemical;
import mekanism.common.registries.MekanismItems;
import mekanism.common.registries.MekanismBlocks;
import mekanism.common.resource.PrimaryResource;
import mekanism.common.resource.ResourceType;
import net.minecraft.core.Holder;
import net.minecraft.world.level.ItemLike;



public class InfinityOreReprocessingRecipeProvider {

    private static mekanism.api.chemical.gas.Gas chemical(String namespace, String path) {
        ResourceLocation id = new ResourceLocation(namespace, path);
        mekanism.api.chemical.gas.Gas gas = MekanismAPI.gasRegistry().getValue(id);
        if (gas == null || gas.isEmptyType()) {
            throw new IllegalStateException("Unknown chemical: " + id);
        }
        return gas;
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

    public static void buildRecipes(Consumer<FinishedRecipe> recipeOutput) {

        //mekanism , vanilla and other mods
        register(recipeOutput, "dirty_lead", dirtyDust(PrimaryResource.LEAD), "mekanismelements", "seawater", 9500, metalDust(PrimaryResource.LEAD));
        register(recipeOutput, "dirty_osmium", dirtyDust(PrimaryResource.OSMIUM), "mekanismelements", "seawater", 9500, metalDust(PrimaryResource.OSMIUM));
        register(recipeOutput, "dirty_tin", dirtyDust(PrimaryResource.TIN), "mekanismelements", "seawater", 9500, metalDust(PrimaryResource.TIN));
        register(recipeOutput, "dirty_uranium", dirtyDust(PrimaryResource.URANIUM), "mekanismelements", "seawater", 9500, metalDust(PrimaryResource.URANIUM));
        register(recipeOutput, "dirty_copper", dirtyDust(PrimaryResource.COPPER), "mekanismelements", "seawater", 9500, metalDust(PrimaryResource.COPPER));
        register(recipeOutput, "dirty_gold", dirtyDust(PrimaryResource.GOLD), "mekanismelements", "seawater", 9500, metalDust(PrimaryResource.GOLD));
        register(recipeOutput, "dirty_iron", dirtyDust(PrimaryResource.IRON), "mekanismelements", "seawater", 9500, metalDust(PrimaryResource.IRON));
        register(recipeOutput, "sulfur_dust", MekanismBlocks.CHARCOAL_BLOCK.getBlock(), "mekanismelements", "seawater", 9500, new ItemStack(MekanismItems.SULFUR_DUST.get(), 16));
        register(recipeOutput, "fluorite_dust", MekanismItems.FLUORITE_GEM.get(), "mekanismelements", "seawater", 9500, new ItemStack(MekanismItems.FLUORITE_DUST.get(), 16));

        //mekanism elements
        register(recipeOutput, "beryllium", MSItems.INGOT_BERYLLIUM.get(), "mekanismelements", "seawater", 9500, new ItemStack(MSItems.DUST_BERYLLIUM.get(), 16));
    }

    private static void register(Consumer<FinishedRecipe> recipeOutput, String name, ItemLike itemInput, String chemicalNamespace, String chemicalPath, long amount, ItemStack output) {
        register(recipeOutput, name, itemInput, 1, chemicalNamespace, chemicalPath, amount, output);
    }

    private static void register(Consumer<FinishedRecipe> recipeOutput, String name, ItemLike itemInput, int itemAmount, String chemicalNamespace, String chemicalPath, long amount, ItemStack output) {
        ItemStackIngredient itemIngredient = IngredientCreatorAccess.item().from(itemInput, itemAmount);
        ChemicalStackIngredient.GasStackIngredient chemicalIngredient = IngredientCreatorAccess.gas().from(chemical(chemicalNamespace, chemicalPath), amount);
        new Builder(itemIngredient, chemicalIngredient, output)
                .unlockedBy("has_" + name, RecipeUnlockedTrigger.unlocked(new ResourceLocation("mekanismelements", "infinity_ore_reprocessing/" + name)))
                .save(recipeOutput, new ResourceLocation("mekanismelements", "infinity_ore_reprocessing/" + name));
    }

    private static class Builder {

        private final ItemStackIngredient itemInput;
        private final ChemicalStackIngredient.GasStackIngredient chemicalInput;
        private final ItemStack output;

        private Builder(ItemStackIngredient itemInput, ChemicalStackIngredient.GasStackIngredient chemicalInput, ItemStack output) {
            this.itemInput = itemInput;
            this.chemicalInput = chemicalInput;
            this.output = output;
        }

        public Builder unlockedBy(String name, Object criterion) {
            return this;
        }

        public Builder group(String group) {
            return this;
        }

        public void save(Consumer<FinishedRecipe> recipeOutput, ResourceLocation id) {
            JsonObject json = new JsonObject();
            json.add(JsonConstants.ITEM_INPUT, itemInput.serialize());
            json.add(JsonConstants.CHEMICAL_INPUT, chemicalInput.serialize());
            json.add(JsonConstants.OUTPUT, SerializerHelper.serializeItemStack(this.output));
            recipeOutput.accept(new MSFinishedRecipe(id, MSRecipeSerializers.INFINITY_ORE_REPROCESSING::get, json));
        }
    }
}
