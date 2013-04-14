package fr.ethilvan.bukkit.drops.customDrops;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

public class CustomDrops {

    private List<CustomDrop> drops = new ArrayList<CustomDrop>();

    public CustomDrops() {
        drops.add(new CustomDrop("Creeper", new ItemStack(Material.STONE),
                1, 4, 80));
        drops.add(new CustomDrop("Creeper", new ItemStack(Material.BOW),
                1, 4, 50, Enchantment.ARROW_DAMAGE, 1));
    }

    public List<CustomDrop> getCustomDrops() {
        return drops;
    }
}
