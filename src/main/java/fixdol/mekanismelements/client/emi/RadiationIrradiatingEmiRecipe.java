package fixdol.mekanismelements.client.emi;

import dev.emi.emi.api.widget.WidgetHolder;
import fixdol.mekanismelements.api.recipes.RadiationIrradiatingRecipe;
import fixdol.mekanismelements.common.tile.machine.TileEntityRadiationIrradiator;
import mekanism.client.gui.element.bar.GuiHorizontalPowerBar;
import mekanism.client.gui.element.gauge.GaugeType;
import mekanism.client.gui.element.gauge.GuiChemicalGauge;
import mekanism.client.gui.element.progress.ProgressType;
import mekanism.client.gui.element.slot.SlotType;
import mekanism.client.recipe_viewer.RecipeViewerUtils;
import mekanism.client.recipe_viewer.emi.MekanismEmiRecipeCategory;
import mekanism.client.recipe_viewer.emi.recipe.MekanismEmiRecipe;
import mekanism.common.inventory.container.slot.SlotOverlay;
import mekanism.common.tile.component.config.DataType;
import net.minecraft.resources.ResourceLocation;

public class RadiationIrradiatingEmiRecipe extends MekanismEmiRecipe<RadiationIrradiatingRecipe> {
    public RadiationIrradiatingEmiRecipe(MekanismEmiRecipeCategory category, ResourceLocation id, RadiationIrradiatingRecipe recipe) {
        // The layout coordinates are those of the full GUI texture, so shift them by the negated offsets of the type
        // (checked in game: with the positive ones the Infinity Ore Reprocessor overflowed its frame)
        super(category, id, recipe, -category.xOffset(), -category.yOffset(), category.width(), category.height());
        addInputDefinition(recipe.getItemInput());
        addInputDefinition(recipe.getGasInput(), TileEntityRadiationIrradiator.BASE_TICKS_REQUIRED);
        addChemicalOutputDefinition(recipe.getOutputDefinition());
    }

    @Override
    public void addWidgets(WidgetHolder widgets) {
        initTank(widgets, GuiChemicalGauge.getDummy(GaugeType.STANDARD.with(DataType.INPUT), this, 28, 13), input(1));
        initTank(widgets, GuiChemicalGauge.getDummy(GaugeType.STANDARD.with(DataType.OUTPUT), this, 131, 13), output(0)).recipeContext(this);
        addSlot(widgets, SlotType.INPUT, 7, 36, input(0));
        addSlot(widgets, SlotType.EXTRA, 7, 55).with(SlotOverlay.MINUS);
        addSlot(widgets, SlotType.OUTPUT, 152, 55).with(SlotOverlay.PLUS);
        addSlot(widgets, SlotType.POWER, 152, 14).with(SlotOverlay.POWER);
        addSimpleProgress(widgets, ProgressType.LARGE_RIGHT, 64, 40, TileEntityRadiationIrradiator.BASE_TICKS_REQUIRED);
        addElement(widgets, new GuiHorizontalPowerBar(this, RecipeViewerUtils.FULL_BAR, 115, 75));
    }
}
