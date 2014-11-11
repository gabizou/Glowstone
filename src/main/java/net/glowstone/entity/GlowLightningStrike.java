package net.glowstone.entity;

import com.flowpowered.networking.Message;
import net.glowstone.entity.components.ExpirableLifeComponent;
import net.glowstone.net.message.play.entity.SpawnLightningStrikeMessage;
import net.glowstone.util.Position;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LightningStrike;

import java.util.Arrays;
import java.util.List;

/**
 * A GlowLightning strike is an entity produced during thunderstorms.
 */
public class GlowLightningStrike extends GlowWeather implements LightningStrike {

    /**
     * Whether the lightning strike is just for effect.
     */
    private boolean effect;

    public GlowLightningStrike(Location location, boolean effect) {
        super(location);
        getArtemisEntity().edit()
                .add(new ExpirableLifeComponent(30));
        this.effect = effect;
    }

    @Override
    public EntityType getType() {
        return EntityType.LIGHTNING;
    }

    @Override
    public boolean isEffect() {
        return effect;
    }

    @Override
    public List<Message> createSpawnMessage() {
        Location location = getLocation();
        int x = Position.getIntX(location);
        int y = Position.getIntY(location);
        int z = Position.getIntZ(location);
        return Arrays.<Message>asList(new SpawnLightningStrikeMessage(id, x, y, z));
    }

    @Override
    public List<Message> createUpdateMessage() {
        return Arrays.asList();
    }
    
}
