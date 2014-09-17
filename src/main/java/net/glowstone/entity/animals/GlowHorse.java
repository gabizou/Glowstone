package net.glowstone.entity.animals;

import java.util.UUID;

import net.glowstone.inventory.GlowHorseInventory;

import org.bukkit.Location;
import org.bukkit.entity.AnimalTamer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;
import org.bukkit.inventory.HorseInventory;

public class GlowHorse extends GlowTameable implements Horse {

    private Variant variant;

    private Color horseColor;

    private Style horseStyle;

    private boolean hasChest;

    private int domestication;

    private int maxDomestication;

    private double jumpStrength;

    private boolean eatingHay;

    private boolean hasReproduced;

    private int temper;

    private UUID ownerUUID;

    private HorseInventory inventory = new GlowHorseInventory(this);

    /**
     * Creates a new tamed animal.
     *
     * @param location The location of the animal
     */
    public GlowHorse(Location location) {
        super(location, EntityType.HORSE);
    }

    /**
     * Creates a new tamed animal.
     *
     * @param location The location of the animal
     * @param owner    The owner of the animal
     */
    protected GlowHorse(Location location, AnimalTamer owner) {
        super(location, EntityType.HORSE, owner);
        this.ownerUUID = owner.getUniqueId();
    }

    @Override
    public Variant getVariant() {
        return this.variant;
    }

    @Override
    public void setVariant(Variant variant) {
        this.variant = variant;
    }

    @Override
    public Color getColor() {
        return horseColor;
    }

    @Override
    public void setColor(Color color) {
        this.horseColor = color;
    }

    @Override
    public Style getStyle() {
        return horseStyle;
    }

    @Override
    public void setStyle(Style style) {
        this.horseStyle = style;
    }

    @Override
    public boolean isCarryingChest() {
        return hasChest;
    }

    @Override
    public void setCarryingChest(boolean b) {
        this.hasChest = b;
    }

    @Override
    public int getDomestication() {
        return domestication;
    }

    @Override
    public void setDomestication(int i) {
        this.domestication = i;
    }

    @Override
    public int getMaxDomestication() {
        return maxDomestication;
    }

    @Override
    public void setMaxDomestication(int i) {
        this.maxDomestication = i;
    }

    @Override
    public double getJumpStrength() {
        return jumpStrength;
    }

    @Override
    public void setJumpStrength(double v) {
        this.jumpStrength = v;
    }

    @Override
    public HorseInventory getInventory() {
        return inventory;
    }


    public boolean isEatingHay() {
        return eatingHay;
    }

    public void setEatingHay(boolean eatingHay) {
        this.eatingHay = eatingHay;
    }

    public boolean isHasReproduced() {
        return hasReproduced;
    }

    public void setHasReproduced(boolean hasReproduced) {
        this.hasReproduced = hasReproduced;
    }

    public int getTemper() {
        return temper;
    }

    public void setTemper(int temper) {
        this.temper = temper;
    }

    public UUID getOwnerUUID() {
        return ownerUUID;
    }

    public void setOwnerUUID(UUID ownerUUID) {
        this.ownerUUID = ownerUUID;
    }

    public void setInventory(HorseInventory inventory) {
        this.inventory = inventory;
    }
}
