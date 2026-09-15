package fixdol.mekanismelements.common.recipe.impl;

import fixdol.mekanismelements.common.recipe.impl.AdsorptionIRecipe;
import fixdol.mekanismelements.api.recipes.AdsorptionRecipe;
import mekanism.api.recipes.ingredients.FluidStackIngredient;
import mekanism.api.chemical.ChemicalStack;
import mekanism.api.recipes.ingredients.ItemStackIngredient;
import fixdol.mekanismelements.common.registries.MSRecipeSerializers;
import fixdol.mekanismelements.common.recipe.MSRecipeType;
import net.minecraft.resources.ResourceLocation;

import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;

public class AdsorptionIRecipe extends AdsorptionRecipe {

    public AdsorptionIRecipe(ResourceLocation id, ItemStackIngredient itemInput, FluidStackIngredient fluidInput, ChemicalStack<?> output) {
        super(id, itemInput, fluidInput, output);
    }

    @Override
    @SuppressWarnings("unchecked")
    public RecipeType<AdsorptionRecipe> getType() {
        return (RecipeType<AdsorptionRecipe>) (RecipeType<?>) MSRecipeType.ADSORPTION.get();
    }

    @Override
    @SuppressWarnings("unchecked")
    public RecipeSerializer<AdsorptionIRecipe> getSerializer() {
        return (RecipeSerializer<AdsorptionIRecipe>) (RecipeSerializer<?>) MSRecipeSerializers.ADSORPTION_SEPARATOR.get();
    }
}
