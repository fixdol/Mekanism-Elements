package com.fxd927.mekanismelements.common.datagen;

import com.fxd927.mekanismelements.common.MekanismElements;
import com.fxd927.mekanismelements.common.datagen.providers.ModAdvancementProvider;
import com.fxd927.mekanismelements.common.datagen.providers.ModLootTableProvider;
import com.fxd927.mekanismelements.common.datagen.providers.ModRecipeProvider;
import com.fxd927.mekanismelements.common.datagen.providers.MSBlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.AdvancementProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.List;

@EventBusSubscriber(modid = MekanismElements.MODID, bus = EventBusSubscriber.Bus.MOD)
public class DataGenerators {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

        generator.addProvider(event.includeServer(), new ModRecipeProvider(output, event.getLookupProvider()));
        generator.addProvider(event.includeServer(), new ModLootTableProvider(output, event.getLookupProvider()));
        generator.addProvider(event.includeServer(), new MSBlockTagsProvider(output, event.getLookupProvider(), existingFileHelper));
        generator.addProvider(event.includeServer(), new AdvancementProvider(output, event.getLookupProvider(), existingFileHelper, List.of(new ModAdvancementProvider())));
    }
}