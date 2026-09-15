package fixdol.mekanismelements.common.recipe.impl;

import fixdol.mekanismelements.common.recipe.impl.ChemicalDemolitionIRecipe;
import fixdol.mekanismelements.api.recipes.ChemicalDemolitionRecipe;
import mekanism.api.recipes.ingredients.ChemicalStackIngredient;
import net.minecraft.world.item.ItemStack;
import mekanism.api.recipes.ingredients.ItemStackIngredient;
import fixdol.mekanismelements.common.registries.MSRecipeSerializers;
import fixdol.mekanismelements.common.recipe.MSRecipeType;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.resources.ResourceLocation;


public class ChemicalDemolitionIRecipe extends ChemicalDemolitionRecipe {

    public ChemicalDemolitionIRecipe(ResourceLocation id, ItemStackIngredient itemInput, ChemicalStackIngredient.GasStackIngredient gasInput, ItemStack firstOutput, ItemStack secondOutput) {
        super(id, itemInput, gasInput, firstOutput, secondOutput);
    }

    @Override
    @SuppressWarnings("unchecked")
    public RecipeType<ChemicalDemolitionRecipe> getType() {
        return (RecipeType<ChemicalDemolitionRecipe>) (RecipeType<?>) MSRecipeType.CHEMICAL_DEMOLITION.get();
    }

    @Override
    @SuppressWarnings("unchecked")
    public RecipeSerializer<ChemicalDemolitionIRecipe> getSerializer() {
        return (RecipeSerializer<ChemicalDemolitionIRecipe>) (RecipeSerializer<?>) MSRecipeSerializers.CHEMICAL_DEMOLITION.get();
    }
}
