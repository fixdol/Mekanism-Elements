package fixdol.mekanismelements.common;

import fixdol.mekanismelements.common.recipe.MSRecipeType;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import org.jetbrains.annotations.NotNull;

public class MSReloadListener implements ResourceManagerReloadListener {
    @Override
    public void onResourceManagerReload(@NotNull ResourceManager resourceManager) {
        MSRecipeType.clearCache();
    }
}

