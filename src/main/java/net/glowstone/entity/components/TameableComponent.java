package net.glowstone.entity.components;

import com.artemis.Component;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public class TameableComponent extends Component {

    private UUID ownerUniqueId;
    private boolean isTamed;
    private boolean isSitting;

}
