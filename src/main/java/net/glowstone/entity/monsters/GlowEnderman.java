package net.glowstone.entity.monsters;

import com.artemis.ComponentMapper;
import net.glowstone.entity.GlowMonster;
import net.glowstone.entity.components.CarriedMaterialComponent;
import org.bukkit.Location;
import org.bukkit.entity.Enderman;
import org.bukkit.entity.EntityType;
import org.bukkit.material.MaterialData;

public class GlowEnderman extends GlowMonster implements Enderman {


    public GlowEnderman(Location location) {
        super(location, EntityType.ENDERMAN);
        getArtemisEntity().edit()
                .add(new CarriedMaterialComponent());
    }

    protected CarriedMaterialComponent getCarriedMaterialComponent() {
        return ComponentMapper.getFor(CarriedMaterialComponent.class, getArtemisEntity().getWorld()).get(getArtemisEntity());
    }

    @Override
    public MaterialData getCarriedMaterial() {
        return getCarriedMaterialComponent().getMaterialData() != null ? getCarriedMaterialComponent().getMaterialData().clone() : null;
    }

    @Override
    public void setCarriedMaterial(MaterialData materialData) {
        if (materialData == null) {
            getCarriedMaterialComponent().setMaterialData(null);
        } else {
            getCarriedMaterialComponent().setMaterialData(materialData.clone());
        }
    }
}
