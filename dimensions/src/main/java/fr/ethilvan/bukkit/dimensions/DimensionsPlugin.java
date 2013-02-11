package fr.ethilvan.bukkit.dimensions;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

import javax.persistence.PersistenceException;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import fr.aumgn.bukkitutils.command.CommandsRegistration;
import fr.ethilvan.bukkit.api.EthilVan;
import fr.ethilvan.bukkit.api.dimensions.Dimension;
import fr.ethilvan.bukkit.api.dimensions.DimensionConfig;
import fr.ethilvan.bukkit.api.dimensions.Dimensions;
import fr.ethilvan.bukkit.dimensions.listener.DimensionListener;
import fr.ethilvan.bukkit.dimensions.listener.EventAdapter;
import fr.ethilvan.bukkit.dimensions.listener.SpawnBedListener;
import fr.ethilvan.bukkit.dimensions.spawnbed.SpawnBed;

public class DimensionsPlugin extends JavaPlugin {

    @Override
    public List<Class<?>> getDatabaseClasses() {
        return Collections.<Class<?>>singletonList(SpawnBed.class);
    }   

    @Override
    public void onEnable() {
        PluginManager pm = Bukkit.getPluginManager();

        try { getDatabase().find(SpawnBed.class).findRowCount(); }
        catch (PersistenceException exc) { installDDL(); }

        EVDimensions dimensions = new EVDimensions(getConfig());
        EthilVan.registerDimensions(dimensions);
        initWorlds(dimensions);

        pm.registerEvents(new EventAdapter(), this);
        pm.registerEvents(new SpawnBedListener(this), this);
        pm.registerEvents(new DimensionListener(), this);

        CommandsRegistration registration =
                new CommandsRegistration(this, Locale.FRANCE);
        registration.register(new DimensionsCommands());
    }

    private void initWorlds(Dimensions dimensions) {
        for (Dimension dimension : dimensions) {
            DimensionConfig config = dimension.getConfig();
            boolean animals = !config.hasFlag("no-animals");
            boolean monsters = !config.hasFlag("no-monsters");

            for (String worldName : ((EVDimension)dimension).worldsName()) {
                World world = Bukkit.getWorld(worldName);
                if (world == null) {
                    getLogger().info("Creating " + worldName + " world.");
                    world = new WorldCreator(worldName).createWorld();
                }

                world.setDifficulty(config.getDifficulty());
                world.setSpawnFlags(monsters, animals);
            }
        }
    }

    private String spawnBedName(Player player, Dimension dimension) {
        return "dimensions." + dimension.getName() + "." + player.getName();
    }

    public SpawnBed getSpawnBed(Player player, Dimension dimension) {
        String dbname = spawnBedName(player, dimension);
        return getDatabase().find(SpawnBed.class)
                .where().eq("name", dbname).findUnique();
    }

    public void setSpawnBed(Player player, Dimension dimension, Block bed,
            Location location) {
        String dbname = spawnBedName(player, dimension);
        SpawnBed spawnbed = getDatabase().find(SpawnBed.class)
                .where().eq("name", dbname).findUnique();
        if (spawnbed == null) {
            spawnbed = new SpawnBed();
            spawnbed.setName(dbname);
        }
        spawnbed.update(bed, location);
        getDatabase().save(spawnbed);
    }
}
