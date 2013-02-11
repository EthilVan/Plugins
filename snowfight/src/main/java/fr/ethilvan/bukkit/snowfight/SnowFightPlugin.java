package fr.ethilvan.bukkit.snowfight;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.PersistenceException;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import fr.ethilvan.bukkit.snowfight.stats.SnowFightDeath;

public class SnowFightPlugin extends JavaPlugin {

    private final SnowFight snowFight = new SnowFight(this);

    @Override
    public List<Class<?>> getDatabaseClasses() {
        ArrayList<Class<?>> list = new ArrayList<Class<?>>();
        list.add(SnowFightDeath.class);
        return list;
    }

    @Override
    public void onEnable() {
        try {
            getDatabase().find(SnowFightDeath.class).findRowCount();
        } catch (PersistenceException exc) {
            installDDL();
        }

        Bukkit.getPluginManager().registerEvents(new SnowFightListener(snowFight), this);
    }
}
