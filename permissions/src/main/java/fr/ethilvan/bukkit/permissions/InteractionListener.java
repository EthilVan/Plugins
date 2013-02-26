package fr.ethilvan.bukkit.permissions;

import org.bukkit.entity.Entity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;

import fr.ethilvan.bukkit.api.event.dimensions.DimensionEnterEvent;
import fr.ethilvan.bukkit.api.event.permissions.PermissionsUpdateEvent;

public class InteractionListener implements Listener {

    private final PermissionsPlugin plugin;

    public InteractionListener(PermissionsPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPermissionsUpdate(PermissionsUpdateEvent event) {
        Player player = event.getPlayer();
        player.setSleepingIgnored(plugin.noInteract(player));
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onPickupItem(PlayerPickupItemEvent event) {
        if (plugin.noInteract(event.getPlayer())) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onDropItem(PlayerDropItemEvent event) {
        if (plugin.noInteract(event.getPlayer())) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onDimensionEnter(DimensionEnterEvent event) {
        if (plugin.noInteract(event.getPlayer())) {
            event.cancelChanges();
        }
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onDamage(EntityDamageEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof Player && plugin.noInteract((Player) entity)) {
            event.setCancelled(true);
            return;
        }
        if (!(event instanceof EntityDamageByEntityEvent)) {
            return;
        }
        entity = ((EntityDamageByEntityEvent) event).getDamager();
        if (entity instanceof Projectile) {
            entity = ((Projectile) entity).getShooter();
        }
        if (entity instanceof Player && plugin.noInteract((Player) entity)) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onInteract(PlayerInteractEvent event) {
        if (!plugin.noInteract(event.getPlayer())) {
            return;
        }

        if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
            event.setCancelled(true);
            return;
        }

        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }

        if (!plugin.isForbiddenBlock(event.getClickedBlock())) {
            return;
        }

        event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onEntityTarget(EntityTargetEvent event) {
        Entity target = event.getTarget();
        if (target instanceof Player && plugin.noInteract((Player) target)) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onFoodChange(FoodLevelChangeEvent event) {
        HumanEntity entity = event.getEntity();
        if (entity instanceof Player
                && plugin.noInteract((Player) event.getEntity())) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onExpChange(PlayerExpChangeEvent event) {
        if (plugin.noInteract(event.getPlayer())) {
            event.setAmount(0);
        }
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onBlockPlace(BlockPlaceEvent event) {
        if (plugin.noInteract(event.getPlayer())) {
            event.setCancelled(true);
        }
    }
}
