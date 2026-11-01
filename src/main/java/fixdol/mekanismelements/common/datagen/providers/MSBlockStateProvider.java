package fixdol.mekanismelements.common.datagen.providers;

import fixdol.mekanismelements.common.MekanismElements;
import fixdol.mekanismelements.common.registries.MSBlocks;
import fixdol.mekanismelements.common.registries.MSFluids;
import mekanism.common.block.attribute.AttributeStateActive;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.client.data.models.blockstates.PropertyDispatch;
import net.minecraft.client.data.models.model.ItemModelUtils;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.data.models.model.TextureSlot;
import net.minecraft.client.data.models.model.TexturedModel;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LiquidBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import mekanism.common.registration.impl.FluidDeferredRegister;

import java.util.stream.Stream;

public class MSBlockStateProvider extends ModelProvider {

    public MSBlockStateProvider(PackOutput output) {
        super(output, MekanismElements.MODID);
    }

    @Override
    protected Stream<? extends Holder<Item>> getKnownItems() {
        return super.getKnownItems().filter(holder -> !(holder.value() instanceof BlockItem));
    }

    @Override
    protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
        machine(blockModels, MSBlocks.ADSORPTION_SEPARATOR.get(), MSMachineModels.adsorptionSeparator(), null);
        machine(blockModels, MSBlocks.AIR_COMPRESSOR.get(), MSMachineModels.airCompressor(), MSMachineModels.airCompressorActive());
        machine(blockModels, MSBlocks.CHEMICAL_DEMOLITION_MACHINE.get(), MSMachineModels.chemicalDemolitionMachine(), null);
        machine(blockModels, MSBlocks.RADIATION_IRRADIATOR.get(), MSMachineModels.radiationIrradiator(), MSMachineModels.radiationIrradiatorActive());
        machine(blockModels, MSBlocks.SEAWATER_PUMP.get(), MSMachineModels.seawaterPump(), null);
        cubeMachine(blockModels, MSBlocks.INFINITY_ORE_REPROCESSING.get());

        fluidBlocks(blockModels);

        concrete(blockModels, MSBlocks.HIGH_QUALITY_CONCRETE.get(), MSBlocks.HIGH_QUALITY_CONCRETE_SLABS.get(), MSBlocks.HIGH_QUALITY_CONCRETE_STAIRS.get());
        concrete(blockModels, MSBlocks.AQUA_HIGH_QUALITY_CONCRETE.get(), MSBlocks.AQUA_HIGH_QUALITY_CONCRETE_SLABS.get(), MSBlocks.AQUA_HIGH_QUALITY_CONCRETE_STAIRS.get());
        concrete(blockModels, MSBlocks.BLACK_HIGH_QUALITY_CONCRETE.get(), MSBlocks.BLACK_HIGH_QUALITY_CONCRETE_SLABS.get(), MSBlocks.BLACK_HIGH_QUALITY_CONCRETE_STAIRS.get());
        concrete(blockModels, MSBlocks.BLUE_HIGH_QUALITY_CONCRETE.get(), MSBlocks.BLUE_HIGH_QUALITY_CONCRETE_SLABS.get(), MSBlocks.BLUE_HIGH_QUALITY_CONCRETE_STAIRS.get());
        concrete(blockModels, MSBlocks.BROWN_HIGH_QUALITY_CONCRETE.get(), MSBlocks.BROWN_HIGH_QUALITY_CONCRETE_SLABS.get(), MSBlocks.BROWN_HIGH_QUALITY_CONCRETE_STAIRS.get());
        concrete(blockModels, MSBlocks.CYAN_HIGH_QUALITY_CONCRETE.get(), MSBlocks.CYAN_HIGH_QUALITY_CONCRETE_SLABS.get(), MSBlocks.CYAN_HIGH_QUALITY_CONCRETE_STAIRS.get());
        concrete(blockModels, MSBlocks.DARK_RED_HIGH_QUALITY_CONCRETE.get(), MSBlocks.DARK_RED_HIGH_QUALITY_CONCRETE_SLABS.get(), MSBlocks.DARK_RED_HIGH_QUALITY_CONCRETE_STAIRS.get());
        concrete(blockModels, MSBlocks.GRAY_HIGH_QUALITY_CONCRETE.get(), MSBlocks.GRAY_HIGH_QUALITY_CONCRETE_SLABS.get(), MSBlocks.GRAY_HIGH_QUALITY_CONCRETE_STAIRS.get());
        concrete(blockModels, MSBlocks.GREEN_HIGH_QUALITY_CONCRETE.get(), MSBlocks.GREEN_HIGH_QUALITY_CONCRETE_SLABS.get(), MSBlocks.GREEN_HIGH_QUALITY_CONCRETE_STAIRS.get());
        concrete(blockModels, MSBlocks.LIGHT_BLUE_HIGH_QUALITY_CONCRETE.get(), MSBlocks.LIGHT_BLUE_HIGH_QUALITY_CONCRETE_SLABS.get(), MSBlocks.LIGHT_BLUE_HIGH_QUALITY_CONCRETE_STAIRS.get());
        concrete(blockModels, MSBlocks.LIGHT_GRAY_HIGH_QUALITY_CONCRETE.get(), MSBlocks.LIGHT_GRAY_HIGH_QUALITY_CONCRETE_SLABS.get(), MSBlocks.LIGHT_GRAY_HIGH_QUALITY_CONCRETE_STAIRS.get());
        concrete(blockModels, MSBlocks.LIME_HIGH_QUALITY_CONCRETE.get(), MSBlocks.LIME_HIGH_QUALITY_CONCRETE_SLABS.get(), MSBlocks.LIME_HIGH_QUALITY_CONCRETE_STAIRS.get());
        concrete(blockModels, MSBlocks.MAGENTA_HIGH_QUALITY_CONCRETE.get(), MSBlocks.MAGENTA_HIGH_QUALITY_CONCRETE_SLABS.get(), MSBlocks.MAGENTA_HIGH_QUALITY_CONCRETE_STAIRS.get());
        concrete(blockModels, MSBlocks.ORANGE_HIGH_QUALITY_CONCRETE.get(), MSBlocks.ORANGE_HIGH_QUALITY_CONCRETE_SLABS.get(), MSBlocks.ORANGE_HIGH_QUALITY_CONCRETE_STAIRS.get());
        concrete(blockModels, MSBlocks.PINK_HIGH_QUALITY_CONCRETE.get(), MSBlocks.PINK_HIGH_QUALITY_CONCRETE_SLABS.get(), MSBlocks.PINK_HIGH_QUALITY_CONCRETE_STAIRS.get());
        concrete(blockModels, MSBlocks.PURPLE_HIGH_QUALITY_CONCRETE.get(), MSBlocks.PURPLE_HIGH_QUALITY_CONCRETE_SLABS.get(), MSBlocks.PURPLE_HIGH_QUALITY_CONCRETE_STAIRS.get());
        concrete(blockModels, MSBlocks.RED_HIGH_QUALITY_CONCRETE.get(), MSBlocks.RED_HIGH_QUALITY_CONCRETE_SLABS.get(), MSBlocks.RED_HIGH_QUALITY_CONCRETE_STAIRS.get());
        concrete(blockModels, MSBlocks.WHITE_HIGH_QUALITY_CONCRETE.get(), MSBlocks.WHITE_HIGH_QUALITY_CONCRETE_SLABS.get(), MSBlocks.WHITE_HIGH_QUALITY_CONCRETE_STAIRS.get());
        concrete(blockModels, MSBlocks.YELLOW_HIGH_QUALITY_CONCRETE.get(), MSBlocks.YELLOW_HIGH_QUALITY_CONCRETE_SLABS.get(), MSBlocks.YELLOW_HIGH_QUALITY_CONCRETE_STAIRS.get());

        MSItemModelProvider.register(itemModels, getKnownItems());
    }

    private static void machine(BlockModelGenerators blockModels, Block block, MSMachineModels.Model inactiveModel, MSMachineModels.Model activeModel) {
        String name = name(block);
        Identifier inactive = inactiveModel.create(MekanismElements.rl("block/" + name), blockModels.modelOutput);
        Identifier active = activeModel == null ? inactive : activeModel.create(MekanismElements.rl("block/" + name + "_active"), blockModels.modelOutput);
        facingBlockState(blockModels, block, inactive, active);
    }

    private static void cubeMachine(BlockModelGenerators blockModels, Block block) {
        String name = name(block);
        String folder = "block/" + name + "/";
        Identifier inactive = ModelTemplates.CUBE.create(MekanismElements.rl("block/" + name), cubeMapping(folder, "front"), blockModels.modelOutput);
        Identifier active = ModelTemplates.CUBE.create(MekanismElements.rl("block/" + name + "_active"), cubeMapping(folder, "front_active"), blockModels.modelOutput);
        facingBlockState(blockModels, block, inactive, active);
    }

    private static TextureMapping cubeMapping(String folder, String front) {
        Material side = texture(folder + "side");
        return new TextureMapping()
              .put(TextureSlot.PARTICLE, side)
              .put(TextureSlot.NORTH, texture(folder + front))
              .put(TextureSlot.SOUTH, texture(folder + "back"))
              .put(TextureSlot.EAST, side)
              .put(TextureSlot.WEST, side)
              .put(TextureSlot.UP, texture(folder + "top"))
              .put(TextureSlot.DOWN, texture(folder + "bottom"));
    }

    private static void facingBlockState(BlockModelGenerators blockModels, Block block, Identifier inactive, Identifier active) {
        blockModels.blockStateOutput.accept(MultiVariantGenerator.dispatch(block)
              .with(PropertyDispatch.initial(AttributeStateActive.activeProperty)
                    .select(false, BlockModelGenerators.plainVariant(inactive))
                    .select(true, BlockModelGenerators.plainVariant(active)))
              .with(BlockModelGenerators.ROTATION_HORIZONTAL_FACING));
        blockModels.itemModelOutput.accept(block.asItem(), ItemModelUtils.plainModel(inactive));
    }

    private static void fluidBlocks(BlockModelGenerators blockModels) {
        for (DeferredHolder<Block, ? extends Block> entry : MSFluids.FLUIDS.getBlockEntries()) {
            if (entry.value() instanceof LiquidBlock block && block.fluid.getFluidType() instanceof FluidDeferredRegister.MekanismFluidType fluidType) {
                blockModels.createTrivialBlock(block, fluid -> new TexturedModel(TextureMapping.particle(new Material(fluidType.stillTexture)), ModelTemplates.PARTICLE_ONLY));
            }
        }
    }

    private static void concrete(BlockModelGenerators blockModels, Block block, Block slab, Block stairs) {
        Material texture = TextureMapping.getBlockTexture(block);
        TextureMapping all = new TextureMapping().put(TextureSlot.BOTTOM, texture).put(TextureSlot.TOP, texture).put(TextureSlot.SIDE, texture);
        Identifier full = ModelTemplates.CUBE_ALL.create(block, TextureMapping.cube(texture), blockModels.modelOutput);
        Identifier slabBottom = ModelTemplates.SLAB_BOTTOM.create(slab, all, blockModels.modelOutput);
        Identifier slabTop = ModelTemplates.SLAB_TOP.create(slab, all, blockModels.modelOutput);
        Identifier straight = ModelTemplates.STAIRS_STRAIGHT.create(stairs, all, blockModels.modelOutput);
        Identifier inner = ModelTemplates.STAIRS_INNER.create(stairs, all, blockModels.modelOutput);
        Identifier outer = ModelTemplates.STAIRS_OUTER.create(stairs, all, blockModels.modelOutput);

        blockModels.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(block, BlockModelGenerators.plainVariant(full)));
        blockModels.blockStateOutput.accept(BlockModelGenerators.createSlab(slab, BlockModelGenerators.plainVariant(slabBottom), BlockModelGenerators.plainVariant(slabTop), BlockModelGenerators.plainVariant(full)));
        blockModels.blockStateOutput.accept(BlockModelGenerators.createStairs(stairs, BlockModelGenerators.plainVariant(inner), BlockModelGenerators.plainVariant(straight), BlockModelGenerators.plainVariant(outer)));
        blockModels.itemModelOutput.accept(block.asItem(), ItemModelUtils.plainModel(full));
        blockModels.itemModelOutput.accept(slab.asItem(), ItemModelUtils.plainModel(slabBottom));
        blockModels.itemModelOutput.accept(stairs.asItem(), ItemModelUtils.plainModel(straight));
    }

    private static Material texture(String path) {
        return new Material(MekanismElements.rl(path));
    }

    private static String name(Block block) {
        return BuiltInRegistries.BLOCK.getKey(block).getPath();
    }
}
