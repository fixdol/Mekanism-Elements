package fixdol.mekanismelements.client.rei;

import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.Display;
import me.shedaniel.rei.api.common.display.DisplaySerializer;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import mekanism.client.recipe_viewer.type.IRecipeViewerRecipeType;
import net.minecraft.resources.Identifier;
import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * One recipe of a Mekanism: Elements machine. The items and fluids are real REI entries, so they can be looked up
 * from REI; the layout is drawn with Mekanism's own GUI elements, see {@link MSReiBuilder}.
 */
public class MSReiDisplay extends BasicDisplay {
    private final CategoryIdentifier<MSReiDisplay> category;
    private final Consumer<MSReiBuilder> layout;

    public MSReiDisplay(IRecipeViewerRecipeType<?> type, List<EntryIngredient> inputs, List<EntryIngredient> outputs,
                        Optional<Identifier> id, Consumer<MSReiBuilder> layout) {
        super(inputs, outputs, id);
        this.category = CategoryIdentifier.of(type.id());
        this.layout = layout;
    }

    @Override
    public CategoryIdentifier<MSReiDisplay> getCategoryIdentifier() {
        return category;
    }

    /** These displays are built on the client from the synced recipes, so they never travel from the server. */
    @Override
    @Nullable
    public DisplaySerializer<? extends Display> getSerializer() {
        return null;
    }

    public Consumer<MSReiBuilder> layout() {
        return layout;
    }
}
