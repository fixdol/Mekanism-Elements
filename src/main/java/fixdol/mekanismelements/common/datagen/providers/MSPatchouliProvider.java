package fixdol.mekanismelements.common.datagen.providers;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import fixdol.mekanismelements.common.MekanismElements;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class MSPatchouliProvider implements DataProvider {

    public static final String BOOK = "guide";
    private static final String LANG = "en_us";

    private record Machine(String id, String recipe) {}

    private static final List<Machine> MACHINES = List.of(
          new Machine("adsorption_separator", "adsorption_separator"),
          new Machine("air_compressor", "air_compressor"),
          new Machine("seawater_pump", "seawater_pump"),
          new Machine("radiation_irradiator", "radiation_irradiator"),
          new Machine("chemical_demolition_machine", "chemical_demolition_machine"),
          new Machine("infinity_ore_reprocessing", "infinity_ore_reprocessing")
    );

    private record Category(String id, String icon, int sort) {}

    private static final List<Category> CATEGORIES = List.of(
          new Category("machines", MekanismElements.MODID + ":adsorption_separator", 0),
          new Category("materials", MekanismElements.MODID + ":ingot_beryllium", 1),
          new Category("chemicals", MekanismElements.MODID + ":seawater_bucket", 2),
          new Category("medicine", MekanismElements.MODID + ":tablet_iodine", 3),
          new Category("building", MekanismElements.MODID + ":high_quality_concrete", 4)
    );

    private final PackOutput.PathProvider books;
    private final PackOutput.PathProvider bookDefinition;
    private final PackOutput.PathProvider recipes;

    public MSPatchouliProvider(PackOutput output) {
        this.books = output.createPathProvider(PackOutput.Target.RESOURCE_PACK, "patchouli_books");
        this.bookDefinition = output.createPathProvider(PackOutput.Target.DATA_PACK, "patchouli_books");
        this.recipes = output.createPathProvider(PackOutput.Target.DATA_PACK, "recipe");
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cache) {
        List<CompletableFuture<?>> futures = new ArrayList<>();
        futures.add(DataProvider.saveStable(cache, book(),
              bookDefinition.json(ResourceLocation.fromNamespaceAndPath(MekanismElements.MODID, BOOK + "/book"))));
        futures.add(DataProvider.saveStable(cache, guideBookRecipe(),
              recipes.json(ResourceLocation.fromNamespaceAndPath(MekanismElements.MODID, "guide_book"))));

        for (Category category : CATEGORIES) {
            futures.add(save(cache, category(category), LANG + "/categories/" + category.id()));
        }

        for (int i = 0; i < MACHINES.size(); i++) {
            Machine machine = MACHINES.get(i);
            futures.add(save(cache, machineEntry(machine, i), LANG + "/entries/machines/" + machine.id()));
        }

        for (Map.Entry<String, List<String>> group : groupItems().entrySet()) {
            String[] parts = group.getKey().split("/", 2);
            String category = parts[0];
            String entry = parts[1];
            futures.add(save(cache, itemEntry(category, entry, group.getValue()),
                  LANG + "/entries/" + category + "/" + entry));
        }

        return CompletableFuture.allOf(futures.toArray(CompletableFuture[]::new));
    }

    private CompletableFuture<?> save(CachedOutput cache, JsonObject json, String path) {
        return DataProvider.saveStable(cache, json,
              books.json(ResourceLocation.fromNamespaceAndPath(MekanismElements.MODID, BOOK + "/" + path)));
    }

    private static JsonObject guideBookRecipe() {
        JsonObject key = new JsonObject();
        key.add("A", ingredient("minecraft:writable_book"));
        key.add("B", ingredient("mekanism:gauge_dropper"));
        key.add("C", ingredient(MekanismElements.MODID + ":syringe"));

        JsonArray pattern = new JsonArray();
        pattern.add("AB ");
        pattern.add("C  ");
        pattern.add("   ");

        JsonObject components = new JsonObject();
        components.addProperty("patchouli:book", MekanismElements.MODID + ":" + BOOK);

        JsonObject result = new JsonObject();
        result.add("components", components);
        result.addProperty("count", 1);
        result.addProperty("id", "patchouli:guide_book");

        JsonObject recipe = new JsonObject();
        recipe.addProperty("type", "minecraft:crafting_shaped");
        recipe.addProperty("category", "misc");
        recipe.add("key", key);
        recipe.add("pattern", pattern);
        recipe.add("result", result);
        return recipe;
    }

    private static JsonObject ingredient(String item) {
        JsonObject json = new JsonObject();
        json.addProperty("item", item);
        return json;
    }

    private static JsonObject book() {
        JsonObject json = new JsonObject();
        json.addProperty("name", "book." + MekanismElements.MODID + ".guide.name");
        json.addProperty("landing_text", "book." + MekanismElements.MODID + ".guide.landing");
        json.addProperty("book_texture", MekanismElements.MODID + ":textures/gui/book_guide.png");
        json.addProperty("model", MekanismElements.MODID + ":guide_book");
        json.addProperty("text_color", "16191A");
        json.addProperty("header_color", "1E4A10");
        json.addProperty("nameplate_color", "5DBB2B");
        json.addProperty("link_color", "2E7D32");
        json.addProperty("link_hover_color", "7CC93A");
        json.addProperty("progress_bar_color", "5DBB2B");
        json.addProperty("progress_bar_background", "23282C");
        json.addProperty("i18n", true);
        json.addProperty("use_resource_pack", true);
        json.addProperty("version", 1);
        return json;
    }

    private static JsonObject category(Category category) {
        JsonObject json = new JsonObject();
        json.addProperty("name", key("category." + category.id()));
        json.addProperty("description", key("category." + category.id() + ".desc"));
        json.addProperty("icon", category.icon());
        json.addProperty("sortnum", category.sort());
        return json;
    }

    private static JsonObject machineEntry(Machine machine, int sort) {
        String id = MekanismElements.MODID + ":" + machine.id();
        JsonArray pages = new JsonArray();

        JsonObject text = new JsonObject();
        text.addProperty("type", "patchouli:text");
        text.addProperty("text", "description." + MekanismElements.MODID + "." + machine.id());
        pages.add(text);

        JsonObject crafting = new JsonObject();
        crafting.addProperty("type", "patchouli:crafting");
        crafting.addProperty("recipe", MekanismElements.MODID + ":" + machine.recipe());
        crafting.addProperty("title", key("page.crafting"));
        pages.add(crafting);

        JsonObject json = new JsonObject();
        json.addProperty("name", key("entry." + machine.id()));
        json.addProperty("category", MekanismElements.MODID + ":machines");
        json.addProperty("icon", id);
        json.addProperty("sortnum", sort);
        json.add("pages", pages);
        return json;
    }

    private static JsonObject itemEntry(String category, String entry, List<String> items) {
        JsonArray pages = new JsonArray();
        for (String item : items) {
            JsonObject spotlight = new JsonObject();
            spotlight.addProperty("type", "patchouli:spotlight");
            spotlight.addProperty("item", MekanismElements.MODID + ":" + item);
            spotlight.addProperty("title", key("entry." + entry));
            spotlight.addProperty("text", nameKey(item));
            spotlight.addProperty("link_recipe", true);
            pages.add(spotlight);
        }

        JsonObject json = new JsonObject();
        json.addProperty("name", key("entry." + entry));
        json.addProperty("category", MekanismElements.MODID + ":" + category);
        json.addProperty("icon", MekanismElements.MODID + ":" + items.get(0));
        json.add("pages", pages);
        return json;
    }

    /**
     * Buckets a registered item into "category/entry" so every item ends up with its own page.
     */
    private static Map<String, List<String>> groupItems() {
        Map<String, List<String>> groups = new LinkedHashMap<>();
        for (Item item : BuiltInRegistries.ITEM) {
            ResourceLocation id = BuiltInRegistries.ITEM.getKey(item);
            if (!MekanismElements.MODID.equals(id.getNamespace())) {
                continue;
            }
            String path = id.getPath();
            if (isMachine(path)) {
                continue;
            }
            groups.computeIfAbsent(groupOf(path, item), k -> new ArrayList<>()).add(path);
        }
        return groups;
    }

    private static boolean isMachine(String path) {
        return MACHINES.stream().anyMatch(m -> m.id().equals(path));
    }

    private static String groupOf(String path, Item item) {
        if (path.endsWith("_bucket")) {
            return "chemicals/buckets";
        }
        if (path.startsWith("syringe")) {
            return "medicine/syringes";
        }
        if (path.startsWith("tablet_")) {
            return "medicine/tablets";
        }
        if (path.contains("high_quality_concrete")) {
            if (path.endsWith("_slab")) {
                return "building/concrete_slabs";
            }
            if (path.endsWith("_stairs")) {
                return "building/concrete_stairs";
            }
            if (path.startsWith("powder_")) {
                return "building/concrete_powder";
            }
            if (path.startsWith("clump_")) {
                return "building/concrete_clumps";
            }
            return "building/concrete";
        }
        if (path.startsWith("high_performance_adsorbent")) {
            return "materials/adsorbents";
        }
        if (path.startsWith("dust_")) {
            return "materials/dusts";
        }
        if (path.startsWith("ingot_")) {
            return "materials/ingots";
        }
        if (path.startsWith("pellet_")) {
            return "materials/pellets";
        }
        return item instanceof BlockItem ? "building/misc_blocks" : "materials/misc";
    }

    private static String nameKey(String path) {
        return (BuiltInRegistries.ITEM.get(ResourceLocation.fromNamespaceAndPath(MekanismElements.MODID, path)) instanceof BlockItem
              ? "block." : "item.") + MekanismElements.MODID + "." + path;
    }

    private static String key(String suffix) {
        return "book." + MekanismElements.MODID + "." + suffix;
    }

    @Override
    public String getName() {
        return "Patchouli Book: " + MekanismElements.MODID;
    }
}
