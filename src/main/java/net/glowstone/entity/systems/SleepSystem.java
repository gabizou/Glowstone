package net.glowstone.entity.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.artemis.systems.EntityProcessingSystem;
import net.glowstone.entity.components.SleepingComponent;

@Wire
public class SleepSystem extends EntityProcessingSystem {

    private ComponentMapper<SleepingComponent> sleepingMapper;

    public SleepSystem() {
        super(Aspect.getAspectForAll(SleepingComponent.class));
    }

    @Override
    protected void process(Entity e) {
        SleepingComponent sleepingComponent = sleepingMapper.get(e);
        if (sleepingComponent.isSleeping()) {
            sleepingComponent.setSleepingTicks(sleepingComponent.getSleepingTicks() + 1);
        } else {
            sleepingComponent.setSleepingTicks(0);
        }
    }
}
