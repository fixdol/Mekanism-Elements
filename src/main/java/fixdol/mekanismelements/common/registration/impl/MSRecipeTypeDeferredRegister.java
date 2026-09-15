package fixdol.mekanismelements.common.registration.impl;

import fixdol.mekanismelements.common.recipe.IMSRecipeTypeProvider;
import fixdol.mekanismelements.common.recipe.MSRecipeType;
import mekanism.api.recipes.MekanismRecipe;
import mekanism.common.recipe.lookup.cache.IInputRecipeCache;
import mekanism.common.registration.WrappedDeferredRegister;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

public class MSRecipeTypeDeferredRegister extends WrappedDeferredRegister<RecipeType<?>> {

    private final List<IMSRecipeTypeProvider<?, ?>> recipeTypes = new ArrayList<>();

    public MSRecipeTypeDeferredRegister(String modid) {
        super(modid, Registries.RECIPE_TYPE);
    }

    public <RECIPE extends MekanismRecipe, MS_INPUT_CACHE extends IInputRecipeCache> MSRecipeTypeRegistryObject<RECIPE, MS_INPUT_CACHE> registerRecipeType(
            String name, Supplier<? extends MSRecipeType<RECIPE, MS_INPUT_CACHE>> sup) {
        MSRecipeTypeRegistryObject<RECIPE, MS_INPUT_CACHE> registeredRecipeType = register(name, sup, MSRecipeTypeRegistryObject::new);
        recipeTypes.add(registeredRecipeType);
        return registeredRecipeType;
    }

    public List<IMSRecipeTypeProvider<?, ?>> getAllRecipeTypes() {
        return Collections.unmodifiableList(recipeTypes);
    }
}
