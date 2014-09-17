package net.glowstone.entity.animals;

import net.glowstone.entity.GlowAnimal;

import org.bukkit.Location;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.EntityType;

public class GlowChicken extends GlowAnimal implements Chicken {

    /**
     * Creates a new Chicken.
     *
     * @param location The location of the monster.
     */
    public GlowChicken(Location location) {
        super(location, EntityType.CHICKEN);
        setSize(0.3F, 0.7F);
    }
}
