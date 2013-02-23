package fr.ethilvan.bukkit.accounts;

import org.bukkit.ChatColor;

public class RoleConfig {

    private final String name;
    private final ChatColor color;
    private final String subrole;

    public RoleConfig() {
        this.subrole = null;
        this.name = null;
        this.color = null;
    }

    public String getName() {
        return name;
    }

    public ChatColor getColor() {
        return color;
    }

    public String getSubrole() {
        return subrole;
    }
}
