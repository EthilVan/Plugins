package fr.ethilvan.bukkit.deaths.death;

import org.bukkit.ChatColor;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.entity.Player;

public class PlayerDeath {

    private static final char[] VOWELS = {'a','e','i','o','u', 'y'};

    protected PlayerDeathCause cause;
    private Player player;

    public PlayerDeath(PlayerDeathEvent event) {
        cause = guessCause(event);
        player = event.getEntity();
    }

    protected PlayerDeathCause guessCause(PlayerDeathEvent event) {
        if (event.getEntity().getLastDamageCause() == null) {
            return PlayerDeathCause.Indeterminee;
        }

        DamageCause bukkitCause = event.getEntity().getLastDamageCause().getCause();
        PlayerDeathCause guessedCause = PlayerDeathCause.get(bukkitCause);
        if (guessedCause == null) {
            return PlayerDeathCause.Indeterminee;
        }

        return guessedCause;
    }

    private boolean isVowel(char c) {
        if (!Character.isLetter(c)) {
            return false;
        }

        for (Character vowel : VOWELS) {
            if (Character.toLowerCase(c) == vowel)
                return true;
        }

        return false;
    }

    protected String ofFormat(String string) {
        char firstChar = ChatColor.stripColor(string).charAt(0);
        if (isVowel(firstChar)) {
            return "d'" + string;
        }

        return "de " + string;
    }

    public String parseMessage(String message) {
        String worldName = player.getWorld().getName();
        message = message.replaceAll("<player>", player.getDisplayName());
        message = message.replaceAll("<ofplayer>", ofFormat(player.getDisplayName()));
        message = message.replaceAll("<world>", worldName);
        return message.replaceAll("<ofworld>", ofFormat(worldName));
    }

    public String getConfigPath() {
        return cause.name();
    }
}
