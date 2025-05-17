package com.fxd927.mekanismelements.common.registries;

import com.fxd927.mekanismelements.api.recipes.AdsorptionRecipe;
import com.fxd927.mekanismelements.api.recipes.ChemicalDemolitionRecipe;
import com.fxd927.mekanismelements.api.recipes.RadiationIrradiatingRecipe;
import com.fxd927.mekanismelements.common.MekanismElements;
import com.fxd927.mekanismelements.common.recipe.impl.AdsorptionIRecipe;
import com.fxd927.mekanismelements.common.recipe.impl.ChemicalDemolitionIRecipe;
import com.fxd927.mekanismelements.common.recipe.impl.RadiationIrradiatingIRecipe;
import com.fxd927.mekanismelements.common.recipe.serializer.AdsorptionRecipeSerializer;
import com.fxd927.mekanismelements.common.recipe.serializer.ChemicalDemolitionRecipeSerializer;
import com.fxd927.mekanismelements.common.recipe.serializer.RadiationIrradiatorRecipeSerializer;
import mekanism.common.registration.impl.RecipeSerializerDeferredRegister;
import mekanism.common.registration.impl.RecipeSerializerRegistryObject;

public class MSRecipeSerializers {
    public static final RecipeSerializerDeferredRegister RECIPE_SERIALIZERS = new RecipeSerializerDeferredRegister(MekanismElements.MODID);

    public static final RecipeSerializerRegistryObject<AdsorptionRecipe> ADSORPTION_SEPARATOR = RECIPE_SERIALIZERS.register("adsorption", () -> new AdsorptionRecipeSerializer<>(AdsorptionIRecipe::new));
    public static final RecipeSerializerRegistryObject<RadiationIrradiatingRecipe> RADIATION_IRRADIATOR = RECIPE_SERIALIZERS.register("radiation_irradiating", () -> new RadiationIrradiatorRecipeSerializer<>(RadiationIrradiatingIRecipe::new));
    public static final RecipeSerializerRegistryObject<ChemicalDemolitionRecipe> CHEMICAL_DEMOLITION = RECIPE_SERIALIZERS.register("chemical_demolition", () -> new ChemicalDemolitionRecipeSerializer<>(ChemicalDemolitionIRecipe::new));

    private MSRecipeSerializers(){
    }
}
