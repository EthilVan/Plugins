package fr.ethilvan.bukkit.travels.commands;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import fr.aumgn.bukkitutils.command.Command;
import fr.aumgn.bukkitutils.command.NestedCommands;
import fr.aumgn.bukkitutils.command.arg.basic.DoubleArg;
import fr.aumgn.bukkitutils.command.args.CommandArgs;
import fr.aumgn.bukkitutils.command.exception.CommandError;
import fr.ethilvan.bukkit.travels.travels.Port;
import fr.ethilvan.bukkit.travels.travels.Travel;
import fr.ethilvan.bukkit.travels.travels.Travels;

@NestedCommands(value = { "travels", "travel" }, defaultTo = "list")
public class TravelCommands extends TravelsCommands{

    @Command(name = "list")
    public void list(Player player, CommandArgs args) {
        if (plugin.getTravels().isEmpty()) {
            player.sendMessage(msg("commands.travel.isEmpty"));
        }

        for (Travel travel : plugin.getTravels()) {
            player.sendMessage(ChatColor.GREEN + " - " + travel.getName());
        }
    }

    @Command(name = "create", min = 1, max = 3, argsFlags = "ps")
    public void create(Player player, CommandArgs args) {
        Travels travels = plugin.getTravels();
        String travelName = args.get(0);

        if (travels.contains(travelName)) {
            throw new CommandError(msg("commands.travel.alreadyExist",
                    travelName));
        }

        Travel travel = travels.create(travelName);
        player.sendMessage(msg("commands.travel.create", travelName));

        if (args.length() > 1) {
            setDeparture(player, travel, args.get(1, Port));

            if (args.length() > 2) {
                setDestination(player, travel, args.get(2, Port));
            }
        }

        if (args.hasArgFlag('p')) {
            setPrice(player, travel, args.getDouble('p'));
        }

        if (args.hasArgFlag('s')) {
            setSchedule(player, travel, args.get('s'));
        }
    }

    @Command(name = "remove", min = 1, max = 1)
    public void remove(Player player, CommandArgs args) {  
        Travel travel = args.get(0, Travel).value();
        plugin.getTravels().remove(travel);
        player.sendMessage(msg("commands.travel.remove", travel.getName()));
    }

    @Command(name = "departure", min = 2, max = 2)
    public void departure(Player player, CommandArgs args) { 
        Travel travel = args.get(0, Travel).value();
        setDeparture(player, travel, args.get(1, Port));
    }

    @Command(name = "destination", min = 2, max = 2)
    public void destination(Player player, CommandArgs args) { 
        Travel travel = args.get(0, Travel).value();
        setDestination(player, travel, args.get(1, Port));
    }

    @Command(name = "price", min = 2, max = 2)
    public void price(Player player, CommandArgs args) {
        Travel travel = args.get(0, Travel).value();
        setPrice(player, travel, args.getDouble(1));
    }

    @Command(name = "schedule", min = 2, max = 2)
    public void schedule(Player player, CommandArgs args) {
        Travel travel = args.get(0, Travel).value();

        if (args.get(1).equalsIgnoreCase("reset")) {
            travel.setSchedule(-1);
            player.sendMessage(msg("commands.travel.schedule",
                    travel.getName()));
            return;
        }

        setSchedule(player, travel, args.get(1));
    }

    @Command(name = "toggleactive", min = 1, max = 1)
    public void active(Player player, CommandArgs args) {
        Travel travel = args.get(0, Travel).value();

        if (travel.isActive()) {
            travel.setActive(false);
            player.sendMessage(msg("commands.travel.inactive"));
        } else {
            travel.setActive(true);
            player.sendMessage(msg("commands.travel.active"));
        }
    }

    @Command(name = "info", min = 1, max = 1)
    public void info(Player player, CommandArgs args) {
        Travel travel = args.get(0, Travel).value();
        player.sendMessage(ChatColor.GOLD + travel.getName() + " : ");
        player.sendMessage(ChatColor.GREEN + " - Départ : "
                + travel.getDeparture());
        player.sendMessage(ChatColor.GREEN + " - Destination : "
                + travel.getDestination());
        player.sendMessage(ChatColor.GREEN + " - Horaire : "
                + scheduleString(travel));
        player.sendMessage(ChatColor.GREEN + " - Prix : "
                + travel.getPrice());
        player.sendMessage(ChatColor.GREEN + " - Activé : "
                + (travel.isActive() ? "Oui" : "Non"));
    }

    private void setDeparture(Player sender, Travel travel, PortArg arg) {
        Port departure = arg.value();
        travel.setDeparture(departure);
        sender.sendMessage(msg("commands.travel.departure",
                travel.getName(), departure.getName()));
    }

    private void setDestination(Player sender, Travel travel, PortArg arg) {
        Port destination = arg.value();
        travel.setDestination(destination);
        sender.sendMessage(msg("commands.travel.destination",
                travel.getName(), destination.getName()));
    }

    private void setPrice(Player sender, Travel travel, DoubleArg arg) {
        double price = arg.value();
        travel.setPrice(price);
        sender.sendMessage(msg("commands.travel.price", travel.getName(),
                price));
    }

    private void setSchedule(Player sender, Travel travel, String string) {
        String[] parts = string.split(":");
        int hours = 0;
        int mn = 0;
        try {
            hours = Integer.parseInt(parts[0]);
            mn = Integer.parseInt(parts[1]);
        } catch (NumberFormatException e) {
            throw new CommandError(msg(
                    "commands.travel.schedule.timeError.format", string));
        }
        if (hours > 23) {
            throw new CommandError(msg(
                    "commands.travel.schedule.timeError.minutes"));
        }
        if (mn > 59) {
            throw new CommandError(msg(
                    "commands.travel.schedule.timeError.hour"));
        }
        int travelSchedule = hours * 60 + mn;
        travel.setSchedule(travelMinutesToTicks(travelSchedule));
        sender.sendMessage(msg("commands.travel.schedule",
                travel.getName(), hours, mn));
    }

    private String scheduleString(Travel travel) {
        int travelMinutes = ticksToTravelMinutes(travel.getSchedule());
        int hours = (int) Math.floor(travelMinutes / 60);
        int minutes = travelMinutes - hours * 60;
        return hours + ":" + minutes;
    }

    private int travelMinutesToTicks(int minutes) {
        return (int) (((minutes + 960) % 1440) * 60 / 3.6);
    }

    private int ticksToTravelMinutes(int ticks) {
        return (int) (Math.round(ticks * 3.6 / 60) + 480) % 1440;
    }
}
