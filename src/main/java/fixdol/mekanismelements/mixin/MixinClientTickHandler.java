package fixdol.mekanismelements.mixin;

import mekanism.common.capabilities.Capabilities;
import mekanism.api.chemical.gas.GasStack;
import mekanism.api.chemical.gas.IGasHandler;
import net.minecraft.world.item.ItemStack;
import fixdol.mekanismelements.common.registries.MSGases;
import net.minecraft.client.Minecraft;

import mekanism.client.ClientTickHandler;
import mekanism.common.item.interfaces.IJetpackItem;
import mekanism.common.item.interfaces.IJetpackItem.JetpackMode;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = ClientTickHandler.class, remap = false)
public class MixinClientTickHandler {

    @Inject(method = "onTick", at = @At("TAIL"), remap = false)
    private void onClientTickEnd(net.minecraftforge.event.TickEvent.ClientTickEvent event, CallbackInfo ci) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null) return;

        ItemStack primaryJetpack = IJetpackItem.getPrimaryJetpack(player);
        if (!primaryJetpack.isEmpty()) {
            IJetpackItem jetpackItem = (IJetpackItem) primaryJetpack.getItem();
            JetpackMode primaryMode = jetpackItem.getJetpackMode(primaryJetpack);
            JetpackMode mode = IJetpackItem.getPlayerJetpackMode(player, primaryMode, () -> player.input.jumping);
            
            if (mode == JetpackMode.HOVER) {
                IGasHandler chemicalHandler = primaryJetpack.getCapability(Capabilities.GAS_HANDLER).resolve().orElse(null);
                if (chemicalHandler != null && chemicalHandler.getTanks() > 0) {
                    GasStack stored = chemicalHandler.getChemicalInTank(0);
                    if (stored.getType() == MSGases.AMMONIA.get()) {
                        Vec3 motion = player.getDeltaMovement();
                        boolean isMoving = player.input.forwardImpulse != 0 || player.input.leftImpulse != 0;
                        if (isMoving) {
                            double speedSq = motion.x * motion.x + motion.z * motion.z;
                            if (speedSq < 0.6) {
                                // Boost horizontal speed artificially since hover only controls vertical.
                                // The 1.1 multiplier overcomes vanilla air drag (0.91) to create acceleration and higher top speed.
                                player.setDeltaMovement(motion.x * 1.1, motion.y, motion.z * 1.1);
                            }
                        } else {
                            // Apply a stronger braking force when the player releases the keys (sliding on ice fix)
                            player.setDeltaMovement(motion.x * 0.5, motion.y, motion.z * 0.5);
                        }
                    }
                }
            }
        }
    }
}
