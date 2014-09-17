package net.glowstone.inventory;

import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.HorseInventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

/**
 * A class which represents an inventory and the items it contains.
 */
public class GlowHorseInventory extends GlowInventory implements HorseInventory {

    private ItemStack saddle;

    private ItemStack armor;

    public GlowHorseInventory(InventoryHolder owner) {
        this(owner, 27);
    }

    public GlowHorseInventory(InventoryHolder owner, int size) {
        this(owner, size, "Horse");
    }

    public GlowHorseInventory(InventoryHolder owner, int size, String title) {
        super(owner, InventoryType.CHEST, size, title); //TODO fix this.
    }

    @Override
    public ItemStack getSaddle() {
        return saddle != null ? saddle.clone() : null;
    }

    @Override
    public ItemStack getArmor() {
        return armor != null ? armor.clone() : null;
    }

    @Override
    public void setSaddle(ItemStack itemStack) {
        if (itemStack != null) {
            this.saddle = new ItemStack(itemStack);
        } else {
            this.armor = null;
        }
    }

    @Override
    public void setArmor(ItemStack itemStack) {
        if (itemStack != null) {
            this.armor = new ItemStack(itemStack);
        } else {
            this.armor = null;
        }
    }
}
