package fixdol.mekanismelements.common.recipe.serializer;

import com.mojang.serialization.codecs.RecordCodecBuilder;
import fixdol.mekanismelements.api.recipes.ChemicalDemolitionRecipe;
import mekanism.api.SerializationConstants;
import mekanism.api.recipes.ingredients.ChemicalStackIngredient;
import mekanism.api.recipes.ingredients.ItemStackIngredient;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.RecipeSerializer;

public final class ChemicalDemolitionRecipeSerializer {

    private ChemicalDemolitionRecipeSerializer() {
    }

    public static <RECIPE extends ChemicalDemolitionRecipe> RecipeSerializer<RECIPE> create(IFactory<RECIPE> factory) {
        return new RecipeSerializer<>(RecordCodecBuilder.<RECIPE>mapCodec(instance -> instance.group(
                ItemStackIngredient.CODEC.fieldOf(SerializationConstants.ITEM_INPUT).forGetter(ChemicalDemolitionRecipe::getItemInput),
                ChemicalStackIngredient.CODEC.fieldOf(SerializationConstants.CHEMICAL_INPUT).forGetter(ChemicalDemolitionRecipe::getGasInput),
                ItemStackTemplate.CODEC.fieldOf(SerializationConstants.MAIN_OUTPUT).forGetter(ChemicalDemolitionRecipe::getFirstOutputRaw),
                ItemStackTemplate.CODEC.fieldOf(SerializationConstants.SECONDARY_OUTPUT).forGetter(ChemicalDemolitionRecipe::getSecondOutputRaw)
        ).apply(instance, factory::create)), StreamCodec.composite(
                ItemStackIngredient.STREAM_CODEC, ChemicalDemolitionRecipe::getItemInput,
                ChemicalStackIngredient.STREAM_CODEC, ChemicalDemolitionRecipe::getGasInput,
                ItemStackTemplate.STREAM_CODEC, ChemicalDemolitionRecipe::getFirstOutputRaw,
                ItemStackTemplate.STREAM_CODEC, ChemicalDemolitionRecipe::getSecondOutputRaw,
                factory::create
        ));
    }

    @FunctionalInterface
    public interface IFactory<RECIPE extends ChemicalDemolitionRecipe> {
        RECIPE create(ItemStackIngredient itemInput, ChemicalStackIngredient gasInput, ItemStackTemplate firstOutput, ItemStackTemplate secondOutput);
    }
}
