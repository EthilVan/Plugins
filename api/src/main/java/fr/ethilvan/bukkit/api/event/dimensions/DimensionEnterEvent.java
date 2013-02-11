package fr.ethilvan.bukkit.api.event.dimensions;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

import fr.ethilvan.bukkit.api.dimensions.Dimension;

public class DimensionEnterEvent extends DimensionPlayerEvent {
    private static final HandlerList handlers = new HandlerList();

    private final Dimension leftDimension;
    private boolean changeGameMode;
    private boolean changeInventories;

    public DimensionEnterEvent(Dimension dimension, Player player,
            Dimension left) {
        super(dimension, player);
        this.leftDimension = left;
        this.changeGameMode = true;
        this.changeInventories = true;
    }

    public Dimension getLeftDimension() {
        return leftDimension;
    }

    public boolean getChangeGameMode() {
        return changeGameMode;
    }

    public void setChangeGameMode(boolean changeGameMode) {
        this.changeGameMode = changeGameMode;
    }

    public boolean getChangeInventories() {
        return changeInventories;
    }

    public void setChangeInventories(boolean switchInventories) {
        this.changeInventories = switchInventories;
    }

    public void cancelChanges() {
        setChangeGameMode(false);
        setChangeInventories(false);
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
