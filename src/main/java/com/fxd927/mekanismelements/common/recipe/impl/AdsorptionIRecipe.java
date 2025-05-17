package com.fxd927.mekanismelements.common.recipe.impl;

import com.fxd927.mekanismelements.api.recipes.AdsorptionRecipe;
import com.fxd927.mekanismelements.common.recipe.MSRecipeType;
import com.fxd927.mekanismelements.common.registries.MSBlocks;
import com.fxd927.mekanismelements.common.registries.MSRecipeSerializers;
import mekanism.api.chemical.ChemicalStack;
import mekanism.api.recipes.ingredients.FluidStackIngredient;
import mekanism.api.recipes.ingredients.ItemStackIngredient;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;

public class AdsorptionIRecipe extends AdsorptionRecipe {
    public AdsorptionIRecipe(ResourceLocation id, ItemStackIngredient itemInput, FluidStackIngredient fluidInput, ChemicalStack<?> output) {
        super(id, itemInput, fluidInput, output);
    }

    @Override
    public RecipeType<AdsorptionRecipe> getType() {
        return MSRecipeType.ADSORPTION.get();
    }

    @Override
    public RecipeSerializer<AdsorptionRecipe> getSerializer() {
        return MSRecipeSerializers.ADSORPTION_SEPARATOR.get();
    }

    @Override
    public String getGroup() {
        return MSBlocks.ADSORPTION_SEPARATOR.getName();
    }

    @Override
    public ItemStack getToastSymbol() {
        return MSBlocks.ADSORPTION_SEPARATOR.getItemStack();
    }
}
