package club.polarite.normalizer;

import club.polarite.normalizer.config.ConfigManager;
import club.polarite.normalizer.config.NormalizerConfigScreen;

import club.polarite.normalizer.util.Ticks;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.minecraft.client.Minecraft;

import net.minecraft.client.gui.screens.ChatScreen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.io.ObjectInputFilter;

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

        ClientPlayConnectionEvents.JOIN.register((handler, sender, client) -> {
            boolean multiplayer = !client.isSingleplayer();

            if (multiplayer) {
                String full = client.getCurrentServer().ip;
                String host = full.contains(":")
                        ? full.split(":", 2)[0]
                        : full;
                ConfigManager.isWhitelisted = false;
                ConfigManager.isWhitelisted = ConfigManager
                        .getConfig()
                        .serverWhitelist
                        .stream()
                        .anyMatch(pattern -> {
                            if (pattern.startsWith("*.")) {
                                String domain = pattern.substring(2);
                                return host.equals(domain) || host.endsWith("." + domain);
                            } else {
                                return host.equals(pattern);
                            }
                        });
            } else {
                ConfigManager.isWhitelisted = !ConfigManager.getConfig().multiplayerOnly;
            }

            if (ConfigManager.getConfig().fixSneakDesync && ConfigManager.isWhitelisted && multiplayer) {
                Ticks.runAfter(40, () -> {
                    LocalPlayer player = client.player;
                    if (player != null) {
                        Component message = Component.translatable("club.polarite.normalizer.sneakDesyncFixWarningMessage");
                        player.displayClientMessage(message, false);

                        client.level.playLocalSound(player.getX(), player.getY(), player.getZ(), SoundEvents.ANVIL_DESTROY, SoundSource.MASTER, 1.0F, 1.0F, false);
                    }
                });
            }
        });
    }
}