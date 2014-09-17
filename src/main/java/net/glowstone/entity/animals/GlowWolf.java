package net.glowstone.entity.animals;

import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.entity.AnimalTamer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Wolf;

public class GlowWolf extends GlowTameable implements Wolf {

    private boolean isAngry = false;

    private boolean isSitting = false;

    private DyeColor collarColor = DyeColor.RED;

    /**
     * Creates a new tamed animal.
     *
     * @param location The location of the animal
     * @param type     The type of animal
     */
    public GlowWolf(Location location, EntityType type) {
        super(location, type);
    }

    /**
     * Creates a new tamed animal.
     *
     * @param location The location of the animal
     * @param type     The type of animal
     * @param owner    The owner of the animal
     */
    protected GlowWolf(Location location, EntityType type, AnimalTamer owner) {
        super(location, type, owner);
    }

    @Override
    public boolean isAngry() {
        return isAngry;
    }

    @Override
    public void setAngry(boolean angry) {
        this.isAngry = angry;
    }

    @Override
    public boolean isSitting() {
        return isSitting;
    }

    @Override
    public void setSitting(boolean sitting) {
        this.isSitting = sitting;
    }

    @Override
    public DyeColor getCollarColor() {
        return collarColor;
    }

    @Override
    public void setCollarColor(DyeColor dyeColor) {
        this.collarColor = dyeColor;
    }
}
