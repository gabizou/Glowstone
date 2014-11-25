package net.glowstone.entity.components;

import com.artemis.Component;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import org.bukkit.GameMode;

@Data
@EqualsAndHashCode(callSuper = false)
public class GameModeComponent extends Component {

    @NonNull
    private GameMode gamemode = GameMode.SURVIVAL;

    public final boolean canDrown() {
        return gamemode == GameMode.SURVIVAL || gamemode == GameMode.ADVENTURE;
    }

}
