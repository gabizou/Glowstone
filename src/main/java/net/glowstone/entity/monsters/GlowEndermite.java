package net.glowstone.entity.monsters;

import net.glowstone.entity.GlowMonster;

import org.bukkit.Location;
import org.bukkit.entity.Endermite;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Giant;

public class GlowEndermite extends GlowMonster implements Endermite {


    public GlowEndermite(Location location) {
        super(location, EntityType.ENDERMITE);
    }
}
