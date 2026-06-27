package com.fxd927.mekanismelements.common.datagen.providers;

import com.fxd927.mekanismelements.common.registries.MSGases;
import mekanism.api.datamaps.IMekanismDataMapTypes;
import mekanism.api.datamaps.chemical.attribute.ChemicalFuel;
import mekanism.api.datamaps.chemical.attribute.ChemicalRadioactivity;
import mekanism.api.datamaps.chemical.attribute.CooledCoolant;
import mekanism.api.datamaps.chemical.attribute.HeatedCoolant;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DataMapProvider;

import java.util.concurrent.CompletableFuture;

public class ModDataMapProvider extends DataMapProvider {

    public ModDataMapProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(packOutput, lookupProvider);
    }

    @Override
    protected void gather() {
        // chemical_attribute_heated_coolant.json
        builder(IMekanismDataMapTypes.INSTANCE.heatedChemicalCoolant())
                .add(
                        MSGases.SUPERHEATED_HELIUM.get().getAsHolder(),
                        new HeatedCoolant(MSGases.HELIUM.get().getAsHolder(), 100.0),
                        false
                );

        // chemical_attribute_cooled_coolant.json
        builder(IMekanismDataMapTypes.INSTANCE.cooledChemicalCoolant())
                .add(
                        MSGases.HELIUM.get().getAsHolder(),
                        new CooledCoolant(MSGases.SUPERHEATED_HELIUM.get().getAsHolder(), 100.0, 1.0),
                        false
                )
                .add(
                        MSGases.AMMONIUM_NITRATE_SOLUTION.get().getAsHolder(),
                        new CooledCoolant(MSGases.AMMONIUM_NITRATE_SOLUTION.get().getAsHolder(), 120.0, 0.7),
                        false
                );

        // chemical_attribute_fuel.json
        builder(IMekanismDataMapTypes.INSTANCE.chemicalFuel())
                .add(
                        MSGases.AMMONIA.get().getAsHolder(),
                        new ChemicalFuel(300, 900000L),
                        false
                );

        // chemical_attribute_radioactivity.json
        builder(IMekanismDataMapTypes.INSTANCE.chemicalRadioactivity())
                .add(
                        MSGases.CALIFORNIUM.get().getAsHolder(),
                        new ChemicalRadioactivity(0.1),
                        false
                )
                .add(
                        MSGases.CURIUM.get().getAsHolder(),
                        new ChemicalRadioactivity(0.07),
                        false
                );
    }
}