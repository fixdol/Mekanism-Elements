package fixdol.mekanismelements.client.jei.machine;

import com.mojang.serialization.Codec;
import fixdol.mekanismelements.client.MSRecipeViewerText;
import fixdol.mekanismelements.common.tile.machine.TileEntitySeawaterPump;
import mekanism.client.gui.element.GuiInnerScreen;
import mekanism.client.gui.element.gauge.GaugeType;
import mekanism.client.gui.element.gauge.GuiFluidGauge;
import mekanism.client.gui.element.gauge.GuiGauge;
import mekanism.client.recipe_viewer.jei.BaseRecipeCategory;
import mekanism.client.recipe_viewer.type.IRecipeViewerRecipeType;
import mekanism.common.tile.component.config.DataType;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.helpers.ICodecHelper;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.IRecipeManager;
import mezz.jei.api.recipe.RecipeIngredientRole;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.Nullable;

import java.util.List;

public class SeawaterPumpRecipeCategory extends BaseRecipeCategory<SeawaterPumpInfoRecipe> {

    private static final Component DESCRIPTION = Component.translatable("description.mekanismelements.seawater_pump");

    private final GuiGauge<?> outputGauge;

    public SeawaterPumpRecipeCategory(IGuiHelper helper, IRecipeViewerRecipeType<SeawaterPumpInfoRecipe> recipeType) {
        super(helper, recipeType);
        addElement(new GuiInnerScreen(this, 5, 5, 122, 70, () -> MSRecipeViewerText.wrapForInnerScreen(DESCRIPTION)).clearFormat());
        outputGauge = addElement(GuiFluidGauge.getDummy(GaugeType.STANDARD.with(DataType.OUTPUT), this, 131, 5));
    }

    @Override
    public void setRecipe(@NotNull IRecipeLayoutBuilder builder, SeawaterPumpInfoRecipe recipe, @NotNull IFocusGroup focusGroup) {
        initFluid(builder, RecipeIngredientRole.OUTPUT, outputGauge, List.of(TileEntitySeawaterPump.SEAWATER_STACK));
    }

    @Override
    public @Nullable Codec<SeawaterPumpInfoRecipe> getCodec(ICodecHelper codecHelper, IRecipeManager recipeManager) {
        return null;
    }

    @Override
    public @Nullable Identifier getIdentifier(SeawaterPumpInfoRecipe recipe) {
        return null;
    }
}
