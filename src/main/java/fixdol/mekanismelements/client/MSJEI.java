package fixdol.mekanismelements.client;

import fixdol.mekanismelements.client.jei.MSRecipeRegistryHelper;
import fixdol.mekanismelements.client.jei.machine.AdsorptionSeparatorRecipeCategory;
import fixdol.mekanismelements.client.jei.machine.AirCompressorInfoRecipe;
import fixdol.mekanismelements.client.jei.machine.AirCompressorRecipeCategory;
import fixdol.mekanismelements.client.jei.machine.ChemicalDemolitionMachineRecipeCategory;
import fixdol.mekanismelements.client.jei.machine.InfinityOreReprocessingRecipeCategory;
import fixdol.mekanismelements.client.jei.machine.RadiationIrradiatorRecipeCategory;
import fixdol.mekanismelements.client.jei.machine.SeawaterPumpInfoRecipe;
import fixdol.mekanismelements.client.jei.machine.SeawaterPumpRecipeCategory;
import fixdol.mekanismelements.common.MekanismElements;
import fixdol.mekanismelements.common.recipe.MSRecipeType;
import fixdol.mekanismelements.common.registries.MSBlocks;
import mekanism.client.jei.CatalystRegistryHelper;
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
import java.util.List;

@JeiPlugin
public class MSJEI implements IModPlugin {

    @Nonnull
    @Override
    public ResourceLocation getPluginUid() {
        return MekanismElements.rl("jei_plugin");
    }

    @Override
    public void registerItemSubtypes(@Nonnull ISubtypeRegistration registry) {
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {
        IGuiHelper guiHelper = registry.getJeiHelpers().getGuiHelper();
        registry.addRecipeCategories(new AdsorptionSeparatorRecipeCategory(guiHelper, MSJEIRecipeType.ADSORPTION_SEPARATOR));
        registry.addRecipeCategories(new ChemicalDemolitionMachineRecipeCategory(guiHelper, MSJEIRecipeType.CHEMICAL_DEMOLITION_MACHINE));
        registry.addRecipeCategories(new RadiationIrradiatorRecipeCategory(guiHelper, MSJEIRecipeType.RADIATION_IRRADIATOR));
        registry.addRecipeCategories(new AirCompressorRecipeCategory(guiHelper, MSJEIRecipeType.AIR_COMPRESSOR));
        registry.addRecipeCategories(new SeawaterPumpRecipeCategory(guiHelper, MSJEIRecipeType.SEAWATER_PUMP));
        registry.addRecipeCategories(new InfinityOreReprocessingRecipeCategory(guiHelper, MSJEIRecipeType.INFINITY_ORE_REPROCESSING));
    }

    @Override
    public void registerRecipeCatalysts(@Nonnull IRecipeCatalystRegistration registry) {
        CatalystRegistryHelper.register(registry, MSJEIRecipeType.ADSORPTION_SEPARATOR, MSBlocks.ADSORPTION_SEPARATOR);
        CatalystRegistryHelper.register(registry, MSJEIRecipeType.CHEMICAL_DEMOLITION_MACHINE, MSBlocks.CHEMICAL_DEMOLITION_MACHINE);
        CatalystRegistryHelper.register(registry, MSJEIRecipeType.RADIATION_IRRADIATOR, MSBlocks.RADIATION_IRRADIATOR);
        CatalystRegistryHelper.register(registry, MSJEIRecipeType.AIR_COMPRESSOR, MSBlocks.AIR_COMPRESSOR);
        CatalystRegistryHelper.register(registry, MSJEIRecipeType.SEAWATER_PUMP, MSBlocks.SEAWATER_PUMP);
        CatalystRegistryHelper.register(registry, MSJEIRecipeType.INFINITY_ORE_REPROCESSING, MSBlocks.INFINITY_ORE_REPROCESSING);
    }

    @Override
    public void registerRecipes(@NotNull IRecipeRegistration registry) {
        MSRecipeRegistryHelper.register(registry, MSJEIRecipeType.ADSORPTION_SEPARATOR, MSRecipeType.ADSORPTION);
        MSRecipeRegistryHelper.register(registry, MSJEIRecipeType.RADIATION_IRRADIATOR, MSRecipeType.RADIATION_IRRADIATING);
        MSRecipeRegistryHelper.register(registry, MSJEIRecipeType.CHEMICAL_DEMOLITION_MACHINE, MSRecipeType.CHEMICAL_DEMOLITION);
        MSRecipeRegistryHelper.register(registry, MSJEIRecipeType.INFINITY_ORE_REPROCESSING, MSRecipeType.INFINITY_ORE_REPROCESSING);

        MSRecipeRegistryHelper.register(registry, MSJEIRecipeType.AIR_COMPRESSOR, List.of(AirCompressorInfoRecipe.INSTANCE));
        MSRecipeRegistryHelper.register(registry, MSJEIRecipeType.SEAWATER_PUMP, List.of(SeawaterPumpInfoRecipe.INSTANCE));
    }
}
