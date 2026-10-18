package fixdol.mekanismelements.client.rei;

import fixdol.mekanismelements.api.recipes.AdsorptionRecipe;
import fixdol.mekanismelements.api.recipes.ChemicalDemolitionRecipe;
import fixdol.mekanismelements.api.recipes.InfinityOreReprocessingRecipe;
import fixdol.mekanismelements.api.recipes.RadiationIrradiatingRecipe;
import fixdol.mekanismelements.client.MSJEIRecipeType;
import fixdol.mekanismelements.client.jei.machine.AirCompressorInfoRecipe;
import fixdol.mekanismelements.client.jei.machine.AirCompressorRecipeViewerType;
import fixdol.mekanismelements.client.jei.machine.SeawaterPumpInfoRecipe;
import fixdol.mekanismelements.client.jei.machine.SeawaterPumpRecipeViewerType;
import fixdol.mekanismelements.common.registries.MSFluids;
import fixdol.mekanismelements.common.registries.MSGases;
import fixdol.mekanismelements.common.tile.machine.TileEntityAdsorptionSeparator;
import fixdol.mekanismelements.common.tile.machine.TileEntityChemicalDemolitionMachine;
import fixdol.mekanismelements.common.tile.machine.TileEntityRadiationIrradiator;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import me.shedaniel.rei.api.common.util.EntryStacks;
import mekanism.api.chemical.Chemical;
import mekanism.api.chemical.ChemicalStack;
import mekanism.client.gui.element.gauge.GaugeType;
import mekanism.client.gui.element.progress.ProgressType;
import mekanism.client.gui.element.slot.GuiSlot;
import mekanism.client.gui.element.slot.SlotType;
import mekanism.common.inventory.container.slot.SlotOverlay;
import mekanism.common.tile.component.config.DataType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.fluids.FluidStack;

import java.util.List;
import java.util.Optional;

/**
 * The layout of each machine, with the same coordinates as its JEI and EMI categories.
 */
final class MSReiLayouts {
    private static final ChemicalStack COMPRESSED_AIR = new ChemicalStack((Chemical) MSGases.COMPRESSED_AIR.get(), 200);
    private static final FluidStack SEAWATER = new FluidStack(MSFluids.SEAWATER.get(), 200);

    private MSReiLayouts() {
    }

    static MSReiDisplay adsorption(AdsorptionRecipe recipe) {
        EntryIngredient item = EntryIngredients.ofItemStacks(recipe.getItemInput().getRepresentations());
        // The machine consumes the fluid for the whole operation, so show the scaled amount like the JEI category does
        List<FluidStack> fluids = recipe.getFluidInput().getRepresentations();
        FluidStack fluid = fluids.isEmpty() ? FluidStack.EMPTY
                : new FluidStack(fluids.get(0).getFluid(), fluids.get(0).getAmount() * TileEntityAdsorptionSeparator.BASE_TICKS_REQUIRED);
        ChemicalStack output = first(recipe.getOutputDefinition());
        return new MSReiDisplay(MSJEIRecipeType.ADSORPTION_SEPARATOR, List.of(item, fluidEntries(fluids, fluid.getAmount())), List.of(),
                Optional.of(recipe.getId()), b -> {
            b.fluidGauge(GaugeType.MEDIUM, DataType.INPUT, 17, 13, fluid);
            b.chemicalGauge(GaugeType.STANDARD, DataType.OUTPUT, 131, 13, output);
            GuiSlot input = b.slot(SlotType.INPUT, 80, 22);
            b.entries(input, item, false);
            b.slot(SlotType.OUTPUT, 152, 55).with(SlotOverlay.PLUS);
            b.slot(SlotType.POWER, 152, 14).with(SlotOverlay.POWER);
            b.progress(ProgressType.LARGE_RIGHT, 64, 40, TileEntityAdsorptionSeparator.BASE_TICKS_REQUIRED);
            b.horizontalPowerBar(115, 75);
        });
    }

    static MSReiDisplay chemicalDemolition(ChemicalDemolitionRecipe recipe) {
        EntryIngredient item = EntryIngredients.ofItemStacks(recipe.getItemInput().getRepresentations());
        ChemicalStack input = scaled(first(recipe.getGasInput().getRepresentations()), TileEntityChemicalDemolitionMachine.BASE_TICKS_REQUIRED);
        List<ItemStack> firstOutput = recipe.getFirstOutputDefinition();
        List<ItemStack> secondOutput = recipe.getSecondOutputDefinition();
        EntryIngredient first = EntryIngredients.ofItemStacks(firstOutput);
        EntryIngredient second = EntryIngredients.ofItemStacks(secondOutput);
        return new MSReiDisplay(MSJEIRecipeType.CHEMICAL_DEMOLITION_MACHINE, List.of(item), List.of(first, second),
                Optional.of(recipe.getId()), b -> {
            b.chemicalGauge(GaugeType.STANDARD, DataType.INPUT, 7, 4, input);
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

    static MSReiDisplay radiationIrradiating(RadiationIrradiatingRecipe recipe) {
        EntryIngredient item = EntryIngredients.ofItemStacks(recipe.getItemInput().getRepresentations());
        ChemicalStack input = scaled(first(recipe.getGasInput().getRepresentations()), TileEntityRadiationIrradiator.BASE_TICKS_REQUIRED);
        ChemicalStack output = first(recipe.getOutputDefinition());
        return new MSReiDisplay(MSJEIRecipeType.RADIATION_IRRADIATOR, List.of(item), List.of(),
                Optional.of(recipe.getId()), b -> {
            b.chemicalGauge(GaugeType.STANDARD, DataType.INPUT, 28, 13, input);
            b.chemicalGauge(GaugeType.STANDARD, DataType.OUTPUT, 131, 13, output);
            GuiSlot inputSlot = b.slot(SlotType.INPUT, 7, 36);
            b.entries(inputSlot, item, false);
            b.slot(SlotType.EXTRA, 7, 55).with(SlotOverlay.MINUS);
            b.slot(SlotType.OUTPUT, 152, 55).with(SlotOverlay.PLUS);
            b.slot(SlotType.POWER, 152, 14).with(SlotOverlay.POWER);
            b.progress(ProgressType.LARGE_RIGHT, 64, 40, TileEntityRadiationIrradiator.BASE_TICKS_REQUIRED);
            b.horizontalPowerBar(115, 75);
        });
    }

    static MSReiDisplay infinityOreReprocessing(InfinityOreReprocessingRecipe recipe) {
        EntryIngredient item = EntryIngredients.ofItemStacks(recipe.getItemInput().getRepresentations());
        ChemicalStack chemical = first(recipe.getChemicalInput().getRepresentations());
        EntryIngredient output = EntryIngredients.ofItemStacks(recipe.getOutputDefinition());
        return new MSReiDisplay(MSJEIRecipeType.INFINITY_ORE_REPROCESSING, List.of(item), List.of(output),
                Optional.of(recipe.getId()), b -> {
            b.chemicalGauge(GaugeType.STANDARD, DataType.INPUT, 42, 13, chemical);
            GuiSlot itemInput = b.slot(SlotType.INPUT, 21, 17).with(SlotOverlay.MINUS);
            b.entries(itemInput, item, false);
            b.constantProgress(ProgressType.LARGE_RIGHT, 64, 40);
            GuiSlot outputSlot = b.slot(SlotType.OUTPUT, 116, 36);
            b.entries(outputSlot, output, true);
            b.verticalPowerBar(164, 15);
            b.slot(SlotType.POWER, 144, 35).with(SlotOverlay.POWER);
        });
    }

    static MSReiDisplay airCompressor() {
        Component description = Component.translatable("description.mekanismelements.air_compressor");
        return new MSReiDisplay(AirCompressorRecipeViewerType.INSTANCE, List.of(), List.of(),
                Optional.of(ResourceLocation.fromNamespaceAndPath("mekanismelements", "/air_compressor")), b -> {
            b.infoScreen(5, 5, 122, 70, description);
            b.chemicalGauge(GaugeType.STANDARD, DataType.OUTPUT, 131, 5, COMPRESSED_AIR);
        });
    }

    static MSReiDisplay seawaterPump() {
        Component description = Component.translatable("description.mekanismelements.seawater_pump");
        return new MSReiDisplay(SeawaterPumpRecipeViewerType.INSTANCE, List.of(), List.of(EntryIngredients.of(MSFluids.SEAWATER.get(), 200)),
                Optional.of(ResourceLocation.fromNamespaceAndPath("mekanismelements", "/seawater_pump")), b -> {
            b.infoScreen(5, 5, 122, 70, description);
            b.fluidGauge(GaugeType.STANDARD, DataType.OUTPUT, 131, 5, SEAWATER);
        });
    }

    private static EntryIngredient fluidEntries(List<FluidStack> fluids, long amount) {
        EntryIngredient.Builder builder = EntryIngredient.builder();
        for (FluidStack fluid : fluids) {
            builder.add(EntryStacks.of(fluid.getFluid(), amount));
        }
        return builder.build();
    }

    private static ChemicalStack first(List<ChemicalStack> stacks) {
        return stacks.isEmpty() ? ChemicalStack.EMPTY : stacks.get(0);
    }

    private static ChemicalStack scaled(ChemicalStack stack, int ticks) {
        return stack.isEmpty() ? stack : stack.copyWithAmount(stack.getAmount() * ticks);
    }
}
