package fr.ethilvan.bukkit.deaths.death;

import org.bukkit.entity.Blaze;
import org.bukkit.entity.CaveSpider;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Enderman;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Ghast;
import org.bukkit.entity.Giant;
import org.bukkit.entity.MagmaCube;
import org.bukkit.entity.PigZombie;
import org.bukkit.entity.Silverfish;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Slime;
import org.bukkit.entity.Spider;
import org.bukkit.entity.Wolf;
import org.bukkit.entity.Zombie;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerKilledByMob extends PlayerKilled {

    public enum Type {
        Creeper,
        Zombie,
        Squelette,
        AraigneeBleue,
        Araignee,
        Loup,
        Slime,
        ZombieCochon,
        Ghast,
        Geant,
        Enderman,
        Silverfish,
        Blaze,
        MagmaCube,
        EnderDragon,
        Inconnu
    }

    private Type type;

    public PlayerKilledByMob(PlayerDeathEvent event, Entity damager) {
        super(event);
        type = guessType(damager);
    }

    private Type guessType(Entity mob) {
        if (mob instanceof Creeper)     { return Type.Creeper; }
        if (mob instanceof PigZombie)   { return Type.ZombieCochon; }
        if (mob instanceof Zombie)      { return Type.Zombie; }
        if (mob instanceof Skeleton)    { return Type.Squelette; }
        if (mob instanceof CaveSpider)  { return Type.AraigneeBleue; }
        if (mob instanceof Spider)      { return Type.Araignee; }
        if (mob instanceof Wolf)        { return Type.Loup; }
        if (mob instanceof MagmaCube)   { return Type.MagmaCube; }
        if (mob instanceof Slime)       { return Type.Slime; }
        if (mob instanceof Ghast)       { return Type.Ghast; }
        if (mob instanceof Giant)       { return Type.Geant; }
        if (mob instanceof Enderman)    { return Type.Enderman; }
        if (mob instanceof Silverfish)  { return Type.Silverfish; }
        if (mob instanceof Blaze)       { return Type.Blaze; }
        if (mob instanceof EnderDragon) { return Type.EnderDragon; }
        return Type.Inconnu;
    }

    @Override
    protected PlayerDeathCause guessCause(PlayerDeathEvent event) {
        return PlayerDeathCause.Mob;
    }

    @Override
    public String getConfigPath() {
        return cause.name() + "." + type.name();
    }
}
