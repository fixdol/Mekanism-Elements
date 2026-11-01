package fixdol.mekanismelements.client.jei.machine;

import com.mojang.serialization.Codec;
import fixdol.mekanismelements.api.recipes.RadiationIrradiatingRecipe;
import fixdol.mekanismelements.common.registries.MSRecipeSerializers;
import fixdol.mekanismelements.common.tile.machine.TileEntityRadiationIrradiator;
import mekanism.api.chemical.ChemicalStack;
import mekanism.client.gui.element.bar.GuiHorizontalPowerBar;
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

public class RadiationIrradiatorRecipeCategory extends BaseRecipeCategory<RadiationIrradiatingRecipe> {
    private final GuiGauge<?> inputGauge;
    private final GuiGauge<?> outputGauge;
    private final GuiSlot inputSlot;

    public RadiationIrradiatorRecipeCategory(IGuiHelper helper, IRecipeViewerRecipeType<RadiationIrradiatingRecipe> recipeType) {
        super(helper, recipeType);
        inputGauge = addElement(GuiChemicalGauge.getDummy(GaugeType.STANDARD.with(DataType.INPUT), this, 28, 13));
        outputGauge = addElement(GuiChemicalGauge.getDummy(GaugeType.STANDARD.with(DataType.OUTPUT), this, 131, 13));
        inputSlot = addSlot(SlotType.INPUT, 7, 36);
        addSlot(SlotType.EXTRA, 7, 55).with(SlotOverlay.MINUS);
        addSlot(SlotType.OUTPUT, 152, 55).with(SlotOverlay.PLUS);
        addSlot(SlotType.POWER, 152, 14).with(SlotOverlay.POWER);
        addSimpleProgress(ProgressType.LARGE_RIGHT, 64, 40);
        addElement(new GuiHorizontalPowerBar(this, RecipeViewerUtils.FULL_BAR, 115, 75));
    }

    @Override
    public void setRecipe(@NotNull IRecipeLayoutBuilder builder, RadiationIrradiatingRecipe recipe, @NotNull IFocusGroup focusGroup) {
        ContextMap slotDisplayContext = getSlotDisplayContext();
        initItem(builder, RecipeIngredientRole.INPUT, inputSlot, recipe.getItemInput().getRepresentations(slotDisplayContext));
        List<ChemicalStack> scaledChemicals = recipe.getGasInput().getRepresentations(slotDisplayContext).stream()
                .map(chemical -> chemical.copyWithAmount(chemical.amount() * TileEntityRadiationIrradiator.BASE_TICKS_REQUIRED))
                .toList();
        initChemical(builder, RecipeIngredientRole.INPUT, inputGauge, scaledChemicals);
        initChemical(builder, outputGauge, recipe.getOutputDefinition());
    }

    @Override
    public Codec<RadiationIrradiatingRecipe> getCodec(ICodecHelper codecHelper, IRecipeManager recipeManager) {
        return MSRecipeSerializers.RADIATION_IRRADIATOR.get().codec().codec();
    }

    @Override
    public @Nullable Identifier getIdentifier(RadiationIrradiatingRecipe recipe) {
        return recipe.getId();
    }
}
