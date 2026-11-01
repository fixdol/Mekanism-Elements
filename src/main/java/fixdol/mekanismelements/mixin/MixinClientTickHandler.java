package fixdol.mekanismelements.mixin;

import fixdol.mekanismelements.common.registries.MSGases;
import mekanism.api.chemical.ChemicalResource;
import mekanism.client.ClientTickHandler;
import mekanism.common.capabilities.Capabilities;
import mekanism.common.item.interfaces.IJetpackItem;
import mekanism.common.item.interfaces.IJetpackItem.JetpackMode;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.transfer.ResourceHandler;
import net.neoforged.neoforge.transfer.item.ItemResource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = ClientTickHandler.class, remap = false)
public class MixinClientTickHandler {

    @Inject(method = "onTick", at = @At("TAIL"), remap = false)
    private void onClientTickEnd(ClientTickEvent.Pre event, CallbackInfo ci) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null) return;

        ItemResource primaryJetpack = IJetpackItem.getPrimaryJetpack(player);
        if (!primaryJetpack.isEmpty()) {
            IJetpackItem jetpackItem = (IJetpackItem) primaryJetpack.getItem();
            JetpackMode primaryMode = jetpackItem.getJetpackMode(primaryJetpack);
            JetpackMode mode = IJetpackItem.getPlayerJetpackMode(player, primaryMode, p -> p.input.keyPresses.jump());

            if (mode == JetpackMode.HOVER) {
                ResourceHandler<ChemicalResource> chemicalHandler = Capabilities.CHEMICAL.getQueryOnlyCapability(primaryJetpack);
                if (chemicalHandler != null && chemicalHandler.size() > 0 && chemicalHandler.getResource(0).is(MSGases.AMMONIA)) {
                    Vec3 motion = player.getDeltaMovement();
                    boolean isMoving = player.input.getMoveVector().y != 0 || player.input.getMoveVector().x != 0;
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
