package net.glowstone.entity.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.artemis.systems.EntityProcessingSystem;
import net.glowstone.entity.components.*;

@Wire
public class ExpireableLifetimeSystem extends EntityProcessingSystem {

    ComponentMapper<ExpirableLifeComponent> expirableLifeMapper;
    ComponentMapper<LifeComponent> lifeMapper;
    ComponentMapper<GlowEntityComponent> entityMapper;

    public ExpireableLifetimeSystem() {
        super(Aspect.getAspectForAll(GlowEntityComponent.class, ExpirableLifeComponent.class, LifeComponent.class));
    }

    @Override
    protected void process(Entity e) {
        ExpirableLifeComponent expirableLife = expirableLifeMapper.get(e);
        LifeComponent life = lifeMapper.get(e);
        GlowEntityComponent glowEntityComponent = entityMapper.get(e);
        if (expirableLife.getMaxLife() <= life.getTicksLived()) {
            glowEntityComponent.getEntity().remove();
        }
    }
}
