package fixdol.mekanismelements.common.recipe.impl;

import fixdol.mekanismelements.api.recipes.ChemicalDemolitionRecipe;
import fixdol.mekanismelements.common.recipe.MSRecipeType;
import fixdol.mekanismelements.common.registries.MSRecipeSerializers;
import mekanism.api.recipes.ingredients.ChemicalStackIngredient;
import mekanism.api.recipes.ingredients.ItemStackIngredient;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;

public class ChemicalDemolitionIRecipe extends ChemicalDemolitionRecipe {

    public ChemicalDemolitionIRecipe(ItemStackIngredient itemInput, ChemicalStackIngredient gasInput, ItemStackTemplate firstOutput, ItemStackTemplate secondOutput) {
        super(itemInput, gasInput, firstOutput, secondOutput);
    }

    @Override
    public RecipeType<ChemicalDemolitionRecipe> getType() {
        return MSRecipeType.CHEMICAL_DEMOLITION.get();
    }

    @Override
    public RecipeSerializer<ChemicalDemolitionRecipe> getSerializer() {
        return MSRecipeSerializers.CHEMICAL_DEMOLITION.get();
    }
}
