package net.glowstone.entity.components;

import com.artemis.Component;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.glowstone.entity.physics.EntityBoundingBox;

@Data
@EqualsAndHashCode(callSuper = false)
public class EntityBoundingBoxComponent extends Component {

    /**
     * The entity's bounding box, or null if it has no physical presence.
     */
    private EntityBoundingBox entityBoundingBox;
}
