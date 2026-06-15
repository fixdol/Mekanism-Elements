package com.fxd927.mekanismelements.common.recipe.serializer;

import com.fxd927.mekanismelements.api.recipes.AdsorptionRecipe;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import mekanism.api.SerializationConstants;
import mekanism.api.chemical.ChemicalStack;
import mekanism.api.recipes.ingredients.FluidStackIngredient;
import mekanism.api.recipes.ingredients.ItemStackIngredient;
import mekanism.common.Mekanism;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.neoforged.neoforge.fluids.FluidStack;
import org.jetbrains.annotations.NotNull;

public class AdsorptionRecipeSerializer<RECIPE extends AdsorptionRecipe> implements RecipeSerializer<RECIPE> {
    private final AdsorptionRecipeSerializer.IFactory<RECIPE> factory;
    private final MapCodec<RECIPE> codec;
    private final StreamCodec<RegistryFriendlyByteBuf, RECIPE> streamCodec;

    public AdsorptionRecipeSerializer(AdsorptionRecipeSerializer.IFactory<RECIPE> factory) {
        this.factory = factory;
        this.codec = RecordCodecBuilder.mapCodec(instance -> instance.group(
                ItemStackIngredient.CODEC.fieldOf(SerializationConstants.ITEM_INPUT).forGetter(AdsorptionRecipe::getItemInput),
                FluidStackIngredient.CODEC.fieldOf(SerializationConstants.FLUID_INPUT).forGetter(AdsorptionRecipe::getFluidInput),
                ChemicalStack.CODEC.fieldOf(SerializationConstants.OUTPUT).forGetter(recipe -> recipe.getOutput(ItemStack.EMPTY, FluidStack.EMPTY))
        ).apply(instance, factory::create));
        
        this.streamCodec = StreamCodec.composite(
                ItemStackIngredient.STREAM_CODEC, AdsorptionRecipe::getItemInput,
                FluidStackIngredient.STREAM_CODEC, AdsorptionRecipe::getFluidInput,
                ChemicalStack.STREAM_CODEC, recipe -> recipe.getOutput(ItemStack.EMPTY, FluidStack.EMPTY),
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
    public interface IFactory<RECIPE extends AdsorptionRecipe> {
        RECIPE create(ItemStackIngredient itemInput, FluidStackIngredient fluidInput, ChemicalStack output);
    }
}

