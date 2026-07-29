package fixdol.mekanismelements.client;

import fixdol.mekanismelements.client.jei.MSRecipeRegistryHelper;
import fixdol.mekanismelements.client.jei.machine.AdsorptionSeparatorRecipeCategory;
import fixdol.mekanismelements.client.jei.machine.AirCompressorInfoRecipe;
import fixdol.mekanismelements.client.jei.machine.AirCompressorRecipeCategory;
import fixdol.mekanismelements.client.jei.machine.AirCompressorRecipeViewerType;
import fixdol.mekanismelements.client.jei.machine.ChemicalDemolitionMachineRecipeCategory;
import fixdol.mekanismelements.client.jei.machine.RadiationIrradiatorRecipeCategory;
import fixdol.mekanismelements.client.jei.machine.SeawaterPumpInfoRecipe;
import fixdol.mekanismelements.client.jei.machine.SeawaterPumpRecipeCategory;
import fixdol.mekanismelements.client.jei.machine.SeawaterPumpRecipeViewerType;
import fixdol.mekanismelements.client.jei.machine.InfinityOreReprocessingRecipeCategory;
import fixdol.mekanismelements.common.MekanismElements;
import fixdol.mekanismelements.common.recipe.MSRecipeType;
import fixdol.mekanismelements.common.registries.MSBlocks;
import mekanism.client.recipe_viewer.jei.CatalystRegistryHelper;
import mekanism.client.recipe_viewer.jei.MekanismJEI;
import mekanism.client.recipe_viewer.type.IRecipeViewerRecipeType;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.registration.ISubtypeRegistration;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static fixdol.mekanismelements.client.MSJEIRecipeType.*;

@JeiPlugin
public class MSJEI implements IModPlugin {
    @Nonnull
    @Override
    public ResourceLocation getPluginUid() {
        return MekanismElements.rl("jei_plugin");
    }

    @Override
    public void registerItemSubtypes(@Nonnull ISubtypeRegistration registry) {
        // Manually collect block items since getAllBlocks() doesn't exist
        // Note: This may need adjustment based on MekanismJEI.registerItemSubtypes signature
        // For now, skip this if the method signature doesn't match
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {
        IGuiHelper guiHelper = registry.getJeiHelpers().getGuiHelper();
        registry.addRecipeCategories(new AdsorptionSeparatorRecipeCategory(guiHelper, MSJEIRecipeType.ADSORPTION_SEPARATOR));
        registry.addRecipeCategories(new ChemicalDemolitionMachineRecipeCategory(guiHelper, MSJEIRecipeType.CHEMICAL_DEMOLITION_MACHINE));
        registry.addRecipeCategories(new RadiationIrradiatorRecipeCategory(guiHelper, MSJEIRecipeType.RADIATION_IRRADIATOR));
        registry.addRecipeCategories(new AirCompressorRecipeCategory(guiHelper, AirCompressorRecipeViewerType.INSTANCE));
        registry.addRecipeCategories(new SeawaterPumpRecipeCategory(guiHelper, SeawaterPumpRecipeViewerType.INSTANCE));
        registry.addRecipeCategories(new InfinityOreReprocessingRecipeCategory(guiHelper, MSJEIRecipeType.INFINITY_ORE_REPROCESSING));
    }

    @Override
    public void registerRecipeCatalysts(@Nonnull IRecipeCatalystRegistration registry) {
        // Register catalysts using the recipe types directly
        CatalystRegistryHelper.register(registry, MSJEIRecipeType.ADSORPTION_SEPARATOR);
        CatalystRegistryHelper.register(registry, MSJEIRecipeType.CHEMICAL_DEMOLITION_MACHINE);
        CatalystRegistryHelper.register(registry, MSJEIRecipeType.RADIATION_IRRADIATOR);
        CatalystRegistryHelper.register(registry, AirCompressorRecipeViewerType.INSTANCE);
        CatalystRegistryHelper.register(registry, SeawaterPumpRecipeViewerType.INSTANCE);
        CatalystRegistryHelper.register(registry, MSJEIRecipeType.INFINITY_ORE_REPROCESSING);
    }

    @Override
    public void registerRecipes(@NotNull IRecipeRegistration registry) {
        MSRecipeRegistryHelper.register(registry, ADSORPTION_SEPARATOR, MSRecipeType.ADSORPTION);
        MSRecipeRegistryHelper.register(registry, RADIATION_IRRADIATOR, MSRecipeType.RADIATION_IRRADIATING);
        MSRecipeRegistryHelper.register(registry, CHEMICAL_DEMOLITION_MACHINE, MSRecipeType.CHEMICAL_DEMOLITION);
        MSRecipeRegistryHelper.register(registry, INFINITY_ORE_REPROCESSING, MSRecipeType.INFINITY_ORE_REPROCESSING);

        // Maquinas sin receta JSON real: se registra una unica "receta" informativa fija
        MSRecipeRegistryHelper.register(registry, AirCompressorRecipeViewerType.INSTANCE,
                List.of(AirCompressorInfoRecipe.INSTANCE));
        MSRecipeRegistryHelper.register(registry, SeawaterPumpRecipeViewerType.INSTANCE,
                List.of(SeawaterPumpInfoRecipe.INSTANCE));
    }
}