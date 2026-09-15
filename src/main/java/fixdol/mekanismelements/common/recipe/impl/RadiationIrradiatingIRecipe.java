package fixdol.mekanismelements.common.recipe.impl;

import mekanism.api.recipes.ingredients.ChemicalStackIngredient;
import mekanism.api.chemical.gas.GasStack;
import mekanism.api.recipes.ingredients.ItemStackIngredient;
import fixdol.mekanismelements.common.registries.MSRecipeSerializers;
import fixdol.mekanismelements.common.recipe.MSRecipeType;
import fixdol.mekanismelements.common.recipe.impl.RadiationIrradiatingIRecipe;
import fixdol.mekanismelements.api.recipes.RadiationIrradiatingRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.resources.ResourceLocation;


public class RadiationIrradiatingIRecipe extends RadiationIrradiatingRecipe {

    public RadiationIrradiatingIRecipe(ResourceLocation id, ItemStackIngredient itemInput, ChemicalStackIngredient.GasStackIngredient gasInput, GasStack output) {
        super(id, itemInput, gasInput, output);
    }

    @Override
    @SuppressWarnings("unchecked")
    public RecipeType<RadiationIrradiatingRecipe> getType() {
        return (RecipeType<RadiationIrradiatingRecipe>) (RecipeType<?>) MSRecipeType.RADIATION_IRRADIATING.get();
    }

    @Override
    @SuppressWarnings("unchecked")
    public RecipeSerializer<RadiationIrradiatingIRecipe> getSerializer() {
        return (RecipeSerializer<RadiationIrradiatingIRecipe>) (RecipeSerializer<?>) MSRecipeSerializers.RADIATION_IRRADIATOR.get();
    }
}
