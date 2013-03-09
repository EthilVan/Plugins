package fr.ethilvan.bukkit.misc;

import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Enderman;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.inventory.FurnaceExtractEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import fr.aumgn.bukkitutils.util.Util;
import fr.ethilvan.bukkit.api.EthilVan;

public class EVMiscListener implements Listener {

    private boolean isSpade(Material material) {
        return material == Material.WOOD_SPADE
                || material == Material.STONE_SPADE
                || material == Material.IRON_SPADE
                || material == Material.GOLD_SPADE
                || material == Material.DIAMOND_SPADE;
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        if (player.getGameMode() == GameMode.CREATIVE) {
            return;
        }

        if (EthilVan.getAccounts().isVisitor(player)) {
            return;
        }

        Block block = event.getBlock(); 
        if (block.getType() == Material.SNOW) {
            Material material = player.getItemInHand().getType();
            ItemStack stack = new ItemStack(Material.SNOW_BALL);
            if (isSpade(material)) {
                stack.setAmount(2);
            } else {
                stack.setAmount(1);
            }

            block.getWorld().dropItemNaturally(block.getLocation(), stack);
        }
    }

    @EventHandler
    public void onEntityChangeBlock(EntityChangeBlockEvent event) {
        if (event.getEntity() instanceof Enderman) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEntityExplode(EntityExplodeEvent event) {
        if (event.getEntity() instanceof Creeper) {
            event.setYield(1.0f);
        }
    }

    @EventHandler
    public void onPlayerInteractEvent(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }

        Block block = event.getClickedBlock();
        if (block.getType() == Material.SAND) {
            onSandRightClick(event);
            return;
        }

        if (block.getType() == Material.ENDER_CHEST) {
            onEnderChestRightClick(event);
            return;
        }
    }

    private void onSandRightClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();
    	ItemStack item = player.getItemInHand();
    	Block block = event.getClickedBlock();
    	boolean chance = Util.getRandom().nextInt() < 0.5;

    	if (item.getType() != Material.INK_SACK
    			|| item.getDurability() != (short) 15) {
    		return;
    	}

    	Block target = block.getRelative(BlockFace.UP);
    	if (!target.isEmpty()) {
    		return;
    	}

    	int amount = player.getItemInHand().getAmount();
    	if (amount == 1) {
    		player.setItemInHand(new ItemStack(0));
    	} else {
    		player.getItemInHand().setAmount(amount - 1);
    	}

    	if(!chance) {
    		return;
    	}

    	target.setTypeIdAndData(32, (byte) 0, false);
    }

    private void onEnderChestRightClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (player.getGameMode() == GameMode.CREATIVE
                && !EthilVan.getAccounts().getPseudoRoles(player)
                        .contains("spm")) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerInteractWithVillager(InventoryOpenEvent event) {
        if (!(event.getView().getType() == InventoryType.MERCHANT)) return;
        event.setCancelled(true);
    }

    @EventHandler
    public void onZombieBreakDoor(EntityChangeBlockEvent event) {
        if (!(event.getBlock().getType() == Material.WOODEN_DOOR)) return;
        if (!(event.getEntity() instanceof Zombie)) return;
        event.setCancelled(true);
    }

    @EventHandler
    public void blockDropXpEvent(BlockBreakEvent event) {
    	event.setExpToDrop(0);
    }

    @EventHandler
    public void furnaceExtrackEvent(FurnaceExtractEvent event) {
        event.setExpToDrop(0);
    }

    @EventHandler
    public void dragonDeath(EntityDeathEvent event) {

        if (event.getEntityType() != EntityType.ENDER_DRAGON) {
            return;
        }

        World dragonWorld = event.getEntity().getWorld();

        if (event.getEntity().getWorld().getPlayers().size() == 0 ) {
            return;
        }

        int expByPlayer = event.getDroppedExp() / 
                dragonWorld.getPlayers().size();

        for (Player killer : dragonWorld.getPlayers()) {
            killer.giveExp(expByPlayer);  
        }

        event.setDroppedExp(0);
    }

    @EventHandler
    public void bookshelfBreak(BlockBreakEvent event) {
        Block block = event.getBlock();

        if (block.getType() != Material.BOOKSHELF) {
            return;
        }

        if (!isAxe(event.getPlayer().getItemInHand().getType())) {
            return;
        }

        simulateBlockMine(event, new ItemStack(Material.BOOKSHELF));
    }

    @EventHandler
    public void spongeBreak(BlockBreakEvent event) {
        Block block = event.getBlock();

        if (block.getType() != Material.SPONGE) {
            return;
        }

        simulateBlockMineWithoutDrop(event);
    }

    @EventHandler
    public void webBreak(BlockBreakEvent event) {
        Block block = event.getBlock();

        if (!(block.getType() == Material.WEB)) {
            return;
        }

        if (event.getPlayer().getItemInHand().getType() != Material.SHEARS) {
            return;
        }

        simulateBlockMine(event, new ItemStack(Material.WEB));
    }

    private boolean isAxe(Material mat) {
        return mat == Material.WOOD_AXE || mat == Material.IRON_AXE 
                || mat == Material.STONE_AXE || mat == Material.GOLD_AXE 
                || mat == Material.DIAMOND_AXE;
    }

    private void simulateBlockMine(BlockBreakEvent event, ItemStack drop) {
        Block block = event.getBlock();
        int lastType = event.getBlock().getTypeId();
        event.setCancelled(true);
        block.setType(Material.AIR);

        event.getPlayer().sendBlockChange(block.getLocation(), block.getType(),
                block.getData());
        block.getWorld().dropItemNaturally(block.getLocation(), drop);

        for (Entity entity : event.getPlayer().getNearbyEntities(64, 64, 64)) {
            if (entity instanceof Player) {
                Player nearbyPlayer = (Player) entity;
                nearbyPlayer.playEffect(block.getLocation(), Effect.STEP_SOUND,
                        lastType);
            }
        }
    }

    private void simulateBlockMineWithoutDrop(BlockBreakEvent event) {
        Block block = event.getBlock();
        int lastType = event.getBlock().getTypeId();
        event.setCancelled(true);
        block.setType(Material.AIR);

        event.getPlayer().sendBlockChange(block.getLocation(), block.getType(),
                block.getData());

        for (Entity entity : event.getPlayer().getNearbyEntities(64, 64, 64)) {
            if (entity instanceof Player) {
                Player nearbyPlayer = (Player) entity;
                nearbyPlayer.playEffect(block.getLocation(), Effect.STEP_SOUND,
                        lastType);
            }
        }
    }
}
