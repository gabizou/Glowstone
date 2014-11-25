package net.glowstone.entity.components;

import com.artemis.Component;
import lombok.*;

@Data
@EqualsAndHashCode(callSuper = false)
public class ExpirableAngerComponent extends Component {

    @Getter
    private int anger = 0;

    public boolean isAngry() {
        return anger > 0;
    }

    public boolean decreaseAnger() {
        if (anger <= 0) {
            return false;
        } else {
            anger--;
            return true;
        }
    }

}
