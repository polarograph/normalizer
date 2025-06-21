package club.polarite.normalizer.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import club.polarite.normalizer.config.ConfigManager;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Disables crawling completely, along with auto sneaking.
 */
@Mixin(Player.class)
public abstract class DisableCrawlMixin extends LivingEntity {
    protected DisableCrawlMixin(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Shadow
    protected abstract boolean canPlayerFitWithinBlocksAndEntitiesWhen(Pose pose);

    @Inject(
            method = "updatePlayerPose",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/player/Player;isSpectator()Z"
            ),
            cancellable = true
    )
    protected void disablePoseToFit(CallbackInfo ci, @Local(ordinal = 0) Pose pose) {
        boolean disableCrawling = ConfigManager.getConfig().disableCrawling;
        if (!disableCrawling) {
            return;
        }
        if (!ConfigManager.isWhitelisted) {
            return;
        }

        Pose pose2;
        if (this.isSpectator() || this.isPassenger() || this.canPlayerFitWithinBlocksAndEntitiesWhen(pose)) {
            pose2 = pose;
        } else if (this.canPlayerFitWithinBlocksAndEntitiesWhen(Pose.CROUCHING)) {
            pose2 = Pose.CROUCHING;
        } else {
            pose2 = pose;
        }
        this.setPose(pose2);
        ci.cancel();
    }
}