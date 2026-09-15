package fixdol.mekanismelements.common.datagen.machines;

import fixdol.mekanismelements.common.datagen.machines.ChemicalInfusingRecipeProvider;
import java.util.function.Consumer;
import net.minecraft.data.recipes.FinishedRecipe;
import mekanism.api.chemical.gas.GasStack;
import mekanism.api.recipes.ingredients.creator.IngredientCreatorAccess;
import fixdol.mekanismelements.common.registries.MSGases;
import fixdol.mekanismelements.common.MekanismElements;
import mekanism.common.registries.MekanismGases;


import mekanism.api.datagen.recipe.builder.ChemicalChemicalToChemicalRecipeBuilder;

public class ChemicalInfusingRecipeProvider {

    public static void buildRecipes(Consumer<FinishedRecipe> output) {
        String base = "chemical_infusing/";

        ChemicalChemicalToChemicalRecipeBuilder.chemicalInfusing(
                IngredientCreatorAccess.gas().from(MSGases.AMMONIUM_NITRATE.get(), 10),
                IngredientCreatorAccess.gas().from(MekanismGases.WATER_VAPOR.get(), 1),
                new GasStack(MSGases.AMMONIUM_NITRATE_SOLUTION.get(), 10)
        ).build(output, MekanismElements.rl(base + "ammonium_nitrate_solution"));

        // nitrogen (1) + hydrogen (3) -> ammonia (2)
        ChemicalChemicalToChemicalRecipeBuilder.chemicalInfusing(
                IngredientCreatorAccess.gas().from(MSGases.NITROGEN.get(), 1),
                IngredientCreatorAccess.gas().from(MekanismGases.HYDROGEN.get(), 3),
                new GasStack(MSGases.AMMONIA.get(), 2)
        ).build(output, MekanismElements.rl(base + "ammonia"));

        // ammonia (1) + nitric_acid (1) -> ammonium_nitrate (1)
        ChemicalChemicalToChemicalRecipeBuilder.chemicalInfusing(
                IngredientCreatorAccess.gas().from(MSGases.AMMONIA.get(), 1),
                IngredientCreatorAccess.gas().from(MSGases.NITRIC_ACID.get(), 1),
                new GasStack(MSGases.AMMONIUM_NITRATE.get(), 1)
        ).build(output, MekanismElements.rl(base + "ammonium_nitrate"));

        // nitric_acid (1) + hydrogen_chloride (3) -> aqua_regia (1)
        ChemicalChemicalToChemicalRecipeBuilder.chemicalInfusing(
                IngredientCreatorAccess.gas().from(MSGases.NITRIC_ACID.get(), 1),
                IngredientCreatorAccess.gas().from(MekanismGases.HYDROGEN_CHLORIDE.get(), 3),
                new GasStack(MSGases.AQUA_REGIA.get(), 1)
        ).build(output, MekanismElements.rl(base + "aqua_regia"));

        // seawater (10) + chlorine (10) -> bromine (1)
        ChemicalChemicalToChemicalRecipeBuilder.chemicalInfusing(
                IngredientCreatorAccess.gas().from(MSGases.SEAWATER.get(), 10),
                IngredientCreatorAccess.gas().from(MekanismGases.CHLORINE.get(), 10),
                new GasStack(MSGases.BROMINE.get(), 1)
        ).build(output, MekanismElements.rl(base + "bromine"));

        // nitric_acid (1) + spent_nuclear_waste (1) -> dissolved_spent_nuclear_waste (1)
        ChemicalChemicalToChemicalRecipeBuilder.chemicalInfusing(
                IngredientCreatorAccess.gas().from(MSGases.NITRIC_ACID.get(), 1),
                IngredientCreatorAccess.gas().from(MekanismGases.SPENT_NUCLEAR_WASTE.get(), 1),
                new GasStack(MSGases.DISSOLVED_SPENT_NUCLEAR_WASTE.get(), 1)
        ).build(output, MekanismElements.rl(base + "dissolved_spent_nuclear_waste"));

        // ammonia (1) + methane (1) -> hydrogen_cyanide (1)
        ChemicalChemicalToChemicalRecipeBuilder.chemicalInfusing(
                IngredientCreatorAccess.gas().from(MSGases.AMMONIA.get(), 1),
                IngredientCreatorAccess.gas().from(MSGases.METHANE.get(), 1),
                new GasStack(MSGases.HYDROGEN_CYANIDE.get(), 1)
        ).build(output, MekanismElements.rl(base + "hydrogen_cyanide"));

        // nitrogen_dioxide (3) + water_vapor (1) -> nitric_acid (2)
        ChemicalChemicalToChemicalRecipeBuilder.chemicalInfusing(
                IngredientCreatorAccess.gas().from(MSGases.NITROGEN_DIOXIDE.get(), 3),
                IngredientCreatorAccess.gas().from(MekanismGases.WATER_VAPOR.get(), 1),
                new GasStack(MSGases.NITRIC_ACID.get(), 2)
        ).build(output, MekanismElements.rl(base + "nitric_acid"));

        // ammonia (4) + oxygen (5) -> nitric_oxide (4)
        ChemicalChemicalToChemicalRecipeBuilder.chemicalInfusing(
                IngredientCreatorAccess.gas().from(MSGases.AMMONIA.get(), 4),
                IngredientCreatorAccess.gas().from(MekanismGases.OXYGEN.get(), 5),
                new GasStack(MSGases.NITRIC_OXIDE.get(), 4)
        ).build(output, MekanismElements.rl(base + "nitric_oxide"));

        // nitric_oxide (2) + oxygen (1) -> nitrogen_dioxide (2)
        ChemicalChemicalToChemicalRecipeBuilder.chemicalInfusing(
                IngredientCreatorAccess.gas().from(MSGases.NITRIC_OXIDE.get(), 2),
                IngredientCreatorAccess.gas().from(MekanismGases.OXYGEN.get(), 1),
                new GasStack(MSGases.NITROGEN_DIOXIDE.get(), 2)
        ).build(output, MekanismElements.rl(base + "nitrogen_dioxide"));

        // potassium_hydroxide (1) + hydrogen_cyanide (1) -> potassium_cyanide (1)
        ChemicalChemicalToChemicalRecipeBuilder.chemicalInfusing(
                IngredientCreatorAccess.gas().from(MSGases.POTASSIUM_HYDROXIDE.get(), 1),
                IngredientCreatorAccess.gas().from(MSGases.HYDROGEN_CYANIDE.get(), 1),
                new GasStack(MSGases.POTASSIUM_CYANIDE.get(), 1)
        ).build(output, MekanismElements.rl(base + "potassium_cyanide"));

        // potassium_hydroxide (6) + iodine (3) -> potassium_iodide (5)
        ChemicalChemicalToChemicalRecipeBuilder.chemicalInfusing(
                IngredientCreatorAccess.gas().from(MSGases.POTASSIUM_HYDROXIDE.get(), 6),
                IngredientCreatorAccess.gas().from(MSGases.IODINE.get(), 3),
                new GasStack(MSGases.POTASSIUM_IODIDE.get(), 5)
        ).build(output, MekanismElements.rl(base + "potassium_iodide"));
    }
}