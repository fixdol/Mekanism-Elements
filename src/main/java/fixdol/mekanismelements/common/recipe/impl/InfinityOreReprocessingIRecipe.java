package fixdol.mekanismelements.common.recipe.impl;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import fixdol.mekanismelements.api.recipes.InfinityOreReprocessingRecipe;
import fixdol.mekanismelements.common.recipe.MSRecipeType;
import fixdol.mekanismelements.common.registries.MSRecipeSerializers;
import mekanism.api.recipes.ingredients.ChemicalStackIngredient;
import mekanism.api.recipes.ingredients.ItemStackIngredient;
import mekanism.api.recipes.vanilla_input.FluidRecipeInput;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

public class InfinityOreReprocessingIRecipe extends InfinityOreReprocessingRecipe {

    public static final MapCodec<InfinityOreReprocessingIRecipe> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            ItemStackIngredient.CODEC.fieldOf("item_input").forGetter(InfinityOreReprocessingRecipe::getItemInput),
            ChemicalStackIngredient.CODEC.fieldOf("chemical_input").forGetter(InfinityOreReprocessingRecipe::getChemicalInput),
            ItemStack.CODEC.fieldOf("output").forGetter(r -> r.getOutputDefinition().get(0))
    ).apply(instance, InfinityOreReprocessingIRecipe::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, InfinityOreReprocessingIRecipe> STREAM_CODEC = StreamCodec.composite(
            ItemStackIngredient.STREAM_CODEC, InfinityOreReprocessingRecipe::getItemInput,
            ChemicalStackIngredient.STREAM_CODEC, InfinityOreReprocessingRecipe::getChemicalInput,
            ItemStack.STREAM_CODEC, r -> r.getOutputDefinition().get(0),
            InfinityOreReprocessingIRecipe::new
    );

    public InfinityOreReprocessingIRecipe(ItemStackIngredient itemInput, ChemicalStackIngredient chemicalInput, ItemStack output) {
        super(itemInput, chemicalInput, output);
    }

    @Override
    @SuppressWarnings("unchecked")
    public RecipeType<InfinityOreReprocessingRecipe> getType() {
        return (RecipeType<InfinityOreReprocessingRecipe>) (RecipeType<?>) MSRecipeType.INFINITY_ORE_REPROCESSING.get();
    }

    @Override
    public RecipeSerializer<InfinityOreReprocessingIRecipe> getSerializer() {
        return MSRecipeSerializers.INFINITY_ORE_REPROCESSING.get();
    }

    @Override
    public boolean matches(FluidRecipeInput input, Level level) {
        return false;
    }
}