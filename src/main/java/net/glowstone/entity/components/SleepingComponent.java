package net.glowstone.entity.components;

import com.artemis.Component;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class SleepingComponent extends Component {

    /**
     * How long this human has been sleeping.
     */
    private int sleepingTicks = 0;

    private boolean isSleeping = false;

}
