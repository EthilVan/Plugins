package fr.ethilvan.bukkit.api.accounts;

import fr.aumgn.bukkitutils.playerref.PlayerRef;

public interface Account {

    String getName();

    Role getRole();

    String getColoredNamePlate();

    String getColoredName();

    String getMinecraftName();

    PlayerRef getPlayerRef();
}