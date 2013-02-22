package fr.ethilvan.bukkit.impl.accounts;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import fr.aumgn.bukkitutils.playerref.PlayerRef;
import fr.ethilvan.bukkit.api.accounts.Account;

@Entity
@Table(name="visitors")
public class Visitor implements Account {

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
    public String getRoleId() {
        return "visitor";
    }

    @Override
    public String getColoredNamePlate() {
        return getName();
    }

    @Override
    public String getColoredName() {
        return getName();
    }

    @Override
    public String getMinecraftName() {
        return getName();
    }

    @Override
    public String[] getRoles() {
        String[] roles = new String[1];
        roles[0] = getRoleId();
        return roles;
    }

    @Override
    public Timestamp getLastMinecraftVisit() {
        return lastMinecraftVisit;
    }

    @Override
    public void setLastMinecraftVisit(Timestamp lastMinecraftVisit) {
        this.lastMinecraftVisit = lastMinecraftVisit;
    }

    @Override
    public PlayerRef getPlayerRef() {
        return PlayerRef.get(name);
    }
}
