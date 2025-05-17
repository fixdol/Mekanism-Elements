package com.fxd927.mekanismelements.common.config;

import mekanism.common.config.MekanismConfigHelper;
import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.ModLoadingContext;

public class MSConfig {
    private MSConfig() {
    }

    public static final MSStorageConfig storageConfig = new MSStorageConfig();
    public static final MSUsageConfig usageConfig = new MSUsageConfig();

    public static void registerConfigs(ModLoadingContext modLoadingContext) {
        ModContainer modContainer = modLoadingContext.getActiveContainer();
        MekanismConfigHelper.registerConfig(modContainer, storageConfig);
        MekanismConfigHelper.registerConfig(modContainer, usageConfig);
    }
}
