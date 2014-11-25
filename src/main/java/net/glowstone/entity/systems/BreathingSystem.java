package net.glowstone.entity.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.artemis.systems.EntityProcessingSystem;
import net.glowstone.entity.GlowLivingEntity;
import net.glowstone.entity.components.AirComponent;
import net.glowstone.entity.components.GlowEntityComponent;
import net.glowstone.entity.components.LocationComponent;
import org.bukkit.Material;
import org.bukkit.event.entity.EntityDamageEvent;

@Wire
public class BreathingSystem extends EntityProcessingSystem {

    private ComponentMapper<AirComponent> airMapper;
    private ComponentMapper<LocationComponent> locationMapper;
    private ComponentMapper<GlowEntityComponent> glowEntityMapper;

    public BreathingSystem() {
        super(Aspect.getAspectForAll(LocationComponent.class, AirComponent.class, GlowEntityComponent.class));
    }

    @Override
    protected void process(Entity e) {
        AirComponent airComponent = airMapper.get(e);
        LocationComponent locationComponent = locationMapper.get(e);
        GlowLivingEntity entity = (GlowLivingEntity) glowEntityMapper.get(e).getEntity();

        Material mat = entity.getEyeLocation().getBlock().getType();
        // breathing
        if (mat == Material.WATER || mat == Material.STATIONARY_WATER) {
            if (entity.canDrown()) {
                airComponent.setAir(airComponent.getAir() - 1);
                if (airComponent.getAir() <= -20) {
                    airComponent.setAir(0);
                    entity.damage(1, EntityDamageEvent.DamageCause.DROWNING);
                }
            }
        } else {
            airComponent.setAir(airComponent.getMaxAir());
        }
    }
}
