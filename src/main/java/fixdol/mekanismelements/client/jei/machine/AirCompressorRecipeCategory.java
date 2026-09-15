package fixdol.mekanismelements.client.jei.machine;

import fixdol.mekanismelements.client.jei.machine.AirCompressorInfoRecipe;
import fixdol.mekanismelements.client.jei.machine.AirCompressorRecipeCategory;
import mekanism.client.jei.BaseRecipeCategory;
import java.util.Collections;
import net.minecraft.network.chat.Component;
import mekanism.common.tile.component.config.DataType;
import mekanism.api.chemical.gas.GasStack;
import mekanism.client.gui.element.gauge.GaugeType;
import mekanism.client.gui.element.gauge.GuiGasGauge;
import mekanism.client.gui.element.gauge.GuiGauge;
import mekanism.client.gui.element.GuiInnerScreen;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import java.util.List;
import mekanism.client.jei.MekanismJEIRecipeType;
import org.jetbrains.annotations.NotNull;
import mezz.jei.api.recipe.RecipeIngredientRole;
import net.minecraft.resources.ResourceLocation;
import mekanism.api.chemical.gas.Gas;

import fixdol.mekanismelements.common.registries.MSGases;



public class AirCompressorRecipeCategory extends BaseRecipeCategory<AirCompressorInfoRecipe> {

    private static final GasStack OUTPUT_STACK =
            new GasStack((Gas) MSGases.COMPRESSED_AIR.get(), 200);

    private static final Component DESCRIPTION = Component.translatable("description.mekanismelements.air_compressor");

    private final GuiGauge<?> outputGauge;

    public AirCompressorRecipeCategory(IGuiHelper helper, MekanismJEIRecipeType<AirCompressorInfoRecipe> recipeType) {
        super(helper, recipeType, fixdol.mekanismelements.common.registries.MSBlocks.AIR_COMPRESSOR, 3, 3, 170, 79);
        addElement(new GuiInnerScreen(this, 5, 5, 122, 70, () -> fixdol.mekanismelements.client.jei.MSScreenText.wrap(DESCRIPTION, 112)).clearFormat());
        outputGauge = addElement(GuiGasGauge.getDummy(GaugeType.STANDARD.with(DataType.OUTPUT), this, 131, 5));
    }

    @Override
    public void setRecipe(@NotNull IRecipeLayoutBuilder builder, AirCompressorInfoRecipe recipe, @NotNull IFocusGroup focusGroup) {
        initChemical(builder, mekanism.client.jei.MekanismJEI.TYPE_GAS, RecipeIngredientRole.OUTPUT, outputGauge, Collections.singletonList(OUTPUT_STACK));
    }


    @Override
    public ResourceLocation getRegistryName(AirCompressorInfoRecipe recipe) {
        return null;
    }
}