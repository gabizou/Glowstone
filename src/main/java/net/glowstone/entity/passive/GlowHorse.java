package net.glowstone.entity.passive;

import com.artemis.ComponentMapper;
import com.flowpowered.networking.Message;
import com.google.common.base.Preconditions;
import net.glowstone.entity.components.ChestCarryingComponent;
import net.glowstone.entity.components.HorseSpeciesComponent;
import net.glowstone.entity.meta.MetadataIndex;
import net.glowstone.entity.meta.MetadataMap;
import net.glowstone.inventory.GlowHorseInventory;
import net.glowstone.net.message.play.entity.EntityMetadataMessage;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.AnimalTamer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;
import org.bukkit.inventory.HorseInventory;

import java.util.List;
import java.util.Random;

public class GlowHorse extends GlowTameable implements Horse {

    private int domestication;
    private int maxDomestication;
    private double jumpStrength;
    private boolean eatingHay;
    private boolean hasReproduced;
    private int temper;

    private HorseInventory inventory = new GlowHorseInventory(this);

    public GlowHorse(Location location) {
        this(location, null);
    }

    protected GlowHorse(Location location, AnimalTamer owner) {
        super(location, EntityType.HORSE, owner);
        Random rand = new Random();
        getArtemisEntity().edit()
                .add(new HorseSpeciesComponent(Variant.HORSE, Color.values()[rand.nextInt(6)], Style.values()[rand.nextInt(4)]));
    }

    ////////////////////////////////////////////////////////////////////////////
    // Artemis getters and setters.
    protected HorseSpeciesComponent getHorseSpeciesComponent() {
        return ComponentMapper.getFor(HorseSpeciesComponent.class, this.getArtemisEntity().getWorld()).get(this.getArtemisEntity());
    }

    protected ChestCarryingComponent getChestCarryingComponent() {
        return ComponentMapper.getFor(ChestCarryingComponent.class, this.getArtemisEntity().getWorld()).get(this.getArtemisEntity());
    }

    @Override
    public Variant getVariant() {
        return this.getHorseSpeciesComponent().getVariant();
    }

    @Override
    public void setVariant(Variant variant) {
        Preconditions.checkArgument(variant != null, "Cannot set a null variant!");
        this.getHorseSpeciesComponent().setVariant(variant);
    }

    @Override
    public Color getColor() {
        return this.getHorseSpeciesComponent().getColor();
    }

    @Override
    public void setColor(Color color) {
        Preconditions.checkArgument(color != null, "Cannot set a null variant!");
        this.getHorseSpeciesComponent().setColor(color);
    }

    @Override
    public Style getStyle() {
        return this.getHorseSpeciesComponent().getStyle();
    }

    @Override
    public void setStyle(Style style) {
        Preconditions.checkArgument(style != null, "Cannot set a null variant!");
        this.getHorseSpeciesComponent().setStyle(style);
    }

    @Override
    public boolean isCarryingChest() {
        return getChestCarryingComponent().isChested();
    }

    @Override
    public void setCarryingChest(boolean b) {
        if (b) {
           // TODO Manipulate the HorseInventory somehow
        }
        this.getChestCarryingComponent().setChested(b);
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

    public void setInventory(HorseInventory inventory) {
        this.inventory = inventory;
    }

    public boolean isEatingHay() {
        return eatingHay;
    }

    public void setEatingHay(boolean eatingHay) {
        this.eatingHay = eatingHay;
    }

    public boolean hasReproduced() {
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

    @Override
    public List<Message> createSpawnMessage() {
        List<Message> messages = super.createSpawnMessage();
        MetadataMap map = new MetadataMap(GlowHorse.class);
        map.set(MetadataIndex.HORSE_TYPE, (byte) this.getVariant().ordinal());
        map.set(MetadataIndex.HORSE_FLAGS, getHorseFlags());
        map.set(MetadataIndex.HORSE_STYLE, getHorseStyleData());
        map.set(MetadataIndex.HORSE_ARMOR, getHorseArmorData());
        messages.add(new EntityMetadataMessage(id, map.getEntryList()));
        return messages;
    }

    private int getHorseFlags() {
        int value = 0;
        if (isTamed()) {
            value |= 0x02;
        }
        if (getInventory() != null && getInventory().getSaddle() != null) {
            value |= 0x04;
        }
        if (isCarryingChest()) {
            value |= 0x08;
        }
        if (hasReproduced) {
            value |= 0x10;
        }
        if (isEatingHay()) {
            value |= 0x20;
        }
        return value;
    }

    private int getHorseStyleData() {
        HorseSpeciesComponent component = getHorseSpeciesComponent();
        return component.getColor().ordinal() & 0xFF | component.getStyle().ordinal() << 8;
    }

    private int getHorseArmorData() {
        if (getInventory().getArmor() != null) {
            if (getInventory().getArmor().getType() == Material.DIAMOND_BARDING) {
                return 3;
            } else if (getInventory().getArmor().getType() == Material.GOLD_BARDING) {
                return 2;
            } else if (getInventory().getArmor().getType() == Material.IRON_BARDING) {
                return 1;
            }
        }
        return 0;
    }
}
