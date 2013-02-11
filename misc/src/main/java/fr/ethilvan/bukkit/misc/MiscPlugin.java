package fr.ethilvan.bukkit.misc;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class MiscPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        createRecipes();

        Listener listener = new EVMiscListener();
        Listener hawkEyeExplosionListener =
                new HawkEyeExtension(this);

        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(listener, this);
        pm.registerEvents(hawkEyeExplosionListener, this);
    }

    private void createRecipes() {
        ItemStack stack = new ItemStack(Material.CHAINMAIL_CHESTPLATE, 1);
        ShapedRecipe recipe = new ShapedRecipe(stack);
        recipe.shape("A A", "BAB", "ABA");
        recipe.setIngredient('A', Material.IRON_INGOT);
        recipe.setIngredient('B', Material.STRING);
        Bukkit.addRecipe(recipe);

        stack = new ItemStack(Material.CHAINMAIL_HELMET, 1);
        recipe = new ShapedRecipe(stack);
        recipe.shape("BAB", "A A");
        recipe.setIngredient('A', Material.IRON_INGOT);
        recipe.setIngredient('B', Material.STRING);
        Bukkit.addRecipe(recipe);

        stack = new ItemStack(Material.CHAINMAIL_LEGGINGS, 1);
        recipe = new ShapedRecipe(stack);
        recipe.shape("ABA", "B B", "A A");
        recipe.setIngredient('A', Material.IRON_INGOT);
        recipe.setIngredient('B', Material.STRING);
        Bukkit.addRecipe(recipe);

        stack = new ItemStack(Material.CHAINMAIL_BOOTS, 1);
        recipe = new ShapedRecipe(stack);
        recipe.shape("B B", "A A");
        recipe.setIngredient('A', Material.IRON_INGOT);
        recipe.setIngredient('B', Material.STRING);
        Bukkit.addRecipe(recipe);

        stack = new ItemStack(Material.WEB, 1);
        recipe = new ShapedRecipe(stack);
        recipe.shape("AAA", "AAA", "AAA");
        recipe.setIngredient('A', Material.STRING);
        Bukkit.addRecipe(recipe);

        stack = new ItemStack(Material.SNOW, 1);
        recipe = new ShapedRecipe(stack);
        recipe.shape("AAA");
        recipe.setIngredient('A', Material.SNOW_BALL);
        Bukkit.addRecipe(recipe);

        stack = new ItemStack(Material.ICE, 1);
        recipe = new ShapedRecipe(stack);
        recipe.shape("AA", "AA");
        recipe.setIngredient('A', Material.SNOW_BLOCK);
        Bukkit.addRecipe(recipe);

        stack = new ItemStack(Material.MOSSY_COBBLESTONE, 8);
        recipe = new ShapedRecipe(stack);
        recipe.shape("AAA", "ABA", "AAA");
        recipe.setIngredient('A', Material.COBBLESTONE);
        recipe.setIngredient('B', Material.VINE);
        Bukkit.addRecipe(recipe);

        stack = new ItemStack(Material.SPONGE, 1);
        recipe = new ShapedRecipe(stack);
        recipe.shape("ABA", "BCB", "ABA");
        recipe.setIngredient('A', Material.WOOL, (byte) 4);
        recipe.setIngredient('B', Material.DEAD_BUSH);
        recipe.setIngredient('C', Material.ENDER_PEARL);
        Bukkit.addRecipe(recipe);

        stack = new ItemStack(Material.SMOOTH_BRICK, 8, (byte) 2);
        recipe = new ShapedRecipe(stack);
        recipe.shape("BBB", "BAB", "BBB");
        recipe.setIngredient('A', Material.SULPHUR);
        recipe.setIngredient('B', Material.SMOOTH_BRICK);
        Bukkit.addRecipe(recipe);

        stack = new ItemStack(Material.SMOOTH_BRICK, 8, (byte) 3);
        recipe = new ShapedRecipe(stack);
        recipe.shape("BBB", "B B", "BBB");
        recipe.setIngredient('B', Material.SMOOTH_BRICK);
        Bukkit.addRecipe(recipe);

        stack = new ItemStack(Material.SMOOTH_BRICK, 8, (byte) 1);
        recipe = new ShapedRecipe(stack);
        recipe.shape("BBB", "BCB", "BBB");
        recipe.setIngredient('B', Material.SMOOTH_BRICK);
        recipe.setIngredient('C', Material.VINE);
        Bukkit.addRecipe(recipe);
    }
}
