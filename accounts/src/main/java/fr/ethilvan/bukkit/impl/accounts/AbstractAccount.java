package fr.ethilvan.bukkit.impl.accounts;

import java.sql.Timestamp;

import org.bukkit.ChatColor;

import fr.aumgn.bukkitutils.playerref.PlayerRef;
import fr.ethilvan.bukkit.api.accounts.Account;

public abstract class AbstractAccount implements Account {

    @Override
    public String getColoredNamePlate() {
        return getRole().getColor() + getName();
    }

    @Override
    public String getColoredName() {
        return getColoredNamePlate() + ChatColor.WHITE;
    }

    @Override
    public PlayerRef getPlayerRef() {
        return PlayerRef.get(getMinecraftName());
    }

    public abstract MinecraftStats getStats();

    public abstract void setLastMinecraftVisit(Timestamp lastMinecraftVisit);
}
