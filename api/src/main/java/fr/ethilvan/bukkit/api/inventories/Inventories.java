package fr.ethilvan.bukkit.api.inventories;

import org.bukkit.entity.Player;

public interface Inventories {

    void save(Player player, String description);

    void set(Player player, String description);

    void replace(Player player, String from, String to);
}
