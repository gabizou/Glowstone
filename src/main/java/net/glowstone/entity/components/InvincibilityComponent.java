package net.glowstone.entity.components;

import com.artemis.Component;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class InvincibilityComponent extends Component {

    public static final int DEFAULT_MAX_INVINCIBILITY_TICKS = 20;
    private int invincibilityTicks = 0, maxInvincibilityTicks = DEFAULT_MAX_INVINCIBILITY_TICKS;
}
