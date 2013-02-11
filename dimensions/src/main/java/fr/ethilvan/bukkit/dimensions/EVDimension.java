package fr.ethilvan.bukkit.dimensions;

import static org.apache.commons.lang.WordUtils.capitalize;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import com.google.common.base.Function;
import com.google.common.collect.Iterators;

import fr.ethilvan.bukkit.api.dimensions.Dimension;
import fr.ethilvan.bukkit.api.dimensions.DimensionConfig;

public class EVDimension implements Dimension {

    private final String name;
    private final String displayName;
    private final DimensionConfig config;
    private final String mainWorld;
    private final Map<String, String> worlds;

    public EVDimension(String name, ConfigurationSection section) {
        this.name = name;
        this.displayName = section.getString("name", capitalize(name));

        config = new EVDimensionConfig(section);

        Map<String, Object> worldsSection =
                section.getConfigurationSection("worlds").getValues(false);
        worlds = new HashMap<String, String>();
        for (Entry<String, Object> entry : worldsSection.entrySet()) {
            worldsSection.put(entry.getKey(), entry.getValue().toString());
        }

        if (section.isString("main")) {
            mainWorld = section.getString("main");
        } else {
            mainWorld = worlds.keySet().iterator().next();
        }
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }

    @Override
    public World getMainWorld() {
        return Bukkit.getWorld(mainWorld);
    }

    @Override
    public boolean contains(World world) {
        return worlds.containsKey(world.getName());
    }

    @Override
    public String getWorldDisplayName(World world) {
        return worlds.get(world);
    }

    @Override
    public DimensionConfig getConfig() {
        return config;
    }

    @Override
    public Set<Player> getPlayers() {
        Set<Player> players = new HashSet<Player>();
        for (World world : this) {
            players.addAll(world.getPlayers());
        }

        return players;
    }

    @Override
    public void send(String message) {
        for (Player player : getPlayers()) {
            player.sendMessage(message);
        }
    }

    @Override
    public Iterator<World> iterator() {
        return Iterators.transform(worlds.values().iterator(),
                new Function<String, World>() {
            @Override
            public World apply(String worldName) {
                return Bukkit.getWorld(worldName);
            }
        });
    }

    public Iterable<String> worldsName() {
        return worlds.values();
    }
}
