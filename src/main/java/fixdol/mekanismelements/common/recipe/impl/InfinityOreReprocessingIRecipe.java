package fixdol.mekanismelements.common.recipe.impl;

import mekanism.api.recipes.ingredients.ChemicalStackIngredient;
import fixdol.mekanismelements.common.recipe.impl.InfinityOreReprocessingIRecipe;
import fixdol.mekanismelements.api.recipes.InfinityOreReprocessingRecipe;
import net.minecraft.world.item.ItemStack;
import mekanism.api.recipes.ingredients.ItemStackIngredient;
import fixdol.mekanismelements.common.registries.MSRecipeSerializers;
import fixdol.mekanismelements.common.recipe.MSRecipeType;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.resources.ResourceLocation;


public class InfinityOreReprocessingIRecipe extends InfinityOreReprocessingRecipe {

    public InfinityOreReprocessingIRecipe(ResourceLocation id, ItemStackIngredient itemInput, ChemicalStackIngredient.GasStackIngredient chemicalInput, ItemStack output) {
        super(id, itemInput, chemicalInput, output);
    }

    @Override
    @SuppressWarnings("unchecked")
    public RecipeType<InfinityOreReprocessingRecipe> getType() {
        return (RecipeType<InfinityOreReprocessingRecipe>) (RecipeType<?>) MSRecipeType.INFINITY_ORE_REPROCESSING.get();
    }

    @Override
    @SuppressWarnings("unchecked")
    public RecipeSerializer<InfinityOreReprocessingIRecipe> getSerializer() {
        return (RecipeSerializer<InfinityOreReprocessingIRecipe>) (RecipeSerializer<?>) MSRecipeSerializers.INFINITY_ORE_REPROCESSING.get();
    }
}
