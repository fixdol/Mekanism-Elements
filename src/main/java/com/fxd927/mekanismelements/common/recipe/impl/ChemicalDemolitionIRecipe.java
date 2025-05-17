package com.fxd927.mekanismelements.common.recipe.impl;

import com.fxd927.mekanismelements.api.recipes.ChemicalDemolitionRecipe;
import com.fxd927.mekanismelements.common.recipe.MSRecipeType;
import com.fxd927.mekanismelements.common.registries.MSBlocks;
import com.fxd927.mekanismelements.common.registries.MSRecipeSerializers;
import mekanism.api.recipes.ingredients.ChemicalStackIngredient;
import mekanism.api.recipes.ingredients.ItemStackIngredient;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;

public class ChemicalDemolitionIRecipe extends ChemicalDemolitionRecipe {
    public ChemicalDemolitionIRecipe(ResourceLocation id, ItemStackIngredient itemInput, ChemicalStackIngredient.GasStackIngredient fluidInput, ItemStack firstOutput, ItemStack secondOutput) {
        super(id, itemInput, fluidInput, firstOutput, secondOutput);
    }

    @Override
    public RecipeType<ChemicalDemolitionRecipe> getType() {
        return MSRecipeType.CHEMICAL_DEMOLITION.get();
    }

    @Override
    public RecipeSerializer<ChemicalDemolitionRecipe> getSerializer() {
        return MSRecipeSerializers.CHEMICAL_DEMOLITION.get();
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
