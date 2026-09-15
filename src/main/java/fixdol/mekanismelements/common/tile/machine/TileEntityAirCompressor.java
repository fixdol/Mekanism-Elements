package fixdol.mekanismelements.common.tile.machine;

import mekanism.common.tile.component.TileComponentConfig;

import mekanism.api.math.FloatingLong;

import mekanism.api.AutomationType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import mekanism.api.chemical.ChemicalTankBuilder;
import mekanism.common.capabilities.holder.chemical.ChemicalTankHelper;
import mekanism.common.util.ChemicalUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.nbt.CompoundTag;
import mekanism.common.tile.component.config.ConfigInfo;
import mekanism.common.inventory.container.slot.ContainerSlotType;
import mekanism.common.tile.component.config.DataType;
import net.minecraft.core.Direction;
import mekanism.common.capabilities.holder.energy.EnergyContainerHelper;
import mekanism.common.inventory.slot.EnergyInventorySlot;
import mekanism.api.chemical.gas.Gas;
import mekanism.common.inventory.slot.chemical.GasInventorySlot;
import mekanism.api.chemical.gas.GasStack;
import mekanism.common.capabilities.holder.chemical.IChemicalTankHolder;
import mekanism.api.IContentsListener;
import mekanism.common.capabilities.holder.energy.IEnergyContainerHolder;
import mekanism.api.chemical.gas.IGasTank;
import mekanism.common.capabilities.holder.slot.IInventorySlotHolder;
import mekanism.common.capabilities.holder.slot.InventorySlotHelper;
import java.util.List;
import fixdol.mekanismelements.common.registries.MSBlocks;
import fixdol.mekanismelements.common.registries.MSGases;
import mekanism.common.capabilities.energy.MachineEnergyContainer;
import mekanism.common.util.MekanismUtils;
import javax.annotation.Nonnull;
import org.jetbrains.annotations.Nullable;
import net.minecraft.world.entity.player.Player;
import mekanism.common.inventory.container.slot.SlotOverlay;
import mekanism.common.integration.computer.SpecialComputerMethodWrapper;
import mekanism.common.tile.component.TileComponentEjector;
import fixdol.mekanismelements.common.tile.machine.TileEntityAirCompressor;
import mekanism.common.lib.transmitter.TransmissionType;
import mekanism.api.Upgrade;
import mekanism.common.integration.computer.annotation.WrappingComputerMethod;

import mekanism.api.Action;
import mekanism.api.chemical.gas.IGasHandler;
import mekanism.common.tile.prefab.TileEntityConfigurableMachine;
import mekanism.common.util.UpgradeUtils;
import net.minecraft.world.InteractionResult;


public class TileEntityAirCompressor extends TileEntityConfigurableMachine {
    private static final int BASE_TICKS_REQUIRED = 19;
    public static final GasStack COMPRESSED_AIR_STACK = new GasStack((Gas) MSGases.COMPRESSED_AIR.get(), 200);

    @WrappingComputerMethod(wrapper = SpecialComputerMethodWrapper.ComputerChemicalTankWrapper.class, methodNames = {"getGas", "getGasCapacity", "getGasNeeded", "getGasFilledPercentage"}, docPlaceholder = "buffer tank")
    public IGasTank chemicalTank;
    public int ticksRequired = BASE_TICKS_REQUIRED;

    public int operatingTicks;

    private MachineEnergyContainer<TileEntityAirCompressor> energyContainer;

    @WrappingComputerMethod(wrapper = SpecialComputerMethodWrapper.ComputerIInventorySlotWrapper.class, methodNames = "getInputItem", docPlaceholder = "")
    GasInventorySlot inputSlot;

    @WrappingComputerMethod(wrapper = SpecialComputerMethodWrapper.ComputerIInventorySlotWrapper.class, methodNames = "getOutputItem", docPlaceholder = "")
    GasInventorySlot outputSlot;

    @WrappingComputerMethod(wrapper = SpecialComputerMethodWrapper.ComputerIInventorySlotWrapper.class, methodNames = "getEnergyItem", docPlaceholder = "")
    private EnergyInventorySlot energySlot;


    public TileEntityAirCompressor(BlockPos pos, BlockState state) {
        super(MSBlocks.AIR_COMPRESSOR, pos, state);
        configComponent = new TileComponentConfig(this, mekanism.common.lib.transmitter.TransmissionType.ITEM, mekanism.common.lib.transmitter.TransmissionType.GAS, mekanism.common.lib.transmitter.TransmissionType.ENERGY);
        // Config is created from block attributes in parent constructor
        // Capabilities are added via tile entity type builder
        getConfig().setupItemIOConfig(List.of(inputSlot),List.of(outputSlot),energySlot,true);
        
        // Chemical Output Config - TOP/RIGHT/FRONT sides
        ConfigInfo chemicalConfig = getConfig().setupOutputConfig(TransmissionType.GAS , chemicalTank);
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
                .setCanEject(type -> MekanismUtils.canFunction(this));
        ejectorComponent.setOutputData(getConfig(),TransmissionType.GAS)
                .setCanEject(type -> MekanismUtils.canFunction(this));
    }

    @Override
    protected void presetVariables() {
        super.presetVariables();
        chemicalTank = ChemicalTankBuilder.GAS.output(10_000, this::markForSave);
        energyContainer = MachineEnergyContainer.input(this, this::markForSave);
    }

    @Nonnull
    @Override
    public IChemicalTankHolder<Gas, GasStack, IGasTank> getInitialGasTanks(IContentsListener listener) {
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
        builder.addSlot(inputSlot = GasInventorySlot.drain(chemicalTank, listener, 28, 20));
        builder.addSlot(outputSlot = GasInventorySlot.drain(chemicalTank, listener, 28, 51));
        builder.addSlot(energySlot = EnergyInventorySlot.fillOrConvert(energyContainer, this::getLevel, listener, 143, 35));
        outputSlot.setSlotType(ContainerSlotType.OUTPUT);
        inputSlot.setSlotOverlay(SlotOverlay.MINUS);
        outputSlot.setSlotOverlay(SlotOverlay.PLUS);
        return builder.build();
    }

    @Override
    protected void onUpdateServer() {
        super.onUpdateServer();
        energySlot.fillContainerOrConvert();
        outputSlot.drainTank();

        boolean isGeneratingCompressedAir = false;

        Direction frontDirection = mekanism.api.RelativeSide.FRONT.getDirection(getDirection());
        BlockPos frontPos = getBlockPos().relative(frontDirection);
        boolean isBlocked = !level.isEmptyBlock(frontPos);

        if (!isBlocked && MekanismUtils.canFunction(this) && COMPRESSED_AIR_STACK.getAmount() <= chemicalTank.getNeeded()) {
            FloatingLong energyPerTick = energyContainer.getEnergyPerTick();
            if (energyContainer.extract(energyPerTick, Action.SIMULATE, AutomationType.INTERNAL).equals(energyPerTick)) {
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
            ChemicalUtil.emit(java.util.Collections.singleton(Direction.UP), chemicalTank, this, emitRate);
        }

        setActive(!isBlocked && MekanismUtils.canFunction(this) && COMPRESSED_AIR_STACK.getAmount() <= chemicalTank.getNeeded() && !energyContainer.getEnergy().smallerThan(energyContainer.getEnergyPerTick()));
    }

    @Override
    public void saveAdditional(@Nonnull CompoundTag nbtTags) {
        super.saveAdditional(nbtTags);
        nbtTags.putInt("progress", operatingTicks);
    }

    @Override
    public void load(@Nonnull CompoundTag nbt) {
        super.load(nbt);
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
        return type == TransmissionType.GAS;
    }

    public List<Component> getInfo(Upgrade upgrade) {
        return UpgradeUtils.getMultScaledInfo(this, upgrade);
    }

    public MachineEnergyContainer<TileEntityAirCompressor> getEnergyContainer() {
        return energyContainer;
    }
}

