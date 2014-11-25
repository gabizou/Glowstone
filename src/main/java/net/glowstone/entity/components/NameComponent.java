package net.glowstone.entity.components;

import com.artemis.Component;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class NameComponent extends Component {

    private String customName = null;
    private boolean customNameVisible = false;
}
