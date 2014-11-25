package net.glowstone.entity.components;

import com.artemis.Component;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ExpirableLifeComponent extends Component {

    private int maxLife;

}
