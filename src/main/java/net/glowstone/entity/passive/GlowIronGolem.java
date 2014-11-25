package net.glowstone.entity.passive;

import com.artemis.ComponentMapper;
import com.flowpowered.networking.Message;
import net.glowstone.entity.GlowGolem;
import net.glowstone.entity.components.IsPlayerCreatedComponent;
import net.glowstone.entity.meta.MetadataIndex;
import net.glowstone.entity.meta.MetadataMap;
import net.glowstone.net.message.play.entity.EntityMetadataMessage;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.IronGolem;

import java.util.List;

public class GlowIronGolem extends GlowGolem implements IronGolem {

    public GlowIronGolem(Location location) {
        super(location, EntityType.IRON_GOLEM);
        getArtemisEntity().edit()
                .add(new IsPlayerCreatedComponent(false));
    }

    protected IsPlayerCreatedComponent getPlayerCreatedComponent() {
        return ComponentMapper.getFor(IsPlayerCreatedComponent.class, getArtemisEntity().getWorld()).get(getArtemisEntity());
    }

    @Override
    public boolean isPlayerCreated() {
        return this.getPlayerCreatedComponent().isPlayerCreated();
    }

    @Override
    public void setPlayerCreated(boolean playerCreated) {
        this.getPlayerCreatedComponent().setPlayerCreated(playerCreated);
    }

    @Override
    public List<Message> createSpawnMessage() {
        List<Message> messages = super.createSpawnMessage();
        MetadataMap map = getMetadataComponent().getMetadata();
        map.set(MetadataIndex.GOLEM_PLAYER_BUILT, (byte) (this.isPlayerCreated() ? 1 : 0));
        messages.add(new EntityMetadataMessage(id, map.getEntryList()));
        return messages;
    }
}
