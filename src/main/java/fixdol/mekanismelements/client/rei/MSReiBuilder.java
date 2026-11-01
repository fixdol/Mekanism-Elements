package fixdol.mekanismelements.client.rei;

import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.widgets.Tooltip;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import mekanism.api.chemical.ChemicalStack;
import mekanism.client.gui.GuiUtils;
import mekanism.client.gui.IGuiWrapper;
import mekanism.client.gui.element.GuiElement;
import mekanism.client.gui.element.GuiInnerScreen;
import mekanism.client.gui.element.bar.GuiHorizontalPowerBar;
import mekanism.client.gui.element.bar.GuiVerticalPowerBar;
import mekanism.client.gui.element.gauge.GaugeType;
import mekanism.client.gui.element.gauge.GuiChemicalGauge;
import mekanism.client.gui.element.gauge.GuiFluidGauge;
import mekanism.client.gui.element.gauge.GuiGauge;
import mekanism.client.gui.element.progress.GuiProgress;
import mekanism.client.gui.element.progress.ProgressType;
import mekanism.client.gui.element.slot.GuiSlot;
import mekanism.client.gui.element.slot.SlotType;
import mekanism.client.recipe_viewer.RecipeViewerUtils;
import mekanism.client.recipe_viewer.type.IRecipeViewerRecipeType;
import mekanism.client.render.MekanismRenderer;
import mekanism.common.MekanismLang;
import mekanism.common.tile.component.config.DataType;
import mekanism.common.util.text.TextUtils;
import net.minecraft.client.gui.components.events.AbstractContainerEventHandler;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.network.chat.Component;
import net.neoforged.neoforge.fluids.FluidStack;
import org.joml.Matrix3x2fStack;

import java.util.List;
import java.util.function.IntSupplier;
import java.util.function.Supplier;

/**
 * Draws a machine layout in REI. Mekanism has no REI support, so the layouts are built from the same GUI elements its
 * JEI and EMI plugins use: this class is the {@link IGuiWrapper} those elements need and turns each of them into an REI
 * widget. Items and fluids are real REI slots; chemicals have no REI entry type, so they are drawn in their gauge and
 * can be hovered for their name and amount, but not looked up.
 */
public class MSReiBuilder extends AbstractContainerEventHandler implements IGuiWrapper {
    private final List<Widget> widgets;
    private final int guiLeft;
    private final int guiTop;
    private final int xSize;
    private final int ySize;

    MSReiBuilder(Rectangle bounds, IRecipeViewerRecipeType<?> type, List<Widget> widgets) {
        this.widgets = widgets;
        // The layout coordinates are those of the full GUI texture, whose visible part starts at the type's offset
        this.guiLeft = bounds.x + MSReiCategory.PADDING - type.xOffset();
        this.guiTop = bounds.y + MSReiCategory.PADDING - type.yOffset();
        this.xSize = type.width();
        this.ySize = type.height();
    }

    public <ELEMENT extends GuiElement> ELEMENT addElement(ELEMENT element) {
        widgets.add(Widgets.createDrawableWidget((graphics, mouseX, mouseY, delta) -> {
            // Same sequence Mekanism's own EMI widget uses to draw a GUI element
            Matrix3x2fStack pose = graphics.pose();
            pose.pushMatrix();
            pose.translate((float) element.getGuiLeft(), (float) element.getGuiTop());
            element.renderShifted(graphics, mouseX, mouseY, 0);
            element.onDrawBackground(graphics, mouseX, mouseY, 0);
            element.onRenderForeground(graphics, mouseX, mouseY);
            pose.popMatrix();
        }));
        return element;
    }

    public GuiSlot slot(SlotType type, int x, int y) {
        return addElement(new GuiSlot(type, this, x, y));
    }

    public GuiProgress progress(ProgressType type, int x, int y, int ticks) {
        return addElement(new GuiProgress(RecipeViewerUtils.progressHandler(ticks), type, this, x, y));
    }

    public GuiProgress constantProgress(ProgressType type, int x, int y) {
        return addElement(new GuiProgress(RecipeViewerUtils.CONSTANT_PROGRESS, type, this, x, y));
    }

    public void horizontalPowerBar(int x, int y) {
        addElement(new GuiHorizontalPowerBar(this, RecipeViewerUtils.FULL_BAR, x, y));
    }

    public void verticalPowerBar(int x, int y) {
        addElement(new GuiVerticalPowerBar(this, RecipeViewerUtils.FULL_BAR, x, y));
    }

    public void infoScreen(int x, int y, int width, int height, Component text) {
        addElement(new GuiInnerScreen(this, x, y, width, height, () -> fixdol.mekanismelements.client.MSRecipeViewerText.wrapForInnerScreen(text)).clearFormat());
    }

    public void chemicalGauge(GaugeType type, DataType data, int x, int y, ChemicalStack stack) {
        GuiChemicalGauge gauge = GuiChemicalGauge.getDummy(type.with(data), this, x, y);
        addElement(gauge);
        if (!stack.isEmpty()) {
            fill(gauge, () -> MekanismRenderer.color(stack), () -> MekanismRenderer.getChemicalTexture(stack));
        }
        tooltip(gauge, stack, stack.isEmpty() ? 0 : stack.amount());
    }

    public void fluidGauge(GaugeType type, DataType data, int x, int y, FluidStack stack) {
        GuiFluidGauge gauge = GuiFluidGauge.getDummy(type.with(data), this, x, y);
        addElement(gauge);
        if (!stack.isEmpty()) {
            fill(gauge, () -> MekanismRenderer.color(stack),
                    () -> MekanismRenderer.getFluidTexture(stack, MekanismRenderer.FluidTextureType.STILL));
        }
        tooltip(gauge, stack, stack.isEmpty() ? 0 : stack.amount());
    }

    /**
     * A dummy gauge has no way to hold a chemical or fluid, so it only draws its frame. JEI and EMI fill it with the
     * ingredient, so do the same here: the sprite of the chemical or fluid at full level, then the scale marks on top
     * (what Mekanism's EMI tank widget does).
     */
    private void fill(GuiGauge<?> gauge, IntSupplier color, Supplier<TextureAtlasSprite> sprite) {
        widgets.add(Widgets.createDrawableWidget((graphics, mouseX, mouseY, delta) -> {
            int width = gauge.getWidth() - 2;
            int height = gauge.getHeight() - 2;
            GuiUtils.drawTiledSprite(graphics, gauge.getX() + 1, gauge.getY() + 1, height, width, height, sprite.get(), 16, 16, 0,
                    GuiUtils.TilingDirection.UP_RIGHT, color.getAsInt());
            Matrix3x2fStack pose = graphics.pose();
            pose.pushMatrix();
            pose.translate((float) gauge.getGuiLeft(), (float) gauge.getGuiTop());
            gauge.drawBarOverlay(graphics);
            pose.popMatrix();
        }));
    }

    /** What the gauge would show if it held the stack: a dummy gauge always reports itself as empty. */
    private void tooltip(GuiGauge<?> gauge, Object stack, long amount) {
        Rectangle area = new Rectangle(gauge.getX(), gauge.getY(), gauge.getWidth(), gauge.getHeight());
        List<Component> text = amount > 0 ? List.of(MekanismLang.GENERIC_STORED_MB.translate(stack, TextUtils.format(amount))) : gauge.getTooltipText();
        widgets.add(Widgets.createTooltip(point -> area.contains(point) ? Tooltip.create(point, text) : null));
    }

    /** An REI slot over the frame of a Mekanism slot, so its item or fluid can be looked up from REI. */
    public void entries(GuiSlot frame, EntryIngredient ingredient, boolean output) {
        entriesAt(frame.getX(), frame.getY(), ingredient, output);
    }

    /** An REI slot whose item sits at the given layout position (the 16x16 item, not the 18x18 slot). */
    public void entriesAtItem(int x, int y, EntryIngredient ingredient, boolean output) {
        entriesAt(guiLeft + x - 1, guiTop + y - 1, ingredient, output);
    }

    private void entriesAt(int screenX, int screenY, EntryIngredient ingredient, boolean output) {
        var slot = Widgets.createSlot(new Point(screenX, screenY)).entries(ingredient).disableBackground();
        widgets.add(output ? slot.markOutput() : slot.markInput());
    }

    @Override
    public int getGuiLeft() {
        return guiLeft;
    }

    @Override
    public int getGuiTop() {
        return guiTop;
    }

    @Override
    public int getXSize() {
        return xSize;
    }

    @Override
    public int getYSize() {
        return ySize;
    }

    @Override
    public List<? extends GuiEventListener> children() {
        return List.of();
    }
}
