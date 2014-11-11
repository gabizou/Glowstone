package net.glowstone.entity.components;

import com.artemis.Component;
import lombok.*;
import org.bukkit.entity.Ocelot;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class OcelotSpeciesComponent extends Component {

    @NonNull
    private Ocelot.Type ocelotType;

}
