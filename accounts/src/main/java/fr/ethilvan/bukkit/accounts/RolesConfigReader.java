package fr.ethilvan.bukkit.accounts;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import fr.ethilvan.bukkit.api.accounts.Role;
import fr.ethilvan.bukkit.impl.accounts.EVRole;

public class RolesConfigReader {

    private final Map<String, RoleConfig> config;
    private final Map<String, Set<String>> superrolesByRole;
    private final Map<String, Set<Role>> subrolesByRole;

    public RolesConfigReader(Map<String, RoleConfig> config) {
        this.config = config;

        this.superrolesByRole = new HashMap<String, Set<String>>();
        this.subrolesByRole   = new HashMap<String, Set<Role>>();
    }

    public Map<String, Role> read() {
        Map<String, Role> roles = new HashMap<String, Role>();
        for (Map.Entry<String, RoleConfig> entry : config.entrySet()) {
            EVRole role = new EVRole(entry.getKey(), entry.getValue());
            roles.put(entry.getKey(), role);
        }
        resolveInheritance(config, roles);
        return roles;
    }

    private void resolveInheritance(Map<String,
            RoleConfig> config, Map<String, Role> roles) {
        this.superrolesByRole.clear();
        this.subrolesByRole.clear();

        for (Map.Entry<String, RoleConfig> entry : config.entrySet()) {
            String role = entry.getKey();
            String subrole = entry.getValue().getSubrole();
            Role subroleObj = roles.get(subrole);
            subroles(role).add(subroleObj);
            for (String superrole : superroles(role)) {
                subroles(superrole).add(subroleObj);
            }
            superroles(subrole).add(role);
        }
        for (Role _role : roles.values()) {
            EVRole role = (EVRole) _role;
            role.initSubroles(subroles(role.getId()));
        }
    }

    public Set<String> superroles(String role) {
        Set<String> superroles = superrolesByRole.get(role);
        if (superroles == null) {
            superroles = new HashSet<String>();
            superrolesByRole.put(role, superroles);
        }
        return superroles;
    }

    public Set<Role> subroles(String role) {
        Set<Role> subroles = subrolesByRole.get(role);
        if (subroles == null) {
            subroles = new HashSet<Role>();
            subrolesByRole.put(role, subroles);
        }
        return subroles;
    }
}
