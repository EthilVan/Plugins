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
            throw new CommandError("Le voyage " + travelName + " existe déjà.");
        }

        travels.create(travelName);
        player.sendMessage(ChatColor.GREEN + "Voyage créé avec le nom : " + travelName + ".");

        if (args.length() == 3) {
            Travel travel = travels.get(travelName);
            Port depart = args.get(1, Port).value();
            Port dest = args.get(2, Port).value();

            travel.setDeparture(depart);
            travel.setDestination(dest);
            player.sendMessage(ChatColor.GREEN + "Départ du voyage "
                    + travel.getName() + " défini au port " + depart.getName() + ".");
            player.sendMessage(ChatColor.GREEN + "Destination du voyage "
                    + travel.getName() + " définie au port " + dest.getName() + ".");
        }

        if (args.hasArgFlag('p')) {
            double price = args.getDouble('p').value();
            Travel travel = travels.get(travelName);
            travel.setPrice(price);
            player.sendMessage(ChatColor.GREEN + "Prix du voyage " +
                    travel.getName() + " défini à " + price + ".");
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
                throw new CommandError("Format d'horaire inconnu : (" + schedule + "), attendu hh:mn");
            }

            if (hours > 23) {
                throw new CommandError("L'heure doit être comprise entre 0 et 23.");
            }
            if (mn > 59) {
                throw new CommandError("Les minutes doivent être comprises entre 0 et 59.");
            }
            int travelSchedule = hours * 60 + mn;
            travel.setSchedule(TimeUtils.travelMinutesToTicks(travelSchedule));
            player.sendMessage(ChatColor.GREEN + "Horaire du voyage : " +
                    travel.getName() + " défini à " + hours + ":" + mn + ".");
        }
    }

    @Command(name = "remove", min = 1, max = 1)
    public void remove(Player player, CommandArgs args) {  
        Travel travel = args.get(0, Travel).value();
        plugin.getTravels().remove(travel);
        player.sendMessage(ChatColor.GREEN + "Voyage " + travel.getName()
                + " supprimé avec succès.");
    }

    @Command(name = "departure", min = 2, max = 2)
    public void departure(Player player, CommandArgs args) { 
        Travel travel = args.get(0, Travel).value();
        Port port = args.get(1, Port).value();

        travel.setDeparture(port);
        player.sendMessage(ChatColor.GREEN + "Départ du voyage "
                + travel.getName() + " défini au port " + port.getName() + ".");
    }

    @Command(name = "destination", min = 2, max = 2)
    public void destination(Player player, CommandArgs args) { 
        Travel travel = args.get(0, Travel).value();
        Port port = args.get(1, Port).value();

        travel.setDestination(port);
        player.sendMessage(ChatColor.GREEN + "Destination du voyage "
                + travel.getName() + " définie au port " + port.getName() + ".");
    }

    @Command(name = "schedule", min = 2, max = 2)
    public void schedule(Player player, CommandArgs args) {
        Travel travel = args.get(0, Travel).value();

        if (args.get(1).equalsIgnoreCase("reset")) {
            travel.setSchedule(-1);
            player.sendMessage(ChatColor.GREEN +
                    "Horaire du voyage " + travel.getName() + " supprimé avec succès.");
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
            throw new CommandError("Format d'horaire inconnu : (" + schedule + "), attendu hh:mn");
        }
        if (hours > 23) {
            throw new CommandError("L'heure doit être comprise entre 0 et 23.");
        }
        if (mn > 59) {
            throw new CommandError("Les minutes doivent être comprises entre 0 et 59.");
        }
        int travelSchedule = hours * 60 + mn;
        travel.setSchedule(TimeUtils.travelMinutesToTicks(travelSchedule));
        player.sendMessage(ChatColor.GREEN + "Horaire du voyage : " +
                travel.getName() + " défini à " + hours + ":" + mn + ".");
    }

    @Command(name = "price", min = 2, max = 2)
    public void price(Player player, CommandArgs args) {  
        Travel travel = args.get(0, Travel).value();
        double price = args.getDouble(1).value();

        travel.setPrice(price);
        player.sendMessage(ChatColor.GREEN + "Prix du voyage " +
                travel.getName() + " défini à " + price + "."); 
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
            player.sendMessage(ChatColor.RED + "Aucun voyage n'a été créé pour l'instant.");
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
            player.sendMessage("Voyage désactivé");
        } else {
            travel.setActive(true);
            player.sendMessage("Voyage activé");
        }
    }
}
