package com.fxd927.mekanismelements.common.recipe.impl;

import com.fxd927.mekanismelements.api.recipes.RadiationIrradiatingRecipe;
import com.fxd927.mekanismelements.common.recipe.MSRecipeType;
import com.fxd927.mekanismelements.common.registries.MSBlocks;
import com.fxd927.mekanismelements.common.registries.MSRecipeSerializers;
import mekanism.api.chemical.ChemicalStack;
import mekanism.api.recipes.ingredients.ChemicalStackIngredient;
import mekanism.api.recipes.ingredients.ItemStackIngredient;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;

public class RadiationIrradiatingIRecipe extends RadiationIrradiatingRecipe {
    public RadiationIrradiatingIRecipe(ResourceLocation id, ItemStackIngredient itemInput, ChemicalStackIngredient.GasStackIngredient gasInput, ChemicalStack<?> output) {
        super(id, itemInput, gasInput, output);
    }

    @Override
    public RecipeType<RadiationIrradiatingRecipe> getType() {
        return MSRecipeType.RADIATION_IRRADIATING.get();
    }

    @Override
    public RecipeSerializer<RadiationIrradiatingRecipe> getSerializer() {
        return MSRecipeSerializers.RADIATION_IRRADIATOR.get();
    }

    @Override
    public String getGroup() {
        return MSBlocks.RADIATION_IRRADIATOR.getName();
    }

    @Override
    public ItemStack getToastSymbol() {
        return MSBlocks.RADIATION_IRRADIATOR.getItemStack();
    }
}
