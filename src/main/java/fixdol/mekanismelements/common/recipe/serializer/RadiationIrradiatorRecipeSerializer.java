package fixdol.mekanismelements.common.recipe.serializer;

import com.mojang.serialization.codecs.RecordCodecBuilder;
import fixdol.mekanismelements.api.recipes.RadiationIrradiatingRecipe;
import mekanism.api.SerializationConstants;
import mekanism.api.chemical.ChemicalStackTemplate;
import mekanism.api.recipes.ingredients.ChemicalStackIngredient;
import mekanism.api.recipes.ingredients.ItemStackIngredient;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.crafting.RecipeSerializer;

public final class RadiationIrradiatorRecipeSerializer {

    private RadiationIrradiatorRecipeSerializer() {
    }

    public static <RECIPE extends RadiationIrradiatingRecipe> RecipeSerializer<RECIPE> create(IFactory<RECIPE> factory) {
        return new RecipeSerializer<>(RecordCodecBuilder.<RECIPE>mapCodec(instance -> instance.group(
                ItemStackIngredient.CODEC.fieldOf(SerializationConstants.ITEM_INPUT).forGetter(RadiationIrradiatingRecipe::getItemInput),
                ChemicalStackIngredient.CODEC.fieldOf(SerializationConstants.CHEMICAL_INPUT).forGetter(RadiationIrradiatingRecipe::getGasInput),
                ChemicalStackTemplate.CODEC.fieldOf(SerializationConstants.OUTPUT).forGetter(RadiationIrradiatingRecipe::getOutputRaw)
        ).apply(instance, factory::create)), StreamCodec.composite(
                ItemStackIngredient.STREAM_CODEC, RadiationIrradiatingRecipe::getItemInput,
                ChemicalStackIngredient.STREAM_CODEC, RadiationIrradiatingRecipe::getGasInput,
                ChemicalStackTemplate.STREAM_CODEC, RadiationIrradiatingRecipe::getOutputRaw,
                factory::create
        ));
    }

    @FunctionalInterface
    public interface IFactory<RECIPE extends RadiationIrradiatingRecipe> {
        RECIPE create(ItemStackIngredient itemInput, ChemicalStackIngredient gasInput, ChemicalStackTemplate output);
    }
}
