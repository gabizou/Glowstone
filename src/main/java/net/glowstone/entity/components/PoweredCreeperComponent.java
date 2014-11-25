package net.glowstone.entity.components;

import com.artemis.Component;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by gabizou on 10/29/14.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class PoweredCreeperComponent extends Component {

    private boolean isPowered;

}
