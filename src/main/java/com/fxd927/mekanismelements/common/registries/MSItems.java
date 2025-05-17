package com.fxd927.mekanismelements.common.registries;

import com.fxd927.mekanismelements.common.MekanismElements;
import com.fxd927.mekanismelements.common.item.IodineTablet;
import com.fxd927.mekanismelements.common.item.NeutronSourcePellet;
import com.fxd927.mekanismelements.common.item.syringe.AnestheticSyringe;
import com.fxd927.mekanismelements.common.item.syringe.FlameRetardantSyringe;
import com.fxd927.mekanismelements.common.item.syringe.LevitationSyringe;
import mekanism.api.text.EnumColor;
import mekanism.common.registration.impl.ItemDeferredRegister;
import mekanism.common.registration.impl.ItemRegistryObject;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;

public class MSItems {
    public static final ItemDeferredRegister ITEMS = new ItemDeferredRegister(MekanismElements.MODID);
    public static final ItemDeferredRegister BUILDING_ITEMS = new ItemDeferredRegister(MekanismElements.MODID);

    public static final ItemRegistryObject<Item> SYRINGE;
    public static final ItemRegistryObject<Item> ANESTHETIC_SYRINGE;
    public static final ItemRegistryObject<Item> FLAME_RETARDANT_SYRINGE;
    public static final ItemRegistryObject<Item> LEVITATION_SYRINGE;
    public static final ItemRegistryObject<Item> HIGH_PERFORMANCE_ADSORBENT;
    public static final ItemRegistryObject<Item> HIGH_PERFORMANCE_ADSORBENT_BERYLLIUM;
    public static final ItemRegistryObject<Item> HIGH_PERFORMANCE_ADSORBENT_COPPER;
    public static final ItemRegistryObject<Item> HIGH_PERFORMANCE_ADSORBENT_GOLD;
    public static final ItemRegistryObject<Item> HIGH_PERFORMANCE_ADSORBENT_IRON;
    public static final ItemRegistryObject<Item> HIGH_PERFORMANCE_ADSORBENT_LEAD;
    public static final ItemRegistryObject<Item> HIGH_PERFORMANCE_ADSORBENT_OSMIUM;
    public static final ItemRegistryObject<Item> HIGH_PERFORMANCE_ADSORBENT_TIN;
    public static final ItemRegistryObject<Item> HIGH_PERFORMANCE_ADSORBENT_URANIUM;

    public static final ItemRegistryObject<Item> NEUTRON_SOURCE_PELLET;
    //public static final ItemRegistryObject<Item> DUST_BERYLLIUM;
    public static final ItemRegistryObject<Item> DUST_CALCIUM_OXIDE;
    //public static final ItemRegistryObject<Item> INGOT_BERYLLIUM;
    //public static final ItemRegistryObject<Item> DUST_YTTRIUM = ITEMS.register("dust_yttrium");
    //public static final ItemRegistryObject<Item> UNSTABLE_CALIFORNIUM_MIXTURE;
    //public static final ItemRegistryObject<Item> REFINED_CALIFORNIUM_INGOT;
    public static final ItemRegistryObject<Item> TABLET_IODINE;

    public static final ItemRegistryObject<Item> HIGH_QUALITY_CONCRETE_CLUMP;
    public static final ItemRegistryObject<Item> HIGH_QUALITY_CONCRETE_CLUMP_AQUA;
    public static final ItemRegistryObject<Item> HIGH_QUALITY_CONCRETE_CLUMP_BLACK;
    public static final ItemRegistryObject<Item> HIGH_QUALITY_CONCRETE_CLUMP_BLUE;
    public static final ItemRegistryObject<Item> HIGH_QUALITY_CONCRETE_CLUMP_BROWN;
    public static final ItemRegistryObject<Item> HIGH_QUALITY_CONCRETE_CLUMP_CYAN;
    public static final ItemRegistryObject<Item> HIGH_QUALITY_CONCRETE_CLUMP_DARK_RED;
    public static final ItemRegistryObject<Item> HIGH_QUALITY_CONCRETE_CLUMP_GRAY;
    public static final ItemRegistryObject<Item> HIGH_QUALITY_CONCRETE_CLUMP_GREEN;
    public static final ItemRegistryObject<Item> HIGH_QUALITY_CONCRETE_CLUMP_LIGHT_BLUE;
    public static final ItemRegistryObject<Item> HIGH_QUALITY_CONCRETE_CLUMP_LIGHT_GRAY;
    public static final ItemRegistryObject<Item> HIGH_QUALITY_CONCRETE_CLUMP_LIME;
    public static final ItemRegistryObject<Item> HIGH_QUALITY_CONCRETE_CLUMP_MAGENTA;
    public static final ItemRegistryObject<Item> HIGH_QUALITY_CONCRETE_CLUMP_ORANGE;
    public static final ItemRegistryObject<Item> HIGH_QUALITY_CONCRETE_CLUMP_PINK;
    public static final ItemRegistryObject<Item> HIGH_QUALITY_CONCRETE_CLUMP_PURPLE;
    public static final ItemRegistryObject<Item> HIGH_QUALITY_CONCRETE_CLUMP_RED;
    public static final ItemRegistryObject<Item> HIGH_QUALITY_CONCRETE_CLUMP_WHITE;
    public static final ItemRegistryObject<Item> HIGH_QUALITY_CONCRETE_CLUMP_YELLOW;
    public static final ItemRegistryObject<Item> HIGH_QUALITY_CONCRETE_POWDER;
    public static final ItemRegistryObject<Item> HIGH_QUALITY_CONCRETE_POWDER_AQUA;
    public static final ItemRegistryObject<Item> HIGH_QUALITY_CONCRETE_POWDER_BLACK;
    public static final ItemRegistryObject<Item> HIGH_QUALITY_CONCRETE_POWDER_BLUE;
    public static final ItemRegistryObject<Item> HIGH_QUALITY_CONCRETE_POWDER_BROWN;
    public static final ItemRegistryObject<Item> HIGH_QUALITY_CONCRETE_POWDER_CYAN;
    public static final ItemRegistryObject<Item> HIGH_QUALITY_CONCRETE_POWDER_DARK_RED;
    public static final ItemRegistryObject<Item> HIGH_QUALITY_CONCRETE_POWDER_GRAY;
    public static final ItemRegistryObject<Item> HIGH_QUALITY_CONCRETE_POWDER_GREEN;
    public static final ItemRegistryObject<Item> HIGH_QUALITY_CONCRETE_POWDER_LIGHT_BLUE;
    public static final ItemRegistryObject<Item> HIGH_QUALITY_CONCRETE_POWDER_LIGHT_GRAY;
    public static final ItemRegistryObject<Item> HIGH_QUALITY_CONCRETE_POWDER_LIME;
    public static final ItemRegistryObject<Item> HIGH_QUALITY_CONCRETE_POWDER_MAGENTA;
    public static final ItemRegistryObject<Item> HIGH_QUALITY_CONCRETE_POWDER_ORANGE;
    public static final ItemRegistryObject<Item> HIGH_QUALITY_CONCRETE_POWDER_PINK;
    public static final ItemRegistryObject<Item> HIGH_QUALITY_CONCRETE_POWDER_PURPLE;
    public static final ItemRegistryObject<Item> HIGH_QUALITY_CONCRETE_POWDER_RED;
    public static final ItemRegistryObject<Item> HIGH_QUALITY_CONCRETE_POWDER_WHITE;
    public static final ItemRegistryObject<Item> HIGH_QUALITY_CONCRETE_POWDER_YELLOW;
    //public static final ItemRegistryObject<Item> TABLET_MUSCLE_ENHANCEMENT = ITEMS.register("tablet_muscle_enhancement", () -> new MuscleEnhancementTablet(new Item.Properties().food(new FoodProperties.Builder().nutrition(0).saturationMod(0.0F).build())));
    //public static final ItemRegistryObject<Item> TABLET_POISON = ITEMS.register("tablet_poison", () -> new PoisonTablet(new Item.Properties().food(new FoodProperties.Builder().nutrition(0).saturationMod(0.0F).build())));
    //public static final ItemRegistryObject<Item> TABLET_SLEEP_INDUCING = ITEMS.register("tablet_sleep_inducing", () -> new SleepInducingTablet(new Item.Properties().food(new FoodProperties.Builder().nutrition(0).saturationMod(0.0F).build())));


    static {
        SYRINGE = ITEMS.register("syringe");
        ANESTHETIC_SYRINGE = ITEMS.register("syringe_anesthetic", () -> new AnestheticSyringe(new Item.Properties()));
        FLAME_RETARDANT_SYRINGE = ITEMS.register("syringe_flame_retardant", () -> new FlameRetardantSyringe(new Item.Properties()));
        LEVITATION_SYRINGE = ITEMS.register("syringe_levitation", () -> new LevitationSyringe(new Item.Properties()));
        HIGH_PERFORMANCE_ADSORBENT = ITEMS.register("high_performance_adsorbent");
        HIGH_PERFORMANCE_ADSORBENT_BERYLLIUM = ITEMS.register("high_performance_adsorbent_beryllium");
        HIGH_PERFORMANCE_ADSORBENT_COPPER = ITEMS.register("high_performance_adsorbent_copper");
        HIGH_PERFORMANCE_ADSORBENT_GOLD = ITEMS.register("high_performance_adsorbent_gold");
        HIGH_PERFORMANCE_ADSORBENT_IRON = ITEMS.register("high_performance_adsorbent_iron");
        HIGH_PERFORMANCE_ADSORBENT_LEAD = ITEMS.register("high_performance_adsorbent_lead");
        HIGH_PERFORMANCE_ADSORBENT_OSMIUM = ITEMS.register("high_performance_adsorbent_osmium");
        HIGH_PERFORMANCE_ADSORBENT_TIN = ITEMS.register("high_performance_adsorbent_tin");
        HIGH_PERFORMANCE_ADSORBENT_URANIUM = ITEMS.register("high_performance_adsorbent_uranium");
        NEUTRON_SOURCE_PELLET = ITEMS.register("pellet_neutron_source", () -> new NeutronSourcePellet(new Item.Properties(), EnumColor.YELLOW));
        DUST_CALCIUM_OXIDE = ITEMS.register("dust_calcium_oxide");
        //DUST_BERYLLIUM = ITEMS.register("dust_beryllium");
        //INGOT_BERYLLIUM = ITEMS.register("ingot_beryllium");
        HIGH_QUALITY_CONCRETE_CLUMP = ITEMS.register("clump_high_quality_concrete");
        HIGH_QUALITY_CONCRETE_POWDER = ITEMS.register("powder_high_quality_concrete");
        //UNSTABLE_CALIFORNIUM_MIXTURE = ITEMS.register("unstable_californium_mixture", ()-> new UnstableCaliforniumMixture(new Item.Properties(), EnumColor.ORANGE));
        //REFINED_CALIFORNIUM_INGOT = ITEMS.register("ingot_refined_californium", ()-> new RefinedCaliforniumIngot(new Item.Properties(), EnumColor.ORANGE));
        TABLET_IODINE = ITEMS.register("tablet_iodine", () -> new IodineTablet(new Item.Properties().food(new FoodProperties.Builder().nutrition(0).saturationMod(0.0F).alwaysEat().build())));

        HIGH_QUALITY_CONCRETE_CLUMP_AQUA = BUILDING_ITEMS.register("clump_high_quality_concrete_aqua");
        HIGH_QUALITY_CONCRETE_CLUMP_BLACK = BUILDING_ITEMS.register("clump_high_quality_concrete_black");
        HIGH_QUALITY_CONCRETE_CLUMP_BLUE = BUILDING_ITEMS.register("clump_high_quality_concrete_blue");
        HIGH_QUALITY_CONCRETE_CLUMP_BROWN = BUILDING_ITEMS.register("clump_high_quality_concrete_brown");
        HIGH_QUALITY_CONCRETE_CLUMP_CYAN = BUILDING_ITEMS.register("clump_high_quality_concrete_cyan");
        HIGH_QUALITY_CONCRETE_CLUMP_DARK_RED = BUILDING_ITEMS.register("clump_high_quality_concrete_dark_red");
        HIGH_QUALITY_CONCRETE_CLUMP_GRAY = BUILDING_ITEMS.register("clump_high_quality_concrete_gray");
        HIGH_QUALITY_CONCRETE_CLUMP_GREEN = BUILDING_ITEMS.register("clump_high_quality_concrete_green");
        HIGH_QUALITY_CONCRETE_CLUMP_LIGHT_BLUE = BUILDING_ITEMS.register("clump_high_quality_concrete_light_blue");
        HIGH_QUALITY_CONCRETE_CLUMP_LIGHT_GRAY = BUILDING_ITEMS.register("clump_high_quality_concrete_light_gray");
        HIGH_QUALITY_CONCRETE_CLUMP_LIME = BUILDING_ITEMS.register("clump_high_quality_concrete_lime");
        HIGH_QUALITY_CONCRETE_CLUMP_MAGENTA = BUILDING_ITEMS.register("clump_high_quality_concrete_magenta");
        HIGH_QUALITY_CONCRETE_CLUMP_ORANGE = BUILDING_ITEMS.register("clump_high_quality_concrete_orange");
        HIGH_QUALITY_CONCRETE_CLUMP_PINK = BUILDING_ITEMS.register("clump_high_quality_concrete_pink");
        HIGH_QUALITY_CONCRETE_CLUMP_PURPLE = BUILDING_ITEMS.register("clump_high_quality_concrete_purple");
        HIGH_QUALITY_CONCRETE_CLUMP_RED = BUILDING_ITEMS.register("clump_high_quality_concrete_red");
        HIGH_QUALITY_CONCRETE_CLUMP_WHITE = BUILDING_ITEMS.register("clump_high_quality_concrete_white");
        HIGH_QUALITY_CONCRETE_CLUMP_YELLOW = BUILDING_ITEMS.register("clump_high_quality_concrete_yellow");
        HIGH_QUALITY_CONCRETE_POWDER_AQUA = BUILDING_ITEMS.register("powder_high_quality_concrete_aqua");
        HIGH_QUALITY_CONCRETE_POWDER_BLACK = BUILDING_ITEMS.register("powder_high_quality_concrete_black");
        HIGH_QUALITY_CONCRETE_POWDER_BLUE = BUILDING_ITEMS.register("powder_high_quality_concrete_blue");
        HIGH_QUALITY_CONCRETE_POWDER_BROWN = BUILDING_ITEMS.register("powder_high_quality_concrete_brown");
        HIGH_QUALITY_CONCRETE_POWDER_CYAN = BUILDING_ITEMS.register("powder_high_quality_concrete_cyan");
        HIGH_QUALITY_CONCRETE_POWDER_DARK_RED = BUILDING_ITEMS.register("powder_high_quality_concrete_dark_red");
        HIGH_QUALITY_CONCRETE_POWDER_GRAY = BUILDING_ITEMS.register("powder_high_quality_concrete_gray");
        HIGH_QUALITY_CONCRETE_POWDER_GREEN = BUILDING_ITEMS.register("powder_high_quality_concrete_green");
        HIGH_QUALITY_CONCRETE_POWDER_LIGHT_BLUE = BUILDING_ITEMS.register("powder_high_quality_concrete_light_blue");
        HIGH_QUALITY_CONCRETE_POWDER_LIGHT_GRAY = BUILDING_ITEMS.register("powder_high_quality_concrete_light_gray");
        HIGH_QUALITY_CONCRETE_POWDER_LIME = BUILDING_ITEMS.register("powder_high_quality_concrete_lime");
        HIGH_QUALITY_CONCRETE_POWDER_MAGENTA = BUILDING_ITEMS.register("powder_high_quality_concrete_magenta");
        HIGH_QUALITY_CONCRETE_POWDER_ORANGE = BUILDING_ITEMS.register("powder_high_quality_concrete_orange");
        HIGH_QUALITY_CONCRETE_POWDER_PINK = BUILDING_ITEMS.register("powder_high_quality_concrete_pink");
        HIGH_QUALITY_CONCRETE_POWDER_PURPLE = BUILDING_ITEMS.register("powder_high_quality_concrete_purple");
        HIGH_QUALITY_CONCRETE_POWDER_RED = BUILDING_ITEMS.register("powder_high_quality_concrete_red");
        HIGH_QUALITY_CONCRETE_POWDER_WHITE = BUILDING_ITEMS.register("powder_high_quality_concrete_white");
        HIGH_QUALITY_CONCRETE_POWDER_YELLOW = BUILDING_ITEMS.register("powder_high_quality_concrete_yellow");
    }

    private MSItems(){
    }
}
