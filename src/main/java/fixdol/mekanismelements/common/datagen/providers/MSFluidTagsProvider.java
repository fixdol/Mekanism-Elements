package fixdol.mekanismelements.common.datagen.providers;

import java.util.concurrent.CompletableFuture;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraft.core.HolderLookup;
import fixdol.mekanismelements.common.datagen.providers.MSFluidTagsProvider;
import fixdol.mekanismelements.common.registries.MSFluids;
import fixdol.mekanismelements.common.MekanismElements;
import net.minecraft.data.PackOutput;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;

import mekanism.common.registration.impl.FluidRegistryObject;
import net.minecraft.data.tags.FluidTagsProvider;
import net.minecraft.world.level.material.Fluid;


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
              MSFluids.AMMONIA.getFluid(),
              MSFluids.AMMONIUM_NITRATE.getFluid(),
              MSFluids.BROMINE.getFluid(),
              MSFluids.BERYLLIUM.getFluid(),
              MSFluids.COMPRESSED_AIR.getFluid(),
              MSFluids.SEAWATER.getFluid(),
              MSFluids.IODINE.getFluid(),
              MSFluids.POTASSIUM_HYDROXIDE.getFluid(),
              MSFluids.POTASSIUM_IODIDE.getFluid(),
              MSFluids.POTASSIUM_CHLORIDE.getFluid(),
              MSFluids.NITROGEN.getFluid(),
              MSFluids.NITRIC_OXIDE.getFluid(),
              MSFluids.NITROGEN_DIOXIDE.getFluid(),
              MSFluids.NITRIC_ACID.getFluid(),
              MSFluids.XENON.getFluid(),
              MSFluids.HELIUM.getFluid(),
              MSFluids.SUPERHEATED_HELIUM.getFluid(),
              MSFluids.STRONTIUM.getFluid(),
              MSFluids.YTTRIUM.getFluid()
        );
    }

    private void common(String path, FluidRegistryObject<?, ?, ?, ?, ?> fluid) {
        tag(fluidTag("forge", path)).add(fluid.getFluid(), fluid.getFlowingFluid());
    }

    private static TagKey<Fluid> fluidTag(String namespace, String path) {
        return TagKey.create(Registries.FLUID, new ResourceLocation(namespace, path));
    }
}
