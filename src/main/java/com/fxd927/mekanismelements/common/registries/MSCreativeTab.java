package com.fxd927.mekanismelements.common.registries;

import com.fxd927.mekanismelements.common.MSLang;
import com.fxd927.mekanismelements.common.MekanismElements;
import mekanism.common.registration.impl.CreativeTabDeferredRegister;
import mekanism.common.registration.impl.CreativeTabRegistryObject;
import mekanism.common.registries.MekanismCreativeTabs;

public class MSCreativeTab {
    public static final CreativeTabDeferredRegister CREATIVE_TABS = new CreativeTabDeferredRegister(MekanismElements.MODID);

    public static final CreativeTabRegistryObject MEKANISM_SCIENCE = CREATIVE_TABS.registerMain(MSLang.MEKANISM_SCIENCE, MSItems.NEUTRON_SOURCE_PELLET, builder ->
              builder.withBackgroundLocation(MekanismElements.rl("textures/gui/creative_tab.png"))
                      .withSearchBar(70)
                      .withTabsBefore(MekanismCreativeTabs.MEKANISM.key())
                      .displayItems((displayParameters, output) -> {
                          CreativeTabDeferredRegister.addToDisplay(MSItems.ITEMS, output);
                          CreativeTabDeferredRegister.addToDisplay(MSBlocks.BLOCKS, output);
                          CreativeTabDeferredRegister.addToDisplay(MSFluids.FLUIDS, output);
                          CreativeTabDeferredRegister.addToDisplay(MSItems.BUILDING_ITEMS, output);
                          CreativeTabDeferredRegister.addToDisplay(MSBlocks.BUILDING_BLOCKS, output);
                      })
    );
}
