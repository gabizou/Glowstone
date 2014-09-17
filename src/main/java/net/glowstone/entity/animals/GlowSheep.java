package net.glowstone.entity.animals;

import net.glowstone.entity.GlowAnimal;

import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Sheep;

public class GlowSheep extends GlowAnimal implements Sheep {

    private boolean isSheared;

    private DyeColor color;

    /**
     * Creates a new Sheep.
     *
     * @param location The location of the monster
     */
    public GlowSheep(Location location) {
        super(location, EntityType.SHEEP);
        setSize(0.9F, 1.3F);
    }

    @Override
    public boolean isSheared() {
        return isSheared;
    }

    @Override
    public void setSheared(boolean sheared) {
        this.isSheared = sheared;
    }

    @Override
    public DyeColor getColor() {
        return color;
    }

    @Override
    public void setColor(DyeColor dyeColor) {
        this.color = dyeColor;
    }
}
