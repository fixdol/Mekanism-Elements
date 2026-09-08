package fixdol.mekanismelements.common.datagen;

import fixdol.mekanismelements.common.MekanismElements;
import fixdol.mekanismelements.common.datagen.providers.LanguageGenerator;
import fixdol.mekanismelements.common.datagen.providers.ModAdvancementProvider;
import fixdol.mekanismelements.common.datagen.providers.ModDataMapProvider;
import fixdol.mekanismelements.common.datagen.providers.ModLootTableProvider;
import fixdol.mekanismelements.common.datagen.providers.ModRecipeProvider;
import fixdol.mekanismelements.common.datagen.providers.ChemLibRecipeProvider;
import fixdol.mekanismelements.common.datagen.providers.MSBlockStateProvider;
import fixdol.mekanismelements.common.datagen.providers.MSFluidTagsProvider;
import fixdol.mekanismelements.common.datagen.providers.MSItemTagsProvider;
import fixdol.mekanismelements.common.datagen.providers.MSPatchouliProvider;
import fixdol.mekanismelements.common.datagen.providers.MSBlockTagsProvider;
import fixdol.mekanismelements.common.datagen.providers.MSItemModelProvider;
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
        MSBlockTagsProvider blockTags = new MSBlockTagsProvider(output, event.getLookupProvider(), existingFileHelper);
        generator.addProvider(event.includeServer(), blockTags);
        generator.addProvider(event.includeServer(), new MSItemTagsProvider(output, event.getLookupProvider(), blockTags.contentsGetter(), existingFileHelper));
        generator.addProvider(event.includeServer(), new MSFluidTagsProvider(output, event.getLookupProvider(), existingFileHelper));
        generator.addProvider(event.includeServer(), new ChemLibRecipeProvider(output));
        generator.addProvider(event.includeServer(), new AdvancementProvider(output, event.getLookupProvider(), existingFileHelper, List.of(new ModAdvancementProvider())));
        generator.addProvider(event.includeServer(), new ModDataMapProvider(output, event.getLookupProvider()));

        // Models and blockstates
        generator.addProvider(event.includeClient(), new MSBlockStateProvider(output, existingFileHelper));
        generator.addProvider(event.includeClient(), new MSItemModelProvider(output, existingFileHelper));
        generator.addProvider(event.includeClient() || event.includeServer(), new MSPatchouliProvider(output));

        // Language Generation
        generator.addProvider(event.includeClient(), new LanguageGenerator(output));
        generator.addProvider(event.includeClient(), new LanguageGenerator.ESES(output));
        generator.addProvider(event.includeClient(), new LanguageGenerator.ESES(output, "es_ar"));
        generator.addProvider(event.includeClient(), new LanguageGenerator.ESES(output, "es_mx"));
        generator.addProvider(event.includeClient(), new LanguageGenerator.JAJP(output));
        generator.addProvider(event.includeClient(), new LanguageGenerator.ZHCN(output));
        generator.addProvider(event.includeClient(), new LanguageGenerator.RURU(output));
        generator.addProvider(event.includeClient(), new LanguageGenerator.RPR(output));
    }
}