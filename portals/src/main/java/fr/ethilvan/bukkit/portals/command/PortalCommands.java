package fr.ethilvan.bukkit.portals.command;

import static fr.ethilvan.bukkit.api.worldedit.WorldEditUtils.*;

import java.util.Map.Entry;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.sk89q.worldedit.bukkit.selections.CuboidSelection;
import com.sk89q.worldedit.bukkit.selections.Selection;

import fr.aumgn.bukkitutils.command.Command;
import fr.aumgn.bukkitutils.command.NestedCommands;
import fr.aumgn.bukkitutils.command.args.CommandArgs;
import fr.aumgn.bukkitutils.command.exception.CommandError;
import fr.ethilvan.bukkit.api.EthilVan;
import fr.ethilvan.bukkit.portals.PortalsCommands;
import fr.ethilvan.bukkit.portals.portal.Portal;
import fr.ethilvan.bukkit.portals.portal.Portals;

@NestedCommands("portals")
public class PortalCommands extends PortalsCommands {

    private final Portals portals;

    public PortalCommands(Portals portals) {
        this.portals = portals;
    }

    @Command(name = "define", min = 1, max = 1)
    public void define(CommandSender sender, CommandArgs args) {
        portals.create(args.get(0));
        sender.sendMessage(ChatColor.GREEN + "Portail créé avec succés");
    }

    @Command(name = "delete", min = 1, max = 1)
    public void delete(CommandSender sender, CommandArgs args) {
        Portal portal = args.get(0, Portal).value();

        portals.remove(portal);
        sender.sendMessage(ChatColor.GREEN + "Portail supprimé");
        for (Entry<String, Portal> entry : portals) {
            Portal incomingPortal = entry.getValue();
            if (incomingPortal.getDestinationName().equals(args.get(0))) {
                incomingPortal.updateDestination(null);
                incomingPortal.turnOff();
                sender.sendMessage(ChatColor.YELLOW
                        + "Portail de destination : "
                        + entry.getKey() + " eteint.");
            }
        }
    }

    @Command(name = "list")
    public void portals(CommandSender sender, CommandArgs args) {
        for (Entry<String, Portal> portal : portals) {
            sender.sendMessage(" " + portal.getKey()
                    + " dans : " + portal.getValue().getWorld().getName());
        }
    }

    @Command(name = "select", min = 1, max = 1)
    public void select(Player sender, CommandArgs args) {
        Portal portal = args.get(0, Portal).value();
        if (portal.getMinPoint() == null) {
            throw new CommandError("Ce portail n'as pas de region defini.");
        }

        Selection sel = new CuboidSelection(
                portal.getWorld(),
                fromBU(portal.getBlockMinPoint()),
                fromBU(portal.getBlockMaxPoint()));
        EthilVan.getWorldEdit().setSelection(sender, sel);
        sender.sendMessage(ChatColor.GREEN + "Portail selectionné.");
    }

    @Command(name = "teleport", min = 1, max = 1)
    public void teleport(Player sender, CommandArgs args) {
        args.get(0, Portal).value().teleport(sender);
        sender.sendMessage(ChatColor.GREEN + "Poof !");
    }

    @Command(name = "lighton", min = 1, max = 1)
    public void lighton(CommandSender sender, CommandArgs args) {
        Portal portal = args.get(0, Portal).value();
        if (portal.getDestinationName() == null) {
            throw new CommandError("Destination non trouvée "
                    + "impossible d'allumer le portail.");
        }

        portal.turnOn();
        sender.sendMessage(ChatColor.GREEN + "Portail allumé.");
    }

    @Command(name = "lightoff", min = 1, max = 1)
    public void lightoff(CommandSender sender, CommandArgs args) {
        args.get(0, Portal).value().turnOff();
        sender.sendMessage(ChatColor.GREEN + "Portail éteint.");
    }
}
