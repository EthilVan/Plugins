package fr.ethilvan.bukkit.permissions;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerKickEvent;

import fr.ethilvan.bukkit.api.EthilVan;
import fr.ethilvan.bukkit.api.event.dimensions.DimensionEnterEvent;

public class PermissionsListener implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerJoin(PlayerJoinEvent event) {
        EthilVan.getPermissions().registerPlayer(event.getPlayer());
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerQuit(PlayerQuitEvent event) {
        EthilVan.getPermissions().unregisterPlayer(event.getPlayer());
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerKick(PlayerKickEvent event) {
        EthilVan.getPermissions().unregisterPlayer(event.getPlayer());
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onDimensionEnter(DimensionEnterEvent event) {
        EthilVan.getPermissions().update(event.getPlayer());
    }
}
