package net.glowstone.entity.animals;

import net.glowstone.entity.GlowAnimal;

import org.bukkit.Location;
import org.bukkit.entity.AnimalTamer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Tameable;

public abstract class GlowTameable extends GlowAnimal implements Tameable {

    private AnimalTamer owner;

    private boolean isTamed;

    /**
     * Creates a new tamed animal.
     *
     * @param location The location of the animal
     * @param type     The type of animal
     */
    public GlowTameable(Location location, EntityType type) {
        super(location, type);
        setSize(0.3F, 0.7F);
    }

    /**
     * Creates a new tamed animal.
     *
     * @param location The location of the animal
     * @param type     The type of animal
     * @param owner    The owner of the animal
     */
    protected GlowTameable(Location location, EntityType type, AnimalTamer owner) {
        super(location, type);
        this.owner = owner;
    }

    @Override
    public boolean isTamed() {
        return isTamed;
    }

    @Override
    public void setTamed(boolean isTamed) {
        this.isTamed = isTamed;
    }

    @Override
    public AnimalTamer getOwner() {
        return owner;
    }

    @Override
    public void setOwner(AnimalTamer animalTamer) {
        this.owner = animalTamer;
    }
}
