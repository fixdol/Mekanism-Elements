package com.fxd927.mekanismelements.mixin;

import mekanism.api.IContentsListener;
import mekanism.api.chemical.ChemicalTankBuilder;
import mekanism.api.chemical.attribute.ChemicalAttributeValidator;
import mekanism.api.chemical.gas.Gas;
import mekanism.api.chemical.gas.GasStack;
import mekanism.api.chemical.gas.IGasTank;
import mekanism.api.providers.IBlockProvider;
import mekanism.api.recipes.ChemicalInfuserRecipe;
import mekanism.api.recipes.cache.CachedRecipe;
import mekanism.common.capabilities.holder.chemical.ChemicalTankHelper;
import mekanism.common.capabilities.holder.chemical.IChemicalTankHolder;
import mekanism.common.recipe.lookup.IEitherSideRecipeLookupHandler;
import mekanism.common.tile.machine.TileEntityChemicalInfuser;
import mekanism.common.tile.prefab.TileEntityRecipeMachine;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.List;

import static mekanism.common.tile.machine.TileEntityChemicalInfuser.MAX_GAS;

@Mixin(value = TileEntityChemicalInfuser.class, remap = false)
public abstract class MixinTileEntityChemicalInfuser extends TileEntityRecipeMachine<ChemicalInfuserRecipe> implements IEitherSideRecipeLookupHandler.EitherSideChemicalRecipeLookupHandler<Gas, GasStack, ChemicalInfuserRecipe> {
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
        ChemicalTankHelper<Gas, GasStack, IGasTank> builder = ChemicalTankHelper.forSideGasWithConfig(this::getDirection, this::getConfig);
        builder.addTank(leftTank = ChemicalTankBuilder.GAS.create(MAX_GAS, ChemicalTankHelper.radioactiveInputTankPredicate(() -> centerTank), ChemicalTankBuilder.GAS.alwaysTrueBi, this::containsRecipe, ChemicalAttributeValidator.ALWAYS_ALLOW, recipeCacheListener));
        builder.addTank(rightTank = ChemicalTankBuilder.GAS.create(MAX_GAS, ChemicalTankHelper.radioactiveInputTankPredicate(() -> centerTank), ChemicalTankBuilder.GAS.alwaysTrueBi, this::containsRecipe, ChemicalAttributeValidator.ALWAYS_ALLOW, recipeCacheListener));
        builder.addTank(centerTank = ChemicalTankBuilder.GAS.output(MAX_GAS, listener));
        return builder.build();
    }
}
