package club.polarite.normalizer.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Config values
 */
public class NormalizerConfig {
    public boolean restoreSneakingHitbox = true;
    public boolean disableSwimming = true;
    public boolean disableCrawling = true;
    public boolean fixSneakDesync = false;
    public boolean restoreLegacyBuckets = false;
    public boolean restoreSprintCancel = true;

    public List<String> serverWhitelist = new ArrayList<>(
            Arrays.asList("*.hypixel.net")
    );

    public boolean multiplayerOnly = true;
}