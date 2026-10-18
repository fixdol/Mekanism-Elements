package fixdol.mekanismelements.client.emi;

import fixdol.mekanismelements.client.MSRecipeViewerText;
import dev.emi.emi.api.widget.WidgetHolder;
import fixdol.mekanismelements.client.jei.machine.AirCompressorInfoRecipe;
import fixdol.mekanismelements.common.registries.MSGases;
import mekanism.api.chemical.Chemical;
import mekanism.api.chemical.ChemicalStack;
import mekanism.client.gui.element.GuiInnerScreen;
import mekanism.client.gui.element.gauge.GaugeType;
import mekanism.client.gui.element.gauge.GuiChemicalGauge;
import mekanism.client.recipe_viewer.emi.MekanismEmiRecipeCategory;
import mekanism.client.recipe_viewer.emi.recipe.MekanismEmiRecipe;
import mekanism.common.tile.component.config.DataType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.List;

public class AirCompressorEmiRecipe extends MekanismEmiRecipe<AirCompressorInfoRecipe> {
    private static final ChemicalStack OUTPUT_STACK = new ChemicalStack((Chemical) MSGases.COMPRESSED_AIR.get(), 200);

    private static final List<Component> DESCRIPTION = List.of(
            Component.translatable("description.mekanismelements.air_compressor")
    );

    public AirCompressorEmiRecipe(MekanismEmiRecipeCategory category, ResourceLocation id, AirCompressorInfoRecipe recipe) {
        // The layout coordinates are those of the full GUI texture, so shift them by the negated offsets of the type
        // (checked in game: with the positive ones the Infinity Ore Reprocessor overflowed its frame)
        super(category, id, recipe, -category.xOffset(), -category.yOffset(), category.width(), category.height());
        addChemicalOutputDefinition(List.of(OUTPUT_STACK));
    }

    @Override
    public void addWidgets(WidgetHolder widgets) {
        addElement(widgets, new GuiInnerScreen(this, 5, 5, 122, 70, () -> MSRecipeViewerText.wrapForInnerScreen(DESCRIPTION.get(0))).clearFormat());
        initTank(widgets, GuiChemicalGauge.getDummy(GaugeType.STANDARD.with(DataType.OUTPUT), this, 131, 5), output(0)).recipeContext(this);
    }
}
