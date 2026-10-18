package fixdol.mekanismelements.client.rei;

import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.network.chat.Component;

import java.util.ArrayList;
import java.util.List;

public class MSReiCategory implements DisplayCategory<MSReiDisplay> {
    /** Margin between the REI panel and the area the machine layout is drawn in. */
    static final int PADDING = 5;

    private final MSReiMachine machine;

    MSReiCategory(MSReiMachine machine) {
        this.machine = machine;
    }

    @Override
    public CategoryIdentifier<? extends MSReiDisplay> getCategoryIdentifier() {
        return machine.categoryId();
    }

    @Override
    public Component getTitle() {
        return machine.title();
    }

    @Override
    public Renderer getIcon() {
        return EntryStacks.of(machine.block().asItem());
    }

    @Override
    public int getDisplayWidth(MSReiDisplay display) {
        return machine.width() + 2 * PADDING;
    }

    @Override
    public int getDisplayHeight() {
        return machine.height() + 2 * PADDING;
    }

    @Override
    public List<Widget> setupDisplay(MSReiDisplay display, Rectangle bounds) {
        List<Widget> widgets = new ArrayList<>();
        widgets.add(Widgets.createRecipeBase(bounds));
        display.layout().accept(new MSReiBuilder(bounds, machine, widgets));
        return widgets;
    }
}
