package fixdol.mekanismelements.client.rei;

import fixdol.mekanismelements.client.MSJEIRecipeType;
import fixdol.mekanismelements.client.jei.machine.AirCompressorRecipeViewerType;
import fixdol.mekanismelements.client.jei.machine.SeawaterPumpRecipeViewerType;
import fixdol.mekanismelements.common.recipe.MSRecipeType;
import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.util.EntryStacks;
import me.shedaniel.rei.forge.REIPluginClient;
import mekanism.client.recipe_viewer.type.IRecipeViewerRecipeType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.level.ItemLike;

import java.util.List;

/**
 * REI side of the recipe viewer integration. Mekanism has no REI support, so the categories draw Mekanism's own GUI
 * elements through {@link MSReiBuilder}.
 */
@REIPluginClient
public class MSRei implements REIClientPlugin {
    private static final List<IRecipeViewerRecipeType<?>> TYPES = List.of(
            MSJEIRecipeType.ADSORPTION_SEPARATOR,
            MSJEIRecipeType.CHEMICAL_DEMOLITION_MACHINE,
            MSJEIRecipeType.RADIATION_IRRADIATOR,
            AirCompressorRecipeViewerType.INSTANCE,
            SeawaterPumpRecipeViewerType.INSTANCE,
            MSJEIRecipeType.INFINITY_ORE_REPROCESSING
    );

    @Override
    public void registerCategories(CategoryRegistry registry) {
        for (IRecipeViewerRecipeType<?> type : TYPES) {
            registry.add(new MSReiCategory(type));
            for (ItemLike workstation : type.workstations()) {
                registry.addWorkstations(CategoryIdentifier.of(type.id()), EntryStacks.of(workstation));
            }
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
