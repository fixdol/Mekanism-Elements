package fixdol.mekanismelements.client.jei.machine;

import mekanism.client.jei.BaseRecipeCategory;
import mekanism.common.tile.component.config.DataType;
import mekanism.client.gui.element.gauge.GaugeType;
import mekanism.client.gui.element.gauge.GuiGasGauge;
import mekanism.client.gui.element.gauge.GuiGauge;
import mekanism.client.gui.element.slot.GuiSlot;
import mekanism.client.gui.element.bar.GuiVerticalPowerBar;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import fixdol.mekanismelements.client.jei.machine.InfinityOreReprocessingRecipeCategory;
import mekanism.client.jei.MekanismJEIRecipeType;
import org.jetbrains.annotations.NotNull;
import mekanism.client.gui.element.progress.ProgressType;
import mezz.jei.api.recipe.RecipeIngredientRole;
import net.minecraft.resources.ResourceLocation;
import mekanism.common.inventory.container.slot.SlotOverlay;
import mekanism.client.gui.element.slot.SlotType;

import fixdol.mekanismelements.api.recipes.InfinityOreReprocessingRecipe;

public class InfinityOreReprocessingRecipeCategory extends BaseRecipeCategory<InfinityOreReprocessingRecipe> {

    private final GuiSlot itemInput;
    private final GuiGauge<?> chemicalInput;
    private final GuiSlot output;

    public InfinityOreReprocessingRecipeCategory(IGuiHelper helper, MekanismJEIRecipeType<InfinityOreReprocessingRecipe> recipeType) {
        super(helper, recipeType, fixdol.mekanismelements.common.registries.MSBlocks.INFINITY_ORE_REPROCESSING, 3, 3, 170, 79);
        chemicalInput = addElement(GuiGasGauge.getDummy(GaugeType.STANDARD.with(DataType.INPUT), this, 42, 13));
        itemInput = addSlot(SlotType.INPUT, 21, 17).with(SlotOverlay.MINUS);
        addConstantProgress(ProgressType.LARGE_RIGHT, 64, 40);
        output = addSlot(SlotType.OUTPUT, 116, 36);
        addElement(new GuiVerticalPowerBar(this, () -> 1.0, 164, 15));
        addSlot(SlotType.POWER, 144, 35).with(SlotOverlay.POWER);
    }

    @Override
    public void setRecipe(@NotNull IRecipeLayoutBuilder builder, InfinityOreReprocessingRecipe recipe, @NotNull IFocusGroup focusGroup) {
        initItem(builder, RecipeIngredientRole.INPUT, itemInput, recipe.getItemInput().getRepresentations());
        initChemical(builder, mekanism.client.jei.MekanismJEI.TYPE_GAS, RecipeIngredientRole.INPUT, chemicalInput, recipe.getChemicalInput().getRepresentations());
        initItem(builder, RecipeIngredientRole.OUTPUT, output, recipe.getOutputDefinition());
    }


    @Override
    public ResourceLocation getRegistryName(InfinityOreReprocessingRecipe recipe) {
        return recipe.getId();
    }
}