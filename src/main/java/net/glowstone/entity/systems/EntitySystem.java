package net.glowstone.entity.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.artemis.systems.EntityProcessingSystem;
import net.glowstone.EventFactory;
import net.glowstone.entity.GlowEntity;
import net.glowstone.entity.components.*;
import net.glowstone.util.Position;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.event.entity.EntityPortalEnterEvent;
import org.bukkit.event.entity.EntityPortalExitEvent;
import org.bukkit.util.Vector;

@Wire
public class EntitySystem extends EntityProcessingSystem {


    private ComponentMapper<GlowEntityComponent> entityMapper;
    private ComponentMapper<EntityBoundingBoxComponent> entityBoundingBoxMapper;
    private ComponentMapper<LocationComponent> locationMapper;
    private ComponentMapper<VelocityComponent> velocityMapper;

    public EntitySystem() {
        super(Aspect.getAspectForAll(GlowEntityComponent.class, EntityBoundingBoxComponent.class, LocationComponent.class, VelocityComponent.class));
    }

    @Override
    protected void process(Entity e) {
        EntityBoundingBoxComponent entityBoundingBoxComponent = entityBoundingBoxMapper.get(e);
        LocationComponent locationComponent = locationMapper.get(e);
        GlowEntity entity = entityMapper.get(e).getEntity();
        VelocityComponent velocityComponent = velocityMapper.get(e);
        // todo: update location based on velocity,
        // do gravity, all that other good stuff

        // make sure bounding box is up to date
        if (entityBoundingBoxComponent.getEntityBoundingBox() != null) {
            entityBoundingBoxComponent.getEntityBoundingBox().setCenter(locationComponent.getLocation().getX(), locationComponent.getLocation().getY(), locationComponent.getLocation().getZ());
        }

        if (Position.hasMoved(locationComponent.getLocation(), locationComponent.getPreviousLocation())) {
            Block currentBlock = locationComponent.getLocation().getBlock();
            if (currentBlock.getType() == Material.ENDER_PORTAL) {
                EventFactory.callEvent(new EntityPortalEnterEvent(entity, currentBlock.getLocation()));
                if (entity.getServer().getAllowEnd()) {
                    Location previousLocation = locationComponent.getLocation().clone();
                    boolean success;
                    if (entity.getWorld().getEnvironment() == World.Environment.THE_END) {
                        success = entity.teleportToSpawn();
                    } else {
                        success = entity.teleportToEnd();
                    }
                    if (success) {
                        EntityPortalExitEvent event = EventFactory.callEvent(new EntityPortalExitEvent(entity, previousLocation, locationComponent.getLocation().clone(), velocityComponent.getVelocity().clone(), new Vector()));
                        if (!event.getAfter().equals(velocityComponent.getVelocity())) {
                            velocityComponent.setVelocity(event.getAfter());
                        }
                    }
                }
            }
        }

    }
}
