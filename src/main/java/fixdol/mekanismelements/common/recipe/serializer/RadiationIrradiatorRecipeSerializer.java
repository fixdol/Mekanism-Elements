package fixdol.mekanismelements.common.recipe.serializer;

import mekanism.api.recipes.ingredients.ChemicalStackIngredient;
import net.minecraft.network.FriendlyByteBuf;
import mekanism.api.chemical.gas.GasStack;
import net.minecraft.util.GsonHelper;
import mekanism.api.recipes.ingredients.creator.IngredientCreatorAccess;
import mekanism.api.recipes.ingredients.ItemStackIngredient;
import mekanism.api.JsonConstants;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import mekanism.common.Mekanism;
import org.jetbrains.annotations.NotNull;
import fixdol.mekanismelements.api.recipes.RadiationIrradiatingRecipe;
import fixdol.mekanismelements.common.recipe.serializer.RadiationIrradiatorRecipeSerializer;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.resources.ResourceLocation;
import mekanism.api.SerializerHelper;


public class RadiationIrradiatorRecipeSerializer<RECIPE extends RadiationIrradiatingRecipe> implements RecipeSerializer<RECIPE> {

    private final IFactory<RECIPE> factory;

    public RadiationIrradiatorRecipeSerializer(IFactory<RECIPE> factory) {
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
        GasStack output = SerializerHelper.getGasStack(json, JsonConstants.OUTPUT);
        if (output.isEmpty()) {
            throw new JsonSyntaxException("Recipe output must not be empty.");
        }
        return this.factory.create(recipeId, itemInput, gasInput, output);
    }

    @Override
    public RECIPE fromNetwork(@NotNull ResourceLocation recipeId, @NotNull FriendlyByteBuf buffer) {
        try {
            ItemStackIngredient itemInput = IngredientCreatorAccess.item().read(buffer);
            ChemicalStackIngredient.GasStackIngredient gasInput = IngredientCreatorAccess.gas().read(buffer);
            GasStack output = GasStack.readFromPacket(buffer);
            return this.factory.create(recipeId, itemInput, gasInput, output);
        } catch (Exception e) {
            Mekanism.logger.error("Error reading radiation irradiating recipe from packet.", e);
            throw e;
        }
    }

    @Override
    public void toNetwork(@NotNull FriendlyByteBuf buffer, @NotNull RECIPE recipe) {
        try {
            recipe.write(buffer);
        } catch (Exception e) {
            Mekanism.logger.error("Error writing radiation irradiating recipe to packet.", e);
            throw e;
        }
    }

    @FunctionalInterface
    public interface IFactory<RECIPE extends RadiationIrradiatingRecipe> {
        RECIPE create(ResourceLocation id, ItemStackIngredient itemInput, ChemicalStackIngredient.GasStackIngredient gasInput, GasStack output);
    }
}
