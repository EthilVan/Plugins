package fr.ethilvan.bukkit.travels;

import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import fr.aumgn.bukkitutils.gson.GsonLoadException;
import fr.aumgn.bukkitutils.gson.GsonLoader;
import fr.aumgn.bukkitutils.gson.typeadapter.DirectionTypeAdapterFactory;
import fr.aumgn.bukkitutils.gson.typeadapter.WorldTypeAdapterFactory;
import fr.ethilvan.bukkit.travels.commands.TravelsCommands;
import fr.ethilvan.bukkit.travels.travels.Ports;
import fr.ethilvan.bukkit.travels.travels.Travels;

public class TravelsPlugin extends JavaPlugin {

    private Travels travels;
    private Ports ports;
    private TravelsConfig config;

    @Override
    public void onEnable() {
        loadConfig();
        loadData();

        TravelsCommands.register(this);

        int delay = config.getCheckDelay();
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this,
                new TravelsRunnable(this), delay, delay);
    }

    @Override
    public void onDisable() {
        writeData();
        Bukkit.getScheduler().cancelTasks(this);
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

    private void loadConfig() {
        GsonLoader loader = getGsonLoader();
        try {
            config = loader.loadOrCreate("config.json", TravelsConfig.class);
        } catch (GsonLoadException exc) {
            getLogger().severe(
                    "Unable to load config.json. Using default values");
            config = new TravelsConfig();
        }
    }

    private void loadData() {
        try {
            ports = getGsonLoader().loadOrCreate("ports.json", Ports.class);
        } catch (GsonLoadException exc) {
            getLogger().log(Level.SEVERE, "Unable to load ports.json", exc);
            ports = new Ports();
        }

        try {
            travels = getGsonLoader().loadOrCreate("travels.json",
                    Travels.class);
        } catch (GsonLoadException exc) {
            getLogger().log(Level.SEVERE, "Unable to load travels.json", exc);
            travels = new Travels();
        }
    }

    private void writeData() {
        try {
            getGsonLoader().write("ports.json", ports);
        } catch (GsonLoadException exc) {
            getLogger().log(Level.SEVERE, "Unable to write ports.json", exc);
        }

        try {
            getGsonLoader().write("travels.json", travels);
        } catch (GsonLoadException exc) {
            getLogger().log(Level.SEVERE, "Unable to write travels.json", exc);
        }
    }

    public TravelsConfig getTravelsConfig() {
        return config;
    }

    public Travels getTravels() {
        return travels;
    }

    public Ports getPorts() {
        return ports;
    }
}
