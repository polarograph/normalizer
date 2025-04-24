package club.polarite.normalizer.util;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.fabricmc.loader.api.metadata.ModMetadata;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

/**
 * Version fetching tool (installed version & latest available version)
 */
public class VersionTool {
    public static String getInstalledVersion() {
        Optional<ModContainer> container = FabricLoader.getInstance().getModContainer("normalizer");
        if (container.isPresent()) {
            ModMetadata metadata = container.get().getMetadata();
            return metadata.getVersion().getFriendlyString();
        }
        return "";
    }

    public static String fetchLatestVersion() {
        try {
            URL url = new URL("https://api.github.com/repos/polarograph/normalizer/releases/latest");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/vnd.github.v3+json");
            try (InputStream is = conn.getInputStream();
                 Reader reader = new InputStreamReader(is, StandardCharsets.UTF_8)) {
                JsonObject json = JsonParser.parseReader(reader).getAsJsonObject();
                return json.get("tag_name").getAsString();
            }
        } catch (Exception e) {
            return "";
        }
    }
}
