package fr.ethilvan.bukkit.snowfight;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import fr.aumgn.bukkitutils.playerref.PlayerRef;
import fr.aumgn.bukkitutils.playerref.map.PlayersRefHashMap;
import fr.aumgn.bukkitutils.playerref.map.PlayersRefMap;
import fr.ethilvan.bukkit.api.EthilVan;
import fr.ethilvan.bukkit.api.accounts.Account;
import fr.ethilvan.bukkit.snowfight.stats.SnowFightStats;

public class SnowFight {

    public class GetReady implements Runnable {

        private final PlayerRef playerRef;

        public GetReady(Player player) {
            this.playerRef = PlayerRef.get(player);
        }

        @Override
        public void run() {
            SnowFight.this.getReadyToSnowFight(playerRef);
        }
    };

    public final static int MAX_LIVES = 10;
    private final static int DEATH_DURATION = 600;

    private final Plugin plugin;
    private final SnowFightStats db;
    private final PlayersRefMap<Integer> lives;

    public SnowFight(SnowFightPlugin plugin) {
        this.plugin = plugin;
        this.db = new SnowFightStats(plugin.getDatabase());
        this.lives = new PlayersRefHashMap<Integer>();
    }

    public int getLives(OfflinePlayer player) {
        if (lives.containsKey(player)) {
            return lives.get(player);
        }

        return MAX_LIVES;
    }

    public int getLives(Account account) {
        PlayerRef player = account.getPlayerRef();
        if (lives.containsKey(player)) {
            return lives.get(player);
        }

        return MAX_LIVES;
    }

    protected boolean isDead(Player player) {
        return getLives(player) == 0;
    }

    public boolean damage(Player player, Entity damager) {
        int playerLives = getLives(player) - 1;
        if (player.getHealth() == 20) {
            player.damage(1);
            player.setHealth(20);
        } else {
            player.setHealth(player.getHealth() + 1);
            player.damage(1);
        }

        lives.put(player, playerLives);
        updateStatus(player, playerLives);
        if (playerLives == 0) {
            db.record(player, damager);
            scheduleGetReadyToSnowFight(player);
            return true;
        } else {
            return false;
        }
    }

    public void updateStatus(Player player, int playerLives) {
        Account account = EthilVan.getAccounts().get(player);
        String name = account.getName();
        if (playerLives == 0) {
            player.setPlayerListName(name + " (KO)");
        } else if (playerLives < MAX_LIVES) {
            player.setPlayerListName(name + " (" + playerLives + ")");
        }

    }

    public void scheduleGetReadyToSnowFight(Player player) {
        Bukkit.getScheduler().scheduleSyncDelayedTask(
                plugin, new GetReady(player), DEATH_DURATION);
    }

    private void getReadyToSnowFight(PlayerRef playerRef) {
        lives.put(playerRef, MAX_LIVES);
        Player player = playerRef.getPlayer();
        if (player!= null) {
            Account account = EthilVan.getAccounts().get(player);
            player.setPlayerListName(account.getColoredName());
            player.sendMessage("Vous pouvez recommencer à jouer.");
        }
    }
}
