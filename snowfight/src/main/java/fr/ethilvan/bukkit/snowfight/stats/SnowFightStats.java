package fr.ethilvan.bukkit.snowfight.stats;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import com.avaje.ebean.EbeanServer;

public class SnowFightStats {

    private final EbeanServer database;

    public SnowFightStats(EbeanServer database) {
        this.database = database;
    }

    public void record(Player player, Entity damager) {
        SnowFightDeath snowDeathCount;
        if (damager instanceof Player) {
            Player playerDamager = (Player) damager;
            snowDeathCount = SnowFightDeath.create(
                    player.getName(), playerDamager.getName());
        } else {
            snowDeathCount = SnowFightDeath.create(
                    player.getName(), "@inconnu");
        }
        database.save(snowDeathCount);
    }

    public int getDeathCount(Player player) {
        return database.find(SnowFightDeath.class)
                .where().eq("victim", player.getName())
                .findRowCount();
    }

    public int getKillCount(Player player) {
        return database.find(SnowFightDeath.class)
                .where().eq("shooter", player.getName())
                .findRowCount();
    }
}
