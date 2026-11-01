package fixdol.mekanismelements.client.gui.machine;

import fixdol.mekanismelements.common.tile.machine.TileEntitySeawaterPump;
import mekanism.client.gui.GuiConfigurableTile;
import mekanism.client.gui.element.GuiInnerScreen;
import mekanism.client.gui.element.bar.GuiVerticalPowerBar;
import mekanism.client.gui.element.gauge.GaugeType;
import mekanism.client.gui.element.gauge.GuiFluidGauge;
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
import net.neoforged.neoforge.transfer.fluid.FluidResource;

import java.util.ArrayList;
import java.util.List;

public class GuiSeawaterPump extends GuiConfigurableTile<TileEntitySeawaterPump, MekanismTileContainer<TileEntitySeawaterPump>> {
    public GuiSeawaterPump(MekanismTileContainer<TileEntitySeawaterPump> container, Inventory inv, Component title) {
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
            if (tile.fluidTank.isEmpty()) {
                list.add(MekanismLang.NO_FLUID.translate());
            } else {
                list.add(MekanismLang.GENERIC_STORED_MB.translate(tile.fluidTank.resource(), TextUtils.format(tile.fluidTank.amountAsLong())));
            }
            return list;
        }));
        addRenderableWidget(new GuiVerticalPowerBar(this, tile.energyContainer(), 164, 15))
                .warning(WarningTracker.WarningType.NOT_ENOUGH_ENERGY, () -> {
                    MachineEnergyContainer<TileEntitySeawaterPump> energyContainer = tile.energyContainer();
                    return energyContainer.getEnergyPerTick() > energyContainer.getAmountAsLong();
                });
        addRenderableWidget(new GuiFluidGauge(() -> tile.fluidTank, tile::getFluidTanks, GaugeType.STANDARD, this, 6, 13))
                .warning(WarningTracker.WarningType.NO_SPACE_IN_OUTPUT, () -> tile.fluidTank.getNeededAsInt(FluidResource.of(TileEntitySeawaterPump.SEAWATER_STACK)) < TileEntitySeawaterPump.SEAWATER_STACK.amount());

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


