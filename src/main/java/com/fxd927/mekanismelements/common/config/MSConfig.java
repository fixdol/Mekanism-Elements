package com.fxd927.mekanismelements.common.config;

import mekanism.common.config.IMekanismConfig;
import mekanism.common.config.MekanismConfigHelper;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModLoadingContext;

import java.util.HashMap;
import java.util.Map;

public class MSConfig {
    private MSConfig() {
    }

    public static final MSStorageConfig storageConfig = new MSStorageConfig();
    public static final MSUsageConfig usageConfig = new MSUsageConfig();

    public static void registerConfigs(ModLoadingContext modLoadingContext) {
        ModContainer modContainer = modLoadingContext.getActiveContainer();
        Map<net.neoforged.fml.config.IConfigSpec, IMekanismConfig> configs = new HashMap<>();
        MekanismConfigHelper.registerConfig(configs, modContainer, storageConfig);
        MekanismConfigHelper.registerConfig(configs, modContainer, usageConfig);
    }
}

