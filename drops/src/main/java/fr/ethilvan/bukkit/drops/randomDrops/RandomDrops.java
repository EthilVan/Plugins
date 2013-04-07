package fr.ethilvan.bukkit.drops.randomDrops;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class RandomDrops {

    private List<RandomDrop> drops = new ArrayList<RandomDrop>();

    public RandomDrops() {
        drops.add(new RandomDrop("Creeper", new ItemStack(Material.STONE),
                1, 4, 80));
    }

    public List<RandomDrop> getRandomDrops() {
        return drops;
    }
}
