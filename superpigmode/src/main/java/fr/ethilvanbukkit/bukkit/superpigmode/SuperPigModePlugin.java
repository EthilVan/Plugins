package fr.ethilvanbukkit.bukkit.superpigmode;

import java.util.Locale;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import fr.aumgn.bukkitutils.command.CommandsRegistration;
import fr.ethilvanbukkit.bukkit.superpigmode.SuperPigMode.TurnOffReason;

public class SuperPigModePlugin extends JavaPlugin {

    private final SuperPigMode spm = new SuperPigMode();

    @Override
    public void onEnable() {
        SPMListener listener = new SPMListener(spm);
        Bukkit.getPluginManager().registerEvents(listener, this);

        CommandsRegistration registration =
                new CommandsRegistration(this, Locale.FRANCE);
        registration.register(new SPMCommands(spm));
    }

    @Override
    public void onDisable() {
        for (Player player : spm.getSuperPigs()) {
            spm.turnOff(player, TurnOffReason.Stop);
        }
    }
}