package net.glowstone.entity.monsters;

import com.artemis.ComponentMapper;
import net.glowstone.entity.GlowMonster;
import net.glowstone.entity.components.PoweredCreeperComponent;
import org.bukkit.Location;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.EntityType;

public class GlowCreeper extends GlowMonster implements Creeper {

    private short fuse;
    private int explosionRadius;
    private boolean ignited;

    public GlowCreeper(Location location) {
        super(location, EntityType.CREEPER);
        getArtemisEntity().edit()
                .add(new PoweredCreeperComponent());
    }

    protected PoweredCreeperComponent getPoweredCreeperComponent() {
        return ComponentMapper.getFor(PoweredCreeperComponent.class, getArtemisEntity().getWorld()).get(getArtemisEntity());
    }

    @Override
    public boolean isPowered() {
        return getPoweredCreeperComponent().isPowered();
    }

    @Override
    public void setPowered(boolean isPowered) {
        getPoweredCreeperComponent().setPowered(isPowered);
    }

    public short getFuse() {
        return fuse;
    }

    public void setFuse(short fuse) {
        this.fuse = fuse;
    }

    public int getExplosionRadius() {
        return explosionRadius;
    }

    public void setExplosionRadius(int explosionRadius) {
        this.explosionRadius = explosionRadius;
    }

    public boolean isIgnited() {
        return ignited;
    }

    public void setIgnited(boolean isIgnited) {
        this.ignited = isIgnited;
    }
}
