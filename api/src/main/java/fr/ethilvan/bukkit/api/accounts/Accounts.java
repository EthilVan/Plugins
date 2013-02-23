package fr.ethilvan.bukkit.api.accounts;

import java.util.List;
import java.util.Set;

import org.bukkit.entity.Player;

import fr.aumgn.bukkitutils.playerref.PlayerRef;

public interface Accounts {

    Account getByName(String name);

    Account getByMinecraftName(String name);

    Account get(PlayerRef playerRef);

    Account get(Player player);

    List<Account> getByPartialName(String name);

    Role getRole(String id);

    Set<String> getPseudoRoles(Player player);

    void addPseudoRole(Player player, String pseudorole);

    void removePseudoRole(Player player, String pseudorole);

    boolean isVisitor(Player player);

    List<? extends Account> getVisitors();
}
