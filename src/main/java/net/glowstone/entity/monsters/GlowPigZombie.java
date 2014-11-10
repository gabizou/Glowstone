package net.glowstone.entity.monsters;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.PigZombie;

public class GlowPigZombie extends GlowZombie implements PigZombie {

    private int anger = 0;
    private boolean isAngry = false;

    public GlowPigZombie(Location location) {
        super(location, EntityType.PIG_ZOMBIE);
    }

    @Override
    public int getAnger() {
        return anger;
    }

    @Override
    public void setAnger(int i) {
        anger = i;
    }

    @Override
    public boolean isAngry() {
        return isAngry;
    }

    // TODO consider this to be incomplete
    @Override
    public void setAngry(boolean b) {
        isAngry = b;
    }
}
