package fixdol.mekanismelements.common.datagen.providers;

import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import fixdol.mekanismelements.common.datagen.providers.MSBlockStateProvider;
import fixdol.mekanismelements.common.registries.MSBlocks;
import fixdol.mekanismelements.common.registries.MSFluids;
import fixdol.mekanismelements.common.MekanismElements;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;

import mekanism.common.block.attribute.Attribute;
import mekanism.common.block.states.BlockStateHelper;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;

public class MSBlockStateProvider extends BlockStateProvider {

    private static final ResourceLocation LIQUID_PARTICLE = new ResourceLocation("mekanism", "liquid/liquid");

    public MSBlockStateProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, MekanismElements.MODID, existingFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        machine(MSBlocks.ADSORPTION_SEPARATOR.getBlock(), MSMachineModels.adsorptionSeparator(models()), null);
        machine(MSBlocks.AIR_COMPRESSOR.getBlock(), MSMachineModels.airCompressor(models()), MSMachineModels.airCompressorActive(models()));
        machine(MSBlocks.CHEMICAL_DEMOLITION_MACHINE.getBlock(), MSMachineModels.chemicalDemolitionMachine(models()), null);
        machine(MSBlocks.RADIATION_IRRADIATOR.getBlock(), MSMachineModels.radiationIrradiator(models()), MSMachineModels.radiationIrradiatorActive(models()));
        machine(MSBlocks.SEAWATER_PUMP.getBlock(), MSMachineModels.seawaterPump(models()), null);
        cubeMachine(MSBlocks.INFINITY_ORE_REPROCESSING.getBlock());

        MSFluids.FLUIDS.getAllFluids().forEach(fluid -> fluidBlock(fluid.getBlock()));

        concrete(MSBlocks.HIGH_QUALITY_CONCRETE.getBlock(), MSBlocks.HIGH_QUALITY_CONCRETE_SLABS.getBlock(), MSBlocks.HIGH_QUALITY_CONCRETE_STAIRS.getBlock());
        concrete(MSBlocks.AQUA_HIGH_QUALITY_CONCRETE.getBlock(), MSBlocks.AQUA_HIGH_QUALITY_CONCRETE_SLABS.getBlock(), MSBlocks.AQUA_HIGH_QUALITY_CONCRETE_STAIRS.getBlock());
        concrete(MSBlocks.BLACK_HIGH_QUALITY_CONCRETE.getBlock(), MSBlocks.BLACK_HIGH_QUALITY_CONCRETE_SLABS.getBlock(), MSBlocks.BLACK_HIGH_QUALITY_CONCRETE_STAIRS.getBlock());
        concrete(MSBlocks.BLUE_HIGH_QUALITY_CONCRETE.getBlock(), MSBlocks.BLUE_HIGH_QUALITY_CONCRETE_SLABS.getBlock(), MSBlocks.BLUE_HIGH_QUALITY_CONCRETE_STAIRS.getBlock());
        concrete(MSBlocks.BROWN_HIGH_QUALITY_CONCRETE.getBlock(), MSBlocks.BROWN_HIGH_QUALITY_CONCRETE_SLABS.getBlock(), MSBlocks.BROWN_HIGH_QUALITY_CONCRETE_STAIRS.getBlock());
        concrete(MSBlocks.CYAN_HIGH_QUALITY_CONCRETE.getBlock(), MSBlocks.CYAN_HIGH_QUALITY_CONCRETE_SLABS.getBlock(), MSBlocks.CYAN_HIGH_QUALITY_CONCRETE_STAIRS.getBlock());
        concrete(MSBlocks.DARK_RED_HIGH_QUALITY_CONCRETE.getBlock(), MSBlocks.DARK_RED_HIGH_QUALITY_CONCRETE_SLABS.getBlock(), MSBlocks.DARK_RED_HIGH_QUALITY_CONCRETE_STAIRS.getBlock());
        concrete(MSBlocks.GRAY_HIGH_QUALITY_CONCRETE.getBlock(), MSBlocks.GRAY_HIGH_QUALITY_CONCRETE_SLABS.getBlock(), MSBlocks.GRAY_HIGH_QUALITY_CONCRETE_STAIRS.getBlock());
        concrete(MSBlocks.GREEN_HIGH_QUALITY_CONCRETE.getBlock(), MSBlocks.GREEN_HIGH_QUALITY_CONCRETE_SLABS.getBlock(), MSBlocks.GREEN_HIGH_QUALITY_CONCRETE_STAIRS.getBlock());
        concrete(MSBlocks.LIGHT_BLUE_HIGH_QUALITY_CONCRETE.getBlock(), MSBlocks.LIGHT_BLUE_HIGH_QUALITY_CONCRETE_SLABS.getBlock(), MSBlocks.LIGHT_BLUE_HIGH_QUALITY_CONCRETE_STAIRS.getBlock());
        concrete(MSBlocks.LIGHT_GRAY_HIGH_QUALITY_CONCRETE.getBlock(), MSBlocks.LIGHT_GRAY_HIGH_QUALITY_CONCRETE_SLABS.getBlock(), MSBlocks.LIGHT_GRAY_HIGH_QUALITY_CONCRETE_STAIRS.getBlock());
        concrete(MSBlocks.LIME_HIGH_QUALITY_CONCRETE.getBlock(), MSBlocks.LIME_HIGH_QUALITY_CONCRETE_SLABS.getBlock(), MSBlocks.LIME_HIGH_QUALITY_CONCRETE_STAIRS.getBlock());
        concrete(MSBlocks.MAGENTA_HIGH_QUALITY_CONCRETE.getBlock(), MSBlocks.MAGENTA_HIGH_QUALITY_CONCRETE_SLABS.getBlock(), MSBlocks.MAGENTA_HIGH_QUALITY_CONCRETE_STAIRS.getBlock());
        concrete(MSBlocks.ORANGE_HIGH_QUALITY_CONCRETE.getBlock(), MSBlocks.ORANGE_HIGH_QUALITY_CONCRETE_SLABS.getBlock(), MSBlocks.ORANGE_HIGH_QUALITY_CONCRETE_STAIRS.getBlock());
        concrete(MSBlocks.PINK_HIGH_QUALITY_CONCRETE.getBlock(), MSBlocks.PINK_HIGH_QUALITY_CONCRETE_SLABS.getBlock(), MSBlocks.PINK_HIGH_QUALITY_CONCRETE_STAIRS.getBlock());
        concrete(MSBlocks.PURPLE_HIGH_QUALITY_CONCRETE.getBlock(), MSBlocks.PURPLE_HIGH_QUALITY_CONCRETE_SLABS.getBlock(), MSBlocks.PURPLE_HIGH_QUALITY_CONCRETE_STAIRS.getBlock());
        concrete(MSBlocks.RED_HIGH_QUALITY_CONCRETE.getBlock(), MSBlocks.RED_HIGH_QUALITY_CONCRETE_SLABS.getBlock(), MSBlocks.RED_HIGH_QUALITY_CONCRETE_STAIRS.getBlock());
        concrete(MSBlocks.WHITE_HIGH_QUALITY_CONCRETE.getBlock(), MSBlocks.WHITE_HIGH_QUALITY_CONCRETE_SLABS.getBlock(), MSBlocks.WHITE_HIGH_QUALITY_CONCRETE_STAIRS.getBlock());
        concrete(MSBlocks.YELLOW_HIGH_QUALITY_CONCRETE.getBlock(), MSBlocks.YELLOW_HIGH_QUALITY_CONCRETE_SLABS.getBlock(), MSBlocks.YELLOW_HIGH_QUALITY_CONCRETE_STAIRS.getBlock());
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
