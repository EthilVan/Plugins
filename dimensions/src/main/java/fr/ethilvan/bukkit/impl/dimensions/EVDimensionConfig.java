package fr.ethilvan.bukkit.impl.dimensions;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.Difficulty;
import org.bukkit.GameMode;
import org.bukkit.configuration.ConfigurationSection;

import fr.ethilvan.bukkit.api.dimensions.DimensionConfig;

public class EVDimensionConfig implements DimensionConfig {

    private static final Difficulty DEFAULT_DIFFICULTY = Difficulty.NORMAL;
    private static final GameMode DEFAULT_GAMEMODE = GameMode.SURVIVAL;

    private final Difficulty difficulty;
    private final GameMode gamemode;
    private final Set<String> flags;

    public EVDimensionConfig(ConfigurationSection section) {
        if (section.isString("difficulty")) {
            difficulty = Difficulty.valueOf(section.getString("difficulty"));
        } else {
            difficulty = DEFAULT_DIFFICULTY;
        }

        if (section.isString("gamemode")) {
            gamemode = GameMode.valueOf(section.getString("gamemode"));
        } else {
            gamemode = DEFAULT_GAMEMODE;
        }

        flags = new HashSet<String>();
        if (section.isList("flags")) {
            for (String flag : section.getStringList("flags")) {
                flags.add(flag);
            }
        }
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    @Override
    public GameMode getGameMode() {
        return gamemode;
    }

    @Override
    public boolean hasFlag(String flag) {
        return flags.contains(flag);
    }
}
