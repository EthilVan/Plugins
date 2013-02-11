package fr.ethilvan.bukkit.api.permissions;

import java.util.Set;

import org.bukkit.entity.Player;

public interface Permissions {

    Set<String> getPermissionsList(String role);

    Set<String> getPermissionsList(String role, String dimension);

    void update(Player player);

    void registerPlayer(Player player);

    void unregisterPlayer(Player player);
}
