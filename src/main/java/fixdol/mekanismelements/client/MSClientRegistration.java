package fixdol.mekanismelements.client;

import fixdol.mekanismelements.client.gui.machine.*;
import fixdol.mekanismelements.common.MekanismElements;
import fixdol.mekanismelements.common.registries.MSContainerTypes;
import mekanism.client.ClientRegistrationUtil;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = MekanismElements.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class MSClientRegistration {

    private MSClientRegistration() {
    }

    @SubscribeEvent
    public static void init(FMLClientSetupEvent event) {
        event.enqueueWork(MSClientRegistration::registerContainers);
    }

    private static void registerContainers() {
        ClientRegistrationUtil.registerScreen(MSContainerTypes.ADSORPTION_SEPARATOR, GuiAdsorptionSeparator::new);
        ClientRegistrationUtil.registerScreen(MSContainerTypes.AIR_COMPRESSOR, GuiAirCompressor::new);
        ClientRegistrationUtil.registerScreen(MSContainerTypes.CHEMICAL_DEMOLITION_MACHINE, GuiChemicalDemolitionMachine::new);
        ClientRegistrationUtil.registerScreen(MSContainerTypes.RADIATION_IRRADIATOR, GuiRadiationIrradiator::new);
        ClientRegistrationUtil.registerScreen(MSContainerTypes.INFINITY_ORE_REPROCESSING, GuiInfinityOreReprocessing::new);
        ClientRegistrationUtil.registerScreen(MSContainerTypes.SEAWATER_PUMP, GuiSeawaterPump::new);
    }
}
