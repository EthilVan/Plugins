package fr.ethilvan.bukkit.inventories;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.PersistenceException;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import com.avaje.ebean.EbeanServer;

import fr.ethilvan.bukkit.api.EthilVan;
import fr.ethilvan.bukkit.api.inventories.Inventories;

public class EVInventories extends JavaPlugin implements Inventories {

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
        EthilVan.registerInventories(this);
        try {
            getDatabase().find(Slot.class).findRowCount();
            getDatabase().find(Inventory.class).findRowCount();
            getDatabase().find(Enchantment.class).findRowCount();
        } catch (PersistenceException exc) {
            installDDL();
        }
    }

    @Override
    public void save(Player player, String description) {
        EbeanServer db = getDatabase();
        Inventory inv = db.find(Inventory.class)  
                .where().eq("description", description).findUnique();
        if (inv == null) {
            inv = new Inventory();
            inv.setDescription(description);
        }
        else { db.delete(inv.getSlots()); }
        inv.update(db, player.getInventory());
        db.save(inv);
    }

    @Override
    public void set(Player player, String description) {
        EbeanServer db = getDatabase();
        Inventory inv = db.find(Inventory.class)  
                .where().eq("description", description).findUnique();
        if (inv == null) {
            player.getInventory().setContents(new ItemStack[36]);
            player.getInventory().setArmorContents(new ItemStack[4]);
        } else {
            inv.setToPlayer(player);
        }
    }

    @Override
    public void replace(Player player, String from, String to) {
        save(player, from);
        set(player, to);
    }
}
