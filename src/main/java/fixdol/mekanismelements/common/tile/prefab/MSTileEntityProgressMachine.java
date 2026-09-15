package fixdol.mekanismelements.common.tile.prefab;

import mekanism.api.providers.IBlockProvider;

import net.minecraft.world.level.block.Block;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import mekanism.api.recipes.cache.CachedRecipe;
import net.minecraft.network.chat.Component;
import net.minecraft.nbt.CompoundTag;
import mekanism.common.integration.computer.annotation.ComputerMethod;
import net.minecraft.core.Holder;
import java.util.List;
import fixdol.mekanismelements.common.tile.prefab.MSTileEntityProgressMachine;
import mekanism.api.recipes.MekanismRecipe;
import mekanism.common.util.MekanismUtils;
import org.jetbrains.annotations.NotNull;
import mekanism.api.Upgrade;
import mekanism.common.util.UpgradeUtils;

import mekanism.common.inventory.container.MekanismContainer;
import mekanism.common.inventory.container.sync.SyncableInt;


public abstract class MSTileEntityProgressMachine<RECIPE extends MekanismRecipe> extends MSTileEntityRecipeMachine<RECIPE> {
    public int ticksRequired;
    protected int baseTicksRequired;
    private int operatingTicks;

    protected MSTileEntityProgressMachine(IBlockProvider blockProvider, BlockPos pos, BlockState state, List<CachedRecipe.OperationTracker.RecipeError> errorTypes, int baseTicksRequired) {
        super(blockProvider, pos, state, errorTypes);
        this.baseTicksRequired = baseTicksRequired;
        ticksRequired = this.baseTicksRequired;
    }

    public double getScaledProgress() {
        return getOperatingTicks() / (double) ticksRequired;
    }

    @ComputerMethod(nameOverride = "getRecipeProgress")
    public int getOperatingTicks() {
        return operatingTicks;
    }

    protected void setOperatingTicks(int ticks) {
        this.operatingTicks = ticks;
    }

    @ComputerMethod
    public int getTicksRequired() {
        return ticksRequired;
    }

    @Override
    public int getSavedOperatingTicks(int cacheIndex) {
        return getOperatingTicks();
    }

    @Override
    public void load(@NotNull CompoundTag nbt) {
        super.load(nbt);
        operatingTicks = nbt.getInt("progress");
    }

    @Override
    public void saveAdditional(@NotNull CompoundTag nbtTags) {
        super.saveAdditional(nbtTags);
        nbtTags.putInt("progress", getOperatingTicks());
    }

    @Override
    public void recalculateUpgrades(Upgrade upgrade) {
        super.recalculateUpgrades(upgrade);
        if (upgrade == Upgrade.SPEED) {
            ticksRequired = MekanismUtils.getTicks(this, baseTicksRequired);
        }
    }

    @NotNull
    @Override
    public List<Component> getInfo(@NotNull Upgrade upgrade) {
        return UpgradeUtils.getMultScaledInfo(this, upgrade);
    }

    @Override
    public void addContainerTrackers(MekanismContainer container) {
        super.addContainerTrackers(container);
        container.track(SyncableInt.create(this::getOperatingTicks, this::setOperatingTicks));
        container.track(SyncableInt.create(this::getTicksRequired, value -> ticksRequired = value));
    }
}

