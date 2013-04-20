package fr.ethilvan.bukkit.deaths;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DeathMessages {

    private Map<String, DeathMessage> deathMessages = new HashMap<String, DeathMessage>();

    public DeathMessages() {
        List<String> lavaMessages = new ArrayList<String>();
        lavaMessages.add("<player> a fini dans la lave...");
        lavaMessages.add("<player> s'est transformé en Obsidienne.");
        lavaMessages.add("D'après <player>, un bain de lave est bon pour la peau, du moins c'est ce qu'il croyait.");
        this.deathMessages.put("LAVA", new DeathMessage(lavaMessages));
        List<String> zombieMessages = new ArrayList<String>();
        zombieMessages.add("<player> s'est fait dévorer vivant par un zombie.");
        zombieMessages.add("<player> a été tué par un zombie.");
        zombieMessages.add("<player> a servi de repas à un zombie.");
        this.deathMessages.put("Zombie", new DeathMessage(zombieMessages));
    }

    public Map<String, DeathMessage> getDeathMessages() {
        return deathMessages;
    }

    public DeathMessage get(String causeOrMob) {
        return deathMessages.get(causeOrMob);
    }

    public boolean contains(String causeOrMob) {
        return deathMessages.containsKey(causeOrMob);
    }
}
