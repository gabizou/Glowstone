package net.glowstone.entity.monsters;

import com.artemis.ComponentMapper;
import net.glowstone.entity.GlowMonster;
import net.glowstone.entity.components.ZombieComponent;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Zombie;

public class GlowZombie extends GlowMonster implements Zombie {
    
    public GlowZombie(Location location) {
        this(location, EntityType.ZOMBIE);
    }
    protected GlowZombie(Location location, EntityType type) {
        super(location, type);
        getArtemisEntity().edit()
                .add(new ZombieComponent());
    }

    protected ZombieComponent getZombieComponent() {
        return ComponentMapper.getFor(ZombieComponent.class, getArtemisEntity().getWorld()).get(getArtemisEntity());
    }

    @Override
    public boolean isBaby() {
        return getZombieComponent().isBaby();
    }

    @Override
    public void setBaby(boolean isBaby) {
        getZombieComponent().setBaby(isBaby);
    }

    @Override
    public boolean isVillager() {
        return getZombieComponent().isVillager();
    }

    @Override
    public void setVillager(boolean isVillager) {
        getZombieComponent().setVillager(isVillager);
    }

    public int getConversionTime() {
        return getZombieComponent().getConversionTime();
    }

    public void setConversionTime(int time) {
        getZombieComponent().setConversionTime(time);
    }

    public boolean canBreakDoors() {
        return getZombieComponent().isCanBreakDoors();
    }

    public void setCanBreakDoors(boolean canBreakDoors) {
        getZombieComponent().setCanBreakDoors(canBreakDoors);
    }
}
