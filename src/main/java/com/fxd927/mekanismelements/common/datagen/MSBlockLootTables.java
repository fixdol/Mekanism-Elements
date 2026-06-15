package com.fxd927.mekanismelements.common.datagen;

import com.fxd927.mekanismelements.common.registries.MSBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;

import java.util.List;
import java.util.Set;

public class MSBlockLootTables extends BlockLootSubProvider {

    protected MSBlockLootTables(HolderLookup.Provider provider) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), provider);
    }

    @Override
    protected void generate() {
        dropSelf(MSBlocks.ADSORPTION_SEPARATOR.get());
        dropSelf(MSBlocks.AIR_COMPRESSOR.get());
        dropSelf(MSBlocks.RADIATION_IRRADIATOR.get());
        dropSelf(MSBlocks.SEAWATER_PUMP.get());

        dropSelf(MSBlocks.HIGH_QUALITY_CONCRETE.get());
        dropSelf(MSBlocks.AQUA_HIGH_QUALITY_CONCRETE.get());
        dropSelf(MSBlocks.BLACK_HIGH_QUALITY_CONCRETE.get());
        dropSelf(MSBlocks.BLUE_HIGH_QUALITY_CONCRETE.get());
        dropSelf(MSBlocks.GREEN_HIGH_QUALITY_CONCRETE.get());
        dropSelf(MSBlocks.CYAN_HIGH_QUALITY_CONCRETE.get());
        dropSelf(MSBlocks.DARK_RED_HIGH_QUALITY_CONCRETE.get());
        dropSelf(MSBlocks.PURPLE_HIGH_QUALITY_CONCRETE.get());
        dropSelf(MSBlocks.ORANGE_HIGH_QUALITY_CONCRETE.get());
        dropSelf(MSBlocks.LIGHT_GRAY_HIGH_QUALITY_CONCRETE.get());
        dropSelf(MSBlocks.GRAY_HIGH_QUALITY_CONCRETE.get());
        dropSelf(MSBlocks.LIGHT_BLUE_HIGH_QUALITY_CONCRETE.get());
        dropSelf(MSBlocks.LIME_HIGH_QUALITY_CONCRETE.get());
        dropSelf(MSBlocks.RED_HIGH_QUALITY_CONCRETE.get());
        dropSelf(MSBlocks.MAGENTA_HIGH_QUALITY_CONCRETE.get());
        dropSelf(MSBlocks.YELLOW_HIGH_QUALITY_CONCRETE.get());
        dropSelf(MSBlocks.WHITE_HIGH_QUALITY_CONCRETE.get());
        dropSelf(MSBlocks.BROWN_HIGH_QUALITY_CONCRETE.get());
        dropSelf(MSBlocks.PINK_HIGH_QUALITY_CONCRETE.get());

        dropSelf(MSBlocks.HIGH_QUALITY_CONCRETE_STAIRS.get());
        dropSelf(MSBlocks.AQUA_HIGH_QUALITY_CONCRETE_STAIRS.get());
        dropSelf(MSBlocks.BLACK_HIGH_QUALITY_CONCRETE_STAIRS.get());
        dropSelf(MSBlocks.BLUE_HIGH_QUALITY_CONCRETE_STAIRS.get());
        dropSelf(MSBlocks.GREEN_HIGH_QUALITY_CONCRETE_STAIRS.get());
        dropSelf(MSBlocks.CYAN_HIGH_QUALITY_CONCRETE_STAIRS.get());
        dropSelf(MSBlocks.DARK_RED_HIGH_QUALITY_CONCRETE_STAIRS.get());
        dropSelf(MSBlocks.PURPLE_HIGH_QUALITY_CONCRETE_STAIRS.get());
        dropSelf(MSBlocks.ORANGE_HIGH_QUALITY_CONCRETE_STAIRS.get());
        dropSelf(MSBlocks.LIGHT_GRAY_HIGH_QUALITY_CONCRETE_STAIRS.get());
        dropSelf(MSBlocks.GRAY_HIGH_QUALITY_CONCRETE_STAIRS.get());
        dropSelf(MSBlocks.LIGHT_BLUE_HIGH_QUALITY_CONCRETE_STAIRS.get());
        dropSelf(MSBlocks.LIME_HIGH_QUALITY_CONCRETE_STAIRS.get());
        dropSelf(MSBlocks.RED_HIGH_QUALITY_CONCRETE_STAIRS.get());
        dropSelf(MSBlocks.MAGENTA_HIGH_QUALITY_CONCRETE_STAIRS.get());
        dropSelf(MSBlocks.YELLOW_HIGH_QUALITY_CONCRETE_STAIRS.get());
        dropSelf(MSBlocks.WHITE_HIGH_QUALITY_CONCRETE_STAIRS.get());
        dropSelf(MSBlocks.BROWN_HIGH_QUALITY_CONCRETE_STAIRS.get());
        dropSelf(MSBlocks.PINK_HIGH_QUALITY_CONCRETE_STAIRS.get());

        add(MSBlocks.HIGH_QUALITY_CONCRETE_SLABS.get(), this::createSlabItemTable);
        add(MSBlocks.AQUA_HIGH_QUALITY_CONCRETE_SLABS.get(), this::createSlabItemTable);
        add(MSBlocks.BLACK_HIGH_QUALITY_CONCRETE_SLABS.get(), this::createSlabItemTable);
        add(MSBlocks.BLUE_HIGH_QUALITY_CONCRETE_SLABS.get(), this::createSlabItemTable);
        add(MSBlocks.GREEN_HIGH_QUALITY_CONCRETE_SLABS.get(), this::createSlabItemTable);
        add(MSBlocks.CYAN_HIGH_QUALITY_CONCRETE_SLABS.get(), this::createSlabItemTable);
        add(MSBlocks.DARK_RED_HIGH_QUALITY_CONCRETE_SLABS.get(), this::createSlabItemTable);
        add(MSBlocks.PURPLE_HIGH_QUALITY_CONCRETE_SLABS.get(), this::createSlabItemTable);
        add(MSBlocks.ORANGE_HIGH_QUALITY_CONCRETE_SLABS.get(), this::createSlabItemTable);
        add(MSBlocks.LIGHT_GRAY_HIGH_QUALITY_CONCRETE_SLABS.get(), this::createSlabItemTable);
        add(MSBlocks.GRAY_HIGH_QUALITY_CONCRETE_SLABS.get(), this::createSlabItemTable);
        add(MSBlocks.LIGHT_BLUE_HIGH_QUALITY_CONCRETE_SLABS.get(), this::createSlabItemTable);
        add(MSBlocks.LIME_HIGH_QUALITY_CONCRETE_SLABS.get(), this::createSlabItemTable);
        add(MSBlocks.RED_HIGH_QUALITY_CONCRETE_SLABS.get(), this::createSlabItemTable);
        add(MSBlocks.MAGENTA_HIGH_QUALITY_CONCRETE_SLABS.get(), this::createSlabItemTable);
        add(MSBlocks.YELLOW_HIGH_QUALITY_CONCRETE_SLABS.get(), this::createSlabItemTable);
        add(MSBlocks.WHITE_HIGH_QUALITY_CONCRETE_SLABS.get(), this::createSlabItemTable);
        add(MSBlocks.BROWN_HIGH_QUALITY_CONCRETE_SLABS.get(), this::createSlabItemTable);
        add(MSBlocks.PINK_HIGH_QUALITY_CONCRETE_SLABS.get(), this::createSlabItemTable);
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return List.of(
                MSBlocks.ADSORPTION_SEPARATOR.get(),
                MSBlocks.AIR_COMPRESSOR.get(),
                MSBlocks.RADIATION_IRRADIATOR.get(),
                MSBlocks.SEAWATER_PUMP.get(),

                MSBlocks.HIGH_QUALITY_CONCRETE.get(),
                MSBlocks.AQUA_HIGH_QUALITY_CONCRETE.get(),
                MSBlocks.BLACK_HIGH_QUALITY_CONCRETE.get(),
                MSBlocks.BLUE_HIGH_QUALITY_CONCRETE.get(),
                MSBlocks.GREEN_HIGH_QUALITY_CONCRETE.get(),
                MSBlocks.CYAN_HIGH_QUALITY_CONCRETE.get(),
                MSBlocks.DARK_RED_HIGH_QUALITY_CONCRETE.get(),
                MSBlocks.PURPLE_HIGH_QUALITY_CONCRETE.get(),
                MSBlocks.ORANGE_HIGH_QUALITY_CONCRETE.get(),
                MSBlocks.LIGHT_GRAY_HIGH_QUALITY_CONCRETE.get(),
                MSBlocks.GRAY_HIGH_QUALITY_CONCRETE.get(),
                MSBlocks.LIGHT_BLUE_HIGH_QUALITY_CONCRETE.get(),
                MSBlocks.LIME_HIGH_QUALITY_CONCRETE.get(),
                MSBlocks.RED_HIGH_QUALITY_CONCRETE.get(),
                MSBlocks.MAGENTA_HIGH_QUALITY_CONCRETE.get(),
                MSBlocks.YELLOW_HIGH_QUALITY_CONCRETE.get(),
                MSBlocks.WHITE_HIGH_QUALITY_CONCRETE.get(),
                MSBlocks.BROWN_HIGH_QUALITY_CONCRETE.get(),
                MSBlocks.PINK_HIGH_QUALITY_CONCRETE.get(),

                MSBlocks.HIGH_QUALITY_CONCRETE_STAIRS.get(),
                MSBlocks.AQUA_HIGH_QUALITY_CONCRETE_STAIRS.get(),
                MSBlocks.BLACK_HIGH_QUALITY_CONCRETE_STAIRS.get(),
                MSBlocks.BLUE_HIGH_QUALITY_CONCRETE_STAIRS.get(),
                MSBlocks.GREEN_HIGH_QUALITY_CONCRETE_STAIRS.get(),
                MSBlocks.CYAN_HIGH_QUALITY_CONCRETE_STAIRS.get(),
                MSBlocks.DARK_RED_HIGH_QUALITY_CONCRETE_STAIRS.get(),
                MSBlocks.PURPLE_HIGH_QUALITY_CONCRETE_STAIRS.get(),
                MSBlocks.ORANGE_HIGH_QUALITY_CONCRETE_STAIRS.get(),
                MSBlocks.LIGHT_GRAY_HIGH_QUALITY_CONCRETE_STAIRS.get(),
                MSBlocks.GRAY_HIGH_QUALITY_CONCRETE_STAIRS.get(),
                MSBlocks.LIGHT_BLUE_HIGH_QUALITY_CONCRETE_STAIRS.get(),
                MSBlocks.LIME_HIGH_QUALITY_CONCRETE_STAIRS.get(),
                MSBlocks.RED_HIGH_QUALITY_CONCRETE_STAIRS.get(),
                MSBlocks.MAGENTA_HIGH_QUALITY_CONCRETE_STAIRS.get(),
                MSBlocks.YELLOW_HIGH_QUALITY_CONCRETE_STAIRS.get(),
                MSBlocks.WHITE_HIGH_QUALITY_CONCRETE_STAIRS.get(),
                MSBlocks.BROWN_HIGH_QUALITY_CONCRETE_STAIRS.get(),
                MSBlocks.PINK_HIGH_QUALITY_CONCRETE_STAIRS.get(),

                MSBlocks.HIGH_QUALITY_CONCRETE_SLABS.get(),
                MSBlocks.AQUA_HIGH_QUALITY_CONCRETE_SLABS.get(),
                MSBlocks.BLACK_HIGH_QUALITY_CONCRETE_SLABS.get(),
                MSBlocks.BLUE_HIGH_QUALITY_CONCRETE_SLABS.get(),
                MSBlocks.GREEN_HIGH_QUALITY_CONCRETE_SLABS.get(),
                MSBlocks.CYAN_HIGH_QUALITY_CONCRETE_SLABS.get(),
                MSBlocks.DARK_RED_HIGH_QUALITY_CONCRETE_SLABS.get(),
                MSBlocks.PURPLE_HIGH_QUALITY_CONCRETE_SLABS.get(),
                MSBlocks.ORANGE_HIGH_QUALITY_CONCRETE_SLABS.get(),
                MSBlocks.LIGHT_GRAY_HIGH_QUALITY_CONCRETE_SLABS.get(),
                MSBlocks.GRAY_HIGH_QUALITY_CONCRETE_SLABS.get(),
                MSBlocks.LIGHT_BLUE_HIGH_QUALITY_CONCRETE_SLABS.get(),
                MSBlocks.LIME_HIGH_QUALITY_CONCRETE_SLABS.get(),
                MSBlocks.RED_HIGH_QUALITY_CONCRETE_SLABS.get(),
                MSBlocks.MAGENTA_HIGH_QUALITY_CONCRETE_SLABS.get(),
                MSBlocks.YELLOW_HIGH_QUALITY_CONCRETE_SLABS.get(),
                MSBlocks.WHITE_HIGH_QUALITY_CONCRETE_SLABS.get(),
                MSBlocks.BROWN_HIGH_QUALITY_CONCRETE_SLABS.get(),
                MSBlocks.PINK_HIGH_QUALITY_CONCRETE_SLABS.get()
        );
    }
}