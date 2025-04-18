package club.polarite.normalizer.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalBooleanRef;
import club.polarite.normalizer.config.ConfigManager;

import net.minecraft.world.entity.Entity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Disables swimming completely.
 */
@Mixin(Entity.class)
public class DisableSwimMixin {
    @Inject(
            method = "setSwimming",
            at = @At("HEAD")
    )
    protected void onSetSwimming(CallbackInfo ci, @Local(argsOnly = true) LocalBooleanRef state) {
        boolean disableSwimming = ConfigManager.getConfig().disableSwimming;
        if (!disableSwimming) {
            return;
        }

        if (!state.get()) {
            return;
        }
        state.set(false);
    }
}
