package net.glowstone.entity.passive;

import com.artemis.ComponentMapper;
import net.glowstone.entity.GlowAnimal;
import net.glowstone.entity.components.TameableComponent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.AnimalTamer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Tameable;

import java.util.UUID;

public abstract class GlowTameable extends GlowAnimal implements Tameable {

    public GlowTameable(Location location, EntityType type) {
        this(location, type, null);
    }

    protected GlowTameable(Location location, EntityType type, AnimalTamer owner) {
        super(location, type);
        getArtemisEntity().edit()
                .add(new TameableComponent(owner != null ? owner.getUniqueId() : null, false, false));
    }

    ////////////////////////////////////////////////////////////////////////////
    // Artemis getters and setters.
    protected TameableComponent getTameableComponent() {
        return ComponentMapper.getFor(TameableComponent.class, getArtemisEntity().getWorld()).get(getArtemisEntity());
    }

    @Override
    public boolean isTamed() {
        return getTameableComponent().isTamed();
    }

    @Override
    public void setTamed(boolean isTamed) {
        getTameableComponent().setTamed(isTamed);
    }

    @Override
    public AnimalTamer getOwner() {
        return Bukkit.getPlayer(this.getTameableComponent().getOwnerUniqueId());
    }

    @Override
    public void setOwner(AnimalTamer animalTamer) {
        getTameableComponent().setOwnerUniqueId(animalTamer.getUniqueId());
    }

    public UUID getOwnerUUID() {
        return this.getTameableComponent().getOwnerUniqueId();
    }

    /**
     * Added needed method for Storage to convert from UUID to owners.
     * The UUID's are validated through offline player checking. If a player
     * with the specified UUID has not played on the server before, the
     * owner is not set.
     *
     * @param ownerUUID The UUID of the owner.
     */
    public void setOwnerUUID(UUID ownerUUID) {
        TameableComponent component = getTameableComponent();
        OfflinePlayer player = Bukkit.getOfflinePlayer(ownerUUID);
        if (player.hasPlayedBefore()) {
            component.setOwnerUniqueId(ownerUUID);
        }
    }
}
