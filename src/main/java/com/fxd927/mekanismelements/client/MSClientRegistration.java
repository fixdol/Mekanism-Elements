package com.fxd927.mekanismelements.client;

import com.fxd927.mekanismelements.client.gui.machine.*;
import com.fxd927.mekanismelements.common.MekanismElements;
import com.fxd927.mekanismelements.common.gas.MSChemicalConstants;
import com.fxd927.mekanismelements.common.registries.MSContainerTypes;
import com.fxd927.mekanismelements.common.registries.MSFluids;
import mekanism.client.ClientRegistrationUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.material.FluidState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;

import static com.fxd927.mekanismelements.common.MekanismElements.rl;

@EventBusSubscriber(modid = MekanismElements.MODID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
public class MSClientRegistration {

    private MSClientRegistration() {
    }

    @SubscribeEvent
    public static void registerContainers(RegisterMenuScreensEvent event) {
        ClientRegistrationUtil.registerScreen(event, MSContainerTypes.ADSORPTION_SEPARATOR, GuiAdsorptionSeparator::new);
        ClientRegistrationUtil.registerScreen(event, MSContainerTypes.AIR_COMPRESSOR, GuiAirCompressor::new);
        //ClientRegistrationUtil.registerScreen(event, MSContainerTypes.CHEMICAL_DEMOLITION_MACHINE, GuiChemicalDemolitionMachine::new);
        ClientRegistrationUtil.registerScreen(event, MSContainerTypes.RADIATION_IRRADIATOR, GuiRadiationIrradiator::new);

        //ClientRegistrationUtil.registerScreen(event, MSContainerTypes.ADSORPTION_TYPE_SEAWATER_METAL_EXTRACTOR, GuiAdsorptionTypeSeawaterMetalExtractor::new);
        //ClientRegistrationUtil.registerScreen(event, MSContainerTypes.ORGANIC_LIQUID_EXTRACTOR, GuiOrganicLiquidExtractor::new);
        ClientRegistrationUtil.registerScreen(event, MSContainerTypes.SEAWATER_PUMP, GuiSeawaterPump::new);
    }

    /**
     * Ensure our fluids have non-null client textures.
     *
     * This fixes:
     * - Embeddium crash when rendering custom fluids in-world (null sprite lookup)
     * - Missing texture tiles in Mekanism GUIs / creative tanks that rely on FluidType textures
     */
    @SubscribeEvent
    public static void registerClientExtensions(RegisterClientExtensionsEvent event) {
        // Mekanism's generic liquid sprites (used for tinted chemical fluids)
        final ResourceLocation mekanismStill = ResourceLocation.fromNamespaceAndPath("mekanism", "liquid/liquid");
        final ResourceLocation mekanismFlow = ResourceLocation.fromNamespaceAndPath("mekanism", "liquid/liquid_flow");

        // Seawater uses custom sprites (and still needs explicit client extensions to avoid null textures)
        registerFluidType(event, MSFluids.SEAWATER.getFluidType(), rl("block/liquid/seawater_still"), rl("block/liquid/seawater_flow"), MSChemicalConstants.SEAWATER.getColor());

        // All other fluids: use Mekanism's generic tinted liquid sprites + our chemical tint
        registerFluidType(event, MSFluids.AMMONIA.getFluidType(), mekanismStill, mekanismFlow, MSChemicalConstants.AMMONIA.getColor());
        registerFluidType(event, MSFluids.AMMONIUM_NITRATE.getFluidType(), mekanismStill, mekanismFlow, MSChemicalConstants.AMMONIUM_NITRATE.getColor());
        registerFluidType(event, MSFluids.BERYLLIUM.getFluidType(), mekanismStill, mekanismFlow, MSChemicalConstants.BERYLLIUM.getColor());
        registerFluidType(event, MSFluids.BROMINE.getFluidType(), mekanismStill, mekanismFlow, MSChemicalConstants.BROMINE.getColor());
        registerFluidType(event, MSFluids.COMPRESSED_AIR.getFluidType(), mekanismStill, mekanismFlow, MSChemicalConstants.COMPRESSED_AIR.getColor());
        registerFluidType(event, MSFluids.HELIUM.getFluidType(), mekanismStill, mekanismFlow, MSChemicalConstants.HELIUM.getColor());
        registerFluidType(event, MSFluids.HYDROGEN_CYANIDE.getFluidType(), mekanismStill, mekanismFlow, MSChemicalConstants.HYDROGEN_CYANIDE.getColor());
        registerFluidType(event, MSFluids.SUPERHEATED_HELIUM.getFluidType(), mekanismStill, mekanismFlow, MSChemicalConstants.SUPERHEATED_HELIUM.getColor());
        registerFluidType(event, MSFluids.IODINE.getFluidType(), mekanismStill, mekanismFlow, MSChemicalConstants.IODINE.getColor());
        registerFluidType(event, MSFluids.METHANE.getFluidType(), mekanismStill, mekanismFlow, MSChemicalConstants.METHANE.getColor());
        registerFluidType(event, MSFluids.NITRIC_ACID.getFluidType(), mekanismStill, mekanismFlow, MSChemicalConstants.NITRIC_ACID.getColor());
        registerFluidType(event, MSFluids.NITROGEN.getFluidType(), mekanismStill, mekanismFlow, MSChemicalConstants.NITROGEN.getColor());
        registerFluidType(event, MSFluids.NITROGEN_DIOXIDE.getFluidType(), mekanismStill, mekanismFlow, MSChemicalConstants.NITROGEN_DIOXIDE.getColor());
        registerFluidType(event, MSFluids.NITRIC_OXIDE.getFluidType(), mekanismStill, mekanismFlow, MSChemicalConstants.NITRIC_OXIDE.getColor());
        registerFluidType(event, MSFluids.POTASSIUM_CHLORIDE.getFluidType(), mekanismStill, mekanismFlow, MSChemicalConstants.POTASSIUM_CHLORIDE.getColor());
        registerFluidType(event, MSFluids.POTASSIUM_CYANIDE.getFluidType(), mekanismStill, mekanismFlow, MSChemicalConstants.POTASSIUM_CYANIDE.getColor());
        registerFluidType(event, MSFluids.POTASSIUM_HYDROXIDE.getFluidType(), mekanismStill, mekanismFlow, MSChemicalConstants.POTASSIUM_HYDROXIDE.getColor());
        registerFluidType(event, MSFluids.POTASSIUM_IODIDE.getFluidType(), mekanismStill, mekanismFlow, MSChemicalConstants.POTASSIUM_IODIDE.getColor());
        registerFluidType(event, MSFluids.STRONTIUM.getFluidType(), mekanismStill, mekanismFlow, MSChemicalConstants.STRONTIUM.getColor());
        registerFluidType(event, MSFluids.XENON.getFluidType(), mekanismStill, mekanismFlow, MSChemicalConstants.XENON.getColor());
        registerFluidType(event, MSFluids.YTTRIUM.getFluidType(), mekanismStill, mekanismFlow, MSChemicalConstants.YTTRIUM.getColor());
    }

    private static void registerFluidType(RegisterClientExtensionsEvent event, net.neoforged.neoforge.fluids.FluidType fluidType,
          ResourceLocation still, ResourceLocation flowing, int tint) {
        if (event.isFluidTypeRegistered(fluidType)) {
            return;
        }
        event.registerFluidType(new IClientFluidTypeExtensions() {
            @Override
            public ResourceLocation getStillTexture() {
                return still;
            }

            @Override
            public ResourceLocation getFlowingTexture() {
                return flowing;
            }

            @Override
            public int getTintColor(FluidState state, BlockAndTintGetter level, BlockPos pos) {
                return tint;
            }

            @Override
            public int getTintColor() {
                return tint;
            }
        }, fluidType);
    }
}

