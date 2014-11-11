package net.glowstone.entity.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.artemis.systems.EntityProcessingSystem;
import net.glowstone.entity.components.PickupDelayComponent;

@Wire
public class PickupDelaySystem extends EntityProcessingSystem {

    private ComponentMapper<PickupDelayComponent> pickupDelayMapper;

    public PickupDelaySystem() {
        super(Aspect.getAspectForAll(PickupDelayComponent.class));
    }

    @Override
    protected void process(Entity e) {
        PickupDelayComponent pickupDelayComponent = pickupDelayMapper.get(e);

        // decrement pickupDelay if it's less than the NBT maximum
        if (pickupDelayComponent.getPickupDelay() > 0) {
            pickupDelayComponent.decreasePickupDelay();
        }
    }
}
