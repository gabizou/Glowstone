package net.glowstone.entity.components;

import com.artemis.Component;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import org.bukkit.Art;

@Data
@EqualsAndHashCode(callSuper = false)
public class PaintingComponent extends Component {

    @NonNull
    private Art motive;
}
