package fixdol.mekanismelements.client.jei.machine;

import mekanism.api.chemical.ChemicalStack;
import mekanism.api.chemical.ChemicalType;
import mekanism.api.chemical.merged.BoxedChemicalStack;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Map;

import fixdol.mekanismelements.api.recipes.AdsorptionRecipe;
import fixdol.mekanismelements.client.jei.machine.AdsorptionSeparatorRecipeCategory;
import java.util.Collections;
import net.minecraftforge.fluids.FluidStack;
import mekanism.api.chemical.gas.GasStack;
import mekanism.client.gui.element.gauge.GaugeType;
import mekanism.client.gui.element.gauge.GuiGasGauge;
import mekanism.client.gui.element.bar.GuiHorizontalPowerBar;
import mekanism.client.gui.element.slot.GuiSlot;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import mekanism.client.gui.element.progress.ProgressType;
import mekanism.client.gui.element.slot.SlotType;
import fixdol.mekanismelements.common.tile.machine.TileEntityAdsorptionSeparator;

import fixdol.mekanismelements.common.registries.MSBlocks;
import mekanism.api.chemical.Chemical;
import mekanism.common.registries.MekanismGases;
import mekanism.client.gui.element.gauge.GuiGauge;
import mekanism.client.jei.BaseRecipeCategory;
import mekanism.client.jei.MekanismJEI;
import mekanism.client.jei.MekanismJEIRecipeType;
import mekanism.common.inventory.container.slot.SlotOverlay;
import mekanism.common.tile.component.config.DataType;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredientType;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;

import java.util.*;

public class AdsorptionSeparatorRecipeCategory extends BaseRecipeCategory<AdsorptionRecipe> {
    private final GuiGauge<?> inputGauge;
    private final GuiGauge<?> outputGauge;
    private final GuiSlot inputSlot;

    public AdsorptionSeparatorRecipeCategory(IGuiHelper helper, MekanismJEIRecipeType<AdsorptionRecipe> recipeType) {
        super(helper, recipeType, fixdol.mekanismelements.common.registries.MSBlocks.ADSORPTION_SEPARATOR, 3, 3, 170, 79);
        inputGauge = addElement(GuiGasGauge.getDummy(GaugeType.MEDIUM.with(DataType.INPUT), this, 17, 13));
        outputGauge = addElement(GuiGasGauge.getDummy(GaugeType.STANDARD.with(DataType.OUTPUT), this, 131, 13));
        inputSlot = addSlot(SlotType.INPUT, 80, 22);
        addSlot(SlotType.OUTPUT, 152, 55).with(SlotOverlay.PLUS);
        addSlot(SlotType.POWER, 152, 14).with(SlotOverlay.POWER);
        addSimpleProgress(ProgressType.LARGE_RIGHT, 64, 40);
        addElement(new GuiHorizontalPowerBar(this, () -> 1.0, 115, 75));
    }

    @Override
    public void setRecipe(@NotNull IRecipeLayoutBuilder builder, AdsorptionRecipe recipe, @NotNull IFocusGroup focusGroup) {
        initItem(builder, RecipeIngredientRole.INPUT, inputSlot, recipe.getItemInput().getRepresentations());
        List<@NotNull FluidStack> fluidInputs = recipe.getFluidInput().getRepresentations();
        List<FluidStack> scaledFluids = fluidInputs.stream().map(fluid -> new FluidStack(fluid.getFluid(), fluid.getAmount() * TileEntityAdsorptionSeparator.BASE_TICKS_REQUIRED))
                .toList();
        initFluid(builder, RecipeIngredientRole.INPUT, inputGauge, scaledFluids);
        List<BoxedChemicalStack> outputDefinition = recipe.getOutputDefinition();
        Map<ChemicalType, List<ChemicalStack<?>>> byType = new EnumMap<>(ChemicalType.class);
        for (BoxedChemicalStack boxed : outputDefinition) {
            byType.computeIfAbsent(boxed.getChemicalType(), type -> new ArrayList<>()).add(boxed.getChemicalStack());
        }
        for (Map.Entry<ChemicalType, List<ChemicalStack<?>>> entry : byType.entrySet()) {
            initChemicalOutput(builder, entry.getKey(), entry.getValue());
        }
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private void initChemicalOutput(IRecipeLayoutBuilder builder, ChemicalType chemicalType, List<ChemicalStack<?>> stacks) {
        IIngredientType type = switch (chemicalType) {
            case INFUSION -> MekanismJEI.TYPE_INFUSION;
            case PIGMENT -> MekanismJEI.TYPE_PIGMENT;
            case SLURRY -> MekanismJEI.TYPE_SLURRY;
            default -> MekanismJEI.TYPE_GAS;
        };
        initChemical(builder, type, RecipeIngredientRole.OUTPUT, outputGauge, (List) stacks);
    }
}
