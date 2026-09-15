package fixdol.mekanismelements.common.registries;

import mekanism.common.util.ChemicalUtil;
import fixdol.mekanismelements.common.registries.MSBlocks;
import fixdol.mekanismelements.common.registries.MSFluids;
import fixdol.mekanismelements.common.registries.MSItems;
import fixdol.mekanismelements.common.MSLang;
import mekanism.api.MekanismAPI;
import mekanism.common.registries.MekanismBlocks;
import fixdol.mekanismelements.common.MekanismElements;
import net.minecraft.resources.ResourceLocation;
import mekanism.common.registration.WrappedRegistryObject;

import mekanism.api.chemical.gas.Gas;
import mekanism.common.registration.impl.CreativeTabDeferredRegister;
import mekanism.common.registries.MekanismCreativeTabs;
import mekanism.generators.common.MekanismGenerators;
import net.minecraft.world.item.CreativeModeTab;
import vazkii.patchouli.api.PatchouliAPI;

public class MSCreativeTab {
    public static final CreativeTabDeferredRegister CREATIVE_TABS = new CreativeTabDeferredRegister(MekanismElements.MODID);

    public static final WrappedRegistryObject<CreativeModeTab> MEKANISM_SCIENCE = CREATIVE_TABS.registerMain(MSLang.MEKANISM_SCIENCE, MSItems.NEUTRON_SOURCE_PELLET, builder ->
              builder.withTabsBefore(MekanismCreativeTabs.MEKANISM.key())
                      .displayItems((displayParameters, output) -> {
                          CreativeTabDeferredRegister.addToDisplay(MSItems.ITEMS, output);
                          CreativeTabDeferredRegister.addToDisplay(MSBlocks.BLOCKS, output);
                          CreativeTabDeferredRegister.addToDisplay(MSFluids.FLUIDS, output);
                          CreativeTabDeferredRegister.addToDisplay(MSItems.BUILDING_ITEMS, output);
                          CreativeTabDeferredRegister.addToDisplay(MSBlocks.BUILDING_BLOCKS, output);
                          // Add filled chemical tanks for our chemicals
                          for (Gas gas : MekanismAPI.gasRegistry()) {
                              if (gas.isEmptyType() || gas.isHidden()) {
                                  continue;
                              }
                              if (MekanismElements.MODID.equals(gas.getRegistryName().getNamespace())) {
                                  output.accept(ChemicalUtil.getFilledVariant(MekanismBlocks.CREATIVE_CHEMICAL_TANK.getItemStack(), Long.MAX_VALUE, gas));
                              }
                          }
                          output.accept(PatchouliAPI.get().getBookStack(new ResourceLocation(MekanismElements.MODID, "guide")));
                      })
    );
}

