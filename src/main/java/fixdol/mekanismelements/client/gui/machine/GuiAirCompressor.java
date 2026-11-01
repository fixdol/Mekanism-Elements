package fixdol.mekanismelements.client.gui.machine;

import fixdol.mekanismelements.common.tile.machine.TileEntityAirCompressor;
import mekanism.api.chemical.ChemicalResource;
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
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

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
            list.add(EnergyDisplay.of(tile.energyContainer()).getTextComponent());
            if (tile.chemicalTank.isEmpty()) {
                list.add(MekanismLang.NO_CHEMICAL.translate());
            } else {
                list.add(MekanismLang.GENERIC_STORED_MB.translate(tile.chemicalTank.resource(), TextUtils.format(tile.chemicalTank.amountAsLong())));
            }
            return list;
        }));
        addRenderableWidget(new GuiVerticalPowerBar(this, tile.energyContainer(), 164, 15))
                .warning(WarningTracker.WarningType.NOT_ENOUGH_ENERGY, () -> {
                    MachineEnergyContainer<TileEntityAirCompressor> energyContainer = tile.energyContainer();
                    return energyContainer.getEnergyPerTick() > energyContainer.getAmountAsLong();
                });
        addRenderableWidget(new GuiChemicalGauge(() -> tile.chemicalTank, tile::getChemicalTanks, GaugeType.STANDARD, this, 6, 13))
                .warning(WarningTracker.WarningType.NO_SPACE_IN_OUTPUT, () -> tile.chemicalTank.getNeededAsLong(ChemicalResource.of(TileEntityAirCompressor.COMPRESSED_AIR_STACK)) < TileEntityAirCompressor.COMPRESSED_AIR_STACK.amount());

        addRenderableWidget(new GuiEnergyTab(this, () -> {
            return List.of(EnergyDisplay.of(tile.energyContainer()).getTextComponent());
        }));
    }


    @Override
    protected void drawForegroundText(GuiGraphicsExtractor matrix, int mouseX, int mouseY) {
        renderTitleText(matrix);
        renderInventoryText(matrix);
        super.drawForegroundText(matrix, mouseX, mouseY);
    }
}

