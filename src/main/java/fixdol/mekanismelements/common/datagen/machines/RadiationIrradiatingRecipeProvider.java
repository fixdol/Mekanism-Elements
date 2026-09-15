package fixdol.mekanismelements.common.datagen.machines;

import mekanism.api.recipes.ingredients.ChemicalStackIngredient;
import java.util.function.Consumer;
import net.minecraft.data.recipes.FinishedRecipe;
import mekanism.api.chemical.gas.GasStack;
import mekanism.api.recipes.ingredients.creator.IngredientCreatorAccess;
import mekanism.api.recipes.ingredients.ItemStackIngredient;
import fixdol.mekanismelements.common.registries.MSGases;
import fixdol.mekanismelements.common.registries.MSItems;
import fixdol.mekanismelements.common.MekanismElements;
import mekanism.common.registries.MekanismGases;
import fixdol.mekanismelements.common.datagen.machines.RadiationIrradiatingRecipeProvider;
import net.minecraft.resources.ResourceLocation;


import fixdol.mekanismelements.api.recipes.RadiationIrradiatingRecipe;
import fixdol.mekanismelements.common.recipe.impl.RadiationIrradiatingIRecipe;
import mekanism.generators.common.registries.GeneratorsGases;

public class RadiationIrradiatingRecipeProvider {

    // Llamado directamente desde ModRecipeProvider.buildRecipes()
    public static void buildRecipes(Consumer<FinishedRecipe> output) {

        // strontium: 1 uranium_hexafluoride + 1 pellet_neutron_source -> 500 strontium
        addRecipe(output, "strontium",
                IngredientCreatorAccess.item().from(MSItems.NEUTRON_SOURCE_PELLET.get(), 1),
                IngredientCreatorAccess.gas().from(MekanismGases.URANIUM_HEXAFLUORIDE.get(), 1),
                new GasStack(MSGases.STRONTIUM.get(), 500)
        );

        // tritium: 4 lithium + 1 pellet_neutron_source -> 500 tritium
        addRecipe(output, "tritium",
                IngredientCreatorAccess.item().from(MSItems.NEUTRON_SOURCE_PELLET.get(), 1),
                IngredientCreatorAccess.gas().from(MekanismGases.LITHIUM.get(), 4),
                new GasStack(GeneratorsGases.TRITIUM.get(), 500)
        );

        // americium: 4 plutonium + 1 pellet_neutron_source -> 500 americium
        addRecipe(output, "americium",
                IngredientCreatorAccess.item().from(MSItems.NEUTRON_SOURCE_PELLET.get(), 1),
                IngredientCreatorAccess.gas().from(MekanismGases.PLUTONIUM.get(), 4),
                new GasStack(MSGases.AMERICIUM.get(), 500)
        );

        // californium: 4 curium + 5 pellet_neutron_source -> 50 californium
        addRecipe(output, "californium",
                IngredientCreatorAccess.item().from(MSItems.NEUTRON_SOURCE_PELLET.get(), 5),
                IngredientCreatorAccess.gas().from(MSGases.CURIUM.get(), 4),
                new GasStack(MSGases.CALIFORNIUM.get(), 50)
        );

        // nuclear_waste: 4 fissile_fuel + 3 pellet_neutron_source -> 10 nuclear_waste
        addRecipe(output, "nuclear_waste",
                IngredientCreatorAccess.item().from(MSItems.NEUTRON_SOURCE_PELLET.get(), 3),
                IngredientCreatorAccess.gas().from(MekanismGases.FISSILE_FUEL.get(), 4),
                new GasStack(MekanismGases.NUCLEAR_WASTE.get(), 10)
        );

        // polonium: 4 nuclear_waste + 1 pellet_neutron_source -> 500 polonium
        addRecipe(output, "polonium",
                IngredientCreatorAccess.item().from(MSItems.NEUTRON_SOURCE_PELLET.get(), 1),
                IngredientCreatorAccess.gas().from(MekanismGases.NUCLEAR_WASTE.get(), 4),
                new GasStack(MekanismGases.POLONIUM.get(), 500)
        );
    }

    private static void addRecipe(Consumer<FinishedRecipe> output, String name,
                                   ItemStackIngredient itemInput,
                                   ChemicalStackIngredient.GasStackIngredient chemicalInput,
                                   GasStack chemicalOutput) {
        ResourceLocation id = MekanismElements.rl("radiation_irradiating/" + name);
        com.google.gson.JsonObject json = new com.google.gson.JsonObject();
        json.add(mekanism.api.JsonConstants.ITEM_INPUT, itemInput.serialize());
        json.add(mekanism.api.JsonConstants.CHEMICAL_INPUT, chemicalInput.serialize());
        json.add(mekanism.api.JsonConstants.OUTPUT, mekanism.api.SerializerHelper.serializeGasStack(chemicalOutput));
        output.accept(new fixdol.mekanismelements.common.datagen.MSFinishedRecipe(id, fixdol.mekanismelements.common.registries.MSRecipeSerializers.RADIATION_IRRADIATOR::get, json));
    }
}