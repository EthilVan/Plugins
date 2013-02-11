package fr.ethilvan.bukkit.permissions;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

import javax.persistence.PersistenceException;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import fr.aumgn.bukkitutils.command.CommandsRegistration;
import fr.ethilvan.bukkit.api.EthilVan;
import fr.ethilvan.bukkit.api.permissions.Permissions;
import fr.ethilvan.bukkit.impl.permissions.EVPermission;
import fr.ethilvan.bukkit.impl.permissions.EVPermissions;

public class PermissionsPlugin extends JavaPlugin {

    @Override
    public List<Class<?>> getDatabaseClasses() {
        return Collections.<Class<?>>singletonList(EVPermission.class);
    }

    @Override
    public void onEnable() {
        PluginManager pm = Bukkit.getPluginManager();

        try {
            getDatabase().find(EVPermission.class).findRowCount();
        } catch (PersistenceException exc) {
            installDDL();
        }

        Permissions permissions = new EVPermissions(this);
        for (Player player : Bukkit.getOnlinePlayers()) {
            permissions.registerPlayer(player);
        }

        EthilVan.registerPermissions(permissions);

        Listener listener = new PermissionsListener();
        pm.registerEvents(listener, this);

        CommandsRegistration registration = new CommandsRegistration(this,
                Locale.FRANCE);
        registration.register(new PermissionsCommands());
    }

    @Override
    public void onDisable() {
        Permissions permissions = EthilVan.getPermissions();
        for (Player player : Bukkit.getOnlinePlayers()) {
            permissions.unregisterPlayer(player);
        }
    }
}
