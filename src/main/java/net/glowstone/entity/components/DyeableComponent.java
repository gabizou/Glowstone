package net.glowstone.entity.components;

import com.artemis.Component;
import lombok.*;
import org.bukkit.DyeColor;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class DyeableComponent extends Component {

    @NonNull
    private DyeColor dyeColor;

}
