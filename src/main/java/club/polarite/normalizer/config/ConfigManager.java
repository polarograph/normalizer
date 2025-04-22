package club.polarite.normalizer.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Config R/W manager
 */
public class ConfigManager {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final File CONFIG_FILE = new File(FabricLoader.getInstance().getConfigDir().toFile(), "normalizer.json");

    private static NormalizerConfig config;

    public static boolean isWhitelisted = false;

    public static NormalizerConfig getConfig() {
        if (config == null) {
            loadConfig();
        }
        return config;
    }

    public static void loadConfig() {
        if (CONFIG_FILE.exists()) {
            try (FileReader reader = new FileReader(CONFIG_FILE)) {
                config = GSON.fromJson(reader, NormalizerConfig.class);
            } catch (IOException e) {
                e.printStackTrace();
                config = new NormalizerConfig();
            }
        } else {
            config = new NormalizerConfig();
        }
    }

    public static void saveConfig() {
        try (FileWriter writer = new FileWriter(CONFIG_FILE)) {
            GSON.toJson(config, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
