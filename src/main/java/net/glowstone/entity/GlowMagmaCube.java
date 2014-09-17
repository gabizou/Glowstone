package net.glowstone.entity;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.MagmaCube;

public class GlowMagmaCube extends GlowSlime implements MagmaCube {

    /**
     * Creates a new ageable animal.
     * @param location The location of the animal.
     */
    public GlowMagmaCube(Location location) {
        super(location, EntityType.MAGMA_CUBE);
    }
}
