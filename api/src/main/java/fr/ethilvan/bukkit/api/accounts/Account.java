package fr.ethilvan.bukkit.api.accounts;

import java.sql.Timestamp;

import fr.aumgn.bukkitutils.playerref.PlayerRef;

public interface Account {

    public String getName();

    public String getRole();

    public String[] getRoles();

    public String getColoredNamePlate();

    public String getColoredName();

    public String getMinecraftName();

    public Timestamp getLastMinecraftVisit();

    public void setLastMinecraftVisit(Timestamp time);

    public PlayerRef getPlayerRef();
}