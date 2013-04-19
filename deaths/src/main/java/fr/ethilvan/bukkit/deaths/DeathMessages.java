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
        List<String> lavaMessages = new ArrayList<String>();
        lavaMessages.add("<player> a fini dans la lave...");
        lavaMessages.add("<player> s'est transformé en Obsidienne.");
        lavaMessages.add("<player> a pris un bain dans un lac de feu.");
        this.deathMessages.add(new DeathMessage("LAVA", lavaMessages));
        List<String> zombieMessages = new ArrayList<String>();
        zombieMessages.add("<player> s'est fait dévorer vivant par un zombie.");
        zombieMessages.add("<player> a été tué par un zombie.");
        zombieMessages.add("<player> a servi de repas à un zombie.");
        this.deathMessages.add(new DeathMessage("ZOMBIE", zombieMessages));
        List<String> skeletonMessages = new ArrayList<String>();
        skeletonMessages.add("<player> s'est fait cribler de flèches par un squelette.");
        skeletonMessages.add("Un squelette a tué <player>.");
        skeletonMessages.add("<player> a une flèche dans le front.");
        this.deathMessages.add(new DeathMessage("SKELETON", skeletonMessages));
        List<String> creeperMessages = new ArrayList<String>();
        creeperMessages.add("<player> a été explosé par un Creeper.");
        creeperMessages.add("<player> a essayé de faire un câlin à un Creeper.");
        creeperMessages.add("SSSSSSssssssss...... est la derniere chose que <player> a entendu.");
        this.deathMessages.add(new DeathMessage("CREEPER", creeperMessages));
        List<String> explosionMessages = new ArrayList<String>();
        explosionMessages.add("<player> a apparemment un caractère explosif.");
        explosionMessages.add("<player> a explosé.");
        explosionMessages.add("C'est ça de jouer avec la TNT <player>.");
        this.deathMessages.add(new DeathMessage("BLOCK_EXPLOSION", explosionMessages));
        List<String> playersMessages = new ArrayList<String>();
        playersMessages.add("<murderer> a tué <player>.");
        playersMessages.add("<player> a été surclassé par <murderer>.");
        playersMessages.add("<murderer> a dominé <player>.");
        this.deathMessages.add(new DeathMessage("PLAYER", playersMessages));
    }

    public List<DeathMessage> getDeathMessages() {
        return this.deathMessages;
    }
}
