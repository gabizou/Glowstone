package net.glowstone.entity.components;

import com.artemis.Component;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ZombieComponent extends Component {

    private boolean isBaby = false, isVillager = false, canBreakDoors = true;
    private int conversionTime;
}
