package fixdol.mekanismelements.common.datagen.machines;

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

import fixdol.mekanismelements.common.datagen.machines.ChemicalDemolitionRecipeProvider;
import mekanism.api.recipes.ingredients.creator.IngredientCreatorAccess;
import net.minecraft.world.item.Item;
import mekanism.api.recipes.ingredients.ItemStackIngredient;
import net.minecraft.world.item.Items;
import java.util.LinkedHashMap;
import fixdol.mekanismelements.common.registries.MSGases;
import java.util.Map;
import org.jetbrains.annotations.Nullable;


import fixdol.mekanismelements.api.recipes.ChemicalDemolitionRecipe;
import fixdol.mekanismelements.common.recipe.impl.ChemicalDemolitionIRecipe;
import mekanism.api.recipes.ingredients.ChemicalStackIngredient;
import net.minecraft.world.item.ItemStack;


public class ChemicalDemolitionRecipeProvider {

    public static void buildRecipes(Consumer<FinishedRecipe> output) {

        // clock -> 4 gold ingot + redstone
        new Builder(
                IngredientCreatorAccess.item().from(Items.CLOCK, 1),
                IngredientCreatorAccess.gas().from(MSGases.SEAWATER.get(), 1),
                new ItemStack(Items.GOLD_INGOT, 4),
                new ItemStack(Items.REDSTONE, 1)
        )
        .unlockedBy("has_clock", InventoryChangeTrigger.TriggerInstance.hasItems(Items.CLOCK))
        .save(output, new ResourceLocation("mekanismelements", "chemical_demolition/clock"));

        // compass -> 4 iron ingot + redstone
        new Builder(
                IngredientCreatorAccess.item().from(Items.COMPASS, 1),
                IngredientCreatorAccess.gas().from(MSGases.SEAWATER.get(), 1),
                new ItemStack(Items.IRON_INGOT, 4),
                new ItemStack(Items.REDSTONE, 1)
        )
        .unlockedBy("has_compass", InventoryChangeTrigger.TriggerInstance.hasItems(Items.COMPASS))
        .save(output, new ResourceLocation("mekanismelements", "chemical_demolition/compass"));

    new Builder(
                IngredientCreatorAccess.item().from(Items.TOTEM_OF_UNDYING, 1),
                IngredientCreatorAccess.gas().from(MSGases.SEAWATER.get(), 1),
                new ItemStack(Items.EMERALD, 20),
                new ItemStack(Items.ENCHANTED_GOLDEN_APPLE, 7)
        )
        .unlockedBy("has_compass", InventoryChangeTrigger.TriggerInstance.hasItems(Items.COMPASS))
        .save(output, new ResourceLocation("mekanismelements", "chemical_demolition/totem_of_undying"));
    }

    private static class Builder {

        private final ItemStackIngredient itemInput;
        private final ChemicalStackIngredient.GasStackIngredient chemicalInput;
        private final ItemStack mainOutput;
        private final ItemStack secondaryOutput;

        private Builder(ItemStackIngredient itemInput, ChemicalStackIngredient.GasStackIngredient chemicalInput,
                        ItemStack mainOutput, ItemStack secondaryOutput) {
            this.itemInput = itemInput;
            this.chemicalInput = chemicalInput;
            this.mainOutput = mainOutput;
            this.secondaryOutput = secondaryOutput;
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
            json.add(JsonConstants.CHEMICAL_INPUT, chemicalInput.serialize());
            json.add(JsonConstants.MAIN_OUTPUT, SerializerHelper.serializeItemStack(mainOutput));
            json.add(JsonConstants.SECONDARY_OUTPUT, SerializerHelper.serializeItemStack(secondaryOutput));
            output.accept(new MSFinishedRecipe(id, MSRecipeSerializers.CHEMICAL_DEMOLITION::get, json));
        }
    }
}
