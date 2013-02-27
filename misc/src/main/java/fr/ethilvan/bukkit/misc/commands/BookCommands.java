package fr.ethilvan.bukkit.misc.commands;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import fr.aumgn.bukkitutils.command.Command;
import fr.aumgn.bukkitutils.command.NestedCommands;
import fr.aumgn.bukkitutils.command.args.CommandArgs;
import fr.aumgn.bukkitutils.command.exception.CommandError;

@NestedCommands({ "evmisc", "book" })
public class BookCommands extends MiscCommands {

    @Command(name="author", min = 0, max = 1)
    public void setAuthor(Player sender, CommandArgs args) {
        ItemStack stack = sender.getItemInHand();

        if (stack.getType() != Material.WRITTEN_BOOK) {
            throw new CommandError("Vous devez tenir un livre écrit");
        }

        BookMeta meta = (BookMeta) stack.getItemMeta();

        if (args.length() == 0) {
            sender.sendMessage(ChatColor.GREEN + "L'auteur de ce livre est "
                    + meta.getAuthor());
            return;
        }

        String author = args.get(0);
        meta.setAuthor(author);
        stack.setItemMeta(meta);
        sender.sendMessage(ChatColor.GREEN + "Auteur redéfini à " + author + ".");
    }

    @Command(name="pages", min = 1, max = -1)
    public void setPages(Player sender, CommandArgs args) {
        ItemStack stack = sender.getItemInHand();

        if (stack.getType() != Material.WRITTEN_BOOK) {
            throw new CommandError("Vous devez tenir un livre écrit");
        }

        BookMeta meta = (BookMeta) stack.getItemMeta();

        List<String> pages = args.asList();
        meta.setPages(pages);
        stack.setItemMeta(meta);
        sender.sendMessage(ChatColor.GREEN + "Pages redéfinies.");
    }

    @Command(name="page", min = 2, max = 2)
    public void setPage(Player sender, CommandArgs args) {
        ItemStack stack = sender.getItemInHand();

        if (stack.getType() != Material.WRITTEN_BOOK) {
            throw new CommandError("Vous devez tenir un livre écrit");
        }

        BookMeta meta = (BookMeta) stack.getItemMeta();

        int page = args.getInteger(0).value();
        String data = args.get(1);
        meta.setPage(page, data);
        sender.sendMessage(ChatColor.GREEN + "Page " + page + " redéfinie");
        stack.setItemMeta(meta);
    }

    @Command(name="title", min = 0, max = 1)
    public void setTitle(Player sender, CommandArgs args) {
        ItemStack stack = sender.getItemInHand();

        if (stack.getType() != Material.WRITTEN_BOOK) {
            throw new CommandError("Item must be a Written Book.");
        }

        BookMeta meta = (BookMeta) stack.getItemMeta();

        if (args.length() == 0) {
            sender.sendMessage(ChatColor.GREEN + "Le titre de ce livre est "
                    + meta.getTitle());
            return;
        }

        String title = args.get(0);
        meta.setTitle(title);
        stack.setItemMeta(meta);
        sender.sendMessage(ChatColor.GREEN + "Titre du livre redéfini à " +
                title);
    }
}