package net.glowstone.entity.components;

import com.artemis.Component;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Age component for storing the age value of an entity
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class AgeComponent extends Component {

    public static final int AGE_BABY = -24000;
    public static final int AGE_ADULT = 0;
    public static final int BREEDING_AGE = 6000;

    private int age;
    private boolean ageLocked;

    public void increaseAge() {
        this.age++;
    }

}
