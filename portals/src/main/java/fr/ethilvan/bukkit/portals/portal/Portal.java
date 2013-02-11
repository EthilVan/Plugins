package fr.ethilvan.bukkit.portals.portal;

import static fr.ethilvan.bukkit.api.worldedit.WorldEditUtils.*;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import com.sk89q.worldedit.regions.Region;

import fr.aumgn.bukkitutils.geom.Direction;
import fr.aumgn.bukkitutils.geom.Directions;
import fr.aumgn.bukkitutils.geom.Vector;
import fr.ethilvan.bukkit.portals.exception.AreaNotInitialized;
import fr.ethilvan.bukkit.portals.exception.RefPointNotInside;
import fr.ethilvan.bukkit.portals.exception.RegionNotFlat;
import fr.ethilvan.bukkit.portals.portal.blocks.Orientation;
import fr.ethilvan.bukkit.portals.portal.blocks.PortalBlocks;

public class Portal {

    private String world;

    private Orientation orientation;
    private Vector minPoint;
    private Vector maxPoint;

    private Vector position;
    private Direction direction;

    private String destination;

    public Portal() {
        this.world = null;
        this.orientation = null;
        this.minPoint = null;
        this.maxPoint = null;
        this.position = null;
        this.direction = null;
        this.destination = null;
    }

    public World getWorld() {
        return Bukkit.getWorld(world);
    }

    public Orientation getOrientation() {
        return orientation;
    }

    public Material getBlock() {
        if (orientation == Orientation.UP_DOWN) {
            return Material.ENDER_PORTAL;
        }

        return Material.PORTAL;
    }

    public Vector getBlockMinPoint() {
        if (orientation == Orientation.NORTH_SOUTH) {
            return minPoint.addX(0.3);
        } else if (orientation == Orientation.UP_DOWN) {
            return minPoint.addY(0.3);
        } else if (orientation == Orientation.WEST_EAST) {
            return minPoint.addZ(0.3);
        } else {
            throw new RegionNotFlat();
        }
    }

    public Vector getBlockMaxPoint() {
        if (orientation == Orientation.NORTH_SOUTH) {
            return maxPoint.subtractX(0.3);
        } else if (orientation == Orientation.UP_DOWN) {
            return maxPoint.subtractY(0.3);
        } else if (orientation == Orientation.WEST_EAST) {
            return maxPoint.subtractZ(0.3);
        } else {
            throw new RegionNotFlat();
        }
    }

    public Vector getMinPoint() {
        return minPoint;
    }

    public Vector getMaxPoint() {
        return maxPoint;
    }

    public Vector getPosition() {
        return position;
    }

    public Direction getDirection() {
        return direction;
    }

    public boolean isComplete() {
        return minPoint != null &&
                position != null;
    }

    public boolean isIn(World world) {
        return world.equals(world);
    }

    public boolean contains(Vector pos) {
        return pos.isInside(minPoint, maxPoint);
    }

    public boolean contains(Location pos) {
        return contains(new Vector(pos));
    }

    public void update(Region region) {
        Vector min = toBU(region.getMinimumPoint());
        Vector max = toBU(region.getMaximumPoint());

        if (min.getBlockX() == max.getBlockX()) {
            orientation = Orientation.NORTH_SOUTH;
            minPoint = min.subtractX(0.3);
            maxPoint = max.addX(1.3);
        } else if (min.getBlockY() == max.getBlockY()) {
            orientation = Orientation.UP_DOWN;
            minPoint = min.subtractY(0.3);
            maxPoint = max.addY(1.3);
        } else if (min.getBlockZ() == max.getBlockZ()) {
            orientation = Orientation.WEST_EAST;
            minPoint = min.subtractZ(0.3);
            maxPoint= max.addZ(1.3);
        } else {
            throw new RegionNotFlat();
        }

        if (position != null && !contains(position)) {
            world = null;
            position = null;
        }
    }

    public void update(Location location) {
        if (minPoint == null) {
            throw new AreaNotInitialized();
        }

        if (!contains(location)) {
            throw new RefPointNotInside();
        }

        world = location.getWorld().getName();
        Vector pos = new Vector(location);
        if (orientation == Orientation.NORTH_SOUTH) {
            position = pos.setX(minPoint.getBlockX());
        } else if (orientation == Orientation.UP_DOWN) {
            position = pos.setY(minPoint.getBlockY());
        } else if (orientation == Orientation.WEST_EAST) {
            position = pos.setZ(minPoint.getBlockZ());
        } else {
            throw new RegionNotFlat();
        }
        direction = Directions.fromLocation(location);
    }

    public void updateDestination(String dest) {
        if (dest == null) {
            this.destination = "";
        } else {
            this.destination = dest;
        }
    }

    public String getDestinationName() {
        return destination;
    }

    public void turnOn() {
        for (Block block : new PortalBlocks(this)) {
            block.setType(getBlock());
        }
    }

    public void turnOff() {
        for (Block block : new PortalBlocks(this)) {
            block.setType(Material.AIR);
        }
    }

    public void teleport(Player player) {
        try {
            Location loc = position
                    .add(direction.getVector().multiply(2))
                    .toLocation(getWorld(), direction);
            Chunk chunk = loc.getChunk();
            chunk.load();
            player.teleport(loc);
            player.setFallDistance(0.0f);
        } catch (NullPointerException exc) {
        }
    }
}
