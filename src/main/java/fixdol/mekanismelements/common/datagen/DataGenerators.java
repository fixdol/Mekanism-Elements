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
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.advancements.AdvancementProvider;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.List;

@EventBusSubscriber(modid = MekanismElements.MODID)
public class DataGenerators {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent.Client event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();

        generator.addProvider(true, new ModRecipeProvider.ModRunner(output, event.getLookupProvider()));
        generator.addProvider(true, new ModLootTableProvider(output, event.getLookupProvider()));
        generator.addProvider(true, new MSBlockTagsProvider(output, event.getLookupProvider()));
        generator.addProvider(true, new MSItemTagsProvider(output, event.getLookupProvider()));
        generator.addProvider(true, new MSFluidTagsProvider(output, event.getLookupProvider()));
        generator.addProvider(true, new ChemLibRecipeProvider(output));
        generator.addProvider(true, new AdvancementProvider(output, event.getLookupProvider(), List.of(new ModAdvancementProvider())));
        generator.addProvider(true, new ModDataMapProvider(output, event.getLookupProvider()));

        // Models and blockstates
        generator.addProvider(true, new MSBlockStateProvider(output));
        generator.addProvider(true, new MSPatchouliProvider(output));

        // Language Generation
        generator.addProvider(true, new LanguageGenerator(output));
        generator.addProvider(true, new LanguageGenerator.ESES(output));
        generator.addProvider(true, new LanguageGenerator.ESES(output, "es_ar"));
        generator.addProvider(true, new LanguageGenerator.ESES(output, "es_mx"));
        generator.addProvider(true, new LanguageGenerator.JAJP(output));
        generator.addProvider(true, new LanguageGenerator.ZHCN(output));
        generator.addProvider(true, new LanguageGenerator.RURU(output));
        generator.addProvider(true, new LanguageGenerator.RPR(output));
    }
}
