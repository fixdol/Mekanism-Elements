package fixdol.mekanismelements.client.emi;

import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import net.minecraft.resources.ResourceLocation;

import java.util.List;
import java.util.function.Consumer;

/**
 * One recipe of a Mekanism: Elements machine. The items and fluids are real EMI stacks, so they can be looked up from
 * EMI; the layout is drawn with Mekanism's own GUI elements, see {@link MSEmiBuilder}.
 */
public class MSEmiRecipe implements EmiRecipe {
    private final MSEmiMachine machine;
    private final ResourceLocation id;
    private final List<EmiIngredient> inputs;
    private final List<EmiStack> outputs;
    private final Consumer<MSEmiBuilder> layout;

    MSEmiRecipe(MSEmiMachine machine, ResourceLocation id, List<EmiIngredient> inputs, List<EmiStack> outputs, Consumer<MSEmiBuilder> layout) {
        this.machine = machine;
        this.id = id;
        this.inputs = inputs;
        this.outputs = outputs;
        this.layout = layout;
    }

    @Override
    public EmiRecipeCategory getCategory() {
        return machine.category();
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public List<EmiIngredient> getInputs() {
        return inputs;
    }

    @Override
    public List<EmiStack> getOutputs() {
        return outputs;
    }

    @Override
    public int getDisplayWidth() {
        return machine.width;
    }

    @Override
    public int getDisplayHeight() {
        return machine.height;
    }

    @Override
    public void addWidgets(WidgetHolder widgets) {
        layout.accept(new MSEmiBuilder(widgets, machine, this));
    }
}
