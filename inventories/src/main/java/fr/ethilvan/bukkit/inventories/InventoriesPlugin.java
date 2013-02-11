package fr.ethilvan.bukkit.inventories;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.PersistenceException;

import org.bukkit.plugin.java.JavaPlugin;

import fr.ethilvan.bukkit.api.EthilVan;
import fr.ethilvan.bukkit.api.inventories.Inventories;

public class InventoriesPlugin extends JavaPlugin {

    @Override
    public List<Class<?>> getDatabaseClasses() {
        List<Class<?>> list = new ArrayList<Class<?>>();
        list.add(Slot.class);
        list.add(Inventory.class);
        list.add(Enchantment.class);
        return list;
    }

    @Override
    public void onEnable() {
        try {
            getDatabase().find(Slot.class).findRowCount();
            getDatabase().find(Inventory.class).findRowCount();
            getDatabase().find(Enchantment.class).findRowCount();
        } catch (PersistenceException exc) {
            installDDL();
        }

        Inventories inventories = new EVInventories(this);
        EthilVan.registerInventories(inventories);
    }
}
