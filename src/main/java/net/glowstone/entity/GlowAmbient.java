package net.glowstone.entity;

import org.bukkit.Location;
import org.bukkit.entity.Ambient;

/**
 * Represents an ambient mob, such as a Bat.
 */
public abstract class GlowAmbient extends GlowLivingEntity implements Ambient {

    public GlowAmbient(Location location) {
        super(location);
    }
}
