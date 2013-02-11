package fr.ethilvan.bukkit.snowfight;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.entity.Snowman;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.util.Vector;

public class SnowFightListener implements Listener {

    private final SnowFight snowFight;

    public SnowFightListener(SnowFight snowFight) {
        this.snowFight = snowFight;
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        int lives = snowFight.getLives(player);
        snowFight.updateStatus(player, lives);

        if (lives == 0) {
            snowFight.scheduleGetReadyToSnowFight(event.getPlayer());
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onEntityDamage(EntityDamageEvent event) {
        if (!(event instanceof EntityDamageByEntityEvent)) {
            return;
        }

        Entity damager = ((EntityDamageByEntityEvent) event).getDamager();
        if (!(damager instanceof Snowball)) {
            return;
        }

        Snowball snowBall = (Snowball) damager;
        LivingEntity shooter = snowBall.getShooter();
        if (shooter instanceof Player) {
            Player playerShooter = (Player) shooter; 
            if (snowFight.isDead(playerShooter)) {
                playerShooter.sendMessage("Vous êtes K.O., "
                        + "vous ne pouvez plus infliger de dégâts.");
                return;
            }
        }

        Entity entity = event.getEntity();
        if (entity instanceof Snowman) {
            ((Snowman) entity).setTarget(snowBall.getShooter());
            return;
        }

        if (!(entity instanceof Player)) {
            return;
        }

        Player player = (Player) entity;
        if (snowFight.isDead(player)) {
            return;
        }

        Vector velocity = snowBall.getVelocity();
        if (snowFight.damage(player, shooter)) {
            velocity = velocity.multiply(3);
            if (shooter instanceof Player) {
                Player playerShooter = (Player) shooter;
                player.sendMessage(
                        playerShooter.getDisplayName() + " vous a mis K.O.");
                playerShooter.sendMessage(
                        "Vous avez mis " + player.getDisplayName() + " K.O.");
            } else {
                player.sendMessage("Vous êtes K.O. !");
            }
            player.sendMessage("Vous pourrez rejouer après 30 secondes.");
        }

        player.setVelocity(velocity);
    }
}
