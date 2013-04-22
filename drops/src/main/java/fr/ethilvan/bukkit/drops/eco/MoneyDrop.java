package fr.ethilvan.bukkit.drops.eco;

import net.milkbowl.vault.economy.Economy;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Zombie;

import fr.aumgn.bukkitutils.util.Util;
import fr.ethilvan.bukkit.api.EthilVan;

public class MoneyDrop {

    private String entityName;
    private int entityType;
    private int rate;
    private int min;
    private int max;

    public MoneyDrop(EntityType entity, int rate, int min, int max) {
        this.entityName = entity.getName();
        this.rate = rate;
        this.min = min;
        this.max = max;
    }

    public MoneyDrop(EntityType entity, int entityType, int rate, int min, int max) {
        this(entity, rate, min, max);
        this.entityType = entityType;
    }

    public EntityType getEntityType() {
        return EntityType.fromName(entityName);
    }

    public int getMoneyToDrop() {
        if (min < max) { 
            return Util.getRandom().nextInt(max - min) + min; 
        } else {
            return min;
        }
    }

    public boolean drop(Entity entity) {
        boolean hasTheSameType = getEntityType() == entity.getType();
        if (entity instanceof Skeleton) {
            Skeleton skeleton = (Skeleton) entity;
            if (skeleton.getSkeletonType().getId() != entityType) {
                return false;
            }
        }

        if (entity instanceof Zombie) {
            Zombie zombie = (Zombie) entity;
            if (getZombieType(zombie) != entityType) {
                return false;
            }
        }
        return hasTheSameType && Util.getRandom().nextDouble(100) <= rate;
    }

    public String getEntityName() {
        return entityName;
    }

    private int getZombieType(Zombie zombie) {
        return !zombie.isVillager() ? 0 : 1;
    }

    public void dropMoneyToPlayer(Player player) {
        String name = player.getName();
        int amount = getMoneyToDrop();
        if (amount > 0) {
            if (EthilVan.getAccounts()
                    .getPseudoRoles(player).contains("spm")) {
                return;
            }
            Economy eco = EthilVan.getEconomy();
            int balance = (int) Math.ceil(eco.getBalance(name));
            eco.depositPlayer(name, ((double)amount) / 100);
            double newBalance = eco.getBalance(name); 
            if ((int)newBalance > balance) {
                player.sendMessage("Vous avez desormais "
                        + eco.format(newBalance));
            }
        }
    }
}
