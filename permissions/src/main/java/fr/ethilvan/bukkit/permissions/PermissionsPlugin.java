package fr.ethilvan.bukkit.permissions;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.persistence.PersistenceException;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import fr.aumgn.bukkitutils.command.CommandsRegistration;
import fr.ethilvan.bukkit.api.EthilVan;
import fr.ethilvan.bukkit.impl.permissions.EVPermission;
import fr.ethilvan.bukkit.impl.permissions.EVPermissions;

public class PermissionsPlugin extends JavaPlugin {

    private static final Set<Material> FORBIDDEN_BLOCKS;
    static {
        FORBIDDEN_BLOCKS = new HashSet<Material>();
        FORBIDDEN_BLOCKS.add(Material.CHEST);
        FORBIDDEN_BLOCKS.add(Material.FURNACE);
        FORBIDDEN_BLOCKS.add(Material.BREWING_STAND);
        FORBIDDEN_BLOCKS.add(Material.ENDER_CHEST);
    }

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

        EVPermissions permissions = new EVPermissions(this);
        for (Player player : Bukkit.getOnlinePlayers()) {
            permissions.registerPlayer(player);
        }

        EthilVan.registerPermissions(permissions);

        Listener permissionsListener = new PermissionsListener(permissions);
        pm.registerEvents(permissionsListener, this);
        Listener interactionsListener = new InteractionListener(this);
        pm.registerEvents(interactionsListener, this);

        CommandsRegistration registration = new CommandsRegistration(this,
                Locale.FRANCE);
        registration.register(new PermissionsCommands());
    }

    @Override
    public void onDisable() {
        EVPermissions permissions = (EVPermissions) EthilVan.getPermissions();
        for (Player player : Bukkit.getOnlinePlayers()) {
            permissions.unregisterPlayer(player);
        }
    }

    public boolean noInteract(Player player) {
        return player.hasPermission("ev.interactions.disable");
    }

    public boolean isForbiddenBlock(Block clickedBlock) {
        return FORBIDDEN_BLOCKS.contains(clickedBlock);
    }
}
