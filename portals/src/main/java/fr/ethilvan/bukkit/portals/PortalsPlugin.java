package fr.ethilvan.bukkit.portals;

import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import fr.aumgn.bukkitutils.gson.GsonLoadException;
import fr.aumgn.bukkitutils.gson.GsonLoader;
import fr.aumgn.bukkitutils.gson.typeadapter.DirectionTypeAdapterFactory;
import fr.aumgn.bukkitutils.gson.typeadapter.WorldTypeAdapterFactory;
import fr.ethilvan.bukkit.portals.portal.Portals;

public class PortalsPlugin extends JavaPlugin {

    private Portals portals;

    @Override
    public void onEnable() {
        try {
            portals = getLoader().loadOrCreate("portals.json", Portals.class);
        } catch (GsonLoadException exc) {
            getLogger().log(Level.SEVERE, "Unable to load portals.json", exc);
            portals = new Portals();
        }

        Bukkit.getPluginManager().registerEvents(new PortalsListener(portals),
                this);
        PortalsCommands.register(this);
    }

    private GsonLoader getLoader() {
        Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapterFactory(new DirectionTypeAdapterFactory())
            .registerTypeAdapterFactory(new WorldTypeAdapterFactory())
            .create();
        return new GsonLoader(gson, this);
    }

    @Override
    public void onDisable() {
        try {
            getLoader().write("portals.json", portals);
        } catch (GsonLoadException exc) {
            getLogger().log(Level.SEVERE, "Unable to write portals.json", exc);
        }
    }

    public Portals getPortals() {
        return portals;
    }
}
