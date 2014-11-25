package net.glowstone.entity.components;

import com.artemis.Component;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.bukkit.util.Vector;

@Data
@EqualsAndHashCode(callSuper = false)
public class VelocityComponent extends Component {

    private Vector velocity = new Vector();
    /**
     * Whether the entity should have its velocity resent.
     */
    private boolean velocityChanged = false;
}
