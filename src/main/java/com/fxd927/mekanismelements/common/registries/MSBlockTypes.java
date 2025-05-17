package com.fxd927.mekanismelements.common.registries;

import com.fxd927.mekanismelements.common.MSLang;
import com.fxd927.mekanismelements.common.config.MSConfig;
import com.fxd927.mekanismelements.common.content.blocktype.MSBlockShapes;
import com.fxd927.mekanismelements.common.content.blocktype.MSMachine;
import com.fxd927.mekanismelements.common.tile.machine.*;
import mekanism.api.Upgrade;
import mekanism.common.block.attribute.Attributes;
import mekanism.generators.common.registries.GeneratorsSounds;

import java.util.EnumSet;

public class MSBlockTypes {
    public static final MSMachine<TileEntityAdsorptionSeparator> ADSORPTION_SEPARATOR = MSMachine.MSMachineBuilder
            .createMSMachine(() -> MSTileEntityTypes.ADSORPTION_SEPARATOR, MSLang.DESCRIPTION_ADSORPTION_SEPARATOR)
            .withGui(() -> MSContainerTypes.ADSORPTION_SEPARATOR)
            .withSound(MSSounds.AIR_COMPRESSOR)
            .withEnergyConfig(MSConfig.usageConfig.adsorptionSeparator, MSConfig.storageConfig.adsorptionSeparator)
            .withCustomShape(MSBlockShapes.ADSORPTION_SEPARATOR)
            .withSupportedUpgrades(EnumSet.of(Upgrade.SPEED, Upgrade.ENERGY, Upgrade.MUFFLING))
            .withComputerSupport("adsorptionSeparator")
            .replace(Attributes.ACTIVE_LIGHT)
            .build();
    public static final MSMachine<TileEntityAirCompressor> AIR_COMPRESSOR = MSMachine.MSMachineBuilder
            .createMSMachine(() -> MSTileEntityTypes.AIR_COMPRESSOR, MSLang.DESCRIPTION_AIR_COMPRESSOR)
            .withGui(() -> MSContainerTypes.AIR_COMPRESSOR)
            .withSound(MSSounds.AIR_COMPRESSOR)
            .withEnergyConfig(MSConfig.usageConfig.airCompressor, MSConfig.storageConfig.airCompressor)
            .withSupportedUpgrades(EnumSet.of(Upgrade.SPEED, Upgrade.ENERGY, Upgrade.MUFFLING))
            .withComputerSupport("airCompressor")
            .replace(Attributes.ACTIVE_LIGHT)
            .build();
    //public static final MSMachine<TileEntityChemicalDemolitionMachine> CHEMICAL_DEMOLITION_MACHINE = MSMachine.MSMachineBuilder
    // .createMSMachine(() -> MSTileEntityTypes.CHEMICAL_DEMOLITION_MACHINE, MSLang.DESCRIPTION_ADSORPTION_SEPARATOR)
    // .withGui(() -> MSContainerTypes.CHEMICAL_DEMOLITION_MACHINE)
    // .withEnergyConfig(MSConfig.usageConfig.adsorptionSeparator, MSConfig.storageConfig.adsorptionSeparator)
    //.withSupportedUpgrades(EnumSet.of(Upgrade.SPEED, Upgrade.ENERGY, Upgrade.MUFFLING))
    // .withComputerSupport("chemicalDemolitionMachine")
    //  .replace(Attributes.ACTIVE_LIGHT)
    //  .build();
    public static final MSMachine<TileEntityRadiationIrradiator> RADIATION_IRRADIATOR = MSMachine.MSMachineBuilder
            .createMSMachine(() -> MSTileEntityTypes.RADIATION_IRRADIATOR, MSLang.DESCRIPTION_RADIATION_IRRADIATOR)
            .withGui(() -> MSContainerTypes.RADIATION_IRRADIATOR)
            .withSound(GeneratorsSounds.FISSION_REACTOR)
            .withEnergyConfig(MSConfig.usageConfig.radiationIrradiator, MSConfig.storageConfig.radiationIrradiator)
            .withSupportedUpgrades(EnumSet.of(Upgrade.SPEED, Upgrade.ENERGY, Upgrade.MUFFLING))
            .withComputerSupport("radiationIrradiator")
            .replace(Attributes.ACTIVE_FULL_LIGHT)
            .build();
    // public static final MSMachine<TileEntityOrganicLiquidExtractor> ORGANIC_LIQUID_EXTRACTOR = MSMachine.MSMachineBuilder
            //.createMSMachine(() -> MSTileEntityTypes.ORGANIC_LIQUID_EXTRACTOR, MSLang.DESCRIPTION_ORGANIC_LIQUID_EXTRACTOR)
            //.withGui(() -> MSContainerTypes.ORGANIC_LIQUID_EXTRACTOR)
            //.withEnergyConfig(MSConfig.usageConfig.organicLiquidExtractor, MSConfig.storageConfig.organicLiquidExtractor)
            //.withSupportedUpgrades(EnumSet.of(Upgrade.SPEED, Upgrade.ENERGY))
            //.withComputerSupport("organicLiquidExtractor")
            //.replace(Attributes.ACTIVE)
            //.build();
    public static final MSMachine<TileEntitySeawaterPump> SEAWATER_PUMP = MSMachine.MSMachineBuilder
            .createMSMachine(() -> MSTileEntityTypes.SEAWATER_PUMP, MSLang.DESCRIPTION_SEAWATER_PUMP)
            .withGui(() -> MSContainerTypes.SEAWATER_PUMP)
            .withEnergyConfig(MSConfig.usageConfig.seawaterPump, MSConfig.storageConfig.seawaterPump)
            .withSupportedUpgrades(EnumSet.of(Upgrade.SPEED, Upgrade.ENERGY))
            .withComputerSupport("seawaterPump")
            .replace(Attributes.ACTIVE)
    .build();

    private MSBlockTypes(){
    }
}
