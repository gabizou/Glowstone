package net.glowstone.entity.components;

import com.artemis.Component;
import lombok.*;
import org.bukkit.entity.Horse;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public class HorseSpeciesComponent extends Component {

    @NonNull
    private Horse.Variant variant;
    @NonNull
    private Horse.Color color;
    @NonNull
    private Horse.Style style;

}
