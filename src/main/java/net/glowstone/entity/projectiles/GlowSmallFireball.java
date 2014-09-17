package net.glowstone.entity.projectiles;

import net.glowstone.entity.GlowProjectile;

import org.bukkit.Location;
import org.bukkit.entity.Egg;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.SmallFireball;
import org.bukkit.projectiles.ProjectileSource;

public class GlowSmallFireball extends GlowFireball implements SmallFireball {

    public GlowSmallFireball(Location location, ProjectileSource shooter) {
        super(location, EntityType.SMALL_FIREBALL, shooter);
    }
}
