package fixdol.mekanismelements.common.datagen.providers;

import fixdol.mekanismelements.common.MekanismElements;
import fixdol.mekanismelements.common.registries.MSFluids;
import mekanism.common.registration.impl.FluidRegistryObject;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.FluidTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

public class MSFluidTagsProvider extends FluidTagsProvider {

    public MSFluidTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, MekanismElements.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        common("ammonia", MSFluids.AMMONIA);
        common("ammonium_nitrate", MSFluids.AMMONIUM_NITRATE);
        common("beryllium", MSFluids.BERYLLIUM);
        common("bromine", MSFluids.BROMINE);
        common("compressed_air", MSFluids.COMPRESSED_AIR);
        common("helium", MSFluids.HELIUM);
        common("iodine", MSFluids.IODINE);
        common("nitric_acid", MSFluids.NITRIC_ACID);
        common("nitric_oxide", MSFluids.NITRIC_OXIDE);
        common("nitrogen", MSFluids.NITROGEN);
        common("nitrogen_dioxide", MSFluids.NITROGEN_DIOXIDE);
        common("potassium_chloride", MSFluids.POTASSIUM_CHLORIDE);
        common("potassium_hydroxide", MSFluids.POTASSIUM_HYDROXIDE);
        common("potassium_iodide", MSFluids.POTASSIUM_IODIDE);
        common("strontium", MSFluids.STRONTIUM);
        common("water", MSFluids.SEAWATER);
        common("yttrium", MSFluids.YTTRIUM);

        tag(fluidTag("create", "no_infinite_draining")).add(
              MSFluids.AMMONIA.get(),
              MSFluids.AMMONIUM_NITRATE.get(),
              MSFluids.BROMINE.get(),
              MSFluids.BERYLLIUM.get(),
              MSFluids.COMPRESSED_AIR.get(),
              MSFluids.SEAWATER.get(),
              MSFluids.IODINE.get(),
              MSFluids.POTASSIUM_HYDROXIDE.get(),
              MSFluids.POTASSIUM_IODIDE.get(),
              MSFluids.POTASSIUM_CHLORIDE.get(),
              MSFluids.NITROGEN.get(),
              MSFluids.NITRIC_OXIDE.get(),
              MSFluids.NITROGEN_DIOXIDE.get(),
              MSFluids.NITRIC_ACID.get(),
              MSFluids.XENON.get(),
              MSFluids.HELIUM.get(),
              MSFluids.SUPERHEATED_HELIUM.get(),
              MSFluids.STRONTIUM.get(),
              MSFluids.YTTRIUM.get()
        );
    }

    private void common(String path, FluidRegistryObject<?, ?, ?, ?, ?> fluid) {
        tag(fluidTag("c", path)).add(fluid.get(), fluid.getFlowingFluid().get());
    }

    private static TagKey<Fluid> fluidTag(String namespace, String path) {
        return TagKey.create(Registries.FLUID, ResourceLocation.fromNamespaceAndPath(namespace, path));
    }
}
