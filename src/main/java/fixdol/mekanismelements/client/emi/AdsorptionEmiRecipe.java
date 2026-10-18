package fixdol.mekanismelements.client.emi;

import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import fixdol.mekanismelements.api.recipes.AdsorptionRecipe;
import fixdol.mekanismelements.common.tile.machine.TileEntityAdsorptionSeparator;
import mekanism.client.gui.element.bar.GuiHorizontalPowerBar;
import mekanism.client.gui.element.gauge.GaugeType;
import mekanism.client.gui.element.gauge.GuiChemicalGauge;
import mekanism.client.gui.element.gauge.GuiFluidGauge;
import mekanism.client.gui.element.progress.ProgressType;
import mekanism.client.gui.element.slot.SlotType;
import mekanism.client.recipe_viewer.RecipeViewerUtils;
import mekanism.client.recipe_viewer.emi.MekanismEmiRecipeCategory;
import mekanism.client.recipe_viewer.emi.recipe.MekanismEmiRecipe;
import mekanism.common.inventory.container.slot.SlotOverlay;
import mekanism.common.tile.component.config.DataType;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.List;

public class AdsorptionEmiRecipe extends MekanismEmiRecipe<AdsorptionRecipe> {
    private final EmiIngredient fluidInput;

    public AdsorptionEmiRecipe(MekanismEmiRecipeCategory category, ResourceLocation id, AdsorptionRecipe recipe) {
        // The layout coordinates are those of the full GUI texture, so shift them by the negated offsets of the type
        // (checked in game: with the positive ones the Infinity Ore Reprocessor overflowed its frame)
        super(category, id, recipe, -category.xOffset(), -category.yOffset(), category.width(), category.height());
        addInputDefinition(recipe.getItemInput());
        // The machine consumes the fluid for the whole operation, so show the scaled amount like the JEI category does
        List<FluidStack> fluids = recipe.getFluidInput().getRepresentations();
        long amount = fluids.isEmpty() ? 1 : (long) fluids.get(0).getAmount() * TileEntityAdsorptionSeparator.BASE_TICKS_REQUIRED;
        fluidInput = EmiIngredient.of(fluids.stream().<EmiIngredient>map(fluid -> EmiStack.of(fluid.getFluid(), amount)).toList(), amount);
        addChemicalOutputDefinition(recipe.getOutputDefinition());
    }

    @Override
    public List<EmiIngredient> getInputs() {
        List<EmiIngredient> inputs = new ArrayList<>(super.getInputs());
        inputs.add(fluidInput);
        return inputs;
    }

    @Override
    public void addWidgets(WidgetHolder widgets) {
        initTank(widgets, GuiFluidGauge.getDummy(GaugeType.MEDIUM.with(DataType.INPUT), this, 17, 13), fluidInput);
        initTank(widgets, GuiChemicalGauge.getDummy(GaugeType.STANDARD.with(DataType.OUTPUT), this, 131, 13), output(0)).recipeContext(this);
        addSlot(widgets, SlotType.INPUT, 80, 22, input(0));
        addSlot(widgets, SlotType.OUTPUT, 152, 55).with(SlotOverlay.PLUS);
        addSlot(widgets, SlotType.POWER, 152, 14).with(SlotOverlay.POWER);
        addSimpleProgress(widgets, ProgressType.LARGE_RIGHT, 64, 40, TileEntityAdsorptionSeparator.BASE_TICKS_REQUIRED);
        addElement(widgets, new GuiHorizontalPowerBar(this, RecipeViewerUtils.FULL_BAR, 115, 75));
    }
}
