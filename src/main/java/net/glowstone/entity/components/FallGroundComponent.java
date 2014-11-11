package net.glowstone.entity.components;

import com.artemis.Component;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class FallGroundComponent extends Component {

    /**
     * A flag indicting if the entity is on the ground
     */
    private boolean onGround = true;
    /**
     * The distance the entity is currently falling without touching the ground.
     */
    private float fallDistance;
}
