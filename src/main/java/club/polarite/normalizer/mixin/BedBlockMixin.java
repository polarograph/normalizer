package club.polarite.normalizer.mixin;

import club.polarite.normalizer.config.ConfigManager;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.BedBlock;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.ObjectInputFilter;

/**
 * Disables bed bounce.
 */
@Mixin(BedBlock.class)
public class BedBlockMixin {
    @Inject(method = "updateEntityMovementAfterFallOn", at = @At("HEAD"), cancellable = true)
    private void disableBounce(BlockGetter blockGetter, Entity entity, CallbackInfo ci) {
        if (ConfigManager.getConfig().disableBedBounce && !ConfigManager.isWhitelisted) {
            ci.cancel();
        }
    }
}
