package com.fxd927.mekanismelements.common.registries;

import com.fxd927.mekanismelements.common.MSLang;
import com.fxd927.mekanismelements.common.MekanismElements;
import mekanism.api.MekanismAPI;
import mekanism.api.MekanismAPITags;
import mekanism.common.registration.MekanismDeferredHolder;
import mekanism.common.registration.impl.CreativeTabDeferredRegister;
import mekanism.common.registries.MekanismBlocks;
import mekanism.common.registries.MekanismCreativeTabs;
import mekanism.common.util.ChemicalUtil;
import mekanism.generators.common.MekanismGenerators;
import net.minecraft.world.item.CreativeModeTab;

public class MSCreativeTab {
    public static final CreativeTabDeferredRegister CREATIVE_TABS = new CreativeTabDeferredRegister(MekanismElements.MODID);

    public static final MekanismDeferredHolder<CreativeModeTab, CreativeModeTab> MEKANISM_SCIENCE = CREATIVE_TABS.registerMain(MSLang.MEKANISM_SCIENCE, MSItems.NEUTRON_SOURCE_PELLET, builder ->
              builder.withSearchBar(65)
                      .backgroundTexture(MekanismElements.rl("textures/gui/creative_tab.png"))
                      .withTabsBefore(MekanismCreativeTabs.MEKANISM.getId())
                      .displayItems((displayParameters, output) -> {
                          CreativeTabDeferredRegister.addToDisplay(MSItems.ITEMS, output);
                          CreativeTabDeferredRegister.addToDisplay(MSBlocks.BLOCKS, output);
                          CreativeTabDeferredRegister.addToDisplay(MSFluids.FLUIDS, output);
                          CreativeTabDeferredRegister.addToDisplay(MSItems.BUILDING_ITEMS, output);
                          CreativeTabDeferredRegister.addToDisplay(MSBlocks.BUILDING_BLOCKS, output);
                          // Add filled chemical tanks for our chemicals
                          displayParameters.holders().lookupOrThrow(MekanismAPI.CHEMICAL_REGISTRY_NAME)
                                  .listElements()
                                  .filter(holder -> {
                                      String namespace = holder.key().location().getNamespace();
                                      return namespace.equals(MekanismElements.MODID) && 
                                             !holder.is(MekanismAPITags.Chemicals.HIDDEN_FROM_RECIPE_VIEWERS) && 
                                             !holder.is(MekanismAPI.EMPTY_CHEMICAL_KEY);
                                  })
                                  .forEach(holder -> output.accept(ChemicalUtil.getFilledVariant(MekanismBlocks.CREATIVE_CHEMICAL_TANK.getItemHolder(), holder)));
                      })
    );
}

