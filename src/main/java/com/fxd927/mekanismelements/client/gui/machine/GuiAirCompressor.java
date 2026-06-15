package com.fxd927.mekanismelements.client.gui.machine;

import com.fxd927.mekanismelements.common.tile.machine.TileEntityAirCompressor;
import mekanism.api.chemical.ChemicalStack;
import mekanism.client.gui.GuiConfigurableTile;
import mekanism.client.gui.element.GuiInnerScreen;
import mekanism.client.gui.element.bar.GuiVerticalPowerBar;
import mekanism.client.gui.element.gauge.GaugeType;
import mekanism.client.gui.element.gauge.GuiChemicalGauge;
import mekanism.client.gui.element.tab.GuiEnergyTab;
import mekanism.common.MekanismLang;
import mekanism.common.capabilities.energy.MachineEnergyContainer;
import mekanism.common.inventory.container.tile.MekanismTileContainer;
import mekanism.common.inventory.warning.WarningTracker;
import mekanism.common.util.text.EnergyDisplay;
import mekanism.common.util.text.TextUtils;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

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
            ChemicalStack chemicalStack = tile.chemicalTank.getStack();
            if (chemicalStack.isEmpty()) {
                list.add(MekanismLang.NO_CHEMICAL.translate());
            } else {
                list.add(MekanismLang.GENERIC_STORED_MB.translate(chemicalStack, TextUtils.format(chemicalStack.getAmount())));
            }
            return list;
        }));
        addRenderableWidget(new GuiVerticalPowerBar(this, tile.getEnergyContainer(), 164, 15))
                .warning(WarningTracker.WarningType.NOT_ENOUGH_ENERGY, () -> {
                    MachineEnergyContainer<TileEntityAirCompressor> energyContainer = tile.getEnergyContainer();
                    return energyContainer.getEnergyPerTick() > energyContainer.getEnergy();
                });
        addRenderableWidget(new GuiChemicalGauge(() -> tile.chemicalTank, () -> tile.getChemicalTanks(null), GaugeType.STANDARD, this, 6, 13))
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

