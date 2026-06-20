package com.fxd927.mekanismelements.client;

import com.fxd927.mekanismelements.api.recipes.AdsorptionRecipe;
import com.fxd927.mekanismelements.api.recipes.ChemicalDemolitionRecipe;
import com.fxd927.mekanismelements.api.recipes.RadiationIrradiatingRecipe;
import com.fxd927.mekanismelements.common.MSLang;
import com.fxd927.mekanismelements.common.MekanismElements;
import com.fxd927.mekanismelements.common.recipe.MSRecipeType;
import com.fxd927.mekanismelements.common.registries.MSBlocks;

public class MSJEIRecipeType {
    public static final com.fxd927.mekanismelements.client.jei.MSJEIRecipeViewerRecipeType<AdsorptionRecipe> ADSORPTION_SEPARATOR =
            new com.fxd927.mekanismelements.client.jei.MSJEIRecipeViewerRecipeType<>(
                    MSRecipeType.ADSORPTION,
                    AdsorptionRecipe.class,
                    MSLang.NAME_ADSORPTION_SEPARATOR,
                    MekanismElements.rl("textures/gui/jei/adsorption_separator.png"),
                    3, 3, 170, 79,
                    MSBlocks.ADSORPTION_SEPARATOR
            );

    public static final com.fxd927.mekanismelements.client.jei.MSJEIRecipeViewerRecipeType<ChemicalDemolitionRecipe> CHEMICAL_DEMOLITION_MACHINE =
            new com.fxd927.mekanismelements.client.jei.MSJEIRecipeViewerRecipeType<>(
                    MSRecipeType.CHEMICAL_DEMOLITION,
                    ChemicalDemolitionRecipe.class,
                    MSLang.NAME_CHEMICAL_DEMOLITION_MACHINE,
                    MekanismElements.rl("textures/gui/jei/chemical_demolition_machine.png"),
                    3, 3, 170, 79,
                    MSBlocks.CHEMICAL_DEMOLITION_MACHINE
            );

    public static final com.fxd927.mekanismelements.client.jei.MSJEIRecipeViewerRecipeType<RadiationIrradiatingRecipe> RADIATION_IRRADIATOR =
            new com.fxd927.mekanismelements.client.jei.MSJEIRecipeViewerRecipeType<>(
                    MSRecipeType.RADIATION_IRRADIATING,
                    RadiationIrradiatingRecipe.class,
                    MSLang.NAME_RADIATION_IRRADIATOR,
                    MekanismElements.rl("textures/gui/jei/radiation_irradiator.png"),
                    3, 3, 170, 79,
                    MSBlocks.RADIATION_IRRADIATOR
            );
}