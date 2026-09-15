package fixdol.mekanismelements.mixin;

import mekanism.api.chemical.gas.Gas;

import mekanism.api.providers.IBlockProvider;

import org.spongepowered.asm.mixin.injection.At;
import net.minecraft.world.level.block.Block;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import mekanism.api.recipes.cache.CachedRecipe;
import mekanism.api.chemical.ChemicalTankBuilder;
import mekanism.common.capabilities.holder.chemical.ChemicalTankHelper;
import mekanism.api.chemical.gas.GasStack;
import net.minecraft.core.Holder;
import mekanism.common.capabilities.holder.chemical.IChemicalTankHolder;
import mekanism.api.IContentsListener;
import mekanism.api.chemical.gas.IGasTank;
import java.util.List;
import mekanism.api.recipes.MekanismRecipe;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import mekanism.common.recipe.lookup.IEitherSideRecipeLookupHandler.EitherSideChemicalRecipeLookupHandler;
import mekanism.common.tile.machine.TileEntityChemicalInfuser;
import mekanism.common.tile.prefab.TileEntityRecipeMachine;
import org.spongepowered.asm.mixin.injection.Redirect;


import static mekanism.common.tile.machine.TileEntityChemicalInfuser.MAX_GAS;

@Mixin(value = TileEntityChemicalInfuser.class, remap = false)
public abstract class MixinTileEntityChemicalInfuser extends TileEntityRecipeMachine<MekanismRecipe> {
    @Shadow
    public IGasTank leftTank;
    @Shadow
    public IGasTank rightTank;
    @Shadow
    public IGasTank centerTank;

    protected MixinTileEntityChemicalInfuser(IBlockProvider blockProvider, BlockPos pos, BlockState state, List<CachedRecipe.OperationTracker.RecipeError> errorTypes) {
        super(blockProvider, pos, state, errorTypes);
    }

    @Redirect(method = "getInitialGasTanks", at = @At(value = "INVOKE", target = "Lmekanism/common/capabilities/holder/chemical/ChemicalTankHelper;build()Lmekanism/common/capabilities/holder/chemical/IChemicalTankHolder;"))
    public IChemicalTankHolder<Gas, GasStack, IGasTank> getInitialGasTanksRedirect(ChemicalTankHelper instance, IContentsListener listener, IContentsListener recipeCacheListener) {
        ChemicalTankHelper builder = ChemicalTankHelper.forSideGasWithConfig(this::getDirection, this::getConfig);
        TileEntityChemicalInfuser self = (TileEntityChemicalInfuser) (Object) this;
        builder.addTank(leftTank = ChemicalTankBuilder.GAS.input(MAX_GAS, chemical -> self.containsRecipe(chemical, self.rightTank != null ? self.rightTank.getStack() : GasStack.EMPTY), chemical -> self.containsRecipe(chemical), recipeCacheListener));
        builder.addTank(rightTank = ChemicalTankBuilder.GAS.input(MAX_GAS, chemical -> self.containsRecipe(chemical, self.leftTank != null ? self.leftTank.getStack() : GasStack.EMPTY), chemical -> self.containsRecipe(chemical), recipeCacheListener));
        builder.addTank(centerTank = ChemicalTankBuilder.GAS.output(MAX_GAS, recipeCacheListener));
        return builder.build();
    }
}

