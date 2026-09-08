package fixdol.mekanismelements.common.datagen.providers;

import fixdol.mekanismelements.common.MekanismElements;
import fixdol.mekanismelements.common.registries.MSBlocks;
import fixdol.mekanismelements.common.registries.MSFluids;
import mekanism.common.block.attribute.Attribute;
import mekanism.common.block.states.BlockStateHelper;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class MSBlockStateProvider extends BlockStateProvider {

    private static final ResourceLocation LIQUID_PARTICLE = ResourceLocation.fromNamespaceAndPath("mekanism", "liquid/liquid");

    public MSBlockStateProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, MekanismElements.MODID, existingFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        machine(MSBlocks.ADSORPTION_SEPARATOR.get(), MSMachineModels.adsorptionSeparator(models()), null);
        machine(MSBlocks.AIR_COMPRESSOR.get(), MSMachineModels.airCompressor(models()), MSMachineModels.airCompressorActive(models()));
        machine(MSBlocks.CHEMICAL_DEMOLITION_MACHINE.get(), MSMachineModels.chemicalDemolitionMachine(models()), null);
        machine(MSBlocks.RADIATION_IRRADIATOR.get(), MSMachineModels.radiationIrradiator(models()), MSMachineModels.radiationIrradiatorActive(models()));
        machine(MSBlocks.SEAWATER_PUMP.get(), MSMachineModels.seawaterPump(models()), null);
        cubeMachine(MSBlocks.INFINITY_ORE_REPROCESSING.get());

        fluidBlock(MSFluids.BERYLLIUM.getBlock());
        fluidBlock(MSFluids.HELIUM.getBlock());
        fluidBlock(MSFluids.SUPERHEATED_HELIUM.getBlock());
        fluidBlock(MSFluids.STRONTIUM.getBlock());
        fluidBlock(MSFluids.YTTRIUM.getBlock());

        concrete(MSBlocks.HIGH_QUALITY_CONCRETE.get(), MSBlocks.HIGH_QUALITY_CONCRETE_SLABS.get(), MSBlocks.HIGH_QUALITY_CONCRETE_STAIRS.get());
        concrete(MSBlocks.AQUA_HIGH_QUALITY_CONCRETE.get(), MSBlocks.AQUA_HIGH_QUALITY_CONCRETE_SLABS.get(), MSBlocks.AQUA_HIGH_QUALITY_CONCRETE_STAIRS.get());
        concrete(MSBlocks.BLACK_HIGH_QUALITY_CONCRETE.get(), MSBlocks.BLACK_HIGH_QUALITY_CONCRETE_SLABS.get(), MSBlocks.BLACK_HIGH_QUALITY_CONCRETE_STAIRS.get());
        concrete(MSBlocks.BLUE_HIGH_QUALITY_CONCRETE.get(), MSBlocks.BLUE_HIGH_QUALITY_CONCRETE_SLABS.get(), MSBlocks.BLUE_HIGH_QUALITY_CONCRETE_STAIRS.get());
        concrete(MSBlocks.BROWN_HIGH_QUALITY_CONCRETE.get(), MSBlocks.BROWN_HIGH_QUALITY_CONCRETE_SLABS.get(), MSBlocks.BROWN_HIGH_QUALITY_CONCRETE_STAIRS.get());
        concrete(MSBlocks.CYAN_HIGH_QUALITY_CONCRETE.get(), MSBlocks.CYAN_HIGH_QUALITY_CONCRETE_SLABS.get(), MSBlocks.CYAN_HIGH_QUALITY_CONCRETE_STAIRS.get());
        concrete(MSBlocks.DARK_RED_HIGH_QUALITY_CONCRETE.get(), MSBlocks.DARK_RED_HIGH_QUALITY_CONCRETE_SLABS.get(), MSBlocks.DARK_RED_HIGH_QUALITY_CONCRETE_STAIRS.get());
        concrete(MSBlocks.GRAY_HIGH_QUALITY_CONCRETE.get(), MSBlocks.GRAY_HIGH_QUALITY_CONCRETE_SLABS.get(), MSBlocks.GRAY_HIGH_QUALITY_CONCRETE_STAIRS.get());
        concrete(MSBlocks.GREEN_HIGH_QUALITY_CONCRETE.get(), MSBlocks.GREEN_HIGH_QUALITY_CONCRETE_SLABS.get(), MSBlocks.GREEN_HIGH_QUALITY_CONCRETE_STAIRS.get());
        concrete(MSBlocks.LIGHT_BLUE_HIGH_QUALITY_CONCRETE.get(), MSBlocks.LIGHT_BLUE_HIGH_QUALITY_CONCRETE_SLABS.get(), MSBlocks.LIGHT_BLUE_HIGH_QUALITY_CONCRETE_STAIRS.get());
        concrete(MSBlocks.LIGHT_GRAY_HIGH_QUALITY_CONCRETE.get(), MSBlocks.LIGHT_GRAY_HIGH_QUALITY_CONCRETE_SLABS.get(), MSBlocks.LIGHT_GRAY_HIGH_QUALITY_CONCRETE_STAIRS.get());
        concrete(MSBlocks.LIME_HIGH_QUALITY_CONCRETE.get(), MSBlocks.LIME_HIGH_QUALITY_CONCRETE_SLABS.get(), MSBlocks.LIME_HIGH_QUALITY_CONCRETE_STAIRS.get());
        concrete(MSBlocks.MAGENTA_HIGH_QUALITY_CONCRETE.get(), MSBlocks.MAGENTA_HIGH_QUALITY_CONCRETE_SLABS.get(), MSBlocks.MAGENTA_HIGH_QUALITY_CONCRETE_STAIRS.get());
        concrete(MSBlocks.ORANGE_HIGH_QUALITY_CONCRETE.get(), MSBlocks.ORANGE_HIGH_QUALITY_CONCRETE_SLABS.get(), MSBlocks.ORANGE_HIGH_QUALITY_CONCRETE_STAIRS.get());
        concrete(MSBlocks.PINK_HIGH_QUALITY_CONCRETE.get(), MSBlocks.PINK_HIGH_QUALITY_CONCRETE_SLABS.get(), MSBlocks.PINK_HIGH_QUALITY_CONCRETE_STAIRS.get());
        concrete(MSBlocks.PURPLE_HIGH_QUALITY_CONCRETE.get(), MSBlocks.PURPLE_HIGH_QUALITY_CONCRETE_SLABS.get(), MSBlocks.PURPLE_HIGH_QUALITY_CONCRETE_STAIRS.get());
        concrete(MSBlocks.RED_HIGH_QUALITY_CONCRETE.get(), MSBlocks.RED_HIGH_QUALITY_CONCRETE_SLABS.get(), MSBlocks.RED_HIGH_QUALITY_CONCRETE_STAIRS.get());
        concrete(MSBlocks.WHITE_HIGH_QUALITY_CONCRETE.get(), MSBlocks.WHITE_HIGH_QUALITY_CONCRETE_SLABS.get(), MSBlocks.WHITE_HIGH_QUALITY_CONCRETE_STAIRS.get());
        concrete(MSBlocks.YELLOW_HIGH_QUALITY_CONCRETE.get(), MSBlocks.YELLOW_HIGH_QUALITY_CONCRETE_SLABS.get(), MSBlocks.YELLOW_HIGH_QUALITY_CONCRETE_STAIRS.get());
    }

    private void machine(Block block, ModelFile inactiveModel, ModelFile activeModel) {
        ModelFile inactive = inactiveModel;
        ModelFile active = activeModel == null ? inactive : activeModel;
        boolean hasActiveModel = activeModel != null;
        getVariantBuilder(block).forAllStatesExcept(state -> ConfiguredModel.builder()
                    .modelFile(hasActiveModel && Attribute.isActive(state) ? active : inactive)
                    .rotationY(((int) Attribute.getFacing(state).toYRot() + 180) % 360)
                    .build(),
              BlockStateHelper.FLUID_LOGGED);
        simpleBlockItem(block, inactive);
    }

    private void cubeMachine(Block block) {
        String name = name(block);
        String folder = "block/" + name + "/";
        ModelFile inactive = models().cube(name, modLoc(folder + "bottom"), modLoc(folder + "top"), modLoc(folder + "front"),
              modLoc(folder + "back"), modLoc(folder + "side"), modLoc(folder + "side")).texture("particle", modLoc(folder + "side"));
        ModelFile active = models().cube(name + "_active", modLoc(folder + "bottom"), modLoc(folder + "top"), modLoc(folder + "front_active"),
              modLoc(folder + "back"), modLoc(folder + "side"), modLoc(folder + "side")).texture("particle", modLoc(folder + "side"));
        getVariantBuilder(block).forAllStatesExcept(state -> ConfiguredModel.builder()
                    .modelFile(Attribute.isActive(state) ? active : inactive)
                    .rotationY(((int) Attribute.getFacing(state).toYRot() + 180) % 360)
                    .build(),
              BlockStateHelper.FLUID_LOGGED);
        simpleBlockItem(block, inactive);
    }

    private void fluidBlock(Block block) {
        simpleBlock(block, models().getBuilder(name(block)).texture("particle", LIQUID_PARTICLE));
    }

    private void concrete(Block block, Block slab, Block stairs) {
        ResourceLocation texture = blockTexture(block);
        ModelFile full = models().cubeAll(name(block), texture);
        ModelFile slabBottom = models().slab(name(slab), texture, texture, texture);
        ModelFile slabTop = models().slabTop(name(slab) + "_top", texture, texture, texture);
        ModelFile straight = models().stairs(name(stairs), texture, texture, texture);
        ModelFile inner = models().stairsInner(name(stairs) + "_inner", texture, texture, texture);
        ModelFile outer = models().stairsOuter(name(stairs) + "_outer", texture, texture, texture);

        simpleBlock(block, full);
        simpleBlockItem(block, full);
        slabBlock((SlabBlock) slab, slabBottom, slabTop, full);
        simpleBlockItem(slab, slabBottom);
        stairsBlock((StairBlock) stairs, straight, inner, outer);
        simpleBlockItem(stairs, straight);
    }

    private static String name(Block block) {
        return BuiltInRegistries.BLOCK.getKey(block).getPath();
    }
}
