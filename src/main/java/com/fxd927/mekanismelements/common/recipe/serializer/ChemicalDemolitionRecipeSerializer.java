package com.fxd927.mekanismelements.common.recipe.serializer;

import com.fxd927.mekanismelements.api.recipes.ChemicalDemolitionRecipe;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import mekanism.api.JsonConstants;
import mekanism.api.SerializerHelper;
import mekanism.api.recipes.ingredients.ChemicalStackIngredient;
import mekanism.api.recipes.ingredients.ItemStackIngredient;
import mekanism.api.recipes.ingredients.creator.IngredientCreatorAccess;
import mekanism.common.Mekanism;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import org.jetbrains.annotations.NotNull;

public class ChemicalDemolitionRecipeSerializer <RECIPE extends ChemicalDemolitionRecipe> implements RecipeSerializer<RECIPE> {
    private final ChemicalDemolitionRecipeSerializer.IFactory<RECIPE> factory;

    public ChemicalDemolitionRecipeSerializer(ChemicalDemolitionRecipeSerializer.IFactory<RECIPE> factory) {
        this.factory = factory;
    }

    @NotNull
    @Override
    public RECIPE fromJson(@NotNull ResourceLocation recipeId, @NotNull JsonObject json) {
        JsonElement itemInput = GsonHelper.isArrayNode(json, JsonConstants.ITEM_INPUT) ? GsonHelper.getAsJsonArray(json, JsonConstants.ITEM_INPUT) :
                GsonHelper.getAsJsonObject(json, JsonConstants.ITEM_INPUT);
        ItemStackIngredient itemIngredient = IngredientCreatorAccess.item().deserialize(itemInput);
        JsonElement fluidInput = GsonHelper.isArrayNode(json, JsonConstants.GAS_INPUT) ? GsonHelper.getAsJsonArray(json, JsonConstants.GAS_INPUT) :
                GsonHelper.getAsJsonObject(json, JsonConstants.GAS_INPUT);
        ChemicalStackIngredient.GasStackIngredient fluidIngredient = IngredientCreatorAccess.gas().deserialize(fluidInput);
        ItemStack firstOutput = SerializerHelper.getItemStack(json, JsonConstants.MAIN_OUTPUT);
        ItemStack secondOutput = SerializerHelper.getItemStack(json, JsonConstants.SECONDARY_OUTPUT);

        if (firstOutput.isEmpty()) {
            throw new JsonSyntaxException("Recipe output must not be empty.");
        }
        if (secondOutput.isEmpty()) {
            throw new JsonSyntaxException("Recipe output must not be empty.");
        }
        return this.factory.create(recipeId, itemIngredient, fluidIngredient, firstOutput, secondOutput);
    }

    @Override
    public RECIPE fromNetwork(@NotNull ResourceLocation recipeId, @NotNull FriendlyByteBuf buffer) {
        try {
            ItemStackIngredient itemInput = IngredientCreatorAccess.item().read(buffer);
            ChemicalStackIngredient.GasStackIngredient fluidInput = IngredientCreatorAccess.gas().read(buffer);
            ItemStack firstOutput = buffer.readItem();
            ItemStack secondOutput = buffer.readItem();
            return this.factory.create(recipeId, itemInput, fluidInput, firstOutput, secondOutput);
        } catch (Exception e) {
            Mekanism.logger.error("Error reading itemstack gas to gas recipe from packet.", e);
            throw e;
        }
    }

    @Override
    public void toNetwork(@NotNull FriendlyByteBuf buffer, @NotNull RECIPE recipe) {
        try {
            recipe.write(buffer);
        } catch (Exception e) {
            Mekanism.logger.error("Error writing itemstack gas to gas recipe to packet.", e);
            throw e;
        }
    }

    @FunctionalInterface
    public interface IFactory<RECIPE extends ChemicalDemolitionRecipe> {

        RECIPE create(ResourceLocation id, ItemStackIngredient itemInput, ChemicalStackIngredient.GasStackIngredient gasInput, ItemStack firstOutput, ItemStack secondOutput);
    }
}
