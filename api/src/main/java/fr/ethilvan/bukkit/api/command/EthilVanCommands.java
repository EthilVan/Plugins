package fr.ethilvan.bukkit.api.command;

import fr.aumgn.bukkitutils.command.Commands;
import fr.aumgn.bukkitutils.command.arg.CommandArgFactory;

public class EthilVanCommands implements Commands {

    protected static final CommandArgFactory<AccountArg> Account =
            new CommandArgFactory<AccountArg>() {
                @Override
                public AccountArg createCommandArg(String string) {
                    return new AccountArg(string);
                }
            };
}
