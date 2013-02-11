package fr.ethilvan.bukkit.portals.command;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.regions.Region;

import fr.aumgn.bukkitutils.command.Command;
import fr.aumgn.bukkitutils.command.NestedCommands;
import fr.aumgn.bukkitutils.command.args.CommandArgs;
import fr.aumgn.bukkitutils.command.exception.CommandError;
import fr.ethilvan.bukkit.api.worldedit.WorldEditUtils;
import fr.ethilvan.bukkit.portals.PortalsCommands;
import fr.ethilvan.bukkit.portals.exception.NotACuboidRegionException;
import fr.ethilvan.bukkit.portals.portal.Portal;

@NestedCommands({ "portals", "set" })
public class SetCommands extends PortalsCommands {

    @Command(name = "area", min = 1, max = 1)
    public void area(Player sender, CommandArgs args) {
        Portal portal = args.get(0, Portal).value();
        Region region = WorldEditUtils.getRegion(sender);
        if (!(region instanceof CuboidRegion)) {
            throw new NotACuboidRegionException();
        }

        portal.update(region);
        sender.sendMessage(ChatColor.GREEN + "Zone du portail mise a jour.");
    }

    @Command(name = "inside", min = 1, max = 1)
    public void inside(Player sender, CommandArgs args) {
        Portal portal = args.get(0, Portal).value();
        portal.update(sender.getLocation());
        sender.sendMessage(ChatColor.GREEN
                + "Position interne du portail mise a jour.");
    }

    @Command(name = "dest", min = 2, max = 2)
    public void dest(CommandSender sender, CommandArgs args) {
        Portal portal = args.get(0, Portal).value();
        Portal dest = args.get(1, Portal).value();

        if (!dest.isComplete()) {
            throw new CommandError("Le portail de destination "
                    + "n'est pas complet");
        }
        portal.updateDestination(args.get(1));
        sender.sendMessage(ChatColor.GREEN
                + "Destination du portail mise a jour.");
    }
}
