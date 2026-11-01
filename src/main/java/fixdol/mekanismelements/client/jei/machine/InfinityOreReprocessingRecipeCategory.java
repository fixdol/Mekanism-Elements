package fixdol.mekanismelements.client.jei.machine;

import com.mojang.serialization.Codec;
import fixdol.mekanismelements.api.recipes.InfinityOreReprocessingRecipe;
import fixdol.mekanismelements.common.registries.MSRecipeSerializers;
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

public class InfinityOreReprocessingRecipeCategory extends BaseRecipeCategory<InfinityOreReprocessingRecipe> {

    private final GuiSlot itemInput;
    private final GuiGauge<?> chemicalInput;
    private final GuiSlot output;

    public InfinityOreReprocessingRecipeCategory(IGuiHelper helper, IRecipeViewerRecipeType<InfinityOreReprocessingRecipe> recipeType) {
        super(helper, recipeType);
        chemicalInput = addElement(GuiChemicalGauge.getDummy(GaugeType.STANDARD.with(DataType.INPUT), this, 42, 13));
        itemInput = addSlot(SlotType.INPUT, 21, 17).with(SlotOverlay.MINUS);
        addConstantProgress(ProgressType.LARGE_RIGHT, 64, 40);
        output = addSlot(SlotType.OUTPUT, 116, 36);
        addElement(new GuiVerticalPowerBar(this, RecipeViewerUtils.FULL_BAR, 164, 15));
        addSlot(SlotType.POWER, 144, 35).with(SlotOverlay.POWER);
    }

    @Override
    public void setRecipe(@NotNull IRecipeLayoutBuilder builder, InfinityOreReprocessingRecipe recipe, @NotNull IFocusGroup focusGroup) {
        ContextMap slotDisplayContext = getSlotDisplayContext();
        initItem(builder, RecipeIngredientRole.INPUT, itemInput, recipe.getItemInput().getRepresentations(slotDisplayContext));
        initChemical(builder, RecipeIngredientRole.INPUT, chemicalInput, recipe.getChemicalInput().getRepresentations(slotDisplayContext));
        initItem(builder, output, recipe.getOutputDefinition());
    }

    @Override
    public Codec<InfinityOreReprocessingRecipe> getCodec(ICodecHelper codecHelper, IRecipeManager recipeManager) {
        return MSRecipeSerializers.INFINITY_ORE_REPROCESSING.get().codec().codec();
    }

    @Override
    public @Nullable Identifier getIdentifier(InfinityOreReprocessingRecipe recipe) {
        return recipe.getId();
    }
}
