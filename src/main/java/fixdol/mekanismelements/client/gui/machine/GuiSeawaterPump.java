package fixdol.mekanismelements.client.gui.machine;

import java.util.ArrayList;
import net.minecraft.network.chat.Component;
import mekanism.common.util.text.EnergyDisplay;
import net.minecraftforge.fluids.FluidStack;
import mekanism.client.gui.element.gauge.GaugeType;
import mekanism.client.gui.GuiConfigurableTile;
import mekanism.client.gui.element.tab.GuiEnergyTab;
import mekanism.client.gui.element.gauge.GuiFluidGauge;
import net.minecraft.client.gui.GuiGraphics;
import mekanism.client.gui.element.GuiInnerScreen;
import mekanism.client.gui.element.bar.GuiVerticalPowerBar;
import net.minecraft.world.entity.player.Inventory;
import java.util.List;
import mekanism.common.capabilities.energy.MachineEnergyContainer;
import mekanism.common.MekanismLang;
import mekanism.common.inventory.container.tile.MekanismTileContainer;
import javax.annotation.Nonnull;
import mekanism.common.util.text.TextUtils;
import mekanism.common.inventory.warning.WarningTracker;

import fixdol.mekanismelements.common.tile.machine.TileEntitySeawaterPump;


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
            list.add(EnergyDisplay.of(tile.getEnergyContainer()).getTextComponent());
            FluidStack fluidStack = tile.fluidTank.getFluid();
            if (fluidStack.isEmpty()) {
                list.add(MekanismLang.NO_FLUID.translate());
            } else {
                list.add(MekanismLang.GENERIC_STORED_MB.translate(fluidStack, TextUtils.format(fluidStack.getAmount())));
            }
            return list;
        }));
        addRenderableWidget(new GuiVerticalPowerBar(this, tile.getEnergyContainer(), 164, 15))
                .warning(WarningTracker.WarningType.NOT_ENOUGH_ENERGY, () -> {
                    MachineEnergyContainer<TileEntitySeawaterPump> energyContainer = tile.getEnergyContainer();
                    return energyContainer.getEnergyPerTick().greaterThan(energyContainer.getEnergy());
                });
        addRenderableWidget(new GuiFluidGauge(() -> tile.fluidTank, () -> tile.getFluidTanks(null), GaugeType.STANDARD, this, 6, 13))
                .warning(WarningTracker.WarningType.NO_SPACE_IN_OUTPUT, () -> tile.fluidTank.getNeeded() < TileEntitySeawaterPump.SEAWATER_STACK.getAmount());

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


