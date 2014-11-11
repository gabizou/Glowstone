package net.glowstone.entity.passive;

import com.artemis.ComponentMapper;
import com.flowpowered.networking.Message;
import net.glowstone.entity.GlowAnimal;
import net.glowstone.entity.components.SaddledComponent;
import net.glowstone.entity.meta.MetadataIndex;
import net.glowstone.entity.meta.MetadataMap;
import net.glowstone.net.message.play.entity.EntityMetadataMessage;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Pig;

import java.util.List;

public class GlowPig extends GlowAnimal implements Pig {

    private boolean hasSaddle;

    public GlowPig(Location location) {
        super(location, EntityType.PIG);
        getArtemisEntity().edit()
                .add(new SaddledComponent(false));
        setSize(0.9F, 0.9F);
    }

    protected SaddledComponent getSaddledComponent() {
        return ComponentMapper.getFor(SaddledComponent.class, getArtemisEntity().getWorld()).get(getArtemisEntity());
    }

    @Override
    public boolean hasSaddle() {
        return getSaddledComponent().isHasSaddle();
    }

    @Override
    public void setSaddle(boolean hasSaddle) {
        this.getSaddledComponent().setHasSaddle(hasSaddle);
    }

    @Override
    public List<Message> createSpawnMessage() {
        List<Message> messages = super.createSpawnMessage();
        MetadataMap map = getMetadataComponent().getMetadata();
        map.set(MetadataIndex.PIG_SADDLE, (byte) (this.hasSaddle() ? 1 : 0));
        messages.add(new EntityMetadataMessage(id, map.getEntryList()));
        return messages;
    }
}
