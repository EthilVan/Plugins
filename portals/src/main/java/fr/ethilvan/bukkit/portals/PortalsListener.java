package fr.ethilvan.bukkit.portals;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.world.PortalCreateEvent;

import fr.ethilvan.bukkit.portals.portal.Portal;
import fr.ethilvan.bukkit.portals.portal.Portals;

public class PortalsListener implements Listener {

    private final Portals portals;

    public PortalsListener(Portals portals) {
        this.portals = portals;
    }

    @EventHandler(ignoreCancelled = true)
    public void onPortalCreate(PortalCreateEvent event) {
        event.setCancelled(true);
    }

    @EventHandler(ignoreCancelled = true)
    public void onBlockPhysicsEvent(BlockPhysicsEvent event) {
        Block block = event.getBlock();
        if (block.getType() != Material.PORTAL) {
            return;
        }
        if (!portals.contains(block)) {
            return;
        }
        event.setCancelled(true);
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerPortalEvent(PlayerPortalEvent event) {
        Location loc = event.getFrom();
        Portal portal = portals.get(loc); 
        if (portal == null) {
            return;
        }

        portals.getDestination(portal).teleport(event.getPlayer());
        event.setCancelled(true);
    }
}
