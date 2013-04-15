package fr.ethilvan.bukkit.drops.eco;

import net.milkbowl.vault.economy.Economy;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import fr.aumgn.bukkitutils.util.Util;
import fr.ethilvan.bukkit.api.EthilVan;

public class MoneyDrop {

    private String entityName;
    private int rate;
    private int min;
    private int max;

    public MoneyDrop(EntityType entity, int rate, int min, int max) {
        this.entityName = entity.getName();
        this.rate = rate;
        this.min = min;
        this.max = max;
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

    public boolean drop(EntityType entity) {
        return getEntityType() == entity
                && Util.getRandom().nextInt(100) < rate - 1;
    }

    public String getEntityName() {
        return entityName;
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
