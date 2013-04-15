package fr.ethilvan.bukkit.drops.eco;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.EntityType;

public class MoneyDrops {

    private List<MoneyDrop> moneyDrops = new ArrayList<MoneyDrop>();

    public MoneyDrops() {
        moneyDrops.add(new MoneyDrop(EntityType.CREEPER, 40, 2, 4));
        moneyDrops.add(new MoneyDrop(EntityType.ENDERMAN, 60, 3, 6));
        moneyDrops.add(new MoneyDrop(EntityType.GHAST, 50, 2, 5));
        moneyDrops.add(new MoneyDrop(EntityType.MAGMA_CUBE, 20, 1, 1));
        moneyDrops.add(new MoneyDrop(EntityType.PIG_ZOMBIE, 30, 1, 3));
        moneyDrops.add(new MoneyDrop(EntityType.SKELETON, 20, 1, 2));
        moneyDrops.add(new MoneyDrop(EntityType.SLIME, 10, 1, 1));
        moneyDrops.add(new MoneyDrop(EntityType.SPIDER, 20, 1, 1));
        moneyDrops.add(new MoneyDrop(EntityType.WITCH, 50, 2, 5));
        moneyDrops.add(new MoneyDrop(EntityType.ZOMBIE, 15, 1, 1));
    }

    public List<MoneyDrop> getMoneyDrops() {
        return moneyDrops;
    }
}
