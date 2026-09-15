package fixdol.mekanismelements.common.datagen.providers;

import net.minecraft.world.level.block.Block;
import net.minecraft.core.HolderLookup;
import java.util.List;
import fixdol.mekanismelements.common.registries.MSBlocks;
import java.util.Set;

import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;


public class MSBlockLootTables extends BlockLootSubProvider {

    protected MSBlockLootTables() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {
        dropSelf(MSBlocks.ADSORPTION_SEPARATOR.getBlock());
        dropSelf(MSBlocks.AIR_COMPRESSOR.getBlock());
        dropSelf(MSBlocks.RADIATION_IRRADIATOR.getBlock());
        dropSelf(MSBlocks.SEAWATER_PUMP.getBlock());
        dropSelf(MSBlocks.CHEMICAL_DEMOLITION_MACHINE.getBlock());

        dropSelf(MSBlocks.HIGH_QUALITY_CONCRETE.getBlock());
        dropSelf(MSBlocks.AQUA_HIGH_QUALITY_CONCRETE.getBlock());
        dropSelf(MSBlocks.BLACK_HIGH_QUALITY_CONCRETE.getBlock());
        dropSelf(MSBlocks.BLUE_HIGH_QUALITY_CONCRETE.getBlock());
        dropSelf(MSBlocks.GREEN_HIGH_QUALITY_CONCRETE.getBlock());
        dropSelf(MSBlocks.CYAN_HIGH_QUALITY_CONCRETE.getBlock());
        dropSelf(MSBlocks.DARK_RED_HIGH_QUALITY_CONCRETE.getBlock());
        dropSelf(MSBlocks.PURPLE_HIGH_QUALITY_CONCRETE.getBlock());
        dropSelf(MSBlocks.ORANGE_HIGH_QUALITY_CONCRETE.getBlock());
        dropSelf(MSBlocks.LIGHT_GRAY_HIGH_QUALITY_CONCRETE.getBlock());
        dropSelf(MSBlocks.GRAY_HIGH_QUALITY_CONCRETE.getBlock());
        dropSelf(MSBlocks.LIGHT_BLUE_HIGH_QUALITY_CONCRETE.getBlock());
        dropSelf(MSBlocks.LIME_HIGH_QUALITY_CONCRETE.getBlock());
        dropSelf(MSBlocks.RED_HIGH_QUALITY_CONCRETE.getBlock());
        dropSelf(MSBlocks.MAGENTA_HIGH_QUALITY_CONCRETE.getBlock());
        dropSelf(MSBlocks.YELLOW_HIGH_QUALITY_CONCRETE.getBlock());
        dropSelf(MSBlocks.WHITE_HIGH_QUALITY_CONCRETE.getBlock());
        dropSelf(MSBlocks.BROWN_HIGH_QUALITY_CONCRETE.getBlock());
        dropSelf(MSBlocks.PINK_HIGH_QUALITY_CONCRETE.getBlock());

        dropSelf(MSBlocks.HIGH_QUALITY_CONCRETE_STAIRS.getBlock());
        dropSelf(MSBlocks.AQUA_HIGH_QUALITY_CONCRETE_STAIRS.getBlock());
        dropSelf(MSBlocks.BLACK_HIGH_QUALITY_CONCRETE_STAIRS.getBlock());
        dropSelf(MSBlocks.BLUE_HIGH_QUALITY_CONCRETE_STAIRS.getBlock());
        dropSelf(MSBlocks.GREEN_HIGH_QUALITY_CONCRETE_STAIRS.getBlock());
        dropSelf(MSBlocks.CYAN_HIGH_QUALITY_CONCRETE_STAIRS.getBlock());
        dropSelf(MSBlocks.DARK_RED_HIGH_QUALITY_CONCRETE_STAIRS.getBlock());
        dropSelf(MSBlocks.PURPLE_HIGH_QUALITY_CONCRETE_STAIRS.getBlock());
        dropSelf(MSBlocks.ORANGE_HIGH_QUALITY_CONCRETE_STAIRS.getBlock());
        dropSelf(MSBlocks.LIGHT_GRAY_HIGH_QUALITY_CONCRETE_STAIRS.getBlock());
        dropSelf(MSBlocks.GRAY_HIGH_QUALITY_CONCRETE_STAIRS.getBlock());
        dropSelf(MSBlocks.LIGHT_BLUE_HIGH_QUALITY_CONCRETE_STAIRS.getBlock());
        dropSelf(MSBlocks.LIME_HIGH_QUALITY_CONCRETE_STAIRS.getBlock());
        dropSelf(MSBlocks.RED_HIGH_QUALITY_CONCRETE_STAIRS.getBlock());
        dropSelf(MSBlocks.MAGENTA_HIGH_QUALITY_CONCRETE_STAIRS.getBlock());
        dropSelf(MSBlocks.YELLOW_HIGH_QUALITY_CONCRETE_STAIRS.getBlock());
        dropSelf(MSBlocks.WHITE_HIGH_QUALITY_CONCRETE_STAIRS.getBlock());
        dropSelf(MSBlocks.BROWN_HIGH_QUALITY_CONCRETE_STAIRS.getBlock());
        dropSelf(MSBlocks.PINK_HIGH_QUALITY_CONCRETE_STAIRS.getBlock());

        add(MSBlocks.HIGH_QUALITY_CONCRETE_SLABS.getBlock(), this::createSlabItemTable);
        add(MSBlocks.AQUA_HIGH_QUALITY_CONCRETE_SLABS.getBlock(), this::createSlabItemTable);
        add(MSBlocks.BLACK_HIGH_QUALITY_CONCRETE_SLABS.getBlock(), this::createSlabItemTable);
        add(MSBlocks.BLUE_HIGH_QUALITY_CONCRETE_SLABS.getBlock(), this::createSlabItemTable);
        add(MSBlocks.GREEN_HIGH_QUALITY_CONCRETE_SLABS.getBlock(), this::createSlabItemTable);
        add(MSBlocks.CYAN_HIGH_QUALITY_CONCRETE_SLABS.getBlock(), this::createSlabItemTable);
        add(MSBlocks.DARK_RED_HIGH_QUALITY_CONCRETE_SLABS.getBlock(), this::createSlabItemTable);
        add(MSBlocks.PURPLE_HIGH_QUALITY_CONCRETE_SLABS.getBlock(), this::createSlabItemTable);
        add(MSBlocks.ORANGE_HIGH_QUALITY_CONCRETE_SLABS.getBlock(), this::createSlabItemTable);
        add(MSBlocks.LIGHT_GRAY_HIGH_QUALITY_CONCRETE_SLABS.getBlock(), this::createSlabItemTable);
        add(MSBlocks.GRAY_HIGH_QUALITY_CONCRETE_SLABS.getBlock(), this::createSlabItemTable);
        add(MSBlocks.LIGHT_BLUE_HIGH_QUALITY_CONCRETE_SLABS.getBlock(), this::createSlabItemTable);
        add(MSBlocks.LIME_HIGH_QUALITY_CONCRETE_SLABS.getBlock(), this::createSlabItemTable);
        add(MSBlocks.RED_HIGH_QUALITY_CONCRETE_SLABS.getBlock(), this::createSlabItemTable);
        add(MSBlocks.MAGENTA_HIGH_QUALITY_CONCRETE_SLABS.getBlock(), this::createSlabItemTable);
        add(MSBlocks.YELLOW_HIGH_QUALITY_CONCRETE_SLABS.getBlock(), this::createSlabItemTable);
        add(MSBlocks.WHITE_HIGH_QUALITY_CONCRETE_SLABS.getBlock(), this::createSlabItemTable);
        add(MSBlocks.BROWN_HIGH_QUALITY_CONCRETE_SLABS.getBlock(), this::createSlabItemTable);
        add(MSBlocks.PINK_HIGH_QUALITY_CONCRETE_SLABS.getBlock(), this::createSlabItemTable);
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return List.of(
                MSBlocks.ADSORPTION_SEPARATOR.getBlock(),
                MSBlocks.AIR_COMPRESSOR.getBlock(),
                MSBlocks.RADIATION_IRRADIATOR.getBlock(),
                MSBlocks.SEAWATER_PUMP.getBlock(),
                MSBlocks.CHEMICAL_DEMOLITION_MACHINE.getBlock(),

                MSBlocks.HIGH_QUALITY_CONCRETE.getBlock(),
                MSBlocks.AQUA_HIGH_QUALITY_CONCRETE.getBlock(),
                MSBlocks.BLACK_HIGH_QUALITY_CONCRETE.getBlock(),
                MSBlocks.BLUE_HIGH_QUALITY_CONCRETE.getBlock(),
                MSBlocks.GREEN_HIGH_QUALITY_CONCRETE.getBlock(),
                MSBlocks.CYAN_HIGH_QUALITY_CONCRETE.getBlock(),
                MSBlocks.DARK_RED_HIGH_QUALITY_CONCRETE.getBlock(),
                MSBlocks.PURPLE_HIGH_QUALITY_CONCRETE.getBlock(),
                MSBlocks.ORANGE_HIGH_QUALITY_CONCRETE.getBlock(),
                MSBlocks.LIGHT_GRAY_HIGH_QUALITY_CONCRETE.getBlock(),
                MSBlocks.GRAY_HIGH_QUALITY_CONCRETE.getBlock(),
                MSBlocks.LIGHT_BLUE_HIGH_QUALITY_CONCRETE.getBlock(),
                MSBlocks.LIME_HIGH_QUALITY_CONCRETE.getBlock(),
                MSBlocks.RED_HIGH_QUALITY_CONCRETE.getBlock(),
                MSBlocks.MAGENTA_HIGH_QUALITY_CONCRETE.getBlock(),
                MSBlocks.YELLOW_HIGH_QUALITY_CONCRETE.getBlock(),
                MSBlocks.WHITE_HIGH_QUALITY_CONCRETE.getBlock(),
                MSBlocks.BROWN_HIGH_QUALITY_CONCRETE.getBlock(),
                MSBlocks.PINK_HIGH_QUALITY_CONCRETE.getBlock(),

                MSBlocks.HIGH_QUALITY_CONCRETE_STAIRS.getBlock(),
                MSBlocks.AQUA_HIGH_QUALITY_CONCRETE_STAIRS.getBlock(),
                MSBlocks.BLACK_HIGH_QUALITY_CONCRETE_STAIRS.getBlock(),
                MSBlocks.BLUE_HIGH_QUALITY_CONCRETE_STAIRS.getBlock(),
                MSBlocks.GREEN_HIGH_QUALITY_CONCRETE_STAIRS.getBlock(),
                MSBlocks.CYAN_HIGH_QUALITY_CONCRETE_STAIRS.getBlock(),
                MSBlocks.DARK_RED_HIGH_QUALITY_CONCRETE_STAIRS.getBlock(),
                MSBlocks.PURPLE_HIGH_QUALITY_CONCRETE_STAIRS.getBlock(),
                MSBlocks.ORANGE_HIGH_QUALITY_CONCRETE_STAIRS.getBlock(),
                MSBlocks.LIGHT_GRAY_HIGH_QUALITY_CONCRETE_STAIRS.getBlock(),
                MSBlocks.GRAY_HIGH_QUALITY_CONCRETE_STAIRS.getBlock(),
                MSBlocks.LIGHT_BLUE_HIGH_QUALITY_CONCRETE_STAIRS.getBlock(),
                MSBlocks.LIME_HIGH_QUALITY_CONCRETE_STAIRS.getBlock(),
                MSBlocks.RED_HIGH_QUALITY_CONCRETE_STAIRS.getBlock(),
                MSBlocks.MAGENTA_HIGH_QUALITY_CONCRETE_STAIRS.getBlock(),
                MSBlocks.YELLOW_HIGH_QUALITY_CONCRETE_STAIRS.getBlock(),
                MSBlocks.WHITE_HIGH_QUALITY_CONCRETE_STAIRS.getBlock(),
                MSBlocks.BROWN_HIGH_QUALITY_CONCRETE_STAIRS.getBlock(),
                MSBlocks.PINK_HIGH_QUALITY_CONCRETE_STAIRS.getBlock(),

                MSBlocks.HIGH_QUALITY_CONCRETE_SLABS.getBlock(),
                MSBlocks.AQUA_HIGH_QUALITY_CONCRETE_SLABS.getBlock(),
                MSBlocks.BLACK_HIGH_QUALITY_CONCRETE_SLABS.getBlock(),
                MSBlocks.BLUE_HIGH_QUALITY_CONCRETE_SLABS.getBlock(),
                MSBlocks.GREEN_HIGH_QUALITY_CONCRETE_SLABS.getBlock(),
                MSBlocks.CYAN_HIGH_QUALITY_CONCRETE_SLABS.getBlock(),
                MSBlocks.DARK_RED_HIGH_QUALITY_CONCRETE_SLABS.getBlock(),
                MSBlocks.PURPLE_HIGH_QUALITY_CONCRETE_SLABS.getBlock(),
                MSBlocks.ORANGE_HIGH_QUALITY_CONCRETE_SLABS.getBlock(),
                MSBlocks.LIGHT_GRAY_HIGH_QUALITY_CONCRETE_SLABS.getBlock(),
                MSBlocks.GRAY_HIGH_QUALITY_CONCRETE_SLABS.getBlock(),
                MSBlocks.LIGHT_BLUE_HIGH_QUALITY_CONCRETE_SLABS.getBlock(),
                MSBlocks.LIME_HIGH_QUALITY_CONCRETE_SLABS.getBlock(),
                MSBlocks.RED_HIGH_QUALITY_CONCRETE_SLABS.getBlock(),
                MSBlocks.MAGENTA_HIGH_QUALITY_CONCRETE_SLABS.getBlock(),
                MSBlocks.YELLOW_HIGH_QUALITY_CONCRETE_SLABS.getBlock(),
                MSBlocks.WHITE_HIGH_QUALITY_CONCRETE_SLABS.getBlock(),
                MSBlocks.BROWN_HIGH_QUALITY_CONCRETE_SLABS.getBlock(),
                MSBlocks.PINK_HIGH_QUALITY_CONCRETE_SLABS.getBlock()
        );
    }
}