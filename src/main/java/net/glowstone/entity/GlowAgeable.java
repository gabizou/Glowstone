package net.glowstone.entity;

import com.artemis.ComponentMapper;
import com.flowpowered.networking.Message;
import net.glowstone.entity.components.AgeComponent;
import net.glowstone.entity.meta.MetadataIndex;
import net.glowstone.entity.meta.MetadataMap;
import net.glowstone.net.message.play.entity.EntityMetadataMessage;
import org.bukkit.Location;
import org.bukkit.entity.Ageable;
import org.bukkit.entity.EntityType;

import java.util.List;

/**
 * Represents a creature that ages, such as a sheep.
 */
public class GlowAgeable extends GlowCreature implements Ageable {

    protected float width, height;

    /**
     * Creates a new ageable monster.
     * @param location The location of the monster.
     * @param type The type of monster.
     */
    public GlowAgeable(Location location, EntityType type) {
        super(location, type);
        getArtemisEntity().edit()
                .add(new AgeComponent());
    }

    ////////////////////////////////////////////////////////////////////////////
    // Artemis getters and setters.

    protected AgeComponent getAgeComponent() {
        return ComponentMapper.getFor(AgeComponent.class, getArtemisEntity().getWorld()).get(getArtemisEntity());
    }

    @Override
    public final void setAge(int age) {
        getAgeComponent().setAge(age);
        this.setScaleForAge(isAdult());
    }

    public final int getAge() {
        return getAgeComponent().getAge();
    }

    @Override
    public final boolean getAgeLock() {
        return this.getAgeComponent().isAgeLocked();
    }

    @Override
    public final void setAgeLock(boolean ageLocked) {
        this.getAgeComponent().setAgeLocked(ageLocked);
    }

    @Override
    public final void setBaby() {
        if (isAdult()) {
            setAge(AgeComponent.AGE_BABY);
        }
    }

    @Override
    public final void setAdult() {
        if (!isAdult()) {
            setAge(AgeComponent.AGE_ADULT);
        }
    }

    @Override
    public final boolean isAdult() {
        return getAge() >= AgeComponent.AGE_ADULT;
    }

    @Override
    public final boolean canBreed() {
        return getAge() == AgeComponent.AGE_ADULT;
    }

    @Override
    public void setBreed(boolean breed) {
        if (breed) {
            setAge(AgeComponent.AGE_ADULT);
        } else if (isAdult()) {
            setAge(AgeComponent.BREEDING_AGE);
        }
    }

    public void setScaleForAge(boolean isAdult) {
        setScale(isAdult ? 1.0F : 0.5F);
    }

    @Override
    public List<Message> createSpawnMessage() {
        List<Message> messages = super.createSpawnMessage();
        MetadataMap map = getMetadataComponent().getMetadata();
        map.set(MetadataIndex.AGE, this.getAge());
        messages.add(new EntityMetadataMessage(id, map.getEntryList()));
        return messages;
    }

    protected final void setScale(float scale) {
        setSize(this.height * scale, this.width * scale);
    }
}
