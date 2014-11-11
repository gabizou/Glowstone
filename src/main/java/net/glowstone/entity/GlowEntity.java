package net.glowstone.entity;

import com.artemis.ComponentMapper;
import com.flowpowered.networking.Message;
import lombok.Getter;
import lombok.Setter;
import net.glowstone.EventFactory;
import net.glowstone.GlowChunk;
import net.glowstone.GlowServer;
import net.glowstone.GlowWorld;
import net.glowstone.entity.components.*;
import net.glowstone.entity.meta.MetadataMap;
import net.glowstone.entity.physics.BoundingBox;
import net.glowstone.entity.physics.EntityBoundingBox;
import net.glowstone.net.message.play.entity.*;
import net.glowstone.util.Position;
import org.apache.commons.lang.Validate;
import org.bukkit.EntityEffect;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityPortalEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

/**
 * Represents some entity in the world such as an item on the floor or a player.
 * @author Graham Edgecombe
 */
public abstract class GlowEntity implements Entity {

    /**
     * The server this entity belongs to.
     */
    protected final GlowServer server;

    /**
     * The world this entity belongs to.
     */
    protected GlowWorld world;

    /**
     * A flag indicating if this entity is currently active.
     */
    protected boolean active = true;
    /**
     * This entity's current identifier for its world.
     */
    protected int id;
    /**
     * This entity's unique id.
     */
    private UUID uuid;

    /**
     * An EntityDamageEvent representing the last damage cause on this entity.
     */
    private EntityDamageEvent lastDamageCause;

    @Getter
    @Setter //TODO greatman Temporary
    private com.artemis.Entity artemisEntity;

    /**
     * Creates an entity and adds it to the specified world.
     * @param location The location of the entity.
     */
    public GlowEntity(Location location) {
        this.world = (GlowWorld) location.getWorld();
        artemisEntity = world.getArtemisWorld().createEntity()
                .edit()
                .add(new LocationComponent(location.clone(), location.clone()))
                .add(new VelocityComponent())
                .add(new LifeComponent())
                .add(new FallGroundComponent())
                .add(new GlowEntityComponent(this))
                .add(new FireComponent())
                .add(new MetadataComponent(this.getClass()))
                .add(new EntityBoundingBoxComponent())
                .getEntity();
        this.server = world.getServer();
        world.getEntityManager().allocate(this);
    }

    ////////////////////////////////////////////////////////////////////////////
    // Artemis Getters
    protected LocationComponent getLocationComponent() {
        return ComponentMapper.getFor(LocationComponent.class, this.getArtemisEntity().getWorld()).get(this.getArtemisEntity());
    }

    protected VelocityComponent getVelocityComponent() {
        return ComponentMapper.getFor(VelocityComponent.class, this.getArtemisEntity().getWorld()).get(this.getArtemisEntity());
    }

    protected LifeComponent getLifeComponent() {
        return ComponentMapper.getFor(LifeComponent.class, this.getArtemisEntity().getWorld()).get(this.getArtemisEntity());
    }

    protected FallGroundComponent getFallGroundComponent() {
        return ComponentMapper.getFor(FallGroundComponent.class, this.getArtemisEntity().getWorld()).get(this.getArtemisEntity());
    }

    protected GlowEntityComponent getGlowEntityComponent() {
        return ComponentMapper.getFor(GlowEntityComponent.class, this.getArtemisEntity().getWorld()).get(this.getArtemisEntity());
    }

    protected FireComponent getFireComponent() {
        return ComponentMapper.getFor(FireComponent.class, this.getArtemisEntity().getWorld()).get(this.getArtemisEntity());
    }

    protected MetadataComponent getMetadataComponent() {
        return ComponentMapper.getFor(MetadataComponent.class, this.getArtemisEntity().getWorld()).get(this.getArtemisEntity());
    }

    protected EntityBoundingBoxComponent getEntityBoundingBoxComponent() {
        return ComponentMapper.getFor(EntityBoundingBoxComponent.class, this.getArtemisEntity().getWorld()).get(this.getArtemisEntity());
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    ////////////////////////////////////////////////////////////////////////////
    // Core properties

    @Override
    public final GlowServer getServer() {
        return server;
    }

    @Override
    public final GlowWorld getWorld() {
        return world;
    }

    @Override
    public final int getEntityId() {
        return id;
    }

    @Override
    public UUID getUniqueId() {
        if (uuid == null) {
            uuid = UUID.randomUUID();
        }
        return uuid;
    }

    /**
     * Sets this entity's unique identifier if possible.
     *
     * @param uuid The new UUID. Must not be null.
     * @throws IllegalArgumentException if the passed UUID is null.
     * @throws IllegalStateException    if a UUID has already been set.
     */
    public void setUniqueId(UUID uuid) {
        Validate.notNull(uuid, "uuid must not be null");
        if (this.uuid == null) {
            this.uuid = uuid;
        } else if (!this.uuid.equals(uuid)) {
            // silently allow setting the same UUID, since
            // it can't be checked with getUniqueId()
            throw new IllegalStateException("UUID of " + this + " is already " + this.uuid);
        }
    }

    @Override
    public boolean isDead() {
        return !active;
    }

    @Override
    public boolean isValid() {
        return world.getEntityManager().getEntity(id) == this;
    }

    ////////////////////////////////////////////////////////////////////////////
    // Location stuff

    @Override
    public Location getLocation() {
        return getLocationComponent().getLocation();
    }

    @Override
    public Location getLocation(Location loc) {
        return Position.copyLocation(getLocationComponent().getLocation(), loc);
    }

    /**
     * Get the direction (SOUTH, WEST, NORTH, or EAST) this entity is facing.
     * @return The cardinal BlockFace of this entity.
     */
    public BlockFace getFacingDirection() {
        double rot = getLocation().getYaw() % 360;
        if (rot < 0) {
            rot += 360.0;
        }
        if (0 <= rot && rot < 45) {
            return BlockFace.SOUTH;
        } else if (45 <= rot && rot < 135) {
            return BlockFace.WEST;
        } else if (135 <= rot && rot < 225) {
            return BlockFace.NORTH;
        } else if (225 <= rot && rot < 315) {
            return BlockFace.EAST;
        } else if (315 <= rot && rot < 360.0) {
            return BlockFace.SOUTH;
        } else {
            return BlockFace.EAST;
        }
    }

    /**
     * Gets the full direction (including SOUTH_SOUTH_EAST etc) this entity is facing.
     * @return The intercardinal BlockFace of this entity
     */
    public BlockFace getFacing() {
        long facing = Math.round(getLocation().getYaw() / 22.5) + 8;
        return Position.getDirection((byte) (facing % 16));
    }
    
    @Override
    public Vector getVelocity() {
        return getVelocityComponent().getVelocity().clone();
    }

    @Override
    public void setVelocity(Vector velocity) {
        getVelocity().copy(velocity);
        getVelocityComponent().setVelocityChanged(true);
    }

    @Override
    public boolean teleport(Location location) {
        if (location.getWorld() != world) {
            world.getEntityManager().deallocate(this);
            world = (GlowWorld) location.getWorld();
            world.getEntityManager().allocate(this);
        }
        setRawLocation(location);
        getLocationComponent().setTeleported(true);
        return true;
    }

    @Override
    public boolean teleport(Entity destination) {
        return teleport(destination.getLocation());
    }

    @Override
    public boolean teleport(Location location, TeleportCause cause) {
        return teleport(location);
    }

    @Override
    public boolean teleport(Entity destination, TeleportCause cause) {
        return teleport(destination.getLocation(), cause);
    }

    ////////////////////////////////////////////////////////////////////////////
    // Internals

    /**
     * Checks if this entity is within the visible radius of another.
     * @param other The other entity.
     * @return {@code true} if the entities can see each other, {@code false} if
     * not.
     */
    public boolean isWithinDistance(GlowEntity other) {
        return isWithinDistance(other.getLocation());
    }

    /**
     * Checks if this entity is within the visible radius of a location.
     * @param loc The location.
     * @return {@code true} if the entities can see each other, {@code false} if
     * not.
     */
    public boolean isWithinDistance(Location loc) {

        double dx = Math.abs(getLocation().getX() - loc.getX());
        double dz = Math.abs(getLocation().getZ() - loc.getZ());
        return loc.getWorld() == getWorld() && dx <= (server.getViewDistance() * GlowChunk.WIDTH) && dz <= (server.getViewDistance()
                                                                                                            * GlowChunk.HEIGHT);
    }

    /**
     * Checks whether this entity should be saved as part of the world.
     * @return True if the entity should be saved.
     */
    public boolean shouldSave() {
        return true;
    }


    /**
     * Resets the previous location and other properties to their current value.
     */
    public void reset() {
        Position.copyLocation(getLocation(), getLocationComponent().getPreviousLocation());
        getMetadataComponent().getMetadata().resetChanges();
        getLocationComponent().setTeleported(false);
        getVelocityComponent().setVelocityChanged(false);
    }

    /**
     * Gets the entity's previous position.
     * @return The previous position of this entity.
     */
    public Location getPreviousLocation() {
        return getLocationComponent().getPreviousLocation();
    }

    /**
     * Sets this entity's location.
     * @param location The new location.
     */
    public void setRawLocation(Location location) {
        if (location.getWorld() != world) {
            throw new IllegalArgumentException("Cannot setRawLocation to a different world (got " + location.getWorld() + ", expected " + world + ")");
        }
        world.getEntityManager().move(this, location);
        Position.copyLocation(location, getLocation());
    }

    /**
     * Creates a {@link Message} which can be sent to a client to spawn this
     * entity.
     * @return A message which can spawn this entity.
     */
    public abstract List<Message> createSpawnMessage();

    /**
     * Creates a {@link Message} which can be sent to a client to update this
     * entity.
     * @return A message which can update this entity.
     */
    public List<Message> createUpdateMessage() {
        boolean moved = hasMoved();
        boolean rotated = hasRotated();

        LocationComponent locationComponent = getLocationComponent();
        int x = Position.getIntX(locationComponent.getLocation());
        int y = Position.getIntY(locationComponent.getLocation());
        int z = Position.getIntZ(locationComponent.getLocation());

        int dx = x - Position.getIntX(locationComponent.getPreviousLocation());
        int dy = y - Position.getIntY(locationComponent.getPreviousLocation());
        int dz = z - Position.getIntZ(locationComponent.getPreviousLocation());

        boolean
                teleport =
                dx > Byte.MAX_VALUE || dy > Byte.MAX_VALUE || dz > Byte.MAX_VALUE || dx < Byte.MIN_VALUE || dy < Byte.MIN_VALUE
                        || dz < Byte.MIN_VALUE;

        int yaw = Position.getIntYaw(locationComponent.getLocation());
        int pitch = Position.getIntPitch(locationComponent.getLocation());

        List<Message> result = new LinkedList<>();
        if (locationComponent.isTeleported() || (moved && teleport)) {
            result.add(new EntityTeleportMessage(id, x, y, z, yaw, pitch));
        } else if (moved && rotated) {
            result.add(new RelativeEntityPositionRotationMessage(id, dx, dy, dz, yaw, pitch));
        } else if (moved) {
            result.add(new RelativeEntityPositionMessage(id, dx, dy, dz));
        } else if (rotated) {
            result.add(new EntityRotationMessage(id, yaw, pitch));
        }

        // todo: handle head rotation as a separate value
        if (rotated) {
            result.add(new EntityHeadRotationMessage(id, yaw));
        }

        // send changed metadata
        List<MetadataMap.Entry> changes = getMetadataComponent().getMetadata().getChanges();
        if (changes.size() > 0) {
            result.add(new EntityMetadataMessage(id, changes));
            getMetadataComponent().getMetadata().resetChanges();
        }

        // send velocity if needed
        if (getVelocityComponent().isVelocityChanged()) {
            result.add(new EntityVelocityMessage(id, getVelocityComponent().getVelocity()));
        }

        return result;
    }

    /**
     * Checks if this entity has moved this cycle.
     * @return {@code true} if so, {@code false} if not.
     */
    public boolean hasMoved() {
        LocationComponent locationComponent = getLocationComponent();
        return Position.hasMoved(locationComponent.getLocation(), locationComponent.getPreviousLocation());
    }

    /**
     * Checks if this entity has rotated this cycle.
     * @return {@code true} if so, {@code false} if not.
     */
    public boolean hasRotated() {
        LocationComponent locationComponent = getLocationComponent();
        return Position.hasRotated(locationComponent.getLocation(), locationComponent.getPreviousLocation());
    }

    protected final void setBoundingBox(double xz, double y) {
        getEntityBoundingBoxComponent().setEntityBoundingBox(new EntityBoundingBox(xz, y));
    }

    /**
     * Teleport this entity to the spawn point of the main world.
     * This is used to teleport out of the End.
     * @return {@code true} if the teleport was successful.
     */
    public boolean teleportToSpawn() {
        Location target = server.getWorlds().get(0).getSpawnLocation();

        EntityPortalEvent event = EventFactory.callEvent(new EntityPortalEvent(this, getLocation().clone(), target, null));
        if (event.isCancelled()) {
            return false;
        }
        target = event.getTo();

        teleport(target);
        return true;
    }

    /**
     * Teleport this entity to the End.
     * If no End world is loaded this does nothing.
     * @return {@code true} if the teleport was successful.
     */
    public boolean teleportToEnd() {
        if (!server.getAllowEnd()) {
            return false;
        }
        Location target = null;
        for (World world : server.getWorlds()) {
            if (world.getEnvironment() == World.Environment.THE_END) {
                target = world.getSpawnLocation();
                break;
            }
        }
        if (target == null) {
            return false;
        }

        EntityPortalEvent event = EventFactory.callEvent(new EntityPortalEvent(this, getLocation().clone(), target, null));
        if (event.isCancelled()) {
            return false;
        }
        target = event.getTo();

        teleport(target);
        return true;
    }

    protected void setSize(float xz, float y) {
        //todo Size stuff with bounding boxes.
    }

    public boolean intersects(BoundingBox box) {
        return getEntityBoundingBoxComponent().getEntityBoundingBox() != null && getEntityBoundingBoxComponent().getEntityBoundingBox().intersects(box);
    }

    @Override
    public int getFireTicks() {
        return getFireComponent().getFireTicks();
    }

    @Override
    public void setFireTicks(int ticks) {
        getFireComponent().setFireTicks(ticks);
    }

    @Override
    public int getMaxFireTicks() {
        return 160;  // this appears to be Minecraft's default value
    }

    @Override
    public float getFallDistance() {
        return getFallGroundComponent().getFallDistance();
    }

    @Override
    public void setFallDistance(float distance) {
        getFallGroundComponent().setFallDistance(Math.max(distance, 0));
    }

    @Override
    public void setLastDamageCause(EntityDamageEvent event) {
        lastDamageCause = event;
    }

    @Override
    public EntityDamageEvent getLastDamageCause() {
        return lastDamageCause;
    }

    @Override
    public int getTicksLived() {
        return getLifeComponent().getTicksLived();
    }

    @Override
    public void setTicksLived(int value) {
        getLifeComponent().setTicksLived(value);
    }

    @Override
    public boolean isOnGround() {
        return getFallGroundComponent().isOnGround();
    }

    public void setOnGround(boolean onGround) {
        getFallGroundComponent().setOnGround(onGround);
    }

    ////////////////////////////////////////////////////////////////////////////
    // Miscellaneous actions

    @Override
    public void remove() {
        active = false;
        world.getEntityManager().deallocate(this);
        getArtemisEntity().deleteFromWorld();
    }

    @Override
    public List<Entity> getNearbyEntities(double x, double y, double z) {
        // This behavior is similar to CraftBukkit, where a call with args
        // (0, 0, 0) finds any entities whose bounding boxes intersect that of
        // this entity.

        BoundingBox searchBox;
        if (getEntityBoundingBoxComponent().getEntityBoundingBox() == null) {
            searchBox = BoundingBox.fromPositionAndSize(getLocation().toVector(), new Vector(0, 0, 0));
        } else {
            searchBox = BoundingBox.copyOf(getEntityBoundingBoxComponent().getEntityBoundingBox());
        }
        Vector vec = new Vector(x, y, z);
        searchBox.minCorner.subtract(vec);
        searchBox.maxCorner.add(vec);

        return world.getEntityManager().getEntitiesInside(searchBox, this);
    }

    @Override
    public void playEffect(EntityEffect type) {

    }

    ////////////////////////////////////////////////////////////////////////////
    // Entity stacking

    @Override
    public boolean isInsideVehicle() {
        return getVehicle() != null;
    }

    @Override
    public boolean leaveVehicle() {
        return false;
    }

    @Override
    public Entity getVehicle() {
        return null;
    }

    @Override
    public Entity getPassenger() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean setPassenger(Entity passenger) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean isEmpty() {
        return getPassenger() == null;
    }

    @Override
    public boolean eject() {
        return !isEmpty() && setPassenger(null);
    }

    ////////////////////////////////////////////////////////////////////////////
    // Metadata

    @Override
    public void setMetadata(String metadataKey, MetadataValue newMetadataValue) {
        MetadataComponent.bukkitMetadata.setMetadata(this, metadataKey, newMetadataValue);
    }

    @Override
    public List<MetadataValue> getMetadata(String metadataKey) {
        return MetadataComponent.bukkitMetadata.getMetadata(this, metadataKey);
    }

    @Override
    public boolean hasMetadata(String metadataKey) {
        return MetadataComponent.bukkitMetadata.hasMetadata(this, metadataKey);
    }

    @Override
    public void removeMetadata(String metadataKey, Plugin owningPlugin) {
        MetadataComponent.bukkitMetadata.removeMetadata(this, metadataKey, owningPlugin);
    }
}
