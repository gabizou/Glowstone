package net.glowstone.entity;

import com.artemis.ComponentMapper;
import com.flowpowered.networking.Message;
import net.glowstone.EventFactory;
import net.glowstone.entity.components.*;
import net.glowstone.inventory.EquipmentMonitor;
import net.glowstone.net.message.play.entity.EntityEquipmentMessage;
import org.bukkit.EntityEffect;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.BlockIterator;
import org.bukkit.util.Vector;

import java.util.*;

/**
 * A GlowLivingEntity is a {@link org.bukkit.entity.Player} or {@link org.bukkit.entity.Monster}.
 *
 * @author Graham Edgecombe.
 */
public abstract class GlowLivingEntity extends GlowEntity implements LivingEntity {

    /**
     * The magnitude of the last damage the entity took.
     */
    private double lastDamage;

    /**
     * Whether the entity should be removed if it is too distant from players.
     */
    private boolean removeDistance;

    /**
     * Whether the (non-Player) entity can pick up armor and tools.
     */
    private boolean pickupItems;

    /**
     * Monitor for the equipment of this entity.
     */
    private EquipmentMonitor equipmentMonitor = new EquipmentMonitor(this);

    /**
     * Creates a mob within the specified world.
     *
     * @param location The location.
     */
    public GlowLivingEntity(Location location) {
        super(location);
        getArtemisEntity().edit()
                .add(new HealthComponent())
                .add(new AirComponent())
                .add(new NameComponent())
                .add(new InvincibilityComponent())
                .add(new PotionEffectsComponent());
        resetMaxHealth();
        HealthComponent health = getHealthComponent();
        health.setHealth(health.getMaxHealth());
    }

    ////////////////////////////////////////////////////////////////////////////
    // Artemis getters and setters.

    protected HealthComponent getHealthComponent() {
        return ComponentMapper.getFor(HealthComponent.class, getArtemisEntity().getWorld()).get(getArtemisEntity());
    }

    protected NameComponent getNameComponent() {
        return ComponentMapper.getFor(NameComponent.class, getArtemisEntity().getWorld()).get(getArtemisEntity());
    }

    protected InvincibilityComponent getInvincibilityComponent() {
        return ComponentMapper.getFor(InvincibilityComponent.class, getArtemisEntity().getWorld()).get(getArtemisEntity());
    }

    protected AirComponent getAirComponent() {
        return ComponentMapper.getFor(AirComponent.class, getArtemisEntity().getWorld()).get(getArtemisEntity());
    }

    protected PotionEffectsComponent getPotionEffectsComponent() {
        return ComponentMapper.getFor(PotionEffectsComponent.class, getArtemisEntity().getWorld()).get(getArtemisEntity());
    }

    public int getInvincibilityTicks() {
        return getInvincibilityComponent().getInvincibilityTicks();
    }

    public void setInvincibilityTicks(int ticks) {
        getInvincibilityComponent().setInvincibilityTicks(ticks);
    }

    ////////////////////////////////////////////////////////////////////////////
    // Internals

    @Override
    public void reset() {
        super.reset();
        equipmentMonitor.resetChanges();
    }

    @Override
    public List<Message> createUpdateMessage() {
        List<Message> messages = super.createUpdateMessage();

        for (EquipmentMonitor.Entry change : equipmentMonitor.getChanges()) {
            messages.add(new EntityEquipmentMessage(id, change.slot, change.item));
        }

        return messages;
    }

    ////////////////////////////////////////////////////////////////////////////
    // Properties

    @Override
    public double getEyeHeight() {
        return 0;
    }

    @Override
    public double getEyeHeight(boolean ignoreSneaking) {
        return getEyeHeight();
    }

    @Override
    public Location getEyeLocation() {
        return getLocation().add(0, getEyeHeight(), 0);
    }

    @Override
    public Player getKiller() {
        return null;
    }

    @Override
    public boolean hasLineOfSight(Entity other) {
        return false;
    }

    @Override
    public EntityEquipment getEquipment() {
        return null;
    }

    ////////////////////////////////////////////////////////////////////////////
    // Properties


    @Override
    public double getHealth() {
        return getHealthComponent().getHealth();
    }

    @Override
    public int getNoDamageTicks() {
        return getInvincibilityComponent().getInvincibilityTicks();
    }

    @Override
    public void setNoDamageTicks(int ticks) {
        getInvincibilityComponent().setInvincibilityTicks(ticks);
    }

    @Override
    public int getMaximumNoDamageTicks() {
        return getInvincibilityComponent().getMaxInvincibilityTicks();
    }

    @Override
    public void setMaximumNoDamageTicks(int ticks) {
        getInvincibilityComponent().setMaxInvincibilityTicks(ticks);
    }

    @Override
    public int getRemainingAir() {
        return getAirComponent().getAir();
    }

    @Override
    public void setRemainingAir(int ticks) {
        getAirComponent().setAir(Math.min(ticks, getMaximumAir()));
    }

    @Override
    public int getMaximumAir() {
        return getAirComponent().getMaxAir();
    }

    @Override
    public void setMaximumAir(int ticks) {
        getAirComponent().setMaxAir(Math.max(0, ticks));
    }

    @Override
    public boolean getRemoveWhenFarAway() {
        return removeDistance;
    }

    @Override
    public void setRemoveWhenFarAway(boolean remove) {
        removeDistance = remove;
    }

    @Override
    public boolean getCanPickupItems() {
        return pickupItems;
    }

    @Override
    public void setCanPickupItems(boolean pickup) {
        pickupItems = pickup;
    }

    /**
     * Get the hurt sound of this entity, or null for silence.
     * @return the hurt sound if available
     */
    protected Sound getHurtSound() {
        return null;
    }

    /**
     * Get the death sound of this entity, or null for silence.
     * @return the death sound if available
     */
    protected Sound getDeathSound() {
        return null;
    }

    /**
     * Get whether this entity should take drowning damage.
     * @return whether this entity can drown
     */
    public boolean canDrown() {
        return true;
    }

    ////////////////////////////////////////////////////////////////////////////
    // Line of Sight

    private List<Block> getLineOfSight(HashSet<Byte> transparent, int maxDistance, int maxLength) {
        // same limit as CraftBukkit
        if (maxDistance > 120) {
            maxDistance = 120;
        }

        LinkedList<Block> blocks = new LinkedList<>();
        Iterator<Block> itr = new BlockIterator(this, maxDistance);
        while (itr.hasNext()) {
            Block block = itr.next();
            blocks.add(block);
            if (maxLength != 0 && blocks.size() > maxLength) {
                blocks.removeFirst();
            }
            int id = block.getTypeId();
            if (transparent == null) {
                if (id != 0) {
                    break;
                }
            } else {
                if (!transparent.contains((byte) id)) {
                    break;
                }
            }
        }
        return blocks;
    }

    @Override
    public List<Block> getLineOfSight(HashSet<Byte> transparent, int maxDistance) {
        return getLineOfSight(transparent, maxDistance, 0);
    }

    @Override
    public Block getTargetBlock(HashSet<Byte> transparent, int maxDistance) {
        return getLineOfSight(transparent, maxDistance, 1).get(0);
    }

    @Override
    public List<Block> getLastTwoTargetBlocks(HashSet<Byte> transparent, int maxDistance) {
        return getLineOfSight(transparent, maxDistance, 2);
    }

    ////////////////////////////////////////////////////////////////////////////
    // Projectiles

    @Override
    public Egg throwEgg() {
        return launchProjectile(Egg.class);
    }

    @Override
    public Snowball throwSnowball() {
        return launchProjectile(Snowball.class);
    }

    @Override
    public Arrow shootArrow() {
        return launchProjectile(Arrow.class);
    }

    @Override
    public <T extends Projectile> T launchProjectile(Class<? extends T> projectile) {
        return launchProjectile(projectile, getLocation().getDirection());  // todo: multiply by some speed
    }

    @Override
    public <T extends Projectile> T launchProjectile(Class<? extends T> projectile, Vector velocity) {
        T entity = world.spawn(getEyeLocation(), projectile);
        entity.setVelocity(velocity);
        return entity;
    }

    ////////////////////////////////////////////////////////////////////////////
    // Health

    @Override
    public void setHealth(double health) {
        getHealthComponent().setHealth(health);
    }

    @Override
    public void damage(double amount) {
        damage(amount, null, EntityDamageEvent.DamageCause.CUSTOM);
    }

    @Override
    public void damage(double amount, Entity source) {
        damage(amount, source, EntityDamageEvent.DamageCause.CUSTOM);
    }

    @Override
    public void damage(double amount, EntityDamageEvent.DamageCause cause) {
        damage(amount, null, cause);
    }

    @Override
    public void damage(double amount, Entity source, EntityDamageEvent.DamageCause cause) {
        // invincibility timer
        if (getInvincibilityComponent().getInvincibilityTicks() > 0 || getHealthComponent().getHealth() <= 0) {
            return;
        }

        // fire resistance
        if (cause != null && hasPotionEffect(PotionEffectType.FIRE_RESISTANCE)) {
            switch (cause) {
                case PROJECTILE:
                    if (source == null || !(source instanceof Fireball)) {
                        break;
                    }
                case FIRE:
                case FIRE_TICK:
                case LAVA:
                    return;
            }
        }

        // fire event
        // todo: use damage modifier system
        EntityDamageEvent event;
        if (source == null) {
            event = new EntityDamageEvent(this, cause, amount);
        } else {
            event = new EntityDamageByEntityEvent(source, this, cause, amount);
        }
        EventFactory.callEvent(event);
        if (event.isCancelled()) {
            return;
        }

        // apply damage
        amount = event.getFinalDamage();
        lastDamage = amount;
        setHealth(getHealth() - amount);
        playEffect(EntityEffect.HURT);

        Location location = getLocation();
        // play sounds, handle death
        if (getHealth() <= 0.0) {
            Sound deathSound = getDeathSound();
            if (deathSound != null) {
                world.playSound(location, deathSound, 1.0f, 1.0f);
            }
            // todo: drop items
        } else {
            Sound hurtSound = getHurtSound();
            if (hurtSound != null) {
                world.playSound(location, hurtSound, 1.0f, 1.0f);
            }
        }
    }

    @Override
    public double getMaxHealth() {
        return getHealthComponent().getMaxHealth();
    }

    @Override
    public void setMaxHealth(double health) {
        getHealthComponent().setMaxHealth(health);
    }

    @Override
    public void resetMaxHealth() {
        getHealthComponent().setMaxHealth(HealthComponent.DEFAULT_MAX_HEALTH);
    }

    @Override
    public double getLastDamage() {
        return lastDamage;
    }

    @Override
    public void setLastDamage(double damage) {
        lastDamage = damage;
    }

    ////////////////////////////////////////////////////////////////////////////
    // Invalid health methods

    @Override
    public void _INVALID_damage(int amount) {
        damage(amount);
    }

    @Override
    public int _INVALID_getLastDamage() {
        return (int) getLastDamage();
    }

    @Override
    public void _INVALID_setLastDamage(int damage) {
        setLastDamage(damage);
    }

    @Override
    public void _INVALID_setMaxHealth(int health) {
        setMaxHealth(health);
    }

    @Override
    public int _INVALID_getMaxHealth() {
        return (int) getMaxHealth();
    }

    @Override
    public void _INVALID_damage(int amount, Entity source) {
        damage(amount, source);
    }

    @Override
    public int _INVALID_getHealth() {
        return (int) getHealth();
    }

    @Override
    public void _INVALID_setHealth(int health) {
        setHealth(health);
    }

    ////////////////////////////////////////////////////////////////////////////
    // Potion effects

    @Override
    public boolean addPotionEffect(PotionEffect effect) {
        return addPotionEffect(effect, false);
    }

    @Override
    public boolean addPotionEffect(PotionEffect effect, boolean force) {
        if (getPotionEffectsComponent().getPotionEffects().containsKey(effect.getType())) {
            if (force) {
                removePotionEffect(effect.getType());
            } else {
                return false;
            }
        }

        getPotionEffectsComponent().getPotionEffects().put(effect.getType(), effect);

        // todo: this, updated, only players in range
        /*EntityEffectMessage msg = new EntityEffectMessage(getEntityId(), effect.getType().getId(), effect.getAmplifier(), effect.getDuration());
        for (Player player : server.getOnlinePlayers()) {
            ((GlowPlayer) player).getSession().send(msg);
        }*/
        return true;
    }

    @Override
    public boolean addPotionEffects(Collection<PotionEffect> effects) {
        boolean result = true;
        for (PotionEffect effect : effects) {
            if (!addPotionEffect(effect)) {
                result = false;
            }
        }
        return result;
    }

    @Override
    public boolean hasPotionEffect(PotionEffectType type) {
        return getPotionEffectsComponent().getPotionEffects().containsKey(type);
    }

    @Override
    public void removePotionEffect(PotionEffectType type) {
        if (!hasPotionEffect(type)) {
            return;
        }
        getPotionEffectsComponent().getPotionEffects().remove(type);

        // todo: this, improved, for players in range
        /*EntityRemoveEffectMessage msg = new EntityRemoveEffectMessage(getEntityId(), type.getId());
        for (Player player : server.getOnlinePlayers()) {
            ((GlowPlayer) player).getSession().send(msg);
        }*/
    }

    @Override
    public Collection<PotionEffect> getActivePotionEffects() {
        return Collections.unmodifiableCollection(getPotionEffectsComponent().getPotionEffects().values());
    }

    ////////////////////////////////////////////////////////////////////////////
    // Custom name

    @Override
    public String getCustomName() {
        return getNameComponent().getCustomName();
    }

    @Override
    public void setCustomName(String name) {
        getNameComponent().setCustomName(name);
    }

    @Override
    public boolean isCustomNameVisible() {
        return getNameComponent().isCustomNameVisible();
    }

    @Override
    public void setCustomNameVisible(boolean flag) {
        getNameComponent().setCustomNameVisible(flag);
    }

    ////////////////////////////////////////////////////////////////////////////
    // Leashes

    @Override
    public boolean isLeashed() {
        return false;
    }

    @Override
    public Entity getLeashHolder() throws IllegalStateException {
        return null;
    }

    @Override
    public boolean setLeashHolder(Entity holder) {
        return false;
    }
}
