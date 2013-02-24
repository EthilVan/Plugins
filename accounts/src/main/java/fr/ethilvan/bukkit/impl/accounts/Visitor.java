package fr.ethilvan.bukkit.impl.accounts;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import fr.ethilvan.bukkit.api.EthilVan;
import fr.ethilvan.bukkit.api.accounts.Role;

@Entity
@Table(name="visitors")
public class Visitor extends AbstractAccount {

    @Id
    private int visitorId;
    @Column(unique=true)
    private String name;
    @Column(name="last_visit")
    private Timestamp lastMinecraftVisit;

    public int getId() {
        return -1;
    }

    public void setId(int id) {}

    public int getVisitorId() {
        return visitorId;
    }

    public void setVisitorId(int id) {
        this.visitorId = id;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Role getRole() {
        return EthilVan.getAccounts().getRole("visitor");
    }

    @Override
    public String getMinecraftName() {
        return getName();
    }

    @Override
    public MinecraftStats getStats() {
        return null;
    }

    public Timestamp getLastMinecraftVisit() {
        return lastMinecraftVisit;
    }

    @Override
    public void setLastMinecraftVisit(Timestamp lastMinecraftVisit) {
        this.lastMinecraftVisit = lastMinecraftVisit;
    }
}
