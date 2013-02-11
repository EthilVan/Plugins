package fr.ethilvan.bukkit.misc;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.plugin.Plugin;

import uk.co.oliwali.HawkEye.util.HawkEyeAPI;

public class HawkEyeExtension implements Listener {

    private final MiscPlugin plugin;
    private final Map<Integer, String> creepersTargets;

    public HawkEyeExtension(MiscPlugin plugin) {
        this.plugin = plugin;
        this.creepersTargets = new HashMap<Integer, String>();
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onTarget(EntityTargetEvent event) {
        if (event.getEntityType() != EntityType.CREEPER) {
            return;
        }

        if (!(event.getTarget() instanceof Player)) {
            return;
        }

        int id = event.getEntity().getEntityId();
        String targetName = ((Player) event.getTarget()).getName();
        creepersTargets.put(id, targetName);
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onExplode(EntityExplodeEvent event) {
        if (event.getEntityType() != EntityType.CREEPER) {
            return;
        }

        Creeper creeper = (Creeper) event.getEntity();
        String target = creepersTargets.get(creeper.getEntityId());
        if (target == null) {
            return;
        }

        Plugin hawkEye = Bukkit.getPluginManager().getPlugin("HawkEye");
        if (hawkEye == null || !hawkEye.isEnabled()) {
            return;
        }

        HawkEyeAPI.addCustomEntry(plugin, "creeper", target, creeper.getLocation(), "");
    }
}
