package net.glowstone.entity.components;

import com.artemis.Component;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.bukkit.material.MaterialData;

@Data
@EqualsAndHashCode(callSuper = false)
public class CarriedMaterialComponent extends Component {

    private MaterialData materialData;

}
