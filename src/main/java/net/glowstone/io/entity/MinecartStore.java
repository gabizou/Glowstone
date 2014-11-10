package net.glowstone.io.entity;

import net.glowstone.entity.minecart.GlowAbstractMinecart;

public abstract class MinecartStore<T extends GlowAbstractMinecart> extends EntityStore<T> {

    public MinecartStore(Class<T> clazz, String id) {
        super(clazz, id);
    }
}
