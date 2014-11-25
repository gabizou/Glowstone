package net.glowstone.entity.monsters;

import com.artemis.ComponentMapper;
import net.glowstone.entity.GlowMonster;
import net.glowstone.entity.components.SkeletonTypeComponent;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Skeleton;

public class GlowSkeleton extends GlowMonster implements Skeleton {


    public GlowSkeleton(Location location) {
        super(location, EntityType.SKELETON);
        getArtemisEntity().edit()
                .add(new SkeletonTypeComponent(SkeletonType.NORMAL));
    }

    protected SkeletonTypeComponent getSkeletonTypeComponent() {
        return ComponentMapper.getFor(SkeletonTypeComponent.class, getArtemisEntity().getWorld()).get(getArtemisEntity());
    }

    @Override
    public SkeletonType getSkeletonType() {
        return getSkeletonTypeComponent().getSkeletonType();
    }

    @Override
    public void setSkeletonType(SkeletonType skeletonType) {
        getSkeletonTypeComponent().setSkeletonType(skeletonType);
    }
}
