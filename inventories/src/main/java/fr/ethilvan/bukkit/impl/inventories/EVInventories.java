package fr.ethilvan.bukkit.impl.inventories;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.avaje.ebean.EbeanServer;

import fr.ethilvan.bukkit.api.inventories.Inventories;
import fr.ethilvan.bukkit.inventories.InventoriesPlugin;

public class EVInventories implements Inventories {

    private final InventoriesPlugin plugin;

    public EVInventories(InventoriesPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void save(Player player, String description) {
        EbeanServer db = plugin.getDatabase();
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
        EbeanServer db = plugin.getDatabase();
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
