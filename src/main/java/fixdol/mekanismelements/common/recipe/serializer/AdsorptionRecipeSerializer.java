package fixdol.mekanismelements.common.recipe.serializer;

import fixdol.mekanismelements.api.recipes.AdsorptionRecipe;
import fixdol.mekanismelements.common.recipe.serializer.AdsorptionRecipeSerializer;
import mekanism.api.recipes.ingredients.FluidStackIngredient;
import net.minecraft.network.FriendlyByteBuf;
import mekanism.api.chemical.ChemicalStack;
import mekanism.api.chemical.ChemicalType;
import mekanism.api.chemical.gas.GasStack;
import mekanism.api.chemical.infuse.InfusionStack;
import mekanism.api.chemical.pigment.PigmentStack;
import mekanism.api.chemical.slurry.SlurryStack;
import mekanism.api.recipes.ingredients.creator.IngredientCreatorAccess;
import mekanism.api.recipes.ingredients.ItemStackIngredient;
import com.google.gson.JsonObject;
import org.jetbrains.annotations.NotNull;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.resources.ResourceLocation;

import mekanism.api.JsonConstants;
import mekanism.api.SerializerHelper;
import mekanism.common.Mekanism;
import net.minecraft.util.GsonHelper;

public class AdsorptionRecipeSerializer<RECIPE extends AdsorptionRecipe> implements RecipeSerializer<RECIPE> {

    private final IFactory<RECIPE> factory;

    public AdsorptionRecipeSerializer(IFactory<RECIPE> factory) {
        this.factory = factory;
    }

    @NotNull
    @Override
    public RECIPE fromJson(@NotNull ResourceLocation recipeId, @NotNull JsonObject json) {
        ItemStackIngredient itemInput = IngredientCreatorAccess.item().deserialize(GsonHelper.isArrayNode(json, JsonConstants.ITEM_INPUT)
              ? GsonHelper.getAsJsonArray(json, JsonConstants.ITEM_INPUT)
              : GsonHelper.getAsJsonObject(json, JsonConstants.ITEM_INPUT));
        FluidStackIngredient fluidInput = IngredientCreatorAccess.fluid().deserialize(GsonHelper.isArrayNode(json, JsonConstants.FLUID_INPUT)
              ? GsonHelper.getAsJsonArray(json, JsonConstants.FLUID_INPUT)
              : GsonHelper.getAsJsonObject(json, JsonConstants.FLUID_INPUT));
        ChemicalStack<?> output = SerializerHelper.getBoxedChemicalStack(json, JsonConstants.OUTPUT);
        if (output.isEmpty()) {
            throw new com.google.gson.JsonSyntaxException("Recipe output must not be empty.");
        }
        return this.factory.create(recipeId, itemInput, fluidInput, output);
    }

    @Override
    public RECIPE fromNetwork(@NotNull ResourceLocation recipeId, @NotNull FriendlyByteBuf buffer) {
        try {
            ItemStackIngredient itemInput = IngredientCreatorAccess.item().read(buffer);
            FluidStackIngredient fluidInput = IngredientCreatorAccess.fluid().read(buffer);
            ChemicalType chemicalType = buffer.readEnum(ChemicalType.class);
            ChemicalStack<?> output = switch (chemicalType) {
                case GAS -> GasStack.readFromPacket(buffer);
                case INFUSION -> InfusionStack.readFromPacket(buffer);
                case PIGMENT -> PigmentStack.readFromPacket(buffer);
                case SLURRY -> SlurryStack.readFromPacket(buffer);
            };
            return this.factory.create(recipeId, itemInput, fluidInput, output);
        } catch (Exception e) {
            Mekanism.logger.error("Error reading adsorption recipe from packet.", e);
            throw e;
        }
    }

    @Override
    public void toNetwork(@NotNull FriendlyByteBuf buffer, @NotNull RECIPE recipe) {
        try {
            recipe.write(buffer);
        } catch (Exception e) {
            Mekanism.logger.error("Error writing adsorption recipe to packet.", e);
            throw e;
        }
    }

    @FunctionalInterface
    public interface IFactory<RECIPE extends AdsorptionRecipe> {
        RECIPE create(ResourceLocation id, ItemStackIngredient itemInput, FluidStackIngredient fluidInput, ChemicalStack<?> output);
    }
}
