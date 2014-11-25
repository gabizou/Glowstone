package net.glowstone.entity.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.artemis.systems.EntityProcessingSystem;
import net.glowstone.entity.components.InvincibilityComponent;

@Wire
public class InvincibilitySystem extends EntityProcessingSystem {

    private ComponentMapper<InvincibilityComponent> invincibilityMapper;

    public InvincibilitySystem() {
        super(Aspect.getAspectForAll(InvincibilityComponent.class));
    }

    @Override
    protected void process(Entity e) {
        InvincibilityComponent component = invincibilityMapper.get(e);

        int invincibility = component.getInvincibilityTicks();
        // invulnerability
        if (invincibility > 0) {
           component.setInvincibilityTicks(invincibility - 1);
        }
    }
}
