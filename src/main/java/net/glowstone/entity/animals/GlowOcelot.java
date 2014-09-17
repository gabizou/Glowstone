package net.glowstone.entity.animals;

import org.bukkit.Location;
import org.bukkit.entity.AnimalTamer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Ocelot;

public class GlowOcelot extends GlowTameable implements Ocelot {

    private boolean isSitting = false;

    private Type ocelotType = Type.WILD_OCELOT;

    /**
     * Creates a new tamed animal.
     *
     * @param location The location of the animal
     * @param type     The type of animal
     */
    public GlowOcelot(Location location, EntityType type) {
        super(location, type);
    }

    /**
     * Creates a new tamed animal.
     *
     * @param location The location of the animal
     * @param type     The type of animal
     * @param owner    The owner of the animal
     */
    protected GlowOcelot(Location location, EntityType type, AnimalTamer owner) {
        super(location, type, owner);
    }

    @Override
    public Type getCatType() {
        return ocelotType;
    }

    @Override
    public void setCatType(Type type) {
        this.ocelotType = type;
    }

    @Override
    public boolean isSitting() {
        return isSitting;
    }

    @Override
    public void setSitting(boolean sitting) {
        this.isSitting = sitting;
    }

}
