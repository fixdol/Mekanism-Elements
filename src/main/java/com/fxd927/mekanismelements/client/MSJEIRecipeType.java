package com.fxd927.mekanismelements.client;

import com.fxd927.mekanismelements.api.recipes.AdsorptionRecipe;
import com.fxd927.mekanismelements.api.recipes.RadiationIrradiatingRecipe;
import com.fxd927.mekanismelements.common.registries.MSBlocks;
import mekanism.client.jei.MekanismJEIRecipeType;

public class MSJEIRecipeType {
    public static final MekanismJEIRecipeType<AdsorptionRecipe> ADSORPTION_SEPARATOR = new MekanismJEIRecipeType<>(MSBlocks.ADSORPTION_SEPARATOR, AdsorptionRecipe.class);
    //public static final MekanismJEIRecipeType<ChemicalDemolitionRecipe> CHEMICAL_DEMOLITION_MACHINE = new MekanismJEIRecipeType<>(MSBlocks.CHEMICAL_DEMOLITION_MACHINE, ChemicalDemolitionRecipe.class);
    public static final MekanismJEIRecipeType<RadiationIrradiatingRecipe> RADIATION_IRRADIATOR = new MekanismJEIRecipeType<>(MSBlocks.RADIATION_IRRADIATOR, RadiationIrradiatingRecipe.class);
}
