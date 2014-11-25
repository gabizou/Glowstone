package net.glowstone.entity.components;

import com.artemis.Component;
import lombok.*;
import org.bukkit.entity.Villager;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class MerchantProfessionComponent extends Component {

    @NonNull
    private Villager.Profession profession;

}
