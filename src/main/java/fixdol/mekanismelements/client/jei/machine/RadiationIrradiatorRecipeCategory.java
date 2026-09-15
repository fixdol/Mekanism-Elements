package fixdol.mekanismelements.client.jei.machine;

import mekanism.client.jei.BaseRecipeCategory;
import java.util.Collections;
import mekanism.common.tile.component.config.DataType;
import mekanism.api.chemical.gas.GasStack;
import mekanism.client.gui.element.gauge.GaugeType;
import mekanism.client.gui.element.gauge.GuiGasGauge;
import mekanism.client.gui.element.gauge.GuiGauge;
import mekanism.client.gui.element.bar.GuiHorizontalPowerBar;
import mekanism.client.gui.element.slot.GuiSlot;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredientType;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import java.util.List;
import mekanism.client.jei.MekanismJEI;
import mekanism.client.jei.MekanismJEIRecipeType;
import org.jetbrains.annotations.NotNull;
import mekanism.client.gui.element.progress.ProgressType;
import fixdol.mekanismelements.api.recipes.RadiationIrradiatingRecipe;
import fixdol.mekanismelements.client.jei.machine.RadiationIrradiatorRecipeCategory;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mekanism.common.inventory.container.slot.SlotOverlay;
import mekanism.client.gui.element.slot.SlotType;
import fixdol.mekanismelements.common.tile.machine.TileEntityRadiationIrradiator;



public class RadiationIrradiatorRecipeCategory extends BaseRecipeCategory<RadiationIrradiatingRecipe> {
    private final GuiGauge<?> inputGauge;
    private final GuiGauge<?> outputGauge;
    private final GuiSlot inputSlot;

    public RadiationIrradiatorRecipeCategory(IGuiHelper helper, MekanismJEIRecipeType<RadiationIrradiatingRecipe> recipeType) {
        super(helper, recipeType, fixdol.mekanismelements.common.registries.MSBlocks.RADIATION_IRRADIATOR, 3, 3, 170, 79);
        inputGauge = addElement(GuiGasGauge.getDummy(GaugeType.STANDARD.with(DataType.INPUT), this, 28, 13));
        outputGauge = addElement(GuiGasGauge.getDummy(GaugeType.STANDARD.with(DataType.OUTPUT), this, 131, 13));
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
        List<@NotNull GasStack> chemicalInputs = recipe.getGasInput().getRepresentations();
        List<GasStack> scaledChemicals = chemicalInputs.stream().map(chemical -> mekanism.common.util.ChemicalUtil.copyWithAmount(chemical, chemical.getAmount() * TileEntityRadiationIrradiator.BASE_TICKS_REQUIRED))
                .toList();
        initChemical(builder, mekanism.client.jei.MekanismJEI.TYPE_GAS, RecipeIngredientRole.INPUT, inputGauge, scaledChemicals);
        List<GasStack> outputDefinition = recipe.getOutputDefinition();
        if (outputDefinition.size() == 1) {
            GasStack output = outputDefinition.get(0);
            initChemicalOutput(builder, getIngredientType(output), Collections.singletonList(output));
        } else {
            // In unified system, all outputs use TYPE_CHEMICAL
            initChemicalOutput(builder, MekanismJEI.TYPE_GAS, outputDefinition);
        }
    }

    @SuppressWarnings("unchecked")
    private <STACK extends GasStack> void initChemicalOutput(IRecipeLayoutBuilder builder, IIngredientType<STACK> type, List<GasStack> stacks) {
        initChemical(builder, mekanism.client.jei.MekanismJEI.TYPE_GAS, RecipeIngredientRole.OUTPUT, outputGauge, stacks);
    }

    @SuppressWarnings("unchecked")
    private <STACK extends GasStack> IIngredientType<STACK> getIngredientType(GasStack stack) {
        // Use the unified TYPE_CHEMICAL for all chemical stacks in Mekanism 10.7
        return (IIngredientType<STACK>) MekanismJEI.TYPE_GAS;
    }
}