package net.glowstone.io.entity;

import net.glowstone.entity.GlowAgeable;
import net.glowstone.entity.GlowCreature;
import net.glowstone.entity.animals.GlowCow;
import net.glowstone.util.nbt.CompoundTag;

import org.bukkit.Location;
import org.bukkit.entity.Cow;

class AgeableStore<T extends GlowAgeable> extends CreatureStore<T> {

    public AgeableStore(Class<T> clazz, String id) {
        super(clazz, id);
    }

    @Override
    @SuppressWarnings("Unchecked")
    public T createEntity(Location location, CompoundTag compound) {
        if (Cow.class.isAssignableFrom(clazz)) {
            return (T) new GlowCow(location);
        } else
            throw new UnsupportedOperationException("Not implemented yet.");
    }

    public void load(T entity, CompoundTag compound) {
        super.load(entity, compound);
        entity.setAge(compound.getInt("Age"));
        entity.setAgeLock(compound.getBool("AgeLocked"));
    }

    public void save(T entity, CompoundTag tag) {
        super.save(entity, tag);
        tag.putInt("Age", entity.getAge());
        tag.putBool("AgeLocked", entity.getAgeLock());
    }
}
