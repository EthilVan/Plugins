package fr.ethilvan.bukkit.orator;

import java.util.Locale;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import fr.aumgn.bukkitutils.command.CommandsRegistration;
import fr.aumgn.bukkitutils.playerref.map.PlayersRefHashMap;
import fr.aumgn.bukkitutils.playerref.map.PlayersRefMap;
import fr.aumgn.bukkitutils.playerref.set.PlayersRefHashSet;
import fr.aumgn.bukkitutils.playerref.set.PlayersRefSet;
import fr.ethilvan.bukkit.api.EthilVan;

public class OratorPlugin extends JavaPlugin implements Listener {

    private PlayersRefSet orators;
    private PlayersRefMap<Location> locations;

    @Override
    public void onEnable() {
        orators = new PlayersRefHashSet(); 
        locations = new PlayersRefHashMap<Location>();
        Bukkit.getPluginManager().registerEvents(this, this);

        CommandsRegistration registration =
                new CommandsRegistration(this, Locale.FRANCE);
        registration.register(new OratorCommands(this));
    }

    @Override
    public void onDisable() {
        for (Player orator : orators.players()) {
            turnOff(orator, true);
        }
    }

    public boolean isOrator(Player player) {
        return orators.contains(player);
    }

    public void turnOn(Player player) {
        EthilVan.getAccounts().addPseudoRole(player, "orator");
        for (Entity entity : player.getWorld().getEntities()) {
            if (entity instanceof Creature) {
                Creature creature = (Creature) entity;
                if (creature.getTarget() != null
                        && creature.getTarget().equals(player)) {
                    creature.setTarget(null);
                }
            }
        }
        player.setSleepingIgnored(true);
        player.setAllowFlight(true);
        player.setFlying(true);
        orators.add(player);
        locations.put(player, player.getLocation());
    }

    public void turnOff(Player player) {
        turnOff(player, false);
    }

    public void turnOff(Player player, boolean force) {
        if (!force) {
            EthilVan.getAccounts().removePseudoRole(player, "orator");
        }
        player.setSleepingIgnored(false);
        player.setAllowFlight(false);
        player.setFlying(false);
        player.setFallDistance(0);
        player.resetPlayerTime();
        player.teleport(locations.get(player));
        orators.remove(player);
        locations.remove(player);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onQuit(PlayerQuitEvent event) {
        if (isOrator(event.getPlayer())) {
            turnOff(event.getPlayer());
        }
    }
}
