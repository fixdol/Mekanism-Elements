package fixdol.mekanismelements.client.emi;

import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import fixdol.mekanismelements.api.recipes.AdsorptionRecipe;
import fixdol.mekanismelements.api.recipes.ChemicalDemolitionRecipe;
import fixdol.mekanismelements.api.recipes.InfinityOreReprocessingRecipe;
import fixdol.mekanismelements.api.recipes.RadiationIrradiatingRecipe;
import fixdol.mekanismelements.common.MekanismElements;
import fixdol.mekanismelements.common.registries.MSFluids;
import fixdol.mekanismelements.common.registries.MSGases;
import fixdol.mekanismelements.common.tile.machine.TileEntityAdsorptionSeparator;
import fixdol.mekanismelements.common.tile.machine.TileEntityChemicalDemolitionMachine;
import fixdol.mekanismelements.common.tile.machine.TileEntityRadiationIrradiator;
import mekanism.api.chemical.ChemicalStack;
import mekanism.api.chemical.ChemicalType;
import mekanism.api.chemical.gas.Gas;
import mekanism.api.chemical.gas.GasStack;
import mekanism.api.recipes.ingredients.ChemicalStackIngredient;
import mekanism.client.gui.element.gauge.GaugeType;
import mekanism.client.gui.element.progress.ProgressType;
import mekanism.client.gui.element.slot.GuiSlot;
import mekanism.client.gui.element.slot.SlotType;
import mekanism.common.inventory.container.slot.SlotOverlay;
import mekanism.common.tile.component.config.DataType;
import mekanism.common.util.ChemicalUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.List;

/**
 * The layout of each machine, with the same coordinates as its JEI category.
 */
final class MSEmiLayouts {
    private static final GasStack COMPRESSED_AIR = new GasStack((Gas) MSGases.COMPRESSED_AIR.get(), 200);
    private static final FluidStack SEAWATER = new FluidStack(MSFluids.SEAWATER.getFluid(), 200);

    private MSEmiLayouts() {
    }

    static MSEmiRecipe adsorption(AdsorptionRecipe recipe) {
        EmiIngredient item = items(recipe.getItemInput().getRepresentations());
        // The machine consumes the fluid for the whole operation, so show the scaled amount like the JEI category does
        List<FluidStack> fluids = recipe.getFluidInput().getRepresentations();
        FluidStack fluid = fluids.isEmpty() ? FluidStack.EMPTY
                : new FluidStack(fluids.get(0).getFluid(), fluids.get(0).getAmount() * TileEntityAdsorptionSeparator.BASE_TICKS_REQUIRED);
        var outputs = recipe.getOutputDefinition();
        ChemicalType outputType = outputs.isEmpty() ? ChemicalType.GAS : outputs.get(0).getChemicalType();
        ChemicalStack<?> output = outputs.isEmpty() ? GasStack.EMPTY : outputs.get(0).getChemicalStack();
        return new MSEmiRecipe(MSEmiMachine.ADSORPTION_SEPARATOR, recipe.getId(), List.of(item, fluidIngredient(fluids, fluid.getAmount())), List.of(), b -> {
            b.fluidGauge(GaugeType.MEDIUM, DataType.INPUT, 17, 13, fluid);
            b.chemicalGauge(GaugeType.STANDARD, DataType.OUTPUT, 131, 13, outputType, output);
            GuiSlot input = b.slot(SlotType.INPUT, 80, 22);
            b.entries(input, item, false);
            b.slot(SlotType.OUTPUT, 152, 55).with(SlotOverlay.PLUS);
            b.slot(SlotType.POWER, 152, 14).with(SlotOverlay.POWER);
            b.progress(ProgressType.LARGE_RIGHT, 64, 40, TileEntityAdsorptionSeparator.BASE_TICKS_REQUIRED);
            b.horizontalPowerBar(115, 75);
        });
    }

    static MSEmiRecipe chemicalDemolition(ChemicalDemolitionRecipe recipe) {
        EmiIngredient item = items(recipe.getItemInput().getRepresentations());
        GasStack input = scaled(firstGas(recipe.getGasInput()), TileEntityChemicalDemolitionMachine.BASE_TICKS_REQUIRED);
        EmiIngredient first = items(recipe.getFirstOutputDefinition());
        EmiIngredient second = items(recipe.getSecondOutputDefinition());
        List<EmiStack> outputs = new ArrayList<>(stacks(recipe.getFirstOutputDefinition()));
        outputs.addAll(stacks(recipe.getSecondOutputDefinition()));
        return new MSEmiRecipe(MSEmiMachine.CHEMICAL_DEMOLITION_MACHINE, recipe.getId(), List.of(item), outputs, b -> {
            b.gasGauge(GaugeType.STANDARD, DataType.INPUT, 7, 4, input);
            GuiSlot output = b.slot(SlotType.OUTPUT_WIDE, 112, 31);
            b.entriesAtItem(output.getRelativeX() + 4, output.getRelativeY() + 4, first, true);
            b.entriesAtItem(output.getRelativeX() + 20, output.getRelativeY() + 4, second, true);
            GuiSlot inputSlot = b.slot(SlotType.INPUT, 28, 36);
            b.entries(inputSlot, item, false);
            b.slot(SlotType.EXTRA, 8, 65).with(SlotOverlay.MINUS);
            b.slot(SlotType.POWER, 154, 62).with(SlotOverlay.POWER);
            b.progress(ProgressType.LARGE_RIGHT, 54, 40, TileEntityChemicalDemolitionMachine.BASE_TICKS_REQUIRED);
            b.verticalPowerBar(164, 5);
        });
    }

    static MSEmiRecipe radiationIrradiating(RadiationIrradiatingRecipe recipe) {
        EmiIngredient item = items(recipe.getItemInput().getRepresentations());
        GasStack input = scaled(firstGas(recipe.getGasInput()), TileEntityRadiationIrradiator.BASE_TICKS_REQUIRED);
        List<GasStack> outputs = recipe.getOutputDefinition();
        GasStack output = outputs.isEmpty() ? GasStack.EMPTY : outputs.get(0);
        return new MSEmiRecipe(MSEmiMachine.RADIATION_IRRADIATOR, recipe.getId(), List.of(item), List.of(), b -> {
            b.gasGauge(GaugeType.STANDARD, DataType.INPUT, 28, 13, input);
            b.gasGauge(GaugeType.STANDARD, DataType.OUTPUT, 131, 13, output);
            GuiSlot inputSlot = b.slot(SlotType.INPUT, 7, 36);
            b.entries(inputSlot, item, false);
            b.slot(SlotType.EXTRA, 7, 55).with(SlotOverlay.MINUS);
            b.slot(SlotType.OUTPUT, 152, 55).with(SlotOverlay.PLUS);
            b.slot(SlotType.POWER, 152, 14).with(SlotOverlay.POWER);
            b.progress(ProgressType.LARGE_RIGHT, 64, 40, TileEntityRadiationIrradiator.BASE_TICKS_REQUIRED);
            b.horizontalPowerBar(115, 75);
        });
    }

    static MSEmiRecipe infinityOreReprocessing(InfinityOreReprocessingRecipe recipe) {
        EmiIngredient item = items(recipe.getItemInput().getRepresentations());
        GasStack chemical = firstGas(recipe.getChemicalInput());
        EmiIngredient output = items(recipe.getOutputDefinition());
        return new MSEmiRecipe(MSEmiMachine.INFINITY_ORE_REPROCESSING, recipe.getId(), List.of(item), stacks(recipe.getOutputDefinition()), b -> {
            b.gasGauge(GaugeType.STANDARD, DataType.INPUT, 42, 13, chemical);
            GuiSlot itemInput = b.slot(SlotType.INPUT, 21, 17).with(SlotOverlay.MINUS);
            b.entries(itemInput, item, false);
            b.constantProgress(ProgressType.LARGE_RIGHT, 64, 40);
            GuiSlot outputSlot = b.slot(SlotType.OUTPUT, 116, 36);
            b.entries(outputSlot, output, true);
            b.verticalPowerBar(164, 15);
            b.slot(SlotType.POWER, 144, 35).with(SlotOverlay.POWER);
        });
    }

    static MSEmiRecipe airCompressor() {
        Component description = Component.translatable("description.mekanismelements.air_compressor");
        return new MSEmiRecipe(MSEmiMachine.AIR_COMPRESSOR, new ResourceLocation(MekanismElements.MODID, "/air_compressor"), List.of(), List.of(), b -> {
            b.infoScreen(5, 5, 122, 70, description);
            b.gasGauge(GaugeType.STANDARD, DataType.OUTPUT, 131, 5, COMPRESSED_AIR);
        });
    }

    static MSEmiRecipe seawaterPump() {
        Component description = Component.translatable("description.mekanismelements.seawater_pump");
        return new MSEmiRecipe(MSEmiMachine.SEAWATER_PUMP, new ResourceLocation(MekanismElements.MODID, "/seawater_pump"), List.of(),
                List.of(EmiStack.of(MSFluids.SEAWATER.getFluid(), 200)), b -> {
            b.infoScreen(5, 5, 122, 70, description);
            b.fluidGauge(GaugeType.STANDARD, DataType.OUTPUT, 131, 5, SEAWATER);
        });
    }

    private static List<EmiStack> stacks(List<ItemStack> stacks) {
        return stacks.stream().map(EmiStack::of).toList();
    }

    private static EmiIngredient items(List<ItemStack> stacks) {
        return stacks.isEmpty() ? EmiStack.EMPTY : EmiIngredient.of(stacks(stacks));
    }

    private static EmiIngredient fluidIngredient(List<FluidStack> fluids, long amount) {
        return EmiIngredient.of(fluids.stream().<EmiIngredient>map(fluid -> EmiStack.of(fluid.getFluid(), amount)).toList(), amount);
    }

    private static GasStack firstGas(ChemicalStackIngredient.GasStackIngredient ingredient) {
        List<GasStack> stacks = ingredient.getRepresentations();
        return stacks.isEmpty() ? GasStack.EMPTY : stacks.get(0);
    }

    private static GasStack scaled(GasStack stack, int ticks) {
        return stack.isEmpty() ? stack : ChemicalUtil.copyWithAmount(stack, stack.getAmount() * ticks);
    }
}
