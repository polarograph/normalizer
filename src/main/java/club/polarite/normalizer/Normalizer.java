package club.polarite.normalizer;

import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Normalizer implements ModInitializer {
    public static final String MOD_ID = "normalizer";

    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("Normal1.8zer initialized");
    }
}