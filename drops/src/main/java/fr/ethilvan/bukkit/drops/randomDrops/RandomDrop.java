package fr.ethilvan.bukkit.drops.randomDrops;

import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

import fr.aumgn.bukkitutils.util.Util;

public class RandomDrop {

    private String entityName;
    private int id;
    private short data;
    private int amountMin;
    private int amountMax;
    private double rate;

    public RandomDrop(String entityName, ItemStack drop, int amountMin,
            int amountMax, double rate) {
        this.entityName = entityName;
        this.id = drop.getTypeId();
        this.data = drop.getDurability();
        this.amountMin = amountMin;
        this.amountMax = amountMax;
        this.rate = rate;
    }

    public ItemStack toItemStack() {
        int randomAmount = Util.getRandom().nextInt(amountMin, amountMax);
        return new ItemStack(id, randomAmount, data);
    }

    public EntityType getEntityType() {
        return EntityType.fromName(entityName);
    }

    public String getEntityName() {
        return entityName;
    }

    public boolean drop(EntityType entity) {
        return getEntityType() == entity 
                && Util.getRandom().nextInt(100) < rate - 1;
    }
}
