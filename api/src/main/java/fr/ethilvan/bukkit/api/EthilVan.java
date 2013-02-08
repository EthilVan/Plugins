package fr.ethilvan.bukkit.api;

import net.milkbowl.vault.economy.Economy;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;

import fr.ethilvan.bukkit.api.accounts.Accounts;
import fr.ethilvan.bukkit.api.dimensions.Dimensions;
import fr.ethilvan.bukkit.api.inventories.Inventories;
import fr.ethilvan.bukkit.api.permissions.Permissions;
import fr.ethilvan.bukkit.api.worldedit.WorldEditMissing;

public final class EthilVan {

    private static Inventories inventories;
    private static Accounts accounts;
    private static Dimensions dimensions;
    private static Permissions permissions;

    private EthilVan() {
    }

    public static boolean registerInventories(Inventories inventories) {
        if (EthilVan.inventories != null) {
            return false;
        }
        EthilVan.inventories = inventories;
        return true;
    }

    public static boolean registerAccounts(Accounts accounts) {
        if (EthilVan.accounts != null) {
            return false;
        }
        EthilVan.accounts = accounts;
        return true;
    }

    public static boolean registerDimensions(Dimensions dimensions) {
        if (EthilVan.dimensions != null) {
            return false;
        }
        EthilVan.dimensions = dimensions;
        return true;
    }

    public static boolean registerPermissions(Permissions permissions) {
        if (EthilVan.permissions != null) {
            return false;
        }
        EthilVan.permissions = permissions;
        return true;
    }

    public static Inventories getInventories() {
        return inventories;
    }

    public static Accounts getAccounts() {
        return accounts;
    }

    public static Dimensions getDimensions() {
        return dimensions;
    }

    public static Permissions getPermissions() {
        return permissions;
    }

    public static Economy getEconomy() {
        return Bukkit.getServicesManager().getRegistration(Economy.class)
                .getProvider();
    }

    public static WorldEditPlugin getWorldEdit() {
        Plugin plugin = Bukkit.getPluginManager().getPlugin("WorldEdit");
        if (!(plugin instanceof WorldEditPlugin)) {
            throw new WorldEditMissing();
        }

        return (WorldEditPlugin) plugin;
    }
}