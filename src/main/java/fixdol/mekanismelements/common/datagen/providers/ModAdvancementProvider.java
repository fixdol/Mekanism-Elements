package fixdol.mekanismelements.common.datagen.providers;

import net.minecraft.advancements.Advancement;
import net.minecraft.network.chat.Component;
import net.minecraft.core.HolderLookup;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import fixdol.mekanismelements.common.registries.MSItems;
import fixdol.mekanismelements.common.MekanismElements;
import fixdol.mekanismelements.common.datagen.providers.ModAdvancementProvider;
import net.minecraft.resources.ResourceLocation;

import fixdol.mekanismelements.common.registries.MSBlocks;
import net.minecraft.advancements.FrameType;
import net.minecraftforge.common.data.ForgeAdvancementProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.function.Consumer;

public class ModAdvancementProvider implements ForgeAdvancementProvider.AdvancementGenerator {

    @Override
    public void generate(HolderLookup.Provider registries, Consumer<Advancement> saver, ExistingFileHelper existingFileHelper) {

        // neutron_source.json -> parent: mekanism:plutonium
        Advancement neutronSource = Advancement.Builder.advancement()
                .parent(new ResourceLocation("mekanism", "plutonium"))
                .display(
                        MSItems.NEUTRON_SOURCE_PELLET.get(),
                        Component.translatable("advancements.mekanismelements.neutron_source.title"),
                        Component.translatable("advancements.mekanismelements.neutron_source.description"),
                        null,
                        FrameType.TASK,
                        true,
                        true,
                        false
                )
                .addCriterion("pellet_neutron_source", InventoryChangeTrigger.TriggerInstance.hasItems(MSItems.NEUTRON_SOURCE_PELLET.get()))
                .save(saver, new ResourceLocation(MekanismElements.MODID, "neutron_source"), existingFileHelper);

        // californium.json -> parent: mekanism:plutonium
        /*
        Advancement californium = Advancement.Builder.advancement()
                .parent(new ResourceLocation("mekanism", "plutonium"))
                .display(
                        MSItems.REFINED_CALIFORNIUM_INGOT.get(),
                        Component.translatable("advancements.mekanismelements.californium.title"),
                        Component.translatable("advancements.mekanismelements.californium.description"),
                        null,
                        FrameType.TASK,
                        true,
                        true,
                        false
                )
                .addCriterion("pellet_neutron_source", InventoryChangeTrigger.TriggerInstance.hasItems(MSItems.REFINED_CALIFORNIUM_INGOT.get()))
                .save(saver, new ResourceLocation(MekanismElements.MODID, "californium"), existingFileHelper);
        */

        // radiation_irradiator.json -> parent: mekanismelements:neutron_source
        Advancement.Builder.advancement()
                .parent(neutronSource)
                .display(
                        MSBlocks.RADIATION_IRRADIATOR.getBlock(),
                        Component.translatable("advancements.mekanismelements.radiation_irradiator.title"),
                        Component.translatable("advancements.mekanismelements.radiation_irradiator.description"),
                        null,
                        FrameType.TASK,
                        true,
                        true,
                        false
                )
                .addCriterion("pellet_neutron_source", InventoryChangeTrigger.TriggerInstance.hasItems(MSBlocks.RADIATION_IRRADIATOR.getBlock()))
                .save(saver, new ResourceLocation(MekanismElements.MODID, "radiation_irradiator"), existingFileHelper);

        // seawater_pump.json -> parent: mekanism:pump
        Advancement.Builder.advancement()
                .parent(new ResourceLocation("mekanism", "pump"))
                .display(
                        MSBlocks.SEAWATER_PUMP.getBlock(),
                        Component.translatable("advancements.mekanismelements.seawater_pump.title"),
                        Component.translatable("advancements.mekanismelements.seawater_pump.description"),
                        null,
                        FrameType.TASK,
                        true,
                        true,
                        false
                )
                .addCriterion("electric_pump", InventoryChangeTrigger.TriggerInstance.hasItems(MSBlocks.SEAWATER_PUMP.getBlock()))
                .save(saver, new ResourceLocation(MekanismElements.MODID, "seawater_pump"), existingFileHelper);
    }
}