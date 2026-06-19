package com.fxd927.mekanismelements.datagen.machines;

import com.fxd927.mekanismelements.api.recipes.RadiationIrradiatingRecipe;
import com.fxd927.mekanismelements.common.MekanismElements;
import com.fxd927.mekanismelements.common.recipe.impl.RadiationIrradiatingIRecipe;
import com.fxd927.mekanismelements.common.registries.MSGases;
import com.fxd927.mekanismelements.common.registries.MSItems;
import mekanism.api.chemical.ChemicalStack;
import mekanism.api.recipes.ingredients.ChemicalStackIngredient;
import mekanism.api.recipes.ingredients.ItemStackIngredient;
import mekanism.api.recipes.ingredients.creator.IngredientCreatorAccess;
import mekanism.common.registries.MekanismChemicals;
import mekanism.generators.common.registries.GeneratorsChemicals;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.crafting.SizedIngredient;

public class RadiationIrradiatingRecipeProvider {

    // Llamado directamente desde ModRecipeProvider.buildRecipes()
    public static void buildRecipes(RecipeOutput output) {

        // strontium: 1 uranium_hexafluoride + 1 pellet_neutron_source -> 500 strontium
        addRecipe(output, "strontium",
                ItemStackIngredient.of(SizedIngredient.of(MSItems.NEUTRON_SOURCE_PELLET.get(), 1)),
                IngredientCreatorAccess.chemicalStack().fromHolder(MekanismChemicals.URANIUM_HEXAFLUORIDE, 1),
                new ChemicalStack(MSGases.STRONTIUM.get(), 500)
        );

        // tritium: 4 lithium + 1 pellet_neutron_source -> 500 tritium
        addRecipe(output, "tritium",
                ItemStackIngredient.of(SizedIngredient.of(MSItems.NEUTRON_SOURCE_PELLET.get(), 1)),
                IngredientCreatorAccess.chemicalStack().fromHolder(MekanismChemicals.LITHIUM, 4),
                new ChemicalStack(GeneratorsChemicals.TRITIUM.get(), 500)
        );

        // americium: 4 plutonium + 1 pellet_neutron_source -> 500 americium
        addRecipe(output, "americium",
                ItemStackIngredient.of(SizedIngredient.of(MSItems.NEUTRON_SOURCE_PELLET.get(), 1)),
                IngredientCreatorAccess.chemicalStack().fromHolder(MekanismChemicals.PLUTONIUM, 4),
                new ChemicalStack(MSGases.AMERICIUM.get(), 500)
        );

        // californium: 4 curium + 5 pellet_neutron_source -> 50 californium
        addRecipe(output, "californium",
                ItemStackIngredient.of(SizedIngredient.of(MSItems.NEUTRON_SOURCE_PELLET.get(), 5)),
                IngredientCreatorAccess.chemicalStack().fromHolder(MSGases.CURIUM, 4),
                new ChemicalStack(MSGases.CALIFORNIUM.get(), 50)
        );

        // nuclear_waste: 4 fissile_fuel + 3 pellet_neutron_source -> 10 nuclear_waste
        addRecipe(output, "nuclear_waste",
                ItemStackIngredient.of(SizedIngredient.of(MSItems.NEUTRON_SOURCE_PELLET.get(), 3)),
                IngredientCreatorAccess.chemicalStack().fromHolder(MekanismChemicals.FISSILE_FUEL, 4),
                new ChemicalStack(MekanismChemicals.NUCLEAR_WASTE.get(), 10)
        );

        // polonium: 4 nuclear_waste + 1 pellet_neutron_source -> 500 polonium
        addRecipe(output, "polonium",
                ItemStackIngredient.of(SizedIngredient.of(MSItems.NEUTRON_SOURCE_PELLET.get(), 1)),
                IngredientCreatorAccess.chemicalStack().fromHolder(MekanismChemicals.NUCLEAR_WASTE, 4),
                new ChemicalStack(MekanismChemicals.POLONIUM.get(), 500)
        );
    }

    private static void addRecipe(RecipeOutput output, String name,
                                   ItemStackIngredient itemInput,
                                   ChemicalStackIngredient chemicalInput,
                                   ChemicalStack chemicalOutput) {
        ResourceLocation id = MekanismElements.rl("radiation_irradiating/" + name);
        RadiationIrradiatingRecipe recipe = new RadiationIrradiatingIRecipe(itemInput, chemicalInput, chemicalOutput);
        output.accept(id, recipe, null);
    }
}