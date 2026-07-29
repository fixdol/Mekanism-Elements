package fixdol.mekanismelements.client;

import fixdol.mekanismelements.api.recipes.AdsorptionRecipe;
import fixdol.mekanismelements.api.recipes.ChemicalDemolitionRecipe;
import fixdol.mekanismelements.api.recipes.InfinityOreReprocessingRecipe; // faltaba este import
import fixdol.mekanismelements.api.recipes.RadiationIrradiatingRecipe;
import fixdol.mekanismelements.common.MSLang;
import fixdol.mekanismelements.common.MekanismElements;
import fixdol.mekanismelements.common.recipe.MSRecipeType;
import fixdol.mekanismelements.common.registries.MSBlocks;

public class MSJEIRecipeType {
    public static final fixdol.mekanismelements.client.jei.MSJEIRecipeViewerRecipeType<AdsorptionRecipe> ADSORPTION_SEPARATOR =
            new fixdol.mekanismelements.client.jei.MSJEIRecipeViewerRecipeType<>(
                    MSRecipeType.ADSORPTION,
                    AdsorptionRecipe.class,
                    MSLang.NAME_ADSORPTION_SEPARATOR,
                    MekanismElements.rl("textures/gui/jei/adsorption_separator.png"),
                    3, 3, 170, 79,
                    MSBlocks.ADSORPTION_SEPARATOR
            );

    public static final fixdol.mekanismelements.client.jei.MSJEIRecipeViewerRecipeType<ChemicalDemolitionRecipe> CHEMICAL_DEMOLITION_MACHINE =
            new fixdol.mekanismelements.client.jei.MSJEIRecipeViewerRecipeType<>(
                    MSRecipeType.CHEMICAL_DEMOLITION,
                    ChemicalDemolitionRecipe.class,
                    MSLang.NAME_CHEMICAL_DEMOLITION_MACHINE,
                    MekanismElements.rl("textures/gui/jei/chemical_demolition_machine.png"),
                    3, 3, 170, 79,
                    MSBlocks.CHEMICAL_DEMOLITION_MACHINE
            );

    public static final fixdol.mekanismelements.client.jei.MSJEIRecipeViewerRecipeType<RadiationIrradiatingRecipe> RADIATION_IRRADIATOR =
            new fixdol.mekanismelements.client.jei.MSJEIRecipeViewerRecipeType<>(
                    MSRecipeType.RADIATION_IRRADIATING,
                    RadiationIrradiatingRecipe.class,
                    MSLang.NAME_RADIATION_IRRADIATOR,
                    MekanismElements.rl("textures/gui/jei/radiation_irradiator.png"),
                    3, 3, 170, 79,
                    MSBlocks.RADIATION_IRRADIATOR
            );

    public static final fixdol.mekanismelements.client.jei.MSJEIRecipeViewerRecipeType<InfinityOreReprocessingRecipe> INFINITY_ORE_REPROCESSING =
            new fixdol.mekanismelements.client.jei.MSJEIRecipeViewerRecipeType<>(
                    MSRecipeType.INFINITY_ORE_REPROCESSING, // agregar este campo en MSRecipeType.java (ver checklist)
                    InfinityOreReprocessingRecipe.class,
                    MSLang.NAME_INFINITY_ORE_REPROCESSING, // agregar en MSLang.java
                    MekanismElements.rl("textures/gui/jei/infinity_ore_reprocessing.png"),
                    3, 12, 170, 62,
                    MSBlocks.INFINITY_ORE_REPROCESSING
            );
}