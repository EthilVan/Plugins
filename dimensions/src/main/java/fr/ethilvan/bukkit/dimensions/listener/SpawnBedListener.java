package fr.ethilvan.bukkit.dimensions.listener;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerBedLeaveEvent;

import fr.ethilvan.bukkit.api.EthilVan;
import fr.ethilvan.bukkit.api.dimensions.Dimension;
import fr.ethilvan.bukkit.api.event.dimensions.DimensionRespawnEvent;
import fr.ethilvan.bukkit.dimensions.DimensionsPlugin;
import fr.ethilvan.bukkit.dimensions.spawnbed.SpawnBed;

public class SpawnBedListener implements Listener {

    private final DimensionsPlugin plugin;

    public SpawnBedListener(DimensionsPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onPlayerBedEnter(PlayerBedEnterEvent event) {
        Player player = event.getPlayer();
        Dimension dimension = EthilVan.getDimensions().get(player);
        if (player.getItemInHand().getType() == Material.TORCH) {
            plugin.setSpawnBed(player, dimension, event.getBed(),
                    player.getLocation());
            player.sendMessage(ChatColor.GREEN + "Spawn mis a jour.");
        }

        dimension.send(player.getDisplayName()
                + ChatColor.YELLOW + " s'est couché.");
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerBedLeave(PlayerBedLeaveEvent event) {
        Player player = event.getPlayer();
        if (player.getWorld().getTime() == 0) {
            return;
        }

        Dimension dimension = EthilVan.getDimensions().get(player);
        dimension.send(player.getDisplayName() + ChatColor.YELLOW
                + " s'est relevé.");
    }

    @EventHandler
    public void onDimensionRespawn(DimensionRespawnEvent event) {
        SpawnBed bed = plugin.getSpawnBed(event.getPlayer(),
                event.getDimension());

        if (bed == null) {
            return;
        }

        Block block = bed.getBedBlock();
        if (block == null || block.getType() != Material.BED_BLOCK) {
            return;
        }

        event.setRespawnLocation(bed.getRespawn());
    }
}
