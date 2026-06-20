package com.fxd927.mekanismelements.client.jei.machine;

import com.fxd927.mekanismelements.common.registries.MSGases;
import mekanism.api.chemical.Chemical;
import mekanism.api.chemical.ChemicalStack;
import mekanism.client.gui.element.GuiInnerScreen;
import mekanism.client.gui.element.gauge.GaugeType;
import mekanism.client.gui.element.gauge.GuiChemicalGauge;
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
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;


public class AirCompressorRecipeCategory extends BaseRecipeCategory<AirCompressorInfoRecipe> {

    private static final ChemicalStack OUTPUT_STACK =
            new ChemicalStack((Chemical) MSGases.COMPRESSED_AIR.get(), 200);

    private static final List<Component> DESCRIPTION = List.of(
            Component.translatable("description.mekanismelements.air_compressor")
    );

    private final GuiGauge<?> outputGauge;

    public AirCompressorRecipeCategory(IGuiHelper helper, IRecipeViewerRecipeType<AirCompressorInfoRecipe> recipeType) {
        super(helper, recipeType);
        addElement(new GuiInnerScreen(this, 5, 5, 122, 70, () -> DESCRIPTION).clearFormat());
        outputGauge = addElement(GuiChemicalGauge.getDummy(GaugeType.STANDARD.with(DataType.OUTPUT), this, 131, 5));
    }

    @Override
    public void setRecipe(@NotNull IRecipeLayoutBuilder builder, AirCompressorInfoRecipe recipe, @NotNull IFocusGroup focusGroup) {
        initChemical(builder, RecipeIngredientRole.OUTPUT, outputGauge, Collections.singletonList(OUTPUT_STACK));
    }

    @Override
    public com.mojang.serialization.Codec<AirCompressorInfoRecipe> getCodec(ICodecHelper codecHelper, IRecipeManager recipeManager) {
        return null;
    }

    @Override
    public ResourceLocation getRegistryName(AirCompressorInfoRecipe recipe) {
        return null;
    }
}