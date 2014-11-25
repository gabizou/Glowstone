package net.glowstone.entity.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.artemis.systems.EntityProcessingSystem;
import com.flowpowered.networking.Message;
import net.glowstone.entity.GlowEntity;
import net.glowstone.entity.GlowPlayer;
import net.glowstone.entity.components.GlowEntityComponent;
import net.glowstone.entity.components.MetadataComponent;
import net.glowstone.entity.components.PlayerComponent;
import net.glowstone.entity.meta.MetadataMap;
import net.glowstone.inventory.InventoryMonitor;
import net.glowstone.net.message.play.entity.DestroyEntitiesMessage;
import net.glowstone.net.message.play.entity.EntityMetadataMessage;
import org.bukkit.Statistic;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

@Wire
public class PlayerSystem extends EntityProcessingSystem {

    private ComponentMapper<GlowEntityComponent> entityMapper;
    private ComponentMapper<MetadataComponent> metadataMapper;

    public PlayerSystem() {
        super(Aspect.getAspectForAll(PlayerComponent.class, GlowEntityComponent.class));
    }

    @Override
    protected void process(Entity e) {
        GlowEntityComponent component = entityMapper.get(e);
        GlowPlayer entityPlayer = (GlowPlayer) component.getEntity();
        MetadataComponent metadataComponent = metadataMapper.get(e);
        // stream world
        entityPlayer.streamBlocks();
        entityPlayer.processBlockChanges();

        // add to playtime
        entityPlayer.incrementStatistic(Statistic.PLAY_ONE_TICK);

        // update inventory
        for (InventoryMonitor.Entry entry : entityPlayer.getInvMonitor().getChanges()) {
            entityPlayer.sendItemChange(entry.slot, entry.item);
        }

        MetadataMap metadata = metadataComponent.getMetadata();
        // send changed metadata
        List<MetadataMap.Entry> changes = metadata.getChanges();
        if (changes.size() > 0) {
            entityPlayer.getSession().send(new EntityMetadataMessage(GlowPlayer.SELF_ID, changes));
        }

        // update or remove entities
        List<Integer> destroyIds = new LinkedList<>();
        for (Iterator<GlowEntity> it = entityPlayer.getKnownEntities().iterator(); it.hasNext(); ) {
            GlowEntity entity = it.next();
            boolean withinDistance = !entity.isDead() && entityPlayer.isWithinDistance(entity);

            if (withinDistance) {
                for (Message msg : entity.createUpdateMessage()) {
                    entityPlayer.getSession().send(msg);
                }
            } else {
                destroyIds.add(entity.getEntityId());
                it.remove();
            }
        }
        if (destroyIds.size() > 0) {
            entityPlayer.getSession().send(new DestroyEntitiesMessage(destroyIds));
        }

        // add entities
        for (GlowEntity entity : entityPlayer.getWorld().getEntityManager()) {
            if (entity == entityPlayer) {
                continue;
            }
            boolean withinDistance = !entity.isDead() && entityPlayer.isWithinDistance(entity);

            if (withinDistance && !entityPlayer.getKnownEntities().contains(entity) && !entityPlayer.getHiddenEntities().contains(entity.getUniqueId())) {
                entityPlayer.getKnownEntities().add(entity);
                for (Message msg : entity.createSpawnMessage()) {
                    entityPlayer.getSession().send(msg);
                }
            }
        }
    }
}
