package fr.ethilvanbukkit.bukkit.superpigmode;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;
import org.bukkit.entity.Player;

public class SPMPlayerSave {

    private int health;
    private float exhaustion;
    private float saturation;
    private int foodLevel;
    private int level;
    private float experience;
    private int remainingAir;
    private float fallDistance;
    private int fireTicks; 
    private ItemStack[] inventory;
    private ItemStack[] armors;
    private Location location;
    private GameMode gameMode;

    public SPMPlayerSave(Player player) {
        health = player.getHealth();
        exhaustion = player.getExhaustion();
        saturation = player.getSaturation();
        foodLevel = player.getFoodLevel();
        level = player.getLevel();
        experience = player.getExp();
        remainingAir = player.getRemainingAir();
        fallDistance = player.getFallDistance();
        fireTicks = player.getFireTicks();
        gameMode = player.getGameMode();
        inventory = player.getInventory().getContents();
        armors = player.getInventory().getArmorContents();
        location = player.getLocation();
    }

    public void restore(Player player) {
        player.setHealth(health);
        player.setExhaustion(exhaustion);
        player.setSaturation(saturation);
        player.setFoodLevel(foodLevel);
        player.setLevel(level);
        player.setExp(experience);
        player.setRemainingAir(remainingAir);
        player.setFallDistance(fallDistance);
        player.setFireTicks(fireTicks);
        player.setGameMode(gameMode);
        player.getInventory().setContents(inventory);
        player.getInventory().setArmorContents(armors);
        player.teleport(location);
    }
}
