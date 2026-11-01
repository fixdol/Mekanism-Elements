package fixdol.mekanismelements.common.recipe.impl;

import fixdol.mekanismelements.api.recipes.RadiationIrradiatingRecipe;
import fixdol.mekanismelements.common.recipe.MSRecipeType;
import fixdol.mekanismelements.common.registries.MSRecipeSerializers;
import mekanism.api.chemical.ChemicalStackTemplate;
import mekanism.api.recipes.ingredients.ChemicalStackIngredient;
import mekanism.api.recipes.ingredients.ItemStackIngredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;

public class RadiationIrradiatingIRecipe extends RadiationIrradiatingRecipe {

    public RadiationIrradiatingIRecipe(ItemStackIngredient itemInput, ChemicalStackIngredient gasInput, ChemicalStackTemplate output) {
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
}
