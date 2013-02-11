package fr.ethilvan.bukkit.permissions;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.aumgn.bukkitutils.command.Command;
import fr.aumgn.bukkitutils.command.NestedCommands;
import fr.aumgn.bukkitutils.command.args.CommandArgs;
import fr.aumgn.bukkitutils.playerref.PlayerRef;
import fr.ethilvan.bukkit.api.EthilVan;
import fr.ethilvan.bukkit.api.accounts.Account;
import fr.ethilvan.bukkit.api.command.EthilVanCommands;
import fr.ethilvan.bukkit.api.permissions.Permissions;

@NestedCommands("permissions")
public class PermissionsCommands extends EthilVanCommands {

    @Command(name = "show", min = 1, max = 2)
    public void show(CommandSender sender, CommandArgs args) {
        Set<String> perms = new HashSet<String>();
        if (args.length() == 1) {
            perms = EthilVan.getPermissions()
                    .getPermissionsList(args.get(0));
        } else if (args.length() == 2) {
            perms = EthilVan.getPermissions()
                    .getPermissionsList(args.get(0), args.get(1));
        }

        for (String permission : perms) {
            sender.sendMessage(ChatColor.GREEN + permission);
        }
    }

    @Command(name = "update", max = 1, flags = "a")
    public void update(CommandSender sender, CommandArgs args) {
        List<Account> accounts = args.getList(0, Account).matchOr(sender);

        Permissions permissions = EthilVan.getPermissions();
        for (Account account : accounts) {
            PlayerRef playerRef = account.getPlayerRef();
            if (playerRef.isOnline()) {
                permissions.update(playerRef.getPlayer());
                sender.sendMessage(ChatColor.GREEN
                        + "Permissions mises a jour pour : "
                        + playerRef.getDisplayName() + ".");
            } else {
                sender.sendMessage(playerRef.getDisplayName()
                        + ChatColor.YELLOW + " n'est pas connecté.");
            }
        }
    }

    @Command(name = "update-all")
    public void updateAll(CommandSender sender) {
        for (Player player : Bukkit.getServer().getOnlinePlayers()) {
            EthilVan.getPermissions().update(player);
        }

        sender.sendMessage(ChatColor.GREEN + "Permissions mises a jour.");
        return;
    }
}
