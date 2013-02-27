package fr.ethilvan.bukkit.travels;

import net.milkbowl.vault.economy.Economy;

import org.bukkit.World;
import org.bukkit.entity.Player;

import fr.ethilvan.bukkit.api.EthilVan;
import fr.ethilvan.bukkit.travels.travels.Port;
import fr.ethilvan.bukkit.travels.travels.Travel;

public class TravelsRunnable implements Runnable {

    private TravelsPlugin plugin;

    public TravelsRunnable(TravelsPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        for (Travel travel : plugin.getTravels()) {
            if (!travel.isActive()) {
                continue;
            }

            Port departure = plugin.getPorts().get(travel.getDeparture());
            if (departure == null) {
                continue;
            }

            World world = departure.getWorld();
            if (!checkSchedule(travel, world.getTime())) {
                continue;
            }

            performTravel(travel, world, departure);
        }
    }

    private boolean checkSchedule(Travel travel, long time) {
        if (!travel.hasSchedule()) {
            return true;
        }

        int delay = plugin.getTravelsConfig().getCheckDelay() / 2;
        int intervalMin = travel.getSchedule() - delay;
        int intervalMax = travel.getSchedule() + delay;

        return time > intervalMin && time < intervalMax;
    }

    private void performTravel(Travel travel, World world, Port departure) {
        Port destination = plugin.getPorts().get(travel.getDestination());
        if (destination == null) {
            return;
        }

        Economy economy = EthilVan.getEconomy();
        double price =  travel.getPrice();
        int displayPrice = (int) (price * 100);

        for (Player player : world.getPlayers()) {
            if (!departure.contains(player)) {
                continue;
            }

            if (!player.hasPermission("travels.use")) {
                player.sendMessage(msg("runnable.hasNotPermission"));
                continue;
            }

            boolean free = price == 0 ||
                    player.hasPermission("travels.use.free");
            if (!free && !economy.has(player.getName(), price)) {
                player.sendMessage(msg("runnable.hasNotEnoughMoney"));
                continue;
            }

            player.teleport(destination.getPosition());

            if (free) {
                player.sendMessage(msg("runnable.thanksForUse_2"));
            } else {
                economy.withdrawPlayer(player.getName(), price);
                player.sendMessage(msg("runnable.thanksForUse_1",
                        displayPrice));
            }
        }
    }

    private String msg(String key) {
        return plugin.getMessages().get(key);
    }

    private String msg(String key, Object... arguments) {
        return plugin.getMessages().get(key, arguments);
    }
}