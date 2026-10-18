package fixdol.mekanismelements.client.rei;

import fixdol.mekanismelements.common.recipe.MSRecipeType;
import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.common.util.EntryStacks;
import me.shedaniel.rei.forge.REIPluginClient;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;

import java.util.List;

/**
 * REI side of the recipe viewer integration. REI does not import JEI plugins (EMI does), and Mekanism has no REI
 * support, so the categories draw Mekanism's own GUI elements through {@link MSReiBuilder}.
 */
@REIPluginClient
public class MSRei implements REIClientPlugin {
    private static final List<MSReiMachine> MACHINES = List.of(
            MSReiMachine.ADSORPTION_SEPARATOR,
            MSReiMachine.CHEMICAL_DEMOLITION_MACHINE,
            MSReiMachine.RADIATION_IRRADIATOR,
            MSReiMachine.AIR_COMPRESSOR,
            MSReiMachine.SEAWATER_PUMP,
            MSReiMachine.INFINITY_ORE_REPROCESSING
    );

    @Override
    public void registerCategories(CategoryRegistry registry) {
        for (MSReiMachine machine : MACHINES) {
            registry.add(new MSReiCategory(machine));
            registry.addWorkstations(machine.categoryId(), EntryStacks.of(machine.block().asItem()));
        }
    }

    @Override
    public void registerDisplays(DisplayRegistry registry) {
        ClientLevel level = Minecraft.getInstance().level;
        if (level != null) {
            MSRecipeType.ADSORPTION.getMSRecipeType().getRecipes(level).forEach(recipe -> registry.add(MSReiLayouts.adsorption(recipe)));
            MSRecipeType.CHEMICAL_DEMOLITION.getMSRecipeType().getRecipes(level).forEach(recipe -> registry.add(MSReiLayouts.chemicalDemolition(recipe)));
            MSRecipeType.RADIATION_IRRADIATING.getMSRecipeType().getRecipes(level).forEach(recipe -> registry.add(MSReiLayouts.radiationIrradiating(recipe)));
            MSRecipeType.INFINITY_ORE_REPROCESSING.getMSRecipeType().getRecipes(level).forEach(recipe -> registry.add(MSReiLayouts.infinityOreReprocessing(recipe)));
        }

        // Machines without a real JSON recipe: a single fixed informational "recipe"
        registry.add(MSReiLayouts.airCompressor());
        registry.add(MSReiLayouts.seawaterPump());
    }
}
