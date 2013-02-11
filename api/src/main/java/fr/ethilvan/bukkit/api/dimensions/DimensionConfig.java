package fr.ethilvan.bukkit.api.dimensions;

import org.bukkit.Difficulty;
import org.bukkit.GameMode;

public interface DimensionConfig {

    Difficulty getDifficulty();

    GameMode getGameMode();

    boolean hasFlag(String flag);
}
