package fixdol.mekanismelements.common.recipe.serializer;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import fixdol.mekanismelements.api.recipes.AdsorptionRecipe;
import mekanism.api.SerializationConstants;
import mekanism.api.chemical.ChemicalStackTemplate;
import mekanism.api.recipes.ingredients.FluidStackIngredient;
import mekanism.api.recipes.ingredients.ItemStackIngredient;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.crafting.RecipeSerializer;

public final class AdsorptionRecipeSerializer {

    private AdsorptionRecipeSerializer() {
    }

    public static <RECIPE extends AdsorptionRecipe> RecipeSerializer<RECIPE> create(IFactory<RECIPE> factory) {
        return new RecipeSerializer<>(RecordCodecBuilder.<RECIPE>mapCodec(instance -> instance.group(
                ItemStackIngredient.CODEC.fieldOf(SerializationConstants.ITEM_INPUT).forGetter(AdsorptionRecipe::getItemInput),
                FluidStackIngredient.CODEC.fieldOf(SerializationConstants.FLUID_INPUT).forGetter(AdsorptionRecipe::getFluidInput),
                ChemicalStackTemplate.CODEC.fieldOf(SerializationConstants.OUTPUT).forGetter(AdsorptionRecipe::getOutputRaw)
        ).apply(instance, factory::create)), StreamCodec.composite(
                ItemStackIngredient.STREAM_CODEC, AdsorptionRecipe::getItemInput,
                FluidStackIngredient.STREAM_CODEC, AdsorptionRecipe::getFluidInput,
                ChemicalStackTemplate.STREAM_CODEC, AdsorptionRecipe::getOutputRaw,
                factory::create
        ));
    }

    @FunctionalInterface
    public interface IFactory<RECIPE extends AdsorptionRecipe> {
        RECIPE create(ItemStackIngredient itemInput, FluidStackIngredient fluidInput, ChemicalStackTemplate output);
    }
}
