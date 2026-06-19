package com.fxd927.mekanismelements.datagen.machines;

import com.fxd927.mekanismelements.common.MekanismElements;
import com.fxd927.mekanismelements.common.registries.MSGases;
import mekanism.api.chemical.ChemicalStack;
import mekanism.api.datagen.recipe.builder.ChemicalChemicalToChemicalRecipeBuilder;
import mekanism.api.recipes.ingredients.creator.IngredientCreatorAccess;
import mekanism.common.registries.MekanismChemicals;
import net.minecraft.data.recipes.RecipeOutput;

public class ChemicalInfusingRecipeProvider {

    public static void buildRecipes(RecipeOutput output) {
        String base = "chemical_infusing/";

        // nitrogen (1) + hydrogen (3) -> ammonia (2)
        ChemicalChemicalToChemicalRecipeBuilder.chemicalInfusing(
                IngredientCreatorAccess.chemicalStack().fromHolder(MSGases.NITROGEN, 1),
                IngredientCreatorAccess.chemicalStack().fromHolder(MekanismChemicals.HYDROGEN, 3),
                new ChemicalStack(MSGases.AMMONIA.get(), 2)
        ).build(output, MekanismElements.rl(base + "ammonia"));

        // ammonia (1) + nitric_acid (1) -> ammonium_nitrate (1)
        ChemicalChemicalToChemicalRecipeBuilder.chemicalInfusing(
                IngredientCreatorAccess.chemicalStack().fromHolder(MSGases.AMMONIA, 1),
                IngredientCreatorAccess.chemicalStack().fromHolder(MSGases.NITRIC_ACID, 1),
                new ChemicalStack(MSGases.AMMONIUM_NITRATE.get(), 1)
        ).build(output, MekanismElements.rl(base + "ammonium_nitrate"));

        // nitric_acid (1) + hydrogen_chloride (3) -> aqua_regia (1)
        ChemicalChemicalToChemicalRecipeBuilder.chemicalInfusing(
                IngredientCreatorAccess.chemicalStack().fromHolder(MSGases.NITRIC_ACID, 1),
                IngredientCreatorAccess.chemicalStack().fromHolder(MekanismChemicals.HYDROGEN_CHLORIDE, 3),
                new ChemicalStack(MSGases.AQUA_REGIA.get(), 1)
        ).build(output, MekanismElements.rl(base + "aqua_regia"));

        // seawater (10) + chlorine (10) -> bromine (1)
        ChemicalChemicalToChemicalRecipeBuilder.chemicalInfusing(
                IngredientCreatorAccess.chemicalStack().fromHolder(MSGases.SEAWATER, 10),
                IngredientCreatorAccess.chemicalStack().fromHolder(MekanismChemicals.CHLORINE, 10),
                new ChemicalStack(MSGases.BROMINE.get(), 1)
        ).build(output, MekanismElements.rl(base + "bromine"));

        // nitric_acid (1) + spent_nuclear_waste (1) -> dissolved_spent_nuclear_waste (1)
        ChemicalChemicalToChemicalRecipeBuilder.chemicalInfusing(
                IngredientCreatorAccess.chemicalStack().fromHolder(MSGases.NITRIC_ACID, 1),
                IngredientCreatorAccess.chemicalStack().fromHolder(MekanismChemicals.SPENT_NUCLEAR_WASTE, 1),
                new ChemicalStack(MSGases.DISSOLVED_SPENT_NUCLEAR_WASTE.get(), 1)
        ).build(output, MekanismElements.rl(base + "dissolved_spent_nuclear_waste"));

        // ammonia (1) + methane (1) -> hydrogen_cyanide (1)
        ChemicalChemicalToChemicalRecipeBuilder.chemicalInfusing(
                IngredientCreatorAccess.chemicalStack().fromHolder(MSGases.AMMONIA, 1),
                IngredientCreatorAccess.chemicalStack().fromHolder(MSGases.METHANE, 1),
                new ChemicalStack(MSGases.HYDROGEN_CYANIDE.get(), 1)
        ).build(output, MekanismElements.rl(base + "hydrogen_cyanide"));

        // nitrogen_dioxide (3) + water_vapor (1) -> nitric_acid (2)
        ChemicalChemicalToChemicalRecipeBuilder.chemicalInfusing(
                IngredientCreatorAccess.chemicalStack().fromHolder(MSGases.NITROGEN_DIOXIDE, 3),
                IngredientCreatorAccess.chemicalStack().fromHolder(MekanismChemicals.WATER_VAPOR, 1),
                new ChemicalStack(MSGases.NITRIC_ACID.get(), 2)
        ).build(output, MekanismElements.rl(base + "nitric_acid"));

        // ammonia (4) + oxygen (5) -> nitric_oxide (4)
        ChemicalChemicalToChemicalRecipeBuilder.chemicalInfusing(
                IngredientCreatorAccess.chemicalStack().fromHolder(MSGases.AMMONIA, 4),
                IngredientCreatorAccess.chemicalStack().fromHolder(MekanismChemicals.OXYGEN, 5),
                new ChemicalStack(MSGases.NITRIC_OXIDE.get(), 4)
        ).build(output, MekanismElements.rl(base + "nitric_oxide"));

        // nitric_oxide (2) + oxygen (1) -> nitrogen_dioxide (2)
        ChemicalChemicalToChemicalRecipeBuilder.chemicalInfusing(
                IngredientCreatorAccess.chemicalStack().fromHolder(MSGases.NITRIC_OXIDE, 2),
                IngredientCreatorAccess.chemicalStack().fromHolder(MekanismChemicals.OXYGEN, 1),
                new ChemicalStack(MSGases.NITROGEN_DIOXIDE.get(), 2)
        ).build(output, MekanismElements.rl(base + "nitrogen_dioxide"));

        // potassium_hydroxide (1) + hydrogen_cyanide (1) -> potassium_cyanide (1)
        ChemicalChemicalToChemicalRecipeBuilder.chemicalInfusing(
                IngredientCreatorAccess.chemicalStack().fromHolder(MSGases.POTASSIUM_HYDROXIDE, 1),
                IngredientCreatorAccess.chemicalStack().fromHolder(MSGases.HYDROGEN_CYANIDE, 1),
                new ChemicalStack(MSGases.POTASSIUM_CYANIDE.get(), 1)
        ).build(output, MekanismElements.rl(base + "potassium_cyanide"));

        // potassium_hydroxide (6) + iodine (3) -> potassium_iodide (5)
        ChemicalChemicalToChemicalRecipeBuilder.chemicalInfusing(
                IngredientCreatorAccess.chemicalStack().fromHolder(MSGases.POTASSIUM_HYDROXIDE, 6),
                IngredientCreatorAccess.chemicalStack().fromHolder(MSGases.IODINE, 3),
                new ChemicalStack(MSGases.POTASSIUM_IODIDE.get(), 5)
        ).build(output, MekanismElements.rl(base + "potassium_iodide"));
    }
}