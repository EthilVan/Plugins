package fr.ethilvan.bukkit.accounts;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import fr.aumgn.bukkitutils.command.CommandsRegistration;
import fr.aumgn.bukkitutils.gson.GsonLoadException;
import fr.aumgn.bukkitutils.gson.GsonLoader;
import fr.ethilvan.bukkit.accounts.listeners.AccountsListener;
import fr.ethilvan.bukkit.accounts.listeners.NamePlatePacketListener;
import fr.ethilvan.bukkit.api.EthilVan;
import fr.ethilvan.bukkit.api.accounts.Accounts;
import fr.ethilvan.bukkit.impl.accounts.AbstractAccount;
import fr.ethilvan.bukkit.impl.accounts.EVAccount;
import fr.ethilvan.bukkit.impl.accounts.EVAccounts;
import fr.ethilvan.bukkit.impl.accounts.MinecraftStats;
import fr.ethilvan.bukkit.impl.accounts.Visitor;

public class AccountsPlugin extends JavaPlugin implements Runnable {

    @Override
    public List<Class<?>> getDatabaseClasses() {
        List<Class<?>> list = new ArrayList<Class<?>>();
        list.add(MinecraftStats.class);
        list.add(EVAccount.class);
        list.add(Visitor.class);
        return list;
    }

    @Override
    public void onEnable() {
        PluginManager pm = Bukkit.getPluginManager();

        AccountsConfig config;
        try {
            config = getLoader().loadOrCreate("config.json",
                    AccountsConfig.class);
        } catch (GsonLoadException exc) {
            getLogger().log(Level.SEVERE, "Unable to load config.json", exc);
            config = new AccountsConfig();
        }

        Accounts accounts = new EVAccounts(this, config);
        EthilVan.registerAccounts(accounts);

        Listener accountsListener = new AccountsListener(this);
        pm.registerEvents(accountsListener, this);

        ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();
        protocolManager.addPacketListener(new NamePlatePacketListener(this));

        CommandsRegistration registration =
                new CommandsRegistration(this, Locale.FRANCE);
        registration.register(new AccountsCommands());

        Bukkit.getScheduler().runTaskTimer(this, this, 6000, 6000);
    }

    private GsonLoader getLoader() {
        Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .create();
        return new GsonLoader(gson, this);
    }

    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            updateStats(player);
        }
    }

    private Timestamp currentTime() {
        return new Timestamp(System.currentTimeMillis());
    }

    public void updateStats(Player player) {
        updateStats(player, false);
    }

    public void updateStats(Player player, boolean death) {
        AbstractAccount account = (AbstractAccount) EthilVan.getAccounts()
                .get(player);
        account.setLastMinecraftVisit(currentTime());

        MinecraftStats stats = account.getStats();
        if (stats == null) {
            return;
        }

        stats.updateMaxLevel(player.getLevel());
        if (death) {
            stats.updateDeath();
        }
        getDatabase().save(stats);
    }
}
