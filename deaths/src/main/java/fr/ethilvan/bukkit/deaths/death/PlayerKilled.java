package fr.ethilvan.bukkit.deaths.death;

import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerKilled extends PlayerDeath {

    public PlayerKilled(PlayerDeathEvent event) {
        super(event);
    }
}
