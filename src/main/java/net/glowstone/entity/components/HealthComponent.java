package net.glowstone.entity.components;

import com.artemis.Component;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class HealthComponent extends Component {

    public static final double DEFAULT_MAX_HEALTH = 20;
    private double maxHealth, health;

    public void setHealth(double health) {
        if (health < 0) {
            health = 0;
        } else {
            this.health = (health > maxHealth) ? maxHealth : health;
        }
    }
}
