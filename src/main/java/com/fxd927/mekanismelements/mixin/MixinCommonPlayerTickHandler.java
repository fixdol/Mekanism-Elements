package com.fxd927.mekanismelements.mixin;

import mekanism.common.CommonPlayerTickHandler;
import mekanism.common.item.interfaces.IJetpackItem;
import mekanism.common.item.interfaces.IJetpackItem.JetpackMode;
import mekanism.common.capabilities.Capabilities;
import mekanism.api.chemical.IChemicalHandler;
import mekanism.api.chemical.ChemicalStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import com.fxd927.mekanismelements.common.registries.MSGases;
import mekanism.common.Mekanism;
import mekanism.common.base.KeySync;

@Mixin(value = CommonPlayerTickHandler.class, remap = false)
public class MixinCommonPlayerTickHandler {

    @Inject(method = "tickEnd", at = @At("TAIL"), remap = false)
    private void onTickEnd(Player player, CallbackInfo ci) {
        ItemStack primaryJetpack = IJetpackItem.getPrimaryJetpack(player);
        if (!primaryJetpack.isEmpty()) {
            IJetpackItem jetpackItem = (IJetpackItem) primaryJetpack.getItem();
            JetpackMode primaryMode = jetpackItem.getJetpackMode(primaryJetpack);
            JetpackMode mode = IJetpackItem.getPlayerJetpackMode(player, primaryMode, p -> Mekanism.keyMap.has(p.getUUID(), KeySync.ASCEND));
            
            if (mode == JetpackMode.HOVER) {
                IChemicalHandler chemicalHandler = Capabilities.CHEMICAL.getCapability(primaryJetpack);
                if (chemicalHandler != null && chemicalHandler.getChemicalTanks() > 0) {
                    ChemicalStack stored = chemicalHandler.getChemicalInTank(0);
                    if (stored.is(MSGases.AMMONIA)) {
                        // The server doesn't know the exact client input keys here,
                        // so we let the client handle horizontal acceleration and braking 
                        // in MixinClientTickHandler to prevent rubber-banding and sliding.
                    }
                }
            }
        }
    }
}
