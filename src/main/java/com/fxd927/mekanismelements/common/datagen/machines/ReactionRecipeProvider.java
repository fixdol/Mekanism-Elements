package com.fxd927.mekanismelements.datagen.machines;

import com.fxd927.mekanismelements.common.MekanismElements;
import com.fxd927.mekanismelements.common.registries.MSFluids;
import com.fxd927.mekanismelements.common.registries.MSGases;
import com.fxd927.mekanismelements.common.registries.MSItems;
import mekanism.api.chemical.ChemicalStack;
import mekanism.api.datagen.recipe.builder.PressurizedReactionRecipeBuilder;
import mekanism.api.recipes.ingredients.creator.IngredientCreatorAccess;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ItemStack;

public class ReactionRecipeProvider {

    public static void buildRecipes(RecipeOutput recipeOutput) {
        String basePath = "reaction/";

        // Pellet Neutron Source: Beryllium (fluid) + Americium (gas) + Steel Dust (item) -> Neutron Source Pellet
        PressurizedReactionRecipeBuilder.reaction(
                IngredientCreatorAccess.item().from(
                        ItemTags.create(ResourceLocation.fromNamespaceAndPath("c", "dusts/steel")), 1),
                IngredientCreatorAccess.fluid().from(MSFluids.BERYLLIUM.get(), 1000),
                IngredientCreatorAccess.chemicalStack().from(MSGases.AMERICIUM.get(), 100),
                25,
                new ItemStack(MSItems.NEUTRON_SOURCE_PELLET.get(), 1)
        ).build(recipeOutput, ResourceLocation.fromNamespaceAndPath(MekanismElements.MODID, basePath + "pellet_neutron_source"));

        // Las siguientes recetas requieren items aún comentados en MSItems:
        // UNSTABLE_CALIFORNIUM_MIXTURE e INGOT_REFINED_CALIFORNIUM
        // Descomentar cuando estén registrados en MSItems y MSFluids (NETHERITE_ACID)

        /*
        // Unstable Californium Mixture: Netherite Acid (fluid) + Californium (gas) + Lead Dust (item) -> Unstable Californium Mixture
        PressurizedReactionRecipeBuilder.reaction(
                IngredientCreatorAccess.item().from(
                        ItemTags.create(ResourceLocation.fromNamespaceAndPath("c", "dusts/lead")), 1),
                IngredientCreatorAccess.fluid().from(MSFluids.NETHERITE_ACID.get(), 3000),
                IngredientCreatorAccess.chemicalStack().from(MSGases.NETHERITE_ACID.get(), 3000),
                50,
                new ItemStack(MSItems.UNSTABLE_CALIFORNIUM_MIXTURE.get(), 1)
        ).build(recipeOutput, ResourceLocation.fromNamespaceAndPath(MekanismElements.MODID, basePath + "unstable_californium_mixture"));
        */

        // Yttrium: Fluorite Dust (item) + Heavy Water (fluid tag) + Strontium (gas) -> Calcium Oxide Dust + Yttrium (gas)
        PressurizedReactionRecipeBuilder.reaction(
                IngredientCreatorAccess.item().from(
                        ItemTags.create(ResourceLocation.fromNamespaceAndPath("c", "dusts/fluorite")), 1),
                IngredientCreatorAccess.fluid().from(
                        FluidTags.create(ResourceLocation.fromNamespaceAndPath("c", "heavy_water")), 10),
                IngredientCreatorAccess.chemicalStack().from(MSGases.STRONTIUM.get(), 100),
                100,
                new ItemStack(MSItems.DUST_CALCIUM_OXIDE.get(), 1),
                new ChemicalStack(MSGases.YTTRIUM.get().getAsHolder(), 100)
        ).build(recipeOutput, ResourceLocation.fromNamespaceAndPath(MekanismElements.MODID, basePath + "yttrium"));
    }
}