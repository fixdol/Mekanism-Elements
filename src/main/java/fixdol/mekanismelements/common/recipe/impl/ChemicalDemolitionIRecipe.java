package fixdol.mekanismelements.common.recipe.impl;

import fixdol.mekanismelements.api.recipes.ChemicalDemolitionRecipe;
import fixdol.mekanismelements.common.recipe.MSRecipeType;
import fixdol.mekanismelements.common.registries.MSBlocks;
import fixdol.mekanismelements.common.registries.MSRecipeSerializers;
import mekanism.api.recipes.ingredients.ChemicalStackIngredient;
import mekanism.api.recipes.ingredients.ItemStackIngredient;
import mekanism.api.recipes.vanilla_input.ItemChemicalRecipeInput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

public class ChemicalDemolitionIRecipe extends ChemicalDemolitionRecipe {

    public ChemicalDemolitionIRecipe(ItemStackIngredient itemInput, ChemicalStackIngredient fluidInput, ItemStack firstOutput, ItemStack secondOutput) {
        super(itemInput, fluidInput, firstOutput, secondOutput);
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
        return new ItemStack(MSBlocks.ADSORPTION_SEPARATOR.asItem());
    }

    @Override
    public boolean matches(ItemChemicalRecipeInput input, Level level) {
        if (isIncomplete()) {
            return false;
        }
        return this.test(input.getItem(0), input.getChemical(0));
    }
}

