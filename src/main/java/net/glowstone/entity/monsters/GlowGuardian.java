package net.glowstone.entity.monsters;

import com.artemis.ComponentMapper;
import net.glowstone.entity.GlowMonster;
import net.glowstone.entity.components.GuardianElderComponent;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Guardian;

public class GlowGuardian extends GlowMonster implements Guardian {


    public GlowGuardian(Location location) {
        super(location, EntityType.GUARDIAN);
        getArtemisEntity().edit()
                .add(new GuardianElderComponent());
    }

    protected GuardianElderComponent getGuardianElderComponent() {
        return ComponentMapper.getFor(GuardianElderComponent.class, getArtemisEntity().getWorld()).get(getArtemisEntity());
    }

    @Override
    public boolean isElder() {
        return this.getGuardianElderComponent().isElder();
    }

    @Override
    public void setElder(boolean isElder) {
        getGuardianElderComponent().setElder(isElder);
    }
}
