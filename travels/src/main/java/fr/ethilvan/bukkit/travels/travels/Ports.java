package fr.ethilvan.bukkit.travels.travels;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import org.bukkit.World;

public class Ports implements Iterable<Port> {

    private Map<String, Port> ports;

    public Ports() {
        ports = new LinkedHashMap<String, Port>();
    }

    public Port get(String name) {
        return ports.get(name);
    }

    public Port create(String name, World world) {
        Port port = new Port(name, world);
        ports.put(name, port);
        return port;
    }

    public void remove(Port port) {
        Iterator<Port> it = ports.values().iterator();
        while (it.hasNext()) {
            if (it.next() == port) {
                it.remove();
            }
        }
    }

    public Port get() {
        for (Port port : ports.values()) {
            return port;
        }

        return null;
    }

    public boolean isEmpty() {
        return ports.isEmpty();
    }

    public boolean contains(String name) {
        return ports.containsKey(name);
    }

    @Override
    public Iterator<Port> iterator() {
        return ports.values().iterator();
    }
}