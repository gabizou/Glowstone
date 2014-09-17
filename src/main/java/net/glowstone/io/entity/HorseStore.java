package net.glowstone.io.entity;

import java.util.UUID;

import net.glowstone.entity.animals.GlowHorse;
import net.glowstone.io.nbt.NbtSerialization;
import net.glowstone.util.nbt.CompoundTag;
import net.glowstone.util.nbt.TagType;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Horse.Style;
import org.bukkit.entity.Horse.Variant;
import org.bukkit.inventory.ItemStack;

class HorseStore extends AgeableStore<GlowHorse> {

    private static final String EATING_HAYSTACK_KEY = "EatingHaystack";
    private static final String BRED_KEY = "Bred";
    private static final String CHESTED_HORSE_KEY = "ChestedHorse";
    private static final String HAS_REPRODUCED_KEY = "HasReproduced";
    private static final String TYPE_KEY = "Type";
    private static final String VARIANT_KEY = "Variant";
    private static final String TEMPER_Key = "Temper";
    private static final String TAME_KEY = "Tame";
    private static final String OWNER_UUID_KEY = "OwnerUUID";
    private static final String ARMOR_ITEM_KEY = "ArmorItem";
    private static final String SADDLE_ITEM_KEY = "SaddleItem";
    private static final String SADDLE_KEY = "Saddle";
    public static final String ITEMS_KEY = "Items";

    public HorseStore() {
        super(GlowHorse.class, "Horse");
    }

    @Override
    public GlowHorse createEntity(Location location, CompoundTag compound) {
        return new GlowHorse(location);
    }

    public void load(GlowHorse entity, CompoundTag compound) {
        super.load(entity, compound);
        entity.setEatingHay(compound.getBool(EATING_HAYSTACK_KEY));
        entity.setCarryingChest(compound.getBool(CHESTED_HORSE_KEY));
        entity.setHasReproduced(compound.getBool(HAS_REPRODUCED_KEY));
        entity.setStyle(Style.values()[compound.getInt(TYPE_KEY)]);
        entity.setVariant(Variant.values()[compound.getInt(VARIANT_KEY)]);
        entity.setTemper(compound.getInt(TEMPER_Key));
        entity.setTamed(compound.getBool(TAME_KEY));
        if (compound.containsKey(OWNER_UUID_KEY))
            entity.setOwnerUUID(UUID.fromString(compound.getString(OWNER_UUID_KEY)));
        if (compound.containsKey(ARMOR_ITEM_KEY)) {
            entity.getInventory().setArmor(NbtSerialization.readItem(compound.getCompound(ARMOR_ITEM_KEY)));
        }
        if (compound.containsKey(SADDLE_ITEM_KEY)) {
            entity.getInventory().setSaddle(NbtSerialization.readItem(compound.getCompound(SADDLE_ITEM_KEY)));
        } else if (compound.getBool(SADDLE_KEY)) {
            entity.getInventory().setSaddle(new ItemStack(Material.SADDLE));
        }
        if (entity.isCarryingChest()) {
            compound.putList(ITEMS_KEY, TagType.COMPOUND, NbtSerialization.writeInventory(entity.getInventory().getContents(), entity.getInventory().getContents().length));
        }

    }

    public void save(GlowHorse entity, CompoundTag tag) {
        super.save(entity, tag);
        tag.putBool(EATING_HAYSTACK_KEY, entity.isEatingHay());
        tag.putBool(CHESTED_HORSE_KEY, entity.isCarryingChest());
        tag.putBool(HAS_REPRODUCED_KEY, entity.isHasReproduced());
        tag.putBool(BRED_KEY, true);
        tag.putInt(TYPE_KEY, entity.getStyle().ordinal());
        tag.putBool(SADDLE_KEY, entity.getInventory().getSaddle() != null);
    }
}
