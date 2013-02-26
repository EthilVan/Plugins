package fr.ethilvan.bukkit.permissions;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerKickEvent;

import fr.ethilvan.bukkit.api.event.dimensions.DimensionEnterEvent;
import fr.ethilvan.bukkit.impl.permissions.EVPermissions;

public class PermissionsListener implements Listener {

    private final EVPermissions permissions;

    public PermissionsListener(EVPermissions permissions) {
        this.permissions = permissions;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerJoin(PlayerJoinEvent event) {
        permissions.registerPlayer(event.getPlayer());
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerQuit(PlayerQuitEvent event) {
        permissions.unregisterPlayer(event.getPlayer());
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerKick(PlayerKickEvent event) {
        permissions.unregisterPlayer(event.getPlayer());
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onDimensionEnter(DimensionEnterEvent event) {
        permissions.update(event.getPlayer());
    }
}
