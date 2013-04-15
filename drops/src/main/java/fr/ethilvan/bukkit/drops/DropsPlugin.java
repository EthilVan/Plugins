package fr.ethilvan.bukkit.drops;

import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import fr.aumgn.bukkitutils.gson.GsonLoadException;
import fr.aumgn.bukkitutils.gson.GsonLoader;
import fr.aumgn.bukkitutils.gson.typeadapter.DirectionTypeAdapterFactory;
import fr.ethilvan.bukkit.drops.customDrops.CustomDrop;
import fr.ethilvan.bukkit.drops.customDrops.CustomDrops;
import fr.ethilvan.bukkit.drops.eco.MoneyDrop;
import fr.ethilvan.bukkit.drops.eco.MoneyDrops;

public class DropsPlugin extends JavaPlugin {

    private HashSet<UUID> spawnerSpawnedMobs;
    private CustomDrops customDrops;
    private MoneyDrops moneyDrops;

    @Override
    public void onEnable() {
        PluginManager pm = Bukkit.getPluginManager();
        spawnerSpawnedMobs = new HashSet<UUID>();
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

    public List<CustomDrop> getCustomDrops() {
        return customDrops.getCustomDrops();
    }

    public List<MoneyDrop> getMoneyDrops() {
        return moneyDrops.getMoneyDrops();
    }

    private GsonLoader getGsonLoader() {
        Gson gson = new GsonBuilder()
        .registerTypeAdapterFactory(new DirectionTypeAdapterFactory())
        .setPrettyPrinting().create();
        return new GsonLoader(gson, this);
    }

    private void loadData() {
        try {
            moneyDrops = getGsonLoader().loadOrCreate("moneyDrops.json",
                    MoneyDrops.class);
        } catch (GsonLoadException exc) {
            getLogger().log(Level.SEVERE, "Unable to load moneyDrops.json", exc);
            moneyDrops = new MoneyDrops();
        }

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
            getGsonLoader().write("moneyDrops.json", moneyDrops);
        } catch (GsonLoadException exc) {
            getLogger().log(Level.SEVERE, "Unable to write moneyDrops.json", exc);
        }

        try {
            getGsonLoader().write("customDrops.json", customDrops);
        } catch (GsonLoadException exc) {
            getLogger().log(Level.SEVERE, "Unable to write customDrops.json", exc);
        }
    }
}
