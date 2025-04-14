package club.polarite.normalizer.mixin;

import club.polarite.normalizer.util.SneakDimensions;

import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


/**
 * Overrides the default hitbox when the player is sneaking.
 */
@Mixin(Player.class)
public abstract class SneakHitboxMixin {
    @Inject(method = "getDefaultDimensions", at = @At("HEAD"), cancellable = true)
    private static void modifySneakDimensions(Pose pose, CallbackInfoReturnable<EntityDimensions> cir) {
        if (pose == Pose.CROUCHING) {
            EntityDimensions customDimensions = SneakDimensions.getCustomCrouchDimensions();
            cir.setReturnValue(customDimensions);
        }
    }
}
