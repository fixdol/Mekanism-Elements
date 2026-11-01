package fixdol.mekanismelements.common.datagen.providers;

import fixdol.mekanismelements.common.MekanismElements;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.model.ItemModelUtils;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.renderer.item.ClientItem;
import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.client.renderer.item.RangeSelectItemModel;
import net.minecraft.client.renderer.item.properties.numeric.CustomModelDataProperty;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * Item models of the non-block items. Not a provider of its own: a mod can only register one ModelProvider, so
 * {@link MSBlockStateProvider} calls this.
 */
public final class MSItemModelProvider {

    private static final List<String> SYRINGES = List.of("syringe_anesthetic", "syringe_flame_retardant", "syringe_levitation");
    private static final int SYRINGE_USES = 4;

    private MSItemModelProvider() {
    }

    static void register(ItemModelGenerators itemModels, Stream<? extends Holder<Item>> knownItems) {
        for (String syringe : SYRINGES) {
            syringe(itemModels, syringe);
        }
        guideBook(itemModels);
        knownItems.map(Holder::value).forEach(item -> {
            Identifier id = BuiltInRegistries.ITEM.getKey(item);
            if (!SYRINGES.contains(id.getPath())) {
                itemModels.generateFlatItem(item, ModelTemplates.FLAT_ITEM);
            }
        });
    }

    /**
     * Patchouli renders its book through ModelManager#getItemModel(book.model), so the client item has to exist under
     * our namespace even though the item itself (patchouli:guide_book) is not ours.
     */
    private static void guideBook(ItemModelGenerators itemModels) {
        Identifier texture = MekanismElements.rl("item/guide_book");
        Identifier model = ModelTemplates.FLAT_ITEM.create(texture, TextureMapping.layer0(new Material(texture)), itemModels.modelOutput);
        itemModels.itemModelOutput.register(MekanismElements.rl("guide_book"),
              new ClientItem(ItemModelUtils.plainModel(model), ClientItem.Properties.DEFAULT));
    }

    private static void syringe(ItemModelGenerators itemModels, String name) {
        Item item = BuiltInRegistries.ITEM.getValue(MekanismElements.rl(name));
        ItemModel.Unbaked fallback = null;
        List<RangeSelectItemModel.Entry> entries = new ArrayList<>();
        for (int uses = 0; uses <= SYRINGE_USES; uses++) {
            Identifier model = ModelTemplates.FLAT_ITEM.create(MekanismElements.rl("item/" + name + "_" + uses),
                  TextureMapping.layer0(new Material(MekanismElements.rl("item/" + name + "_" + uses))), itemModels.modelOutput);
            ItemModel.Unbaked unbaked = ItemModelUtils.plainModel(model);
            if (uses == 0) {
                fallback = unbaked;
            } else {
                entries.add(ItemModelUtils.override(unbaked, uses));
            }
        }
        itemModels.itemModelOutput.accept(item, ItemModelUtils.rangeSelect(new CustomModelDataProperty(0), fallback, entries));
    }
}
