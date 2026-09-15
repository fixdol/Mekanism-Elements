package fixdol.mekanismelements.client.jei.machine;

import mekanism.client.jei.BaseRecipeCategory;
import java.util.Collections;
import net.minecraft.network.chat.Component;
import mekanism.common.tile.component.config.DataType;
import net.minecraftforge.fluids.FluidStack;
import mekanism.client.gui.element.gauge.GaugeType;
import mekanism.client.gui.element.gauge.GuiFluidGauge;
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
import fixdol.mekanismelements.client.jei.machine.SeawaterPumpInfoRecipe;
import fixdol.mekanismelements.client.jei.machine.SeawaterPumpRecipeCategory;

import fixdol.mekanismelements.common.registries.MSFluids;




public class SeawaterPumpRecipeCategory extends BaseRecipeCategory<SeawaterPumpInfoRecipe> {

    private static final FluidStack OUTPUT_STACK = new FluidStack(MSFluids.SEAWATER.getFluid(), 200);

    private static final Component DESCRIPTION = Component.translatable("description.mekanismelements.seawater_pump");

    private final GuiGauge<?> outputGauge;

    public SeawaterPumpRecipeCategory(IGuiHelper helper, MekanismJEIRecipeType<SeawaterPumpInfoRecipe> recipeType) {
        super(helper, recipeType, fixdol.mekanismelements.common.registries.MSBlocks.SEAWATER_PUMP, 3, 3, 170, 79);
        addElement(new GuiInnerScreen(this, 5, 5, 122, 70, () -> fixdol.mekanismelements.client.jei.MSScreenText.wrap(DESCRIPTION, 112)).clearFormat());
        outputGauge = addElement(GuiFluidGauge.getDummy(GaugeType.STANDARD.with(DataType.OUTPUT), this, 131, 5));
    }

    @Override
    public void setRecipe(@NotNull IRecipeLayoutBuilder builder, SeawaterPumpInfoRecipe recipe, @NotNull IFocusGroup focusGroup) {
        initFluid(builder, RecipeIngredientRole.OUTPUT, outputGauge, Collections.singletonList(OUTPUT_STACK));
    }


    @Override
    public ResourceLocation getRegistryName(SeawaterPumpInfoRecipe recipe) {
        return null;
    }
}