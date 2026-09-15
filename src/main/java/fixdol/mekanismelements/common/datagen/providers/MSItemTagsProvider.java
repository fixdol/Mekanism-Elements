package fixdol.mekanismelements.common.datagen.providers;

import net.minecraft.world.level.block.Block;
import java.util.concurrent.CompletableFuture;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.Item;
import fixdol.mekanismelements.common.datagen.providers.MSItemTagsProvider;
import fixdol.mekanismelements.common.registries.MSItems;
import fixdol.mekanismelements.common.MekanismElements;
import net.minecraft.data.PackOutput;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;

import net.minecraft.data.tags.ItemTagsProvider;


public class MSItemTagsProvider extends ItemTagsProvider {

    public MSItemTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider,
          CompletableFuture<TagLookup<Block>> blockTags, ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, blockTags, MekanismElements.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(commonTag("dusts/beryllium")).add(MSItems.DUST_BERYLLIUM.get());
        tag(commonTag("dusts/calcium_oxide")).add(MSItems.DUST_CALCIUM_OXIDE.get());
        tag(commonTag("ingots/beryllium")).add(MSItems.INGOT_BERYLLIUM.get());
    }

    private static TagKey<Item> commonTag(String path) {
        return TagKey.create(Registries.ITEM, new ResourceLocation("forge", path));
    }
}
