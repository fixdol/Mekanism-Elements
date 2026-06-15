package com.fxd927.mekanismelements.common.tile.machine;

import mekanism.common.tile.component.config.ConfigInfo;
import com.fxd927.mekanismelements.common.registries.MSBlocks;
import com.fxd927.mekanismelements.common.registries.MSFluids;
import mekanism.api.*;
import mekanism.common.capabilities.Capabilities;
import mekanism.common.capabilities.energy.MachineEnergyContainer;
import mekanism.common.capabilities.fluid.BasicFluidTank;
import mekanism.common.capabilities.holder.energy.EnergyContainerHelper;
import mekanism.common.capabilities.holder.energy.IEnergyContainerHolder;
import mekanism.common.capabilities.holder.fluid.FluidTankHelper;
import mekanism.common.capabilities.holder.fluid.IFluidTankHolder;
import mekanism.common.capabilities.holder.slot.IInventorySlotHolder;
import mekanism.common.capabilities.holder.slot.InventorySlotHelper;
import mekanism.common.capabilities.resolver.BasicCapabilityResolver;
import mekanism.common.integration.computer.SpecialComputerMethodWrapper;
import mekanism.common.integration.computer.annotation.WrappingComputerMethod;
import mekanism.common.inventory.slot.EnergyInventorySlot;
import mekanism.common.inventory.slot.FluidInventorySlot;
import mekanism.common.inventory.slot.OutputInventorySlot;
import mekanism.common.lib.transmitter.TransmissionType;
import mekanism.common.tile.component.TileComponentConfig;
import mekanism.common.tile.component.TileComponentEjector;
import mekanism.common.tile.prefab.TileEntityConfigurableMachine;
import mekanism.common.util.FluidUtils;
import mekanism.common.util.MekanismUtils;
import mekanism.common.util.UpgradeUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.capabilities.BlockCapabilityCache;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class TileEntitySeawaterPump extends TileEntityConfigurableMachine implements IConfigurable {
    private static final int BASE_TICKS_REQUIRED = 19;
    public static final FluidStack SEAWATER_STACK = new FluidStack(MSFluids.SEAWATER.get(), 200);

    @WrappingComputerMethod(wrapper = SpecialComputerMethodWrapper.ComputerFluidTankWrapper.class, methodNames = {"getFluid", "getFluidCapacity", "getFluidNeeded", "getFluidFilledPercentage"}, docPlaceholder = "buffer tank")
    public BasicFluidTank fluidTank;
    public int ticksRequired = BASE_TICKS_REQUIRED;

    public int operatingTicks;

    private MachineEnergyContainer<TileEntitySeawaterPump> energyContainer;

    @WrappingComputerMethod(wrapper = SpecialComputerMethodWrapper.ComputerIInventorySlotWrapper.class, methodNames = "getInputItem", docPlaceholder = "")
    FluidInventorySlot inputSlot;

    @WrappingComputerMethod(wrapper = SpecialComputerMethodWrapper.ComputerIInventorySlotWrapper.class, methodNames = "getOutputItem", docPlaceholder = "")
    OutputInventorySlot outputSlot;

    @WrappingComputerMethod(wrapper = SpecialComputerMethodWrapper.ComputerIInventorySlotWrapper.class, methodNames = "getEnergyItem", docPlaceholder = "")
    private EnergyInventorySlot energySlot;

    public TileEntitySeawaterPump(BlockPos pos, BlockState state) {
        super(MSBlocks.SEAWATER_PUMP, pos, state);
        // Config is created from block attributes in parent constructor
        getConfig().setupItemIOConfig(List.of(inputSlot),List.of(outputSlot),energySlot,true);
        
        // Fluid Output Config - TOP and RIGHT sides
        ConfigInfo fluidConfig = getConfig().setupOutputConfig(TransmissionType.FLUID , fluidTank , RelativeSide.TOP);
        if (fluidConfig != null) {
            fluidConfig.setDataType(mekanism.common.tile.component.config.DataType.OUTPUT, RelativeSide.TOP);
            fluidConfig.setDataType(mekanism.common.tile.component.config.DataType.OUTPUT, RelativeSide.RIGHT);
            fluidConfig.setDataType(mekanism.common.tile.component.config.DataType.OUTPUT, RelativeSide.FRONT);
        }
        
        // Energy Config - all sides accept
        ConfigInfo energyConfig = getConfig().setupInputConfig(TransmissionType.ENERGY , energyContainer);
        if (energyConfig != null) {
            for (RelativeSide side : RelativeSide.values()) {
                energyConfig.setDataType(mekanism.common.tile.component.config.DataType.INPUT, side);
            }
        }

        ejectorComponent = new TileComponentEjector(this);
        ejectorComponent.setOutputData(getConfig(),TransmissionType.ITEM)
                .setCanEject(type -> canFunction());
        ejectorComponent.setOutputData(getConfig(),TransmissionType.FLUID)
                .setCanEject(type -> canFunction());
    }

    @Override
    protected void presetVariables() {
        super.presetVariables();
        fluidTank = BasicFluidTank.output(10_000, this::markForSave);
        energyContainer = MachineEnergyContainer.input(this, this::markForSave);
    }

    @Nonnull
    @Override
    public IFluidTankHolder getInitialFluidTanks(IContentsListener listener) {
        FluidTankHelper builder = FluidTankHelper.forSide(this::getDirection);
        builder.addTank(fluidTank, RelativeSide.TOP);
        return builder.build();
    }

    @Nonnull
    @Override
    protected IEnergyContainerHolder getInitialEnergyContainers(IContentsListener listener) {
        EnergyContainerHelper builder = EnergyContainerHelper.forSide(this::getDirection);
        builder.addContainer(energyContainer, RelativeSide.BACK);
        return builder.build();
    }

    @Nonnull
    @Override
    protected IInventorySlotHolder getInitialInventory(IContentsListener listener) {
        InventorySlotHelper builder = InventorySlotHelper.forSide(this::getDirection);
        builder.addSlot(inputSlot = FluidInventorySlot.drain(fluidTank, listener, 28, 20), RelativeSide.TOP);
        builder.addSlot(outputSlot = OutputInventorySlot.at(listener, 28, 51), RelativeSide.BOTTOM);
        builder.addSlot(energySlot = EnergyInventorySlot.fillOrConvert(energyContainer, this::getLevel, listener, 143, 35), RelativeSide.BACK);
        return builder.build();
    }

    @Override
    protected boolean onUpdateServer() {
        boolean needsUpdate = super.onUpdateServer();
        if (this.getLevel().getBiome(this.getBlockPos()).is(BiomeTags.IS_OCEAN)) {
            BlockPos belowPos = this.getBlockPos().below();
            BlockState belowState = this.getLevel().getBlockState(belowPos);
            if (belowState.getFluidState().isSource() && belowState.getFluidState().getType() == Fluids.WATER) {

                energySlot.fillContainerOrConvert();
                inputSlot.drainTank(outputSlot);

                if (canFunction() && SEAWATER_STACK.getAmount() <= fluidTank.getNeeded()) {
                    long energyPerTick = energyContainer.getEnergyPerTick();

                    if (energyContainer.extract(energyPerTick, Action.SIMULATE, AutomationType.INTERNAL) == energyPerTick) {
                        operatingTicks++;

                        if (operatingTicks >= ticksRequired) {
                            operatingTicks = 0;
                            energyContainer.extract(energyPerTick, Action.EXECUTE, AutomationType.INTERNAL);
                            fluidTank.insert(SEAWATER_STACK, Action.EXECUTE, AutomationType.INTERNAL);
                        }
                    }
                }
                if (!fluidTank.isEmpty()) {
                    int emitRate = (int)(256L * (1 + upgradeComponent.getUpgrades(Upgrade.SPEED)));
                    if (level instanceof net.minecraft.server.level.ServerLevel serverLevel) {
                        BlockCapabilityCache<net.neoforged.neoforge.fluids.capability.IFluidHandler, net.minecraft.core.Direction> cache = 
                            mekanism.common.capabilities.Capabilities.FLUID.createCache(serverLevel, getBlockPos().relative(Direction.UP), Direction.DOWN);
                        FluidUtils.emit(Collections.singletonList(cache), fluidTank, emitRate);
                    }
                }
            }
        }
        return needsUpdate;
    }

    @Override
    public void saveAdditional(@Nonnull CompoundTag nbtTags, @Nonnull HolderLookup.Provider provider) {
        super.saveAdditional(nbtTags, provider);
        nbtTags.putInt("progress", operatingTicks);
    }

    @Override
    public void loadAdditional(@Nonnull CompoundTag nbt, @Nonnull HolderLookup.Provider provider) {
        super.loadAdditional(nbt, provider);

        operatingTicks = nbt.getInt("progress");
    }

    @Override
    public InteractionResult onSneakRightClick(Player player) {
        return InteractionResult.PASS;
    }

    @Override
    public InteractionResult onRightClick(Player player) {
        return InteractionResult.PASS;
    }

    public boolean canPulse() {
        return true;
    }

    @Override
    public void recalculateUpgrades(Upgrade upgrade) {
        super.recalculateUpgrades(upgrade);
        if (upgrade == Upgrade.SPEED) {
            ticksRequired = MekanismUtils.getTicks(this, BASE_TICKS_REQUIRED);
        }
    }

    @Override
    public int getRedstoneLevel() {
        return MekanismUtils.redstoneLevelFromContents(fluidTank.getFluidAmount(), fluidTank.getCapacity());
    }

    protected boolean makesComparatorDirty(@Nullable TransmissionType type) {
        return type == TransmissionType.FLUID;
    }

    @Override
    public List<Component> getInfo(Upgrade upgrade) {
        return UpgradeUtils.getMultScaledInfo(this, upgrade);
    }

    public MachineEnergyContainer<TileEntitySeawaterPump> getEnergyContainer() {
        return energyContainer;
    }
}

