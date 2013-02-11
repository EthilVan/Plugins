package fr.ethilvan.bukkit.api.dimensions;

import java.util.Set;

import org.bukkit.World;
import org.bukkit.entity.Player;

public interface Dimension extends Iterable<World> {

    String getName();

    String getDisplayName();

    World getMainWorld();

    boolean contains(World world);

    String getWorldDisplayName(World world);

    DimensionConfig getConfig();

    Set<Player> getPlayers();

    void send(String message);
}
