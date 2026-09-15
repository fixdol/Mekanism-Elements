package fixdol.mekanismelements.common.datagen.machines;

import java.util.function.Consumer;
import net.minecraft.data.recipes.FinishedRecipe;
import mekanism.api.chemical.gas.GasStack;
import mekanism.api.recipes.ingredients.creator.IngredientCreatorAccess;
import net.minecraft.world.item.ItemStack;
import net.minecraft.tags.ItemTags;
import fixdol.mekanismelements.common.registries.MSFluids;
import fixdol.mekanismelements.common.registries.MSGases;
import fixdol.mekanismelements.common.registries.MSItems;
import fixdol.mekanismelements.common.MekanismElements;
import fixdol.mekanismelements.common.datagen.machines.ReactionRecipeProvider;
import net.minecraft.resources.ResourceLocation;


import mekanism.api.datagen.recipe.builder.PressurizedReactionRecipeBuilder;
import net.minecraft.tags.FluidTags;

public class ReactionRecipeProvider {

    public static void buildRecipes(Consumer<FinishedRecipe> recipeOutput) {
        String basePath = "reaction/";

        // Pellet Neutron Source: Beryllium (fluid) + Americium (gas) + Steel Dust (item) -> Neutron Source Pellet
        PressurizedReactionRecipeBuilder.reaction(
                IngredientCreatorAccess.item().from(
                        ItemTags.create(new ResourceLocation("forge", "dusts/steel")), 1),
                IngredientCreatorAccess.fluid().from(MSFluids.BERYLLIUM.getFluid(), 1000),
                IngredientCreatorAccess.gas().from(MSGases.AMERICIUM.get(), 100),
                25,
                new ItemStack(MSItems.NEUTRON_SOURCE_PELLET.get(), 1)
        ).build(recipeOutput, new ResourceLocation(MekanismElements.MODID, basePath + "pellet_neutron_source"));

        // Las siguientes recetas requieren items aún comentados en MSItems:
        // UNSTABLE_CALIFORNIUM_MIXTURE e INGOT_REFINED_CALIFORNIUM
        // Descomentar cuando estén registrados en MSItems y MSFluids (NETHERITE_ACID)

        /*
        // Unstable Californium Mixture: Netherite Acid (fluid) + Californium (gas) + Lead Dust (item) -> Unstable Californium Mixture
        PressurizedReactionRecipeBuilder.reaction(
                IngredientCreatorAccess.item().from(
                        ItemTags.create(new ResourceLocation("forge", "dusts/lead")), 1),
                IngredientCreatorAccess.fluid().from(MSFluids.NETHERITE_ACID.getFluid(), 3000),
                IngredientCreatorAccess.gas().from(MSGases.NETHERITE_ACID.get(), 3000),
                50,
                new ItemStack(MSItems.UNSTABLE_CALIFORNIUM_MIXTURE.get(), 1)
        ).build(recipeOutput, new ResourceLocation(MekanismElements.MODID, basePath + "unstable_californium_mixture"));
        */

        // Yttrium: Fluorite Dust (item) + Heavy Water (fluid tag) + Strontium (gas) -> Calcium Oxide Dust + Yttrium (gas)
        PressurizedReactionRecipeBuilder.reaction(
                IngredientCreatorAccess.item().from(
                        ItemTags.create(new ResourceLocation("forge", "dusts/fluorite")), 1),
                IngredientCreatorAccess.fluid().from(
                        FluidTags.create(new ResourceLocation("forge", "heavy_water")), 10),
                IngredientCreatorAccess.gas().from(MSGases.STRONTIUM.get(), 100),
                100,
                new ItemStack(MSItems.DUST_CALCIUM_OXIDE.get(), 1),
                new GasStack(MSGases.YTTRIUM.get(), 100)
        ).build(recipeOutput, new ResourceLocation(MekanismElements.MODID, basePath + "yttrium"));
    }
}