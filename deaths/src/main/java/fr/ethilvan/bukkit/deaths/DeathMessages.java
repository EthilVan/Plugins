package fr.ethilvan.bukkit.deaths;

import java.util.HashMap;
import java.util.Map;

public class DeathMessages {

    private Map<String, DeathMessage> deathMessages = new HashMap<String, DeathMessage>();

    public Map<String, DeathMessage> getDeathMessages() {
        return deathMessages;
    }

    public DeathMessage get(String causeOrMob) {
        return deathMessages.get(getEntityTypeName(causeOrMob));
    }

    public DeathMessage getIfEntityHasTypes(String causeOrMob, int type) {
        return deathMessages.get(causeOrMob + ":" + type);
    }

    public int getEntityType(String mob) {
        int type = 0;
        try {
            type = Integer.parseInt(mob.split(":")[1]);
        } catch (NumberFormatException e) {
        }
        return type;
    }

    public String getEntityTypeName(String mob) {
        return mob.split(":")[0];
    }
}
