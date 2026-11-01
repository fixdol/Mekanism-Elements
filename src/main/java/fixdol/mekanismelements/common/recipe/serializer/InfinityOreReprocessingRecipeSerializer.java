package fixdol.mekanismelements.common.recipe.serializer;

import com.mojang.serialization.codecs.RecordCodecBuilder;
import fixdol.mekanismelements.api.recipes.InfinityOreReprocessingRecipe;
import mekanism.api.SerializationConstants;
import mekanism.api.recipes.ingredients.ChemicalStackIngredient;
import mekanism.api.recipes.ingredients.ItemStackIngredient;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.RecipeSerializer;

public final class InfinityOreReprocessingRecipeSerializer {

    private InfinityOreReprocessingRecipeSerializer() {
    }

    public static <RECIPE extends InfinityOreReprocessingRecipe> RecipeSerializer<RECIPE> create(IFactory<RECIPE> factory) {
        return new RecipeSerializer<>(RecordCodecBuilder.<RECIPE>mapCodec(instance -> instance.group(
                ItemStackIngredient.CODEC.fieldOf(SerializationConstants.ITEM_INPUT).forGetter(InfinityOreReprocessingRecipe::getItemInput),
                ChemicalStackIngredient.CODEC.fieldOf(SerializationConstants.CHEMICAL_INPUT).forGetter(InfinityOreReprocessingRecipe::getChemicalInput),
                ItemStackTemplate.CODEC.fieldOf(SerializationConstants.OUTPUT).forGetter(InfinityOreReprocessingRecipe::getOutputRaw)
        ).apply(instance, factory::create)), StreamCodec.composite(
                ItemStackIngredient.STREAM_CODEC, InfinityOreReprocessingRecipe::getItemInput,
                ChemicalStackIngredient.STREAM_CODEC, InfinityOreReprocessingRecipe::getChemicalInput,
                ItemStackTemplate.STREAM_CODEC, InfinityOreReprocessingRecipe::getOutputRaw,
                factory::create
        ));
    }

    @FunctionalInterface
    public interface IFactory<RECIPE extends InfinityOreReprocessingRecipe> {
        RECIPE create(ItemStackIngredient itemInput, ChemicalStackIngredient chemicalInput, ItemStackTemplate output);
    }
}
