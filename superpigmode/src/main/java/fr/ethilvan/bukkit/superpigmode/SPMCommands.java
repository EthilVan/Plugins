package fr.ethilvan.bukkit.superpigmode;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.aumgn.bukkitutils.command.Command;
import fr.aumgn.bukkitutils.command.Commands;
import fr.aumgn.bukkitutils.command.NestedCommands;
import fr.aumgn.bukkitutils.command.args.CommandArgs;
import fr.aumgn.bukkitutils.command.exception.CommandError;
import fr.ethilvan.bukkit.api.EthilVan;
import fr.ethilvan.bukkit.api.inventories.Inventories;

@NestedCommands(value = "super-pig-mode", defaultTo = "toggle")
public class SPMCommands implements Commands {

    private final SuperPigMode spm;

    public SPMCommands(SuperPigMode spm) {
        this.spm = spm;
    }

    @Command(name = "toggle", flags = "v")
    public void toggle(Player player, CommandArgs args) {
        if (spm.toggleSPM(player)) {
            player.sendMessage("Super Pig Mode Activé");
            if (args.hasFlag('v')) {
                spm.toggleVanish(player);
            }
        } else {
            player.sendMessage("Super Pig Mode Desactivé");
        }
    }

    @Command(name = "vanish")
    public void vanish(Player player, CommandArgs args) {
        if (!spm.isInSPM(player)) {
            throw new CommandError(
                    "Vous devez etre en Super Pig Mode pour passer en Vanish");
        }

        if (spm.toggleVanish(player)) {
            player.sendMessage("Vanish Activé");
        } else {
            player.sendMessage("Vanish Desactivé");
        }
    }

    @Command(name = "save-inventory")
    public void saveInventory(Player player, CommandArgs args) {
        if (!spm.isInSPM(player)) {
            throw new CommandError(
                    "Commande Utilisable seulement en Super Pig Mode.");
        }

        Inventories inventories = EthilVan.getInventories();
        inventories.save(player, "spm." + player.getName());
        player.sendMessage(ChatColor.RED + "Inventaire sauvegardé.");
    }

    @Command(name = "list")
    public void list(CommandSender sender, CommandArgs args) {
        sender.sendMessage(ChatColor.GREEN + "~~~~~ Super Pigs : ~~~~~");
        for (Player player : spm.getSuperPigs()) {
            sender.sendMessage(ChatColor.YELLOW
                    + " - " + player.getDisplayName());
        }
    }
}
