package fr.ethilvan.bukkit.accounts.db;

import java.sql.Timestamp;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.bukkit.ChatColor;

import fr.aumgn.bukkitutils.playerref.PlayerRef;
import fr.ethilvan.bukkit.accounts.EVAccounts;
import fr.ethilvan.bukkit.api.accounts.Account;

@Entity
@Table(name="accounts")
public class EVAccount implements Account {

    private static Map<String, String[]>  rolesInheritance;
    private static Map<String, ChatColor> nameColors;

    public static void initConfig(EVAccounts plugin) {
        /*AccountsConfig config = new AccountsConfig(plugin);
        rolesInheritance = config.getRolesInheritance();
        nameColors = config.getRolesColors();*/
    }

    @Id
    private int id;
    @Column(unique=true)
    private String name;
    private String role;
    private String email;
    private String cryptedPassword;
    @Column(unique=true)
    private String minecraftName;
    private Timestamp lastVisit;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "account")
    private MinecraftStats stats;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public ChatColor getColor() {
        return nameColors.get(getRole());
    }

    @Override
    public String getColoredNamePlate() {
        return getColor() + getName();
    }

    @Override
    public String getColoredName() {
        return getColoredNamePlate() + ChatColor.WHITE;
    }

    @Override
    public String[] getRoles() {
        String role = getRole();
        return rolesInheritance.get(role);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCryptedPassword() {
        return cryptedPassword;
    }

    public void setCryptedPassword(String cryptedPassword) {
        this.cryptedPassword = cryptedPassword;
    }

    @Override
    public String getMinecraftName() {
        return minecraftName;
    }

    public void setMinecraftName(String minecraftName) {
        this.minecraftName = minecraftName;
    }

    public Timestamp getLastVisit() {
        return lastVisit;
    }

    public void setLastVisit(Timestamp lastVisit) {
        this.lastVisit = lastVisit;
    }

    public MinecraftStats getStats() {
        return stats;
    }

    public void setStats(MinecraftStats stats) {
        this.stats = stats;
    }

    @Override
    public Timestamp getLastMinecraftVisit() {
        return stats.getLastVisit();
    }

    @Override
    public void setLastMinecraftVisit(Timestamp lastVisit) {
        stats.setLastVisit(lastVisit);
    }

    @Override
    public PlayerRef getPlayerRef() {
        return PlayerRef.get(minecraftName);
    }
}