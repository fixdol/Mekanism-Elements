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


public class SeawaterPumpRecipeViewerType implements IRecipeViewerRecipeType<SeawaterPumpInfoRecipe> {

    public static final SeawaterPumpRecipeViewerType INSTANCE = new SeawaterPumpRecipeViewerType();

    private final List<ItemLike> workstations = List.of(MSBlocks.SEAWATER_PUMP);

    private SeawaterPumpRecipeViewerType() {
    }

    @Override
    public ResourceLocation id() {
        return MekanismElements.rl("seawater_pump");
    }

    @Override
    public Class<? extends SeawaterPumpInfoRecipe> recipeClass() {
        return SeawaterPumpInfoRecipe.class;
    }

    @Override
    public boolean requiresHolder() {
        return false;
    }

    @Override
    public ItemStack iconStack() {
        return new ItemStack(MSBlocks.SEAWATER_PUMP.asItem());
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
        return Component.translatable("block.mekanismelements.seawater_pump");
    }
}