package fixdol.mekanismelements.common.registries;

import fixdol.mekanismelements.api.recipes.AdsorptionRecipe;
import fixdol.mekanismelements.api.recipes.ChemicalDemolitionRecipe;
import fixdol.mekanismelements.api.recipes.RadiationIrradiatingRecipe;
import fixdol.mekanismelements.common.MekanismElements;
import fixdol.mekanismelements.common.recipe.impl.AdsorptionIRecipe;
import fixdol.mekanismelements.common.recipe.impl.ChemicalDemolitionIRecipe;
import fixdol.mekanismelements.common.recipe.impl.InfinityOreReprocessingIRecipe;
import fixdol.mekanismelements.common.recipe.impl.RadiationIrradiatingIRecipe;
import fixdol.mekanismelements.common.recipe.serializer.AdsorptionRecipeSerializer;
import fixdol.mekanismelements.common.recipe.serializer.ChemicalDemolitionRecipeSerializer;
import fixdol.mekanismelements.common.recipe.serializer.RadiationIrradiatorRecipeSerializer;
import mekanism.common.registration.MekanismDeferredRegister;
import mekanism.common.registration.MekanismDeferredHolder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class MSRecipeSerializers {
    public static final MekanismDeferredRegister<net.minecraft.world.item.crafting.RecipeSerializer<?>> RECIPE_SERIALIZERS = new MekanismDeferredRegister<>(net.minecraft.core.registries.Registries.RECIPE_SERIALIZER, MekanismElements.MODID);

    public static final MekanismDeferredHolder<net.minecraft.world.item.crafting.RecipeSerializer<?>, AdsorptionRecipeSerializer<AdsorptionRecipe>> ADSORPTION_SEPARATOR = RECIPE_SERIALIZERS.register("adsorption", () -> new AdsorptionRecipeSerializer<>(AdsorptionIRecipe::new));
    public static final MekanismDeferredHolder<net.minecraft.world.item.crafting.RecipeSerializer<?>, RadiationIrradiatorRecipeSerializer<RadiationIrradiatingRecipe>> RADIATION_IRRADIATOR = RECIPE_SERIALIZERS.register("radiation_irradiating", () -> new RadiationIrradiatorRecipeSerializer<>(RadiationIrradiatingIRecipe::new));
    public static final MekanismDeferredHolder<net.minecraft.world.item.crafting.RecipeSerializer<?>, ChemicalDemolitionRecipeSerializer<ChemicalDemolitionRecipe>> CHEMICAL_DEMOLITION = RECIPE_SERIALIZERS.register("chemical_demolition", () -> new ChemicalDemolitionRecipeSerializer<>(ChemicalDemolitionIRecipe::new));

    public static final MekanismDeferredHolder<net.minecraft.world.item.crafting.RecipeSerializer<?>, RecipeSerializer<InfinityOreReprocessingIRecipe>> INFINITY_ORE_REPROCESSING =
            RECIPE_SERIALIZERS.register("infinity_ore_reprocessing", () -> new RecipeSerializer<InfinityOreReprocessingIRecipe>() {
                @Override
                public com.mojang.serialization.MapCodec<InfinityOreReprocessingIRecipe> codec() {
                    return InfinityOreReprocessingIRecipe.CODEC;
                }

                @Override
                public StreamCodec<RegistryFriendlyByteBuf, InfinityOreReprocessingIRecipe> streamCodec() {
                    return InfinityOreReprocessingIRecipe.STREAM_CODEC;
                }
            });

    private MSRecipeSerializers(){
    }
}