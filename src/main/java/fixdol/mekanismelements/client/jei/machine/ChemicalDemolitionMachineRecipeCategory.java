package fixdol.mekanismelements.client.jei.machine;

import mekanism.client.jei.BaseRecipeCategory;
import fixdol.mekanismelements.client.jei.machine.ChemicalDemolitionMachineRecipeCategory;
import fixdol.mekanismelements.api.recipes.ChemicalDemolitionRecipe;
import mekanism.common.tile.component.config.DataType;
import mekanism.api.chemical.gas.GasStack;
import mekanism.client.gui.element.gauge.GaugeType;
import mekanism.client.gui.element.gauge.GuiGasGauge;
import mekanism.client.gui.element.gauge.GuiGauge;
import mekanism.client.gui.element.slot.GuiSlot;
import mekanism.client.gui.element.bar.GuiVerticalPowerBar;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import java.util.List;
import mekanism.client.jei.MekanismJEIRecipeType;
import org.jetbrains.annotations.NotNull;
import mekanism.client.gui.element.progress.ProgressType;
import mezz.jei.api.recipe.RecipeIngredientRole;
import net.minecraft.resources.ResourceLocation;
import mekanism.common.inventory.container.slot.SlotOverlay;
import mekanism.client.gui.element.slot.SlotType;
import fixdol.mekanismelements.common.tile.machine.TileEntityChemicalDemolitionMachine;

import fixdol.mekanismelements.common.registries.MSRecipeSerializers;


public class ChemicalDemolitionMachineRecipeCategory extends BaseRecipeCategory<ChemicalDemolitionRecipe> {
    private final GuiGauge<?> inputGauge;
    private final GuiSlot outputSlot;
    private final GuiSlot inputSlot;

    public ChemicalDemolitionMachineRecipeCategory(IGuiHelper helper, MekanismJEIRecipeType<ChemicalDemolitionRecipe> recipeType) {
        super(helper, recipeType, fixdol.mekanismelements.common.registries.MSBlocks.CHEMICAL_DEMOLITION_MACHINE, 3, 3, 170, 79);
        inputGauge = addElement(GuiGasGauge.getDummy(GaugeType.STANDARD.with(DataType.INPUT), this, 7, 4));
        outputSlot = addSlot(SlotType.OUTPUT_WIDE, 112, 31);
        inputSlot = addSlot(SlotType.INPUT, 28, 36);
        addSlot(SlotType.EXTRA, 8, 65).with(SlotOverlay.MINUS);
        addSlot(SlotType.POWER, 154, 62).with(SlotOverlay.POWER);
        addSimpleProgress(ProgressType.LARGE_RIGHT, 54, 40);
        addElement(new GuiVerticalPowerBar(this, () -> 1.0, 164, 5));
    }

    @Override
    public void setRecipe(@NotNull IRecipeLayoutBuilder builder, ChemicalDemolitionRecipe recipe, @NotNull IFocusGroup focusGroup) {
        initItem(builder, RecipeIngredientRole.INPUT, inputSlot, recipe.getItemInput().getRepresentations());
        List<@NotNull GasStack> chemicalInputs = recipe.getGasInput().getRepresentations();
        List<GasStack> scaledChemicals = chemicalInputs.stream().map(chemical -> mekanism.common.util.ChemicalUtil.copyWithAmount(chemical, chemical.getAmount() * TileEntityChemicalDemolitionMachine.BASE_TICKS_REQUIRED))
                .toList();
        initChemical(builder, mekanism.client.jei.MekanismJEI.TYPE_GAS, RecipeIngredientRole.INPUT, inputGauge, scaledChemicals);
        initItem(builder, RecipeIngredientRole.OUTPUT, outputSlot.getRelativeX() + 4, outputSlot.getRelativeY() + 4, recipe.getFirstOutputDefinition());
        initItem(builder, RecipeIngredientRole.OUTPUT, outputSlot.getRelativeX() + 20, outputSlot.getRelativeY() + 4, recipe.getSecondOutputDefinition());
    }

    @Override
    public ResourceLocation getRegistryName(ChemicalDemolitionRecipe recipe) {
        return recipe.getId();
    }
}