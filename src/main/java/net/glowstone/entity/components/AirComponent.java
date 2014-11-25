package net.glowstone.entity.components;

import com.artemis.Component;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class AirComponent extends Component {

    public static final int DEFAULT_MAX_AIR = 300;
    private int air = DEFAULT_MAX_AIR, maxAir = DEFAULT_MAX_AIR;
}
