package fr.ethilvan.bukkit.deaths;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.PlayerDeathEvent;

import fr.aumgn.bukkitutils.util.Util;

public class DeathMessage {

    private String deathCauseOrMob;
    private List<String> messages;

    public DeathMessage(String deathCauseOrMob, List<String> messages) {
        this.deathCauseOrMob = deathCauseOrMob;
        this.messages = new ArrayList<String>();
        for (String message : messages) {
            this.messages.add(message);
        }
    }

    public List<String> getMessages() {
        return this.messages;
    }

    public String getOneMessage(PlayerDeathEvent event) {
        if (this.messages.size() > 0) {
            int index = Util.getRandom().nextInt(this.messages.size());
            String message = this.messages.get(index);
            if (event.getEntity().getKiller() != null ) {
                message = message.replace("<murderer>", event.getEntity().getKiller().getDisplayName());
                message = message.replace("<item>", event.getEntity().getKiller().getItemInHand().toString());
            }
            return message.replace("<player>", getPlayer(event).getDisplayName());
        }
        return null;
    }

    public boolean isDamageCause() {
        DamageCause cause = null;
        try {
            cause = DamageCause.valueOf(deathCauseOrMob);
        } catch (IllegalArgumentException e) {
        }
        return cause != null;
    }

    public boolean isEntity() {
        return EntityType.fromName(this.deathCauseOrMob) != null;
    }

    public String getDeathCauseOrMob() {
        return this.deathCauseOrMob;
    }

    private Player getPlayer(PlayerDeathEvent event) {
        return event.getEntity();
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
}
