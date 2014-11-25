package net.glowstone.entity.components;

import com.artemis.Component;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.bukkit.inventory.InventoryView;

@Data
@EqualsAndHashCode(callSuper = false)
public class InventoryViewComponent extends Component {

    private InventoryView inventoryView;

}
