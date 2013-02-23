package fr.ethilvan.bukkit.accounts;

import java.util.HashMap;
import java.util.Map;

public class AccountsConfig {

    private final Map<String, RoleConfig> roles;

    public AccountsConfig() {
        roles = new HashMap<String, RoleConfig>();
    }

    public Map<String, RoleConfig> getRoles() {
        return roles;
    }
}
