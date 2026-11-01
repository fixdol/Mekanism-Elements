package fixdol.mekanismelements.datagen.machines;

import fixdol.mekanismelements.common.MekanismElements;
import fixdol.mekanismelements.common.registries.MSGases;
import mekanism.api.datagen.recipe.builder.ChemicalDissolutionRecipeBuilder;
import mekanism.api.recipes.ingredients.creator.IngredientCreatorAccess;
import mekanism.common.registries.MekanismChemicals;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;

public class DissolutionRecipeProvider {

    public static void buildRecipes(RecipeOutput recipeOutput, HolderLookup.Provider registries) {
        String basePath = "dissolution/";
        HolderGetter<Item> items = registries.lookupOrThrow(Registries.ITEM);

        // Netherite Dust (item) + Aqua Regia (gas, 1 mB) -> Netherite Acid (gas, 100 mB)
        ChemicalDissolutionRecipeBuilder.dissolution(
                IngredientCreatorAccess.item().from(items,
                        ItemTags.create(Identifier.fromNamespaceAndPath("c", "dusts/netherite")), 1),
                IngredientCreatorAccess.chemicalStack().fromHolder(MSGases.AQUA_REGIA, 1),
                MSGases.NETHERITE_ACID.asTemplate(100),
                false
        ).save(recipeOutput, MekanismElements.rl(basePath + "netherite_acid"));

        // Emerald Ore (item, 1) + Sulfuric Acid (gas, 1 mB) -> Beryllium (gas, 1000 mB) [per_tick_usage = true]
        ChemicalDissolutionRecipeBuilder.dissolution(
                IngredientCreatorAccess.item().from(items,
                        ItemTags.create(Identifier.fromNamespaceAndPath("c", "ores/emerald")), 1),
                IngredientCreatorAccess.chemicalStack().fromHolder(MekanismChemicals.SULFURIC_ACID, 1),
                MSGases.BERYLLIUM.asTemplate(1000),
                true
        ).save(recipeOutput, MekanismElements.rl(basePath + "beryllium"));
    }
}
