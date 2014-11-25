package net.glowstone.entity.monsters;

import com.artemis.ComponentMapper;
import com.flowpowered.networking.Message;
import net.glowstone.entity.GlowLivingEntity;
import net.glowstone.entity.components.SlimeSizeComponent;
import net.glowstone.entity.meta.MetadataIndex;
import net.glowstone.entity.meta.MetadataMap;
import net.glowstone.net.message.play.entity.EntityHeadRotationMessage;
import net.glowstone.net.message.play.entity.EntityMetadataMessage;
import net.glowstone.net.message.play.entity.SpawnMobMessage;
import net.glowstone.util.Position;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Slime;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class GlowSlime extends GlowLivingEntity implements Slime {

    private EntityType type;

    protected GlowSlime(Location location, EntityType type) {
        this(location);
        this.type = type;
    }

    public GlowSlime(Location location) {
        super(location);
        this.type = EntityType.SLIME;
        getArtemisEntity().edit()
                .add(new SlimeSizeComponent(new Random().nextInt(4)));
    }

    protected SlimeSizeComponent getSlimeSizeComponent() {
        return ComponentMapper.getFor(SlimeSizeComponent.class, getArtemisEntity().getWorld()).get(getArtemisEntity());
    }

    @Override
    public List<Message> createSpawnMessage() {
        List<Message> result = new LinkedList<>();

        Location location = getLocation();
        MetadataMap map = getMetadataComponent().getMetadata();
        // spawn mob
        int x = Position.getIntX(location);
        int y = Position.getIntY(location);
        int z = Position.getIntZ(location);
        int yaw = Position.getIntYaw(location);
        int pitch = Position.getIntPitch(location);
        result.add(new SpawnMobMessage(id, type.getTypeId(), x, y, z, yaw, pitch, pitch, 0, 0, 0, map.getEntryList()));
        result.add(new EntityHeadRotationMessage(id, yaw));
        map.set(MetadataIndex.SLIME_SIZE, this.getSize());
        result.add(new EntityMetadataMessage(id, map.getEntryList()));
        return result;
    }

    @Override
    public int getSize() {
        return getSlimeSizeComponent().getSize();
    }

    @Override
    public void setSize(int i) {
        getSlimeSizeComponent().setSize(i);
    }

    @Override
    public EntityType getType() {
        return type;
    }
}
