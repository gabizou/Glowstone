package net.glowstone.entity.components;

import com.artemis.Component;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class FireComponent extends Component {

    public static final int MAX_FIRE_TICKS = 160;
    private int fireTicks;
}
