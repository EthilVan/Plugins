package fr.ethilvan.bukkit.deaths.death;

import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

public enum PlayerDeathCause {

    Noyade        (DamageCause.DROWNING),
    Chute         (DamageCause.FALL),
    Vide          (DamageCause.VOID),
    Suffocation   (DamageCause.SUFFOCATION),
    Faim          (DamageCause.STARVATION),
    Suicide       (DamageCause.SUICIDE),

    Cactus        (DamageCause.CONTACT),
    Feu           (DamageCause.FIRE),
    Combustion    (DamageCause.FIRE_TICK),
    Lave          (DamageCause.LAVA),
    Explosion     (DamageCause.BLOCK_EXPLOSION, DamageCause.ENTITY_EXPLOSION),
    Eclair        (DamageCause.LIGHTNING),
    Poison        (DamageCause.POISON),
    Magie         (DamageCause.MAGIC),

    Dispenser,
    Mob,
    Joueur,

    Indeterminee;

    private DamageCause[] bukkitCauses;

    private PlayerDeathCause() {
        this(new DamageCause[0]);
    }

    private PlayerDeathCause(DamageCause... cause) {
        this.bukkitCauses = cause;
    }

    public static PlayerDeathCause get(DamageCause bukkitCause) {
        for (PlayerDeathCause cause : values()) {
            if (cause.bukkitCauses == null) {
                continue;
            }

            for (DamageCause associatedBukkitCause : cause.bukkitCauses) {
                if (associatedBukkitCause == bukkitCause) {
                    return cause;
                }
            }
        }

        return null;
    }
}
