package fr.ethilvan.bukkit.api.accounts;

import java.util.Set;

import org.bukkit.ChatColor;

public interface Role {

    String getId();

    String getName();

    ChatColor getColor();

    boolean strictInherit(Role role);

    boolean inherit(Role role);

    Set<Role> getSubroles();
}
