package fr.ethilvan.bukkit.portals.portal;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.configuration.serialization.SerializableAs;

@SerializableAs("ethilvan-portals")
public class Portals implements Iterable<Entry<String, Portal>> {

    private Map<String, Portal> portals;

    public Portals() {
        portals = new LinkedHashMap<String, Portal>();
    }

    public Portal get(String name) {
        return portals.get(name);
    }

    public Portal create(String name) {
        Portal portal = new Portal();
        portals.put(name, portal);
        return portal;
    }

    public void remove(Portal portal) {
        portal.turnOff();
        Iterator<Entry<String, Portal>> it = portals.entrySet().iterator();
        while (it.hasNext()) {
            Entry<String, Portal> entry = it.next();
            if (entry.getValue() == portal) {
                it.remove();
            }
        }
    }

    public Portal get(Location loc) {
        for (Portal portal : portals.values()) {
            if (portal.isIn(loc.getWorld())
                    && portal.contains(loc)) {
                return portal;
            }
        }
        return null;
    }

    public boolean contains(Block block) {
        return get(block.getLocation()) != null;
    }

    @Override
    public Iterator<Entry<String, Portal>> iterator() {
        return portals.entrySet().iterator();
    }

    public Portal getDestination(Portal portal) {
        return get(portal.getDestinationName());
    }
}
