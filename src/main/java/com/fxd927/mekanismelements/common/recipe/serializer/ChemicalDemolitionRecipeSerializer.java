package com.fxd927.mekanismelements.common.recipe.serializer;

import com.fxd927.mekanismelements.api.recipes.ChemicalDemolitionRecipe;
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

public class ChemicalDemolitionRecipeSerializer<RECIPE extends ChemicalDemolitionRecipe> implements RecipeSerializer<RECIPE> {
    private final ChemicalDemolitionRecipeSerializer.IFactory<RECIPE> factory;
    private final MapCodec<RECIPE> codec;
    private final StreamCodec<RegistryFriendlyByteBuf, RECIPE> streamCodec;

    public ChemicalDemolitionRecipeSerializer(ChemicalDemolitionRecipeSerializer.IFactory<RECIPE> factory) {
        this.factory = factory;
        this.codec = RecordCodecBuilder.mapCodec(instance -> instance.group(
                ItemStackIngredient.CODEC.fieldOf(SerializationConstants.ITEM_INPUT).forGetter(ChemicalDemolitionRecipe::getItemInput),
                ChemicalStackIngredient.CODEC.fieldOf(SerializationConstants.CHEMICAL_INPUT).forGetter(ChemicalDemolitionRecipe::getGasInput),
                ItemStack.CODEC.fieldOf(SerializationConstants.MAIN_OUTPUT).forGetter(recipe -> recipe.getFirstOutput(ItemStack.EMPTY, ChemicalStack.EMPTY)),
                ItemStack.CODEC.fieldOf(SerializationConstants.SECONDARY_OUTPUT).forGetter(recipe -> recipe.getSecondOutput(ItemStack.EMPTY, ChemicalStack.EMPTY))
        ).apply(instance, factory::create));

        this.streamCodec = StreamCodec.composite(
                ItemStackIngredient.STREAM_CODEC, ChemicalDemolitionRecipe::getItemInput,
                ChemicalStackIngredient.STREAM_CODEC, ChemicalDemolitionRecipe::getGasInput,
                ItemStack.STREAM_CODEC, recipe -> recipe.getFirstOutput(ItemStack.EMPTY, ChemicalStack.EMPTY),
                ItemStack.STREAM_CODEC, recipe -> recipe.getSecondOutput(ItemStack.EMPTY, ChemicalStack.EMPTY),
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
    public interface IFactory<RECIPE extends ChemicalDemolitionRecipe> {
        RECIPE create(ItemStackIngredient itemInput, ChemicalStackIngredient gasInput, ItemStack firstOutput, ItemStack secondOutput);
    }
}

