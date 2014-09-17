package net.glowstone.entity;

import org.bukkit.Location;
import org.bukkit.entity.Ageable;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.NPC;
import org.bukkit.entity.Villager;

/**
 * Represents a Villager
 */
public class GlowVillager extends GlowAgeable implements Villager {

    private Profession profession;

    /**
     * Creates a new Villager.
     * @param location The location of the villager
     */
    public GlowVillager(Location location) {
        super(location, EntityType.VILLAGER);
    }

    @Override
    public Profession getProfession() {
        return profession;
    }

    @Override
    public void setProfession(Profession profession) {
        this.profession = profession;
    }
}
