package club.polarite.normalizer.mixin;

import club.polarite.normalizer.config.ConfigManager;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.BedBlock;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/**
 * Disables bed bounce.
 */
@Mixin(BedBlock.class)
public class BedBlockMixin {
    @Redirect(
            method = "updateEntityMovementAfterFallOn",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;isSuppressingBounce()Z")
    )
    private boolean fakeSuppressBounce(Entity entity) {
        return (ConfigManager.getConfig().disableBedBounce && ConfigManager.isWhitelisted) || entity.isSuppressingBounce();
    }
}