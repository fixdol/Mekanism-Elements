package fixdol.mekanismelements.datagen.machines;

import fixdol.mekanismelements.common.MekanismElements;
import fixdol.mekanismelements.common.registries.MSFluids;
import fixdol.mekanismelements.common.registries.MSGases;
import fixdol.mekanismelements.common.registries.MSItems;
import mekanism.api.datagen.recipe.builder.PressurizedReactionRecipeBuilder;
import mekanism.api.recipes.ingredients.creator.IngredientCreatorAccess;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.material.Fluid;

public class ReactionRecipeProvider {

    public static void buildRecipes(RecipeOutput recipeOutput, HolderLookup.Provider registries) {
        String basePath = "reaction/";
        HolderGetter<Item> items = registries.lookupOrThrow(Registries.ITEM);
        HolderGetter<Fluid> fluids = registries.lookupOrThrow(Registries.FLUID);

        // Pellet Neutron Source: Beryllium (fluid) + Americium (gas) + Steel Dust (item) -> Neutron Source Pellet
        PressurizedReactionRecipeBuilder.reaction(
                IngredientCreatorAccess.item().from(items,
                        ItemTags.create(Identifier.fromNamespaceAndPath("c", "dusts/steel")), 1),
                IngredientCreatorAccess.fluid().fromHolder(MSFluids.BERYLLIUM, 1000),
                IngredientCreatorAccess.chemicalStack().fromHolder(MSGases.AMERICIUM, 100),
                25,
                MSItems.NEUTRON_SOURCE_PELLET.asTemplate()
        ).save(recipeOutput, MekanismElements.rl(basePath + "pellet_neutron_source"));

        // Las siguientes recetas requieren items aún comentados en MSItems:
        // UNSTABLE_CALIFORNIUM_MIXTURE e INGOT_REFINED_CALIFORNIUM
        // Descomentar cuando estén registrados en MSItems y MSFluids (NETHERITE_ACID)

        /*
        // Unstable Californium Mixture: Netherite Acid (fluid) + Californium (gas) + Lead Dust (item) -> Unstable Californium Mixture
        PressurizedReactionRecipeBuilder.reaction(
                IngredientCreatorAccess.item().from(items,
                        ItemTags.create(Identifier.fromNamespaceAndPath("c", "dusts/lead")), 1),
                IngredientCreatorAccess.fluid().fromHolder(MSFluids.NETHERITE_ACID, 3000),
                IngredientCreatorAccess.chemicalStack().fromHolder(MSGases.NETHERITE_ACID, 3000),
                50,
                MSItems.UNSTABLE_CALIFORNIUM_MIXTURE.asTemplate()
        ).save(recipeOutput, MekanismElements.rl(basePath + "unstable_californium_mixture"));
        */

        // Yttrium: Fluorite Dust (item) + Heavy Water (fluid tag) + Strontium (gas) -> Calcium Oxide Dust + Yttrium (gas)
        PressurizedReactionRecipeBuilder.reaction(
                IngredientCreatorAccess.item().from(items,
                        ItemTags.create(Identifier.fromNamespaceAndPath("c", "dusts/fluorite")), 1),
                IngredientCreatorAccess.fluid().from(fluids,
                        TagKey.create(Registries.FLUID, Identifier.fromNamespaceAndPath("c", "heavy_water")), 10),
                IngredientCreatorAccess.chemicalStack().fromHolder(MSGases.STRONTIUM, 100),
                100,
                MSItems.DUST_CALCIUM_OXIDE.asTemplate(),
                MSGases.YTTRIUM.asTemplate(100)
        ).save(recipeOutput, MekanismElements.rl(basePath + "yttrium"));
    }
}
