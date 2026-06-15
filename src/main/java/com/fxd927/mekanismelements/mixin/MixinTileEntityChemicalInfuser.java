package com.fxd927.mekanismelements.mixin;

import mekanism.api.IContentsListener;
import mekanism.api.chemical.BasicChemicalTank;
import mekanism.api.chemical.attribute.ChemicalAttributeValidator;
import mekanism.api.chemical.Chemical;
import mekanism.api.chemical.ChemicalStack;
import mekanism.api.chemical.IChemicalTank;
import net.minecraft.core.Holder;
import net.minecraft.world.level.block.Block;
import mekanism.api.recipes.MekanismRecipe;
import mekanism.api.recipes.cache.CachedRecipe;
import mekanism.common.capabilities.holder.chemical.ChemicalTankHelper;
import mekanism.common.capabilities.holder.chemical.IChemicalTankHolder;
import mekanism.common.recipe.lookup.IEitherSideRecipeLookupHandler.EitherSideChemicalRecipeLookupHandler;
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
public abstract class MixinTileEntityChemicalInfuser extends TileEntityRecipeMachine<MekanismRecipe<?>> {
    @Shadow
    public IChemicalTank leftTank;
    @Shadow
    public IChemicalTank rightTank;
    @Shadow
    public IChemicalTank centerTank;

    protected MixinTileEntityChemicalInfuser(Holder<Block> blockProvider, BlockPos pos, BlockState state, List<CachedRecipe.OperationTracker.RecipeError> errorTypes) {
        super(blockProvider, pos, state, errorTypes);
    }

    @Redirect(method = "getInitialChemicalTanks", at = @At(value = "INVOKE", target = "Lmekanism/common/capabilities/holder/chemical/ChemicalTankHelper;build()Lmekanism/common/capabilities/holder/chemical/IChemicalTankHolder;"))
    public IChemicalTankHolder getInitialGasTanksRedirect(ChemicalTankHelper instance, IContentsListener listener, IContentsListener recipeCacheListener, IContentsListener recipeCacheUnpauseListener) {
        ChemicalTankHelper builder = ChemicalTankHelper.forSideWithConfig(this);
        TileEntityChemicalInfuser self = (TileEntityChemicalInfuser) (Object) this;
        builder.addTank(leftTank = BasicChemicalTank.inputModern(MAX_GAS, chemical -> self.containsRecipe(chemical, self.rightTank != null ? self.rightTank.getStack() : ChemicalStack.EMPTY), chemical -> self.containsRecipe(chemical), recipeCacheListener));
        builder.addTank(rightTank = BasicChemicalTank.inputModern(MAX_GAS, chemical -> self.containsRecipe(chemical, self.leftTank != null ? self.leftTank.getStack() : ChemicalStack.EMPTY), chemical -> self.containsRecipe(chemical), recipeCacheListener));
        builder.addTank(centerTank = BasicChemicalTank.output(MAX_GAS, recipeCacheUnpauseListener));
        return builder.build();
    }
}

