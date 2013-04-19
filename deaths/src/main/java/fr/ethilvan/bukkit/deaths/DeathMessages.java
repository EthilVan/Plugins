package fr.ethilvan.bukkit.deaths;

import java.util.ArrayList;
import java.util.List;

public class DeathMessages {

    private List<DeathMessage> deathMessages = new ArrayList<DeathMessage>();

    public DeathMessages() {
        List<String> fireMessages = new ArrayList<String>();
        fireMessages.add("<player> a été brûlé vif.");
        fireMessages.add("<player> a fini en cendres.");
        fireMessages.add("<player> a eu chaud, très chaud, trop chaud...");
        this.deathMessages.add(new DeathMessage("FIRE", fireMessages));
        List<String> zombieMessages = new ArrayList<String>();
        zombieMessages.add("<player> s'est fait dévorer vivant par un zombie.");
        zombieMessages.add("<player> a été tué par un zombie.");
        zombieMessages.add("<player> a servi de repas à un zombie.");
    }

    public List<DeathMessage> getDeathMessages() {
        return this.deathMessages;
    }
}
