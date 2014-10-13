package net.glowstone.io.entity;

import java.util.Map;

import net.glowstone.entity.passive.GlowRabbit;
import net.glowstone.util.nbt.CompoundTag;

import org.bukkit.Location;
import org.bukkit.entity.Rabbit;

import com.google.common.collect.ImmutableMap;

class RabbitStore extends AgeableStore<GlowRabbit>
{

    private final Map<Integer, Rabbit.RabbitType> rabbitTypeMap = ImmutableMap.<Integer, Rabbit.RabbitType>builder()
            .put(0, Rabbit.RabbitType.BROWN)
            .put(1, Rabbit.RabbitType.WHITE)
            .put(2, Rabbit.RabbitType.BLACK)
            .put(3, Rabbit.RabbitType.BLACK_AND_WHITE)
            .put(4, Rabbit.RabbitType.GOLD)
            .put(5, Rabbit.RabbitType.SALT_PEPPER)
            .put(99, Rabbit.RabbitType.KILLER)
            .build();

    @Override
    public GlowRabbit createEntity(Location location, CompoundTag compound)
    {
        return new GlowRabbit(location);
    }

    public RabbitStore()
    {
        super(GlowRabbit.class, "Rabbit");
    }

    @Override
    public void load(GlowRabbit entity, CompoundTag compound)
    {
        super.load(entity, compound);
        Rabbit.RabbitType rabbitType;
        int rabbitId = compound.getInt("RabbitType");
        if (rabbitTypeMap.containsKey(rabbitId))
        {
            rabbitType = rabbitTypeMap.get(rabbitId);
        } else
        {
            rabbitType = Rabbit.RabbitType.BROWN;
        }
        entity.setRabbitType(rabbitType);
        // TODO "MoreCarrotTicks" -> int
    }

    @Override
    public void save(GlowRabbit entity, CompoundTag tag)
    {
        super.save(entity, tag);
    }
}
