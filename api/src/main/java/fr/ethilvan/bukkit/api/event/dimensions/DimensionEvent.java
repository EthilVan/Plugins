package fr.ethilvan.bukkit.api.event.dimensions;

import org.bukkit.event.Event;

import fr.ethilvan.bukkit.api.dimensions.Dimension;

public abstract class DimensionEvent extends Event {

    private Dimension dimension;

    protected DimensionEvent(Dimension dimension) {
        super();
    }

    public Dimension getDimension() {
        return dimension;
    }
}
