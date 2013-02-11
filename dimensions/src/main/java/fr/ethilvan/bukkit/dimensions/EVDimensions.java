package fr.ethilvan.bukkit.dimensions;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.World;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import fr.ethilvan.bukkit.api.dimensions.Dimension;
import fr.ethilvan.bukkit.api.dimensions.Dimensions;

public class EVDimensions implements Dimensions, Iterable<Dimension> {

    private final String main;
    private final Map<String, EVDimension> dimensions;

    public EVDimensions(Configuration config) {
        dimensions = new HashMap<String, EVDimension>();

        String _main = null;
        for (Entry<String, Object> entry : config.getValues(false).entrySet()) {
            String key = entry.getKey();
            if (key.equals("main")) {
                _main = (String) entry.getValue();
                continue;
            }

            ConfigurationSection section =
                    (ConfigurationSection) entry.getValue();
            dimensions.put(key, new EVDimension(key, section));
        }

        if (_main == null) {
            main = dimensions.keySet().iterator().next();
        } else {
            main = _main;
        }
    }

    public Dimension getMain() {
        return get(main);
    }

    @Override
    public Dimension get(String name) {
        return dimensions.get(name); 
    }

    @Override
    public Dimension get(World world) {
        for (Dimension dimension : dimensions.values()) {
            if (dimension.contains(world)) {
                return dimension;
            }
        }
        return null;
    }

    @Override
    public Dimension get(Player player) {
        return get(player.getWorld());
    }

    @Override
    public Iterator<Dimension> iterator() {
        return Collections.<Dimension>unmodifiableCollection(
                dimensions.values()).iterator();
    }
}
