package com.fxd927.mekanismelements.common.tile.machine;

import com.fxd927.mekanismelements.common.registries.MSBlocks;
import com.fxd927.mekanismelements.common.registries.MSGases;
import mekanism.api.Action;
import mekanism.api.AutomationType;
import mekanism.api.IContentsListener;
import mekanism.api.Upgrade;
import mekanism.api.chemical.Chemical;
import mekanism.api.chemical.ChemicalStack;
import mekanism.api.chemical.IChemicalTank;
import mekanism.api.chemical.BasicChemicalTank;
import mekanism.common.capabilities.Capabilities;
import mekanism.common.capabilities.energy.MachineEnergyContainer;
import mekanism.common.capabilities.holder.chemical.ChemicalTankHelper;
import mekanism.common.capabilities.holder.chemical.IChemicalTankHolder;
import mekanism.common.capabilities.holder.energy.EnergyContainerHelper;
import mekanism.common.capabilities.holder.energy.IEnergyContainerHolder;
import mekanism.common.capabilities.holder.slot.IInventorySlotHolder;
import mekanism.common.capabilities.holder.slot.InventorySlotHelper;
import mekanism.api.chemical.IChemicalHandler;
import net.neoforged.neoforge.capabilities.BlockCapabilityCache;
import mekanism.common.integration.computer.SpecialComputerMethodWrapper;
import mekanism.common.integration.computer.annotation.WrappingComputerMethod;
import mekanism.common.inventory.container.slot.ContainerSlotType;
import mekanism.common.inventory.container.slot.SlotOverlay;
import mekanism.common.inventory.slot.EnergyInventorySlot;
import mekanism.common.inventory.slot.chemical.ChemicalInventorySlot;
import mekanism.api.RelativeSide;
import mekanism.common.lib.transmitter.TransmissionType;
import mekanism.common.tile.component.TileComponentConfig;
import mekanism.common.tile.component.TileComponentEjector;
import mekanism.common.tile.component.config.ConfigInfo;
import mekanism.common.tile.component.config.DataType;
import mekanism.common.tile.prefab.TileEntityConfigurableMachine;
import mekanism.common.util.ChemicalUtil;
import mekanism.common.util.MekanismUtils;
import mekanism.common.util.UpgradeUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class TileEntityAirCompressor extends TileEntityConfigurableMachine {
    private static final int BASE_TICKS_REQUIRED = 19;
    public static final ChemicalStack COMPRESSED_AIR_STACK = new ChemicalStack((Chemical) MSGases.COMPRESSED_AIR.get(), 200);

    @WrappingComputerMethod(wrapper = SpecialComputerMethodWrapper.ComputerChemicalTankWrapper.class, methodNames = {"getGas", "getGasCapacity", "getGasNeeded", "getGasFilledPercentage"}, docPlaceholder = "buffer tank")
    public IChemicalTank chemicalTank;
    public int ticksRequired = BASE_TICKS_REQUIRED;

    public int operatingTicks;

    private MachineEnergyContainer<TileEntityAirCompressor> energyContainer;

    @WrappingComputerMethod(wrapper = SpecialComputerMethodWrapper.ComputerIInventorySlotWrapper.class, methodNames = "getInputItem", docPlaceholder = "")
    ChemicalInventorySlot inputSlot;

    @WrappingComputerMethod(wrapper = SpecialComputerMethodWrapper.ComputerIInventorySlotWrapper.class, methodNames = "getOutputItem", docPlaceholder = "")
    ChemicalInventorySlot outputSlot;

    @WrappingComputerMethod(wrapper = SpecialComputerMethodWrapper.ComputerIInventorySlotWrapper.class, methodNames = "getEnergyItem", docPlaceholder = "")
    private EnergyInventorySlot energySlot;


    public TileEntityAirCompressor(BlockPos pos, BlockState state) {
        super(MSBlocks.AIR_COMPRESSOR, pos, state);
        // Config is created from block attributes in parent constructor
        // Capabilities are added via tile entity type builder
        getConfig().setupItemIOConfig(List.of(inputSlot),List.of(outputSlot),energySlot,true);
        
        // Chemical Output Config - TOP/RIGHT/FRONT sides
        ConfigInfo chemicalConfig = getConfig().setupOutputConfig(TransmissionType.CHEMICAL , chemicalTank);
        if (chemicalConfig != null) {
            chemicalConfig.setDataType(DataType.OUTPUT, mekanism.api.RelativeSide.TOP);
            chemicalConfig.setDataType(DataType.OUTPUT, mekanism.api.RelativeSide.RIGHT);
            chemicalConfig.setDataType(DataType.OUTPUT, mekanism.api.RelativeSide.FRONT);
        }
        
        // Energy Config - all sides accept input by default
        ConfigInfo energyConfig = getConfig().setupInputConfig(TransmissionType.ENERGY , energyContainer);
        if (energyConfig != null) {
            for (mekanism.api.RelativeSide side : mekanism.common.util.EnumUtils.SIDES) {
                energyConfig.setDataType(DataType.INPUT, side);
            }
        }

        ejectorComponent = new TileComponentEjector(this);
        ejectorComponent.setOutputData(getConfig(),TransmissionType.ITEM)
                .setCanEject(type -> canFunction());
        ejectorComponent.setOutputData(getConfig(),TransmissionType.CHEMICAL)
                .setCanEject(type -> canFunction());
    }

    @Override
    protected void presetVariables() {
        super.presetVariables();
        chemicalTank = BasicChemicalTank.output(10_000, this::markForSave);
        energyContainer = MachineEnergyContainer.input(this, this::markForSave);
    }

    @Nonnull
    @Override
    public IChemicalTankHolder getInitialChemicalTanks(IContentsListener listener) {
        ChemicalTankHelper builder = ChemicalTankHelper.forSide(this::getDirection);
        builder.addTank(chemicalTank);
        return builder.build();
    }

    @Nonnull
    @Override
    protected IEnergyContainerHolder getInitialEnergyContainers(IContentsListener listener) {
        EnergyContainerHelper builder = EnergyContainerHelper.forSide(this::getDirection);
        builder.addContainer(energyContainer);
        return builder.build();
    }

    @Override
    public boolean getActive() {
        return super.getActive();
    }


    @Nonnull
    @Override
    protected IInventorySlotHolder getInitialInventory(IContentsListener listener) {
        InventorySlotHelper builder = InventorySlotHelper.forSide(this::getDirection);
        builder.addSlot(inputSlot = ChemicalInventorySlot.drain(chemicalTank, listener, 28, 20));
        builder.addSlot(outputSlot = ChemicalInventorySlot.drain(chemicalTank, listener, 28, 51));
        builder.addSlot(energySlot = EnergyInventorySlot.fillOrConvert(energyContainer, this::getLevel, listener, 143, 35));
        outputSlot.setSlotType(ContainerSlotType.OUTPUT);
        inputSlot.setSlotOverlay(SlotOverlay.MINUS);
        outputSlot.setSlotOverlay(SlotOverlay.PLUS);
        return builder.build();
    }

    @Override
    protected boolean onUpdateServer() {
        boolean needsUpdate = super.onUpdateServer();
        energySlot.fillContainerOrConvert();
        outputSlot.drainTank();

        boolean isGeneratingCompressedAir = false;

        Direction frontDirection = mekanism.api.RelativeSide.FRONT.getDirection(getDirection());
        BlockPos frontPos = getBlockPos().relative(frontDirection);
        boolean isBlocked = !level.isEmptyBlock(frontPos);

        if (!isBlocked && canFunction() && COMPRESSED_AIR_STACK.getAmount() <= chemicalTank.getNeeded()) {
            long energyPerTick = energyContainer.getEnergyPerTick();
            if (energyContainer.extract(energyPerTick, Action.SIMULATE, AutomationType.INTERNAL) == energyPerTick) {
                // Extract energy every tick
                energyContainer.extract(energyPerTick, Action.EXECUTE, AutomationType.INTERNAL);
                operatingTicks++;
                if (operatingTicks >= ticksRequired) {
                    operatingTicks = 0;
                    chemicalTank.insert(COMPRESSED_AIR_STACK, Action.EXECUTE, AutomationType.INTERNAL);
                    isGeneratingCompressedAir = true;
                }
            } else {
                // Not enough energy, reset progress
                operatingTicks = 0;
            }
        } else {
            // Can't function, reset progress
            operatingTicks = 0;
        }

        if (!chemicalTank.isEmpty()) {
            long emitRate = 256L * (1 + upgradeComponent.getUpgrades(Upgrade.SPEED));
            if (level instanceof net.minecraft.server.level.ServerLevel serverLevel) {
                BlockCapabilityCache<IChemicalHandler, net.minecraft.core.Direction> cache = 
                    Capabilities.CHEMICAL.createCache(serverLevel, getBlockPos().relative(Direction.UP), Direction.DOWN);
                ChemicalUtil.emit(Collections.singletonList(cache), chemicalTank, emitRate);
            }
        }

        setActive(!isBlocked && canFunction() && COMPRESSED_AIR_STACK.getAmount() <= chemicalTank.getNeeded() && energyContainer.getEnergy() >= energyContainer.getEnergyPerTick());
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

    public InteractionResult onSneakRightClick(Player player) {
        return InteractionResult.PASS;
    }

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

    public int getRedstoneLevel() {
        return MekanismUtils.redstoneLevelFromContents(chemicalTank.getStored(), chemicalTank.getCapacity());
    }

    protected boolean makesComparatorDirty(@Nullable TransmissionType type) {
        return type == TransmissionType.CHEMICAL;
    }

    public List<Component> getInfo(Upgrade upgrade) {
        return UpgradeUtils.getMultScaledInfo(this, upgrade);
    }

    public MachineEnergyContainer<TileEntityAirCompressor> getEnergyContainer() {
        return energyContainer;
    }
}

