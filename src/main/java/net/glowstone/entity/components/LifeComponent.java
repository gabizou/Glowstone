package net.glowstone.entity.components;

import com.artemis.Component;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Keep track of the entity life time
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class LifeComponent extends Component {

    private int ticksLived;


}
