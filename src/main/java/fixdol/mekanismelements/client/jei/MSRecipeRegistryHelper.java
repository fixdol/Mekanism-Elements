package fixdol.mekanismelements.client.jei;

import java.util.List;
import fixdol.mekanismelements.client.jei.MSRecipeRegistryHelper;
import mekanism.client.jei.MekanismJEI;
import mekanism.client.jei.MekanismJEIRecipeType;
import mekanism.api.recipes.MekanismRecipe;

import fixdol.mekanismelements.common.recipe.IMSRecipeTypeProvider;
import mekanism.client.MekanismClient;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;


public class MSRecipeRegistryHelper {
    private MSRecipeRegistryHelper() {
    }

    public static <RECIPE extends MekanismRecipe> void register(IRecipeRegistration registry, MekanismJEIRecipeType<RECIPE> recipeType,
                                                                IMSRecipeTypeProvider<RECIPE, ?> type) {
        ClientLevel world = Minecraft.getInstance().level;
        if (world != null) {
            List<RECIPE> recipes = type.getMSRecipeType().getRecipes(world);
            if (!recipes.isEmpty()) {
                register(registry, recipeType, recipes);
            }
        }
    }

    public static <RECIPE> void register(IRecipeRegistration registry, MekanismJEIRecipeType<RECIPE> recipeType, List<RECIPE> recipes) {
        if (!recipes.isEmpty()) {
            registry.addRecipes(MekanismJEI.recipeType(recipeType), recipes);
        }
    }
}