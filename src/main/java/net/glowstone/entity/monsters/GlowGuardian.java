package net.glowstone.entity.monsters;

import net.glowstone.entity.GlowMonster;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Guardian;

public class GlowGuardian extends GlowMonster implements Guardian {

    private GuardianType guardianType = GuardianType.NORMAL;

    public GlowGuardian(Location location) {
        super(location, EntityType.GUARDIAN);
    }

    @Override
    public GuardianType getGuardianType() {
        return guardianType;
    }

    @Override
    public void setGuardianType(GuardianType type) {
        this.guardianType = type;
    }
}
