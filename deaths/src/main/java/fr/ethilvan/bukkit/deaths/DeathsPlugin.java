package fr.ethilvan.bukkit.deaths;

import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Zombie;
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
        DeathMessage deathMessage = deathMessages.get(eventCause.toString());
        String killerName = getKillerName(event);

        if (killerName == null) {
            killerName = "";
        }

        if (killerName.equals("Dispenser")) {
            deathMessage = deathMessages.get("Dispenser");
        }

        EntityType killer = getEntityType(killerName);

        if (deathMessage == null && killer != null) {
            deathMessage = deathMessages.get(killer.getName());
        }

        if (killer == EntityType.SKELETON) {
            Skeleton entity = (Skeleton) getKillerEntityClass(event);
            deathMessage = deathMessages.getIfEntityHasTypes(killerName, entity.getSkeletonType().getId());
        } else if (killer == EntityType.ZOMBIE) {
            Zombie entity = (Zombie) getKillerEntityClass(event);
            deathMessage = deathMessages.getIfEntityHasTypes(killerName, getZombieType(entity));
        } else if (killer == EntityType.PLAYER) {
            deathMessage = deathMessages.get("Player");
        }

        if (deathMessage != null) {
            event.setDeathMessage(deathMessage.getOneMessage(event));
        }
    }

    private int getZombieType(Zombie zombie) {
        return !zombie.isVillager() ? 0 : 1;
    }

    private Entity getKillerEntityClass(PlayerDeathEvent event) {
        EntityDamageEvent dmgEvent = (event.getEntity()).getLastDamageCause();
        if (dmgEvent instanceof EntityDamageByEntityEvent) {
            EntityDamageByEntityEvent dmgByEntityEvent =
                    (EntityDamageByEntityEvent)dmgEvent;
            Entity damager = dmgByEntityEvent.getDamager();
            if (damager instanceof Projectile) {
                damager = ((Projectile) damager).getShooter();
            }

            return damager;
        }
        return null;
    }

    private String getKillerName(PlayerDeathEvent event) {
        EntityDamageEvent dmgEvent = (event.getEntity()).getLastDamageCause();
        if (dmgEvent instanceof EntityDamageByEntityEvent) {
            EntityDamageByEntityEvent dmgByEntityEvent =
                    (EntityDamageByEntityEvent)dmgEvent;
            Entity damager = dmgByEntityEvent.getDamager();
            if (damager instanceof Projectile) {
                damager = ((Projectile) damager).getShooter();
                if (damager == null) {
                    return "Dispenser";
                }
            }

            return damager.getType().getName();
        }
        return null;
    }

    private EntityType getEntityType(String name) {
        EntityType type = EntityType.fromName(name);
        if (type == null) {
            return EntityType.UNKNOWN;
        }

        return type;
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
