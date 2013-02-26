package fr.ethilvan.bukkit.impl.permissions;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;

import com.avaje.ebean.ExpressionList;

import fr.aumgn.bukkitutils.playerref.map.PlayersRefHashMap;
import fr.aumgn.bukkitutils.playerref.map.PlayersRefMap;
import fr.aumgn.bukkitutils.util.Util;
import fr.ethilvan.bukkit.api.EthilVan;
import fr.ethilvan.bukkit.api.accounts.Account;
import fr.ethilvan.bukkit.api.accounts.Role;
import fr.ethilvan.bukkit.api.event.permissions.PermissionsUpdateEvent;
import fr.ethilvan.bukkit.api.permissions.Permissions;
import fr.ethilvan.bukkit.permissions.PermissionsPlugin;

public class EVPermissions implements Permissions {

    private static final String GROUP_PREFIX = "group.";

    private final PermissionsPlugin plugin;
    private final PlayersRefMap<PermissionAttachment> permissions;

    public EVPermissions(PermissionsPlugin plugin) {
        this.plugin = plugin;
        this.permissions = new PlayersRefHashMap<PermissionAttachment>();
    }

    @Override
    public Set<String> getPermissionsList(String role) {
        return getPermissionsList(role, "");
    }

    @Override
    public Set<String> getPermissionsList(String role, String dimension) {
        HashSet<String> nodes = new HashSet<String>();
        addSpecificPermissions(nodes, dimension, role, true);
        return nodes;
    }

    @Override
    public void update(Player player) {
        unregisterPlayer(player);
        registerPlayer(player);
    }

    public void registerPlayer(Player player) {
        PermissionAttachment attachment = player.addAttachment(plugin);
        permissions.put(player, attachment);
        Set<String> perms = getNodes(player);
        for (String permission : perms) {
            attachment.setPermission(permission, true);
        }

        Util.callEvent(new PermissionsUpdateEvent(player));
    }

    public void unregisterPlayer(Player player) {
        if (permissions.containsKey(player)) {
            try {
                player.removeAttachment(permissions.get(player));
            } catch (IllegalArgumentException ex) {
                plugin.getLogger().warning(
                        "Player does not have PermissionAttachment");
            }
            permissions.remove(player);
        }
    }

    private Set<String> getNodes(Player player) {
        String dimension = EthilVan.getDimensions().get(player).getName();
        Account account = EthilVan.getAccounts().get(player);
        Role role = account.getRole();
        Set<Role> subroles = role.getSubroles();
        Set<String> pseudoRoles = EthilVan.getAccounts()
                .getPseudoRoles(player);

        HashSet<String> set = new HashSet<String>();
        addRoleNodes(set, dimension, role, false, pseudoRoles);
        for (Role subrole : subroles) {
            addRoleNodes(set, dimension, subrole, true, pseudoRoles);
        }
        return set;
    }

    private void addRoleNodes(Collection<String> nodes, String dimension,
            Role role, boolean inherited, Set<String> pseudoRoles) {
        nodes.add(GROUP_PREFIX + role.getId());
        boolean roleInherited = inherited || !pseudoRoles.isEmpty();
        addSpecificPermissions(nodes, "", role.getId(), roleInherited);
        addSpecificPermissions(nodes, dimension, role.getId(), roleInherited);
        for (String pseudoRole : pseudoRoles) {
            String computedRole = role.getId() + ":" + pseudoRole;
            nodes.add(GROUP_PREFIX + computedRole);
            addSpecificPermissions(nodes, "", computedRole, inherited);
            addSpecificPermissions(nodes, dimension, computedRole, inherited);
        }
    }

    private void addSpecificPermissions(Collection<String> nodes,
            String dimension, String role, boolean inherited) {
        ExpressionList<EVPermission> expression = plugin.getDatabase()
                .find(EVPermission.class)
                .where()
                .eq("dimension", dimension)
                .eq("role", role);
        if (inherited) { expression = expression.eq("inheritable", true); }
        List<EVPermission> list = expression.findList();
        for (EVPermission perm : list) {
            nodes.add(perm.getNode());
        }
    }
}
