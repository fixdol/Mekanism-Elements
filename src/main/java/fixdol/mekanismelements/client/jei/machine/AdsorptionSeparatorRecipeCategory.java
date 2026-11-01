package fixdol.mekanismelements.client.jei.machine;

import com.mojang.serialization.Codec;
import fixdol.mekanismelements.api.recipes.AdsorptionRecipe;
import fixdol.mekanismelements.common.registries.MSRecipeSerializers;
import fixdol.mekanismelements.common.tile.machine.TileEntityAdsorptionSeparator;
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
import net.neoforged.neoforge.fluids.FluidStack;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.Nullable;

import java.util.List;

public class AdsorptionSeparatorRecipeCategory extends BaseRecipeCategory<AdsorptionRecipe> {
    private final GuiGauge<?> inputGauge;
    private final GuiGauge<?> outputGauge;
    private final GuiSlot inputSlot;

    public AdsorptionSeparatorRecipeCategory(IGuiHelper helper, IRecipeViewerRecipeType<AdsorptionRecipe> recipeType) {
        super(helper, recipeType);
        inputGauge = addElement(GuiChemicalGauge.getDummy(GaugeType.MEDIUM.with(DataType.INPUT), this, 17, 13));
        outputGauge = addElement(GuiChemicalGauge.getDummy(GaugeType.STANDARD.with(DataType.OUTPUT), this, 131, 13));
        inputSlot = addSlot(SlotType.INPUT, 80, 22);
        addSlot(SlotType.OUTPUT, 152, 55).with(SlotOverlay.PLUS);
        addSlot(SlotType.POWER, 152, 14).with(SlotOverlay.POWER);
        addSimpleProgress(ProgressType.LARGE_RIGHT, 64, 40);
        addElement(new GuiHorizontalPowerBar(this, RecipeViewerUtils.FULL_BAR, 115, 75));
    }

    @Override
    public void setRecipe(@NotNull IRecipeLayoutBuilder builder, AdsorptionRecipe recipe, @NotNull IFocusGroup focusGroup) {
        ContextMap slotDisplayContext = getSlotDisplayContext();
        initItem(builder, RecipeIngredientRole.INPUT, inputSlot, recipe.getItemInput().getRepresentations(slotDisplayContext));
        List<FluidStack> scaledFluids = recipe.getFluidInput().getRepresentations(slotDisplayContext).stream()
                .map(fluid -> fluid.copyWithAmount(fluid.amount() * TileEntityAdsorptionSeparator.BASE_TICKS_REQUIRED))
                .toList();
        initFluid(builder, RecipeIngredientRole.INPUT, inputGauge, scaledFluids);
        initChemical(builder, outputGauge, recipe.getOutputDefinition());
    }

    @Override
    public Codec<AdsorptionRecipe> getCodec(ICodecHelper codecHelper, IRecipeManager recipeManager) {
        return MSRecipeSerializers.ADSORPTION_SEPARATOR.get().codec().codec();
    }

    @Override
    public @Nullable Identifier getIdentifier(AdsorptionRecipe recipe) {
        return recipe.getId();
    }
}
