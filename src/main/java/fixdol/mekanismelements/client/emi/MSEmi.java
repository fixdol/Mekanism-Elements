package fixdol.mekanismelements.client.emi;

import dev.emi.emi.api.EmiEntrypoint;
import dev.emi.emi.api.EmiPlugin;
import dev.emi.emi.api.EmiRegistry;
import fixdol.mekanismelements.common.MekanismElements;
import fixdol.mekanismelements.common.recipe.MSRecipeType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;

import java.util.List;

/**
 * EMI side of the recipe viewer integration. EMI can import the JEI plugin through its JEI bridge, but only while JEI
 * is installed, and Mekanism 10.4 has no EMI support, so the categories draw Mekanism's own GUI elements through
 * {@link MSEmiBuilder}. {@link fixdol.mekanismelements.client.MSJEI} registers nothing while EMI is installed, so
 * every machine shows up once.
 */
@EmiEntrypoint
public class MSEmi implements EmiPlugin {
    private static final List<MSEmiMachine> MACHINES = List.of(
            MSEmiMachine.ADSORPTION_SEPARATOR,
            MSEmiMachine.CHEMICAL_DEMOLITION_MACHINE,
            MSEmiMachine.RADIATION_IRRADIATOR,
            MSEmiMachine.AIR_COMPRESSOR,
            MSEmiMachine.SEAWATER_PUMP,
            MSEmiMachine.INFINITY_ORE_REPROCESSING
    );

    @Override
    public void register(EmiRegistry registry) {
        for (MSEmiMachine machine : MACHINES) {
            registry.addCategory(machine.category());
            registry.addWorkstation(machine.category(), machine.workstation());
        }

        int recipes = 0;
        ClientLevel level = Minecraft.getInstance().level;
        if (level != null) {
            for (var recipe : MSRecipeType.ADSORPTION.getMSRecipeType().getRecipes(level)) {
                registry.addRecipe(MSEmiLayouts.adsorption(recipe));
                recipes++;
            }
            for (var recipe : MSRecipeType.CHEMICAL_DEMOLITION.getMSRecipeType().getRecipes(level)) {
                registry.addRecipe(MSEmiLayouts.chemicalDemolition(recipe));
                recipes++;
            }
            for (var recipe : MSRecipeType.RADIATION_IRRADIATING.getMSRecipeType().getRecipes(level)) {
                registry.addRecipe(MSEmiLayouts.radiationIrradiating(recipe));
                recipes++;
            }
            for (var recipe : MSRecipeType.INFINITY_ORE_REPROCESSING.getMSRecipeType().getRecipes(level)) {
                registry.addRecipe(MSEmiLayouts.infinityOreReprocessing(recipe));
                recipes++;
            }
        }

        // Machines without a real JSON recipe: a single fixed informational "recipe"
        registry.addRecipe(MSEmiLayouts.airCompressor());
        registry.addRecipe(MSEmiLayouts.seawaterPump());

        MekanismElements.logger.info("EMI: registered {} Mekanism: Elements categories with {} machine recipes", MACHINES.size(), recipes);
    }
}
