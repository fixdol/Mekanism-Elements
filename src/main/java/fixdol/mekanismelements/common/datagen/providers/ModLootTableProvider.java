package fixdol.mekanismelements.common.datagen.providers;

import java.util.concurrent.CompletableFuture;
import java.util.List;
import fixdol.mekanismelements.common.datagen.providers.ModLootTableProvider;
import net.minecraft.data.PackOutput;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import java.util.Set;

public class ModLootTableProvider extends LootTableProvider {

    public ModLootTableProvider(PackOutput output) {
        super(output, Set.of(), List.of(
                new SubProviderEntry(MSBlockLootTables::new, LootContextParamSets.BLOCK)
        ));
    }
}