package club.polarite.normalizer;

import club.polarite.normalizer.config.ConfigManager;
import club.polarite.normalizer.config.NormalizerConfigScreen;

import club.polarite.normalizer.util.Ticks;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;

import net.minecraft.client.Minecraft;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Normalizer implements ModInitializer {
    public static final String MOD_ID = "normalizer";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        ConfigManager.loadConfig();
        LOGGER.info("[NORMAL1.8ZER] Normal1.8zer initialized");

        // /normalizerconfig command
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
            dispatcher.register(ClientCommandManager.literal("normalizerconfig")
                    .executes(context -> {
                        Minecraft client = Minecraft.getInstance();
                        client.execute(() -> {
                            Ticks.runAfter(2, () -> {
                                client.setScreen(NormalizerConfigScreen.create(client.screen));
                            });
                        });
                        return 1;
                    }));
        });
    }
}