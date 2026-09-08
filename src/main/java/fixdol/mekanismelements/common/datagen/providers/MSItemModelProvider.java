package fixdol.mekanismelements.common.datagen.providers;

import fixdol.mekanismelements.common.MekanismElements;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.List;
import java.util.Set;

public class MSItemModelProvider extends ItemModelProvider {

    private static final List<String> SYRINGES = List.of("syringe_anesthetic", "syringe_flame_retardant", "syringe_levitation");
    private static final int SYRINGE_USES = 4;

    private static final Set<String> HANDLED_SEPARATELY = Set.copyOf(SYRINGES);

    private static final List<String> UNREGISTERED = List.of(
          "aqua_regia_bucket", "concentrated_seawater_bucket", "dark_red_dye", "dust_yttrium", "ethanol_bucket",
          "excipient", "ingot_refined_californium", "lactose_bucket", "methanol_bucket", "methylamine_bucket",
          "methylammonium_lead_iodide_bucket", "netherite_acid_bucket", "pellet_high-density_neutron_source",
          "tablet_anesthetic", "tablet_fire_resistance", "tablet_poison", "tablet_sleep_inducing",
          "tetrodotoxin_bucket", "unstable_californium_mixture", "whey_bucket",
          "guide_book");

    public MSItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, MekanismElements.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        for (String syringe : SYRINGES) {
            syringe(syringe);
        }
        for (String name : UNREGISTERED) {
            generated(name, modLoc("item/" + name));
        }
        for (Item item : BuiltInRegistries.ITEM) {
            ResourceLocation id = BuiltInRegistries.ITEM.getKey(item);
            if (!MekanismElements.MODID.equals(id.getNamespace()) || HANDLED_SEPARATELY.contains(id.getPath())) {
                continue;
            }
            if (item instanceof BlockItem) {
                continue;
            }
            basicItem(item);
        }
    }

    private void syringe(String name) {
        ItemModelBuilder base = generated(name, modLoc("item/" + name + "_0"));
        for (int uses = 1; uses <= SYRINGE_USES; uses++) {
            String usedName = name + "_" + uses;
            generated(usedName, modLoc("item/" + usedName));
            base.override()
                  .predicate(modLoc("use_count"), uses)
                  .model(getExistingFile(modLoc("item/" + usedName)))
                  .end();
        }
    }

    private ItemModelBuilder generated(String name, ResourceLocation texture) {
        return singleTexture(name, mcLoc("item/generated"), "layer0", texture);
    }
}
