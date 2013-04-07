package fr.ethilvan.bukkit.drops;

import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import fr.aumgn.bukkitutils.gson.GsonLoadException;
import fr.aumgn.bukkitutils.gson.GsonLoader;
import fr.aumgn.bukkitutils.gson.typeadapter.DirectionTypeAdapterFactory;
import fr.aumgn.bukkitutils.gson.typeadapter.WorldTypeAdapterFactory;
import fr.ethilvan.bukkit.drops.randomDrops.RandomDrop;
import fr.ethilvan.bukkit.drops.randomDrops.RandomDrops;

public class DropsPlugin extends JavaPlugin {

    private HashSet<UUID> spawnerSpawnedMobs;
    private EcoConfig ecoConfig;
    private RandomDrops randomDrops;

    @Override
    public void onEnable() {
        PluginManager pm = Bukkit.getPluginManager();

        spawnerSpawnedMobs = new HashSet<UUID>();
        ecoConfig = new EcoConfig(this);
        writeData();
        Listener listener = new DropsListener(this); 
        pm.registerEvents(listener, this);
    }

    @Override
    public void onDisable() {
        loadData();
    }

    public HashSet<UUID> getSpawnerSpawnedMobs() {
        return spawnerSpawnedMobs;
    }

    public EcoConfig getEcoConfig() {
        return ecoConfig;
    }

    public List<RandomDrop> getRandomDrops() {
        return randomDrops.getRandomDrops();
    }

    private GsonLoader getGsonLoader() {
        Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapterFactory(new DirectionTypeAdapterFactory())
            .registerTypeAdapterFactory(new WorldTypeAdapterFactory())
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_DASHES)
            .setPrettyPrinting()
            .create();
        return new GsonLoader(gson, this);
    }

    private void loadData() {
        try {
            randomDrops = getGsonLoader().loadOrCreate("randomDrops.json",
                    RandomDrops.class);
        } catch (GsonLoadException exc) {
            getLogger().log(Level.SEVERE, "Unable to load randomDrops.json", exc);
            randomDrops = new RandomDrops();
        }
    }

    private void writeData() {
        try {
            getGsonLoader().write("randomDrops.json", randomDrops);
        } catch (GsonLoadException exc) {
            getLogger().log(Level.SEVERE, "Unable to write randomDrops.json", exc);
        }
    }
}
