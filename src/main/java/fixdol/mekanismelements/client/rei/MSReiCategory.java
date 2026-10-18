package fixdol.mekanismelements.client.rei;

import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.util.EntryStacks;
import mekanism.client.recipe_viewer.type.IRecipeViewerRecipeType;
import net.minecraft.network.chat.Component;

import java.util.ArrayList;
import java.util.List;

public class MSReiCategory implements DisplayCategory<MSReiDisplay> {
    /** Margin between the REI panel and the area the machine layout is drawn in. */
    static final int PADDING = 5;

    private final IRecipeViewerRecipeType<?> type;

    public MSReiCategory(IRecipeViewerRecipeType<?> type) {
        this.type = type;
    }

    @Override
    public CategoryIdentifier<? extends MSReiDisplay> getCategoryIdentifier() {
        return CategoryIdentifier.of(type.id());
    }

    @Override
    public Component getTitle() {
        return type.getTextComponent();
    }

    @Override
    public Renderer getIcon() {
        return EntryStacks.of(type.iconStack());
    }

    @Override
    public int getDisplayWidth(MSReiDisplay display) {
        return type.width() + 2 * PADDING;
    }

    @Override
    public int getDisplayHeight() {
        return type.height() + 2 * PADDING;
    }

    @Override
    public List<Widget> setupDisplay(MSReiDisplay display, Rectangle bounds) {
        List<Widget> widgets = new ArrayList<>();
        widgets.add(Widgets.createRecipeBase(bounds));
        display.layout().accept(new MSReiBuilder(bounds, type, widgets));
        return widgets;
    }
}
