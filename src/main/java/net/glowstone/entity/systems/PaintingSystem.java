package net.glowstone.entity.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.systems.EntityProcessingSystem;
import net.glowstone.entity.components.GlowEntityComponent;
import net.glowstone.entity.components.LocationComponent;
import net.glowstone.entity.components.PaintingComponent;
import net.glowstone.entity.objects.GlowPainting;

public class PaintingSystem extends EntityProcessingSystem {

    private ComponentMapper<PaintingComponent> paintingMapper;
    private ComponentMapper<LocationComponent> locationMapper;
    private ComponentMapper<GlowEntityComponent> glowEntityMapper;

    public PaintingSystem() {
        super(Aspect.getAspectForAll(PaintingComponent.class, LocationComponent.class, GlowEntityComponent.class));
    }

    @Override
    protected void process(Entity e) {
        PaintingComponent paintingComponent = paintingMapper.get(e);
        LocationComponent locationComponent = locationMapper.get(e);
        GlowPainting painting = (GlowPainting) glowEntityMapper.get(e).getEntity();
        if (!GlowPainting.fitsOnWall(locationComponent.getLocation().getWorld(), locationComponent.getLocation(), painting.getFacingDirection(), painting.getArt())) {
            painting.remove();
        }
    }
}
