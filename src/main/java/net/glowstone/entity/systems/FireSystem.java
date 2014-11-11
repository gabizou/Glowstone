package net.glowstone.entity.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.artemis.systems.EntityProcessingSystem;
import net.glowstone.entity.GlowEntity;
import net.glowstone.entity.GlowLivingEntity;
import net.glowstone.entity.components.FireComponent;
import net.glowstone.entity.components.GlowEntityComponent;
import net.glowstone.entity.components.HealthComponent;
import net.glowstone.entity.components.MetadataComponent;
import net.glowstone.entity.meta.MetadataIndex;
import org.bukkit.event.entity.EntityDamageEvent;

@Wire
public class FireSystem extends EntityProcessingSystem {

    ComponentMapper<FireComponent> fireMapper;
    ComponentMapper<GlowEntityComponent> entityMapper;
    ComponentMapper<MetadataComponent> metadataMapper;

    public FireSystem() {
        super(Aspect.getAspectForAll(FireComponent.class, HealthComponent.class, GlowEntityComponent.class, MetadataComponent.class));
    }

    @Override
    protected void process(Entity e) {
        FireComponent component = fireMapper.get(e);
        GlowEntity entity = entityMapper.get(e).getEntity();
        if (component.getFireTicks() > 0) {
            metadataMapper.get(e).getMetadata().setBit(MetadataIndex.STATUS, MetadataIndex.StatusFlags.ON_FIRE, component.getFireTicks() > 0);
            if (entity instanceof GlowLivingEntity) {
                // This seems a little too dependent on the OOP system, we could probably do this better by handing it off to a general DamageSystem
                ((GlowLivingEntity) entity).damage(1, EntityDamageEvent.DamageCause.FIRE_TICK);

            } else {
                entity.remove();
            }
            component.setFireTicks(component.getFireTicks() - 1);
        }
    }
}
