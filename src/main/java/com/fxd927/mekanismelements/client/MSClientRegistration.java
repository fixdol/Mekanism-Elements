package com.fxd927.mekanismelements.client;

import com.fxd927.mekanismelements.client.gui.machine.*;
import com.fxd927.mekanismelements.common.MekanismElements;
import com.fxd927.mekanismelements.common.registries.MSContainerTypes;
import mekanism.client.ClientRegistrationUtil;
import net.minecraft.core.registries.Registries;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegisterEvent;

@Mod.EventBusSubscriber(modid = MekanismElements.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class MSClientRegistration {

    private MSClientRegistration() {
    }

    @SubscribeEvent(priority = EventPriority.LOW)
    public static void registerContainers(RegisterEvent event) {
        event.register(Registries.MENU, helper -> {
            ClientRegistrationUtil.registerScreen(MSContainerTypes.ADSORPTION_SEPARATOR, GuiAdsorptionSeparator::new);
            ClientRegistrationUtil.registerScreen(MSContainerTypes.AIR_COMPRESSOR, GuiAirCompressor::new);
            //ClientRegistrationUtil.registerScreen(MSContainerTypes.CHEMICAL_DEMOLITION_MACHINE, GuiChemicalDemolitionMachine::new);
            ClientRegistrationUtil.registerScreen(MSContainerTypes.RADIATION_IRRADIATOR, GuiRadiationIrradiator::new);

            //ClientRegistrationUtil.registerScreen(MSContainerTypes.ADSORPTION_TYPE_SEAWATER_METAL_EXTRACTOR, GuiAdsorptionTypeSeawaterMetalExtractor::new);
            //ClientRegistrationUtil.registerScreen(MSContainerTypes.ORGANIC_LIQUID_EXTRACTOR, GuiOrganicLiquidExtractor::new);
            ClientRegistrationUtil.registerScreen(MSContainerTypes.SEAWATER_PUMP, GuiSeawaterPump::new);
        });
    }
}
