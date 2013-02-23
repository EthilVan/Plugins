package fr.ethilvan.bukkit.travels.commands;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import fr.aumgn.bukkitutils.command.Command;
import fr.aumgn.bukkitutils.command.NestedCommands;
import fr.aumgn.bukkitutils.command.args.CommandArgs;
import fr.aumgn.bukkitutils.command.exception.CommandError;
import fr.ethilvan.bukkit.travels.TimeUtils;
import fr.ethilvan.bukkit.travels.travels.Port;
import fr.ethilvan.bukkit.travels.travels.Travel;
import fr.ethilvan.bukkit.travels.travels.Travels;

@NestedCommands({ "travels", "travel" })
public class TravelCommands extends TravelsCommands{

    @Command(name = "create", min = 1, max = 3, argsFlags = "ps")
    public void create(Player player, CommandArgs args) {
        Travels travels = plugin.getTravels();
        String travelName = args.get(0);

        if (travels.contains(travelName)) {
            throw new CommandError(get("commands.travel.alreadyExist", travelName));
        }

        travels.create(travelName);
        player.sendMessage(get("commands.travel.create", travelName));

        if (args.length() == 3) {
            Travel travel = travels.get(travelName);
            Port depart = args.get(1, Port).value();
            Port dest = args.get(2, Port).value();

            travel.setDeparture(depart);
            travel.setDestination(dest);
            player.sendMessage(get("commands.travel.departure",
                    travel.getName(), depart.getName()));
            player.sendMessage(get("commands.travel.destination",
                    travel.getName(), dest.getName()));
        }

        if (args.hasArgFlag('p')) {
            double price = args.getDouble('p').value();
            Travel travel = travels.get(travelName);
            travel.setPrice(price);
            player.sendMessage(get("commands.travel.price",
                    travel.getName(), price));
        }

        if (args.hasArgFlag('s')) {
            Travel travel = travels.get(travelName);
            String schedule = args.get('s');
            String[] parts = schedule.split(":");
            int hours = 0;
            int mn = 0;
            try {
                hours = Integer.parseInt(parts[0]);
                mn = Integer.parseInt(parts[1]);
            } catch (NumberFormatException e) {
                throw new CommandError(get("commands.travel.schedule.timeError.format",
                        schedule));
            }
            if (hours > 23) {
                throw new CommandError(get("commands.travel.schedule.timeError.minutes"));
            }
            if (mn > 59) {
                throw new CommandError(get("commands.travel.schedule.timeError.hour"));
            }
            int travelSchedule = hours * 60 + mn;
            travel.setSchedule(TimeUtils.travelMinutesToTicks(travelSchedule));
            player.sendMessage(get("commands.travel.schedule",
                    travel.getName(), hours, mn));
        }
    }

    @Command(name = "remove", min = 1, max = 1)
    public void remove(Player player, CommandArgs args) {  
        Travel travel = args.get(0, Travel).value();
        plugin.getTravels().remove(travel);
        player.sendMessage(get("commands.travel.remove", travel.getName()));
    }

    @Command(name = "departure", min = 2, max = 2)
    public void departure(Player player, CommandArgs args) { 
        Travel travel = args.get(0, Travel).value();
        Port port = args.get(1, Port).value();

        travel.setDeparture(port);
        player.sendMessage(get("commands.travel.departure",
                travel.getName(), port.getName()));
    }

    @Command(name = "destination", min = 2, max = 2)
    public void destination(Player player, CommandArgs args) { 
        Travel travel = args.get(0, Travel).value();
        Port port = args.get(1, Port).value();

        travel.setDestination(port);
        player.sendMessage(get("commands.travel.destination",
                travel.getName(), port.getName()));
    }

    @Command(name = "schedule", min = 2, max = 2)
    public void schedule(Player player, CommandArgs args) {
        Travel travel = args.get(0, Travel).value();

        if (args.get(1).equalsIgnoreCase("reset")) {
            travel.setSchedule(-1);
            player.sendMessage(get("commands.travel.schedule",
                    travel.getName()));
            return;
        }

        String schedule = args.get(1);
        String[] parts = schedule.split(":");
        int hours = 0;
        int mn = 0;
        try {
            hours = Integer.parseInt(parts[0]);
            mn = Integer.parseInt(parts[1]);
        } catch (NumberFormatException e) {
            throw new CommandError(get("commands.travel.schedule.timeError.format",
                    schedule));
        }
        if (hours > 23) {
            throw new CommandError(get("commands.travel.schedule.timeError.minutes"));
        }
        if (mn > 59) {
            throw new CommandError(get("commands.travel.schedule.timeError.hour"));
        }
        int travelSchedule = hours * 60 + mn;
        travel.setSchedule(TimeUtils.travelMinutesToTicks(travelSchedule));
        player.sendMessage(get("commands.travel.schedule",
                travel.getName(), hours, mn));
    }

    @Command(name = "price", min = 2, max = 2)
    public void price(Player player, CommandArgs args) {  
        Travel travel = args.get(0, Travel).value();
        double price = args.getDouble(1).value();

        travel.setPrice(price);
        player.sendMessage(get("commands.travel.price", travel.getName(), price));
    }

    @Command(name = "info", min = 1, max = 1)
    public void info(Player player, CommandArgs args) {
        Travel travel = args.get(0, Travel).value();
        player.sendMessage(ChatColor.GOLD +
                travel.getName() + "");
        player.sendMessage(ChatColor.GREEN +
                " - Départ : " + travel.getDeparture());
        player.sendMessage(ChatColor.GREEN +
                " - Destination : " + travel.getDestination());
        int travelMinutes = TimeUtils.ticksToTravelMinutes(travel.getSchedule());
        int hours = (int) Math.floor(travelMinutes / 60);
        int minutes = travelMinutes - hours * 60;
        player.sendMessage(ChatColor.GREEN +
                " - Horaire : " + hours + ":" + minutes);
        player.sendMessage(ChatColor.GREEN +
                " - Prix : " + travel.getPrice());

        String yesNo = null; 
        if (travel.isActive()) {
            yesNo = "Oui";
        } else {
            yesNo = "Non";
        }
        player.sendMessage(ChatColor.GREEN +
                " - Activé : " + yesNo);
    }

    @Command(name = "list")
    public void list(Player player, CommandArgs args) {
        if (plugin.getTravels().isEmpty()) {
            player.sendMessage(get("commands.travel.isEmpty"));
        }

        for (Travel travel : plugin.getTravels()) {
            player.sendMessage(ChatColor.GREEN + " - " + travel.getName());
        }
    }

    @Command(name = "toggleactive", min = 1, max = 1)
    public void active(Player player, CommandArgs args) {
        Travel travel = args.get(0, Travel).value();

        if (travel.isActive()) {
            travel.setActive(false);
            player.sendMessage(get("commands.travel.inactive"));
        } else {
            travel.setActive(true);
            player.sendMessage(get("commands.travel.active"));
        }
    }
}
