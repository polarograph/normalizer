package club.polarite.normalizer.mixin;

import club.polarite.normalizer.config.ConfigManager;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.protocol.game.ClientboundSetEntityDataPacket;
import net.minecraft.network.syncher.EntityDataSerializers;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Fixes MC-159163
 */
@Mixin(ClientPacketListener.class)
public class SneakDesyncFixMixin {
    @Inject(method = "handleSetEntityData", at = @At("HEAD"))
    private void onHandleSetEntityData(ClientboundSetEntityDataPacket packet, CallbackInfo ci) {
        boolean fixSneakDesync = ConfigManager.getConfig().fixSneakDesync;

        Minecraft minecraft = Minecraft.getInstance();
        Entity entity = minecraft.level.getEntity(packet.id());

        if (entity != null && entity == minecraft.player && fixSneakDesync) {
            packet.packedItems().removeIf(item -> item.serializer().equals(EntityDataSerializers.POSE));
        }
    }
}