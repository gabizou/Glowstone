package net.glowstone.entity.projectiles;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.WitherSkull;
import org.bukkit.projectiles.ProjectileSource;

public class GlowWitherSkull extends GlowFireball implements WitherSkull {

    public GlowWitherSkull(Location location) {
        this(location, null);
    }

    public GlowWitherSkull(Location location, ProjectileSource shooter) {
        super(location, EntityType.WITHER_SKULL, shooter);
    }

    @Override
    public boolean isCharged() {
        // TODO Implement
        return false;
    }

    @Override
    public void setCharged(boolean charged) {
        // TODO Implement
    }
}
