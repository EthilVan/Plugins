package fr.ethilvan.bukkit.travels.travels;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class Travels implements Iterable<Travel> {

    private Map<String, Travel> travels;

    public Travels() {
        travels = new LinkedHashMap<String, Travel>();
    }

    public Travel get(String name) {
        return travels.get(name);
    }

    public Travel create(String name) {
        Travel travel = new Travel(name);
        travels.put(name, travel);
        return travel;
    }

    public void remove(Travel travel) {
        Iterator<Travel> it = travels.values().iterator();
        while (it.hasNext()) {
            if (it.next() == travel) {
                it.remove();
            }
        }
    }

    public boolean isEmpty() {
        return travels.isEmpty();
    }

    public boolean contains(String name) {
        return travels.containsKey(name);
    }

    @Override
    public Iterator<Travel> iterator() {
        return travels.values().iterator();
    }
}