package fr.ethilvan.bukkit.impl.accounts;

import java.util.Set;

import org.bukkit.ChatColor;

import fr.ethilvan.bukkit.accounts.RoleConfig;
import fr.ethilvan.bukkit.api.accounts.Role;

public class EVRole implements Role {

    private final String id;
    private final String name;
    private final ChatColor color;
    private Set<Role> subroles;

    public EVRole(String roleId, RoleConfig config) {
        this.id = roleId;
        this.name = config.getName();
        this.color = config.getColor();
        this.subroles = null;
    }

    public void initSubroles(Set<Role> subroles) {
        if (this.subroles != null) {
            throw new UnsupportedOperationException();
        }

        this.subroles = subroles;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public ChatColor getColor() {
        return color;
    }

    @Override
    public boolean strictInherit(Role role) {
        return subroles.contains(role);
    }

    @Override
    public boolean inherit(Role role) {
        return equals(role) || strictInherit(role);
    }

    @Override
    public Set<Role> getSubroles() {
        return subroles;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Role)) {
            return false;
        }

        Role otherRole = (Role) other;
        return otherRole.getId().equals(id);
    }

    @Override
    public int hashCode() {
        return getId().hashCode();
    }
}
