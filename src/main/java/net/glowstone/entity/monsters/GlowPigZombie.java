package net.glowstone.entity.monsters;

import com.artemis.ComponentMapper;
import net.glowstone.entity.components.ExpirableAngerComponent;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.PigZombie;

import java.util.Random;

public class GlowPigZombie extends GlowZombie implements PigZombie {

    public GlowPigZombie(Location location) {
        super(location, EntityType.PIG_ZOMBIE);
        getArtemisEntity().edit()
                .add(new ExpirableAngerComponent());
    }

    protected ExpirableAngerComponent getExpirableAngerComponent() {
        return ComponentMapper.getFor(ExpirableAngerComponent.class, getArtemisEntity().getWorld()).get(getArtemisEntity());
    }

    @Override
    public int getAnger() {
        return getExpirableAngerComponent().getAnger();
    }

    @Override
    public void setAnger(int i) {
        getExpirableAngerComponent().setAnger(i);
    }

    @Override
    public boolean isAngry() {
        return getExpirableAngerComponent().isAngry();
    }

    // TODO consider this to be incomplete
    @Override
    public void setAngry(boolean b) {
        if (b) {
            getExpirableAngerComponent().setAnger(400 + new Random().nextInt(400));
        } else {
            getExpirableAngerComponent().setAnger(0);
        }
    }
}
