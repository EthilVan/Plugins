package fr.ethilvan.bukkit.impl.inventories;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="inventories_slots_enchantments")
public class Enchantment {

    @Id
    private int id;
    @ManyToOne
    private Slot slot;
    private int bukkitId;
    private int level;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Slot getSlot() {
        return slot;
    }

    public void setSlot(Slot slot) {
        this.slot = slot;
    }

    public int getBukkitId() {
        return bukkitId;
    }

    public void setBukkitId(int bukkitId) {
        this.bukkitId = bukkitId;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }

    public org.bukkit.enchantments.Enchantment toEnchantment() {
        return org.bukkit.enchantments.Enchantment.getById(bukkitId);
    }
}
