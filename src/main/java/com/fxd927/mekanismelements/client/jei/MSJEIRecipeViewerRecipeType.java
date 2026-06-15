package com.fxd927.mekanismelements.client.jei;

import com.fxd927.mekanismelements.common.registration.impl.MSRecipeTypeRegistryObject;
import mekanism.api.recipes.MekanismRecipe;
import mekanism.api.text.IHasTranslationKey;
import mekanism.client.recipe_viewer.type.IRecipeViewerRecipeType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

public class MSJEIRecipeViewerRecipeType<RECIPE extends MekanismRecipe<?>> implements IRecipeViewerRecipeType<RECIPE> {
    private final MSRecipeTypeRegistryObject<RECIPE, ?> registryObject;
    private final Class<? extends RECIPE> recipeClass;
    private final IHasTranslationKey description;
    private final ResourceLocation icon;
    private final int xOffset;
    private final int yOffset;
    private final int width;
    private final int height;
    private final List<ItemLike> workstations;

    public MSJEIRecipeViewerRecipeType(
            MSRecipeTypeRegistryObject<RECIPE, ?> registryObject,
            Class<? extends RECIPE> recipeClass,
            IHasTranslationKey description,
            ResourceLocation icon,
            int xOffset, int yOffset, int width, int height,
            ItemLike... workstations) {
        this.registryObject = registryObject;
        this.recipeClass = recipeClass;
        this.description = description;
        this.icon = icon;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        this.width = width;
        this.height = height;
        this.workstations = Arrays.asList(workstations);
    }

    @Override
    public ResourceLocation id() {
        return registryObject.getId();
    }

    @Override
    public Class<? extends RECIPE> recipeClass() {
        return recipeClass;
    }

    @Override
    public boolean requiresHolder() {
        return false;
    }

    @Override
    public ItemStack iconStack() {
        if (!workstations.isEmpty()) {
            return new ItemStack(workstations.get(0));
        }
        return ItemStack.EMPTY;
    }

    @Override
    public ResourceLocation icon() {
        return icon;
    }

    @Override
    public int xOffset() {
        return xOffset;
    }

    @Override
    public int yOffset() {
        return yOffset;
    }

    @Override
    public int width() {
        return width;
    }

    @Override
    public int height() {
        return height;
    }

    @Override
    public List<ItemLike> workstations() {
        return workstations;
    }

    @Override
    public @NotNull Component getTextComponent() {
        return net.minecraft.network.chat.Component.translatable(description.getTranslationKey());
    }
}

