package net.glowstone.entity.passive;

import com.artemis.ComponentMapper;
import net.glowstone.entity.GlowAgeable;
import net.glowstone.entity.components.MerchantProfessionComponent;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Villager;

import java.util.Random;

public class GlowVillager extends GlowAgeable implements Villager {
    
    public GlowVillager(Location location) {
        super(location, EntityType.VILLAGER);
        getArtemisEntity().edit()
                .add(new MerchantProfessionComponent(Profession.values()[new Random().nextInt(4)]));
    }

    protected MerchantProfessionComponent getMerchantComponent() {
        return ComponentMapper.getFor(MerchantProfessionComponent.class, getArtemisEntity().getWorld()).get(getArtemisEntity());
    }

    @Override
    public Profession getProfession() {
        return getMerchantComponent().getProfession();
    }

    @Override
    public void setProfession(Profession profession) {
        getMerchantComponent().setProfession(profession);
    }
}
