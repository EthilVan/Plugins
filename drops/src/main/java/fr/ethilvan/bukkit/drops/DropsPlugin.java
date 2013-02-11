package fr.ethilvan.bukkit.drops;

import java.util.HashSet;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class DropsPlugin extends JavaPlugin {

    private HashSet<UUID> spawnerSpawnedMobs;
    private EcoConfig ecoConfig;

    @Override
    public void onEnable() {
        PluginManager pm = Bukkit.getPluginManager();

        spawnerSpawnedMobs = new HashSet<UUID>();
        ecoConfig = new EcoConfig(this);
        Listener listener = new DropsListener(this); 
        pm.registerEvents(listener, this);
    }

    public HashSet<UUID> getSpawnerSpawnedMobs() {
        return spawnerSpawnedMobs;
    }

    public EcoConfig getEcoConfig() {
        return ecoConfig;
    }

}
