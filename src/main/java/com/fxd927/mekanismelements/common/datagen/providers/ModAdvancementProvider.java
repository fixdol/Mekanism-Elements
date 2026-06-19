package com.fxd927.mekanismelements.common.datagen.providers;

import com.fxd927.mekanismelements.common.MekanismElements;
import com.fxd927.mekanismelements.common.registries.MSBlocks;
import com.fxd927.mekanismelements.common.registries.MSItems;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.data.AdvancementProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.function.Consumer;

public class ModAdvancementProvider implements AdvancementProvider.AdvancementGenerator {

    @Override
    public void generate(HolderLookup.Provider registries, Consumer<AdvancementHolder> saver, ExistingFileHelper existingFileHelper) {

        // neutron_source.json -> parent: mekanism:plutonium
        AdvancementHolder neutronSource = Advancement.Builder.advancement()
                .parent(ResourceLocation.fromNamespaceAndPath("mekanism", "plutonium"))
                .display(
                        MSItems.NEUTRON_SOURCE_PELLET.get(),
                        Component.translatable("advancements.mekanismelements.neutron_source.title"),
                        Component.translatable("advancements.mekanismelements.neutron_source.description"),
                        null,
                        AdvancementType.TASK,
                        true,
                        true,
                        false
                )
                .addCriterion("pellet_neutron_source", InventoryChangeTrigger.TriggerInstance.hasItems(MSItems.NEUTRON_SOURCE_PELLET.get()))
                .save(saver, ResourceLocation.fromNamespaceAndPath(MekanismElements.MODID, "neutron_source").toString());

        // californium.json -> parent: mekanism:plutonium
        /*
        AdvancementHolder californium = Advancement.Builder.advancement()
                .parent(ResourceLocation.fromNamespaceAndPath("mekanism", "plutonium"))
                .display(
                        MSItems.REFINED_CALIFORNIUM_INGOT.get(),
                        Component.translatable("advancements.mekanismelements.californium.title"),
                        Component.translatable("advancements.mekanismelements.californium.description"),
                        null,
                        AdvancementType.TASK,
                        true,
                        true,
                        false
                )
                .addCriterion("pellet_neutron_source", InventoryChangeTrigger.TriggerInstance.hasItems(MSItems.REFINED_CALIFORNIUM_INGOT.get()))
                .save(saver, ResourceLocation.fromNamespaceAndPath(MekanismElements.MODID, "californium").toString());
        */

        // radiation_irradiator.json -> parent: mekanismelements:neutron_source
        Advancement.Builder.advancement()
                .parent(neutronSource)
                .display(
                        MSBlocks.RADIATION_IRRADIATOR.get(),
                        Component.translatable("advancements.mekanismelements.radiation_irradiator.title"),
                        Component.translatable("advancements.mekanismelements.radiation_irradiator.description"),
                        null,
                        AdvancementType.TASK,
                        true,
                        true,
                        false
                )
                .addCriterion("pellet_neutron_source", InventoryChangeTrigger.TriggerInstance.hasItems(MSBlocks.RADIATION_IRRADIATOR.get()))
                .save(saver, ResourceLocation.fromNamespaceAndPath(MekanismElements.MODID, "radiation_irradiator").toString());

        // seawater_pump.json -> parent: mekanism:pump
        Advancement.Builder.advancement()
                .parent(ResourceLocation.fromNamespaceAndPath("mekanism", "pump"))
                .display(
                        MSBlocks.SEAWATER_PUMP.get(),
                        Component.translatable("advancements.mekanismelements.seawater_pump.title"),
                        Component.translatable("advancements.mekanismelements.seawater_pump.description"),
                        null,
                        AdvancementType.TASK,
                        true,
                        true,
                        false
                )
                .addCriterion("electric_pump", InventoryChangeTrigger.TriggerInstance.hasItems(MSBlocks.SEAWATER_PUMP.get()))
                .save(saver, ResourceLocation.fromNamespaceAndPath(MekanismElements.MODID, "seawater_pump").toString());
    }
}