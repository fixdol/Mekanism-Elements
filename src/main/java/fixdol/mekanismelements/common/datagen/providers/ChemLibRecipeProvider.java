package fixdol.mekanismelements.common.datagen.providers;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import fixdol.mekanismelements.common.MekanismElements;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class ChemLibRecipeProvider implements DataProvider {

    private static final String CHEMLIB = "chemlib";
    private static final long AMOUNT = 1000;

    private static final Map<String, String> CRYSTALLIZING = new LinkedHashMap<>();

    static {
        CRYSTALLIZING.put("americium", "americium");
        CRYSTALLIZING.put("ammonia", "ammonia");
        CRYSTALLIZING.put("bromine", "bromine");
        CRYSTALLIZING.put("helium", "helium");
        CRYSTALLIZING.put("iodine", "iodine");
        CRYSTALLIZING.put("nitric_acid", "nitric_acid");
        CRYSTALLIZING.put("nitric_oxide", "nitric_oxide");
        CRYSTALLIZING.put("nitrogen", "nitrogen");
        CRYSTALLIZING.put("nitrogen_dioxide", "nitrogen_dioxide");
        CRYSTALLIZING.put("potassium_chloride", "potassium_chloride_dust");
        CRYSTALLIZING.put("potassium_hydroxide", "potassium_hydroxide_dust");
        CRYSTALLIZING.put("strontium", "strontium_dust");
        CRYSTALLIZING.put("xenon", "xenon");
        CRYSTALLIZING.put("yttrium", "yttrium_dust");
    }

    private final PackOutput.PathProvider recipePath;

    public ChemLibRecipeProvider(PackOutput output) {
        this.recipePath = output.createPathProvider(PackOutput.Target.DATA_PACK, "recipe");
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cache) {
        CompletableFuture<?>[] futures = new CompletableFuture<?>[CRYSTALLIZING.size()];
        int i = 0;
        for (Map.Entry<String, String> entry : CRYSTALLIZING.entrySet()) {
            ResourceLocation id = ResourceLocation.fromNamespaceAndPath(CHEMLIB, "crystallizing/" + entry.getValue());
            futures[i++] = DataProvider.saveStable(cache, crystallizing(entry.getKey(), entry.getValue()), recipePath.json(id));
        }
        return CompletableFuture.allOf(futures);
    }

    private static JsonObject crystallizing(String chemical, String result) {
        JsonObject condition = new JsonObject();
        condition.addProperty("type", "neoforge:mod_loaded");
        condition.addProperty("modid", CHEMLIB);
        JsonArray conditions = new JsonArray();
        conditions.add(condition);

        JsonObject input = new JsonObject();
        input.addProperty("amount", AMOUNT);
        input.addProperty("chemical", ResourceLocation.fromNamespaceAndPath(MekanismElements.MODID, chemical).toString());

        JsonObject output = new JsonObject();
        output.addProperty("id", ResourceLocation.fromNamespaceAndPath(CHEMLIB, result).toString());

        JsonObject recipe = new JsonObject();
        recipe.add("neoforge:conditions", conditions);
        recipe.addProperty("type", "mekanism:crystallizing");
        recipe.add("input", input);
        recipe.add("output", output);
        return recipe;
    }

    @Override
    public String getName() {
        return "ChemLib Recipes: " + MekanismElements.MODID;
    }
}
