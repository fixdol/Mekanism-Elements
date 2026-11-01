package fixdol.mekanismelements.common.recipe.impl;

import fixdol.mekanismelements.api.recipes.AdsorptionRecipe;
import fixdol.mekanismelements.common.recipe.MSRecipeType;
import fixdol.mekanismelements.common.registries.MSRecipeSerializers;
import mekanism.api.chemical.ChemicalStackTemplate;
import mekanism.api.recipes.ingredients.FluidStackIngredient;
import mekanism.api.recipes.ingredients.ItemStackIngredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;

public class AdsorptionIRecipe extends AdsorptionRecipe {

    public AdsorptionIRecipe(ItemStackIngredient itemInput, FluidStackIngredient fluidInput, ChemicalStackTemplate output) {
        super(itemInput, fluidInput, output);
    }

    @Override
    public RecipeType<AdsorptionRecipe> getType() {
        return MSRecipeType.ADSORPTION.get();
    }

    @Override
    public RecipeSerializer<AdsorptionRecipe> getSerializer() {
        return MSRecipeSerializers.ADSORPTION_SEPARATOR.get();
    }
}
