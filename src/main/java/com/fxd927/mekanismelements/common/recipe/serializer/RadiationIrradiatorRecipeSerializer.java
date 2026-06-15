package com.fxd927.mekanismelements.common.recipe.serializer;

import com.fxd927.mekanismelements.api.recipes.RadiationIrradiatingRecipe;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import mekanism.api.SerializationConstants;
import mekanism.api.chemical.ChemicalStack;
import mekanism.api.recipes.ingredients.ChemicalStackIngredient;
import mekanism.api.recipes.ingredients.ItemStackIngredient;
import mekanism.common.Mekanism;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import org.jetbrains.annotations.NotNull;

public class RadiationIrradiatorRecipeSerializer<RECIPE extends RadiationIrradiatingRecipe> implements RecipeSerializer<RECIPE> {
    private final RadiationIrradiatorRecipeSerializer.IFactory<RECIPE> factory;
    private final MapCodec<RECIPE> codec;
    private final StreamCodec<RegistryFriendlyByteBuf, RECIPE> streamCodec;

    public RadiationIrradiatorRecipeSerializer(RadiationIrradiatorRecipeSerializer.IFactory<RECIPE> factory) {
        this.factory = factory;
        this.codec = RecordCodecBuilder.mapCodec(instance -> instance.group(
                ItemStackIngredient.CODEC.fieldOf(SerializationConstants.ITEM_INPUT).forGetter(RadiationIrradiatingRecipe::getItemInput),
                ChemicalStackIngredient.CODEC.fieldOf(SerializationConstants.CHEMICAL_INPUT).forGetter(RadiationIrradiatingRecipe::getGasInput),
                ChemicalStack.CODEC.fieldOf(SerializationConstants.OUTPUT).forGetter(recipe -> recipe.getOutput(ItemStack.EMPTY, ChemicalStack.EMPTY))
        ).apply(instance, factory::create));

        this.streamCodec = StreamCodec.composite(
                ItemStackIngredient.STREAM_CODEC, RadiationIrradiatingRecipe::getItemInput,
                ChemicalStackIngredient.STREAM_CODEC, RadiationIrradiatingRecipe::getGasInput,
                ChemicalStack.STREAM_CODEC, recipe -> recipe.getOutput(ItemStack.EMPTY, ChemicalStack.EMPTY),
                factory::create
        );
    }

    @Override
    @NotNull
    public MapCodec<RECIPE> codec() {
        return this.codec;
    }

    @Override
    @NotNull
    public StreamCodec<RegistryFriendlyByteBuf, RECIPE> streamCodec() {
        return this.streamCodec;
    }

    @FunctionalInterface
    public interface IFactory<RECIPE extends RadiationIrradiatingRecipe> {
        RECIPE create(ItemStackIngredient itemInput, ChemicalStackIngredient gasInput, ChemicalStack output);
    }
}

