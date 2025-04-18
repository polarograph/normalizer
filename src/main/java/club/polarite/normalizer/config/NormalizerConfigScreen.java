package club.polarite.normalizer.config;

import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

/**
 * Config screen
 * Localizations in resources/assets/normalizer/lang/*.json
 */
public class NormalizerConfigScreen {
    public static Screen create(Screen parent) {
        NormalizerConfig config = ConfigManager.getConfig();

        ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(parent)
                .setTitle(Component.translatable("config.normalizer.title"))
                .setSavingRunnable(ConfigManager::saveConfig);

        ConfigCategory general = builder.getOrCreateCategory(Component.translatable("config.normalizer.category.general"));
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
                        Component.translatable("config.normalizer.option.restoreLegacyBuckets"),
                        config.restoreLegacyBuckets
                )
                .setDefaultValue(true)
                .setTooltip(Component.translatable("config.normalizer.tooltip.restoreLegacyBuckets"))
                .setSaveConsumer(value -> config.restoreLegacyBuckets = value)
                .build()
        );

        return builder.build();
    }
}