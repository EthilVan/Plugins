package fr.ethilvan.bukkit.accounts;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.entity.Player;

import fr.aumgn.bukkitutils.playerref.PlayerRef;
import fr.ethilvan.bukkit.accounts.db.EVAccount;
import fr.ethilvan.bukkit.accounts.db.Visitor;
import fr.ethilvan.bukkit.api.EthilVan;
import fr.ethilvan.bukkit.api.accounts.Account;
import fr.ethilvan.bukkit.api.accounts.Accounts;

public class EVAccounts implements Accounts {

        private AccountsPlugin plugin;
        private Map<String, Set<String>> pseudoRoles;

        public EVAccounts(AccountsPlugin plugin) {
            this.plugin = plugin;
            pseudoRoles = new HashMap<String, Set<String>>();
        }

        private Account getVisitorByName(String name) {
            Account account = plugin.getDatabase().find(Visitor.class)
                    .where().eq("name", name).findUnique();
            return account;
        }

        @Override
        public Account getByName(String name) {
            Account account = plugin.getDatabase().find(EVAccount.class)
                    .where().eq("name", name).findUnique();
            if (account != null) {
                return account;
            }
            return getVisitorByName(name);
        }

        @Override
        public Account get(PlayerRef playerRef) {
            return getByMinecraftName(playerRef.getName());
        }

        @Override
        public Account get(Player player) {
            return getByMinecraftName(player.getName());
        }

        @Override
        public Account getByMinecraftName(String name) {
            Account account = plugin.getDatabase().find(EVAccount.class)
                    .where().eq("minecraft_name", name).findUnique();
            if (account != null) {
                return account;
            }
            return getVisitorByName(name);
        }

        @Override
        public List<Account> getByPartialName(String name) {
            List<Account> accounts = new ArrayList<Account>();
            List<EVAccount> evAccounts = plugin.getDatabase()
                    .find(EVAccount.class).where().like("name", name+"%")
                    .findList();
            List<Visitor> visitors = plugin.getDatabase().find(Visitor.class)
                    .where().like("name", name+"%").findList();
            for (EVAccount evAccount : evAccounts) {
                accounts.add(evAccount);
            }
            for (Visitor visitor : visitors) {
                accounts.add(visitor);
            }
            return accounts;
        }

        @Override
        public Set<String> getPseudoRoles(Player player) {
            if (!pseudoRoles.containsKey(player.getName())) {
                pseudoRoles.put(player.getName(), new HashSet<String>());
            }
            return pseudoRoles.get(player.getName());
        }

        @Override
        public void addPseudoRole(Player player, String pseudorole) {
            getPseudoRoles(player).add(pseudorole);
            EthilVan.getPermissions().update(player);
        }

        @Override
        public void removePseudoRole(Player player, String pseudorole) {
            getPseudoRoles(player).remove(pseudorole);
            EthilVan.getPermissions().update(player);
        }

        @Override
        public boolean isVisitor(Player player) {
            int rowCount = plugin.getDatabase().find(Visitor.class)
                    .where().eq("name", player.getName()).findRowCount();
            return rowCount > 0;
        }

        @Override
        public List<Visitor> getVisitors() {
            return plugin.getDatabase().find(Visitor.class).findList();
        }
    }