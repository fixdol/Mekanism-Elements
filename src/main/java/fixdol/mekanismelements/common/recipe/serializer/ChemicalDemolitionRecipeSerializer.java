package fixdol.mekanismelements.common.recipe.serializer;

import fixdol.mekanismelements.api.recipes.ChemicalDemolitionRecipe;
import fixdol.mekanismelements.common.recipe.serializer.ChemicalDemolitionRecipeSerializer;
import mekanism.api.recipes.ingredients.ChemicalStackIngredient;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.GsonHelper;
import mekanism.api.recipes.ingredients.creator.IngredientCreatorAccess;
import net.minecraft.world.item.ItemStack;
import mekanism.api.recipes.ingredients.ItemStackIngredient;
import mekanism.api.JsonConstants;
import com.google.gson.JsonObject;
import mekanism.common.Mekanism;
import org.jetbrains.annotations.NotNull;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.resources.ResourceLocation;
import mekanism.api.SerializerHelper;

import com.google.gson.JsonSyntaxException;

public class ChemicalDemolitionRecipeSerializer<RECIPE extends ChemicalDemolitionRecipe> implements RecipeSerializer<RECIPE> {

    private final IFactory<RECIPE> factory;

    public ChemicalDemolitionRecipeSerializer(IFactory<RECIPE> factory) {
        this.factory = factory;
    }

    @NotNull
    @Override
    public RECIPE fromJson(@NotNull ResourceLocation recipeId, @NotNull JsonObject json) {
        ItemStackIngredient itemInput = IngredientCreatorAccess.item().deserialize(GsonHelper.isArrayNode(json, JsonConstants.ITEM_INPUT)
              ? GsonHelper.getAsJsonArray(json, JsonConstants.ITEM_INPUT)
              : GsonHelper.getAsJsonObject(json, JsonConstants.ITEM_INPUT));
        ChemicalStackIngredient.GasStackIngredient gasInput = IngredientCreatorAccess.gas().deserialize(GsonHelper.isArrayNode(json, JsonConstants.CHEMICAL_INPUT)
              ? GsonHelper.getAsJsonArray(json, JsonConstants.CHEMICAL_INPUT)
              : GsonHelper.getAsJsonObject(json, JsonConstants.CHEMICAL_INPUT));
        ItemStack firstOutput = SerializerHelper.getItemStack(json, JsonConstants.MAIN_OUTPUT);
        ItemStack secondOutput = SerializerHelper.getItemStack(json, JsonConstants.SECONDARY_OUTPUT);
        if (firstOutput.isEmpty() || secondOutput.isEmpty()) {
            throw new JsonSyntaxException("Recipe outputs must not be empty.");
        }
        return this.factory.create(recipeId, itemInput, gasInput, firstOutput, secondOutput);
    }

    @Override
    public RECIPE fromNetwork(@NotNull ResourceLocation recipeId, @NotNull FriendlyByteBuf buffer) {
        try {
            ItemStackIngredient itemInput = IngredientCreatorAccess.item().read(buffer);
            ChemicalStackIngredient.GasStackIngredient gasInput = IngredientCreatorAccess.gas().read(buffer);
            ItemStack firstOutput = buffer.readItem();
            ItemStack secondOutput = buffer.readItem();
            return this.factory.create(recipeId, itemInput, gasInput, firstOutput, secondOutput);
        } catch (Exception e) {
            Mekanism.logger.error("Error reading chemical demolition recipe from packet.", e);
            throw e;
        }
    }

    @Override
    public void toNetwork(@NotNull FriendlyByteBuf buffer, @NotNull RECIPE recipe) {
        try {
            recipe.write(buffer);
        } catch (Exception e) {
            Mekanism.logger.error("Error writing chemical demolition recipe to packet.", e);
            throw e;
        }
    }

    @FunctionalInterface
    public interface IFactory<RECIPE extends ChemicalDemolitionRecipe> {
        RECIPE create(ResourceLocation id, ItemStackIngredient itemInput, ChemicalStackIngredient.GasStackIngredient gasInput, ItemStack firstOutput, ItemStack secondOutput);
    }
}
