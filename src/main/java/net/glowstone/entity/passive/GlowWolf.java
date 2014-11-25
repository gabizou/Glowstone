package net.glowstone.entity.passive;

import com.artemis.ComponentMapper;
import com.flowpowered.networking.Message;
import net.glowstone.entity.components.BooleanAngerComponent;
import net.glowstone.entity.components.DyeableComponent;
import net.glowstone.entity.meta.MetadataIndex;
import net.glowstone.entity.meta.MetadataMap;
import net.glowstone.net.message.play.entity.EntityMetadataMessage;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.entity.AnimalTamer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Wolf;

import java.util.List;

public class GlowWolf extends GlowTameable implements Wolf {

    private boolean angry = false;
    private boolean sitting = false;
    private DyeColor collarColor = DyeColor.RED;

    public GlowWolf(Location location) {
        this(location, null);
    }

    protected GlowWolf(Location location, AnimalTamer owner) {
        super(location, EntityType.WOLF, owner);
        getArtemisEntity().edit()
                .add(new DyeableComponent(DyeColor.RED))
                .add(new BooleanAngerComponent());
    }

    protected DyeableComponent getDyeableComponent() {
        return ComponentMapper.getFor(DyeableComponent.class, getArtemisEntity().getWorld()).get(getArtemisEntity());
    }

    protected BooleanAngerComponent getAngerComponent() {
        return ComponentMapper.getFor(BooleanAngerComponent.class, getArtemisEntity().getWorld()).get(getArtemisEntity());
    }

    @Override
    public boolean isAngry() {
        return getAngerComponent().isAngry();
    }

    @Override
    public void setAngry(boolean angry) {
        getAngerComponent().setAngry(angry);
    }

    @Override
    public boolean isSitting() {
        return getTameableComponent().isSitting();
    }

    @Override
    public void setSitting(boolean sitting) {
        getTameableComponent().setSitting(sitting);
    }

    @Override
    public DyeColor getCollarColor() {
        return this.getDyeableComponent().getDyeColor();
    }

    @Override
    public void setCollarColor(DyeColor dyeColor) {
        this.getDyeableComponent().setDyeColor(dyeColor);
    }

    @Override
    public List<Message> createSpawnMessage() {
        List<Message> messages = super.createSpawnMessage();
        MetadataMap map = getMetadataComponent().getMetadata();
        map.set(MetadataIndex.WOLF_COLOR, getColorByte());
        messages.add(new EntityMetadataMessage(id, map.getEntryList()));
        return messages;
    }


    private byte getColorByte() {
        return (byte) (this.getCollarColor().getData());
    }
}
