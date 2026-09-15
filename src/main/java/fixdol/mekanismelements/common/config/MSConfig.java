package fixdol.mekanismelements.common.config;

import fixdol.mekanismelements.common.config.MSConfig;

import mekanism.common.config.IMekanismConfig;
import mekanism.common.config.MekanismConfigHelper;
import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.ModLoadingContext;

import java.util.HashMap;
import java.util.Map;

public class MSConfig {
    private MSConfig() {
    }

    public static final MSStorageConfig storageConfig = new MSStorageConfig();
    public static final MSUsageConfig usageConfig = new MSUsageConfig();

    public static void registerConfigs(ModLoadingContext modLoadingContext) {
        ModContainer modContainer = modLoadingContext.getActiveContainer();
        Map<net.minecraftforge.fml.config.IConfigSpec, IMekanismConfig> configs = new HashMap<>();
        MekanismConfigHelper.registerConfig(modContainer, storageConfig);
        MekanismConfigHelper.registerConfig(modContainer, usageConfig);
    }
}