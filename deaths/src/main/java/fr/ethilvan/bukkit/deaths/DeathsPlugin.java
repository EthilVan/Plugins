package fr.ethilvan.bukkit.deaths;

import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.plugin.java.JavaPlugin;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import fr.aumgn.bukkitutils.gson.GsonLoadException;
import fr.aumgn.bukkitutils.gson.GsonLoader;

public class DeathsPlugin extends JavaPlugin implements Listener {

    private DeathMessages deathMessages;

    @Override
    public void onEnable() {
        loadData();
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {
        writeData();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd,
            String lbl, String[] args) {
        loadData();
        sender.sendMessage("Messages rechargés.");
        return true;
    }

    @EventHandler
    public void onEntityDeath(PlayerDeathEvent event) {
        DamageCause eventCause = event.getEntity().getLastDamageCause().getCause();
        System.out.println(eventCause.toString());
        DeathMessage deathMessage = this.deathMessages.get(eventCause.toString());
        EntityType killer = this.getKiller(event);
        if (deathMessage == null && killer != null) {
            deathMessage = this.deathMessages.get(killer.getName());
        }

        if (killer == EntityType.PLAYER) {
            deathMessage = this.deathMessages.get("Player");
        }

        if (deathMessage != null) {
            event.setDeathMessage(deathMessage.getOneMessage(event));
        }
    }

    public EntityType getKiller(PlayerDeathEvent event) {
        EntityDamageEvent dmgEvent = (event.getEntity()).getLastDamageCause();
        if (dmgEvent instanceof EntityDamageByEntityEvent) {
            EntityDamageByEntityEvent dmgByEntityEvent =
                    (EntityDamageByEntityEvent)dmgEvent;
            Entity damager = dmgByEntityEvent.getDamager();
            if (damager instanceof Projectile) {
                damager = ((Projectile) damager).getShooter();
            }

            return damager.getType();
        }
        return null;
    }

    private GsonLoader getGsonLoader() {
        Gson gson = new GsonBuilder()
        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_DASHES)
        .setPrettyPrinting().create();
        return new GsonLoader(gson, this);
    }

    private void loadData() {
        try {
            deathMessages = getGsonLoader().loadOrCreate("deathMessages.json",
                    DeathMessages.class);
        } catch (GsonLoadException exc) {
            getLogger().log(Level.SEVERE, "Unable to load deathMessages.json", exc);
            deathMessages = new DeathMessages();
        }
    }

    private void writeData() {
        try {
            getGsonLoader().write("deathMessages.json", deathMessages);
        } catch (GsonLoadException exc) {
            getLogger().log(Level.SEVERE, "Unable to write deathMessages.json", exc);
        }
    }
}
