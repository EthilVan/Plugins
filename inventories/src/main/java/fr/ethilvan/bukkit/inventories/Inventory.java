package fr.ethilvan.bukkit.inventories;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.avaje.ebean.EbeanServer;

@Entity
@Table(name="inventories")
public class Inventory {

    @Id
    private int id;
    @Column(unique=true)
    private String description;
    @OneToMany(cascade=CascadeType.ALL)
    @OrderBy("fieldIndex")
    private List<Slot> slots;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Slot> getSlots() {
        return slots;
    }

    public void setSlots(List<Slot> slots) {
        this.slots = slots;
    }

    public void update(EbeanServer db, org.bukkit.inventory.PlayerInventory inventory) {
        ItemStack[] stacks = inventory.getContents();
        ArrayList<Slot> slots = new ArrayList<Slot>();  
        for (int i=0; i<36; i++) {
            if (stacks[i] != null) {
                Slot slot = new Slot();
                slot.update(db, i, stacks[i]);
                slots.add(slot);
            }
        }
        stacks = inventory.getArmorContents();
        for (int i=0; i<4; i++) {
            if (stacks[i] != null) {
                Slot slot = new Slot();
                slot.update(db, i+36, stacks[i]);
                slots.add(slot);
            }
        }
        setSlots(slots);
    }

    public ItemStack[] getContents() {
        ItemStack[] stacks = new ItemStack[36];
        for (Slot slot : getSlots()) {
            if (slot.getFieldIndex() < 36)
                stacks[slot.getFieldIndex()] = slot.toItemStack();
        }
        return stacks;
    }

    public ItemStack[] getArmorContents() {
        ItemStack[] stacks = new ItemStack[4];
        for (Slot slot : getSlots()) {
            if (slot.getFieldIndex() >= 36)
                stacks[slot.getFieldIndex()-36] = slot.toItemStack();
        }
        return stacks;
    };

    public void setToPlayer(Player player) {
        player.getInventory().setContents(getContents());
        player.getInventory().setArmorContents(getArmorContents());
    }
}
