package net.glowstone.entity.components;

import com.artemis.Component;
import lombok.*;
import org.bukkit.entity.Rabbit;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class RabbitSpeciesComponent extends Component {

    @NonNull
    private Rabbit.RabbitType rabbitType;
}
