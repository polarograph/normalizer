package club.polarite.normalizer.config;

import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;

import net.minecraft.SharedConstants;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

import java.util.Arrays;
import java.util.Objects;

/**
 * Config screen
 * Localizations in resources/assets/normalizer/lang/*.json
 */
public class NormalizerConfigScreen {
    public static Screen create(Screen parent) {
        NormalizerConfig config = ConfigManager.getConfig();
        String mcVersion = SharedConstants.getCurrentVersion().getName();

        ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(parent)
                .setTitle(Component.translatable("config.normalizer.title"))
                .setSavingRunnable(ConfigManager::saveConfig);

        ConfigCategory general = builder.getOrCreateCategory(Component.translatable("config.normalizer.category.general"));
        ConfigCategory servers = builder.getOrCreateCategory(Component.translatable("config.normalizer.category.servers"));
        ConfigEntryBuilder entryBuilder = builder.entryBuilder();

        general.addEntry(entryBuilder
                .startBooleanToggle(
                        Component.translatable("config.normalizer.option.restoreSneakingHitbox"),
                        config.restoreSneakingHitbox
                )
                .setDefaultValue(true)
                .setTooltip(Component.translatable("config.normalizer.tooltip.restoreSneakingHitbox"))
                .setSaveConsumer(value -> config.restoreSneakingHitbox = value)
                .build()
        );

        general.addEntry(entryBuilder
                .startBooleanToggle(
                        Component.translatable("config.normalizer.option.disableSwimming"),
                        config.disableSwimming
                )
                .setDefaultValue(true)
                .setTooltip(Component.translatable("config.normalizer.tooltip.disableSwimming"))
                .setSaveConsumer(value -> config.disableSwimming = value)
                .build()
        );

        general.addEntry(entryBuilder
                .startBooleanToggle(
                        Component.translatable("config.normalizer.option.disableCrawling"),
                        config.disableCrawling
                )
                .setDefaultValue(true)
                .setTooltip(Component.translatable("config.normalizer.tooltip.disableCrawling"))
                .setSaveConsumer(value -> config.disableCrawling = value)
                .build()
        );

        general.addEntry(entryBuilder
                .startBooleanToggle(
                        Component.translatable("config.normalizer.option.fixSneakDesync"),
                        config.fixSneakDesync
                )
                .setDefaultValue(false)
                .setTooltip(Component.translatable("config.normalizer.tooltip.fixSneakDesync"))
                .setSaveConsumer(value -> config.fixSneakDesync = value)
                .build()
        );

        general.addEntry(entryBuilder
                .startBooleanToggle(
                        Component.translatable("config.normalizer.option.restoreLegacyBuckets"),
                        config.restoreLegacyBuckets
                )
                .setDefaultValue(false)
                .setTooltip(Component.translatable("config.normalizer.tooltip.restoreLegacyBuckets"))
                .setSaveConsumer(value -> config.restoreLegacyBuckets = value)
                .requireRestart()
                .build()
        );

        if (!Objects.equals(mcVersion, "1.21.4")) {
            general.addEntry(entryBuilder
                    .startBooleanToggle(
                            Component.translatable("config.normalizer.option.restoreSprintCancel"),
                            config.restoreSprintCancel
                    )
                    .setDefaultValue(true)
                    .setTooltip(Component.translatable("config.normalizer.tooltip.restoreSprintCancel"))
                    .setSaveConsumer(value -> config.restoreSprintCancel = value)
                    .build()
            );
        }

        servers.addEntry(entryBuilder
                .startStrList(
                        Component.translatable("config.normalizer.option.serverWhitelist"),
                        config.serverWhitelist
                )
                .setDefaultValue(Arrays.asList("*.hypixel.net"))
                .setTooltip(Component.translatable("config.normalizer.tooltip.serverWhitelist"))
                .setSaveConsumer(list -> config.serverWhitelist = list)
                .build()
        );

        servers.addEntry(entryBuilder
                .startBooleanToggle(
                        Component.translatable("config.normalizer.option.multiplayerOnly"),
                        config.multiplayerOnly
                )
                .setDefaultValue(true)
                .setTooltip(Component.translatable("config.normalizer.tooltip.multiplayerOnly"))
                .setSaveConsumer(value -> config.multiplayerOnly = value)
                .build()
        );

        return builder.build();
    }
}