package fixdol.mekanismelements.client.emi;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.widget.Bounds;
import dev.emi.emi.api.widget.SlotWidget;
import dev.emi.emi.api.widget.Widget;
import dev.emi.emi.api.widget.WidgetHolder;
import fixdol.mekanismelements.client.jei.MSScreenText;
import mekanism.api.chemical.ChemicalStack;
import mekanism.api.chemical.ChemicalType;
import mekanism.api.chemical.gas.GasStack;
import mekanism.api.chemical.infuse.InfusionStack;
import mekanism.api.chemical.pigment.PigmentStack;
import mekanism.api.chemical.slurry.SlurryStack;
import mekanism.client.gui.GuiUtils;
import mekanism.client.gui.IGuiWrapper;
import mekanism.client.gui.element.GuiElement;
import mekanism.client.gui.element.GuiInnerScreen;
import mekanism.client.gui.element.bar.GuiHorizontalPowerBar;
import mekanism.client.gui.element.bar.GuiVerticalPowerBar;
import mekanism.client.gui.element.gauge.GaugeType;
import mekanism.client.gui.element.gauge.GuiFluidGauge;
import mekanism.client.gui.element.gauge.GuiGasGauge;
import mekanism.client.gui.element.gauge.GuiGauge;
import mekanism.client.gui.element.gauge.GuiInfusionGauge;
import mekanism.client.gui.element.gauge.GuiPigmentGauge;
import mekanism.client.gui.element.gauge.GuiSlurryGauge;
import mekanism.client.gui.element.progress.GuiProgress;
import mekanism.client.gui.element.progress.ProgressType;
import mekanism.client.gui.element.slot.GuiSlot;
import mekanism.client.gui.element.slot.SlotType;
import mekanism.client.render.MekanismRenderer;
import mekanism.common.tile.component.config.DataType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.network.chat.Component;
import net.minecraftforge.fluids.FluidStack;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Draws a machine layout in EMI. Mekanism 10.4 has no EMI support, so the layouts are built from the same GUI elements
 * its JEI plugin uses: this class is the {@link IGuiWrapper} those elements need and turns each of them into an EMI
 * widget. Items and fluids are real EMI slots; chemicals have no EMI stack type, so they are drawn in their gauge and
 * can be hovered for their name and amount, but not looked up.
 */
public class MSEmiBuilder implements IGuiWrapper {
    private final WidgetHolder widgets;
    private final MSEmiRecipe recipe;
    private final int guiLeft;
    private final int guiTop;
    private final int width;
    private final int height;

    MSEmiBuilder(WidgetHolder widgets, MSEmiMachine machine, MSEmiRecipe recipe) {
        this.widgets = widgets;
        this.recipe = recipe;
        // The layout coordinates are those of the full GUI texture, whose visible part starts at the machine's offset
        this.guiLeft = -machine.xOffset;
        this.guiTop = -machine.yOffset;
        this.width = machine.width;
        this.height = machine.height;
    }

    public <ELEMENT extends GuiElement> ELEMENT addElement(ELEMENT element) {
        widgets.add(new Widget() {
            @Override
            public Bounds getBounds() {
                return new Bounds(element.getX(), element.getY(), element.getWidth(), element.getHeight());
            }

            @Override
            public void render(GuiGraphics graphics, int mouseX, int mouseY, float delta) {
                // Same sequence Mekanism's own EMI widget (10.7) uses to draw a GUI element
                PoseStack pose = graphics.pose();
                pose.pushPose();
                pose.translate((float) element.getGuiLeft(), (float) element.getGuiTop(), 0);
                element.renderShifted(graphics, mouseX, mouseY, 0);
                element.onDrawBackground(graphics, mouseX, mouseY, 0);
                pose.pushPose();
                element.onRenderForeground(graphics, mouseX, mouseY, 200, 200);
                pose.popPose();
                pose.popPose();
            }

            @Override
            public List<ClientTooltipComponent> getTooltip(int mouseX, int mouseY) {
                if (element instanceof GuiGauge<?> gauge) {
                    return gauge.getTooltipText().stream().<ClientTooltipComponent>map(line -> ClientTooltipComponent.create(line.getVisualOrderText())).toList();
                }
                return List.of();
            }
        });
        return element;
    }

    public GuiSlot slot(SlotType type, int x, int y) {
        return addElement(new GuiSlot(type, this, x, y));
    }

    public GuiProgress progress(ProgressType type, int x, int y, int ticks) {
        long millis = ticks * 50L;
        return addElement(new GuiProgress(() -> (System.currentTimeMillis() % millis) / (double) millis, type, this, x, y));
    }

    public GuiProgress constantProgress(ProgressType type, int x, int y) {
        return addElement(new GuiProgress(() -> 1.0, type, this, x, y));
    }

    public void horizontalPowerBar(int x, int y) {
        addElement(new GuiHorizontalPowerBar(this, () -> 1.0, x, y));
    }

    public void verticalPowerBar(int x, int y) {
        addElement(new GuiVerticalPowerBar(this, () -> 1.0, x, y));
    }

    public void infoScreen(int x, int y, int width, int height, Component text) {
        addElement(new GuiInnerScreen(this, x, y, width, height, () -> MSScreenText.wrap(text, 112)).clearFormat());
    }

    public void chemicalGauge(GaugeType type, DataType data, int x, int y, ChemicalType chemicalType, ChemicalStack<?> stack) {
        GuiGauge<?> gauge = switch (chemicalType) {
            case INFUSION -> {
                GuiInfusionGauge infusion = GuiInfusionGauge.getDummy(type.with(data), this, x, y);
                infusion.setDummyType(((InfusionStack) stack).getType());
                yield infusion;
            }
            case PIGMENT -> {
                GuiPigmentGauge pigment = GuiPigmentGauge.getDummy(type.with(data), this, x, y);
                pigment.setDummyType(((PigmentStack) stack).getType());
                yield pigment;
            }
            case SLURRY -> {
                GuiSlurryGauge slurry = GuiSlurryGauge.getDummy(type.with(data), this, x, y);
                slurry.setDummyType(((SlurryStack) stack).getType());
                yield slurry;
            }
            default -> {
                GuiGasGauge gas = GuiGasGauge.getDummy(type.with(data), this, x, y);
                gas.setDummyType(((GasStack) stack).getType());
                yield gas;
            }
        };
        addElement(gauge);
        if (!stack.isEmpty()) {
            fill(gauge, graphics -> MekanismRenderer.color(graphics, stack), () -> MekanismRenderer.getChemicalTexture(stack.getType()));
        }
    }

    public void gasGauge(GaugeType type, DataType data, int x, int y, GasStack stack) {
        chemicalGauge(type, data, x, y, ChemicalType.GAS, stack);
    }

    public void fluidGauge(GaugeType type, DataType data, int x, int y, FluidStack stack) {
        GuiFluidGauge gauge = GuiFluidGauge.getDummy(type.with(data), this, x, y);
        gauge.setDummyType(stack);
        addElement(gauge);
        if (!stack.isEmpty()) {
            fill(gauge, graphics -> MekanismRenderer.color(graphics, stack),
                    () -> MekanismRenderer.getFluidTexture(stack, MekanismRenderer.FluidTextureType.STILL));
        }
    }

    /**
     * A dummy gauge only draws its frame. JEI fills it with the ingredient, so do the same here: the sprite of the
     * chemical or fluid at full level, then the scale marks on top.
     */
    private void fill(GuiGauge<?> gauge, Consumer<GuiGraphics> color, Supplier<TextureAtlasSprite> sprite) {
        widgets.add(new Widget() {
            @Override
            public Bounds getBounds() {
                return new Bounds(gauge.getX(), gauge.getY(), gauge.getWidth(), gauge.getHeight());
            }

            @Override
            public void render(GuiGraphics graphics, int mouseX, int mouseY, float delta) {
                int width = gauge.getWidth() - 2;
                int height = gauge.getHeight() - 2;
                color.accept(graphics);
                GuiUtils.drawTiledSprite(graphics, gauge.getX() + 1, gauge.getY() + 1, height, width, height, sprite.get(), 16, 16, 0,
                        GuiUtils.TilingDirection.UP_RIGHT);
                MekanismRenderer.resetColor(graphics);
                PoseStack pose = graphics.pose();
                pose.pushPose();
                pose.translate((float) gauge.getGuiLeft(), (float) gauge.getGuiTop(), 0);
                gauge.drawBarOverlay(graphics);
                pose.popPose();
            }
        });
    }

    /** An EMI slot over the frame of a Mekanism slot, so its item can be looked up from EMI. */
    public void entries(GuiSlot frame, EmiIngredient ingredient, boolean output) {
        entriesAt(frame.getX(), frame.getY(), ingredient, output);
    }

    /** An EMI slot whose item sits at the given layout position (the 16x16 item, not the 18x18 slot). */
    public void entriesAtItem(int x, int y, EmiIngredient ingredient, boolean output) {
        entriesAt(guiLeft + x - 1, guiTop + y - 1, ingredient, output);
    }

    private void entriesAt(int x, int y, EmiIngredient ingredient, boolean output) {
        SlotWidget slot = widgets.add(new SlotWidget(ingredient, x, y).drawBack(false));
        if (output) {
            slot.recipeContext(recipe);
        }
    }

    @Override
    public int getLeft() {
        return guiLeft;
    }

    @Override
    public int getTop() {
        return guiTop;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public Font getFont() {
        return Minecraft.getInstance().font;
    }
}
