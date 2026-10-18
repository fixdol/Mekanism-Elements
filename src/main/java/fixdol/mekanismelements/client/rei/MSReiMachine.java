package fixdol.mekanismelements.client.rei;

import fixdol.mekanismelements.common.MekanismElements;
import fixdol.mekanismelements.common.registries.MSBlocks;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import mekanism.api.providers.IItemProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

/**
 * One machine as REI sees it: its category id, the block that works as its icon and workstation, and the size of
 * the area its layout is drawn in. The offsets are those of the same area in the JEI categories.
 */
record MSReiMachine(ResourceLocation id, IItemProvider block, int xOffset, int yOffset, int width, int height) {
    static final MSReiMachine ADSORPTION_SEPARATOR = of("adsorption_separator", MSBlocks.ADSORPTION_SEPARATOR);
    static final MSReiMachine CHEMICAL_DEMOLITION_MACHINE = of("chemical_demolition_machine", MSBlocks.CHEMICAL_DEMOLITION_MACHINE);
    static final MSReiMachine RADIATION_IRRADIATOR = of("radiation_irradiator", MSBlocks.RADIATION_IRRADIATOR);
    static final MSReiMachine AIR_COMPRESSOR = of("air_compressor", MSBlocks.AIR_COMPRESSOR);
    static final MSReiMachine SEAWATER_PUMP = of("seawater_pump", MSBlocks.SEAWATER_PUMP);
    static final MSReiMachine INFINITY_ORE_REPROCESSING = of("infinity_ore_reprocessing", MSBlocks.INFINITY_ORE_REPROCESSING);

    private static MSReiMachine of(String path, IItemProvider block) {
        return new MSReiMachine(MekanismElements.rl(path), block, 3, 3, 170, 79);
    }

    CategoryIdentifier<MSReiDisplay> categoryId() {
        return CategoryIdentifier.of(id);
    }

    Component title() {
        return block.asItem().getDescription();
    }
}
