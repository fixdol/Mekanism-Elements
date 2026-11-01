package fixdol.mekanismelements.common.registries;

import fixdol.mekanismelements.api.recipes.AdsorptionRecipe;
import fixdol.mekanismelements.api.recipes.ChemicalDemolitionRecipe;
import fixdol.mekanismelements.api.recipes.InfinityOreReprocessingRecipe;
import fixdol.mekanismelements.api.recipes.RadiationIrradiatingRecipe;
import fixdol.mekanismelements.common.MekanismElements;
import fixdol.mekanismelements.common.recipe.impl.AdsorptionIRecipe;
import fixdol.mekanismelements.common.recipe.impl.ChemicalDemolitionIRecipe;
import fixdol.mekanismelements.common.recipe.impl.InfinityOreReprocessingIRecipe;
import fixdol.mekanismelements.common.recipe.impl.RadiationIrradiatingIRecipe;
import fixdol.mekanismelements.common.recipe.serializer.AdsorptionRecipeSerializer;
import fixdol.mekanismelements.common.recipe.serializer.ChemicalDemolitionRecipeSerializer;
import fixdol.mekanismelements.common.recipe.serializer.InfinityOreReprocessingRecipeSerializer;
import fixdol.mekanismelements.common.recipe.serializer.RadiationIrradiatorRecipeSerializer;
import mekanism.common.registration.MekanismDeferredHolder;
import mekanism.common.registration.MekanismDeferredRegister;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class MSRecipeSerializers {
    public static final MekanismDeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = new MekanismDeferredRegister<>(net.minecraft.core.registries.Registries.RECIPE_SERIALIZER, MekanismElements.MODID);

    public static final MekanismDeferredHolder<RecipeSerializer<?>, RecipeSerializer<AdsorptionRecipe>> ADSORPTION_SEPARATOR = RECIPE_SERIALIZERS.register("adsorption", () -> AdsorptionRecipeSerializer.create(AdsorptionIRecipe::new));
    public static final MekanismDeferredHolder<RecipeSerializer<?>, RecipeSerializer<RadiationIrradiatingRecipe>> RADIATION_IRRADIATOR = RECIPE_SERIALIZERS.register("radiation_irradiating", () -> RadiationIrradiatorRecipeSerializer.create(RadiationIrradiatingIRecipe::new));
    public static final MekanismDeferredHolder<RecipeSerializer<?>, RecipeSerializer<ChemicalDemolitionRecipe>> CHEMICAL_DEMOLITION = RECIPE_SERIALIZERS.register("chemical_demolition", () -> ChemicalDemolitionRecipeSerializer.create(ChemicalDemolitionIRecipe::new));
    public static final MekanismDeferredHolder<RecipeSerializer<?>, RecipeSerializer<InfinityOreReprocessingRecipe>> INFINITY_ORE_REPROCESSING = RECIPE_SERIALIZERS.register("infinity_ore_reprocessing", () -> InfinityOreReprocessingRecipeSerializer.create(InfinityOreReprocessingIRecipe::new));

    private MSRecipeSerializers(){
    }
}
