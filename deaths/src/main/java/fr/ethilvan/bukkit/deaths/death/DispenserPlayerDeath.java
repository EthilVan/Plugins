package fr.ethilvan.bukkit.deaths.death;

import org.bukkit.event.entity.PlayerDeathEvent;


public class DispenserPlayerDeath extends PlayerDeath {

    public DispenserPlayerDeath(PlayerDeathEvent event) {
        super(event);
    }

    protected PlayerDeathCause guessCause(PlayerDeathEvent event) {
        return PlayerDeathCause.Dispenser;
    }
}
