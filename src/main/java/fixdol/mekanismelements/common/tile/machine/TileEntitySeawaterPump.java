package fixdol.mekanismelements.common.tile.machine;

import fixdol.mekanismelements.common.registries.MSBlocks;
import fixdol.mekanismelements.common.registries.MSFluids;
import mekanism.api.AutomationType;
import mekanism.api.IConfigurable;
import mekanism.api.IContentsListener;
import mekanism.api.RelativeSide;
import mekanism.api.SerializationConstants;
import mekanism.api.Upgrade;
import mekanism.api.energy.IEnergyContainer;
import mekanism.api.fluid.IFluidTank;
import mekanism.api.inventory.IInventorySlot;
import mekanism.common.capabilities.Capabilities;
import mekanism.common.capabilities.energy.MachineEnergyContainer;
import mekanism.common.capabilities.fluid.BasicFluidTank;
import mekanism.common.capabilities.holder.container.IContainerHolder;
import mekanism.common.capabilities.holder.container.MekContainerHelper;
import mekanism.common.capabilities.holder.single.BasicSingleHolder;
import mekanism.common.capabilities.holder.single.ISingleContainerHolder;
import mekanism.common.component.containers.type.ContainerType;
import mekanism.common.component.containers.type.IContainerType;
import mekanism.common.integration.computer.SpecialComputerMethodWrapper;
import mekanism.common.integration.computer.annotation.WrappingComputerMethod;
import mekanism.common.inventory.slot.EnergyInventorySlot;
import mekanism.common.inventory.slot.FluidInventorySlot;
import mekanism.common.inventory.slot.OutputInventorySlot;
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
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.neoforged.neoforge.capabilities.BlockCapabilityCache;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.transfer.ResourceHandler;
import net.neoforged.neoforge.transfer.fluid.FluidResource;
import net.neoforged.neoforge.transfer.transaction.Transaction;
import org.jetbrains.annotations.UnknownNullability;
import org.jspecify.annotations.Nullable;

import java.util.List;

public class TileEntitySeawaterPump extends TileEntityConfigurableMachine implements IConfigurable {
    private static final int BASE_TICKS_REQUIRED = 19;
    private static final int BASE_OUTPUT_RATE = 256;
    private static final long MAX_FLUID = 10_000;
    public static final FluidStack SEAWATER_STACK = MSFluids.SEAWATER.asStack(200);

    @UnknownNullability//Initialized via getInitialFluidTanks
    @WrappingComputerMethod(wrapper = SpecialComputerMethodWrapper.ComputerFluidTankWrapper.class, methodNames = {"getFluid", "getFluidCapacity", "getFluidNeeded", "getFluidFilledPercentage"}, docPlaceholder = "buffer tank")
    public BasicFluidTank fluidTank;
    public int ticksRequired = BASE_TICKS_REQUIRED;

    public int operatingTicks;
    private int outputRate = BASE_OUTPUT_RATE;
    @Nullable
    private BlockCapabilityCache<ResourceHandler<FluidResource>, @Nullable Direction> fluidHandlerAbove;

    @UnknownNullability//Initialized via getInitialEnergyContainer
    private MachineEnergyContainer<TileEntitySeawaterPump> energyContainer;

    @UnknownNullability//Initialized via getInitialInventory
    @WrappingComputerMethod(wrapper = SpecialComputerMethodWrapper.ComputerIInventorySlotWrapper.class, methodNames = "getInputItem", docPlaceholder = "")
    FluidInventorySlot inputSlot;

    @UnknownNullability//Initialized via getInitialInventory
    @WrappingComputerMethod(wrapper = SpecialComputerMethodWrapper.ComputerIInventorySlotWrapper.class, methodNames = "getOutputItem", docPlaceholder = "")
    OutputInventorySlot outputSlot;

    @UnknownNullability//Initialized via getInitialInventory
    @WrappingComputerMethod(wrapper = SpecialComputerMethodWrapper.ComputerIInventorySlotWrapper.class, methodNames = "getEnergyItem", docPlaceholder = "")
    private EnergyInventorySlot energySlot;

    public TileEntitySeawaterPump(BlockPos pos, BlockState state) {
        super(MSBlocks.SEAWATER_PUMP, pos, state);
        configComponent.setupItemIOConfig(List.of(inputSlot), List.of(outputSlot), energySlot, true);

        // Fluid Output Config - TOP, RIGHT and FRONT sides
        ConfigInfo fluidConfig = configComponent.setupOutputConfig(TransmissionType.FLUID, fluidTank);
        fluidConfig.setDataType(DataType.OUTPUT, RelativeSide.TOP);
        fluidConfig.setDataType(DataType.OUTPUT, RelativeSide.RIGHT);
        fluidConfig.setDataType(DataType.OUTPUT, RelativeSide.FRONT);

        // Energy Config - all sides accept
        ConfigInfo energyConfig = configComponent.setupInputConfig(TransmissionType.ENERGY, energyContainer);
        for (RelativeSide side : EnumUtils.SIDES) {
            energyConfig.setDataType(DataType.INPUT, side);
        }

        ejectorComponent.setOutputData(configComponent, TransmissionType.ITEM)
                .setCanEject(type -> canFunction());
        ejectorComponent.setOutputData(configComponent, TransmissionType.FLUID)
                .setCanEject(type -> canFunction());
    }

    @Override
    protected IContainerHolder<IFluidTank> getInitialFluidTanks(IContentsListener listener) {
        MekContainerHelper<IFluidTank> builder = MekContainerHelper.forSide(facingSupplier);
        builder.addContainer(fluidTank = BasicFluidTank.output(MAX_FLUID, listener), RelativeSide.TOP);
        return builder.build();
    }

    @Override
    protected ISingleContainerHolder<IEnergyContainer> getInitialEnergyContainer(IContentsListener listener) {
        energyContainer = MachineEnergyContainer.input(this, listener);
        return new BasicSingleHolder<>(energyContainer, facingSupplier, BACK_ONLY);
    }

    @Override
    protected IContainerHolder<IInventorySlot> getInitialInventory(IContentsListener listener) {
        MekContainerHelper<IInventorySlot> builder = MekContainerHelper.forSide(facingSupplier);
        builder.addContainer(inputSlot = FluidInventorySlot.drain(fluidTank, listener, 28, 20), RelativeSide.TOP);
        builder.addContainer(outputSlot = OutputInventorySlot.at(listener, 28, 51), RelativeSide.BOTTOM);
        builder.addContainer(energySlot = EnergyInventorySlot.fillOrConvert(energyContainer, this::getLevel, listener, 143, 35), RelativeSide.BACK);
        return builder.build();
    }

    @Override
    protected boolean onUpdateServer(ServerLevel level) {
        boolean sendUpdatePacket = super.onUpdateServer(level);
        if (level.getBiome(getBlockPos()).is(BiomeTags.IS_OCEAN)) {
            BlockState belowState = level.getBlockState(getBlockPos().below());
            if (belowState.getFluidState().isSource() && belowState.getFluidState().getType() == Fluids.WATER) {

                energySlot.fillContainerOrConvert(null);
                inputSlot.drainTankIntoSlot(outputSlot, null);

                FluidResource seawater = FluidResource.of(SEAWATER_STACK);
                if (canFunction() && SEAWATER_STACK.amount() <= fluidTank.getNeededAsInt(seawater)) {
                    int energyPerTick = energyContainer.getEnergyPerTick();
                    try (Transaction transaction = Transaction.openRoot()) {
                        if (energyContainer.extract(energyPerTick, transaction, AutomationType.INTERNAL) == energyPerTick) {
                            operatingTicks++;
                            if (operatingTicks >= ticksRequired) {
                                operatingTicks = 0;
                                fluidTank.insert(seawater, SEAWATER_STACK.amount(), transaction, AutomationType.INTERNAL);
                                transaction.commit();
                            }
                        }
                    }
                }
                if (!fluidTank.isEmpty()) {
                    if (fluidHandlerAbove == null) {
                        fluidHandlerAbove = Capabilities.FLUID.createCache(level, worldPosition.above(), Direction.DOWN);
                    }
                    ResourceUtils.emit(fluidHandlerAbove.getCapability(), fluidTank, outputRate, null);
                }
            }
        }
        return sendUpdatePacket;
    }

    @Override
    public void setLevel(Level level) {
        super.setLevel(level);
        //Invalidate the cache as if the level changed then it might no longer be valid
        fluidHandlerAbove = null;
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
    public InteractionResult onSneakRightClick(Level level, Player player) {
        return InteractionResult.PASS;
    }

    @Override
    public InteractionResult onRightClick(Level level, Player player) {
        return InteractionResult.PASS;
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
        return ContainerType.FLUID.getRedstoneSignalFromContainer(fluidTank);
    }

    @Override
    protected boolean makesComparatorDirty(IContainerType<?, ?> type) {
        return type == ContainerType.FLUID;
    }

    @Override
    public List<Component> getInfo(Upgrade upgrade) {
        return UpgradeUtils.getMultScaledInfo(this, upgrade);
    }

    public MachineEnergyContainer<TileEntitySeawaterPump> energyContainer() {
        return energyContainer;
    }
}
