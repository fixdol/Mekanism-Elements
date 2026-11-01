package fixdol.mekanismelements.datagen.machines;

import fixdol.mekanismelements.common.MekanismElements;
import fixdol.mekanismelements.common.registries.MSItems;
import mekanism.api.datagen.recipe.builder.ItemStackChemicalToItemStackRecipeBuilder;
import mekanism.api.recipes.ingredients.creator.IngredientCreatorAccess;
import mekanism.api.text.EnumColor;
import mekanism.common.registries.MekanismChemicals;
import net.minecraft.data.recipes.RecipeOutput;

public class PaintingRecipeProvider {

    // Must match PigmentExtractingRecipeProvider.DYE_RATE from Mekanism
    private static final int PIGMENT_RATE = 256;

    public static void buildRecipes(RecipeOutput recipeOutput) {
        String basePath = "painting/";

        // powder_high_quality_concrete + dark_red pigment (256 mB) -> powder_high_quality_concrete_dark_red
        ItemStackChemicalToItemStackRecipeBuilder.painting(
                IngredientCreatorAccess.item().from(MSItems.HIGH_QUALITY_CONCRETE_POWDER),
                IngredientCreatorAccess.chemicalStack().fromHolder(
                        MekanismChemicals.PIGMENT_COLOR_LOOKUP.get(EnumColor.DARK_RED), PIGMENT_RATE),
                MSItems.HIGH_QUALITY_CONCRETE_POWDER_DARK_RED.asTemplate(),
                false
        ).save(recipeOutput, MekanismElements.rl(basePath + "powder_high_quality_concrete_dark_red"));

        // powder_high_quality_concrete + aqua pigment (256 mB) -> powder_high_quality_concrete_aqua
        ItemStackChemicalToItemStackRecipeBuilder.painting(
                IngredientCreatorAccess.item().from(MSItems.HIGH_QUALITY_CONCRETE_POWDER),
                IngredientCreatorAccess.chemicalStack().fromHolder(
                        MekanismChemicals.PIGMENT_COLOR_LOOKUP.get(EnumColor.AQUA), PIGMENT_RATE),
                MSItems.HIGH_QUALITY_CONCRETE_POWDER_AQUA.asTemplate(),
                false
        ).save(recipeOutput, MekanismElements.rl(basePath + "powder_high_quality_concrete_aqua"));
    }
}
