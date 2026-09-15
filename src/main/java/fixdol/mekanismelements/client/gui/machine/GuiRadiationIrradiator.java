package fixdol.mekanismelements.client.gui.machine;

import mekanism.api.recipes.cache.CachedRecipe;
import net.minecraft.network.chat.Component;
import mekanism.client.gui.element.gauge.GaugeType;
import mekanism.client.gui.element.gauge.GuiGasGauge;
import mekanism.client.gui.GuiConfigurableTile;
import mekanism.client.gui.element.tab.GuiEnergyTab;
import net.minecraft.client.gui.GuiGraphics;
import mekanism.client.gui.element.bar.GuiHorizontalPowerBar;
import mekanism.client.gui.element.progress.GuiProgress;
import net.minecraft.world.entity.player.Inventory;
import mekanism.common.inventory.container.tile.MekanismTileContainer;
import org.jetbrains.annotations.NotNull;
import mekanism.client.gui.element.progress.ProgressType;
import mekanism.common.inventory.warning.WarningTracker;

import fixdol.mekanismelements.common.tile.machine.TileEntityRadiationIrradiator;

public class GuiRadiationIrradiator extends GuiConfigurableTile<TileEntityRadiationIrradiator, MekanismTileContainer<TileEntityRadiationIrradiator>> {
    public GuiRadiationIrradiator(MekanismTileContainer<TileEntityRadiationIrradiator> container, Inventory inv, Component title) {
        super(container, inv, title);
        dynamicSlots = true;
        titleLabelY = 4;
    }

    @Override
    protected void addGuiElements() {
        super.addGuiElements();
        addRenderableWidget(new GuiHorizontalPowerBar(this, tile.getEnergyContainer(), 115, 75))
                .warning(WarningTracker.WarningType.NOT_ENOUGH_ENERGY, tile.getWarningCheck(CachedRecipe.OperationTracker.RecipeError.NOT_ENOUGH_ENERGY))
                .warning(WarningTracker.WarningType.NOT_ENOUGH_ENERGY_REDUCED_RATE, tile.getWarningCheck(CachedRecipe.OperationTracker.RecipeError.NOT_ENOUGH_ENERGY_REDUCED_RATE));
        addRenderableWidget(new GuiEnergyTab(this, tile.getEnergyContainer(), tile::getActive));
        addRenderableWidget(new GuiGasGauge(() -> tile.injectTank, () -> tile.getGasTanks(null), GaugeType.STANDARD, this, 28, 13))
                .warning(WarningTracker.WarningType.NO_MATCHING_RECIPE, tile.getWarningCheck(CachedRecipe.OperationTracker.RecipeError.NOT_ENOUGH_SECONDARY_INPUT));
        addRenderableWidget(new GuiGasGauge(() -> tile.chemicalOutputTank, () -> tile.getGasTanks(null), GaugeType.STANDARD, this, 131, 13))
                .warning(WarningTracker.WarningType.NO_SPACE_IN_OUTPUT, tile.getWarningCheck(CachedRecipe.OperationTracker.RecipeError.NOT_ENOUGH_OUTPUT_SPACE));
        addRenderableWidget(new GuiProgress(tile::getScaledProgress, ProgressType.LARGE_RIGHT, this, 64, 40)
                .jeiCategories(fixdol.mekanismelements.client.MSJEIRecipeType.RADIATION_IRRADIATOR))
                .warning(WarningTracker.WarningType.INPUT_DOESNT_PRODUCE_OUTPUT, tile.getWarningCheck(CachedRecipe.OperationTracker.RecipeError.INPUT_DOESNT_PRODUCE_OUTPUT));
    }

    @Override
    protected void drawForegroundText(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY) {
        renderTitleText(guiGraphics);
        super.drawForegroundText(guiGraphics, mouseX, mouseY);
    }
}