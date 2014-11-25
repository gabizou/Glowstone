package net.glowstone.entity.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.artemis.systems.EntityProcessingSystem;
import net.glowstone.entity.GlowLivingEntity;
import net.glowstone.entity.components.GlowEntityComponent;
import net.glowstone.entity.components.LifeComponent;
import net.glowstone.entity.components.LocationComponent;
import org.bukkit.Material;
import org.bukkit.entity.*;

@Wire
public class GravitySystem extends EntityProcessingSystem {

    public static final double ENTITY_FALL_SPEED = 0.08;
    public static final double ITEM_TNT_BOAT_MINECART_FALL_SPEED = 0.04;
    public static final double PROJECTILE_FALL_SPEED = 0.03;
    public static final double ARROW_FALL_SPEED = 0.05;

    ComponentMapper<GlowEntityComponent> entityMapper;
    ComponentMapper<LocationComponent> locationMapper;
    ComponentMapper<LifeComponent> lifeMapper;

    public GravitySystem() {
        super(Aspect.getAspectForAll(LocationComponent.class, GlowEntityComponent.class, LifeComponent.class));
    }

    @Override
    protected void process(Entity e) {
        LifeComponent lifeComponent = lifeMapper.get(e);
        GlowEntityComponent glowEntityComponent = entityMapper.get(e);
        LocationComponent locationComponent = locationMapper.get(e);
        if (lifeComponent.getTicksLived() >= 100) {
            double value = 0;
            if (glowEntityComponent.getEntity() instanceof GlowLivingEntity) {
                value = ENTITY_FALL_SPEED;
            } else if (glowEntityComponent.getEntity() instanceof FallingBlock || glowEntityComponent.getEntity() instanceof TNTPrimed || glowEntityComponent.getEntity() instanceof Item || glowEntityComponent.getEntity() instanceof Vehicle) {
                value = ITEM_TNT_BOAT_MINECART_FALL_SPEED;
            } else if (glowEntityComponent.getEntity() instanceof Arrow) {
                value = ARROW_FALL_SPEED;
            } else if (glowEntityComponent.getEntity() instanceof Projectile) {
                value = PROJECTILE_FALL_SPEED;
            }

            if (value != 0 && locationComponent.getLocation().clone().subtract(0, value, 0).getBlock().getType() == Material.AIR) {
                //TODO Apply velocity here
                locationComponent.getLocation().subtract(0, value, 0);
            }
        }
    }
}
