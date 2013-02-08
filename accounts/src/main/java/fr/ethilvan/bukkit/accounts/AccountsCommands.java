package fr.ethilvan.bukkit.accounts;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import fr.aumgn.bukkitutils.command.Command;
import fr.aumgn.bukkitutils.command.args.CommandArgs;
import fr.ethilvan.bukkit.api.EthilVan;
import fr.ethilvan.bukkit.api.accounts.Account;
import fr.ethilvan.bukkit.api.command.EthilVanCommands;

public class AccountsCommands extends EthilVanCommands {

    @Command(name = "realname", min = 0, max = 1)
    public void realName(CommandSender sender, CommandArgs args) {
        List<Account> accounts = args.get(0, Account).matchOr(sender);
        for (Account account : accounts) {
            StringBuilder msg = new StringBuilder();
            msg.append("Le pseudo Minecraft de ");
            msg.append(account.getColoredName());
            msg.append(" est ");
            msg.append(ChatColor.GREEN);
            msg.append(account.getMinecraftName());

            sender.sendMessage(msg.toString());
        }
    }

    @Command(name = "visitors")
    public void visitors(CommandSender sender) {
        List<? extends Account> visitors = EthilVan.getAccounts()
                .getVisitors();
        for (Account visitor : visitors) {
            sender.sendMessage(" - " + visitor.getColoredName());
        }
    }
}
