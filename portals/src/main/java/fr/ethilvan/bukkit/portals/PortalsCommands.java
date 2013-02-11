package fr.ethilvan.bukkit.portals;

import java.util.Locale;

import fr.aumgn.bukkitutils.command.Commands;
import fr.aumgn.bukkitutils.command.CommandsRegistration;
import fr.aumgn.bukkitutils.command.arg.CommandArgFactory;
import fr.ethilvan.bukkit.portals.command.PortalArg;
import fr.ethilvan.bukkit.portals.command.PortalCommands;
import fr.ethilvan.bukkit.portals.command.SetCommands;

public abstract class PortalsCommands implements Commands {

    protected static CommandArgFactory<PortalArg> Portal;

    public static void register(final PortalsPlugin plugin) {
        Portal = new CommandArgFactory<PortalArg>() {
            @Override
            public PortalArg createCommandArg(String string) {
                return new PortalArg(plugin.getPortals(), string);
            }
        };

        CommandsRegistration registration =
                new CommandsRegistration(plugin, Locale.FRANCE);
        registration.register(new PortalCommands(plugin.getPortals()));
        registration.register(new SetCommands());
    }
}
