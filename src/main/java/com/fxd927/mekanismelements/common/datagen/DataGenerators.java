package com.fxd927.mekanismelements.common.datagen;

import com.fxd927.mekanismelements.common.MekanismElements;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;

@EventBusSubscriber(modid = MekanismElements.MODID, bus = EventBusSubscriber.Bus.MOD)
@SuppressWarnings("removal")
public class DataGenerators {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();

        generator.addProvider(event.includeServer(), new ModRecipeProvider(output, event.getLookupProvider()));
        generator.addProvider(event.includeServer(), new ModLootTableProvider(output, event.getLookupProvider()));
        generator.addProvider(event.includeServer(), new MSBlockTagsProvider(output, event.getLookupProvider(), event.getExistingFileHelper()));
    }
}