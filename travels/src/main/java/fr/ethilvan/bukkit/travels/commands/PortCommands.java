package fr.ethilvan.bukkit.travels.commands;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.sk89q.worldedit.LocalSession;
import com.sk89q.worldedit.LocalWorld;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.bukkit.BukkitUtil;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.regions.CuboidRegionSelector;
import com.sk89q.worldedit.regions.Region;
import com.sk89q.worldedit.regions.RegionSelector;

import fr.aumgn.bukkitutils.command.Command;
import fr.aumgn.bukkitutils.command.NestedCommands;
import fr.aumgn.bukkitutils.command.args.CommandArgs;
import fr.aumgn.bukkitutils.command.exception.CommandError;
import fr.ethilvan.bukkit.api.EthilVan;
import fr.ethilvan.bukkit.travels.travels.Port;
import fr.ethilvan.bukkit.travels.travels.Ports;
import static fr.ethilvan.bukkit.api.worldedit.WorldEditUtils.fromBU;
import static fr.ethilvan.bukkit.api.worldedit.WorldEditUtils.getRegion;

@NestedCommands({ "travels", "port" })
public class PortCommands extends TravelsCommands {
 
    @Command(name = "create", min = 1, max = 1)
    public void create(Player player, CommandArgs args) {
        Ports ports = plugin.getPorts();
        String portName = args.get(0);

        if (ports.contains(portName)) {
            throw new CommandError(get("commands.port.alreadyExist", portName));
        }

        ports.create(portName, player.getWorld());
        player.sendMessage(get("commands.port.create", portName));
    }

    @Command(name = "remove", min = 1, max = 1)
    public void remove(Player player, CommandArgs args) {
        Port port = args.get(0, Port).value();
        plugin.getPorts().remove(port);
        player.sendMessage(get("commands.port.remove", port.getName()));
    }

    @Command(name = "departure", min = 1, max = 1)
    public void setDeparture(Player player, CommandArgs args) {
        Port port = args.get(0, Port).value();
        Region region = getRegion(player);
        port.setDeparture(region); 
        player.sendMessage(get("commands.port.departure", port.getName()));
    }

    @Command(name = "destination", min = 1, max = 1)
    public void setDestination(Player player, CommandArgs args) {
        Port port = args.get(0, Port).value();
        port.setPosition(player.getLocation());
        player.sendMessage(get("commands.port.destination", port.getName()));
    }

    @Command(name = "info", min = 1, max = 1)
    public void info(Player player, CommandArgs args) {
        Port port = args.get(0, Port).value();
        player.sendMessage(ChatColor.UNDERLINE + "" + ChatColor.AQUA + port.getName() + ":");
        player.sendMessage(ChatColor.GREEN + "- " + port.getDestination().getBlockX() + ", " +
                port.getDestination().getBlockY() + "," +
                port.getDestination().getBlockZ() +
                "(" + port.getWorld().getName() + ")");
    }

    @Command(name = "list")
    public void list(Player player, CommandArgs args) {
        if (plugin.getPorts().isEmpty()) {
            player.sendMessage(get("commands.isEmpty"));
        }

        for (Port port : plugin.getPorts()) {
            player.sendMessage(ChatColor.GREEN + "   - " + port.getName());
        }
    }

    @Command(name = "select", min = 1, max = 1)
    public void select(Player player, CommandArgs args) {
        Port port = args.get(0, Port).value();
        WorldEditPlugin we = EthilVan.getWorldEdit();

        LocalSession session = we.getSession(player);
        Vector min = fromBU(port.getDepartureMin());
        Vector max = fromBU(port.getDepartureMax());
        LocalWorld weWorld = BukkitUtil.getLocalWorld(port.getWorld());
        RegionSelector sel = new CuboidRegionSelector(weWorld, min, max);
        session.setRegionSelector(weWorld, sel);
        session.dispatchCUISelection(we.wrapPlayer(player));
        player.sendMessage(get("commands.port.select"));
    }
}
