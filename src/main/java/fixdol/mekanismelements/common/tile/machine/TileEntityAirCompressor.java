package fixdol.mekanismelements.common.tile.machine;

import fixdol.mekanismelements.common.registries.MSBlocks;
import fixdol.mekanismelements.common.registries.MSGases;
import mekanism.api.AutomationType;
import mekanism.api.IContentsListener;
import mekanism.api.RelativeSide;
import mekanism.api.SerializationConstants;
import mekanism.api.Upgrade;
import mekanism.api.chemical.BasicChemicalTank;
import mekanism.api.chemical.ChemicalResource;
import mekanism.api.chemical.ChemicalStack;
import mekanism.api.chemical.IChemicalTank;
import mekanism.api.energy.IEnergyContainer;
import mekanism.api.inventory.IInventorySlot;
import mekanism.common.capabilities.Capabilities;
import mekanism.common.capabilities.energy.MachineEnergyContainer;
import mekanism.common.capabilities.holder.container.IContainerHolder;
import mekanism.common.capabilities.holder.container.MekContainerHelper;
import mekanism.common.capabilities.holder.single.BasicSingleHolder;
import mekanism.common.capabilities.holder.single.ISingleContainerHolder;
import mekanism.common.component.containers.type.ContainerType;
import mekanism.common.component.containers.type.IContainerType;
import mekanism.common.integration.computer.SpecialComputerMethodWrapper;
import mekanism.common.integration.computer.annotation.WrappingComputerMethod;
import mekanism.common.inventory.container.slot.ContainerSlotType;
import mekanism.common.inventory.container.slot.SlotOverlay;
import mekanism.common.inventory.slot.ChemicalInventorySlot;
import mekanism.common.inventory.slot.EnergyInventorySlot;
import mekanism.common.lib.transmitter.TransmissionType;
import mekanism.common.tile.component.config.ConfigInfo;
import mekanism.common.tile.component.config.DataType;
import mekanism.common.tile.prefab.TileEntityConfigurableMachine;
import mekanism.common.util.EnumUtils;
import mekanism.common.util.MekanismUtils;
import mekanism.common.util.ResourceUtils;
import mekanism.common.util.UpgradeUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.neoforged.neoforge.capabilities.BlockCapabilityCache;
import net.neoforged.neoforge.transfer.ResourceHandler;
import net.neoforged.neoforge.transfer.transaction.Transaction;
import org.jetbrains.annotations.UnknownNullability;
import org.jspecify.annotations.Nullable;

import java.util.List;

public class TileEntityAirCompressor extends TileEntityConfigurableMachine {
    private static final int BASE_TICKS_REQUIRED = 19;
    private static final int BASE_OUTPUT_RATE = 256;
    private static final long MAX_CHEMICAL = 10_000;
    public static final ChemicalStack COMPRESSED_AIR_STACK = new ChemicalStack(MSGases.COMPRESSED_AIR, 200);

    @UnknownNullability//Initialized via getInitialChemicalTanks
    @WrappingComputerMethod(wrapper = SpecialComputerMethodWrapper.ComputerChemicalTankWrapper.class, methodNames = {"getGas", "getGasCapacity", "getGasNeeded", "getGasFilledPercentage"}, docPlaceholder = "buffer tank")
    public IChemicalTank chemicalTank;
    public int ticksRequired = BASE_TICKS_REQUIRED;

    public int operatingTicks;
    private int outputRate = BASE_OUTPUT_RATE;
    @Nullable
    private BlockCapabilityCache<ResourceHandler<ChemicalResource>, @Nullable Direction> chemicalHandlerAbove;

    @UnknownNullability//Initialized via getInitialEnergyContainer
    private MachineEnergyContainer<TileEntityAirCompressor> energyContainer;

    @UnknownNullability//Initialized via getInitialInventory
    @WrappingComputerMethod(wrapper = SpecialComputerMethodWrapper.ComputerIInventorySlotWrapper.class, methodNames = "getInputItem", docPlaceholder = "")
    ChemicalInventorySlot inputSlot;

    @UnknownNullability//Initialized via getInitialInventory
    @WrappingComputerMethod(wrapper = SpecialComputerMethodWrapper.ComputerIInventorySlotWrapper.class, methodNames = "getOutputItem", docPlaceholder = "")
    ChemicalInventorySlot outputSlot;

    @UnknownNullability//Initialized via getInitialInventory
    @WrappingComputerMethod(wrapper = SpecialComputerMethodWrapper.ComputerIInventorySlotWrapper.class, methodNames = "getEnergyItem", docPlaceholder = "")
    private EnergyInventorySlot energySlot;

    public TileEntityAirCompressor(BlockPos pos, BlockState state) {
        super(MSBlocks.AIR_COMPRESSOR, pos, state);
        configComponent.setupItemIOConfig(List.of(inputSlot), List.of(outputSlot), energySlot, true);

        // Chemical Output Config - TOP/RIGHT/FRONT sides
        ConfigInfo chemicalConfig = configComponent.setupOutputConfig(TransmissionType.CHEMICAL, chemicalTank);
        chemicalConfig.setDataType(DataType.OUTPUT, RelativeSide.TOP);
        chemicalConfig.setDataType(DataType.OUTPUT, RelativeSide.RIGHT);
        chemicalConfig.setDataType(DataType.OUTPUT, RelativeSide.FRONT);

        // Energy Config - all sides accept input by default
        ConfigInfo energyConfig = configComponent.setupInputConfig(TransmissionType.ENERGY, energyContainer);
        for (RelativeSide side : EnumUtils.SIDES) {
            energyConfig.setDataType(DataType.INPUT, side);
        }

        ejectorComponent.setOutputData(configComponent, TransmissionType.ITEM)
                .setCanEject(type -> canFunction());
        ejectorComponent.setOutputData(configComponent, TransmissionType.CHEMICAL)
                .setCanEject(type -> canFunction());
    }

    @Override
    public IContainerHolder<IChemicalTank> getInitialChemicalTanks(IContentsListener listener) {
        MekContainerHelper<IChemicalTank> builder = MekContainerHelper.forSide(facingSupplier);
        builder.addContainer(chemicalTank = BasicChemicalTank.output(MAX_CHEMICAL, listener));
        return builder.build();
    }

    @Override
    protected ISingleContainerHolder<IEnergyContainer> getInitialEnergyContainer(IContentsListener listener) {
        energyContainer = MachineEnergyContainer.input(this, listener);
        return new BasicSingleHolder<>(energyContainer, facingSupplier, null, null);
    }

    @Override
    protected IContainerHolder<IInventorySlot> getInitialInventory(IContentsListener listener) {
        MekContainerHelper<IInventorySlot> builder = MekContainerHelper.forSide(facingSupplier);
        builder.addContainer(inputSlot = ChemicalInventorySlot.drain(chemicalTank, listener, 28, 20));
        builder.addContainer(outputSlot = ChemicalInventorySlot.drain(chemicalTank, listener, 28, 51));
        builder.addContainer(energySlot = EnergyInventorySlot.fillOrConvert(energyContainer, this::getLevel, listener, 143, 35));
        outputSlot.setSlotType(ContainerSlotType.OUTPUT);
        inputSlot.setSlotOverlay(SlotOverlay.MINUS);
        outputSlot.setSlotOverlay(SlotOverlay.PLUS);
        return builder.build();
    }

    @Override
    protected boolean onUpdateServer(ServerLevel level) {
        boolean sendUpdatePacket = super.onUpdateServer(level);
        energySlot.fillContainerOrConvert(null);
        outputSlot.drainTankIntoSlot(null);

        Direction frontDirection = RelativeSide.FRONT.getDirection(getDirection());
        BlockPos frontPos = getBlockPos().relative(frontDirection);
        boolean isBlocked = !level.isEmptyBlock(frontPos);
        ChemicalResource compressedAir = ChemicalResource.of(COMPRESSED_AIR_STACK);
        boolean hasSpace = COMPRESSED_AIR_STACK.amount() <= chemicalTank.getNeededAsLong(compressedAir);

        if (!isBlocked && canFunction() && hasSpace) {
            int energyPerTick = energyContainer.getEnergyPerTick();
            try (Transaction transaction = Transaction.openRoot()) {
                if (energyContainer.extract(energyPerTick, transaction, AutomationType.INTERNAL) == energyPerTick) {
                    operatingTicks++;
                    if (operatingTicks >= ticksRequired) {
                        operatingTicks = 0;
                        chemicalTank.insert(compressedAir, COMPRESSED_AIR_STACK.amount(), transaction, AutomationType.INTERNAL);
                    }
                    transaction.commit();
                } else {
                    // Not enough energy, reset progress
                    operatingTicks = 0;
                }
            }
        } else {
            // Cannot function, reset progress
            operatingTicks = 0;
        }

        if (!chemicalTank.isEmpty()) {
            if (chemicalHandlerAbove == null) {
                chemicalHandlerAbove = Capabilities.CHEMICAL.createCache(level, worldPosition.above(), Direction.DOWN);
            }
            ResourceUtils.emit(chemicalHandlerAbove.getCapability(), chemicalTank, outputRate, null);
        }

        setActive(!isBlocked && canFunction() && hasSpace && energyContainer.getAmountAsLong() >= energyContainer.getEnergyPerTick());
        return sendUpdatePacket;
    }

    @Override
    public void setLevel(Level level) {
        super.setLevel(level);
        //Invalidate the cache as if the level changed then it might no longer be valid
        chemicalHandlerAbove = null;
    }

    @Override
    public void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);
        output.putInt(SerializationConstants.PROGRESS, operatingTicks);
    }

    @Override
    public void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        operatingTicks = input.getIntOr(SerializationConstants.PROGRESS, operatingTicks);
    }

    @Override
    public void recalculateUpgrades(Upgrade upgrade) {
        super.recalculateUpgrades(upgrade);
        if (upgrade == Upgrade.SPEED) {
            ticksRequired = MekanismUtils.getTicks(this, BASE_TICKS_REQUIRED);
            outputRate = BASE_OUTPUT_RATE * (1 + getUpgrades(Upgrade.SPEED));
        }
    }

    @Override
    public int getRedstoneLevel() {
        return ContainerType.CHEMICAL.getRedstoneSignalFromContainer(chemicalTank);
    }

    @Override
    protected boolean makesComparatorDirty(IContainerType<?, ?> type) {
        return type == ContainerType.CHEMICAL;
    }

    @Override
    public List<Component> getInfo(Upgrade upgrade) {
        return UpgradeUtils.getMultScaledInfo(this, upgrade);
    }

    public MachineEnergyContainer<TileEntityAirCompressor> energyContainer() {
        return energyContainer;
    }
}
