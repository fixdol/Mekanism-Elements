package com.fxd927.mekanismelements.common.recipe.impl;

import com.fxd927.mekanismelements.api.recipes.AdsorptionRecipe;
import com.fxd927.mekanismelements.common.recipe.MSRecipeType;
import com.fxd927.mekanismelements.common.registries.MSBlocks;
import com.fxd927.mekanismelements.common.registries.MSRecipeSerializers;
import mekanism.api.chemical.ChemicalStack;
import mekanism.api.recipes.ingredients.FluidStackIngredient;
import mekanism.api.recipes.ingredients.ItemStackIngredient;
import mekanism.api.recipes.vanilla_input.FluidRecipeInput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

public class AdsorptionIRecipe extends AdsorptionRecipe {

    public AdsorptionIRecipe(ItemStackIngredient itemInput, FluidStackIngredient fluidInput, ChemicalStack output) {
        super(itemInput, fluidInput, output);
        com.fxd927.mekanismelements.common.MekanismElements.logger.info("DEBUG: AdsorptionIRecipe CREATED: {}", output);
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
        return new ItemStack(MSBlocks.ADSORPTION_SEPARATOR.asItem());
    }

    @Override
    public boolean matches(FluidRecipeInput input, Level level) {
        if (isIncomplete()) {
            return false;
        }
        boolean itemMatch = this.test(input.getItem(0), input.getFluid(0));
        if (level.getGameTime() % 100 == 0) { // Log occasionally to avoid spam
             com.fxd927.mekanismelements.common.MekanismElements.logger.info("DEBUG: AdsorptionIRecipe.matches? {} | Item: {} | Fluid: {}", itemMatch, input.getItem(0), input.getFluid(0).getFluid());
        }
        return itemMatch;
    }
}

