package net.glowstone.entity.passive;

import com.artemis.ComponentMapper;
import com.flowpowered.networking.Message;
import net.glowstone.entity.GlowAmbient;
import net.glowstone.entity.components.BatAwakeComponent;
import net.glowstone.entity.meta.MetadataIndex;
import net.glowstone.entity.meta.MetadataMap;
import net.glowstone.net.message.play.entity.EntityHeadRotationMessage;
import net.glowstone.net.message.play.entity.EntityMetadataMessage;
import net.glowstone.net.message.play.entity.SpawnMobMessage;
import net.glowstone.util.Position;
import org.bukkit.Location;
import org.bukkit.entity.Bat;
import org.bukkit.entity.EntityType;

import java.util.LinkedList;
import java.util.List;

public class GlowBat extends GlowAmbient implements Bat {

    public GlowBat(Location location) {
        super(location);
        getArtemisEntity().edit()
                .add(new BatAwakeComponent());
    }

    protected BatAwakeComponent getBatAwakeComponent() {
        return ComponentMapper.getFor(BatAwakeComponent.class, getArtemisEntity().getWorld()).get(getArtemisEntity());
    }

    @Override
    public List<Message> createSpawnMessage() {
        List<Message> result = new LinkedList<>();

        Location location = getLocation();
        MetadataMap metadata = getMetadataComponent().getMetadata();
        // spawn mob
        int x = Position.getIntX(location);
        int y = Position.getIntY(location);
        int z = Position.getIntZ(location);
        int yaw = Position.getIntYaw(location);
        int pitch = Position.getIntPitch(location);
        result.add(new SpawnMobMessage(id, getType().getTypeId(), x, y, z, yaw, pitch, pitch, 0, 0, 0, metadata.getEntryList()));

        // head facing
        result.add(new EntityHeadRotationMessage(id, yaw));
        metadata.set(MetadataIndex.BAT_HANGING, (byte) (this.isAwake() ? 1 : 0));
        result.add(new EntityMetadataMessage(id, metadata.getEntryList()));
        return result;
    }

    @Override
    public boolean isAwake() {
        return getBatAwakeComponent().isAwake();
    }

    @Override
    public void setAwake(boolean isAwake) {
        getBatAwakeComponent().setAwake(isAwake);
    }

    @Override
    public EntityType getType() {
        return EntityType.BAT;
    }
}
