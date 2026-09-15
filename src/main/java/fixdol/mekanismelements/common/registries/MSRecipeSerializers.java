package fixdol.mekanismelements.common.registries;

import fixdol.mekanismelements.api.recipes.AdsorptionRecipe;
import fixdol.mekanismelements.api.recipes.ChemicalDemolitionRecipe;
import fixdol.mekanismelements.api.recipes.InfinityOreReprocessingRecipe;
import fixdol.mekanismelements.common.registries.MSRecipeSerializers;
import fixdol.mekanismelements.common.MekanismElements;
import fixdol.mekanismelements.api.recipes.RadiationIrradiatingRecipe;

import fixdol.mekanismelements.common.recipe.impl.AdsorptionIRecipe;
import fixdol.mekanismelements.common.recipe.impl.ChemicalDemolitionIRecipe;
import fixdol.mekanismelements.common.recipe.impl.InfinityOreReprocessingIRecipe;
import fixdol.mekanismelements.common.recipe.impl.RadiationIrradiatingIRecipe;
import fixdol.mekanismelements.common.recipe.serializer.AdsorptionRecipeSerializer;
import fixdol.mekanismelements.common.recipe.serializer.ChemicalDemolitionRecipeSerializer;
import fixdol.mekanismelements.common.recipe.serializer.InfinityOreReprocessingRecipeSerializer;
import fixdol.mekanismelements.common.recipe.serializer.RadiationIrradiatorRecipeSerializer;
import mekanism.common.registration.impl.RecipeSerializerDeferredRegister;
import mekanism.common.registration.impl.RecipeSerializerRegistryObject;

public class MSRecipeSerializers {
    public static final RecipeSerializerDeferredRegister RECIPE_SERIALIZERS = new RecipeSerializerDeferredRegister(MekanismElements.MODID);

    public static final RecipeSerializerRegistryObject<AdsorptionRecipe> ADSORPTION_SEPARATOR = RECIPE_SERIALIZERS.register("adsorption", () -> new AdsorptionRecipeSerializer<>(AdsorptionIRecipe::new));
    public static final RecipeSerializerRegistryObject<RadiationIrradiatingRecipe> RADIATION_IRRADIATOR = RECIPE_SERIALIZERS.register("radiation_irradiating", () -> new RadiationIrradiatorRecipeSerializer<>(RadiationIrradiatingIRecipe::new));
    public static final RecipeSerializerRegistryObject<ChemicalDemolitionRecipe> CHEMICAL_DEMOLITION = RECIPE_SERIALIZERS.register("chemical_demolition", () -> new ChemicalDemolitionRecipeSerializer<>(ChemicalDemolitionIRecipe::new));
    public static final RecipeSerializerRegistryObject<InfinityOreReprocessingRecipe> INFINITY_ORE_REPROCESSING = RECIPE_SERIALIZERS.register("infinity_ore_reprocessing", () -> new InfinityOreReprocessingRecipeSerializer<>(InfinityOreReprocessingIRecipe::new));

    private MSRecipeSerializers(){
    }
}
