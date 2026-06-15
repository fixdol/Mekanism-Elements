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
import mekanism.common.registration.MekanismDeferredRegister;
import mekanism.common.registration.MekanismDeferredHolder;

public class MSRecipeSerializers {
    public static final MekanismDeferredRegister<net.minecraft.world.item.crafting.RecipeSerializer<?>> RECIPE_SERIALIZERS = new MekanismDeferredRegister<>(net.minecraft.core.registries.Registries.RECIPE_SERIALIZER, MekanismElements.MODID);

    public static final MekanismDeferredHolder<net.minecraft.world.item.crafting.RecipeSerializer<?>, AdsorptionRecipeSerializer<AdsorptionRecipe>> ADSORPTION_SEPARATOR = RECIPE_SERIALIZERS.register("adsorption", () -> new AdsorptionRecipeSerializer<>(AdsorptionIRecipe::new));
    public static final MekanismDeferredHolder<net.minecraft.world.item.crafting.RecipeSerializer<?>, RadiationIrradiatorRecipeSerializer<RadiationIrradiatingRecipe>> RADIATION_IRRADIATOR = RECIPE_SERIALIZERS.register("radiation_irradiating", () -> new RadiationIrradiatorRecipeSerializer<>(RadiationIrradiatingIRecipe::new));
    public static final MekanismDeferredHolder<net.minecraft.world.item.crafting.RecipeSerializer<?>, ChemicalDemolitionRecipeSerializer<ChemicalDemolitionRecipe>> CHEMICAL_DEMOLITION = RECIPE_SERIALIZERS.register("chemical_demolition", () -> new ChemicalDemolitionRecipeSerializer<>(ChemicalDemolitionIRecipe::new));

    private MSRecipeSerializers(){
    }
}

