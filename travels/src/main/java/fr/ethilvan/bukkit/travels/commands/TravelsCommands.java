package fr.ethilvan.bukkit.travels.commands;

import java.util.Locale;

import fr.aumgn.bukkitutils.command.Commands;
import fr.aumgn.bukkitutils.command.CommandsRegistration;
import fr.aumgn.bukkitutils.command.arg.CommandArgFactory;
import fr.aumgn.bukkitutils.command.arg.impl.AbstractCommandArg;
import fr.ethilvan.bukkit.travels.TravelsPlugin;
import fr.ethilvan.bukkit.travels.exception.NoSuchPort;
import fr.ethilvan.bukkit.travels.exception.NoSuchTravel;
import fr.ethilvan.bukkit.travels.travels.Port;
import fr.ethilvan.bukkit.travels.travels.Travel;

public class TravelsCommands implements Commands {

    public static class TravelArg extends AbstractCommandArg<Travel> {

        public TravelArg(String string) {
            super(string);
        }

        @Override
        public Travel value() {
            if (!plugin.getTravels().contains(string)) {
                throw new NoSuchTravel(plugin.getMessages().get("exception.travel.notExist", string));
            }

            return plugin.getTravels().get(string);
        }
    }

    public static class PortArg extends AbstractCommandArg<Port> {

        public PortArg(String string) {
            super(string);
        }

        @Override
        public Port value() {
            if (!plugin.getPorts().contains(string)) {
                throw new NoSuchPort(plugin.getMessages().get("exception.port.notExist", string));
            }

            return plugin.getPorts().get(string);
        }
    }

    protected static TravelsPlugin plugin;
    protected static CommandArgFactory<TravelArg> Travel;
    protected static CommandArgFactory<PortArg> Port;

    public static void register(TravelsPlugin plugin) {
        TravelsCommands.plugin = plugin;

        Travel = new CommandArgFactory<TravelArg>() {
            @Override
            public TravelArg createCommandArg(String string) {
                return new TravelArg(string);
            }
        };

        Port = new CommandArgFactory<PortArg>() {
            @Override
            public PortArg createCommandArg(String string) {
                return new PortArg(string);
            }
        };

        CommandsRegistration registration =
                new CommandsRegistration(plugin, Locale.FRANCE);
        registration.register(new PortCommands());
        registration.register(new TravelCommands());
    }

    protected String get(String key) {
        return plugin.getMessages().get(key);
    }

    protected String get(String key, Object... arguments) {
        return plugin.getMessages().get(key, arguments);
    }
}