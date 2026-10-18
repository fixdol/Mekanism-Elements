package fixdol.mekanismelements.client.emi;

import dev.emi.emi.api.widget.WidgetHolder;
import fixdol.mekanismelements.api.recipes.InfinityOreReprocessingRecipe;
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

public class InfinityOreReprocessingEmiRecipe extends MekanismEmiRecipe<InfinityOreReprocessingRecipe> {
    public InfinityOreReprocessingEmiRecipe(MekanismEmiRecipeCategory category, ResourceLocation id, InfinityOreReprocessingRecipe recipe) {
        // The layout coordinates are those of the full GUI texture, so shift them by the negated offsets of the type
        // (checked in game: with the positive ones the Infinity Ore Reprocessor overflowed its frame)
        super(category, id, recipe, -category.xOffset(), -category.yOffset(), category.width(), category.height());
        addInputDefinition(recipe.getItemInput());
        addInputDefinition(recipe.getChemicalInput());
        addItemOutputDefinition(recipe.getOutputDefinition());
    }

    @Override
    public void addWidgets(WidgetHolder widgets) {
        initTank(widgets, GuiChemicalGauge.getDummy(GaugeType.STANDARD.with(DataType.INPUT), this, 42, 13), input(1));
        GuiSlot itemInput = addSlot(widgets, SlotType.INPUT, 21, 17).with(SlotOverlay.MINUS);
        initItem(widgets, itemInput, input(0));
        addConstantProgress(widgets, ProgressType.LARGE_RIGHT, 64, 40);
        addSlot(widgets, SlotType.OUTPUT, 116, 36, output(0)).recipeContext(this);
        addElement(widgets, new GuiVerticalPowerBar(this, RecipeViewerUtils.FULL_BAR, 164, 15));
        addSlot(widgets, SlotType.POWER, 144, 35).with(SlotOverlay.POWER);
    }
}
