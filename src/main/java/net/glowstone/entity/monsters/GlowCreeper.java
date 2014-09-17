package net.glowstone.entity.monsters;

import net.glowstone.entity.GlowMonster;

import org.bukkit.Location;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.EntityType;

public class GlowCreeper extends GlowMonster implements Creeper {

    private boolean isPowered = false;


    public GlowCreeper(Location location) {
        super(location, EntityType.CREEPER);
    }

    @Override
    public boolean isPowered() {
        return isPowered;
    }

    @Override
    public void setPowered(boolean isPowered) {
        this.isPowered = isPowered;
    }
}
