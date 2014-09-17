package net.glowstone.entity.animals;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.MushroomCow;


public class GlowMushroomCow extends GlowCow implements MushroomCow {

    /**
     * Creates a new Cow.
     *
     * @param location The location of the monster.
     */
    public GlowMushroomCow(Location location) {
        super(location, EntityType.MUSHROOM_COW);
        setSize(0.9F, 1.3F);
    }
}
