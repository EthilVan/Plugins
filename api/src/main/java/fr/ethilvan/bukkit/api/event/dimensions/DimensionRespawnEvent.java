package fr.ethilvan.bukkit.api.event.dimensions;

import org.bukkit.Location;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerRespawnEvent;

import fr.ethilvan.bukkit.api.dimensions.Dimension;

public class DimensionRespawnEvent extends DimensionPlayerEvent {
    private static final HandlerList handlers = new HandlerList();

    private final PlayerRespawnEvent worldEvent;

    public DimensionRespawnEvent(Dimension dimension,
            PlayerRespawnEvent event) {
        super(dimension, event.getPlayer());
        this.worldEvent = event;
        event.setRespawnLocation(dimension.getMainWorld().getSpawnLocation());
    }

    public Location getRespawnLocation() {
        return worldEvent.getRespawnLocation();
    }

    public void setRespawnLocation(Location location) {
        worldEvent.setRespawnLocation(location);
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
