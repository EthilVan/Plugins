package fr.ethilvan.bukkit.snowfight.stats;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="snowfight_deaths")
public class SnowFightDeath {

    public static SnowFightDeath create(String victim, String shooter) {
        SnowFightDeath death = new SnowFightDeath();
        death.setVictim(victim);
        death.setShooter(shooter);
        return death;
    }

    @Id
    private int id;
    private String victim;
    private String shooter;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVictim() {
        return victim;
    }

    public void setVictim(String victim) {
        this.victim = victim;
    }

    public String getShooter() {
        return shooter;
    }

    public void setShooter(String shooter) {
        this.shooter = shooter;
    }
}
