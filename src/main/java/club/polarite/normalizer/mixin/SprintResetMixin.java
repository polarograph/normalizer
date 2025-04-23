package club.polarite.normalizer.mixin;

import net.minecraft.client.player.ClientInput;
import net.minecraft.client.player.LocalPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LocalPlayer.class)
public class SneakResetMixin {
    @Shadow protected ClientInput input;
    @Shadow
    protected int sprintTriggerTime;

    @Shadow public abstract boolean isFallFlying();
    @Shadow
    protected abstract boolean hasBlindness();
    @Shadow public abstract boolean isMovingSlowly();
    public abstract boolean isPassenger();
    public abstract boolean isRidingCamel();
    @Shadow public abstract boolean isUsingItem();
    @Shadow public abstract boolean isUnderWater();

    public abstract void setSprinting(boolean sprinting);

    private boolean shouldStopSprinting() {
        return this.isFallFlying() || this.hasBlindness() || this.isMovingSlowly() || this.isPassenger() && !this.isRidingCamel() || this.isUsingItem() && !this.isPassenger() && !this.isUnderWater();
    }

    @Inject(
            method = "aiStep",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/player/ClientInput;tick()V",
                    shift = At.Shift.AFTER
            )
    )
    private void backportSprintFix(CallbackInfo ci) {
        if (this.shouldStopSprinting()) {
            this.setSprinting(false);
        }
    }
}