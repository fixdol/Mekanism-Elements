package fixdol.mekanismelements.client;

import fixdol.mekanismelements.client.gui.machine.*;
import fixdol.mekanismelements.common.MekanismElements;
import fixdol.mekanismelements.common.registries.MSContainerTypes;
import fixdol.mekanismelements.common.registries.MSFluids;
import mekanism.client.ClientRegistrationUtil;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterFluidModelsEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;

@EventBusSubscriber(modid = MekanismElements.MODID, value = Dist.CLIENT)
public class MSClientRegistration {

    private MSClientRegistration() {
    }

    @SubscribeEvent
    public static void registerContainers(RegisterMenuScreensEvent event) {
        ClientRegistrationUtil.registerScreen(event, MSContainerTypes.ADSORPTION_SEPARATOR, GuiAdsorptionSeparator::new);
        ClientRegistrationUtil.registerScreen(event, MSContainerTypes.AIR_COMPRESSOR, GuiAirCompressor::new);
        ClientRegistrationUtil.registerScreen(event, MSContainerTypes.CHEMICAL_DEMOLITION_MACHINE, GuiChemicalDemolitionMachine::new);
        ClientRegistrationUtil.registerScreen(event, MSContainerTypes.RADIATION_IRRADIATOR, GuiRadiationIrradiator::new);
        ClientRegistrationUtil.registerScreen(event, MSContainerTypes.INFINITY_ORE_REPROCESSING, GuiInfinityOreReprocessing::new);
        //ClientRegistrationUtil.registerScreen(event, MSContainerTypes.ADSORPTION_TYPE_SEAWATER_METAL_EXTRACTOR, GuiAdsorptionTypeSeawaterMetalExtractor::new);
        //ClientRegistrationUtil.registerScreen(event, MSContainerTypes.ORGANIC_LIQUID_EXTRACTOR, GuiOrganicLiquidExtractor::new);
        ClientRegistrationUtil.registerScreen(event, MSContainerTypes.SEAWATER_PUMP, GuiSeawaterPump::new);
    }

    /// Our fluids are Mekanism fluid types, so their sprites and tint are registered through Mekanism's own helpers
    @SubscribeEvent
    public static void registerFluidModels(RegisterFluidModelsEvent event) {
        ClientRegistrationUtil.registerFluidModels(event, MSFluids.FLUIDS);
    }

    @SubscribeEvent
    public static void registerClientExtensions(RegisterClientExtensionsEvent event) {
        ClientRegistrationUtil.registerFluidExtensions(event, MSFluids.FLUIDS);
    }
}
