package fr.ethilvan.bukkit.api.event.dimensions;

import org.bukkit.entity.Player;

import fr.ethilvan.bukkit.api.dimensions.Dimension;

public abstract class DimensionPlayerEvent extends DimensionEvent {

    private Player player;

    protected DimensionPlayerEvent(Dimension dimension, Player player) {
        super(dimension);
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }
}
