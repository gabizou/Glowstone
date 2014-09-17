package net.glowstone.entity;

import java.util.LinkedList;
import java.util.List;

import com.flowpowered.networking.Message;
import net.glowstone.net.message.play.entity.EntityHeadRotationMessage;
import net.glowstone.net.message.play.entity.SpawnMobMessage;
import net.glowstone.util.Position;

import org.bukkit.Location;
import org.bukkit.entity.Creature;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;

/**
 * Represents a monster such as a creeper.
 * @author Graham Edgecombe
 */
public class GlowMonster extends GlowCreature implements Monster {

    /**
     * Creates a new monster.
     * @param location The location of the monster.
     * @param type The type of monster.
     */
    public GlowMonster(Location location, EntityType type) {
        super(location, type);
    }
}
