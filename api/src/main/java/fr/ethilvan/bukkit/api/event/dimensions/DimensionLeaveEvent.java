package fr.ethilvan.bukkit.api.event.dimensions;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

import fr.ethilvan.bukkit.api.dimensions.Dimension;

public class DimensionLeaveEvent extends DimensionPlayerEvent {
    private static final HandlerList handlers = new HandlerList();

    public DimensionLeaveEvent(Dimension dimension, Player player) {
        super(dimension, player);
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
