package com.fxd927.mekanismelements.client.jei.machine;

import com.fxd927.mekanismelements.common.MekanismElements;
import com.fxd927.mekanismelements.common.registries.MSBlocks;
import mekanism.client.recipe_viewer.type.IRecipeViewerRecipeType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.NotNull;

import java.util.List;


public class AirCompressorRecipeViewerType implements IRecipeViewerRecipeType<AirCompressorInfoRecipe> {

    public static final AirCompressorRecipeViewerType INSTANCE = new AirCompressorRecipeViewerType();

    private final List<ItemLike> workstations = List.of(MSBlocks.AIR_COMPRESSOR);

    private AirCompressorRecipeViewerType() {
    }

    @Override
    public ResourceLocation id() {
        return MekanismElements.rl("air_compressor");
    }

    @Override
    public Class<? extends AirCompressorInfoRecipe> recipeClass() {
        return AirCompressorInfoRecipe.class;
    }

    @Override
    public boolean requiresHolder() {
        return false;
    }

    @Override
    public ItemStack iconStack() {
        return new ItemStack(MSBlocks.AIR_COMPRESSOR.asItem());
    }

    @Override
    public ResourceLocation icon() {
        return null;
    }

    @Override
    public int xOffset() {
        return 3;
    }

    @Override
    public int yOffset() {
        return 3;
    }

    @Override
    public int width() {
        return 170;
    }

    @Override
    public int height() {
        return 79;
    }

    @Override
    public List<ItemLike> workstations() {
        return workstations;
    }

    @Override
    public @NotNull Component getTextComponent() {
        return Component.translatable("block.mekanismelements.air_compressor");
    }
}