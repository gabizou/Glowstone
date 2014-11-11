package net.glowstone.entity.passive;

import com.artemis.ComponentMapper;
import com.flowpowered.networking.Message;
import net.glowstone.entity.components.OcelotSpeciesComponent;
import net.glowstone.entity.meta.MetadataIndex;
import net.glowstone.entity.meta.MetadataMap;
import net.glowstone.net.message.play.entity.EntityMetadataMessage;
import org.bukkit.Location;
import org.bukkit.entity.AnimalTamer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Ocelot;

import java.util.List;

public class GlowOcelot extends GlowTameable implements Ocelot {
    
    protected GlowOcelot(Location location, AnimalTamer owner) {
        super(location, EntityType.OCELOT, owner);
        getArtemisEntity().edit()
                .add(new OcelotSpeciesComponent(Type.WILD_OCELOT));
    }

    public GlowOcelot(Location location) {
        this(location, null);
    }
    
    protected OcelotSpeciesComponent getSpeciesComponent() {
        return ComponentMapper.getFor(OcelotSpeciesComponent.class, getArtemisEntity().getWorld()).get(getArtemisEntity());
    }

    @Override
    public Type getCatType() {
        return getSpeciesComponent().getOcelotType();
    }

    @Override
    public void setCatType(Type type) {
        this.getSpeciesComponent().setOcelotType(type);
    }

    @Override
    public boolean isSitting() {
        return getTameableComponent().isSitting();
    }

    @Override
    public void setSitting(boolean sitting) {
        this.getTameableComponent().setSitting(sitting);
    }

    @Override
    public List<Message> createSpawnMessage() {
        List<Message> messages = super.createSpawnMessage();
        MetadataMap map = this.getMetadataComponent().getMetadata();
        map.set(MetadataIndex.OCELOT_TYPE, (byte) this.getCatType().ordinal());
        messages.add(new EntityMetadataMessage(id, map.getEntryList()));
        return messages;
    }
}
