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
import fr.ethilvan.bukkit.drops.customDrops.CustomDrop;
import fr.ethilvan.bukkit.drops.customDrops.CustomDrops;

public class DropsPlugin extends JavaPlugin {

    private HashSet<UUID> spawnerSpawnedMobs;
    private EcoConfig ecoConfig;
    private CustomDrops customDrops;

    @Override
    public void onEnable() {
        PluginManager pm = Bukkit.getPluginManager();

        spawnerSpawnedMobs = new HashSet<UUID>();
        ecoConfig = new EcoConfig(this);
        loadData();
        Listener listener = new DropsListener(this); 
        pm.registerEvents(listener, this);
    }

    @Override
    public void onDisable() {
        writeData();
    }

    public HashSet<UUID> getSpawnerSpawnedMobs() {
        return spawnerSpawnedMobs;
    }

    public EcoConfig getEcoConfig() {
        return ecoConfig;
    }

    public List<CustomDrop> getCustomDrops() {
        return customDrops.getCustomDrops();
    }

    private GsonLoader getGsonLoader() {
        Gson gson = new GsonBuilder()
        .registerTypeAdapterFactory(new DirectionTypeAdapterFactory())
        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_DASHES)
        .setPrettyPrinting().create();
        return new GsonLoader(gson, this);
    }

    private void loadData() {
        try {
            customDrops = getGsonLoader().loadOrCreate("customDrops.json",
                    CustomDrops.class);
        } catch (GsonLoadException exc) {
            getLogger().log(Level.SEVERE, "Unable to load customDrops.json", exc);
            customDrops = new CustomDrops();
        }
    }

    private void writeData() {
        try {
            getGsonLoader().write("customDrops.json", customDrops);
        } catch (GsonLoadException exc) {
            getLogger().log(Level.SEVERE, "Unable to write customDrops.json", exc);
        }
    }
}
