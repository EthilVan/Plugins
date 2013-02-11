package fr.ethilvan.bukkit.dimensions;

import org.bukkit.World;
import org.bukkit.entity.Player;

import fr.aumgn.bukkitutils.command.Command;
import fr.aumgn.bukkitutils.command.Commands;
import fr.aumgn.bukkitutils.command.args.CommandArgs;
import fr.ethilvan.bukkit.api.EthilVan;

public class DimensionsCommands implements Commands {

    @Command(name = "worldtp", max = 1)
    public void onPlayerCommand(Player player, CommandArgs args) {
        World world = args.getWorld(0)
                .valueOr(EthilVan.getDimensions().getMain().getMainWorld());
        player.teleport(world.getSpawnLocation());
        player.sendMessage("Poof !");
    }
}
