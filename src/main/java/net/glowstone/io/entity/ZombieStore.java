package net.glowstone.io.entity;

import net.glowstone.entity.monsters.GlowZombie;
import net.glowstone.util.nbt.CompoundTag;

import org.bukkit.Location;

class ZombieStore extends CreatureStore<GlowZombie> {

    private static final String BREAK_DOORS_TAG = "CanBreakDoors";
    private static final String CONVERSION_TIME_TAG = "ConversionTime";
    private static final String VILLAGER_TAG = "IsVillager";
    private static final String BABY_TAG = "IsBaby";

    public ZombieStore() {
        super(GlowZombie.class, "Zombie");
    }

    public void load(GlowZombie entity, CompoundTag compound) {
        super.load(entity, compound);
        entity.setBaby(compound.getBool(BABY_TAG));
        entity.setVillager(compound.getBool(VILLAGER_TAG));
        entity.setConversionTime(compound.getInt(CONVERSION_TIME_TAG));
        entity.setCanBreakDoors(compound.getBool(BREAK_DOORS_TAG));
    }

    public void save(GlowZombie entity, CompoundTag tag) {
        super.save(entity, tag);
        tag.putBool(BABY_TAG, entity.isBaby());
        tag.putBool(VILLAGER_TAG, entity.isVillager());
        tag.putInt(CONVERSION_TIME_TAG, entity.getConversionTime());
        tag.putBool(BREAK_DOORS_TAG, entity.canBreakDoors());
    }

    @Override
    @SuppressWarnings("unchecked")
    public GlowZombie createEntity(Location location, CompoundTag compound) {
        return new GlowZombie(location);
    }
}
