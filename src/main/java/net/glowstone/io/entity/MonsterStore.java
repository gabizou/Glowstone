package net.glowstone.io.entity;

import net.glowstone.entity.GlowMonster;
import net.glowstone.util.nbt.CompoundTag;

abstract class MonsterStore<T extends GlowMonster> extends CreatureStore<T> {

    public MonsterStore(Class<T> clazz, String id) {
        super(clazz, id);
    }

    public void load(T entity, CompoundTag compound) {
        super.load(entity, compound);
    }

    public void save(T entity, CompoundTag tag) {
        super.save(entity, tag);
    }
}
