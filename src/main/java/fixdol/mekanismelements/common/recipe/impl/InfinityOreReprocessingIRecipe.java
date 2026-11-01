package fixdol.mekanismelements.common.recipe.impl;

import fixdol.mekanismelements.api.recipes.InfinityOreReprocessingRecipe;
import fixdol.mekanismelements.common.recipe.MSRecipeType;
import fixdol.mekanismelements.common.registries.MSRecipeSerializers;
import mekanism.api.recipes.ingredients.ChemicalStackIngredient;
import mekanism.api.recipes.ingredients.ItemStackIngredient;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;

public class InfinityOreReprocessingIRecipe extends InfinityOreReprocessingRecipe {

    public InfinityOreReprocessingIRecipe(ItemStackIngredient itemInput, ChemicalStackIngredient chemicalInput, ItemStackTemplate output) {
        super(itemInput, chemicalInput, output);
    }

    @Override
    public RecipeType<InfinityOreReprocessingRecipe> getType() {
        return MSRecipeType.INFINITY_ORE_REPROCESSING.get();
    }

    @Override
    public RecipeSerializer<InfinityOreReprocessingRecipe> getSerializer() {
        return MSRecipeSerializers.INFINITY_ORE_REPROCESSING.get();
    }
}
