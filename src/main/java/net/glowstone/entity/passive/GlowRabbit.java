package net.glowstone.entity.passive;

import com.artemis.ComponentMapper;
import com.flowpowered.networking.Message;
import com.google.common.collect.ImmutableBiMap;
import net.glowstone.entity.GlowAnimal;
import net.glowstone.entity.components.RabbitSpeciesComponent;
import net.glowstone.entity.meta.MetadataIndex;
import net.glowstone.entity.meta.MetadataMap;
import net.glowstone.net.message.play.entity.EntityMetadataMessage;
import org.apache.commons.lang.Validate;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Rabbit;

import java.util.List;
import java.util.Random;

public class GlowRabbit extends GlowAnimal implements Rabbit {


    private static final ImmutableBiMap<Integer, RabbitType> rabbitTypeMap = ImmutableBiMap.<Integer, Rabbit.RabbitType>builder()
            .put(0, Rabbit.RabbitType.BROWN)
            .put(1, Rabbit.RabbitType.WHITE)
            .put(2, Rabbit.RabbitType.BLACK)
            .put(3, Rabbit.RabbitType.BLACK_AND_WHITE)
            .put(4, Rabbit.RabbitType.GOLD)
            .put(5, Rabbit.RabbitType.SALT_PEPPER)
            .put(99, Rabbit.RabbitType.KILLER)
            .build();
    
    public GlowRabbit(Location location) {
        super(location, EntityType.RABBIT);
        getArtemisEntity().edit()
                .add(new RabbitSpeciesComponent(RabbitType.values()[new Random().nextInt(6)]));
        setSize(0.3F, 0.7F);
    }

    protected RabbitSpeciesComponent getRabbitComponent() {
        return ComponentMapper.getFor(RabbitSpeciesComponent.class, getArtemisEntity().getWorld()).get(getArtemisEntity());
    }

    @Override
    public RabbitType getRabbitType() {
        return this.getRabbitComponent().getRabbitType();
    }

    @Override
    public void setRabbitType(RabbitType type) {
        Validate.notNull(type, "Cannot set a null rabbit type!");
        this.getRabbitComponent().setRabbitType(type);
    }

    @Override
    public List<Message> createSpawnMessage() {
        List<Message> messages = super.createSpawnMessage();
        MetadataMap map = new MetadataMap(GlowRabbit.class);
        map.set(MetadataIndex.RABBIT_TYPE, rabbitTypeMap.inverse().get(this.getRabbitType()).byteValue());
        messages.add(new EntityMetadataMessage(id, map.getEntryList()));
        return messages;
    }
}
