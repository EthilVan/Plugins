package fr.ethilvan.bukkit.api.accounts;

import java.util.List;
import java.util.Set;

import org.bukkit.entity.Player;

import fr.aumgn.bukkitutils.playerref.PlayerRef;

public interface Accounts {

    public Account getByName(String name);

    public Account getByMinecraftName(String name);

    public Account get(PlayerRef playerRef);

    public Account get(Player player);

    public List<Account> getByPartialName(String name);

    public Set<String> getPseudoRoles(Player player);

    public void addPseudoRole(Player player, String pseudorole);

    public void removePseudoRole(Player player, String pseudorole);

    public boolean isVisitor(Player player);

    public List<? extends Account> getVisitors();
}
