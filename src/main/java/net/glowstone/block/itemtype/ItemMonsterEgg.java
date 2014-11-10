package net.glowstone.block.itemtype;

import net.glowstone.block.GlowBlock;
import net.glowstone.entity.GlowPlayer;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.SpawnEgg;
import org.bukkit.util.Vector;

/**
 * Base class for specific types of items.
 */
public class ItemMonsterEgg extends ItemType {

    @Override
    public void rightClickBlock(GlowPlayer player, GlowBlock target, BlockFace face, ItemStack holding, Vector clickedLoc) {
        final SpawnEgg data = (SpawnEgg) holding.getData();
        final EntityType type = data.getSpawnedType();
        switch (face) {
            case UP:
            default:
                target.getWorld().spawnEntity(target.getLocation().add(0.5, 0.25, 0.5), type);
        }
    }
}
