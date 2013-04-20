package fr.ethilvan.bukkit.deaths;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;

import fr.aumgn.bukkitutils.util.Util;

public class DeathMessage {

    private List<String> messages;

    public DeathMessage(List<String> messages) {
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
        return false;
    }

    public boolean isEntity() {
        return true;
    }

    private Player getPlayer(PlayerDeathEvent event) {
        return event.getEntity();
    }
}
