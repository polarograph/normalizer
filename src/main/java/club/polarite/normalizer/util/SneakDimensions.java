package club.polarite.normalizer.util;

import net.minecraft.world.entity.EntityDimensions;

public final class SneakDimensions {

    private static float cachedHitboxHeight = Float.NaN;
    private static EntityDimensions cachedDimensions;

    /**
     * Return sneaking to a full block, like 1.8.
     * This is based off of PlayerDimensionsOverrideHelper.kt in NoChangeTheGame
     */
    public static EntityDimensions getCustomCrouchDimensions() {
        final float targetHitboxHeight = 1.8F;

        if (Float.compare(cachedHitboxHeight, targetHitboxHeight) != 0 || cachedDimensions == null) {
            cachedDimensions = EntityDimensions.scalable(0.6F, targetHitboxHeight);
            cachedHitboxHeight = targetHitboxHeight;
        }
        return cachedDimensions;
    }
}
