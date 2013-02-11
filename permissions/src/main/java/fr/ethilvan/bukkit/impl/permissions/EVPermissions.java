package fr.ethilvan.bukkit.impl.permissions;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;

import com.avaje.ebean.ExpressionList;

import fr.aumgn.bukkitutils.playerref.map.PlayersRefHashMap;
import fr.aumgn.bukkitutils.playerref.map.PlayersRefMap;
import fr.ethilvan.bukkit.api.EthilVan;
import fr.ethilvan.bukkit.api.accounts.Account;
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
        return getSpecificPermissions("", role, true);
    }

    @Override
    public Set<String> getPermissionsList(String role, String dimension) {
        return getSpecificPermissions(dimension, role, true);
    }

    @Override
    public void update(Player player) {
        unregisterPlayer(player);
        registerPlayer(player);
    }

    @Override
    public void registerPlayer(Player player) {
        PermissionAttachment attachment = player.addAttachment(plugin);
        permissions.put(player, attachment);
        Set<String> perms = getNodes(player);
        for (String permission : perms) {
            attachment.setPermission(permission, true);
        }
    }

    @Override
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

    public Set<String> getSpecificPermissions(String dimension, String role,
            boolean inherited) {
        ExpressionList<EVPermission> expression = plugin.getDatabase()
                .find(EVPermission.class)
                .where()
                .eq("dimension", dimension)
                .eq("role", role);
        if (inherited) { expression = expression.eq("inheritable", true); }
        List<EVPermission> list = expression.findList();
        HashSet<String> set = new HashSet<String>();
        for (EVPermission perm : list) {
            set.add(perm.getNode());
        }
        return set;
    }

    public Set<String> getNodes(Player player) {
        String dimension = EthilVan.getDimensions().get(player).getName();
        Account account = EthilVan.getAccounts().get(player);
        String real_role = account.getRole();
        String[] roles = account.getRoles();
        Set<String> pseudoRoles = EthilVan.getAccounts()
                .getPseudoRoles(player);
        HashSet<String> set = new HashSet<String>();
        for (String role : roles) {
            boolean inherited = !role.equals(real_role);
            set.add(GROUP_PREFIX + role);
            set.addAll(getSpecificPermissions("", role, inherited));
            set.addAll(getSpecificPermissions(dimension, role, inherited));
            for (String pseudoRole : pseudoRoles) {
                String computedRole = role + ":" + pseudoRole;
                set.add(GROUP_PREFIX + computedRole);
                set.addAll(getSpecificPermissions("", computedRole,
                        inherited));
                set.addAll(getSpecificPermissions(dimension, computedRole,
                        inherited));
            }
        }
        return set;
    }
}
