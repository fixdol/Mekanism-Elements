package fixdol.mekanismelements.client.rei;

import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import net.minecraft.resources.ResourceLocation;

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

    MSReiDisplay(MSReiMachine machine, List<EntryIngredient> inputs, List<EntryIngredient> outputs,
                 Optional<ResourceLocation> id, Consumer<MSReiBuilder> layout) {
        super(inputs, outputs, id);
        this.category = machine.categoryId();
        this.layout = layout;
    }

    @Override
    public CategoryIdentifier<MSReiDisplay> getCategoryIdentifier() {
        return category;
    }

    Consumer<MSReiBuilder> layout() {
        return layout;
    }
}
