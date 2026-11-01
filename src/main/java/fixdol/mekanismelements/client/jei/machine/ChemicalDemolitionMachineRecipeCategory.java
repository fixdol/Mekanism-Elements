package fixdol.mekanismelements.client.jei.machine;

import com.mojang.serialization.Codec;
import fixdol.mekanismelements.api.recipes.ChemicalDemolitionRecipe;
import fixdol.mekanismelements.common.registries.MSRecipeSerializers;
import fixdol.mekanismelements.common.tile.machine.TileEntityChemicalDemolitionMachine;
import mekanism.api.chemical.ChemicalStack;
import mekanism.client.gui.element.bar.GuiVerticalPowerBar;
import mekanism.client.gui.element.gauge.GaugeType;
import mekanism.client.gui.element.gauge.GuiChemicalGauge;
import mekanism.client.gui.element.gauge.GuiGauge;
import mekanism.client.gui.element.progress.ProgressType;
import mekanism.client.gui.element.slot.GuiSlot;
import mekanism.client.gui.element.slot.SlotType;
import mekanism.client.recipe_viewer.RecipeViewerUtils;
import mekanism.client.recipe_viewer.jei.BaseRecipeCategory;
import mekanism.client.recipe_viewer.type.IRecipeViewerRecipeType;
import mekanism.common.inventory.container.slot.SlotOverlay;
import mekanism.common.tile.component.config.DataType;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.helpers.ICodecHelper;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.IRecipeManager;
import mezz.jei.api.recipe.RecipeIngredientRole;
import net.minecraft.resources.Identifier;
import net.minecraft.util.context.ContextMap;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.Nullable;

import java.util.List;

public class ChemicalDemolitionMachineRecipeCategory extends BaseRecipeCategory<ChemicalDemolitionRecipe> {
    private final GuiGauge<?> inputGauge;
    private final GuiSlot outputSlot;
    private final GuiSlot inputSlot;

    public ChemicalDemolitionMachineRecipeCategory(IGuiHelper helper, IRecipeViewerRecipeType<ChemicalDemolitionRecipe> recipeType) {
        super(helper, recipeType);
        inputGauge = addElement(GuiChemicalGauge.getDummy(GaugeType.STANDARD.with(DataType.INPUT), this, 7, 4));
        outputSlot = addSlot(SlotType.OUTPUT_WIDE, 112, 31);
        inputSlot = addSlot(SlotType.INPUT, 28, 36);
        addSlot(SlotType.EXTRA, 8, 65).with(SlotOverlay.MINUS);
        addSlot(SlotType.POWER, 154, 62).with(SlotOverlay.POWER);
        addSimpleProgress(ProgressType.LARGE_RIGHT, 54, 40);
        addElement(new GuiVerticalPowerBar(this, RecipeViewerUtils.FULL_BAR, 164, 5));
    }

    @Override
    public void setRecipe(@NotNull IRecipeLayoutBuilder builder, ChemicalDemolitionRecipe recipe, @NotNull IFocusGroup focusGroup) {
        ContextMap slotDisplayContext = getSlotDisplayContext();
        initItem(builder, RecipeIngredientRole.INPUT, inputSlot, recipe.getItemInput().getRepresentations(slotDisplayContext));
        List<ChemicalStack> scaledChemicals = recipe.getGasInput().getRepresentations(slotDisplayContext).stream()
                .map(chemical -> chemical.copyWithAmount(chemical.amount() * TileEntityChemicalDemolitionMachine.BASE_TICKS_REQUIRED))
                .toList();
        initChemical(builder, RecipeIngredientRole.INPUT, inputGauge, scaledChemicals);
        initItem(builder, outputSlot.getRelativeX() + 4, outputSlot.getRelativeY() + 4, recipe.getFirstOutputDefinition());
        initItem(builder, outputSlot.getRelativeX() + 20, outputSlot.getRelativeY() + 4, recipe.getSecondOutputDefinition());
    }

    @Override
    public Codec<ChemicalDemolitionRecipe> getCodec(ICodecHelper codecHelper, IRecipeManager recipeManager) {
        return MSRecipeSerializers.CHEMICAL_DEMOLITION.get().codec().codec();
    }

    @Override
    public @Nullable Identifier getIdentifier(ChemicalDemolitionRecipe recipe) {
        return recipe.getId();
    }
}
