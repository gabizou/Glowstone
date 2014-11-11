package net.glowstone.entity.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.artemis.systems.EntityProcessingSystem;
import net.glowstone.entity.GlowAgeable;
import net.glowstone.entity.components.AgeComponent;
import net.glowstone.entity.components.GlowEntityComponent;

@Wire
public class AgeSystem extends EntityProcessingSystem {

    private ComponentMapper<AgeComponent> ageMapper;
    private ComponentMapper<GlowEntityComponent> glowEntityMapper;

    public AgeSystem() {
        super(Aspect.getAspectForAll(AgeComponent.class, GlowEntityComponent.class));
    }

    @Override
    protected void process(Entity e) {
        AgeComponent ageComponent = ageMapper.get(e);
        GlowAgeable entity = (GlowAgeable) glowEntityMapper.get(e).getEntity();
        if (!ageComponent.isAgeLocked()) {
            ageComponent.increaseAge();
        } else {
            entity.setScaleForAge(!entity.isAdult());
        }
    }
}
