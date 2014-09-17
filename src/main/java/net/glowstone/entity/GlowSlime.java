package net.glowstone.entity;

import java.util.List;

import com.flowpowered.networking.Message;

import org.bukkit.Location;
import org.bukkit.entity.Animals;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Slime;

public class GlowSlime extends GlowLivingEntity implements Slime {

    private int size = 0;
    private EntityType type;

    /**
     * Creates a new ageable animal.
     * @param location The location of the animal.
     * @param type The type of animal.
     */
    protected GlowSlime(Location location, EntityType type) {
        super(location);
        this.type = type;
    }

    public GlowSlime(Location location) {
        super(location);
        this.type = EntityType.SLIME;
    }

    @Override
    public List<Message> createSpawnMessage() {
        return null;
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public void setSize(int i) {
        this.size = i;
    }

    @Override
    public EntityType getType() {
        return type;
    }
}
