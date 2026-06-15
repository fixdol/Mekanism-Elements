package com.fxd927.mekanismelements.client.jei.machine;

import com.fxd927.mekanismelements.api.recipes.RadiationIrradiatingRecipe;
import com.fxd927.mekanismelements.common.registries.MSBlocks;
import com.fxd927.mekanismelements.common.tile.machine.TileEntityRadiationIrradiator;
import mekanism.api.chemical.ChemicalStack;
import mekanism.client.gui.element.bar.GuiHorizontalPowerBar;
import mekanism.client.gui.element.gauge.GaugeType;
import mekanism.client.gui.element.gauge.GuiChemicalGauge;
import mekanism.client.gui.element.gauge.GuiGauge;
import mekanism.client.gui.element.progress.ProgressType;
import mekanism.client.gui.element.slot.GuiSlot;
import mekanism.client.gui.element.slot.SlotType;
import mekanism.client.recipe_viewer.jei.BaseRecipeCategory;
import mekanism.client.recipe_viewer.jei.MekanismJEI;
import mekanism.client.recipe_viewer.type.IRecipeViewerRecipeType;
import mekanism.common.inventory.container.slot.SlotOverlay;
import mekanism.common.tile.component.config.DataType;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.helpers.ICodecHelper;
import mezz.jei.api.ingredients.IIngredientType;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.IRecipeManager;
import mezz.jei.api.recipe.RecipeIngredientRole;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class RadiationIrradiatorRecipeCategory extends BaseRecipeCategory<RadiationIrradiatingRecipe> {
    private final GuiGauge<?> inputGauge;
    private final GuiGauge<?> outputGauge;
    private final GuiSlot inputSlot;

    public RadiationIrradiatorRecipeCategory(IGuiHelper helper, IRecipeViewerRecipeType<RadiationIrradiatingRecipe> recipeType) {
        super(helper, recipeType);
        inputGauge = addElement(GuiChemicalGauge.getDummy(GaugeType.STANDARD.with(DataType.INPUT), this, 28, 13));
        outputGauge = addElement(GuiChemicalGauge.getDummy(GaugeType.STANDARD.with(DataType.OUTPUT), this, 131, 13));
        inputSlot = addSlot(SlotType.INPUT, 7, 36);
        addSlot(SlotType.EXTRA, 7, 55).with(SlotOverlay.MINUS);
        addSlot(SlotType.OUTPUT, 152, 55).with(SlotOverlay.PLUS);
        addSlot(SlotType.POWER, 152, 14).with(SlotOverlay.POWER);
        addSimpleProgress(ProgressType.LARGE_RIGHT, 64, 40);
        addElement(new GuiHorizontalPowerBar(this, () -> 1.0, 115, 75));
    }

    @Override
    public void setRecipe(@NotNull IRecipeLayoutBuilder builder, RadiationIrradiatingRecipe recipe, @NotNull IFocusGroup focusGroup) {
        initItem(builder, RecipeIngredientRole.INPUT, inputSlot, recipe.getItemInput().getRepresentations());
        List<@NotNull ChemicalStack> chemicalInputs = recipe.getGasInput().getRepresentations();
        List<ChemicalStack> scaledChemicals = chemicalInputs.stream().map(chemical -> chemical.copyWithAmount(chemical.getAmount() * TileEntityRadiationIrradiator.BASE_TICKS_REQUIRED))
                .toList();
        initChemical(builder, RecipeIngredientRole.INPUT, inputGauge, scaledChemicals);
        List<ChemicalStack> outputDefinition = recipe.getOutputDefinition();
        if (outputDefinition.size() == 1) {
            ChemicalStack output = outputDefinition.get(0);
            initChemicalOutput(builder, getIngredientType(output), Collections.singletonList(output));
        } else {
            // In unified system, all outputs use TYPE_CHEMICAL
            initChemicalOutput(builder, MekanismJEI.TYPE_CHEMICAL, outputDefinition);
        }
    }

    @SuppressWarnings("unchecked")
    private <STACK extends ChemicalStack> void initChemicalOutput(IRecipeLayoutBuilder builder, IIngredientType<STACK> type, List<ChemicalStack> stacks) {
        initChemical(builder, RecipeIngredientRole.OUTPUT, outputGauge, stacks);
    }

    @SuppressWarnings("unchecked")
    private <STACK extends ChemicalStack> IIngredientType<STACK> getIngredientType(ChemicalStack stack) {
        // Use the unified TYPE_CHEMICAL for all chemical stacks in Mekanism 10.7
        return (IIngredientType<STACK>) MekanismJEI.TYPE_CHEMICAL;
    }

    @Override
    public com.mojang.serialization.Codec<RadiationIrradiatingRecipe> getCodec(ICodecHelper codecHelper, IRecipeManager recipeManager) {
        return com.fxd927.mekanismelements.common.registries.MSRecipeSerializers.RADIATION_IRRADIATOR.get().codec().codec();
    }

    @Override
    public net.minecraft.resources.ResourceLocation getRegistryName(RadiationIrradiatingRecipe recipe) {
        return recipe.getId();
    }
}