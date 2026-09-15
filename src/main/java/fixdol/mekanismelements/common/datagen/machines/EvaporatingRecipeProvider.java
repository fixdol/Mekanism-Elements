package fixdol.mekanismelements.common.datagen.machines;

import java.util.function.Consumer;
import fixdol.mekanismelements.common.datagen.machines.EvaporatingRecipeProvider;
import net.minecraft.data.recipes.FinishedRecipe;
import mekanism.api.recipes.ingredients.creator.IngredientCreatorAccess;
import fixdol.mekanismelements.common.MekanismElements;
import net.minecraft.resources.ResourceLocation;


import fixdol.mekanismelements.common.registries.MSFluids;
import mekanism.api.datagen.recipe.builder.FluidToFluidRecipeBuilder;
import net.minecraftforge.fluids.FluidStack;

public class EvaporatingRecipeProvider {

    public static void buildRecipes(Consumer<FinishedRecipe> recipeOutput) {
        String basePath = "evaporating/";

        // Seawater (fluid, 1 mB) -> Potassium Chloride (fluid, 1 mB)
        FluidToFluidRecipeBuilder.evaporating(
                IngredientCreatorAccess.fluid().from(MSFluids.SEAWATER.getFluid(), 1),
                new FluidStack(MSFluids.POTASSIUM_CHLORIDE.getFluid(), 1)
        ).build(recipeOutput, new ResourceLocation(MekanismElements.MODID, basePath + "potassium_chloride"));
    }
}