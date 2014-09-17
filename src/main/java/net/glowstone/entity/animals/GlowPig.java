package net.glowstone.entity.animals;

import net.glowstone.entity.GlowAnimal;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Pig;

public class GlowPig extends GlowAnimal implements Pig {

    private boolean hasSaddle;

    /**
     * Creates a new Pig.
     *
     * @param location The location of the monster.
     */
    public GlowPig(Location location) {
        super(location, EntityType.PIG);
        setSize(0.9F, 0.9F);
    }

    @Override
    public boolean hasSaddle() {
        return hasSaddle;
    }

    @Override
    public void setSaddle(boolean hasSaddle) {
        this.hasSaddle = hasSaddle;
    }
}
