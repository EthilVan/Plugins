package fr.ethilvan.bukkit.drops;

import java.util.HashSet;
import java.util.UUID;

import net.milkbowl.vault.economy.Economy;

import org.bukkit.entity.Blaze;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;

import fr.ethilvan.bukkit.api.EthilVan;

public class DropsListener implements Listener {

    private final DropsPlugin plugin;

    public DropsListener(DropsPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onCreatureSpawn(CreatureSpawnEvent event) {
        if (event.getSpawnReason() == SpawnReason.SPAWNER) {
            plugin.getSpawnerSpawnedMobs().add(event.getEntity()
                    .getUniqueId());
        }
    }

    private void cancelDrops(EntityDeathEvent event, boolean objects,
            boolean xp) {
        if (objects) {
            event.getDrops().clear();
        }
        if (xp) {
            event.setDroppedExp(0);
        }
    }

    private void cancelDrops(EntityDeathEvent event) {
        cancelDrops(event, true, true);
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof Player) {
            return;
        }

        HashSet<UUID> spawnerSpawnedMobs = plugin.getSpawnerSpawnedMobs();
        if (spawnerSpawnedMobs.contains(entity.getUniqueId())) {
            spawnerSpawnedMobs.remove(entity.getUniqueId());
            cancelDrops(event, !(entity instanceof Blaze), true);
            return;
        }

        EntityDamageEvent lastDmg = entity.getLastDamageCause();
        if (!(lastDmg instanceof EntityDamageByEntityEvent)) {
            cancelDrops(event);
            return;
        }

        Entity damager = ((EntityDamageByEntityEvent)lastDmg).getDamager();
        if (damager instanceof Projectile) {
            Projectile projectile = (Projectile) damager;
            if (projectile.getShooter() == null) {
                cancelDrops(event);
                return;
            }
            damager = projectile.getShooter();
        }

        if (damager instanceof Player) {
            Player player = (Player) damager;
            String name = player.getName();
            int amount = plugin.getEcoConfig().getMoneyDrop(entity);
            if (amount > 0) {
                Economy eco = EthilVan.getEconomy();
                int balance = (int) Math.ceil(eco.getBalance(name));
                eco.depositPlayer(name, ((double)amount) / 100);
                double newBalance = eco.getBalance(name); 
                if ((int)newBalance > balance) {
                    player.sendMessage("Vous avez desormais "
                            + eco.format(newBalance));
                }
            }
        }
    }

    @EventHandler
    public void onAnimalsDeath(EntityDeathEvent event) {
        if (!isAnimal(event.getEntityType())) {
            return;
        }

        cancelDrops(event, false, true);
    }

    public boolean isAnimal(EntityType type) {
        return type == EntityType.CHICKEN
                || type == EntityType.COW
                || type == EntityType.MUSHROOM_COW
                || type == EntityType.OCELOT
                || type == EntityType.PIG
                || type == EntityType.SHEEP
                || type == EntityType.WOLF;
    }
}
