package com.fxd927.mekanismelements.client.jei.machine;

import com.fxd927.mekanismelements.api.recipes.AdsorptionRecipe;
import com.fxd927.mekanismelements.common.registries.MSBlocks;
import com.fxd927.mekanismelements.common.tile.machine.TileEntityAdsorptionSeparator;
import mekanism.api.chemical.Chemical;
import mekanism.api.chemical.ChemicalStack;
import mekanism.common.registries.MekanismChemicals;
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
import net.neoforged.neoforge.fluids.FluidStack;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class AdsorptionSeparatorRecipeCategory extends BaseRecipeCategory<AdsorptionRecipe> {
    private final GuiGauge<?> inputGauge;
    private final GuiGauge<?> outputGauge;
    private final GuiSlot inputSlot;

    public AdsorptionSeparatorRecipeCategory(IGuiHelper helper, IRecipeViewerRecipeType<AdsorptionRecipe> recipeType) {
        super(helper, recipeType);
        inputGauge = addElement(GuiChemicalGauge.getDummy(GaugeType.MEDIUM.with(DataType.INPUT), this, 17, 13));
        outputGauge = addElement(GuiChemicalGauge.getDummy(GaugeType.STANDARD.with(DataType.OUTPUT), this, 131, 13));
        inputSlot = addSlot(SlotType.INPUT, 80, 22);
        addSlot(SlotType.OUTPUT, 152, 55).with(SlotOverlay.PLUS);
        addSlot(SlotType.POWER, 152, 14).with(SlotOverlay.POWER);
        addSimpleProgress(ProgressType.LARGE_RIGHT, 64, 40);
        addElement(new GuiHorizontalPowerBar(this, () -> 1.0, 115, 75));
    }

    @Override
    public void setRecipe(@NotNull IRecipeLayoutBuilder builder, AdsorptionRecipe recipe, @NotNull IFocusGroup focusGroup) {
        initItem(builder, RecipeIngredientRole.INPUT, inputSlot, recipe.getItemInput().getRepresentations());
        List<@NotNull FluidStack> fluidInputs = recipe.getFluidInput().getRepresentations();
        List<FluidStack> scaledFluids = fluidInputs.stream().map(fluid -> new FluidStack(fluid.getFluid(), fluid.getAmount() * TileEntityAdsorptionSeparator.BASE_TICKS_REQUIRED))
                .toList();
        initFluid(builder, RecipeIngredientRole.INPUT, inputGauge, scaledFluids);
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
    public com.mojang.serialization.Codec<AdsorptionRecipe> getCodec(ICodecHelper codecHelper, IRecipeManager recipeManager) {
        return com.fxd927.mekanismelements.common.registries.MSRecipeSerializers.ADSORPTION_SEPARATOR.get().codec().codec();
    }

    @Override
    public net.minecraft.resources.ResourceLocation getRegistryName(AdsorptionRecipe recipe) {
        return recipe.getId();
    }

    @SuppressWarnings("unchecked")
    private <STACK extends ChemicalStack> IIngredientType<STACK> getIngredientTypeForClass(Class<? extends ChemicalStack> stackClass) {
        // For class-based lookup, we need to check the first stack in the list
        // This is a fallback - ideally we should use getIngredientType(ChemicalStack) instead
        throw new UnsupportedOperationException("Class-based ingredient type lookup not supported in unified chemical system");
    }

    @SuppressWarnings("unchecked")
    private Class<? extends ChemicalStack> getStackClass(ChemicalStack stack) {
        // In unified system, all stacks are ChemicalStack, but we can group by registry
        return ChemicalStack.class;
    }

    @SuppressWarnings("unchecked")
    private ChemicalStack getEmptyStack(Class<? extends ChemicalStack> stackClass) {
        // Return empty stack - in unified system, we use ChemicalStack.EMPTY
        return ChemicalStack.EMPTY;
    }
}