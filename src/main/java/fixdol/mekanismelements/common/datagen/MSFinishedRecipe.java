package fixdol.mekanismelements.common.datagen;

import com.google.gson.JsonObject;
import net.minecraft.advancements.Advancement;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public class MSFinishedRecipe implements FinishedRecipe {

    private final ResourceLocation id;
    private final Supplier<RecipeSerializer<?>> serializer;
    private final JsonObject json;

    public MSFinishedRecipe(ResourceLocation id, Supplier<RecipeSerializer<?>> serializer, JsonObject json) {
        this.id = id;
        this.serializer = serializer;
        this.json = json;
    }

    @Override
    public void serializeRecipeData(JsonObject output) {
        for (String key : json.keySet()) {
            output.add(key, json.get(key));
        }
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getType() {
        return serializer.get();
    }

    @Nullable
    @Override
    public JsonObject serializeAdvancement() {
        return null;
    }

    @Nullable
    @Override
    public ResourceLocation getAdvancementId() {
        return null;
    }
}
