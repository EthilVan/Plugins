package fr.ethilvan.bukkit.dimensions.listener;

import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerExpChangeEvent;

import fr.ethilvan.bukkit.api.EthilVan;
import fr.ethilvan.bukkit.api.dimensions.Dimension;

public class DimensionListener implements Listener {

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onExpChange(PlayerExpChangeEvent event) {
        World world = event.getPlayer().getWorld();

        Dimension dimension = EthilVan.getDimensions().get(world);
        if (dimension.getConfig().hasFlag("no-experience")) {
            event.setAmount(0);
        }
    }
}
