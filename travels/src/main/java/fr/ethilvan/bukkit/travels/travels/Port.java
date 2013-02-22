package fr.ethilvan.bukkit.travels.travels;

import java.util.UUID;
import static fr.ethilvan.bukkit.api.worldedit.WorldEditUtils.toBU;
import static fr.ethilvan.bukkit.api.worldedit.WorldEditUtils.toBukkit;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import com.sk89q.worldedit.regions.Region;

import fr.aumgn.bukkitutils.geom.Direction;
import fr.aumgn.bukkitutils.geom.Directions;
import fr.aumgn.bukkitutils.geom.Vector;
import fr.ethilvan.bukkit.travels.exception.TravelsException;

public class Port {

    private String name;
    private UUID worldId;

    private Vector departureMin;
    private Vector departureMax;

    private Vector destination;
    private Direction direction;

    public Port(String name, World world) {
        this.name = name;
        this.worldId = world.getUID();
        this.departureMin = null;
        this.departureMax = null;
        this.destination = null;
        this.direction = null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean departureContains(Location location) {
        if (departureMin == null) {
            return false;
        }

        if (!location.getWorld().getUID().equals(worldId)) {
            return false;
        }

        return new Vector(location).isInside(departureMin, departureMax);
    }

    public boolean departureContains(Player player) {
        return departureContains(player.getLocation());
    }

    public Location getDestination() {
        if (destination == null) {
            return null;
        }

        return destination.toLocation(getWorld(), direction);
    }

    public World getWorld() {
        return Bukkit.getWorld(worldId);
    }

    public void setDeparture(Region region) {
        World world = toBukkit(region.getWorld());

        if (!world.getUID().equals(worldId)) {
            throw new TravelsException(
                    "Votre monde n'est pas le même que celui du port");
        }

        this.departureMin = toBU(region.getMinimumPoint());
        this.departureMax = toBU(region.getMaximumPoint());
    }

    public void setPosition(Location location) {
        if (!location.getWorld().getUID().equals(worldId)) {
            throw new TravelsException(
                    "Votre monde n'est pas le même que celui du port");
        }

        this.destination = new Vector(location);
        this.direction = Directions.fromLocation(location);
    }

    public Vector getDepartureMin() {
        return departureMin;
    }

    public Vector getDepartureMax() {
        return departureMax;
    }
}
