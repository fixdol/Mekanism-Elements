package fixdol.mekanismelements.client.emi;

import dev.emi.emi.api.EmiEntrypoint;
import dev.emi.emi.api.EmiPlugin;
import dev.emi.emi.api.EmiRegistry;
import dev.emi.emi.api.stack.EmiStack;
import fixdol.mekanismelements.api.recipes.AdsorptionRecipe;
import fixdol.mekanismelements.api.recipes.ChemicalDemolitionRecipe;
import fixdol.mekanismelements.api.recipes.InfinityOreReprocessingRecipe;
import fixdol.mekanismelements.api.recipes.RadiationIrradiatingRecipe;
import fixdol.mekanismelements.client.MSJEIRecipeType;
import fixdol.mekanismelements.client.jei.machine.AirCompressorInfoRecipe;
import fixdol.mekanismelements.client.jei.machine.AirCompressorRecipeViewerType;
import fixdol.mekanismelements.client.jei.machine.SeawaterPumpInfoRecipe;
import fixdol.mekanismelements.client.jei.machine.SeawaterPumpRecipeViewerType;
import fixdol.mekanismelements.common.MekanismElements;
import fixdol.mekanismelements.common.recipe.IMSRecipeTypeProvider;
import fixdol.mekanismelements.common.recipe.MSRecipeType;
import mekanism.api.recipes.MekanismRecipe;
import mekanism.client.recipe_viewer.emi.MekanismEmi;
import mekanism.client.recipe_viewer.emi.MekanismEmiRecipeCategory;
import mekanism.client.recipe_viewer.type.IRecipeViewerRecipeType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ItemLike;

import java.util.List;
import java.util.function.Function;

/**
 * EMI side of the recipe viewer integration. Mekanism ships its own EMI plugin, so the categories reuse its base
 * classes and the same recipe viewer types the JEI categories use. EMI also imports JEI plugins, which is why
 * {@link fixdol.mekanismelements.client.MSJEI} registers nothing while EMI is installed: otherwise every
 * machine would show up twice.
 */
@EmiEntrypoint
public class MSEmi implements EmiPlugin {
    @Override
    public void register(EmiRegistry registry) {
        ClientLevel level = Minecraft.getInstance().level;

        int recipes = addRecipes(registry, MSJEIRecipeType.ADSORPTION_SEPARATOR, MSRecipeType.ADSORPTION, level, AdsorptionRecipe::getId, AdsorptionEmiRecipe::new);
        recipes += addRecipes(registry, MSJEIRecipeType.CHEMICAL_DEMOLITION_MACHINE, MSRecipeType.CHEMICAL_DEMOLITION, level, ChemicalDemolitionRecipe::getId, ChemicalDemolitionEmiRecipe::new);
        recipes += addRecipes(registry, MSJEIRecipeType.RADIATION_IRRADIATOR, MSRecipeType.RADIATION_IRRADIATING, level, RadiationIrradiatingRecipe::getId, RadiationIrradiatingEmiRecipe::new);
        recipes += addRecipes(registry, MSJEIRecipeType.INFINITY_ORE_REPROCESSING, MSRecipeType.INFINITY_ORE_REPROCESSING, level, InfinityOreReprocessingRecipe::getId, InfinityOreReprocessingEmiRecipe::new);

        // Machines without a real JSON recipe: a single fixed informational "recipe"
        MekanismEmiRecipeCategory airCompressor = addCategory(registry, AirCompressorRecipeViewerType.INSTANCE);
        registry.addRecipe(new AirCompressorEmiRecipe(airCompressor, MekanismElements.rl("/air_compressor"), AirCompressorInfoRecipe.INSTANCE));
        MekanismEmiRecipeCategory seawaterPump = addCategory(registry, SeawaterPumpRecipeViewerType.INSTANCE);
        registry.addRecipe(new SeawaterPumpEmiRecipe(seawaterPump, MekanismElements.rl("/seawater_pump"), SeawaterPumpInfoRecipe.INSTANCE));

        MekanismElements.logger.info("EMI: registered 6 Mekanism: Elements categories with {} machine recipes", recipes);
    }

    private static <RECIPE extends MekanismRecipe<?>> int addRecipes(EmiRegistry registry, IRecipeViewerRecipeType<RECIPE> viewerType,
                                                                      IMSRecipeTypeProvider<RECIPE, ?> type, ClientLevel level,
                                                                      Function<RECIPE, ResourceLocation> idGetter,
                                                                      MekanismEmi.BasicRecipeCreator<RECIPE> creator) {
        MekanismEmiRecipeCategory category = addCategory(registry, viewerType);
        if (level == null) {
            return 0;
        }
        List<RECIPE> recipes = type.getMSRecipeType().getRecipes(level);
        for (RECIPE recipe : recipes) {
            registry.addRecipe(creator.create(category, idGetter.apply(recipe), recipe));
        }
        return recipes.size();
    }

    private static MekanismEmiRecipeCategory addCategory(EmiRegistry registry, IRecipeViewerRecipeType<?> viewerType) {
        MekanismEmiRecipeCategory category = MekanismEmiRecipeCategory.create(viewerType);
        registry.addCategory(category);
        for (ItemLike workstation : viewerType.workstations()) {
            registry.addWorkstation(category, EmiStack.of(workstation));
        }
        return category;
    }
}
