package fr.ethilvan.bukkit.api.dimensions;

import org.bukkit.World;
import org.bukkit.entity.Player;

public interface Dimensions extends Iterable<Dimension> {

    Dimension getMain();

    Dimension get(String name);

    Dimension get(World world);

    Dimension get(Player player);
}
