package fr.ethilvan.bukkit.deaths.death;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerKilledByPlayer extends PlayerKilled {

    private Player murderer;
    private Material item;

    public PlayerKilledByPlayer(PlayerDeathEvent event, Player murderer) {
        super(event);
        this.murderer = murderer;
        this.item = murderer.getItemInHand().getType();
    }

    @Override
    protected PlayerDeathCause guessCause(PlayerDeathEvent event) {
        return PlayerDeathCause.Joueur;
    }

    public String parseMessage(String message) {
        message = super.parseMessage(message);
        message = message.replaceAll("<item>", item.toString());
        message = message.replaceAll("<murderer>", murderer.getDisplayName());
        return message.replaceAll("<ofmurderer>", ofFormat(murderer.getDisplayName()));
    }
}
