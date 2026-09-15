package fixdol.mekanismelements.client.gui.machine;

import net.minecraft.network.chat.Component;
import mekanism.api.chemical.gas.GasStack;
import mekanism.client.gui.element.gauge.GaugeType;
import mekanism.client.gui.element.gauge.GuiGasGauge;
import mekanism.client.gui.GuiConfigurableTile;
import mekanism.client.gui.element.tab.GuiEnergyTab;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.entity.player.Inventory;
import java.util.List;
import mekanism.common.inventory.container.tile.MekanismTileContainer;
import mekanism.common.inventory.warning.WarningTracker;

import fixdol.mekanismelements.common.tile.machine.TileEntityAirCompressor;
import mekanism.client.gui.element.GuiInnerScreen;
import mekanism.client.gui.element.bar.GuiVerticalPowerBar;
import mekanism.common.MekanismLang;
import mekanism.common.capabilities.energy.MachineEnergyContainer;
import mekanism.common.util.text.EnergyDisplay;
import mekanism.common.util.text.TextUtils;

import javax.annotation.Nonnull;
import java.util.ArrayList;

public class GuiAirCompressor extends GuiConfigurableTile<TileEntityAirCompressor, MekanismTileContainer<TileEntityAirCompressor>> {
    public GuiAirCompressor(MekanismTileContainer<TileEntityAirCompressor> container, Inventory inv, Component title) {
        super(container, inv, title);
        inventoryLabelY += 2;
        dynamicSlots = true;
    }

    @Override
    protected void addGuiElements() {
        super.addGuiElements();
        addRenderableWidget(new GuiInnerScreen(this, 54, 23, 80, 41, () -> {
            List<Component> list = new ArrayList<>();
            list.add(EnergyDisplay.of(tile.getEnergyContainer()).getTextComponent());
            GasStack chemicalStack = tile.chemicalTank.getStack();
            if (chemicalStack.isEmpty()) {
                list.add(MekanismLang.NO_GAS.translate());
            } else {
                list.add(MekanismLang.GENERIC_STORED_MB.translate(chemicalStack, TextUtils.format(chemicalStack.getAmount())));
            }
            return list;
        }));
        addRenderableWidget(new GuiVerticalPowerBar(this, tile.getEnergyContainer(), 164, 15))
                .warning(WarningTracker.WarningType.NOT_ENOUGH_ENERGY, () -> {
                    MachineEnergyContainer<TileEntityAirCompressor> energyContainer = tile.getEnergyContainer();
                    return energyContainer.getEnergyPerTick().greaterThan(energyContainer.getEnergy());
                });
        addRenderableWidget(new GuiGasGauge(() -> tile.chemicalTank, () -> tile.getGasTanks(null), GaugeType.STANDARD, this, 6, 13))
                .warning(WarningTracker.WarningType.NO_SPACE_IN_OUTPUT, () -> tile.chemicalTank.getNeeded() < TileEntityAirCompressor.COMPRESSED_AIR_STACK.getAmount());

        addRenderableWidget(new GuiEnergyTab(this, () -> {
            return List.of(EnergyDisplay.of(tile.getEnergyContainer()).getTextComponent());
        }));
    }


    @Override
    protected void drawForegroundText(@Nonnull GuiGraphics matrix, int mouseX, int mouseY) {
        renderTitleText(matrix);
        matrix.drawString(font, playerInventoryTitle, inventoryLabelX, inventoryLabelY, titleTextColor());
        super.drawForegroundText(matrix, mouseX, mouseY);
    }
}

