package fixdol.mekanismelements.client.emi;

import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.stack.EmiStack;
import fixdol.mekanismelements.common.MekanismElements;
import fixdol.mekanismelements.common.registries.MSBlocks;
import mekanism.api.providers.IItemProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

/**
 * One machine as EMI sees it: its category, the block that works as its icon and workstation, and the size of the
 * area its layout is drawn in. The offsets are those of the same area in the JEI categories.
 */
final class MSEmiMachine {
    static final MSEmiMachine ADSORPTION_SEPARATOR = new MSEmiMachine("adsorption_separator", MSBlocks.ADSORPTION_SEPARATOR);
    static final MSEmiMachine CHEMICAL_DEMOLITION_MACHINE = new MSEmiMachine("chemical_demolition_machine", MSBlocks.CHEMICAL_DEMOLITION_MACHINE);
    static final MSEmiMachine RADIATION_IRRADIATOR = new MSEmiMachine("radiation_irradiator", MSBlocks.RADIATION_IRRADIATOR);
    static final MSEmiMachine AIR_COMPRESSOR = new MSEmiMachine("air_compressor", MSBlocks.AIR_COMPRESSOR);
    static final MSEmiMachine SEAWATER_PUMP = new MSEmiMachine("seawater_pump", MSBlocks.SEAWATER_PUMP);
    static final MSEmiMachine INFINITY_ORE_REPROCESSING = new MSEmiMachine("infinity_ore_reprocessing", MSBlocks.INFINITY_ORE_REPROCESSING);

    final int xOffset = 3;
    final int yOffset = 3;
    final int width = 170;
    final int height = 79;

    private final IItemProvider block;
    private final EmiRecipeCategory category;

    private MSEmiMachine(String path, IItemProvider block) {
        this.block = block;
        ResourceLocation id = MekanismElements.rl(path);
        Component name = block.asItem().getDescription();
        this.category = new EmiRecipeCategory(id, EmiStack.of(block.asItem())) {
            @Override
            public Component getName() {
                return name;
            }
        };
    }

    EmiRecipeCategory category() {
        return category;
    }

    EmiStack workstation() {
        return EmiStack.of(block.asItem());
    }
}
