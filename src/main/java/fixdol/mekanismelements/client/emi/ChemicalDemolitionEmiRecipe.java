package fixdol.mekanismelements.client.emi;

import dev.emi.emi.api.widget.WidgetHolder;
import fixdol.mekanismelements.api.recipes.ChemicalDemolitionRecipe;
import fixdol.mekanismelements.common.tile.machine.TileEntityChemicalDemolitionMachine;
import mekanism.client.gui.element.bar.GuiVerticalPowerBar;
import mekanism.client.gui.element.gauge.GaugeType;
import mekanism.client.gui.element.gauge.GuiChemicalGauge;
import mekanism.client.gui.element.progress.ProgressType;
import mekanism.client.gui.element.slot.GuiSlot;
import mekanism.client.gui.element.slot.SlotType;
import mekanism.client.recipe_viewer.RecipeViewerUtils;
import mekanism.client.recipe_viewer.emi.MekanismEmiRecipeCategory;
import mekanism.client.recipe_viewer.emi.recipe.MekanismEmiRecipe;
import mekanism.common.inventory.container.slot.SlotOverlay;
import mekanism.common.tile.component.config.DataType;
import net.minecraft.resources.ResourceLocation;

public class ChemicalDemolitionEmiRecipe extends MekanismEmiRecipe<ChemicalDemolitionRecipe> {
    public ChemicalDemolitionEmiRecipe(MekanismEmiRecipeCategory category, ResourceLocation id, ChemicalDemolitionRecipe recipe) {
        // The layout coordinates are those of the full GUI texture, so shift them by the negated offsets of the type
        // (checked in game: with the positive ones the Infinity Ore Reprocessor overflowed its frame)
        super(category, id, recipe, -category.xOffset(), -category.yOffset(), category.width(), category.height());
        addInputDefinition(recipe.getItemInput());
        addInputDefinition(recipe.getGasInput(), TileEntityChemicalDemolitionMachine.BASE_TICKS_REQUIRED);
        addItemOutputDefinition(recipe.getFirstOutputDefinition());
        addItemOutputDefinition(recipe.getSecondOutputDefinition());
    }

    @Override
    public void addWidgets(WidgetHolder widgets) {
        initTank(widgets, GuiChemicalGauge.getDummy(GaugeType.STANDARD.with(DataType.INPUT), this, 7, 4), input(1));
        GuiSlot outputSlot = addSlot(widgets, SlotType.OUTPUT_WIDE, 112, 31);
        initItem(widgets, outputSlot.getRelativeX() + 4, outputSlot.getRelativeY() + 4, output(0)).recipeContext(this);
        initItem(widgets, outputSlot.getRelativeX() + 20, outputSlot.getRelativeY() + 4, output(1)).recipeContext(this);
        addSlot(widgets, SlotType.INPUT, 28, 36, input(0));
        addSlot(widgets, SlotType.EXTRA, 8, 65).with(SlotOverlay.MINUS);
        addSlot(widgets, SlotType.POWER, 154, 62).with(SlotOverlay.POWER);
        addSimpleProgress(widgets, ProgressType.LARGE_RIGHT, 54, 40, TileEntityChemicalDemolitionMachine.BASE_TICKS_REQUIRED);
        addElement(widgets, new GuiVerticalPowerBar(this, RecipeViewerUtils.FULL_BAR, 164, 5));
    }
}
