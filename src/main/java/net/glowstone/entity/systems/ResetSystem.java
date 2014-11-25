package net.glowstone.entity.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.artemis.systems.EntityProcessingSystem;
import net.glowstone.entity.components.GlowEntityComponent;

/**
 * Used to call reset() on every entities at the very end
 */
@Wire
public class ResetSystem extends EntityProcessingSystem {

    private ComponentMapper<GlowEntityComponent> glowEntityMapper;

    public ResetSystem() {
        super(Aspect.getAspectForAll(GlowEntityComponent.class));
    }

    @Override
    protected void process(Entity e) {
        glowEntityMapper.get(e).getEntity().reset();
    }
}
