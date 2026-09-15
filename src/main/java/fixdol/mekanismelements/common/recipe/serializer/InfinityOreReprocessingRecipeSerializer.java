package fixdol.mekanismelements.common.recipe.serializer;

import mekanism.api.recipes.ingredients.ChemicalStackIngredient;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.GsonHelper;
import fixdol.mekanismelements.api.recipes.InfinityOreReprocessingRecipe;
import fixdol.mekanismelements.common.recipe.serializer.InfinityOreReprocessingRecipeSerializer;
import mekanism.api.recipes.ingredients.creator.IngredientCreatorAccess;
import net.minecraft.world.item.ItemStack;
import mekanism.api.recipes.ingredients.ItemStackIngredient;
import mekanism.api.JsonConstants;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import mekanism.common.Mekanism;
import org.jetbrains.annotations.NotNull;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.resources.ResourceLocation;
import mekanism.api.SerializerHelper;


public class InfinityOreReprocessingRecipeSerializer<RECIPE extends InfinityOreReprocessingRecipe> implements RecipeSerializer<RECIPE> {

    private final IFactory<RECIPE> factory;

    public InfinityOreReprocessingRecipeSerializer(IFactory<RECIPE> factory) {
        this.factory = factory;
    }

    @NotNull
    @Override
    public RECIPE fromJson(@NotNull ResourceLocation recipeId, @NotNull JsonObject json) {
        ItemStackIngredient itemInput = IngredientCreatorAccess.item().deserialize(GsonHelper.isArrayNode(json, JsonConstants.ITEM_INPUT)
              ? GsonHelper.getAsJsonArray(json, JsonConstants.ITEM_INPUT)
              : GsonHelper.getAsJsonObject(json, JsonConstants.ITEM_INPUT));
        ChemicalStackIngredient.GasStackIngredient chemicalInput = IngredientCreatorAccess.gas().deserialize(GsonHelper.isArrayNode(json, JsonConstants.CHEMICAL_INPUT)
              ? GsonHelper.getAsJsonArray(json, JsonConstants.CHEMICAL_INPUT)
              : GsonHelper.getAsJsonObject(json, JsonConstants.CHEMICAL_INPUT));
        ItemStack output = SerializerHelper.getItemStack(json, JsonConstants.OUTPUT);
        if (output.isEmpty()) {
            throw new JsonSyntaxException("Recipe output must not be empty.");
        }
        return this.factory.create(recipeId, itemInput, chemicalInput, output);
    }

    @Override
    public RECIPE fromNetwork(@NotNull ResourceLocation recipeId, @NotNull FriendlyByteBuf buffer) {
        try {
            ItemStackIngredient itemInput = IngredientCreatorAccess.item().read(buffer);
            ChemicalStackIngredient.GasStackIngredient chemicalInput = IngredientCreatorAccess.gas().read(buffer);
            ItemStack output = buffer.readItem();
            return this.factory.create(recipeId, itemInput, chemicalInput, output);
        } catch (Exception e) {
            Mekanism.logger.error("Error reading infinity ore reprocessing recipe from packet.", e);
            throw e;
        }
    }

    @Override
    public void toNetwork(@NotNull FriendlyByteBuf buffer, @NotNull RECIPE recipe) {
        try {
            recipe.write(buffer);
        } catch (Exception e) {
            Mekanism.logger.error("Error writing infinity ore reprocessing recipe to packet.", e);
            throw e;
        }
    }

    @FunctionalInterface
    public interface IFactory<RECIPE extends InfinityOreReprocessingRecipe> {
        RECIPE create(ResourceLocation id, ItemStackIngredient itemInput, ChemicalStackIngredient.GasStackIngredient chemicalInput, ItemStack output);
    }
}
