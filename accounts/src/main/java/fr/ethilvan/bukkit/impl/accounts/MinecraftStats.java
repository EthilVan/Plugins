package fr.ethilvan.bukkit.impl.accounts;

import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

@Entity
@Table(name="minecraft_stats")
public class MinecraftStats {

    @Id
    private int id;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="account_id")
    private EVAccount account;
    @Temporal(TemporalType.TIME)
    private Timestamp lastVisit;

    private int deaths;
    private int maxLevel;

    @Version
    private int version;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public EVAccount getAccount() {
        return account;
    }

    public void setAccount(EVAccount account) {
        this.account = account;
    }

    public Timestamp getLastVisit() {
        return lastVisit;
    }

    public void setLastVisit(Timestamp lastVisit) {
        this.lastVisit = lastVisit;
    }

    public int getDeaths() {
        return deaths;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    public void updateDeath() {
        setDeaths(getDeaths() + 1);
    }

    public int getMaxLevel() {
        return maxLevel;
    }

    public void setMaxLevel(int maxLevel) {
        this.maxLevel = maxLevel;
    }

    public void updateMaxLevel(int maxLevel) {
        if (getMaxLevel() < maxLevel) {
            setMaxLevel(maxLevel);
        }
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
