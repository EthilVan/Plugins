package fr.ethilvan.bukkit.impl.inventories;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

import com.avaje.ebean.EbeanServer;

@Entity
@Table(name="inventories_slots")
public class Slot {

    @Id
    private int id;
    @ManyToOne
    private Inventory inventory;
    private int fieldIndex;
    private int material;
    private int amount;
    private byte data;
    private short durability;
    @OneToMany(cascade=CascadeType.ALL)
    private List<Enchantment> enchantments;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory; 
    }

    public int getFieldIndex() {
        return fieldIndex;
    }

    public void setFieldIndex(int fieldIndex) {
        this.fieldIndex = fieldIndex;
    }

    public int getMaterial() {
        return material;
    }

    public void setMaterial(int material) {
        this.material = material;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public byte getData() {
        return data;
    }

    public void setData(byte data) {
        this.data = data;
    }

    public short getDurability() {
        return durability;
    }

    public void setDurability(short durability) {
        this.durability = durability;
    }

    public List<Enchantment> getEnchantments() {
        return enchantments;
    }

    public void setEnchantments(List<Enchantment> enchantments) {
        this.enchantments = enchantments;
    }

    public org.bukkit.inventory.ItemStack toItemStack() {
        MaterialData data = new MaterialData(getMaterial(), getData());
        ItemStack stack = data.toItemStack(getAmount());
        stack.setDurability(getDurability());
        for (Enchantment enchantment : getEnchantments()) {
            stack.addEnchantment(enchantment.toEnchantment(), enchantment.getLevel());
        }
        return stack;
    }

    public void update(EbeanServer db, int index, org.bukkit.inventory.ItemStack stack) {
        setFieldIndex(index);
        setMaterial(stack.getTypeId());
        setData(stack.getData().getData());
        setAmount(stack.getAmount());
        setDurability(stack.getDurability());
        if (enchantments != null) {
            db.delete(enchantments);
        }
        List<Enchantment> enchs = new ArrayList<Enchantment>();
        for (Map.Entry<org.bukkit.enchantments.Enchantment, Integer> entry : stack.getEnchantments().entrySet()) {
            Enchantment enchantment = new Enchantment();
            enchantment.setLevel(entry.getValue());
            enchantment.setBukkitId(entry.getKey().getId());
            enchs.add(enchantment);
        }
        setEnchantments(enchs);
    }
}
