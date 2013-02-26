package fr.ethilvan.bukkit.api.event.permissions;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PermissionsUpdateEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    private final Player player;

    public PermissionsUpdateEvent(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
