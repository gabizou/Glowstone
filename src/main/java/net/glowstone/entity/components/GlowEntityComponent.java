package net.glowstone.entity.components;

import com.artemis.Component;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.glowstone.entity.GlowEntity;

/**
 * Component to get back to the Glowstone entity.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class GlowEntityComponent extends Component {

    private final GlowEntity entity;
}
