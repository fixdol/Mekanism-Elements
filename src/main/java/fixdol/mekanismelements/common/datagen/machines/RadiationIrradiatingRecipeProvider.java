package fixdol.mekanismelements.datagen.machines;

import fixdol.mekanismelements.api.recipes.RadiationIrradiatingRecipe;
import fixdol.mekanismelements.common.MekanismElements;
import fixdol.mekanismelements.common.recipe.impl.RadiationIrradiatingIRecipe;
import fixdol.mekanismelements.common.registries.MSGases;
import fixdol.mekanismelements.common.registries.MSItems;
import mekanism.api.chemical.ChemicalStackTemplate;
import mekanism.api.recipes.ingredients.ChemicalStackIngredient;
import mekanism.api.recipes.ingredients.ItemStackIngredient;
import mekanism.api.recipes.ingredients.creator.IngredientCreatorAccess;
import mekanism.common.registries.MekanismChemicals;
import mekanism.generators.common.registries.GeneratorsChemicals;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;

public class RadiationIrradiatingRecipeProvider {

    // Llamado directamente desde ModRecipeProvider.buildRecipes()
    public static void buildRecipes(RecipeOutput output) {

        // strontium: 1 uranium_hexafluoride + 1 pellet_neutron_source -> 500 strontium
        addRecipe(output, "strontium",
                IngredientCreatorAccess.item().from(MSItems.NEUTRON_SOURCE_PELLET, 1),
                IngredientCreatorAccess.chemicalStack().fromHolder(MekanismChemicals.URANIUM_HEXAFLUORIDE, 1),
                MSGases.STRONTIUM.asTemplate(500)
        );

        // tritium: 4 lithium + 1 pellet_neutron_source -> 500 tritium
        addRecipe(output, "tritium",
                IngredientCreatorAccess.item().from(MSItems.NEUTRON_SOURCE_PELLET, 1),
                IngredientCreatorAccess.chemicalStack().fromHolder(MekanismChemicals.LITHIUM, 4),
                GeneratorsChemicals.TRITIUM.asTemplate(500)
        );

        // americium: 4 plutonium + 1 pellet_neutron_source -> 500 americium
        addRecipe(output, "americium",
                IngredientCreatorAccess.item().from(MSItems.NEUTRON_SOURCE_PELLET, 1),
                IngredientCreatorAccess.chemicalStack().fromHolder(MekanismChemicals.PLUTONIUM, 4),
                MSGases.AMERICIUM.asTemplate(500)
        );

        // californium: 4 curium + 5 pellet_neutron_source -> 50 californium
        addRecipe(output, "californium",
                IngredientCreatorAccess.item().from(MSItems.NEUTRON_SOURCE_PELLET, 5),
                IngredientCreatorAccess.chemicalStack().fromHolder(MSGases.CURIUM, 4),
                MSGases.CALIFORNIUM.asTemplate(50)
        );

        // nuclear_waste: 4 fissile_fuel + 3 pellet_neutron_source -> 10 nuclear_waste
        addRecipe(output, "nuclear_waste",
                IngredientCreatorAccess.item().from(MSItems.NEUTRON_SOURCE_PELLET, 3),
                IngredientCreatorAccess.chemicalStack().fromHolder(MekanismChemicals.FISSILE_FUEL, 4),
                MekanismChemicals.NUCLEAR_WASTE.asTemplate(10)
        );

        // polonium: 4 nuclear_waste + 1 pellet_neutron_source -> 500 polonium
        addRecipe(output, "polonium",
                IngredientCreatorAccess.item().from(MSItems.NEUTRON_SOURCE_PELLET, 1),
                IngredientCreatorAccess.chemicalStack().fromHolder(MekanismChemicals.NUCLEAR_WASTE, 4),
                MekanismChemicals.POLONIUM.asTemplate(500)
        );
    }

    private static void addRecipe(RecipeOutput output, String name,
                                   ItemStackIngredient itemInput,
                                   ChemicalStackIngredient chemicalInput,
                                   ChemicalStackTemplate chemicalOutput) {
        Identifier id = MekanismElements.rl("radiation_irradiating/" + name);
        RadiationIrradiatingRecipe recipe = new RadiationIrradiatingIRecipe(itemInput, chemicalInput, chemicalOutput);
        output.accept(ResourceKey.create(Registries.RECIPE, id), recipe, null);
    }
}
