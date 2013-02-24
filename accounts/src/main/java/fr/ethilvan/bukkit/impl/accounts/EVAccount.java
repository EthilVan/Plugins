package fr.ethilvan.bukkit.impl.accounts;

import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import fr.ethilvan.bukkit.api.EthilVan;
import fr.ethilvan.bukkit.api.accounts.Role;

@Entity
@Table(name="accounts")
public class EVAccount extends AbstractAccount {

    @Id
    private int id;
    @Column(unique=true)
    private String name;
    private String roleId;
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

    @Override
    public MinecraftStats getStats() {
        return stats;
    }

    public void setStats(MinecraftStats stats) {
        this.stats = stats;
    }

    public Timestamp getLastMinecraftVisit() {
        return stats.getLastVisit();
    }

    @Override
    public void setLastMinecraftVisit(Timestamp lastVisit) {
        stats.setLastVisit(lastVisit);
    }
}
