package com.fxd927.mekanismelements.client.jei.machine;

import com.fxd927.mekanismelements.common.registries.MSFluids;
import mekanism.client.gui.element.GuiInnerScreen;
import mekanism.client.gui.element.gauge.GaugeType;
import mekanism.client.gui.element.gauge.GuiFluidGauge;
import mekanism.client.gui.element.gauge.GuiGauge;
import mekanism.client.recipe_viewer.jei.BaseRecipeCategory;
import mekanism.client.recipe_viewer.type.IRecipeViewerRecipeType;
import mekanism.common.tile.component.config.DataType;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.helpers.ICodecHelper;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.IRecipeManager;
import mezz.jei.api.recipe.RecipeIngredientRole;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.fluids.FluidStack;

import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;


public class SeawaterPumpRecipeCategory extends BaseRecipeCategory<SeawaterPumpInfoRecipe> {

    private static final FluidStack OUTPUT_STACK = new FluidStack(MSFluids.SEAWATER.get(), 200);

    private static final List<Component> DESCRIPTION = List.of(
            Component.translatable("description.mekanismelements.seawater_pump")
    );

    private final GuiGauge<?> outputGauge;

    public SeawaterPumpRecipeCategory(IGuiHelper helper, IRecipeViewerRecipeType<SeawaterPumpInfoRecipe> recipeType) {
        super(helper, recipeType);
        addElement(new GuiInnerScreen(this, 5, 5, 122, 70, () -> DESCRIPTION).clearFormat());
        outputGauge = addElement(GuiFluidGauge.getDummy(GaugeType.STANDARD.with(DataType.OUTPUT), this, 131, 5));
    }

    @Override
    public void setRecipe(@NotNull IRecipeLayoutBuilder builder, SeawaterPumpInfoRecipe recipe, @NotNull IFocusGroup focusGroup) {
        initFluid(builder, RecipeIngredientRole.OUTPUT, outputGauge, Collections.singletonList(OUTPUT_STACK));
    }

    @Override
    public com.mojang.serialization.Codec<SeawaterPumpInfoRecipe> getCodec(ICodecHelper codecHelper, IRecipeManager recipeManager) {
        return null;
    }

    @Override
    public ResourceLocation getRegistryName(SeawaterPumpInfoRecipe recipe) {
        return null;
    }
}