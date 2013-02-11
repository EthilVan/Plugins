package fr.ethilvanbukkit.bukkit.superpigmode;

import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import fr.ethilvan.bukkit.api.event.dimensions.DimensionEnterEvent;
import fr.ethilvanbukkit.bukkit.superpigmode.SuperPigMode.TurnOffReason;

public class SPMListener implements Listener {

    public final SuperPigMode spm;

    public SPMListener(SuperPigMode plugin) {
        this.spm = plugin;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEntityDeath(EntityDeathEvent event) {
        EntityDamageEvent dmgCause = event.getEntity().getLastDamageCause();
        if (!(dmgCause instanceof EntityDamageByEntityEvent)) {
            return;
        }

        Entity damager = ((EntityDamageByEntityEvent) dmgCause).getDamager();
        if (damager instanceof Player && spm.isInSPM((Player) damager)) {
            event.getDrops().clear();
            event.setDroppedExp(0);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onPlayerPickupItem(PlayerPickupItemEvent event) {
        Player player = event.getPlayer();
        event.setCancelled(spm.isInSPM(player) && !player.isSneaking());
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerBedEnterEvent(PlayerBedEnterEvent event) {
        Player player = event.getPlayer();
        if (spm.isInSPM(player)) {
            event.setCancelled(true);
            player.sendMessage(ChatColor.YELLOW
                    + "Un Super Pig ne dort jamais !");
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onDimensionEnter(DimensionEnterEvent event) {
        if (spm.isInSPM(event.getPlayer())) {
            event.cancelChanges();
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer(); 
        for (Player vanished : spm.getVanished()) {
            spm.vanish(player, vanished);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if (spm.isVanished(player)) {
            event.setQuitMessage(null);
        }
        if (spm.isInSPM(player)) {
            spm.turnOff(player, TurnOffReason.Quit);
        }
    }
}
