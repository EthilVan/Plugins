package fr.ethilvan.bukkit.deaths;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.Plugin;

import fr.ethilvan.bukkit.deaths.death.PlayerDeath;
import fr.ethilvan.bukkit.deaths.death.PlayerDeathCause;
import fr.ethilvan.bukkit.deaths.death.PlayerKilledByMob;

public class DeathMessages {

    private DeathsPlugin plugin; 

    public DeathMessages(DeathsPlugin plugin) {
        super();
        this.plugin = plugin;
        File configFile = new File(plugin.getDataFolder(), "config.yml");
        if (!configFile.exists()) {
            saveDefaultConfig(plugin);
        }
    }

    private void saveDefaultConfig(Plugin plugin) {
        List<String> emptyList = Collections.<String>emptyList();
        Configuration config = plugin.getConfig();
        for (PlayerDeathCause dmgCause : PlayerDeathCause.values()) {
            if (dmgCause != PlayerDeathCause.Mob) {
                config.set(dmgCause.name(), emptyList);
            }
        }
        ConfigurationSection mobSection = config.createSection("Mob");
        for (PlayerKilledByMob.Type creatureType : PlayerKilledByMob.Type.values()) {
            mobSection.set(creatureType.name(), emptyList);
        }
        plugin.saveConfig();
    }

    public String getDeathMessage(PlayerDeath death) {
        List<String> messages = plugin.getConfig().getStringList(death.getConfigPath());
        if (messages == null || messages.size() < 1) {
            return null;
        }

        int max = new Random().nextInt(messages.size());
        String message = messages.get(max);
        if (message == null) {
            return null;
        }

        return death.parseMessage(message);
    }
}
