package net.glowstone.entity.monsters;

import com.artemis.ComponentMapper;
import com.google.common.base.Preconditions;
import net.glowstone.entity.GlowMonster;
import net.glowstone.entity.components.ExpirableLifeComponent;
import org.bukkit.Location;
import org.bukkit.entity.Endermite;
import org.bukkit.entity.EntityType;

public class GlowEndermite extends GlowMonster implements Endermite {

    public GlowEndermite(Location location) {
        super(location, EntityType.ENDERMITE);
        getArtemisEntity().edit()
                .add(new ExpirableLifeComponent(2400));
    }

    protected ExpirableLifeComponent getExpireableLifeComponent() {
        return ComponentMapper.getFor(ExpirableLifeComponent.class, getArtemisEntity().getWorld()).get(getArtemisEntity());
    }

    @Override
    public int getLifetime() {
        return getLifeComponent().getTicksLived();
    }

    public void setLifetime(int lifetime) {
        getLifeComponent().setTicksLived(lifetime);
    }

    public int getMaxLifetime() {
        return getExpireableLifeComponent().getMaxLife();
    }

    public void setMaxLifetime(int maxLifetime) {
        Preconditions.checkArgument(maxLifetime > 0, "Cannot have a zero or less than zero max lifetime!");
        getExpireableLifeComponent().setMaxLife(maxLifetime);
    }
}
