package fr.ethilvan.bukkit.drops;

import java.util.HashSet;
import java.util.UUID;
import java.util.logging.Level;

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

import fr.ethilvan.bukkit.drops.customDrops.CustomDrop;
import fr.ethilvan.bukkit.drops.eco.MoneyDrop;

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
            for (CustomDrop randomDrop : plugin.getCustomDrops()) {
                if (randomDrop.getEntityType() == null) {
                    plugin.getLogger().log(Level.SEVERE, "Aucun mob trouvé avec : "
                            + randomDrop.getEntityName());
                    return;
                }
                if (randomDrop.drop(event.getEntity().getType())) {
                    event.getDrops().add(randomDrop.toItemStack());
                }
            }

            for (MoneyDrop moneyDrop : plugin.getMoneyDrops()) {
                if (moneyDrop.getEntityType() == null) {
                    plugin.getLogger().log(Level.SEVERE, "Aucun mob trouvé avec : "
                            + moneyDrop.getEntityName());
                    return;
                }

                if (moneyDrop.drop(event.getEntity().getType())) {
                    moneyDrop.dropMoneyToPlayer((Player) damager);
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
