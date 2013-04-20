package fr.ethilvan.bukkit.deaths;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;

import fr.aumgn.bukkitutils.util.Util;

public class DeathMessage {

    private List<String> messages;

    public DeathMessage(List<String> messages) {
        this.messages = new ArrayList<String>();
        this.messages.addAll(messages);
    }

    public List<String> getMessages() {
        return this.messages;
    }

    public String getOneMessage(PlayerDeathEvent event) {
        return this.parse(Util.pickRandom(messages), event);
    }

    private boolean isVowel(char c) {
        if (!Character.isLetter(c)) {
            return false;
        }

        char[] VOWELS = {'a','e','i','o','u','y'};

        for (Character vowel : VOWELS) {
            if (Character.toLowerCase(c) == vowel)
                return true;
        }

        return false;
    }

    private String ofFormat(String string) {
        char firstChar = ChatColor.stripColor(string).charAt(0);
        if (isVowel(firstChar)) {
            return "d'" + string;
        }

        return "de " + string;
    }

    private String parse(String message, PlayerDeathEvent event) {
        Player death = event.getEntity();

        message = message.replaceAll("<player>", death.getDisplayName());
        message = message.replaceAll("<world>", death.getWorld().getName());

        if (message.contains("<ofplayer>")) {
            message.replaceAll("<ofplayer>", this.ofFormat(death.getDisplayName()));
        }
        if (message.contains("<ofworld>")) {
            message.replaceAll("<ofworld>", this.ofFormat(death.getWorld().getName()));
        }
        Player killer = death.getKiller();
        if (killer != null) {
            message = message.replaceAll("<murderer>", killer.getDisplayName());
            if (message.contains("<ofmurderer>")) {
                message.replaceAll("<ofmurderer>", this.ofFormat(killer.getDisplayName()));
            }
            message = message.replaceAll("<item>", killer.getItemInHand().getType().toString());
        }
        return message;
    }
}
