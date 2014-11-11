package net.glowstone.entity.objects;

import com.artemis.ComponentMapper;
import com.flowpowered.networking.Message;
import net.glowstone.entity.GlowEntity;
import net.glowstone.entity.components.ExpirableLifeComponent;
import net.glowstone.entity.components.PickupDelayComponent;
import net.glowstone.entity.meta.MetadataIndex;
import net.glowstone.net.message.play.entity.EntityMetadataMessage;
import net.glowstone.net.message.play.entity.EntityTeleportMessage;
import net.glowstone.net.message.play.entity.EntityVelocityMessage;
import net.glowstone.net.message.play.entity.SpawnObjectMessage;
import net.glowstone.util.Position;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

/**
 * Represents an item that is also an {@link net.glowstone.entity.GlowEntity} within the world.
 * @author Graham Edgecombe
 */
public final class GlowItem extends GlowEntity implements Item {

    /**
     * The number of ticks (equal to 5 minutes) that item entities should live for.
     */
    private static final int LIFETIME = 5 * 60 * 20;

    /**
     * Creates a new item entity.
     * @param location The location of the entity.
     * @param item The item stack the entity is carrying.
     */
    public GlowItem(Location location, ItemStack item) {
        super(location);
        setItemStack(item);
        setBoundingBox(0.25, 0.25);
        getArtemisEntity().edit()
                .add(new ExpirableLifeComponent(LIFETIME))
                .add(new PickupDelayComponent(20));
    }

    protected ExpirableLifeComponent getExpirableLifeComponent() {
        return ComponentMapper.getFor(ExpirableLifeComponent.class, getArtemisEntity().getWorld()).get(getArtemisEntity());
    }

    protected PickupDelayComponent getPickupDelayComponent() {
        return ComponentMapper.getFor(PickupDelayComponent.class, getArtemisEntity().getWorld()).get(getArtemisEntity());
    }

    ////////////////////////////////////////////////////////////////////////////
    // Overrides

    @Override
    public EntityType getType() {
        return EntityType.DROPPED_ITEM;
    }

    @Override
    public List<Message> createSpawnMessage() {
        Location location = getLocation();
        int x = Position.getIntX(location);
        int y = Position.getIntY(location);
        int z = Position.getIntZ(location);

        int yaw = Position.getIntYaw(location);
        int pitch = Position.getIntPitch(location);

        return Arrays.asList(
                new SpawnObjectMessage(id, SpawnObjectMessage.ITEM, x, y, z, pitch, yaw),
                new EntityMetadataMessage(id, getMetadataComponent().getMetadata().getEntryList()),
                // these keep the client from assigning a random velocity
                new EntityTeleportMessage(id, x, y, z, yaw, pitch),
                new EntityVelocityMessage(id, getVelocity())
        );
    }

    ////////////////////////////////////////////////////////////////////////////
    // Item stuff

    @Override
    public int getPickupDelay() {
        return getPickupDelayComponent().getPickupDelay();
    }

    @Override
    public void setPickupDelay(int delay) {
        getPickupDelayComponent().setPickupDelay(delay);
    }

    @Override
    public ItemStack getItemStack() {
        return getMetadataComponent().getMetadata().getItem(MetadataIndex.ITEM_ITEM);
    }

    @Override
    public void setItemStack(ItemStack stack) {
        // stone is the "default state" for the item stack according to the client
        getMetadataComponent().getMetadata().set(MetadataIndex.ITEM_ITEM, stack == null ? new ItemStack(Material.STONE) : stack.clone());
    }

}
