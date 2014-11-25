package net.glowstone.entity.components;

import com.artemis.Component;
import lombok.*;
import org.bukkit.entity.Skeleton;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class SkeletonTypeComponent extends Component {

    @NonNull
    private Skeleton.SkeletonType skeletonType;

}
