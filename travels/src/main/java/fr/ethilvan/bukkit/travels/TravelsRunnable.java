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

            Port departurePort = plugin.getPorts().get(travel.getDeparture());
            World world = departurePort.getWorld();

            if (travel.getSchedule() == -1) {
                teleport(travel, world, departurePort);
                continue;
            }

            long worldTime = world.getTime();
            int delay = plugin.getTravelsConfig().getCheckDelay() / 2;
            int minAllowedTime = travel.getSchedule() - delay;
            int maxAllowedTime = travel.getSchedule() + delay;

            if (worldTime < minAllowedTime || worldTime > maxAllowedTime) {
                continue;
            }

            teleport(travel, world, departurePort);
        }
    }

    private void teleport(Travel travel, World world, Port departurePort) {
        for (Player player : world.getPlayers()) {
            if (!departurePort.departureContains(player)) {
                continue;
            }

            if (!player.hasPermission("travels.use")) {
                player.sendMessage(plugin.getMessages()
                        .get("runnable.hasNotPermission"));
                continue;
            }

            Economy eco = EthilVan.getEconomy();
            double price =  travel.getPrice();

            if (player.hasPermission("travels.use.free")) {
                price = 0;
            }

            if (!eco.has(player.getName(), price)) {
                player.sendMessage(plugin.getMessages()
                        .get("runnable.hasNotEnoughMoney"));
                continue;
            }

            Port destinationPort = plugin.getPorts().get(travel.getDestination());
            player.teleport(destinationPort.getDestination());
            int travelprice = (int) (price * 100);
            if (price != 0) {
                player.sendMessage(plugin.getMessages()
                        .get("runnable.thanksForUse_1"));
            } else {
                player.sendMessage(plugin.getMessages()
                        .get("runnable.thanksForUse_2", travelprice));
            }

            if (price == 0) {
                continue;
            }

            eco.withdrawPlayer(player.getName(), price);
        }
    }
}