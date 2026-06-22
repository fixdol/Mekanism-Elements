package com.fxd927.mekanismelements.datagen.machines;

import com.fxd927.mekanismelements.common.MekanismElements;
import com.fxd927.mekanismelements.common.registries.MSFluids;
import com.fxd927.mekanismelements.common.registries.MSGases;
import mekanism.api.chemical.Chemical;
import mekanism.api.chemical.ChemicalStack;
import mekanism.api.datagen.recipe.builder.RotaryRecipeBuilder;
import mekanism.api.recipes.ingredients.creator.IngredientCreatorAccess;
import mekanism.common.registries.MekanismChemicals;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.fluids.FluidStack;

public class RotaryRecipeProvider {

    public static void buildRecipes(RecipeOutput recipeOutput) {
        addRotary(recipeOutput, "ammonia",             MSFluids.AMMONIA.get(),             MSGases.AMMONIA.get());
        addRotary(recipeOutput, "ammonium_nitrate",    MSFluids.AMMONIUM_NITRATE.get(),    MSGases.AMMONIUM_NITRATE.get());
        // AQUA_REGIA: fluid comentado en MSFluids, descomentar cuando esté registrado
        addRotary(recipeOutput, "antimatter_fluid",    MSFluids.ANTIMATTER_FLUID.get(),    MekanismChemicals.ANTIMATTER.get());
        addRotary(recipeOutput, "beryllium",           MSFluids.BERYLLIUM.get(),           MSGases.BERYLLIUM.get());
        addRotary(recipeOutput, "bromine",             MSFluids.BROMINE.get(),             MSGases.BROMINE.get());
        addRotary(recipeOutput, "compressed_air",      MSFluids.COMPRESSED_AIR.get(),      MSGases.COMPRESSED_AIR.get());
        addRotary(recipeOutput, "helium",              MSFluids.HELIUM.get(),              MSGases.HELIUM.get());
        addRotary(recipeOutput, "hydrogen_cyanide",    MSFluids.HYDROGEN_CYANIDE.get(),    MSGases.HYDROGEN_CYANIDE.get());
        addRotary(recipeOutput, "iodine",              MSFluids.IODINE.get(),              MSGases.IODINE.get());
        addRotary(recipeOutput, "methane",             MSFluids.METHANE.get(),             MSGases.METHANE.get());
        // NETHERITE_ACID: fluid comentado en MSFluids, descomentar cuando esté registrado
        addRotary(recipeOutput, "nitric_acid",         MSFluids.NITRIC_ACID.get(),         MSGases.NITRIC_ACID.get());
        addRotary(recipeOutput, "nitric_oxide",        MSFluids.NITRIC_OXIDE.get(),        MSGases.NITRIC_OXIDE.get());
        addRotary(recipeOutput, "nitrogen",            MSFluids.NITROGEN.get(),            MSGases.NITROGEN.get());
        addRotary(recipeOutput, "nitrogen_dioxide",    MSFluids.NITROGEN_DIOXIDE.get(),    MSGases.NITROGEN_DIOXIDE.get());
        // POTASSIUM: no registrado en MSFluids, agregar cuando esté listo
        addRotary(recipeOutput, "potassium_chloride",  MSFluids.POTASSIUM_CHLORIDE.get(),  MSGases.POTASSIUM_CHLORIDE.get());
        addRotary(recipeOutput, "potassium_cyanide",   MSFluids.POTASSIUM_CYANIDE.get(),   MSGases.POTASSIUM_CYANIDE.get());
        addRotary(recipeOutput, "potassium_hydroxide", MSFluids.POTASSIUM_HYDROXIDE.get(), MSGases.POTASSIUM_HYDROXIDE.get());
        addRotary(recipeOutput, "potassium_iodide",    MSFluids.POTASSIUM_IODIDE.get(),    MSGases.POTASSIUM_IODIDE.get());
        addRotary(recipeOutput, "seawater",            MSFluids.SEAWATER.get(),            MSGases.SEAWATER.get());
        addRotary(recipeOutput, "strontium",           MSFluids.STRONTIUM.get(),           MSGases.STRONTIUM.get());
        addRotary(recipeOutput, "superheated_helium",  MSFluids.SUPERHEATED_HELIUM.get(),  MSGases.SUPERHEATED_HELIUM.get());
        addRotary(recipeOutput, "xenon",               MSFluids.XENON.get(),               MSGases.XENON.get());
        addRotary(recipeOutput, "yttrium",             MSFluids.YTTRIUM.get(),             MSGases.YTTRIUM.get());
    }

    private static void addRotary(RecipeOutput recipeOutput, String name, Fluid fluid, Chemical chemical) {
        RotaryRecipeBuilder.rotary(
                IngredientCreatorAccess.fluid().from(fluid, 1),
                IngredientCreatorAccess.chemicalStack().from(chemical, 1),
                new ChemicalStack(chemical, 1),
                new FluidStack(fluid, 1)
        ).build(recipeOutput, ResourceLocation.fromNamespaceAndPath(MekanismElements.MODID, "rotary/" + name));
    }
}