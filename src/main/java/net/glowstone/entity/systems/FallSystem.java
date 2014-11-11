package net.glowstone.entity.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.artemis.systems.EntityProcessingSystem;
import net.glowstone.entity.GlowLivingEntity;
import net.glowstone.entity.components.FallGroundComponent;
import net.glowstone.entity.components.GlowEntityComponent;
import net.glowstone.entity.components.HealthComponent;
import org.bukkit.event.entity.EntityDamageEvent;

@Wire
public class FallSystem extends EntityProcessingSystem {

    private ComponentMapper<FallGroundComponent> fallGroundMapper;

    public FallSystem() {
        super(Aspect.getAspectForAll(HealthComponent.class, FallGroundComponent.class));
    }

    @Override
    protected void process(Entity e) {
        FallGroundComponent component = fallGroundMapper.get(e);
        if (component.isOnGround() && component.getFallDistance() > 0) {
            if (component.getFallDistance() >= 4) {
                int damage = (int) (component.getFallDistance() - 3);
                System.out.println("Removing " + damage);
                ((GlowLivingEntity) e.getComponent(GlowEntityComponent.class).getEntity()).damage(damage, EntityDamageEvent.DamageCause.FALL);
                component.setFallDistance(0);
            }
        }
    }
}
