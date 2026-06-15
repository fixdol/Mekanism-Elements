package com.fxd927.mekanismelements.common.recipe.impl;

import com.fxd927.mekanismelements.api.recipes.RadiationIrradiatingRecipe;
import com.fxd927.mekanismelements.common.recipe.MSRecipeType;
import com.fxd927.mekanismelements.common.registries.MSBlocks;
import com.fxd927.mekanismelements.common.registries.MSRecipeSerializers;
import mekanism.api.chemical.ChemicalStack;
import mekanism.api.recipes.ingredients.ChemicalStackIngredient;
import mekanism.api.recipes.ingredients.ItemStackIngredient;
import mekanism.api.recipes.vanilla_input.ItemChemicalRecipeInput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

public class RadiationIrradiatingIRecipe extends RadiationIrradiatingRecipe {

    public RadiationIrradiatingIRecipe(ItemStackIngredient itemInput, ChemicalStackIngredient gasInput, ChemicalStack output) {
        super(itemInput, gasInput, output);
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
        return new ItemStack(MSBlocks.RADIATION_IRRADIATOR.asItem());
    }

    @Override
    public boolean matches(ItemChemicalRecipeInput input, Level level) {
        if (isIncomplete()) {
            return false;
        }
        boolean itemMatch = this.test(input.getItem(0), input.getChemical(0));
        if (level.getGameTime() % 100 == 0) { // Log occasionally to avoid spam
             com.fxd927.mekanismelements.common.MekanismElements.logger.info("DEBUG: RadiationIrradiatingIRecipe.matches? {} | Item: {} | Chemical: {}", itemMatch, input.getItem(0), input.getChemical(0).getChemical());
        }
        return itemMatch;
    }
}

