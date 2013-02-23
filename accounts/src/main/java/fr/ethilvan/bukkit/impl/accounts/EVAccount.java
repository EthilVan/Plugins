package fr.ethilvan.bukkit.impl.accounts;

import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.bukkit.ChatColor;

import fr.aumgn.bukkitutils.playerref.PlayerRef;
import fr.ethilvan.bukkit.api.EthilVan;
import fr.ethilvan.bukkit.api.accounts.Account;
import fr.ethilvan.bukkit.api.accounts.Role;

@Entity
@Table(name="accounts")
public class EVAccount implements Account {

    @Id
    private int id;
    @Column(unique=true)
    private String name;
    private String roleId;
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

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    @Override
    public Role getRole() {
        return EthilVan.getAccounts().getRole(roleId);
    }

    @Override
    public String getColoredNamePlate() {
        return getRole().getColor() + getName();
    }

    @Override
    public String getColoredName() {
        return getColoredNamePlate() + ChatColor.WHITE;
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
