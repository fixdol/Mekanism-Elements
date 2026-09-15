package fixdol.mekanismelements.client;

import fixdol.mekanismelements.api.recipes.AdsorptionRecipe;
import fixdol.mekanismelements.api.recipes.ChemicalDemolitionRecipe;
import fixdol.mekanismelements.api.recipes.InfinityOreReprocessingRecipe;
import fixdol.mekanismelements.api.recipes.RadiationIrradiatingRecipe;
import fixdol.mekanismelements.client.jei.machine.AirCompressorInfoRecipe;
import fixdol.mekanismelements.client.jei.machine.SeawaterPumpInfoRecipe;
import fixdol.mekanismelements.common.MekanismElements;
import mekanism.client.jei.MekanismJEIRecipeType;
import net.minecraft.resources.ResourceLocation;

public class MSJEIRecipeType {

    public static final MekanismJEIRecipeType<AdsorptionRecipe> ADSORPTION_SEPARATOR =
          new MekanismJEIRecipeType<>(rl("adsorption_separator"), AdsorptionRecipe.class);
    public static final MekanismJEIRecipeType<RadiationIrradiatingRecipe> RADIATION_IRRADIATOR =
          new MekanismJEIRecipeType<>(rl("radiation_irradiator"), RadiationIrradiatingRecipe.class);
    public static final MekanismJEIRecipeType<ChemicalDemolitionRecipe> CHEMICAL_DEMOLITION_MACHINE =
          new MekanismJEIRecipeType<>(rl("chemical_demolition_machine"), ChemicalDemolitionRecipe.class);
    public static final MekanismJEIRecipeType<InfinityOreReprocessingRecipe> INFINITY_ORE_REPROCESSING =
          new MekanismJEIRecipeType<>(rl("infinity_ore_reprocessing"), InfinityOreReprocessingRecipe.class);
    public static final MekanismJEIRecipeType<AirCompressorInfoRecipe> AIR_COMPRESSOR =
          new MekanismJEIRecipeType<>(rl("air_compressor"), AirCompressorInfoRecipe.class);
    public static final MekanismJEIRecipeType<SeawaterPumpInfoRecipe> SEAWATER_PUMP =
          new MekanismJEIRecipeType<>(rl("seawater_pump"), SeawaterPumpInfoRecipe.class);

    private static ResourceLocation rl(String path) {
        return new ResourceLocation(MekanismElements.MODID, path);
    }

    private MSJEIRecipeType() {
    }
}
