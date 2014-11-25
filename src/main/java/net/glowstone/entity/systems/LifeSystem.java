package net.glowstone.entity.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.artemis.systems.EntityProcessingSystem;
import net.glowstone.entity.components.LifeComponent;
import net.glowstone.entity.components.LocationComponent;

@Wire
public class LifeSystem extends EntityProcessingSystem {

    ComponentMapper<LifeComponent> lifeMapper;
    ComponentMapper<LocationComponent> locationMapper;

    public LifeSystem() {
        super(Aspect.getAspectForAll(LifeComponent.class, LocationComponent.class));
    }

    @Override
    protected void process(Entity e) {
        LifeComponent life = lifeMapper.get(e);
        life.setTicksLived(life.getTicksLived() + 1);
        if (life.getTicksLived() % (30 * 20) == 0) {
            locationMapper.get(e).setTeleported(true);
        }
    }
}
