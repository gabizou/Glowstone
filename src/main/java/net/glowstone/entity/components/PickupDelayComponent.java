package net.glowstone.entity.components;

import com.artemis.Component;
import lombok.*;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class PickupDelayComponent extends Component {
    /**
     * The remaining delay until this item may be picked up.
     */
    private int pickupDelay;

    public void decreasePickupDelay() {
        --pickupDelay;
    }
}
