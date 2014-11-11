package net.glowstone.entity.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.artemis.systems.EntityProcessingSystem;
import net.glowstone.entity.components.GlowEntityComponent;
import net.glowstone.entity.components.LifeComponent;
import net.glowstone.entity.components.LocationComponent;
import org.bukkit.Material;

@Wire
public class GravitySystem extends EntityProcessingSystem {

    ComponentMapper<GlowEntityComponent> entityMapper;
    ComponentMapper<LocationComponent> locationMapper;
    ComponentMapper<LifeComponent> lifeMapper;

    public GravitySystem() {
        super(Aspect.getAspectForAll(LocationComponent.class, GlowEntityComponent.class, LifeComponent.class));
    }

    @Override
    protected void process(Entity e) {
        LifeComponent lifeComponent = lifeMapper.get(e);
        GlowEntityComponent glowEntityComponent = entityMapper.get(e);
        LocationComponent locationComponent = locationMapper.get(e);
        if (lifeComponent.getTicksLived() >= 100) {
            if (locationComponent.getLocation().clone().subtract(0, 1, 0).getBlock().getType() == Material.AIR) {
                locationComponent.getLocation().subtract(0, 1, 0);
            }
        }
    }
}
