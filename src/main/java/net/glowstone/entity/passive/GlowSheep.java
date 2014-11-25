package net.glowstone.entity.passive;

import com.artemis.ComponentMapper;
import com.flowpowered.networking.Message;
import net.glowstone.entity.GlowAnimal;
import net.glowstone.entity.components.DyeableComponent;
import net.glowstone.entity.components.ShearableComponent;
import net.glowstone.entity.meta.MetadataIndex;
import net.glowstone.entity.meta.MetadataMap;
import net.glowstone.net.message.play.entity.EntityMetadataMessage;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Sheep;

import java.util.List;

public class GlowSheep extends GlowAnimal implements Sheep {

    private boolean sheared = false;
    private DyeColor color = DyeColor.WHITE;

    public GlowSheep(Location location) {
        super(location, EntityType.SHEEP);
        getArtemisEntity().edit()
                .add(new DyeableComponent(DyeColor.WHITE))
                .add(new ShearableComponent());
        setSize(0.9F, 1.3F);
    }

    protected DyeableComponent getDyeableComponent() {
        return ComponentMapper.getFor(DyeableComponent.class, getArtemisEntity().getWorld()).get(getArtemisEntity());
    }

    protected ShearableComponent getShearableComponent() {
        return ComponentMapper.getFor(ShearableComponent.class, getArtemisEntity().getWorld()).get(getArtemisEntity());
    }

    @Override
    public boolean isSheared() {
        return getShearableComponent().isSheared();
    }

    @Override
    public void setSheared(boolean sheared) {
        getShearableComponent().setSheared(sheared);
    }

    @Override
    public DyeColor getColor() {
        return getDyeableComponent().getDyeColor();
    }

    @Override
    public void setColor(DyeColor dyeColor) {
        getDyeableComponent().setDyeColor(dyeColor);
    }

    @Override
    public List<Message> createSpawnMessage() {
        List<Message> messages = super.createSpawnMessage();
        MetadataMap map = getMetadataComponent().getMetadata();
        map.set(MetadataIndex.SHEEP_DATA, getColorByte());
        messages.add(new EntityMetadataMessage(id, map.getEntryList()));
        return messages;
    }

    private byte getColorByte() {
        return (byte) (this.getColor().getData() & (isSheared() ? 0x10 : 0x0F));
    }
}
