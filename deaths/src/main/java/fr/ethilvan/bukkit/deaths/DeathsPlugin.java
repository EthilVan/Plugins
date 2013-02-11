package fr.ethilvan.bukkit.deaths;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.plugin.java.JavaPlugin;

import fr.ethilvan.bukkit.deaths.death.DispenserPlayerDeath;
import fr.ethilvan.bukkit.deaths.death.PlayerDeath;
import fr.ethilvan.bukkit.deaths.death.PlayerKilledByMob;
import fr.ethilvan.bukkit.deaths.death.PlayerKilledByPlayer;

public class DeathsPlugin extends JavaPlugin implements Listener {

    private DeathMessages messages;

    @Override
    public void onEnable() {
        messages = new DeathMessages(this);
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd,
            String lbl, String[] args) {
        messages = new DeathMessages(this);
        sender.sendMessage("Messages rechargés.");
        return true;
    }

    @EventHandler
    protected void onPlayerDeath(PlayerDeathEvent event) {
        event.setDeathMessage(null);
        PlayerDeath playerDeath = process(event);
        String message = messages.getDeathMessage(playerDeath);
        event.setDeathMessage(message);
    }

    public PlayerDeath process(PlayerDeathEvent event) {
        EntityDamageEvent dmgEvent = (event.getEntity()).getLastDamageCause();
        if (dmgEvent instanceof EntityDamageByEntityEvent) {
            EntityDamageByEntityEvent dmgByEntityEvent =
                    (EntityDamageByEntityEvent)dmgEvent;
            Entity damager = dmgByEntityEvent.getDamager();
            if (damager instanceof Projectile) {
                damager = ((Projectile) damager).getShooter();
                if (damager == null) {
                    return new DispenserPlayerDeath(event);
                }
            }
            if (damager instanceof Player) {
                return new PlayerKilledByPlayer(event, (Player) damager);
            } else {
                return new PlayerKilledByMob(event, damager);
            }
        }
        return new PlayerDeath(event);
    }
}
