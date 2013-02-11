package fr.ethilvan.bukkit.dimensions.spawnbed;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;

@Entity
@Table(name="spawn_bed")
public class SpawnBed {

    @Id
    private int id;
    @Column(unique=true)
    private String name;

    private String world;
    private int bedX;
    private int bedY;
    private int bedZ;
    private double x;
    private double y;
    private double z;
    private float pitch;
    private float yaw;

    @Version
    private Timestamp updated;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWorld() {
        return world;
    }

    public void setWorld(String world) {
        this.world = world;
    }

    public int getBedX() {
        return bedX;
    }

    public void setBedX(int bedX) {
        this.bedX = bedX;
    }

    public int getBedY() {
        return bedY;
    }

    public void setBedY(int bedY) {
        this.bedY = bedY;
    }

    public int getBedZ() {
        return bedZ;
    }

    public void setBedZ(int bedZ) {
        this.bedZ = bedZ;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public float getPitch() {
        return pitch;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
    }

    public float getYaw() {
        return yaw;
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
    }

    public Timestamp getUpdated() {
        return updated;
    }

    public void setUpdated(Timestamp updated) {
        this.updated = updated;
    }

    public void update(Block bed, Location loc) {
        setWorld(bed.getWorld().getName());
        setBedX(bed.getX());
        setBedY(bed.getY());
        setBedZ(bed.getZ());
        setX(loc.getX());
        setY(loc.getY());
        setZ(loc.getZ());
        setYaw(loc.getYaw());
        setPitch(loc.getPitch());
    }

    public Block getBedBlock() {
        World bukkitWorld = Bukkit.getWorld(world);
        if (bukkitWorld == null) {
            return null;
        }

        return bukkitWorld.getBlockAt(bedX, bedY, bedZ);
    }

    public Location getRespawn() {
        World bukkitWorld = Bukkit.getWorld(world);
        if (bukkitWorld == null) {
            return null;
        }

        return new Location(bukkitWorld, x, y, z, yaw, pitch);
    }
}
