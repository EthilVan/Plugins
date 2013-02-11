package fr.ethilvan.bukkit.api.worldedit;

import org.bukkit.World;
import org.bukkit.entity.Player;

import com.sk89q.worldedit.IncompleteRegionException;
import com.sk89q.worldedit.LocalWorld;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.bukkit.BukkitUtil;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.regions.Region;
import com.sk89q.worldedit.regions.RegionSelector;

import fr.ethilvan.bukkit.api.EthilVan;

public final class WorldEditUtils {

    public static Vector fromBU(fr.aumgn.bukkitutils.geom.Vector vector) {
        return new Vector(vector.getX(), vector.getY(), vector.getZ());
    }

    public static fr.aumgn.bukkitutils.geom.Vector toBU(Vector vector) {
        return new fr.aumgn.bukkitutils.geom.Vector(vector.getX(),
                vector.getY(), vector.getZ());
    }

    public static Vector fromBukkit(org.bukkit.util.Vector vector) {
        return new Vector(vector.getX(), vector.getY(), vector.getZ());
    }

    public static org.bukkit.util.Vector toBukkit(Vector vector) {
        return new org.bukkit.util.Vector(vector.getX(), vector.getY(),
                vector.getZ());
    }

    public static LocalWorld fromBukkit(World world) {
        return BukkitUtil.getLocalWorld(world);
    }

    public static World toBukkit(LocalWorld world) {
        return ((BukkitWorld) world).getWorld();
    }

    public static Region getRegion(WorldEditPlugin worldEdit, Player player) {
        LocalWorld world = fromBukkit(player.getWorld());
        RegionSelector selector;
        selector = worldEdit.getSession(player)
                .getRegionSelector(world);
        try {
            return selector.getRegion();
        } catch (IncompleteRegionException exc) {
            throw new WorldEditIncompleteRegion();
        }
    }

    public static Region getRegion(Player player) {
        return getRegion(EthilVan.getWorldEdit(), player);
    }

    private WorldEditUtils() {
    }
}