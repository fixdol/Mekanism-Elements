package fixdol.mekanismelements.common.datagen.providers;

import fixdol.mekanismelements.common.registries.MSGases;
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
    protected void gather(HolderLookup.Provider provider) {
        // chemical_attribute_heated_coolant.json
        builder(IMekanismDataMapTypes.INSTANCE.heatedChemicalCoolant())
                .add(
                        MSGases.SUPERHEATED_HELIUM,
                        new HeatedCoolant(MSGases.HELIUM, 100.0),
                        false
                );

        // chemical_attribute_cooled_coolant.json
        builder(IMekanismDataMapTypes.INSTANCE.cooledChemicalCoolant())
                .add(
                        MSGases.HELIUM,
                        new CooledCoolant(MSGases.SUPERHEATED_HELIUM, 100.0, 1.0),
                        false
                )
                .add(
                        MSGases.AMMONIUM_NITRATE_SOLUTION,
                        new CooledCoolant(MSGases.AMMONIUM_NITRATE_SOLUTION, 120.0, 0.7),
                        false
                );

        // chemical_attribute_fuel.json
        builder(IMekanismDataMapTypes.INSTANCE.chemicalFuel())
                .add(
                        MSGases.AMMONIA,
                        new ChemicalFuel(10, 99999),
                        false
                )
                .add(
                        MSGases.AMERICIUM,
                        new ChemicalFuel(500, 60000),
                        false
                );

        // chemical_attribute_radioactivity.json
        builder(IMekanismDataMapTypes.INSTANCE.chemicalRadioactivity())
                .add(
                        MSGases.CALIFORNIUM,
                        new ChemicalRadioactivity(0.1),
                        false
                )
                .add(
                        MSGases.CURIUM,
                        new ChemicalRadioactivity(0.07),
                        false
                );
    }
}