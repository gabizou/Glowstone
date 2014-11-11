package net.glowstone.entity.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.artemis.systems.EntityProcessingSystem;
import net.glowstone.entity.components.ExpirableAngerComponent;

@Wire
public class AngerSystem extends EntityProcessingSystem {

    private ComponentMapper<ExpirableAngerComponent> angerMap;

    public AngerSystem() {
        super(Aspect.getAspectForAll(ExpirableAngerComponent.class));
    }

    @Override
    protected void process(Entity e) {
        ExpirableAngerComponent angerComponent = angerMap.get(e);
        if (!angerComponent.isAngry()) {
            angerComponent.decreaseAnger();
        }
    }
}
