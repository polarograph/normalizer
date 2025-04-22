package club.polarite.normalizer.util;

import net.minecraft.world.entity.EntityDimensions;

public final class SneakDimensions {
    /**
     * Return sneaking to a full block, like 1.8.
     * This is based off of PlayerDimensionsOverrideHelper.kt in NoChangeTheGame
     */
    public static EntityDimensions getCustomCrouchDimensions() {
        return EntityDimensions.scalable(0.6F, 1.8F);
    }
}
