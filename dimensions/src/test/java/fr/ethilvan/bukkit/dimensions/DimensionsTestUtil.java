package fr.ethilvan.bukkit.dimensions;

import java.io.IOException;

import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

public class DimensionsTestUtil {

    public static Configuration config;

    static {
        YamlConfiguration yamlConfig = new YamlConfiguration();
        try {
            yamlConfig.load(DimensionsPlugin.class.getResourceAsStream(
                    "/config-test.yml"));
        } catch (IOException exc) {
            exc.printStackTrace();
        } catch (InvalidConfigurationException exc) {
            exc.printStackTrace();
        }

        config = yamlConfig;
    }
}
